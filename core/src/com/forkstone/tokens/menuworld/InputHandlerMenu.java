package com.forkstone.tokens.menuworld;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.MenuButton;

/**
 * Created by sergi on 1/12/15.
 */
public class InputHandlerMenu implements InputProcessor {

    private final MenuWorld world;
    private final float scaleFactorX, scaleFactorY;
    private final Rectangle rectangle;
    private ArrayList<MenuButton> menuButtons;

    public InputHandlerMenu(MenuWorld world, float scaleFactorX, float scaleFactorY) {
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
        if (rectangle.contains(screenX, screenY)) {
        }

        if (menuButtons.get(0).isTouchUp(screenX, screenY)) {
            if(AssetLoader.getCharacterPart() == 0){
//                world.goToCharacterScreen(1);
//                world.goToTutorialScreen("NORMAL", 1);
                world.goToTutorial2Screen("NORMAL", 1);
            } else {
                world.goToLevelScreen();
            }
//            world.goToSubmenuScreen();
        } else if (menuButtons.get(1).isTouchUp(screenX, screenY)) {
            world.goToShopScreen();
        } else if (menuButtons.get(2).isTouchUp(screenX, screenY)) {
            world.actionResolver.rateGame();
            AssetLoader.setIsRated(true);
        } else if (menuButtons.get(3).isTouchUp(screenX, screenY)) {
            world.goToSettingsScreen();
        } else if (menuButtons.get(4).isTouchUp(screenX, screenY)) {
            world.actionResolver.shareGame(Settings.SHARE_MESSAGE);
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
