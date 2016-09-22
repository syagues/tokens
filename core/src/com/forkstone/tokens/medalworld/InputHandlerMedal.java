package com.forkstone.tokens.medalworld;

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
public class InputHandlerMedal implements InputProcessor {

    private static final String TAG = "InputHandlerMedal";
    private final MedalWorld world;
    private final float scaleFactorX, scaleFactorY;
    private final Rectangle rectangle;
    private ArrayList<MenuButton> menuButtons;
    private float initialPointY;
    private Text tapText;

    public InputHandlerMedal(MedalWorld world, float scaleFactorX, float scaleFactorY) {
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

        if(menuButtons.get(0).isTouchUp(screenX, screenY)){
            if (world.getDistrict() == 1){
                AssetLoader.setMedalPart(1);
                world.goToStoryScreen(2);
            } else if (world.getDistrict() == 2){
                AssetLoader.setMedalPart(2);
                world.goToStoryScreen(3);
            } else if (world.getDistrict() == 3){
                AssetLoader.setMedalPart(3);
                world.goToStoryScreen(4);
            } else if (world.getDistrict() == 4){
                AssetLoader.setMedalPart(4);
                world.goToStoryScreen(5);
            } else if (world.getDistrict() == 5){
                AssetLoader.setMedalPart(5);
                world.goToStoryScreen(6);
            } else if (world.getDistrict() == 6){
                AssetLoader.setMedalPart(6);
                world.goToStoryScreen(7);
            } else if (world.getDistrict() == 7){
                AssetLoader.setMedalPart(7);
                world.goToStoryScreen(8);
            } else if (world.getDistrict() == 8){
                AssetLoader.setMedalPart(8);
                world.goToStoryScreen(9);
            } else if (world.getDistrict() == 9){
                AssetLoader.setMedalPart(9);
                world.goToStoryScreen(10);
            } else if (world.getDistrict() == 10){
                AssetLoader.setMedalPart(10);
                world.goToHomeScreen();
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
