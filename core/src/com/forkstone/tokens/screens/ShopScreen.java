package com.forkstone.tokens.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.shopworld.InputHandlerShop;
import com.forkstone.tokens.shopworld.ShopRenderer;
import com.forkstone.tokens.shopworld.ShopWorld;

/**
 * Created by sergi on 10/1/16.
 */
public class ShopScreen implements Screen {

    public static final String TAG = "ShopScreen";

    private ShopWorld world;
    private ShopRenderer renderer;
    private float runTime;
    public float sW = Gdx.graphics.getWidth();
    public float sH = Gdx.graphics.getHeight();
    public float gameWidth = 1080;
    public float gameHeight = sH / (sW / gameWidth);
    public float w = 1080 / 100;
    public float worldWidth = gameWidth * 1;
    public float worldHeight = gameHeight * 1;

    public ShopScreen(ButtonsGame game, ActionResolver actionResolver, int backScreen) {

        Gdx.app.log("GameWidth " + gameWidth, "GameHeight " + gameHeight);
        world = new ShopWorld(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, backScreen);
        Gdx.input.setInputProcessor(new InputHandlerShop(world, sW / gameWidth, sH / gameHeight));
        renderer = new ShopRenderer(world, gameWidth, gameHeight);
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(delta, runTime);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize called");
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "resume called");
    }

    @Override
    public void dispose() {
        AssetLoader.dispose();
    }
}
