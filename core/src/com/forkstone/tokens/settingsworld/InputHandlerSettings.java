package com.forkstone.tokens.settingsworld;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.LanguageButton;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;

import java.util.ArrayList;

/**
 * Created by sergi on 10/1/16.
 */
public class InputHandlerSettings implements InputProcessor {

    private static final String TAG = "InputHandlerSettings";
    private final SettingsWorld world;
    private final float scaleFactorX, scaleFactorY;
    private final Rectangle rectangle;
    private ArrayList<MenuButton> menuButtons;
    private ArrayList<LanguageButton> languageButtons;
    private ArrayList<Text> languageOptions;
    private float initialPointY;

    public InputHandlerSettings(SettingsWorld world, float scaleFactorX, float scaleFactorY) {

        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        rectangle = new Rectangle(0, 0, 200, 200);
        menuButtons = world.getMenuButtons();
        languageButtons = world.getLanguageButtons();
        languageOptions = world.getLanguageOptions();
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

        for (int i = 0; i < languageButtons.size(); i++) {
            if (languageButtons.get(i).isTouchDown(screenX, screenY)) {
                if(AssetLoader.getSoundState())
                    AssetLoader.soundClick.play();
            }
        }

        for (int i = 0; i < languageOptions.size(); i++) {
            if (languageOptions.get(i).isTouchDown(screenX, screenY)) {
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

        if(menuButtons.get(0).isTouchUp(screenX, screenY)){
            world.changeSoundButtonState(0);
        } else if(menuButtons.get(1).isTouchUp(screenX, screenY)){
            world.changeSoundButtonState(1);
        } else if(menuButtons.get(2).isTouchUp(screenX, screenY)){
            world.changeSettingsScreen();
            world.setLanguageScreen();
        } else if(menuButtons.get(3).isTouchUp(screenX, screenY)){
//            world.goToTutorialScreen("NORMAL", 2);
            world.goToTutorial2Screen("NORMAL", 2);
        } else if(menuButtons.get(4).isTouchUp(screenX, screenY)){
            if(world.isDefaultState()){
                world.goToHomeScreen();
            } else if(world.isLanguageState()){
                world.changeSettingsScreen();
                world.setDefaultScreen();
            }
        }

        // Language
        for (int i = 0; i < languageButtons.size(); i++){
            if(world.isLanguageState()){
                if(languageButtons.get(i).isTouchUp(screenX, screenY)){
                    if (!languageButtons.get(i).isActive()){
                        languageButtons.get(i).setActive();
                        AssetLoader.setLanguage(world.getLanguages()[i]);
                        for (int j = 0; j < languageButtons.size(); j++){
                            if(j != i){
                                languageButtons.get(j).setInactive();
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < languageOptions.size(); i++){
            if(languageOptions.get(i).isTouchUp(screenX, screenY)){
                if (!languageButtons.get(i).isActive()){
                    languageButtons.get(i).setActive();
                    AssetLoader.setLanguage(world.getLanguages()[i]);
                    for (int j = 0; j < languageButtons.size(); j++){
                        if(j != i){
                            languageButtons.get(j).setInactive();
                        }
                    }
                }
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
