package com.forkstone.tokens.levelworld;

import com.badlogic.gdx.InputProcessor;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.LevelButton;
import com.forkstone.tokens.ui.MenuButton;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

/**
 * Created by sergi on 9/12/15.
 */
public class InputHandlerLevel implements InputProcessor {

    private static final String TAG = "InputHandlerLevel";
    private final LevelWorld world;
    private final float scaleFactorX, scaleFactorY;
    private final Rectangle rectangle;
    private ArrayList<LevelButton> levelButtons;
    private ArrayList<MenuButton> menuButtons;
    private float initialPointY;

    public InputHandlerLevel(LevelWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        rectangle = new Rectangle(0, 0, 200, 200);
        levelButtons = world.getLevelButtons();
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
        for (int i = 0; i < levelButtons.size(); i++) {
            if (levelButtons.get(i).isTouchDown(screenX, screenY)) {
                if(AssetLoader.getSoundState())
                    AssetLoader.soundClick.play();
            }
        }
        for (int i = 0; i < menuButtons.size(); i++) {
            if (menuButtons.get(i).isTouchDown(screenX, screenY)) {
                if(AssetLoader.getSoundState())
                    AssetLoader.soundClick.play();
            }
        }
        initialPointY = screenY;
        //Gdx.app.log(TAG, "InitialPointY TouchDown: " + initialPointY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        boolean memory = false, changePosition = false, changeColor = false, changeBlocked = false;

        for (int i = 0; i < levelButtons.size(); i++) {
            if(levelButtons.get(i).isTouchUp(screenX, screenY)) {
                if(!AssetLoader.getTutorialMemory()) {
                    for (int l = 0; l < Settings.MEMORY_LEVELS.length; l++){
                        if(levelButtons.get(i).getGameLevel() == Settings.MEMORY_LEVELS[l]) {
                            memory = true;
                        }
                    }
                }
                if(!AssetLoader.getTutorialPositionChange()) {
                    for (int l = 0; l < Settings.CHANGEPOSITION_LEVELS.length; l++) {
                        if (levelButtons.get(i).getGameLevel() == Settings.CHANGEPOSITION_LEVELS[l]) {
                            changePosition = true;
                        }
                    }
                }
                if(!AssetLoader.getTutorialColorChange()) {
                    for (int l = 0; l < Settings.CHANGECOLOR_LEVELS.length; l++) {
                        if (levelButtons.get(i).getGameLevel() == Settings.CHANGECOLOR_LEVELS[l]) {
                            changeColor = true;
                        }
                    }
                }
                if(!AssetLoader.getTutorialBlockedChange()) {
                    for (int l = 0; l < Settings.CHANGEBLOCKED_LEVELS.length; l++) {
                        if (levelButtons.get(i).getGameLevel() == Settings.CHANGEBLOCKED_LEVELS[l]) {
                            changeBlocked = true;
                        }
                    }
                }

                if(levelButtons.get(i).getGameLevel() == Settings.FIRST_BLOCK_TOKEN_LEVEL && AssetLoader.getTutorialPart() <= 1) {
                    world.goToTutorialScreen("BLOCK", Settings.FIRST_BLOCK_TOKEN_LEVEL);
                } else if(levelButtons.get(i).getGameLevel() == Settings.FIRST_TIME_LEVEL && AssetLoader.getTutorialPart() == 2) {
                    world.goToTutorialScreen("TIME", Settings.FIRST_TIME_LEVEL);
                } else if(memory) {
                    world.goToTutorialScreen("MEMORY", levelButtons.get(i).getGameLevel());
                } else if(changePosition) {
                    world.goToTutorialScreen("CHANGEPOSITION", levelButtons.get(i).getGameLevel());
                } else if(changeColor) {
                    world.goToTutorialScreen("CHANGECOLOR", levelButtons.get(i).getGameLevel());
                } else if(changeBlocked) {
                    world.goToTutorialScreen("CHANGEBLOCKED", levelButtons.get(i).getGameLevel());
                } else if(levelButtons.get(i).getGameLevel() == Settings.DISTRICTTWO_FIRSTLEVEL &&
                        AssetLoader.getStoryPart() == 1){
                    world.goToStoryScreen(2);
                } else if(levelButtons.get(i).getGameLevel() == Settings.DISTRICTTHREE_FIRSTLEVEL &&
                        AssetLoader.getStoryPart() == 2){
                    world.goToStoryScreen(3);
                } else if(levelButtons.get(i).getGameLevel() == Settings.DISTRICTFOUR_FIRSTLEVEL &&
                        AssetLoader.getStoryPart() == 3){
                    world.goToStoryScreen(4);
                } else if(levelButtons.get(i).getGameLevel() == Settings.DISTRICTFIVE_FIRSTLEVEL &&
                        AssetLoader.getStoryPart() == 4){
                    world.goToStoryScreen(5);
                } else if(levelButtons.get(i).getGameLevel() == Settings.DISTRICTSIX_FIRSTLEVEL &&
                        AssetLoader.getStoryPart() == 5){
                    world.goToStoryScreen(6);
                } else if(levelButtons.get(i).getGameLevel() == Settings.DISTRICTSEVEN_FIRSTLEVEL &&
                        AssetLoader.getStoryPart() == 6){
                    world.goToStoryScreen(7);
                } else if(levelButtons.get(i).getGameLevel() == Settings.DISTRICTEIGHT_FIRSTLEVEL &&
                        AssetLoader.getStoryPart() == 7){
                    world.goToStoryScreen(8);
                } else if(levelButtons.get(i).getGameLevel() == Settings.DISTRICTNINE_FIRSTLEVEL &&
                        AssetLoader.getStoryPart() == 8){
                    world.goToStoryScreen(9);
                } else if(levelButtons.get(i).getGameLevel() == Settings.DISTRICTTEN_FIRSTLEVEL &&
                        AssetLoader.getStoryPart() == 9){
                    world.goToStoryScreen(10);
                } else {
                    world.goToGameScreen(levelButtons.get(i).getGameLevel());
                }
            }
        }
        for (int i = 0; i < menuButtons.size(); i++) {
            if(menuButtons.get(0).isTouchUp(screenX, screenY)) {
                if (world.getCurrentDistrict() > 1){
                    world.goToLevelScreen(world.getCurrentDistrict()-1);
                }
            } else if(menuButtons.get(1).isTouchUp(screenX, screenY)) {
                if((AssetLoader.getLevel() > world.getCurrentDistrict()*25) && world.getCurrentDistrict() < Settings.DISTRICTS_NUMBER){
                    world.goToLevelScreen(world.getCurrentDistrict()+1);
                }
            } else if(menuButtons.get(2).isTouchUp(screenX, screenY)) {
                world.goToHomeScreen();
            } else if(menuButtons.get(3).isTouchUp(screenX, screenY)) {
                world.goToShopScreen();
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
