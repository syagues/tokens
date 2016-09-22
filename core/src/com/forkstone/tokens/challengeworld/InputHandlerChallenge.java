package com.forkstone.tokens.challengeworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by sergi on 14/2/16.
 */
public class InputHandlerChallenge implements InputProcessor {

    private static final String TAG = "InputHandlerChallenge";
    private final ChallengeWorld world;
    private final float scaleFactorX, scaleFactorY;
    private final Rectangle rectangle;
    private ArrayList<MenuButton> menuButtons;
    private int buttonPosition = -1, buttonNumber = -1;
    private int initialScreenX, initialScreenY;
    private Text tapText;

    public InputHandlerChallenge(ChallengeWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        rectangle = new Rectangle(0, 0, 200, 200);
        menuButtons = world.getMenuButtons();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        for (int i = 0; i < menuButtons.size(); i++) {
            if (menuButtons.get(i).isTouchDown(screenX, screenY)) {
                if(AssetLoader.getSoundState())
                    AssetLoader.soundClick.play();
            }
        }

        buttonPosition = world.getBoard().getButtonTablePositionByTouchDown(screenX, screenY);
        buttonNumber = world.getBoard().getButtonNumberByTouchDown(screenX, screenY);

        // Initial Screen Position
        initialScreenX = screenX;
        initialScreenY = screenY;

//        Gdx.app.log(TAG, "ButtonPosition: " + buttonPosition + ", ButtonNumber: " + buttonNumber);
//        Gdx.app.log(TAG, "touchDown position: (" + initialScreenX + ", " + initialScreenY + ")");

        // Set checkSprite transparent when is touching down
        if(buttonNumber != -1 && buttonPosition != -1) {
            world.getBoard().getColorButtonByTablePosition(buttonPosition).setOkSpriteOff();
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (menuButtons.get(0).isTouchUp(screenX, screenY)) {
            world.goToHomeScreen();
        } else {
            // Not Buttons (Game)
            // Screen Position
            if(initialScreenX != screenX || initialScreenY != screenY){
                if(buttonNumber != -1 && buttonPosition != -1 && !world.getBoard().getColorButtons().get(buttonNumber).isStatic()) {
                    if(world.getSteps()>0 || world.getTime()>0) {
//                        Gdx.app.log(TAG, "touchUp position: (" + screenX + ", " + screenY + ")");
//                        Gdx.app.log(TAG, "Gap Position: (" + world.getBoard().getTokenPositions().get(world.getBoard().getGapPositions()).x + ", " + world.getBoard().getTokenPositions().get(world.getBoard().getGapPositions()).y + ")");
                        world.getBoard().moveToken(buttonPosition, buttonNumber, screenX, screenY);
                    }
                }

                if(world.getBoard().getSameColorNumber() == 3) {world.getBoard().checkOkSpritesThree();}
                if(world.getBoard().getSameColorNumber() >= 4) {world.getBoard().checkOkSpritesFour();}
            }
        }

        // IsTouchUp
        world.getBoard().checkIfButtonsTouchUp(screenX, screenY);

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        if(buttonNumber != -1 && !world.getBoard().getColorButtons().get(buttonNumber).isStatic()) {
//            if(world.getSteps()>0 || world.getTime()>0) {
                if(world.getBoard().ableToMove(buttonPosition) == 1 &&
                        world.getBoard().getTokenPositions().get(buttonPosition).x >= (screenX - ((Settings.BUTTON_SIZE * 1.5f) / 2)) &&
                        world.getBoard().getTokenPositions().get(world.getBoard().getGapPositions()).x <= (screenX - ((Settings.BUTTON_SIZE * 1.5f) / 2))) {
                    world.getBoard().getColorButtons().get(buttonNumber).setPositionPointX(screenX - ((Settings.BUTTON_SIZE * 1.5f) / 2));
                } else if(world.getBoard().ableToMove(buttonPosition) == 2 &&
                        world.getBoard().getTokenPositions().get(buttonPosition).y <= (screenY - (((Settings.BUTTON_SIZE - 10) * 1.5f) / 2)) &&
                        world.getBoard().getTokenPositions().get(world.getBoard().getGapPositions()).y >= (screenY - (((Settings.BUTTON_SIZE + 10) * 1.5f) / 2))) {
                    world.getBoard().getColorButtons().get(buttonNumber).setPositionPointY(screenY - ((Settings.BUTTON_SIZE * 1.5f) / 2));
                } else if(world.getBoard().ableToMove(buttonPosition) == 3 &&
                        world.getBoard().getTokenPositions().get(buttonPosition).x <= (screenX - ((Settings.BUTTON_SIZE * 1.5f) / 2)) &&
                        world.getBoard().getTokenPositions().get(world.getBoard().getGapPositions()).x >= (screenX - ((Settings.BUTTON_SIZE * 1.5f) / 2))) {
                    world.getBoard().getColorButtons().get(buttonNumber).setPositionPointX(screenX - ((Settings.BUTTON_SIZE * 1.5f) / 2));
                } else if(world.getBoard().ableToMove(buttonPosition) == 4 &&
                        world.getBoard().getTokenPositions().get(buttonPosition).y >= (screenY - (((Settings.BUTTON_SIZE + 10) * 1.5f) / 2)) &&
                        world.getBoard().getTokenPositions().get(world.getBoard().getGapPositions()).y <= (screenY - (((Settings.BUTTON_SIZE - 10) * 1.5f) / 2))) {
                    world.getBoard().getColorButtons().get(buttonNumber).setPositionPointY(screenY - ((Settings.BUTTON_SIZE * 1.5f) / 2));
                }
//            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (world.gameHeight - screenY / scaleFactorY);
    }
}