package com.forkstone.tokens.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.menuworld.InputHandlerMenu;
import com.forkstone.tokens.menuworld.MenuRenderer;
import com.forkstone.tokens.menuworld.MenuWorld;
import com.forkstone.tokens.tweens.SpriteAccessor;
import com.forkstone.tokens.tweens.Value;
import com.forkstone.tokens.tweens.ValueAccessor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;


/**
 * Created by sergi on 1/12/15.
 */
public class MenuScreen implements Screen {

    private MenuWorld world;
    private MenuRenderer renderer;
    private float runTime;
    public float sW = Gdx.graphics.getWidth();
    public float sH = Gdx.graphics.getHeight();
    public float gameWidth = 1080;
    public float gameHeight = sH / (sW / gameWidth);
    public float w = 1080 / 100;
    public float worldWidth = gameWidth * 1;
    public float worldHeight = gameHeight * 1;
    private ButtonsGame game;
    // -----------------------------------------------
    private SpriteBatch batcher;

    public MenuScreen(ButtonsGame game, ActionResolver actionResolver, boolean firstLoad) {
        this.game = game;
        Gdx.app.log("MenuScreen", "Attached");
        Gdx.app.log("GameWidth " + gameWidth, "GameHeight " + gameHeight);
        world = new MenuWorld(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, firstLoad);
        Gdx.input.setInputProcessor(new InputHandlerMenu(world, sW / gameWidth, sH / gameHeight));
        renderer = new MenuRenderer(world, gameWidth, gameHeight);
    }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen", "show called");
        batcher = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        game.manager.update();
        world.update(delta);

        renderer.render(delta, runTime);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resize");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose() {
        AssetLoader.dispose();
    }
}
