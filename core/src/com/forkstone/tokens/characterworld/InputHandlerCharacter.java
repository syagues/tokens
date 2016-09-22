package com.forkstone.tokens.characterworld;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;

import java.util.ArrayList;

/**
 * Created by sergi on 21/1/16.
 */
public class InputHandlerCharacter implements InputProcessor {

    private static final String TAG = "InputHandlerCharacter";
    private final CharacterWorld world;
    private final float scaleFactorX, scaleFactorY;
    private final Rectangle rectangle;
    private ArrayList<MenuButton> menuButtons;
    private float initialPointY;
    private Text tapText;

    public InputHandlerCharacter(CharacterWorld world, float scaleFactorX, float scaleFactorY) {
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

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (menuButtons.get(0).isTouchUp(screenX, screenY)) {
            if(world.getPart() < 4){
                world.goToNextPart();
            } else {
                AssetLoader.setCharacterPart(1);
//                world.goToTutorialScreen("NORMAL", 1);
                world.goToStoryScreen(1);
            }
        } else if(menuButtons.size() == 2){
            if(menuButtons.get(1).isTouchUp(screenX, screenY)){
                AssetLoader.setCharacterPart(1);
                world.goToStoryScreen(1);
            }
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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