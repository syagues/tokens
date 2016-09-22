package com.forkstone.tokens.randomworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.Background;
import com.forkstone.tokens.gameobjects.GameObject;
import com.forkstone.tokens.gameobjects.ShopStar;
import com.forkstone.tokens.gameworld.GameCam;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;

import java.util.ArrayList;

/**
 * Created by sergi on 25/1/16.
 */
public class RandomWorld extends GameWorld {

    public static final String TAG = "RandomWorld";

    // SETTINGS
    private Settings settings;

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public RandomWorld world;

    //GAME CAMERA
    private GameCam camera;

    // OBJECTS
    private Background background, topWLayer;
    private MenuButton shuffleButton, playButton;
    private Text titleText, subText;
    private GameObject dot;
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private ArrayList<ShopStar> shopStars = new ArrayList<ShopStar>();

    // VARIABLES
    private int numberOfStars = 50;
    private boolean random = false;
    private int state = 0;
    private int backScreen;

    public RandomWorld(ButtonsGame game, ActionResolver actionResolver, float gameWidth, float gameHeight, float worldWidth, float worldHeight, int backScreen) {

        super(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, 0);
        settings = new Settings();
        this.game = game;
        this.actionResolver = actionResolver;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.backScreen = backScreen;

        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_PURPLE_900, 1f));
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_PURPLE_900, 1f));
        topWLayer.fadeOut(.5f, .1f);

        setUpItems();

        menuButtons.add(playButton);

        for (int i = 0; i < numberOfStars; i++){
            shopStars.add(new ShopStar(this));
        }

        // Tracking
        actionResolver.setTrackerScreenName("RandomScreen");
    }

    public void update(float delta) {
        background.update(delta);
        for (ShopStar shopStar : shopStars){
            shopStar.update(delta);
        }
        if (random) setRandomColor();
        titleText.update(delta);
        subText.update(delta);
        shuffleButton.update(delta);
        dot.update(delta);
        playButton.update(delta);
        topWLayer.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        background.render(batch, shapeRenderer, fontShader, objectShader);
        for (ShopStar shopStar : shopStars){
            shopStar.render(batch, shapeRenderer, fontShader);
        }
        titleText.render(batch, shapeRenderer, fontShader, objectShader);
        subText.render(batch, shapeRenderer, fontShader, objectShader);
        shuffleButton.render(batch, shapeRenderer, fontShader, objectShader);
        dot.render(batch, shapeRenderer, fontShader, objectShader);
        playButton.render(batch, shapeRenderer, fontShader, objectShader);
        topWLayer.render(batch, shapeRenderer, fontShader, objectShader);
    }

    public void setUpItems(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            setSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)) {
            setTablet1610();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)) {
            setSmallSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)) {
            setTablet43();
        }
    }

    // --------------- SMARTPHONE -----------------
    public void setSmartphone(){
        titleText = new Text(this, 40, gameHeight*3/4 + 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.RANDOM_TITLE,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        subText = new Text(this, 40, titleText.getPosition().y - 200, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.RANDOM_PLAY_SUBTEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        shuffleButton = new MenuButton(this, gameWidth/2 - Settings.SHUFFLE_BUTTON_SIZE/2, gameHeight/2 - (Settings.SHUFFLE_BUTTON_SIZE + 10)/2 + 40,
                Settings.SHUFFLE_BUTTON_SIZE, Settings.SHUFFLE_BUTTON_SIZE + 10,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.shuffleButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        playButton = new MenuButton(this, gameWidth/2 - Settings.BUTTON_SIZE/2, gameHeight/4 - (Settings.BUTTON_SIZE + 10)/2,
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));

        dot = new GameObject(world, gameWidth/2 - 50, gameHeight/4 - 50, 100, 100, AssetLoader.dot, Color.WHITE);
        dot.getSprite().setAlpha(0f);
    }

    // --------------- TABLET 16/10 -----------------
    public void setTablet1610(){
        titleText = new Text(this, 40, gameHeight*3/4 + 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.RANDOM_TITLE,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        subText = new Text(this, 40, titleText.getPosition().y - 200, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.RANDOM_PLAY_SUBTEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        shuffleButton = new MenuButton(this, gameWidth/2 - Settings.SHUFFLE_BUTTON_SIZE/2, gameHeight/2 - (Settings.SHUFFLE_BUTTON_SIZE + 10)/2 + 40,
                Settings.SHUFFLE_BUTTON_SIZE, Settings.SHUFFLE_BUTTON_SIZE + 10,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.shuffleButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        playButton = new MenuButton(this, gameWidth/2 - Settings.BUTTON_SIZE/2, gameHeight/4 - (Settings.BUTTON_SIZE + 10)/2,
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));

        dot = new GameObject(world, gameWidth/2 - 50, gameHeight/4 - 50, 100, 100, AssetLoader.dot, Color.WHITE);
        dot.getSprite().setAlpha(0f);
    }

    // --------------- SMALL SMARTPHONE -----------------
    public void setSmallSmartphone(){
        titleText = new Text(this, 40, gameHeight*3/4 + 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.RANDOM_TITLE,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        subText = new Text(this, 40, titleText.getPosition().y - 200, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.RANDOM_PLAY_SUBTEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        shuffleButton = new MenuButton(this, gameWidth/2 - Settings.SHUFFLE_BUTTON_SIZE/2, gameHeight/2 - (Settings.SHUFFLE_BUTTON_SIZE + 10)/2 - 30,
                Settings.SHUFFLE_BUTTON_SIZE, Settings.SHUFFLE_BUTTON_SIZE + 10,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.shuffleButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        playButton = new MenuButton(this, gameWidth/2 - Settings.BUTTON_SIZE/2, gameHeight/4 - (Settings.BUTTON_SIZE + 10)/2 - 60,
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));

        dot = new GameObject(world, gameWidth/2 - 50, gameHeight/4 - 50, 100, 100, AssetLoader.dot, Color.WHITE);
        dot.getSprite().setAlpha(0f);
    }

    // --------------- TABLET 4/3 -----------------
    public void setTablet43(){
        titleText = new Text(this, 40, gameHeight*3/4 + 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.RANDOM_TITLE,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        subText = new Text(this, 40, titleText.getPosition().y - 200, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.RANDOM_PLAY_SUBTEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        shuffleButton = new MenuButton(this, gameWidth/2 - Settings.SHUFFLE_BUTTON_SIZE_TABLET43/2, gameHeight/2 - (Settings.SHUFFLE_BUTTON_SIZE_TABLET43 + 10)/2 + 40,
                Settings.SHUFFLE_BUTTON_SIZE_TABLET43, Settings.SHUFFLE_BUTTON_SIZE_TABLET43 + 10,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.shuffleButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        playButton = new MenuButton(this, gameWidth/2 - Settings.BUTTON_SIZE/2, gameHeight/4 - (Settings.BUTTON_SIZE + 10)/2 - 20,
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));

        dot = new GameObject(world, gameWidth/2 - 50, gameHeight/4 - 60, 100, 100, AssetLoader.dot, Color.WHITE);
        dot.getSprite().setAlpha(0f);
    }
    // ------------------------------------------------------------

    public void startRandom(){
        random = true;
        state = 1;
        shuffleButton.setIconColor(parseColor(Settings.COLOR_WHITE, 1f));
        playButton.setIcon(AssetLoader.stopButtonUp);
        playButton.setColor(parseColor(Settings.COLOR_RED_500, 1f));
        titleText.setText("...");
        subText.setText(Settings.RANDOM_STOP_SUBTEXT);
        effectStart();
    }

    public void stopRandom(){
        random = false;
        if(state == 1){
            state = 2;
            playButton.setIcon(AssetLoader.backButtonUp);
            playButton.setColor(parseColor(Settings.COLOR_BOARD, 1f));
            playButton.setIconColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            setRandomResult();
            effectStop();
        }
    }

    public void setRandomResult(){
        float randomNum = MathUtils.random(0, 100);
        if(randomNum >= 0 && randomNum < 22){
            shuffleButton.setIcon(AssetLoader.plusThreeButtonUp);
            titleText.setText(Settings.RANDOM_MORESTEPS);
            subText.setText(Settings.RANDOM_MORESTEPS_SUBTEXT);
            AssetLoader.addMoreMoves(1);
            if(AssetLoader.getSoundState()) AssetLoader.soundWin.play();
        } else if(randomNum >= 22 && randomNum < 44){
            shuffleButton.setIcon(AssetLoader.plusTenButtonUp);
            titleText.setText(Settings.RANDOM_MORETIME);
            subText.setText(Settings.RANDOM_MORETIME_SUBTEXT);
            AssetLoader.addMoreTime(1);
            if(AssetLoader.getSoundState()) AssetLoader.soundWin.play();
        } else if(randomNum >= 44 && randomNum < 66){
            shuffleButton.setIcon(AssetLoader.multicolorButtonUp);
            titleText.setText(Settings.RANDOM_MULTICOLOR);
            subText.setText(Settings.RANDOM_MULTICOLOR_SUBTEXT);
            AssetLoader.addMulticolor(1);
            if(AssetLoader.getSoundState()) AssetLoader.soundWin.play();
        } else if(randomNum >= 66 && randomNum < 68){
            shuffleButton.setIcon(AssetLoader.lightningButtonUp);
            titleText.setText(Settings.RANDOM_RESOL);
            subText.setText(Settings.RANDOM_RESOL_SUBTEXT);
            AssetLoader.addResol(1);
            if(AssetLoader.getSoundState()) AssetLoader.soundWin.play();
        } else if(randomNum >= 68 && randomNum < 100){
            shuffleButton.setIcon(AssetLoader.emperarorButtonUp);
            titleText.setText(Settings.RANDOM_NOTHING);
            subText.setText(Settings.RANDOM_NOTHING_SUBTEXT);
            if(AssetLoader.getSoundState()) AssetLoader.soundLose.play();
        }
    }

    public void setRandomColor(){
        float randomNum = MathUtils.random(0, 100);
        if(randomNum >= 0 && randomNum < 10){
            shuffleButton.setColor(world.parseColor(Settings.COLOR_GREEN_500, 1f));
            shuffleButton.setIcon(AssetLoader.multicolorButtonUp);
        } else if(randomNum >= 10 && randomNum < 20){
            shuffleButton.setColor(world.parseColor(Settings.COLOR_RED_500, 1f));
            shuffleButton.setIcon(AssetLoader.plusThreeButtonUp);
        } else if(randomNum >= 20 && randomNum < 30){
            shuffleButton.setColor(world.parseColor(Settings.COLOR_BLUE_500, 1f));
            shuffleButton.setIcon(AssetLoader.emperarorButtonUp);
        } else if(randomNum >= 30 && randomNum < 40){
            shuffleButton.setColor(world.parseColor(Settings.COLOR_PINK_500, 1f));
            shuffleButton.setIcon(AssetLoader.multicolorButtonUp);
        } else if(randomNum >= 40 && randomNum < 50){
            shuffleButton.setColor(world.parseColor(Settings.COLOR_TEAL_500, 1f));
            shuffleButton.setIcon(AssetLoader.emperarorButtonUp);
        } else if(randomNum >= 50 && randomNum < 60){
            shuffleButton.setColor(world.parseColor(Settings.COLOR_INDIGO_500, 1f));
            shuffleButton.setIcon(AssetLoader.plusTenButtonUp);
        } else if(randomNum >= 60 && randomNum < 70){
            shuffleButton.setColor(world.parseColor(Settings.COLOR_AMBER_500, 1f));
            shuffleButton.setIcon(AssetLoader.emperarorButtonUp);
        } else if(randomNum >= 70 && randomNum < 80){
            shuffleButton.setColor(world.parseColor(Settings.COLOR_PURPLE_500, 1f));
            shuffleButton.setIcon(AssetLoader.plusThreeButtonUp);
        } else if(randomNum >= 80 && randomNum < 90){
            shuffleButton.setColor(world.parseColor(Settings.COLOR_ORANGE_500, 1f));
            shuffleButton.setIcon(AssetLoader.emperarorButtonUp);
        } else if(randomNum >= 90 && randomNum < 100){
            shuffleButton.setColor(world.parseColor(Settings.COLOR_CYAN_500, 1f));
            shuffleButton.setIcon(AssetLoader.lightningButtonUp);
        }
    }

    public void effectStart(){
        dot.setColor(world.parseColor(Settings.COLOR_GREEN_500, .3f));
        dot.scale(0, 50, .3f, 0f);
        dot.fadeOut(1f, 0f, .3f);
    }

    public void effectStop(){
        dot.setColor(world.parseColor(Settings.COLOR_RED_500, .3f));
        dot.scale(0, 50, .3f, 0f);
        dot.fadeOut(1f, 0f, .3f);
    }

    public boolean getRandom(){
        return random;
    }

    public int getState(){
        return state;
    }

    public int getBackScreen(){
        return backScreen;
    }

    public ArrayList<MenuButton> getMenuButtons(){
        return menuButtons;
    }

    public void goToShopScreen() {
        menuButtons.get(0).toShopScreen(0.6f, 0.1f, backScreen);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToLevelScreen() {
        menuButtons.get(0).toLevelScreen(0.6f, 0.1f, 0);
        topWLayer.fadeIn(0.6f, .1f);
    }
}

