package com.forkstone.tokens.tutorialworld;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;

import java.util.ArrayList;

/**
 * Created by sergi on 4/1/16.
 */
public class InputHandlerTutorial implements InputProcessor {

    private static final String TAG = "InputHandlerTutorial";
    private final TutorialWorld world;
    private final float scaleFactorX, scaleFactorY;
    private final Rectangle rectangle;
    private ArrayList<MenuButton> menuButtons;
    private float initialPointY;

    public InputHandlerTutorial(TutorialWorld world, float scaleFactorX, float scaleFactorY) {
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

        initialPointY = screenY;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if(menuButtons.get(0).isTouchUp(screenX, screenY)) {
            if (world.stateName.equals("BLOCK")) {
                AssetLoader.setTutorialPart(2);
                world.goToGameScreen(Settings.FIRST_BLOCK_TOKEN_LEVEL);
            } else if (world.stateName.equals("TIME")) {
                AssetLoader.setTutorialPart(3);
                world.goToGameScreen(Settings.FIRST_TIME_LEVEL);
            } else if (world.stateName.equals("MEMORY")) {
                AssetLoader.setTutorialMemory(true);
                world.goToGameScreen(world.getLevel());
            } else if (world.stateName.equals("CHANGEPOSITION")) {
                AssetLoader.setTutorialPositionChange(true);
                world.goToGameScreen(world.getLevel());
            } else if (world.stateName.equals("CHANGECOLOR")) {
                AssetLoader.setTutorialColorChange(true);
                world.goToGameScreen(world.getLevel());
            } else if (world.stateName.equals("CHANGEBLOCKED")) {
                AssetLoader.setTutorialBlockedChange(true);
                world.goToGameScreen(world.getLevel());
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
