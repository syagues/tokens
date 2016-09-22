package com.forkstone.tokens.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.tweens.Value;
import com.forkstone.tokens.ui.LifesBanner;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by sergi on 5/12/15.
 */
public class GameOver extends GameObject {

    private static final String TAG = "GameOver";
    private GameObject bigBannerPass, bigBannerFail, bannerRoulette, bannerRate;
    private MenuButton homeButton, replayButton, bannerPassButton, bannerFailButton, plusButton, rateButton;
    private static MenuButton rouletteButton;
    private Counter moreCounter, lightningCounter;
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private Text continueTextPass, continueTextFail, rouletteText, rateText;
    private boolean gameComplete, rouletteActive = false, showRate = false;
    private TweenCallback stateToActive;
    private Value time = new Value();
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private LifesBanner lifesBanner;

    private int numOfCircles = 0;

    public enum GameOverState {
        INACTIVE, ACTIVE
    }
    private GameOverState gameOverState;

    public GameOver(GameWorld world, float x, float y, float width, float height, TextureRegion texture, Color color) {
        super(world, x, y, width, height, texture, color);

        //OBJECTS
        setUpItems();

        //objects.add(xButton);
        objects.add(bigBannerPass);
        objects.add(bigBannerFail);
        objects.add(bannerPassButton);
        objects.add(bannerFailButton);
        objects.add(replayButton);
        objects.add(homeButton);
        objects.add(moreCounter);
        objects.add(lightningCounter);
        objects.add(plusButton);
        //objects.add(coin);

        // State
        gameOverState = GameOverState.INACTIVE;
        stateToActive = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                gameOverState = GameOverState.ACTIVE;
            }
        };

        // MenuButtons
        menuButtons.add(bannerPassButton);
        menuButtons.add(bannerFailButton);
        menuButtons.add(homeButton);
        menuButtons.add(replayButton);
        menuButtons.add(plusButton);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        // Banner
        bigBannerPass.update(delta);
        bigBannerFail.update(delta);
        continueTextPass.setPosition(continueTextPass.getPosition().x,
                bigBannerPass.getPosition().y + bigBannerPass.getSprite().getHeight() / 2 - 30);
        continueTextFail.setPosition(continueTextFail.getPosition().x,
                bigBannerFail.getPosition().y + bigBannerFail.getSprite().getHeight() / 2 - 30);

        continueTextPass.update(delta);
        continueTextFail.update(delta);
        bannerPassButton.update(delta);
        bannerFailButton.update(delta);

        // Roulette
        bannerRoulette.update(delta);
        rouletteText.update(delta);
        rouletteButton.update(delta);

        if(world.isVideoAdActive()){
            rouletteButton.setColor(world.parseColor(Settings.COLOR_GREEN_500, 1f));
            rouletteButton.setIconColor(world.parseColor(Settings.COLOR_WHITE, 1f));
        } else {
            rouletteButton.setColor(world.parseColor(Settings.COLOR_GREEN_300, 1f));
            rouletteButton.setIconColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f));
        }

        // Rate
        bannerRate.update(delta);
        rateText.update(delta);
        rateButton.update(delta);

        // Buttons
        homeButton.update(delta);
        replayButton.update(delta);
        plusButton.update(delta);
        moreCounter.update(delta);
        lightningCounter.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        if(gameOverState == GameOverState.ACTIVE) {
            // Buttons
            homeButton.render(batch, shapeRenderer,fontShader, objectShader);
            replayButton.render(batch, shapeRenderer,fontShader, objectShader);

            // Banner
            if(gameComplete) {
                objects.get(0).render(batch, shapeRenderer,fontShader, objectShader);
                continueTextPass.render(batch, shapeRenderer, fontShader, objectShader);
                bannerPassButton.render(batch, shapeRenderer,fontShader, objectShader);
                if(showRate){
                    bannerRate.render(batch, shapeRenderer, fontShader, objectShader);
                    rateText.render(batch, shapeRenderer, fontShader, objectShader);
                    rateButton.render(batch, shapeRenderer, fontShader, objectShader);
                }
            } else {
                objects.get(1).render(batch, shapeRenderer,fontShader, objectShader);
                continueTextFail.render(batch, shapeRenderer, fontShader, objectShader);
                bannerFailButton.render(batch, shapeRenderer,fontShader, objectShader);
                plusButton.render(batch, shapeRenderer, fontShader, objectShader);
                moreCounter.render(batch, shapeRenderer, fontShader, objectShader);
                lightningCounter.render(batch, shapeRenderer, fontShader, objectShader);
                // Roulette
                if(rouletteActive){
                    bannerRoulette.render(batch, shapeRenderer, fontShader, objectShader);
                    rouletteText.render(batch, shapeRenderer, fontShader, objectShader);
                    rouletteButton.render(batch, shapeRenderer, fontShader, objectShader);
                }
            }
        }
    }

    public void start(boolean gameComplete) {
        this.gameComplete = gameComplete;
        if(gameComplete) {
            replayButton.setIcon(AssetLoader.replayButtonUp);
            replayButton.setIconColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            // Unblock next level
            if(world.getLevel() == AssetLoader.getLevel()) AssetLoader.addLevel();
            if(AssetLoader.getLevel() > 10 && !AssetLoader.getIsRated()) {
                if(MathUtils.random(0, 100) > 50) showRate = true;
            }
            // Tracking
            world.actionResolver.setTrackerScreenName("GameOverScreen: Wins");
        } else {
            replayButton.setIcon(AssetLoader.lightningButtonUp);
            replayButton.setIconColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            // Plus Button
            if(world.isTimeGame()) plusButton.setIcon(AssetLoader.plusTenButtonUp);
            else plusButton.setIcon(AssetLoader.plusThreeButtonUp);
            plusButton.setIconColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            if(AssetLoader.getAttempt() >= 2) rouletteActive = true;
            // Tracking
            world.actionResolver.setTrackerScreenName("GameOverScreen: Loses");
        }

        setGameObjectsPositions();
        for (int i = 0; i < objects.size() - numOfCircles - 1; i++) {
            objects.get(i).effectY(objects.get(i).getPosition().y - world.gameHeight,
                    objects.get(i).getPosition().y, 0.7f, 0.03f * i);
        }
        // Animacio
        plusButton.effectY(plusButton.getPosition().y - world.gameHeight, plusButton.getPosition().y, 0.7f, 0.03f * 3);
        for (int i = objects.size() - numOfCircles; i < objects.size(); i++) {
            objects.get(i).effectY(objects.get(i).getPosition().y - world.gameHeight,
                    objects.get(i).getPosition().y, 0.7f, 0.12f);
        }
        setStateToActive();

        // Roulette
        bannerRoulette.effectY(world.gameHeight + 600, bannerRoulette.getPosition().y, 0.7f, 0.4f);
        rouletteText.effectY(world.gameHeight + 600, rouletteText.getPosition().y, 0.7f, 0.43f);
        rouletteButton.effectY(world.gameHeight + 600, rouletteButton.getPosition().y, 0.7f, 0.46f);

        // Rate
        bannerRate.effectY(world.gameHeight + 600, bannerRate.getPosition().y, 0.7f, 0.4f);
        rateText.effectY(world.gameHeight + 600, rateText.getPosition().y, 0.7f, 0.43f);
        rateButton.effectY(world.gameHeight + 600, rateButton.getPosition().y, 0.7f, 0.46f);
    }

    public void finish() {
        for (int i = 0; i < objects.size() - numOfCircles - 1; i++) {
            objects.get(i).effectY(objects.get(i).getPosition().y,
                    objects.get(i).getPosition().y + world.gameHeight, 0.7f, 0.03f * i);
        }
        // Animacio
        plusButton.effectY(plusButton.getPosition().y, plusButton.getPosition().y + world.gameHeight, 0.7f, 0.03f * 3);
        for (int i = objects.size() - numOfCircles; i < objects.size(); i++) {
            objects.get(i).effectY(objects.get(i).getPosition().y,
                    objects.get(i).getPosition().y + world.gameHeight, 0.7f, 0.12f);
        }
        world.fadeInLayer();
        //setStateToInactive();

        // Roulette
        bannerRoulette.effectY(bannerRoulette.getPosition().y, world.gameHeight + 600, 0.4f, 0.03f);
        rouletteText.effectY(rouletteText.getPosition().y, world.gameHeight + 600, 0.4f, 0.06f);
        rouletteButton.effectY(rouletteButton.getPosition().y, world.gameHeight + 600, 0.4f, 0.09f);

        // Rate
        bannerRate.effectY(bannerRate.getPosition().y, world.gameHeight + 600, 0.4f, 0.03f);
        rateText.effectY(rateText.getPosition().y, world.gameHeight + 600, 0.4f, 0.06f);
        rateButton.effectY(rateButton.getPosition().y, world.gameHeight + 600, 0.4f, 0.09f);
    }

    public void startAgain() {

    }

    public void setUpItems(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            setBigBannerSmarthone();
            setButtonsSmarthone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)) {
            setBigBannerTablet169();
            setButtonsTablet169();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)) {
            setBigBannerSmallSmartphone();
            setButtonsSmallSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            setBigBannerTablet43();
            setButtonsTablet43();
        }
    }

    public void setGameObjectsPositions(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            setGameObjectsPositionsSmarthone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)) {
            setGameObjectsPositionsTablet169();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)) {
            setGameObjectsPositionsSmallSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            setGameObjectsPositionsTablet43();
        }
    }

    // ----------- SMARTPHONE -------------
    public void setBigBannerSmarthone(){

        bigBannerPass = new GameObject(world,
                world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2,
                world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 40,
                AssetLoader.bigBanner.getRegionWidth(), AssetLoader.bigBanner.getRegionHeight(),
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_GREEN_200, 1f));
        continueTextPass = new Text(world, bigBannerPass.getPosition().x + 50,
                bigBannerPass.getPosition().y + bigBannerPass.getSprite().getHeight() / 2 - 35,
                bigBannerPass.getSprite().getWidth(), 100, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_PASS_TEXT, AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                Align.left);
        bannerPassButton = new MenuButton(world,
                world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 150,
                Settings.GAMEOVER_BUTTON_SIZE, Settings.GAMEOVER_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));
        bigBannerFail = new GameObject(world,
                world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2,
                world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 40,
                AssetLoader.bigBanner.getRegionWidth(), AssetLoader.bigBanner.getRegionHeight(),
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_RED_200, 1f));
        continueTextFail = new Text(world, bigBannerFail.getPosition().x + 50,
                bigBannerFail.getPosition().y + bigBannerFail.getSprite().getHeight() / 2 - 35,
                bigBannerFail.getSprite().getWidth(), 100, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_REPLAY_TEXT, AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                Align.left);
        bannerFailButton = new MenuButton(world,
                world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 150,
                Settings.GAMEOVER_BUTTON_SIZE, Settings.GAMEOVER_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.gOReplayButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        // Roulette
        bannerRoulette = new GameObject(world,
                -50, world.gameHeight - 230,
                world.gameWidth + 100, 400,
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_PURPLE_500, 1f));

        rouletteText = new Text(world,
                30, world.gameHeight - 180,
                world.gameWidth - 260, 180, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_ROULETTE_TEXT, AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 30,
                Align.left);

        rouletteButton = new MenuButton(world,
                world.gameWidth - Settings.LEVEL_BUTTON_SIZE - 100, world.gameHeight - Settings.LEVEL_BUTTON_SIZE - 40,
                Settings.LEVEL_BUTTON_SIZE, Settings.LEVEL_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.videoButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        // Rate
        bannerRate = new GameObject(world,
                -50, world.gameHeight - 230,
                world.gameWidth + 100, 400,
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_PINK_500, 1f));

        rateText = new Text(world,
                30, world.gameHeight - 200,
                world.gameWidth - 260, 180, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_RATE_TEXT, AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 30,
                Align.left);

        rateButton = new MenuButton(world,
                world.gameWidth - Settings.LEVEL_BUTTON_SIZE - 100, world.gameHeight - Settings.LEVEL_BUTTON_SIZE - 40,
                Settings.LEVEL_BUTTON_SIZE, Settings.LEVEL_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUE_500, 1f), AssetLoader.rateButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));

    }

    public void setButtonsSmarthone() {
        homeButton = new MenuButton(world,
                world.gameWidth / 2 - (AssetLoader.replayButton.getRegionWidth() / 2) - 250,
                bigBannerPass.getPosition().y - 170,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        plusButton = new MenuButton(world,
                world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) + 20,
                bigBannerPass.getPosition().y - 170,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.plusThreeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreCounter = new Counter(world, world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) - 30 + Settings.GAME_BUTTON_SIZE,
                bigBannerPass.getPosition().y - 170 + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f), getHelpName(), false,
                world.parseColor(Settings.COLOR_RED_500, 1f), 10f);
        replayButton = new MenuButton(world,
                world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 260,
                bigBannerPass.getPosition().y - 170,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningCounter = new Counter(world, world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 310 + Settings.GAME_BUTTON_SIZE,
                bigBannerPass.getPosition().y - 170 + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f), "resol", false,
                world.parseColor(Settings.COLOR_RED_500, 1f), 10f);
    }

    public void setGameObjectsPositionsSmarthone(){
        bigBannerPass.setPosition(world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2, world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 40);
        continueTextPass.setPosition(bigBannerPass.getPosition().x + 50, bigBannerPass.getPosition().y + bigBannerPass.getSprite().getHeight() / 2 - 35);
        bannerPassButton.setPosition(world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 150);
        bigBannerFail.setPosition(world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2, world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 40);
        continueTextFail.setPosition(bigBannerFail.getPosition().x + 50, bigBannerFail.getPosition().y + bigBannerFail.getSprite().getHeight() / 2 - 35);
        bannerFailButton.setPosition(world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 150);
        homeButton.setPosition(world.gameWidth / 2 - (AssetLoader.replayButton.getRegionWidth() / 2) - 250, bigBannerPass.getPosition().y - 170);
        plusButton.setPosition(world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) + 20, bigBannerPass.getPosition().y - 170);
        moreCounter.setPosition(world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) - 30 + Settings.GAME_BUTTON_SIZE, bigBannerPass.getPosition().y - 170 + Settings.GAME_BUTTON_SIZE - 35);
        replayButton.setPosition(world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 260, bigBannerPass.getPosition().y - 170);
        lightningCounter.setPosition(world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 310 + Settings.GAME_BUTTON_SIZE, bigBannerPass.getPosition().y - 170 + Settings.GAME_BUTTON_SIZE - 35);
        bannerRoulette.setPosition(-50, world.gameHeight - 230);
        rouletteText.setPosition(30, world.gameHeight - 180);
        rouletteButton.setPosition(world.gameWidth - Settings.LEVEL_BUTTON_SIZE - 100, world.gameHeight - Settings.LEVEL_BUTTON_SIZE - 40);
        bannerRate.setPosition(-50, world.gameHeight - 230);
        rateText.setPosition(30, world.gameHeight - 200);
        rateButton.setPosition(world.gameWidth - Settings.LEVEL_BUTTON_SIZE - 100, world.gameHeight - Settings.LEVEL_BUTTON_SIZE - 40);
    }

    // ----------- TABLET 16/9 -------------
    public void setBigBannerTablet169(){

        bigBannerPass = new GameObject(world,
                world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2,
                world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 40 + 30,
                AssetLoader.bigBanner.getRegionWidth(), AssetLoader.bigBanner.getRegionHeight(),
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_GREEN_200, 1f));
        continueTextPass = new Text(world, bigBannerPass.getPosition().x + 50,
                bigBannerPass.getPosition().y + bigBannerPass.getSprite().getHeight() / 2 - 35,
                bigBannerPass.getSprite().getWidth(), 100, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_PASS_TEXT, AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                Align.left);
        bannerPassButton = new MenuButton(world,
                world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 150,
                Settings.GAMEOVER_BUTTON_SIZE, Settings.GAMEOVER_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));
        bigBannerFail = new GameObject(world,
                world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2,
                world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 40 + 30,
                AssetLoader.bigBanner.getRegionWidth(), AssetLoader.bigBanner.getRegionHeight(),
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_RED_200, 1f));
        continueTextFail = new Text(world, bigBannerFail.getPosition().x + 50,
                bigBannerFail.getPosition().y + bigBannerFail.getSprite().getHeight() / 2 - 35,
                bigBannerFail.getSprite().getWidth(), 100, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_REPLAY_TEXT, AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                Align.left);
        bannerFailButton = new MenuButton(world,
                world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 150,
                Settings.GAMEOVER_BUTTON_SIZE, Settings.GAMEOVER_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.gOReplayButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        // Roulette
        bannerRoulette = new GameObject(world,
                -50, world.gameHeight - 230,
                world.gameWidth + 100, 400,
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_PURPLE_500, 1f));

        rouletteText = new Text(world,
                30, world.gameHeight - 180,
                world.gameWidth - 260, 180, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_ROULETTE_TEXT, AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 30,
                Align.left);

        rouletteButton = new MenuButton(world,
                world.gameWidth - Settings.LEVEL_BUTTON_SIZE - 100, world.gameHeight - Settings.LEVEL_BUTTON_SIZE - 40,
                Settings.LEVEL_BUTTON_SIZE, Settings.LEVEL_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.videoButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        // Rate
        bannerRate = new GameObject(world,
                -50, world.gameHeight - 230,
                world.gameWidth + 100, 400,
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_PINK_500, 1f));

        rateText = new Text(world,
                30, world.gameHeight - 200,
                world.gameWidth - 260, 180, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_RATE_TEXT, AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 30,
                Align.left);

        rateButton = new MenuButton(world,
                world.gameWidth - Settings.LEVEL_BUTTON_SIZE - 100, world.gameHeight - Settings.LEVEL_BUTTON_SIZE - 40,
                Settings.LEVEL_BUTTON_SIZE, Settings.LEVEL_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUE_500, 1f), AssetLoader.rateButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));
    }

    public void setButtonsTablet169() {
        homeButton = new MenuButton(world,
                world.gameWidth / 2 - (AssetLoader.replayButton.getRegionWidth() / 2) - 250,
                bigBannerPass.getPosition().y - 170,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        plusButton = new MenuButton(world,
                world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) + 20,
                bigBannerPass.getPosition().y - 170,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.plusThreeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreCounter = new Counter(world, world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) - 30 + Settings.GAME_BUTTON_SIZE,
                bigBannerPass.getPosition().y - 170 + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f), getHelpName(), false,
                world.parseColor(Settings.COLOR_RED_500, 1f), 10f);
        replayButton = new MenuButton(world,
                world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 260,
                bigBannerPass.getPosition().y - 170,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningCounter = new Counter(world, world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 310 + Settings.GAME_BUTTON_SIZE,
                bigBannerPass.getPosition().y - 170 + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f), "resol", false,
                world.parseColor(Settings.COLOR_RED_500, 1f), 10f);
    }

    public void setGameObjectsPositionsTablet169(){
        bigBannerPass.setPosition(world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2, world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 40 + 30);
        continueTextPass.setPosition(bigBannerPass.getPosition().x + 50, bigBannerPass.getPosition().y + bigBannerPass.getSprite().getHeight() / 2 - 35);
        bannerPassButton.setPosition(world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 150);
        bigBannerFail.setPosition(world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2, world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 40 + 30);
        continueTextFail.setPosition(bigBannerFail.getPosition().x + 50, bigBannerFail.getPosition().y + bigBannerFail.getSprite().getHeight() / 2 - 35);
        bannerFailButton.setPosition(world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 150);
        homeButton.setPosition(world.gameWidth / 2 - (AssetLoader.replayButton.getRegionWidth() / 2) - 250, bigBannerPass.getPosition().y - 170);
        plusButton.setPosition(world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) + 20, bigBannerPass.getPosition().y - 170);
        moreCounter.setPosition(world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) - 30 + Settings.GAME_BUTTON_SIZE, bigBannerPass.getPosition().y - 170 + Settings.GAME_BUTTON_SIZE - 35);
        replayButton.setPosition(world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 260, bigBannerPass.getPosition().y - 170);
        lightningCounter.setPosition(world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 310 + Settings.GAME_BUTTON_SIZE, bigBannerPass.getPosition().y - 170 + Settings.GAME_BUTTON_SIZE - 35);
        bannerRoulette.setPosition(-50, world.gameHeight - 230);
        rouletteText.setPosition(30, world.gameHeight - 180);
        rouletteButton.setPosition(world.gameWidth - Settings.LEVEL_BUTTON_SIZE - 100, world.gameHeight - Settings.LEVEL_BUTTON_SIZE - 40);
        bannerRate.setPosition(-50, world.gameHeight - 230);
        rateText.setPosition(30, world.gameHeight - 200);
        rateButton.setPosition(world.gameWidth - Settings.LEVEL_BUTTON_SIZE - 100, world.gameHeight - Settings.LEVEL_BUTTON_SIZE - 40);
    }

    // ----------- SMALL SMARTPHONE -------------
    public void setBigBannerSmallSmartphone(){

        bigBannerPass = new GameObject(world,
                world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2,
                world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 40 + 30,
                AssetLoader.bigBanner.getRegionWidth(), AssetLoader.bigBanner.getRegionHeight(),
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_GREEN_200, 1f));
        continueTextPass = new Text(world, bigBannerPass.getPosition().x + 50,
                bigBannerPass.getPosition().y + bigBannerPass.getSprite().getHeight() / 2 - 35,
                bigBannerPass.getSprite().getWidth(), 100, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_PASS_TEXT, AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                Align.left);
        bannerPassButton = new MenuButton(world,
                world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 150,
                Settings.GAMEOVER_BUTTON_SIZE, Settings.GAMEOVER_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));
        bigBannerFail = new GameObject(world,
                world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2,
                world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 40 + 30,
                AssetLoader.bigBanner.getRegionWidth(), AssetLoader.bigBanner.getRegionHeight(),
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_RED_200, 1f));
        continueTextFail = new Text(world, bigBannerFail.getPosition().x + 50,
                bigBannerFail.getPosition().y + bigBannerFail.getSprite().getHeight() / 2 - 35,
                bigBannerFail.getSprite().getWidth(), 100, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_REPLAY_TEXT, AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                Align.left);
        bannerFailButton = new MenuButton(world,
                world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 150,
                Settings.GAMEOVER_BUTTON_SIZE, Settings.GAMEOVER_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.gOReplayButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        // Roulette
        bannerRoulette = new GameObject(world,
                -50, world.gameHeight - 230,
                world.gameWidth + 100, 400,
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_PURPLE_500, 1f));

        rouletteText = new Text(world,
                30, world.gameHeight - 180,
                world.gameWidth - 260, 180, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_ROULETTE_TEXT, AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 30,
                Align.left);

        rouletteButton = new MenuButton(world,
                world.gameWidth - Settings.LEVEL_BUTTON_SIZE - 100, world.gameHeight - Settings.LEVEL_BUTTON_SIZE - 40,
                Settings.LEVEL_BUTTON_SIZE, Settings.LEVEL_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.videoButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        // Rate
        bannerRate = new GameObject(world,
                -50, world.gameHeight - 230,
                world.gameWidth + 100, 400,
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_PINK_500, 1f));

        rateText = new Text(world,
                30, world.gameHeight - 200,
                world.gameWidth - 260, 180, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_RATE_TEXT, AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 30,
                Align.left);

        rateButton = new MenuButton(world,
                world.gameWidth - Settings.LEVEL_BUTTON_SIZE - 100, world.gameHeight - Settings.LEVEL_BUTTON_SIZE - 40,
                Settings.LEVEL_BUTTON_SIZE, Settings.LEVEL_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUE_500, 1f), AssetLoader.rateButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));
    }

    public void setButtonsSmallSmartphone() {
        homeButton = new MenuButton(world,
                world.gameWidth / 2 - (AssetLoader.replayButton.getRegionWidth() / 2) - 250,
                bigBannerPass.getPosition().y - 170,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        plusButton = new MenuButton(world,
                world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) + 20,
                bigBannerPass.getPosition().y - 170,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.plusThreeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreCounter = new Counter(world, world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) - 30 + Settings.GAME_BUTTON_SIZE,
                bigBannerPass.getPosition().y - 170 + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f), getHelpName(), false,
                world.parseColor(Settings.COLOR_RED_500, 1f), 10f);
        replayButton = new MenuButton(world,
                world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 260,
                bigBannerPass.getPosition().y - 170,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningCounter = new Counter(world, world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 310 + Settings.GAME_BUTTON_SIZE,
                bigBannerPass.getPosition().y - 170 + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f), "resol", false,
                world.parseColor(Settings.COLOR_RED_500, 1f), 10f);
    }

    public void setGameObjectsPositionsSmallSmartphone(){
        bigBannerPass.setPosition(world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2, world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 40 + 30);
        continueTextPass.setPosition(bigBannerPass.getPosition().x + 50, bigBannerPass.getPosition().y + bigBannerPass.getSprite().getHeight() / 2 - 35);
        bannerPassButton.setPosition(world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 150);
        bigBannerFail.setPosition(world.gameWidth / 2 - AssetLoader.bigBanner.getRegionWidth() / 2, world.gameHeight / 2 - AssetLoader.bigBanner.getRegionHeight() / 2 + 40 + 30);
        continueTextFail.setPosition(bigBannerFail.getPosition().x + 50, bigBannerFail.getPosition().y + bigBannerFail.getSprite().getHeight() / 2 - 35);
        bannerFailButton.setPosition(world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 150);
        homeButton.setPosition(world.gameWidth / 2 - (AssetLoader.replayButton.getRegionWidth() / 2) - 250, bigBannerPass.getPosition().y - 170);
        plusButton.setPosition(world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) + 20, bigBannerPass.getPosition().y - 170);
        moreCounter.setPosition(world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) - 30 + Settings.GAME_BUTTON_SIZE, bigBannerPass.getPosition().y - 170 + Settings.GAME_BUTTON_SIZE - 35);
        replayButton.setPosition(world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 260, bigBannerPass.getPosition().y - 170);
        lightningCounter.setPosition(world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 310 + Settings.GAME_BUTTON_SIZE, bigBannerPass.getPosition().y - 170 + Settings.GAME_BUTTON_SIZE - 35);
        bannerRoulette.setPosition(-50, world.gameHeight - 230);
        rouletteText.setPosition(30, world.gameHeight - 180);
        rouletteButton.setPosition(world.gameWidth - Settings.LEVEL_BUTTON_SIZE - 100, world.gameHeight - Settings.LEVEL_BUTTON_SIZE - 40);
        bannerRate.setPosition(-50, world.gameHeight - 230);
        rateText.setPosition(30, world.gameHeight - 200);
        rateButton.setPosition(world.gameWidth - Settings.LEVEL_BUTTON_SIZE - 100, world.gameHeight - Settings.LEVEL_BUTTON_SIZE - 40);
    }

    // ----------- TABLET 4/3 -------------
    public void setBigBannerTablet43(){

        bigBannerPass = new GameObject(world,
                world.gameWidth / 2 - 350,
                world.gameHeight / 2 - 130,
                700, 400,
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_GREEN_200, 1f));
        continueTextPass = new Text(world, bigBannerPass.getPosition().x + 50,
                bigBannerPass.getPosition().y + bigBannerPass.getSprite().getHeight() / 2 - 70,
                bigBannerPass.getSprite().getWidth(), 100, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_PASS_TEXT, AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                Align.left);
        bannerPassButton = new MenuButton(world,
                world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 130,
                Settings.NORMAL_BUTTON_SIZE_TABLET43, Settings.NORMAL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));
        bigBannerFail = new GameObject(world,
                world.gameWidth / 2 - 350,
                world.gameHeight / 2 - 130,
                700, 400,
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_RED_200, 1f));
        continueTextFail = new Text(world, bigBannerFail.getPosition().x + 50,
                bigBannerFail.getPosition().y + bigBannerFail.getSprite().getHeight() / 2 - 75,
                bigBannerFail.getSprite().getWidth(), 100, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_REPLAY_TEXT, AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                Align.left);
        bannerFailButton = new MenuButton(world,
                world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 130,
                Settings.NORMAL_BUTTON_SIZE_TABLET43, Settings.NORMAL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.gOReplayButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        // Roulette
        bannerRoulette = new GameObject(world,
                -50, world.gameHeight - 170,
                world.gameWidth + 100, 400,
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_PURPLE_500, 1f));

        rouletteText = new Text(world,
                30, world.gameHeight - 190,
                world.gameWidth - 260, 180, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_ROULETTE_TEXT, AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 30,
                Align.left);

        rouletteButton = new MenuButton(world,
                world.gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 120, world.gameHeight - Settings.SMALL_BUTTON_SIZE_TABLET43 - 25,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.videoButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        // Rate
        bannerRate = new GameObject(world,
                -50, world.gameHeight - 170,
                world.gameWidth + 100, 400,
                AssetLoader.bigBanner,
                world.parseColor(Settings.COLOR_PINK_500, 1f));

        rateText = new Text(world,
                30, world.gameHeight - 190,
                world.gameWidth - 260, 180, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                Settings.GAME_GAMEOVER_RATE_TEXT, AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 30,
                Align.left);

        rateButton = new MenuButton(world,
                world.gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 120, world.gameHeight - Settings.SMALL_BUTTON_SIZE_TABLET43 - 25,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUE_500, 1f), AssetLoader.rateButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));
    }

    public void setButtonsTablet43() {
        homeButton = new MenuButton(world,
                world.gameWidth / 2 - (AssetLoader.replayButton.getRegionWidth() / 2) - 250,
                bigBannerPass.getPosition().y - 140,
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        plusButton = new MenuButton(world,
                world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) + 20,
                bigBannerPass.getPosition().y - 140,
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.plusThreeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreCounter = new Counter(world, world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) - 45 + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43,
                bigBannerPass.getPosition().y - 140 + Settings.GAME_BUTTON_SIZE - 50,
                50, 50, AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_WHITE, 1f), getHelpName(), false,
                world.parseColor(Settings.COLOR_RED_500, 1f), 10f);
        replayButton = new MenuButton(world,
                world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 260,
                bigBannerPass.getPosition().y - 140,
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningCounter = new Counter(world, world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 325 + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43,
                bigBannerPass.getPosition().y - 140 + Settings.GAME_BUTTON_SIZE - 50,
                50, 50, AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_WHITE, 1f), "resol", false,
                world.parseColor(Settings.COLOR_RED_500, 1f), 10f);
    }

    public void setGameObjectsPositionsTablet43(){
        bigBannerPass.setPosition(world.gameWidth / 2 - 350, world.gameHeight / 2 - 130);
        continueTextPass.setPosition(bigBannerPass.getPosition().x + 50, bigBannerPass.getPosition().y + bigBannerPass.getSprite().getHeight() / 2 - 65);
        bannerPassButton.setPosition(world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 130);
        bigBannerFail.setPosition(world.gameWidth / 2 - 350, world.gameHeight / 2 - 130);
        continueTextFail.setPosition(bigBannerFail.getPosition().x + 50, bigBannerFail.getPosition().y + bigBannerFail.getSprite().getHeight() / 2 - 65);
        bannerFailButton.setPosition(world.gameWidth / 2 + 120, bigBannerPass.getPosition().y + 130);
        homeButton.setPosition(world.gameWidth / 2 - (AssetLoader.replayButton.getRegionWidth() / 2) - 250, bigBannerPass.getPosition().y - 140);
        plusButton.setPosition(world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) + 20, bigBannerPass.getPosition().y - 140);
        moreCounter.setPosition(world.gameWidth / 2 - (AssetLoader.homeButton.getRegionWidth() / 2) - 45 + Settings.GAME_BUTTON_SIZE, bigBannerPass.getPosition().y - 140 + Settings.GAME_BUTTON_SIZE - 50);
        replayButton.setPosition(world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 260, bigBannerPass.getPosition().y - 140);
        lightningCounter.setPosition(world.gameWidth - (AssetLoader.homeButton.getRegionWidth() / 2) - 325 + Settings.GAME_BUTTON_SIZE, bigBannerPass.getPosition().y - 140 + Settings.GAME_BUTTON_SIZE - 50);
        bannerRoulette.setPosition(-50, world.gameHeight - 170);
        rouletteText.setPosition(30, world.gameHeight - 190);
        rouletteButton.setPosition(world.gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 120, world.gameHeight - Settings.SMALL_BUTTON_SIZE_TABLET43 - 25);
        bannerRate.setPosition(-50, world.gameHeight - 170);
        rateText.setPosition(30, world.gameHeight - 190);
        rateButton.setPosition(world.gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 120, world.gameHeight - Settings.SMALL_BUTTON_SIZE_TABLET43 - 25);
    }
    // ------------------------------------------------

    public String getHelpName(){
        if(world.isTimeGame()){
            return "moreTime";
        } else {
            return "moreMoves";
        }
    }

    public void setStateToActive() {
        Tween.to(time, -1, 0.1f).target(1).delay(.0f).setCallback(stateToActive)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void setStateToInactive() {
        gameOverState = GameOverState.INACTIVE;
    }

    public boolean isGameComplete() {
        return gameComplete;
    }

    public ArrayList<MenuButton> getMenuButtons() {
        return menuButtons;
    }

    public MenuButton getRouletteButton(){
        return rouletteButton;
    }

    public MenuButton getRateButton() {
        return rateButton;
    }

    public boolean isRouletteActive(){
        return rouletteActive;
    }

    public boolean isRateActive() {
        return showRate;
    }
}
