package com.forkstone.tokens.shopworld;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.PriceButton;


import java.util.ArrayList;

/**
 * Created by sergi on 10/1/16.
 */
public class InputHandlerShop implements InputProcessor {

    private static final String TAG = "InputHandlerShop";
    private final ShopWorld world;
    private final float scaleFactorX, scaleFactorY;
    private final Rectangle rectangle;
    private ArrayList<MenuButton> menuButtons;
    private ArrayList<PriceButton> priceButtons;
    private float initialPointY;

    public InputHandlerShop(ShopWorld world, float scaleFactorX, float scaleFactorY) {

        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        rectangle = new Rectangle(0, 0, 200, 200);
        menuButtons = world.getMenuButtons();
        priceButtons = world.getPriceButtons();
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

        for (int i = 0; i < priceButtons.size(); i++) {
            if (priceButtons.get(i).isTouchDown(screenX, screenY)) {
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
            if (world.getBackScreen() == 1){
                world.goToHomeScreen();
            } else if(world.getBackScreen() == 2){
                world.goToLevelScreen();
            }
        }

        if(priceButtons.get(0).isTouchUp(screenX, screenY)){
            world.actionResolver.iapMoreMovesClick();
//            AssetLoader.addMoreMoves(10);
        } else if(priceButtons.get(1).isTouchUp(screenX, screenY)){
            world.actionResolver.iapMoreTimesClick();
//            AssetLoader.addMoreTime(10);
        } else if(priceButtons.get(2).isTouchUp(screenX, screenY)){
            world.actionResolver.iapMulticolorClick();
//            AssetLoader.addMulticolor(10);
        } else if(priceButtons.get(3).isTouchUp(screenX, screenY)){
            world.actionResolver.iapSolutionClick();
//            AssetLoader.addResol(3);
        } else if(priceButtons.get(4).isTouchUp(screenX, screenY)){
            // Random token
            if(world.isVideoAdActive()){
                world.actionResolver.viewVideoAd();
//                world.goToRandomScreen();
            }
        } else if(!AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)){
            if(priceButtons.get(5).isTouchUp(screenX, screenY)){
                // No Ads
                world.actionResolver.iapNoAdsClick();
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
