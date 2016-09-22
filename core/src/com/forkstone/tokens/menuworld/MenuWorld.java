package com.forkstone.tokens.menuworld;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.utils.Align;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.Background;
import com.forkstone.tokens.gameobjects.GameObject;
import com.forkstone.tokens.gameobjects.MapLights;
import com.forkstone.tokens.gameworld.GameCam;
import com.forkstone.tokens.gameworld.GameState;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.tweens.SpriteAccessor;
import com.forkstone.tokens.tweens.Value;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;

/**
 * Created by sergi on 1/12/15.
 */
public class MenuWorld extends GameWorld {

    public static final String TAG = "MenuWorld";

    // SETTINGS
    private Settings settings;

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public MenuWorld world = this;
    private GameState gameState;
    private int numberOfMapLights = 0;

    //GAME CAMERA
    private GameCam camera;

    private int score;
    private float timePlayed;

    //MENU OBJECTS
    private Background background, topWLayer, drawedBack;
    //Items
    private MenuButton playButton, rateButton, shareButton, settingsButton, shopButton;
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private Text playButtonText, rateButtonText, shareButtonText, settingsButtonText, shopButtonText;
    private ArrayList<Text> buttonsText = new ArrayList<Text>();
    private GameObject titleBanner;
    private ArrayList<MapLights> mapLights = new ArrayList<MapLights>();

    public MenuWorld(ButtonsGame game, final ActionResolver actionResolver, float gameWidth,
                     float gameHeight,
                     float worldWidth, float worldHeight, boolean firstLoad) {

        super(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, 0);
        settings = new Settings();
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.game = game;
        this.actionResolver = actionResolver;

        ButtonsGame.gameState = GameState.HOME;

        //REMOVE ADS IF BUYER
        if (AssetLoader.getAds()) {
            actionResolver.viewAd(false);
        }

        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMap, world.parseColor(Settings.COLOR_WHITE, 1f));
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        topWLayer.fadeOut(.6f, 0f);

        setUpItems();

        // Buttons
        menuButtons.add(playButton);
        menuButtons.add(shopButton);
        menuButtons.add(rateButton);
        menuButtons.add(settingsButton);
        menuButtons.add(shareButton);

        // Buttons Text
        buttonsText.add(playButtonText);
        buttonsText.add(shopButtonText);
        buttonsText.add(rateButtonText);
        buttonsText.add(settingsButtonText);
        buttonsText.add(shareButtonText);

        // Music
        setMusic();
        if(!firstLoad) startAnimation();
        staticAnimation();

        setMapLights();

        // Tracking
        actionResolver.setTrackerScreenName("MenuScreen");
    }

    public void update(float delta) {
//        manager.update(delta);
        background.update(delta);
        drawedBack.update(delta);

        // Lights
        for (MapLights mapLight: mapLights) {
            mapLight.update(delta);
        }

        // Buttons
        for (int i = 0; i < getMenuButtons().size(); i++) {
            menuButtons.get(i).update(delta);
        }

        // Title
        titleBanner.update(delta);

        // Texts
        for (int i = 0; i < buttonsText.size(); i++) {
            buttonsText.get(i).update(delta);
        }

        topWLayer.update(delta);
    }


    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
//        camera.render(batch, shapeRenderer);
        background.render(batch, shapeRenderer, fontShader, objectShader);
        drawedBack.render(batch, shapeRenderer, fontShader, objectShader);

        // Lights
        for (MapLights mapLight: mapLights) {
            mapLight.render(batch, shapeRenderer, fontShader);
        }

        // Buttons
        for (int i = 0; i < getMenuButtons().size(); i++) {
            menuButtons.get(i).render(batch, shapeRenderer, fontShader, objectShader);
        }

        // Title
        titleBanner.render(batch, shapeRenderer, fontShader, objectShader);

        // Texts
        for (int i = 0; i < buttonsText.size(); i++){
            buttonsText.get(i).render(batch, shapeRenderer, fontShader, objectShader);
        }

        topWLayer.render(batch, shapeRenderer, fontShader, objectShader);
    }

    // Music
    public void setMusic() {
        if (AssetLoader.getMusicState()) {
//            AssetLoader.musicJungle.stop();
//            AssetLoader.musicField.stop();
//            AssetLoader.musicDesert.stop();
//            AssetLoader.musicMountain.stop();
            AssetLoader.musicSoundtrack.setLooping(true);
            AssetLoader.musicSoundtrack.play();
            AssetLoader.musicSoundtrack.setVolume(0.45f);
        }
    }

    public void setMapLights(){
        for (int i = 0; i < numberOfMapLights; i++){
            mapLights.add(new MapLights(this));
        }
    }

    // Start Animation
    public void startAnimation(){
        titleBanner.effectY(gameHeight + 400, titleBanner.getPosition().y, .6f, .2f);

        for (int i = 0; i < menuButtons.size(); i++){
            menuButtons.get(i).effectY(-200, menuButtons.get(i).getPosition().y, .6f, .1f + (.03f * i));
        }

        for (int i = 0; i < buttonsText.size(); i++){
            buttonsText.get(i).effectY(-200, buttonsText.get(i).getPosition().y, .6f, .1f + (.03f * i));
        }
    }

    public void staticAnimation(){
        Tween.to(titleBanner.getSprite(), SpriteAccessor.SCALE, 1f).target(0.1f)
                .target(.985f, .99f)
                .repeatYoyo(1000, 0f)
                .ease(TweenEquations.easeInOutSine).start(titleBanner.getManager());

        Tween.to(menuButtons.get(0).getIcon(), SpriteAccessor.SCALE, .5f).target(0.1f)
                .target(.98f, .985f)
                .repeatYoyo(1000, 0f)
                .ease(TweenEquations.easeInOutSine).start(menuButtons.get(0).getManager());
    }

    public void finishAnimation(int button){
        titleBanner.effectY(titleBanner.getPosition().y, gameHeight + 400, .5f, .0f);

        for (int i = 0; i < menuButtons.size(); i++){
            if(i == button){
                menuButtons.get(i).fadeOut(.3f, 0f);
                Tween.to(menuButtons.get(i).getIcon(), SpriteAccessor.ALPHA, .3f).target(0)
                        .ease(TweenEquations.easeInOutSine).start(menuButtons.get(i).getManager());
                Tween.to(menuButtons.get(i).getBackSprite(), SpriteAccessor.ALPHA, .3f).target(0)
                        .ease(TweenEquations.easeInOutSine).start(menuButtons.get(i).getManager());
            } else {
                if(i == 0){
                    menuButtons.get(i).effectY(menuButtons.get(i).getPosition().y, gameHeight + 400, .5f, (.03f * i));
                } else {
                    menuButtons.get(i).effectY(menuButtons.get(i).getPosition().y, -200, .5f, (.03f * i));
                }
            }
        }

        for (int i = 0; i < buttonsText.size(); i++){
            if(i == 0 && button != 0){
                buttonsText.get(i).effectY(buttonsText.get(i).getPosition().y, gameHeight + 200, .5f, (.03f * i));
            } else {
                buttonsText.get(i).effectY(buttonsText.get(i).getPosition().y, -200, .5f, (.03f * i));
            }
        }
    }

    public void setUpItems(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            setUpSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)){
            setUpTablet1610();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)){
            setUpSmallSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            setUpTablet43();
        }
    }

    // ------------ SMARTPHONE ------------
    public void setUpSmartphone(){
        titleBanner = new GameObject(this, gameWidth/2 - 410, gameHeight*2/3 + 60,
                810, 350, AssetLoader.titleBanner, parseColor(Settings.COLOR_WHITE, 1f));

        playButton = new MenuButton(this, gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2),
                gameHeight / 2 - (Settings.PLAY_BUTTON_SIZE / 2) - 30,
                Settings.PLAY_BUTTON_SIZE,
                Settings.PLAY_BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_PLAY_BUTTON, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        playButtonText = new Text(this, gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2),
                gameHeight / 2 - (Settings.PLAY_BUTTON_SIZE / 2) - 60,
                Settings.PLAY_BUTTON_SIZE, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_PLAYBUTTON,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        shopButton = new MenuButton(this, gameWidth / 2 - 75 - (Settings.BUTTON_SIZE * 2) - 20,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 20,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_SHOP_BUTTON, 1f), AssetLoader.shopButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        shopButtonText = new Text(this, gameWidth / 2 - 75 - (Settings.BUTTON_SIZE * 2) - 50 - 20,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 35,
                Settings.BUTTON_SIZE + 100, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_SHOPBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        rateButton = new MenuButton(this, gameWidth / 2 - Settings.BUTTON_SIZE - 25 - 8,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 20, Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RATE_BUTTON, 1f),
                AssetLoader.rateButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        rateButtonText = new Text(this, gameWidth / 2 - Settings.BUTTON_SIZE - 25 - 50 - 8,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 35,
                Settings.BUTTON_SIZE + 100, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_RATEBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        settingsButton = new MenuButton(this, gameWidth / 2 + 25 + 8,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 20, Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_ACHIEVEMENTS_BUTTON, 1f), AssetLoader.settingsButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        settingsButtonText = new Text(this, gameWidth / 2 + 25 - 50 + 8,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 35,
                Settings.BUTTON_SIZE + 100, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_SETTINGSBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        shareButton = new MenuButton(this, gameWidth / 2 + 75 + Settings.BUTTON_SIZE + 20,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 20,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_SHARE_BUTTON, 1f),
                AssetLoader.shareButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        shareButtonText = new Text(this, gameWidth / 2 + 75 + Settings.BUTTON_SIZE - 65 + 20,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 35,
                Settings.BUTTON_SIZE + 130, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_SHAREBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    // ------------ TABLET 16/10 ------------
    public void setUpTablet1610(){
        titleBanner = new GameObject(this, gameWidth/2 - 410, gameHeight*2/3 + 60,
                810, 350, AssetLoader.titleBanner, parseColor(Settings.COLOR_WHITE, 1f));

        playButton = new MenuButton(this, gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2),
                gameHeight / 2 - (Settings.PLAY_BUTTON_SIZE / 2) - 30,
                Settings.PLAY_BUTTON_SIZE,
                Settings.PLAY_BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_PLAY_BUTTON, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        playButtonText = new Text(this, gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2),
                gameHeight / 2 - (Settings.PLAY_BUTTON_SIZE / 2) - 60,
                Settings.PLAY_BUTTON_SIZE, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_PLAYBUTTON,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        shopButton = new MenuButton(this, gameWidth / 2 - 75 - (Settings.BUTTON_SIZE * 2) - 20,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 20,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_SHOP_BUTTON, 1f), AssetLoader.shopButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        shopButtonText = new Text(this, gameWidth / 2 - 75 - (Settings.BUTTON_SIZE * 2) - 50 - 20,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 35,
                Settings.BUTTON_SIZE + 100, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_SHOPBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        rateButton = new MenuButton(this, gameWidth / 2 - Settings.BUTTON_SIZE - 25 - 8,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 20, Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RATE_BUTTON, 1f),
                AssetLoader.rateButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        rateButtonText = new Text(this, gameWidth / 2 - Settings.BUTTON_SIZE - 25 - 50 - 8,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 35,
                Settings.BUTTON_SIZE + 100, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_RATEBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        settingsButton = new MenuButton(this, gameWidth / 2 + 25 + 8,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 20, Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_ACHIEVEMENTS_BUTTON, 1f), AssetLoader.settingsButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        settingsButtonText = new Text(this, gameWidth / 2 + 25 - 50 + 8,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 35,
                Settings.BUTTON_SIZE + 100, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_SETTINGSBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        shareButton = new MenuButton(this, gameWidth / 2 + 75 + Settings.BUTTON_SIZE + 20,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 20,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_SHARE_BUTTON, 1f),
                AssetLoader.shareButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        shareButtonText = new Text(this, gameWidth / 2 + 75 + Settings.BUTTON_SIZE - 65 + 20,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 35,
                Settings.BUTTON_SIZE + 130, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_SHAREBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    // ------------ SMALL SMARTPHONE ------------
    public void setUpSmallSmartphone(){
        titleBanner = new GameObject(this, gameWidth/2 - 410, gameHeight*2/3 + 60,
                810, 350, AssetLoader.titleBanner, parseColor(Settings.COLOR_WHITE, 1f));

        playButton = new MenuButton(this, gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2),
                gameHeight / 2 - (Settings.PLAY_BUTTON_SIZE / 2) - 30,
                Settings.PLAY_BUTTON_SIZE,
                Settings.PLAY_BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_PLAY_BUTTON, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        playButtonText = new Text(this, gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2),
                gameHeight / 2 - (Settings.PLAY_BUTTON_SIZE / 2) - 60,
                Settings.PLAY_BUTTON_SIZE, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_PLAYBUTTON,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        shopButton = new MenuButton(this, gameWidth / 2 - 75 - (Settings.BUTTON_SIZE * 2) - 20,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 20,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_SHOP_BUTTON, 1f), AssetLoader.shopButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        shopButtonText = new Text(this, gameWidth / 2 - 75 - (Settings.BUTTON_SIZE * 2) - 50 - 20,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 35,
                Settings.BUTTON_SIZE + 100, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_SHOPBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        rateButton = new MenuButton(this, gameWidth / 2 - Settings.BUTTON_SIZE - 25 - 8,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 20, Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RATE_BUTTON, 1f),
                AssetLoader.rateButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        rateButtonText = new Text(this, gameWidth / 2 - Settings.BUTTON_SIZE - 25 - 50 - 8,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 35,
                Settings.BUTTON_SIZE + 100, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_RATEBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        settingsButton = new MenuButton(this, gameWidth / 2 + 25 + 8,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 20, Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_ACHIEVEMENTS_BUTTON, 1f), AssetLoader.settingsButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        settingsButtonText = new Text(this, gameWidth / 2 + 25 - 50 + 8,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 35,
                Settings.BUTTON_SIZE + 100, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_SETTINGSBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        shareButton = new MenuButton(this, gameWidth / 2 + 75 + Settings.BUTTON_SIZE + 20,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 20,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_SHARE_BUTTON, 1f),
                AssetLoader.shareButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        shareButtonText = new Text(this, gameWidth / 2 + 75 + Settings.BUTTON_SIZE - 65 + 20,
                gameHeight / 2 - 350 - Settings.BUTTON_SIZE - 35,
                Settings.BUTTON_SIZE + 130, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_SHAREBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    // ------------ TABLET 4/3 ------------
    public void setUpTablet43(){
        titleBanner = new GameObject(this, gameWidth/2 - 308, gameHeight*2/3 + 50,
                608, 262, AssetLoader.titleBanner, parseColor(Settings.COLOR_WHITE, 1f));

        playButton = new MenuButton(this, gameWidth / 2 - (Settings.NORMALBIG_BUTTON_SIZE_TABLET43 / 2),
                gameHeight / 2 - (Settings.NORMALBIG_BUTTON_SIZE_TABLET43 / 2) - 10,
                Settings.NORMALBIG_BUTTON_SIZE_TABLET43,
                Settings.NORMALBIG_BUTTON_SIZE_TABLET43 + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_PLAY_BUTTON, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        playButtonText = new Text(this, gameWidth / 2 - (Settings.NORMALBIG_BUTTON_SIZE_TABLET43 / 2),
                gameHeight / 2 - (Settings.NORMALBIG_BUTTON_SIZE_TABLET43 / 2) - 40,
                Settings.NORMALBIG_BUTTON_SIZE_TABLET43, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_PLAYBUTTON,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        shopButton = new MenuButton(this, gameWidth / 2 - 75 - (Settings.SMALL_BUTTON_SIZE_TABLET43 * 2) - 20,
                gameHeight / 2 - 350 - Settings.SMALL_BUTTON_SIZE_TABLET43 + 40,
                Settings.SMALL_BUTTON_SIZE_TABLET43,
                Settings.SMALL_BUTTON_SIZE_TABLET43 + 5, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_SHOP_BUTTON, 1f), AssetLoader.shopButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        shopButtonText = new Text(this, gameWidth / 2 - 75 - (Settings.SMALL_BUTTON_SIZE_TABLET43 * 2) - 50 - 20,
                gameHeight / 2 - 350 - Settings.SMALL_BUTTON_SIZE_TABLET43 - 15 + 40,
                Settings.SMALL_BUTTON_SIZE_TABLET43 + 100, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_SHOPBUTTON,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        rateButton = new MenuButton(this, gameWidth / 2 - Settings.SMALL_BUTTON_SIZE_TABLET43 - 25 - 8,
                gameHeight / 2 - 350 - Settings.SMALL_BUTTON_SIZE_TABLET43 + 40,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RATE_BUTTON, 1f),
                AssetLoader.rateButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        rateButtonText = new Text(this, gameWidth / 2 - Settings.SMALL_BUTTON_SIZE_TABLET43 - 25 - 50 - 8,
                gameHeight / 2 - 350 - Settings.SMALL_BUTTON_SIZE_TABLET43 - 15 + 40,
                Settings.SMALL_BUTTON_SIZE_TABLET43 + 100, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_RATEBUTTON,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        settingsButton = new MenuButton(this, gameWidth / 2 + 25 + 8,
                gameHeight / 2 - 350 - Settings.SMALL_BUTTON_SIZE_TABLET43 + 40,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_ACHIEVEMENTS_BUTTON, 1f), AssetLoader.settingsButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        settingsButtonText = new Text(this, gameWidth / 2 + 25 - 50,
                gameHeight / 2 - 350 - Settings.SMALL_BUTTON_SIZE_TABLET43 - 15 + 40,
                Settings.SMALL_BUTTON_SIZE_TABLET43 + 110, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_SETTINGSBUTTON,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        shareButton = new MenuButton(this, gameWidth / 2 + 75 + Settings.SMALL_BUTTON_SIZE_TABLET43 + 20,
                gameHeight / 2 - 350 - Settings.SMALL_BUTTON_SIZE_TABLET43 + 40,
                Settings.SMALL_BUTTON_SIZE_TABLET43,
                Settings.SMALL_BUTTON_SIZE_TABLET43 + 5, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_SHARE_BUTTON, 1f),
                AssetLoader.shareButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        shareButtonText = new Text(this, gameWidth / 2 + 75 + Settings.SMALL_BUTTON_SIZE_TABLET43 - 65 + 20,
                gameHeight / 2 - 350 - Settings.SMALL_BUTTON_SIZE_TABLET43 - 15 + 40,
                Settings.SMALL_BUTTON_SIZE_TABLET43 + 130, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.HOME_SHAREBUTTON,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }
    // -----------------------------------------------------------------

    public GameCam getCamera() {
        return camera;
    }

    public ArrayList<MenuButton> getMenuButtons() {
        return menuButtons;
    }

    public void goToLevelScreen() {
        playButton.toLevelScreen(0.6f, 0.1f, 0);
        finishAnimation(0);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToTutorialScreen(String type, int part) {
        playButton.toTutorialScreen(0.6f, 0.1f, type, part);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToTutorial2Screen(String type, int part) {
        playButton.toTutorial2Screen(0.6f, 0.1f, type, part);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToShopScreen() {
        shopButton.toShopScreen(0.6f, 0.1f, 1);
        finishAnimation(1);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToSettingsScreen() {
        settingsButton.toSettingsScreen(0.6f, 0.1f);
        finishAnimation(3);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToStoryScreen(int storyPart) {
        settingsButton.toStoryScreen(0.6f, 0.1f, storyPart);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToMedalScreen(int district) {
        settingsButton.toMedalScreen(0.6f, 0.1f, district);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToCharacterScreen(int part) {
        settingsButton.toCharacterScreen(0.6f, 0.1f, part);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToSubmenuScreen(){
        playButton.toSubmenuScreen(0.6f, 0.1f);
        topWLayer.fadeIn(0.6f, 0.1f);
    }
}
