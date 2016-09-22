package com.forkstone.tokens.levelworld;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Settings;

import com.forkstone.tokens.gameobjects.Background;
import com.forkstone.tokens.gameobjects.GameObject;

import com.forkstone.tokens.gameworld.GameCam;
import com.forkstone.tokens.gameworld.GameState;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.LevelButton;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;

import java.util.ArrayList;

/**
 * Created by sergi on 9/12/15.
 */
public class LevelWorld extends GameWorld {

    public static final String TAG = "LevelWorld";

    // SETTINGS
    private Settings settings;

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public LevelWorld world;

    //GAME CAMERA
    private GameCam camera;

    //MENU OBJECTS
    private Background background, topWLayer, drawedBack;
    private GameObject banner;
    private ArrayList<LevelButton> levelButtons = new ArrayList<LevelButton>();
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private Text title;
    private MenuButton rightButton, leftButton, homeButton, shopButton;
    private int currentDistrict, levelFrom, levelTo;
    private String districtText;

    public LevelWorld(ButtonsGame game, ActionResolver actionResolver, float gameWidth, float gameHeight, float worldWidth, float worldHeight, int district) {

        super(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, 0);
        settings = new Settings();
        this.game = game;
        this.actionResolver = actionResolver;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        ButtonsGame.gameState = GameState.LEVELS;

        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        initialDistrict(district);
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, getDistrictBackground(currentDistrict), world.parseColor(Settings.COLOR_WHITE, 1f));
    }

    public void update(float delta) {
        background.update(delta);
        drawedBack.update(delta);
        leftButton.update(delta);
        rightButton.update(delta);
        homeButton.update(delta);
        shopButton.update(delta);
        banner.update(delta);
        for (int i = 0; i < levelButtons.size(); i++) {
            levelButtons.get(i).update(delta);
        }
        title.update(delta);
        topWLayer.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
//        camera.render(batch, shapeRenderer);
        background.render(batch, shapeRenderer, fontShader, objectShader);
        drawedBack.render(batch, shapeRenderer, fontShader, objectShader);
        //drawedBackground.render(batch, shapeRenderer, fontShader);
        if(currentDistrict != 1) {leftButton.render(batch, shapeRenderer, fontShader, objectShader);}
        rightButton.render(batch, shapeRenderer, fontShader, objectShader);
        homeButton.render(batch, shapeRenderer, fontShader, objectShader);
        shopButton.render(batch, shapeRenderer, fontShader, objectShader);
        banner.render(batch, shapeRenderer, fontShader, objectShader);
        for (int i = 0; i < levelButtons.size(); i++) {
            levelButtons.get(i).render(batch, shapeRenderer, fontShader, objectShader);
        }
        title.render(batch, shapeRenderer, fontShader, objectShader);
        topWLayer.render(batch, shapeRenderer, fontShader, objectShader);
    }

    // Music
    public void setMusic(int currentDistrict) {
        if (AssetLoader.getMusicState()) {
            AssetLoader.musicJungle.stop();
            AssetLoader.musicField.stop();
            AssetLoader.musicDesert.stop();
            AssetLoader.musicMountain.stop();
            AssetLoader.musicMenu.stop();
            Music districtMusic;
            switch (currentDistrict){
                case 1:
                    districtMusic = AssetLoader.musicJungle;
                    break;
                case 2:
                    districtMusic = AssetLoader.musicField;
                    break;
                case 3:
                    districtMusic = AssetLoader.musicDesert;
                    break;
                default:
                    districtMusic = AssetLoader.musicMountain;
                    break;
            }
            districtMusic.setLooping(true);
            districtMusic.play();
            districtMusic.setVolume(Settings.MUSIC_VOLUME);
        }
    }

    public void initialDistrict(int district){
        if(district == 0){
            if(AssetLoader.getLevel() <= 25){
                currentDistrict = 1;
            } else if(AssetLoader.getLevel() > 25 && AssetLoader.getLevel() <= 50){
                currentDistrict = 2;
            } else if(AssetLoader.getLevel() > 50 && AssetLoader.getLevel() <= 75){
                currentDistrict = 3;
            } else if(AssetLoader.getLevel() > 75 && AssetLoader.getLevel() <= 100){
                currentDistrict = 4;
            } else if(AssetLoader.getLevel() > 100 && AssetLoader.getLevel() <= 125){
                currentDistrict = 5;
            } else if(AssetLoader.getLevel() > 125 && AssetLoader.getLevel() <= 150){
                currentDistrict = 6;
            } else if(AssetLoader.getLevel() > 150 && AssetLoader.getLevel() <= 175){
                currentDistrict = 7;
            } else if(AssetLoader.getLevel() > 175 && AssetLoader.getLevel() <= 200){
                currentDistrict = 8;
            } else if(AssetLoader.getLevel() > 200 && AssetLoader.getLevel() <= 225){
                currentDistrict = 9;
            } else if(AssetLoader.getLevel() > 225){
                currentDistrict = 10;
            }
        } else {
            currentDistrict = district;
        }
        setDistrict(currentDistrict);
    }

    public void setDistrict(int district){
        // Tracking
        actionResolver.setTrackerScreenName("LevelScreen: District " + district);
        switch (district){
            case 1:
                levelFrom = 0;
                levelTo = 25;
                districtText = Settings.LEVELS_DISTRICT_ONE_TEXT;
                break;
            case 2:
                levelFrom = 25;
                levelTo = 50;
                districtText = Settings.LEVELS_DISTRICT_TWO_TEXT;
                break;
            case 3:
                levelFrom = 50;
                levelTo = 75;
                districtText = Settings.LEVELS_DISTRICT_THREE_TEXT;
                break;
            case 4:
                levelFrom = 75;
                levelTo = 100;
                districtText = Settings.LEVELS_DISTRICT_FOUR_TEXT;
                break;
            case 5:
                levelFrom = 100;
                levelTo = 125;
                districtText = Settings.LEVELS_DISTRICT_FIVE_TEXT;
                break;
            case 6:
                levelFrom = 125;
                levelTo = 150;
                districtText = Settings.LEVELS_DISTRICT_SIX_TEXT;
                break;
            case 7:
                levelFrom = 150;
                levelTo = 175;
                districtText = Settings.LEVELS_DISTRICT_SEVEN_TEXT;
                break;
            case 8:
                levelFrom = 175;
                levelTo = 200;
                districtText = Settings.LEVELS_DISTRICT_EIGHT_TEXT;
                break;
            case 9:
                levelFrom = 200;
                levelTo = 225;
                districtText = Settings.LEVELS_DISTRICT_NINE_TEXT;
                break;
            default:
                levelFrom = 225;
                levelTo = 250;
                districtText = Settings.LEVELS_DISTRICT_TEN_TEXT;
                break;
        }
        setUpItemPositions();
//        setMusic(district);
        topWLayer.fadeOut(.5f, .1f);
    }

    public void setUpItemPositions(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            setLevelBannerSmartphone();
            setMenuButtonsSmartphone();
            setLevelButtonsSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)){
            setLevelBannerTablet1610();
            setMenuButtonsTablet1610();
            setLevelButtonsTablet1610();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)){
            setLevelBannerSmallSmartphone();
            setMenuButtonsSmallSmartphone();
            setLevelButtonsSmallSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            setLevelBannerTablet43();
            setMenuButtonsTablet43();
            setLevelButtonsTablet43();
        }
    }

    // ----------- SMARTPHONE -------------
    public void setLevelBannerSmartphone(){
        title = new Text(world, 100, gameHeight - 460,
                gameWidth - 200, 300,
                AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                districtText , AssetLoader.steelfishFontM, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                Align.center);

        banner = new GameObject(world, 200, gameHeight - 330,
                gameWidth - 400, 210,
                AssetLoader.districtBanner, world.parseColor(Settings.COLOR_BLUEGREY_100, 1f));
    }

    public void setMenuButtonsSmartphone(){
        leftButton = new MenuButton(this, 35, gameHeight - 295,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.leftButton, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        rightButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 35, gameHeight - 295,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), getRightButtonTextureRegion(), false, world.parseColor(Settings.COLOR_WHITE, 1f));

        homeButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.homeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        shopButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.shopButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        menuButtons.add(leftButton);
        menuButtons.add(rightButton);
        menuButtons.add(homeButton);
        menuButtons.add(shopButton);
    }

    public void setLevelButtonsSmartphone() {
        int j = 0, k;
        for (int i = levelFrom; i < levelTo; i++) {
            k = i%5;
            if(k == 0) j++;
            levelButtons.add(new LevelButton(this, (Settings.LEVEL_BUTTON_SIZE * k) + (65 * (k + 1)),
                    gameHeight - (202 * j) - 390,
                    Settings.LEVEL_BUTTON_SIZE, Settings.LEVEL_BUTTON_SIZE + 10,
                    AssetLoader.colorButton,
                    getButtonColor(i), getType(i)));
        }
    }

    // ----------- TABLET 16/10 -------------
    public void setLevelBannerTablet1610(){
        title = new Text(world, 100, gameHeight - 460 + 40,
                gameWidth - 200, 300,
                AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                districtText , AssetLoader.steelfishFontM, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                Align.center);

        banner = new GameObject(world, 200, gameHeight - 330 + 40,
                gameWidth - 400, 210,
                AssetLoader.districtBanner, world.parseColor(Settings.COLOR_BLUEGREY_100, 1f));
    }

    public void setMenuButtonsTablet1610(){
        leftButton = new MenuButton(this, 35, gameHeight - 295 + 40,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.leftButton, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        rightButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 35, gameHeight - 295 + 40,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), getRightButtonTextureRegion(), false, world.parseColor(Settings.COLOR_WHITE, 1f));

        homeButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.homeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        shopButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.shopButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        menuButtons.add(leftButton);
        menuButtons.add(rightButton);
        menuButtons.add(homeButton);
        menuButtons.add(shopButton);
    }

    public void setLevelButtonsTablet1610() {
        int j = 0, k;
        for (int i = levelFrom; i < levelTo; i++) {
            k = i%5;
            if(k == 0) j++;
            levelButtons.add(new LevelButton(this, (Settings.LEVEL_BUTTON_SIZE * k) + (65 * (k + 1)),
                    gameHeight - (197 * j) - 390 + 70,
                    Settings.LEVEL_BUTTON_SIZE, Settings.LEVEL_BUTTON_SIZE + 10,
                    AssetLoader.colorButton,
                    getButtonColor(i), getType(i)));
        }
    }

    // ----------- SMALL SMARTPHONE -------------
    public void setLevelBannerSmallSmartphone(){
        title = new Text(world, 100, gameHeight - 460 + 40,
                gameWidth - 200, 300,
                AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                districtText , AssetLoader.steelfishFontM, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                Align.center);

        banner = new GameObject(world, 200, gameHeight - 330 + 40,
                gameWidth - 400, 210,
                AssetLoader.districtBanner, world.parseColor(Settings.COLOR_BLUEGREY_100, 1f));
    }

    public void setMenuButtonsSmallSmartphone(){
        leftButton = new MenuButton(this, 35, gameHeight - 295 + 40,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.leftButton, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        rightButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 35, gameHeight - 295 + 40,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), getRightButtonTextureRegion(), false, world.parseColor(Settings.COLOR_WHITE, 1f));

        homeButton = new MenuButton(this, 180, 100,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.homeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        shopButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 100,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.shopButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        menuButtons.add(leftButton);
        menuButtons.add(rightButton);
        menuButtons.add(homeButton);
        menuButtons.add(shopButton);
    }

    public void setLevelButtonsSmallSmartphone() {
        int j = 0, k;
        for (int i = levelFrom; i < levelTo; i++) {
            k = i%5;
            if(k == 0) j++;
            levelButtons.add(new LevelButton(this, (Settings.LEVEL_BUTTON_SIZE * k) + (65 * (k + 1)),
                    gameHeight - (197 * j) - 390 + 60,
                    Settings.LEVEL_BUTTON_SIZE, Settings.LEVEL_BUTTON_SIZE + 10,
                    AssetLoader.colorButton,
                    getButtonColor(i), getType(i)));
        }
    }

    // ----------- TABLET 4/3 -------------
    public void setLevelBannerTablet43(){
        title = new Text(world, 100, gameHeight - 460 + 75,
                gameWidth - 200, 300,
                AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                districtText , AssetLoader.steelfishFontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                Align.center);

        banner = new GameObject(world, 300, gameHeight - 330 + 110,
                gameWidth - 600, 160,
                AssetLoader.districtBanner, world.parseColor(Settings.COLOR_BLUEGREY_100, 1f));
    }

    public void setMenuButtonsTablet43(){
        leftButton = new MenuButton(this, 100, gameHeight - 295 + 100,
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.leftButton, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        rightButton = new MenuButton(this, gameWidth - Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 - 100, gameHeight - 295 + 100,
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), getRightButtonTextureRegion(), false, world.parseColor(Settings.COLOR_WHITE, 1f));

        homeButton = new MenuButton(this, 180, 170,
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.homeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        shopButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 170,
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.shopButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        menuButtons.add(leftButton);
        menuButtons.add(rightButton);
        menuButtons.add(homeButton);
        menuButtons.add(shopButton);
    }

    public void setLevelButtonsTablet43() {
        int j = 0, k;
        for (int i = levelFrom; i < levelTo; i++) {
            k = i%5;
            if(k == 0) j++;
            levelButtons.add(new LevelButton(this, (Settings.SMALL_BUTTON_SIZE_TABLET43 * k) + (75 * (k + 1)) + 40,
                    gameHeight - (172 * j) - 390 + 150,
                    Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 10,
                    AssetLoader.colorButton,
                    getButtonColor(i), getType(i)));
        }
    }
    // ---------------------------------------------------------------

    public int getType(int i) {
        if(i+1 > AssetLoader.getLevel()) {
            return -1;
        } else {
            return i+1;
        }
    }

    public Color getButtonColor(int i) {
        switch (currentDistrict){
            case 1:
                if(i+1 > AssetLoader.getLevel()) {
                    return world.parseColor(Settings.COLOR_DISTRICT1_INACTIVE, 1f);
                } else {
                    return world.parseColor(Settings.COLOR_DISTRICT1, 1f);
                }
            case 2:
                if(i+1 > AssetLoader.getLevel()) {
                    return world.parseColor(Settings.COLOR_DISTRICT2_INACTIVE, 1f);
                } else {
                    return world.parseColor(Settings.COLOR_DISTRICT2, 1f);
                }
            case 3:
                if(i+1 > AssetLoader.getLevel()) {
                    return world.parseColor(Settings.COLOR_DISTRICT3_INACTIVE, 1f);
                } else {
                    return world.parseColor(Settings.COLOR_DISTRICT3, 1f);
                }
            case 4:
                if(i+1 > AssetLoader.getLevel()) {
                    return world.parseColor(Settings.COLOR_DISTRICT4_INACTIVE, 1f);
                } else {
                    return world.parseColor(Settings.COLOR_DISTRICT4, 1f);
                }
            case 5:
                if(i+1 > AssetLoader.getLevel()) {
                    return world.parseColor(Settings.COLOR_DISTRICT5_INACTIVE, 1f);
                } else {
                    return world.parseColor(Settings.COLOR_DISTRICT5, 1f);
                }
            case 6:
                if(i+1 > AssetLoader.getLevel()) {
                    return world.parseColor(Settings.COLOR_DISTRICT6_INACTIVE, 1f);
                } else {
                    return world.parseColor(Settings.COLOR_DISTRICT6, 1f);
                }
            case 7:
                if(i+1 > AssetLoader.getLevel()) {
                    return world.parseColor(Settings.COLOR_DISTRICT7_INACTIVE, 1f);
                } else {
                    return world.parseColor(Settings.COLOR_DISTRICT7, 1f);
                }
            case 8:
                if(i+1 > AssetLoader.getLevel()) {
                    return world.parseColor(Settings.COLOR_DISTRICT8_INACTIVE, 1f);
                } else {
                    return world.parseColor(Settings.COLOR_DISTRICT8, 1f);
                }
            case 9:
                if(i+1 > AssetLoader.getLevel()) {
                    return world.parseColor(Settings.COLOR_DISTRICT9_INACTIVE, 1f);
                } else {
                    return world.parseColor(Settings.COLOR_DISTRICT9, 1f);
                }
            default:
                if(i+1 > AssetLoader.getLevel()) {
                    return world.parseColor(Settings.COLOR_DISTRICT10_INACTIVE, 1f);
                } else {
                    return world.parseColor(Settings.COLOR_DISTRICT10, 1f);
                }
        }
    }

    public TextureRegion getRightButtonTextureRegion(){
        switch (currentDistrict){
            case 1:
                if(AssetLoader.getLevel() <= 25){
                    return AssetLoader.lockButton;
                } else {
                    return AssetLoader.rightButton;
                }
            case 2:
                if(AssetLoader.getLevel() <= 50){
                    return AssetLoader.lockButton;
                } else {
                    return AssetLoader.rightButton;
                }
            case 3:
                if(AssetLoader.getLevel() <= 75){
                    return AssetLoader.lockButton;
                } else {
                    return AssetLoader.rightButton;
                }
            case 4:
                if(AssetLoader.getLevel() <= 100){
                    return AssetLoader.lockButton;
                } else {
                    return AssetLoader.rightButton;
                }
            case 5:
                if(AssetLoader.getLevel() <= 125){
                    return AssetLoader.lockButton;
                } else {
                    return AssetLoader.rightButton;
                }
            case 6:
                if(AssetLoader.getLevel() <= 150){
                    return AssetLoader.lockButton;
                } else {
                    return AssetLoader.rightButton;
                }
            case 7:
                if(AssetLoader.getLevel() <= 175){
                    return AssetLoader.lockButton;
                } else {
                    return AssetLoader.rightButton;
                }
            case 8:
                if(AssetLoader.getLevel() <= 200){
                    return AssetLoader.lockButton;
                } else {
                    return AssetLoader.rightButton;
                }
            case 9:
                if(AssetLoader.getLevel() <= 225){
                    return AssetLoader.lockButton;
                } else {
                    return AssetLoader.rightButton;
                }
            default:
                return AssetLoader.lockButton;

        }
    }

    public TextureRegion getDistrictBackground(int district){
        switch (district){
            case 1:
                return AssetLoader.backgroundJungle;
            case 2:
                return AssetLoader.backgroundField;
            case 3:
                return AssetLoader.backgroundDesert;
            case 4:
                return AssetLoader.backgroundMountain;
            case 5:
                return AssetLoader.backgroundFishers;
            case 6:
                return AssetLoader.backgroundMusicians;
            case 7:
                return AssetLoader.backgroundMine;
            case 8:
                return AssetLoader.backgroundOriental;
            case 9:
                return AssetLoader.backgroundIceland;
            default:
                return AssetLoader.backgroundVikings;
        }
    }

    public GameCam getCamera() {
        return camera;
    }

    public ArrayList<LevelButton> getLevelButtons() {
        return levelButtons;
    }

    public ArrayList<MenuButton> getMenuButtons() {return menuButtons;}

    public int getCurrentDistrict() {
        return currentDistrict;
    }

    public void goToGameScreen(int i) {
        levelButtons.get(0).toGameScreen(0.6f, 0.1f, i);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToHomeScreen() {
        levelButtons.get(0).toHomeScreen(0.6f, 0.1f);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToLevelScreen(int district) {
        if(district <= Settings.DISTRICTS_NUMBER) {
            levelButtons.get(0).toLevelScreen(0.2f, 0.05f, district);
            topWLayer.fadeIn(0.2f, .05f);
        }
    }

    public void goToTutorialScreen(String type, int level) {
        menuButtons.get(0).toTutorialScreen(0.6f, 0.1f, type, level);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToSettingsScreen() {
        shopButton.toSettingsScreen(0.6f, 0.1f);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToShopScreen() {
        shopButton.toShopScreen(0.6f, 0.1f, 2);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToStoryScreen(int storyPart) {
        menuButtons.get(0).toStoryScreen(0.6f, 0.1f, storyPart);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToCharacterScreen(int part) {
        menuButtons.get(0).toCharacterScreen(0.6f, 0.1f, part);
        topWLayer.fadeIn(0.6f, .1f);
    }
}
