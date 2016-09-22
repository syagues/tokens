package com.forkstone.tokens.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.tweens.SpriteAccessor;
import com.forkstone.tokens.tweens.Value;
import com.forkstone.tokens.tweens.ValueAccessor;
import com.immersion.content.Log;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by sergi on 1/12/15.
 */
public class SplashScreen implements Screen {

    private static final String TAG = "SplashScreen";

    float height, scale, loadingSpriteWidth, loadingSpriteHeight;
    private TweenManager manager;
    private SpriteBatch batcher;
    private Sprite sprite, presentsSprite, loadingSprite, loadingSpriteBack;
    private ButtonsGame game;
    private ActionResolver actionResolver;
    private Sprite spriteBack;
    private TweenCallback cb;
    private boolean ready = false;
    private boolean isDeviceSmartphone = false, isDeviceTablet169 = false, isDeviceSmallSmartphone = false, isDeviceTablet43 = false;

    public SplashScreen(final ButtonsGame game, final ActionResolver actionResolver) {
        this.game = game;
        this.actionResolver = actionResolver;
        spriteBack = new Sprite(AssetLoader.square);
        spriteBack.setColor(GameWorld.parseColor(Settings.COLOR_BLACK, 1f));
        spriteBack.setPosition(0, 0);
        spriteBack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite = new Sprite(AssetLoader.logo);
        loadingSprite = new Sprite(AssetLoader.loadingBar);
        loadingSpriteBack = new Sprite(AssetLoader.loadingBar);
    }

    @Override
    public void show() {
        AssetLoader.loadAssets(game);
        setDevice();

        setupTween();
        batcher = new SpriteBatch();

        // Ads
        if(AssetLoader.getAds()){
            actionResolver.viewAd(false);
        } else {
            actionResolver.viewAd(true);
        }
    }

    private void setDevice(){

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float screenSizeRelation = screenWidth / screenHeight;

        Gdx.app.log(TAG, "Width: " + screenWidth);
        Gdx.app.log(TAG, "Height: " + screenHeight);
        Gdx.app.log(TAG, "ScreenRelation: " + screenSizeRelation);

        if(screenSizeRelation <= 0.6f){
            Gdx.app.log(TAG, Settings.DEVICE_SMARTPHONE);
            isDeviceSmartphone = true;
            AssetLoader.setDevice(Settings.DEVICE_SMARTPHONE);
            setSmartphoneScreen();
            AssetLoader.loadNoTabletAssets(game);
        } else if(screenSizeRelation > 0.6f && screenSizeRelation < 0.7f){
            if(screenSizeRelation < 0.65f){
                Gdx.app.log(TAG, Settings.DEVICE_TABLET_1610);
                isDeviceTablet169 = true;
                AssetLoader.setDevice(Settings.DEVICE_TABLET_1610);
                setTablet169();
                AssetLoader.loadNoTabletAssets(game);
            } else {
                Gdx.app.log(TAG, Settings.DEVICE_SMALLSMARTPHONE);
                AssetLoader.setAds(true);
                actionResolver.viewAd(false);
                isDeviceSmallSmartphone = true;
                AssetLoader.setDevice(Settings.DEVICE_SMALLSMARTPHONE);
                setSmallSmartphone();
                AssetLoader.loadNoTabletAssets(game);
            }
        } else {
            Gdx.app.log(TAG, Settings.DEVICE_TABLET_43);
            isDeviceTablet43 = true;
            AssetLoader.setDevice(Settings.DEVICE_TABLET_43);
            setTablet43();
            AssetLoader.loadTabletAssets(game);
        }
    }

    private void setSmartphoneScreen(){
        float width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        float desiredWidth = width * .5f;
        scale = desiredWidth / sprite.getWidth();

        // Logo
        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        sprite.setPosition((width / 2) - (sprite.getWidth() / 2), (height * 4f / 7) - (sprite.getHeight() / 2));

        // Loading Bar
        loadingSpriteWidth = loadingSprite.getWidth();
        loadingSpriteHeight = loadingSprite.getHeight();
        loadingSprite.setSize(loadingSprite.getWidth() * (scale - .1f), loadingSprite.getHeight() * (scale - .1f));
        loadingSprite.setPosition((width / 2) - (loadingSprite.getWidth() / 2), (height * 1.6f / 5) - (loadingSprite.getHeight() / 2));
//        loadingSprite.setColor(0.8039f, 0.8627f, 0.2235f, 1f); // Lime
        loadingSprite.setColor(0f, 0.9019f, 0.4627f, 1f); // VerdClar
//        loadingSprite.setColor(0.1294f, 0.5882f, 0.9529f, 1f); // Blau

        // Loading Bar Back
        loadingSpriteBack.setSize(loadingSpriteBack.getWidth() * (scale - .091f), loadingSpriteBack.getHeight() * (scale + .4f));
        loadingSpriteBack.setPosition((width / 2) - (loadingSpriteBack.getWidth() / 2), (height * 1.6f / 5) - (loadingSpriteBack.getHeight() / 2));
//        loadingSpriteBack.setColor(0f, 0f, 0f, .5f);
        loadingSpriteBack.setColor(0f, 0f, 0f, .5f);
    }

    private void setTablet169(){
        float width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        float desiredWidth = width * .5f;
        scale = desiredWidth / sprite.getWidth();

        // Logo
        sprite.setSize(sprite.getWidth() * (scale - .1f), sprite.getHeight() * (scale - .1f));
        sprite.setPosition((width / 2) - (sprite.getWidth() / 2), (height * 4f / 7) - (sprite.getHeight() / 2));

        // Loading Bar
        loadingSpriteWidth = loadingSprite.getWidth();
        loadingSpriteHeight = loadingSprite.getHeight();
        loadingSprite.setSize(loadingSprite.getWidth() * (scale - .2f), loadingSprite.getHeight() * (scale - .2f));
        loadingSprite.setPosition((width / 2) - (loadingSprite.getWidth() / 2), (height * 1.85f / 5) - (loadingSprite.getHeight() / 2));
        loadingSprite.setColor(0f, 0.9019f, 0.4627f, 1f); // VerdClar

        // Loading Bar Back
        loadingSpriteBack.setSize(loadingSpriteBack.getWidth() * (scale - .186f), loadingSpriteBack.getHeight() * (scale + .4f));
        loadingSpriteBack.setPosition((width / 2) - (loadingSpriteBack.getWidth() / 2), (height * 1.85f / 5) - (loadingSpriteBack.getHeight() / 2));
        loadingSpriteBack.setColor(0f, 0f, 0f, .5f);
    }

    private void setSmallSmartphone(){
        float width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        float desiredWidth = width * .5f;
        scale = desiredWidth / sprite.getWidth();

        // Logo
        sprite.setSize(sprite.getWidth() * (scale), sprite.getHeight() * (scale));
        sprite.setPosition((width / 2) - (sprite.getWidth() / 2), (height * 4f / 7) - (sprite.getHeight() / 2));

        // Loading Bar
        loadingSpriteWidth = loadingSprite.getWidth();
        loadingSpriteHeight = loadingSprite.getHeight();
        loadingSprite.setSize(loadingSprite.getWidth() * (scale - .2f), loadingSprite.getHeight() * (scale - .2f));
        loadingSprite.setPosition((width / 2) - (loadingSprite.getWidth() / 2), (height * 1.6f / 5) - (loadingSprite.getHeight() / 2));
        loadingSprite.setColor(0f, 0.9019f, 0.4627f, 1f); // VerdClar

        // Loading Bar Back
        loadingSpriteBack.setSize(loadingSpriteBack.getWidth() * (scale - .186f), loadingSpriteBack.getHeight() * (scale + .4f));
        loadingSpriteBack.setPosition((width / 2) - (loadingSpriteBack.getWidth() / 2), (height * 1.85f / 5) - (loadingSpriteBack.getHeight() / 2));
        loadingSpriteBack.setColor(0f, 0f, 0f, .5f);
    }

    private void setTablet43(){
        float width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        float desiredWidth = width * .5f;
        scale = desiredWidth / sprite.getWidth();

        // Logo
        sprite.setSize(sprite.getWidth() * (scale - .35f), sprite.getHeight() * (scale - .35f));
        sprite.setPosition((width / 2) - (sprite.getWidth() / 2), (height * 4f / 7) - (sprite.getHeight() / 2));

        // Loading Bar
        loadingSpriteWidth = loadingSprite.getWidth();
        loadingSpriteHeight = loadingSprite.getHeight();
        loadingSprite.setSize(loadingSprite.getWidth() * (scale - .47f), loadingSprite.getHeight() * (scale - .48f));
        loadingSprite.setPosition((width / 2) - (loadingSprite.getWidth() / 2), (height * 1.85f / 5) - (loadingSprite.getHeight() / 2));
        loadingSprite.setColor(0f, 0.9019f, 0.4627f, 1f); // VerdClar

        // Loading Bar Back
        loadingSpriteBack.setSize(loadingSpriteBack.getWidth() * (scale - .455f), loadingSpriteBack.getHeight() * (scale + .2f));
        loadingSpriteBack.setPosition((width / 2) - (loadingSpriteBack.getWidth() / 2), (height * 1.85f / 5) - (loadingSpriteBack.getHeight() / 2));
        loadingSpriteBack.setColor(0f, 0f, 0f, .5f);
    }

    private void setupTween() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        if(game.manager.update() && !ready){
            ready = true;
            AssetLoader.setAssets(game, actionResolver);
        }

        if(isDeviceSmartphone){
            loadingSprite.setSize((loadingSpriteWidth * (scale - .1f)) * game.manager.getProgress(), loadingSpriteHeight * (scale - .1f));
        } else if(isDeviceTablet169){
            loadingSprite.setSize((loadingSpriteWidth * (scale - .2f)) * game.manager.getProgress(), loadingSpriteHeight * (scale - .2f));
        } else if(isDeviceSmallSmartphone){
            loadingSprite.setSize((loadingSpriteWidth * (scale - .2f)) * game.manager.getProgress(), loadingSpriteHeight * (scale - .2f));
        } else if(isDeviceTablet43){
            loadingSprite.setSize((loadingSpriteWidth * (scale - .47f)) * game.manager.getProgress(), loadingSpriteHeight * (scale - .48f));
        }

        Gdx.gl.glClearColor(92.5f, 94.1f, 94.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();
        spriteBack.draw(batcher);
        sprite.draw(batcher);
        loadingSpriteBack.draw(batcher);
        loadingSprite.draw(batcher);
        batcher.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        AssetLoader.dispose();
    }
}
