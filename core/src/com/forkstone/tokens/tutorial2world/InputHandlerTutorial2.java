package com.forkstone.tokens.tutorial2world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.MenuButton;

import java.util.ArrayList;

/**
 * Created by sergi on 21/2/16.
 */
public class InputHandlerTutorial2 implements InputProcessor {

    private static final String TAG = "InputHandlerTutorial2";
    private final Tutorial2World world;
    private final float scaleFactorX, scaleFactorY;
    private Rectangle rectangle;
    private int buttonPosition = -1, buttonNumber = -1;
    private int initialScreenX, initialScreenY;
    private ArrayList<MenuButton> menuButtons;
    private MenuButton continueButton;
    private float initialPointY;

    public InputHandlerTutorial2(Tutorial2World world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        rectangle = new Rectangle(0, 0, 200, 200);
        menuButtons = world.getMenuButtons();
        continueButton = world.getContinueButton();
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

        if (continueButton.isTouchDown(screenX, screenY)) {
            if(AssetLoader.getSoundState())
                AssetLoader.soundClick.play();
        }

        if((world.getStateNum() >= 5 && world.getStateNum() < 8) || (world.getStateNum() >= 10 && world.getStateNum() < 13)){

            buttonPosition = world.getBoard().getButtonTablePositionByTouchDown(screenX, screenY);
            buttonNumber = world.getBoard().getButtonNumberByTouchDown(screenX, screenY);

            // Initial Screen Position
            initialScreenX = screenX;
            initialScreenY = screenY;
        } else {
            buttonPosition = -1;
            buttonNumber = -1;
        }

        if (world.getStateNum() == 5){
            if(buttonPosition != 4){
                buttonPosition = -1;
                buttonNumber = -1;
            }
        } else if(world.getStateNum() == 6){
            if(buttonPosition != 7){
                buttonPosition = -1;
                buttonNumber = -1;
            }
        } else if(world.getStateNum() == 7){
            if(buttonPosition != 8){
                buttonPosition = -1;
                buttonNumber = -1;
            }
        } else if(world.getStateNum() == 11){
            if(buttonPosition != 7){
                buttonPosition = -1;
                buttonNumber = -1;
            }
        } else if(world.getStateNum() == 12){
            if(buttonPosition != 2){
                buttonPosition = -1;
                buttonNumber = -1;
            }
        }

        // Set checkSprite transparent when is touching down
        if(buttonNumber != -1 && buttonPosition != -1) {
            world.getBoard().getColorButtonByTablePosition(buttonPosition).setOkSpriteOff();
            if(world.isMulticolor()){
                world.getBoard().getColorButtonByTablePosition(buttonPosition).setMulticolor();
                world.continueTutorial();
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (continueButton.isTouchUp(screenX, screenY)) {
            world.continueTutorial();
        } else {
            if(world.getStateNum() >= 5){
                if (menuButtons.get(0).isTouchUp(screenX, screenY)) {
                    //
                } else if(menuButtons.get(1).isTouchUp(screenX, screenY)) {

                } else if(menuButtons.get(3).isTouchUp(screenX, screenY)) {
                    if(world.getStateNum() == 10){
                        if(AssetLoader.getMulticolor() > 0 && !world.isMulticolor()){
                            menuButtons.get(3).setColor(world.parseColor(Settings.COLOR_RED_500,1f));
                            world.setMulticolorUp();
                            AssetLoader.restMulticolor(1);
                            world.continueTutorial();
                        }
                    }
                } else {
                    // Not Buttons (Game)
                    // Screen Position
                    if(initialScreenX != screenX || initialScreenY != screenY){
                        if(buttonNumber != -1 && buttonPosition != -1 && !world.getBoard().getColorButtons().get(buttonNumber).isStatic()) {
                            if(world.getSteps()>0 || world.getTime()>0) {
                                if(world.getBoard().moveToken(buttonPosition, buttonNumber, screenX, screenY)){
                                    if(world.getStateNum() < 7){
                                        world.continueTutorial();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(world.getBoard().getSameColorNumber() == 3) {world.getBoard().checkOkSpritesThree();}
            if(world.getBoard().getSameColorNumber() >= 4) {world.getBoard().checkOkSpritesFour();}
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
            if(world.getSteps()>0 || world.getTime()>0) {

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
            }
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
