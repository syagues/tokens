package com.forkstone.tokens.storyworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;
import java.util.ArrayList;

/**
 * Created by sergi on 21/1/16.
 */
public class InputHandlerStory implements InputProcessor {

    private static final String TAG = "InputHandlerStory";
    private final StoryWorld world;
    private final float scaleFactorX, scaleFactorY;
    private final Rectangle rectangle;
    private ArrayList<MenuButton> menuButtons;
    private float initialPointY;
    private Text tapText;

    public InputHandlerStory(StoryWorld world, float scaleFactorX, float scaleFactorY) {
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
        boolean memory = false, changePosition = false, changeColor = false, changeBlocked = false;
        Gdx.app.log(TAG, "Level: " + AssetLoader.getLevel());

        if(menuButtons.get(0).isTouchUp(screenX, screenY)){
            if(!AssetLoader.getTutorialMemory()) {
                for (int l = 0; l < Settings.MEMORY_LEVELS.length; l++){
                    if(AssetLoader.getLevel() == Settings.MEMORY_LEVELS[l]) {
                        memory = true;
                    }
                }
            }
            if(!AssetLoader.getTutorialPositionChange()) {
                for (int l = 0; l < Settings.CHANGEPOSITION_LEVELS.length; l++) {
                    if (AssetLoader.getLevel() == Settings.CHANGEPOSITION_LEVELS[l]) {
                        changePosition = true;
                    }
                }
            }
            if(!AssetLoader.getTutorialColorChange()) {
                for (int l = 0; l < Settings.CHANGECOLOR_LEVELS.length; l++) {
                    if (AssetLoader.getLevel() == Settings.CHANGECOLOR_LEVELS[l]) {
                        changeColor = true;
                    }
                }
            }
            if(!AssetLoader.getTutorialBlockedChange()) {
                for (int l = 0; l < Settings.CHANGEBLOCKED_LEVELS.length; l++) {
                    if (AssetLoader.getLevel() == Settings.CHANGEBLOCKED_LEVELS[l]) {
                        changeBlocked = true;
                    }
                }
            }


            if(memory) {
                world.goToTutorialScreen("MEMORY", AssetLoader.getLevel());
            } else if (changePosition) {
                world.goToTutorialScreen("CHANGEPOSITION", AssetLoader.getLevel());
            } else if (changeColor) {
                world.goToTutorialScreen("CHANGECOLOR", AssetLoader.getLevel());
            } else if (changeBlocked) {
                world.goToTutorialScreen("CHANGEBLOCKED", AssetLoader.getLevel());
            } else if (world.getPart() == 1){
                AssetLoader.setStoryPart(1);
                world.goToGameScreen(Settings.DISTRICTONE_FIRSTLEVEL);
            } else if (world.getPart() == 2){
                AssetLoader.setStoryPart(2);
                world.goToGameScreen(Settings.DISTRICTTWO_FIRSTLEVEL);
            } else if (world.getPart() == 3){
                AssetLoader.setStoryPart(3);
                world.goToGameScreen(Settings.DISTRICTTHREE_FIRSTLEVEL);
            } else if (world.getPart() == 4){
                AssetLoader.setStoryPart(4);
                world.goToGameScreen(Settings.DISTRICTFOUR_FIRSTLEVEL);
            } else if (world.getPart() == 5){
                AssetLoader.setStoryPart(5);
                world.goToGameScreen(Settings.DISTRICTFIVE_FIRSTLEVEL);
            } else if (world.getPart() == 6){
                AssetLoader.setStoryPart(6);
                world.goToGameScreen(Settings.DISTRICTSIX_FIRSTLEVEL);
            } else if (world.getPart() == 7){
                AssetLoader.setStoryPart(7);
                world.goToGameScreen(Settings.DISTRICTSEVEN_FIRSTLEVEL);
            } else if (world.getPart() == 8){
                AssetLoader.setStoryPart(8);
                world.goToGameScreen(Settings.DISTRICTEIGHT_FIRSTLEVEL);
            } else if (world.getPart() == 9){
                AssetLoader.setStoryPart(9);
                world.goToGameScreen(Settings.DISTRICTNINE_FIRSTLEVEL);
            } else if (world.getPart() == 10){
                AssetLoader.setStoryPart(10);
                world.goToGameScreen(Settings.DISTRICTTEN_FIRSTLEVEL);
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
