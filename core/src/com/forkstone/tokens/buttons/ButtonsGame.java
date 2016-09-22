package com.forkstone.tokens.buttons;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.forkstone.tokens.gameworld.GameState;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.screens.SplashScreen;

public class ButtonsGame extends Game {

    private ActionResolver actionresolver;

    private Sprite sprite;
    public static GameState gameState;
    public AssetManager manager = new AssetManager();

    public ButtonsGame(ActionResolver actionresolver) {
        this.actionresolver = actionresolver;
    }

    @Override
    public void create() {
        AssetLoader.load1(this);
        setScreen(new SplashScreen(this, actionresolver));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
