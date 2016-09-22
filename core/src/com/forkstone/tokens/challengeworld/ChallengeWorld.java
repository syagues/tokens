package com.forkstone.tokens.challengeworld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.Background;
import com.forkstone.tokens.gameobjects.Board;
import com.forkstone.tokens.gameobjects.Board44;
import com.forkstone.tokens.gameobjects.Board66;
import com.forkstone.tokens.gameobjects.ColorButton;
import com.forkstone.tokens.gameobjects.Counter;
import com.forkstone.tokens.gameobjects.GameObject;
import com.forkstone.tokens.gameworld.GameCam;
import com.forkstone.tokens.gameworld.GameState;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.InfoBanner;
import com.forkstone.tokens.ui.MenuButton;

import java.util.ArrayList;

/**
 * Created by sergi on 14/2/16.
 */
public class ChallengeWorld extends GameWorld {

    public static final String TAG = "ChallengeWorld";

    // SETTINGS
    private Settings settings;

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public ChallengeWorld world;

    //GAME CAMERA
    private GameCam camera;

    // VARIABLES
    private int initialLeft, left, steps, score;
    private int initialSteps;

    // OBJECTS
    private Background background, topWLayer, drawedBack;
    private MenuButton backButton, replayButton, lightningButton, multicolorButton;
    private Counter lightningCounter, multicolorCounter;
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private InfoBanner checksLeftBanner, scoreBanner, countBanner;
    private Board board;
    private GameObject auxBoard;

    public ChallengeWorld(ButtonsGame game, ActionResolver actionResolver, float gameWidth, float gameHeight, float worldWidth, float worldHeight) {

        super(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, 0);
        settings = new Settings();
        this.game = game;
        this.actionResolver = actionResolver;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(this, 0, 0, gameWidth, gameHeight, AssetLoader.square, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        drawedBack = new Background(this, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMap, parseColor(Settings.COLOR_WHITE, 1f));
        topWLayer = new Background(this, 0, 0, gameWidth, gameHeight, AssetLoader.square, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        startGame();
        setSteps(200);

        // Tracking
//        actionResolver.setTrackerScreenName("The Challenge");
    }

    public void update(float delta) {
        background.update(delta);
        drawedBack.update(delta);

        // Banners
        checksLeftBanner.update(delta);
        countBanner.update(delta);
        scoreBanner.update(delta);

        // Board
        board.update(delta);
        auxBoard.update(delta);

        // Buttons
        backButton.update(delta);
        replayButton.update(delta);
        lightningButton.update(delta);
        multicolorButton.update(delta);
        lightningCounter.update(delta);
        multicolorCounter.update(delta);

        topWLayer.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        background.render(batch, shapeRenderer, fontShader, objectShader);
        drawedBack.render(batch, shapeRenderer, fontShader, objectShader);

        // Banners
        checksLeftBanner.render(batch, shapeRenderer, fontShader, objectShader);
        countBanner.render(batch, shapeRenderer, fontShader, objectShader);
        scoreBanner.render(batch, shapeRenderer, fontShader, objectShader);

        // Board
        auxBoard.render(batch, shapeRenderer, fontShader, objectShader);
        board.render(batch, shapeRenderer, fontShader, objectShader);

        // Buttons
        backButton.render(batch, shapeRenderer, fontShader, objectShader);
        replayButton.render(batch, shapeRenderer, fontShader, objectShader);
        lightningButton.render(batch, shapeRenderer, fontShader, objectShader);
        multicolorButton.render(batch, shapeRenderer, fontShader, objectShader);
        lightningCounter.render(batch, shapeRenderer, fontShader, objectShader);
        multicolorCounter.render(batch, shapeRenderer, fontShader, objectShader);

        topWLayer.render(batch, shapeRenderer, fontShader, objectShader);
    }

    public void startGame() {
        setUpItems();
        beginGame();

        ButtonsGame.gameState = GameState.RUNNING;
    }

    public void beginGame() {
        topWLayer.fadeOut(0.5f, getDelay(0f));
        startEffects(0);
    }

    public void finishGame(boolean gameComplete){

    }

    private void setUpItems(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            setUpInfoBannersSmartphone();
            setUpButtonsSmartphone();
            setUpBoardSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)){
            setUpInfoBannersTablet1610();
            setUpButtonsTablet1610();
            setUpBoardTablet1610();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)){
            setUpInfoBannersSmallSmartphone();
            setUpButtonsSmallSmartphone();
            setUpBoardSmallSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            setUpInfoBannersTablet43();
            setUpButtonsTablet43();
            setUpBoardTablet43();
        }
    }

    // ----------- SMARTPHONE ------------
    private void setUpInfoBannersSmartphone() {
        checksLeftBanner = new InfoBanner(this,
                100, bannersAndButtonsPositionSmartphone("banner"),
                gameWidth / 2 - 310, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_LEFTBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_WHITE, 1f), 0);

        scoreBanner = new InfoBanner(this,
                gameWidth / 2 - 95, bannersAndButtonsPositionSmartphone("banner") + 65,
                gameWidth / 2 - 310, 155,
                AssetLoader.banner,
                getLevelColor(), Settings.GAME_SCOREBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontS, parseColor(Settings.COLOR_WHITE, 1f),
                parseColor(Settings.COLOR_WHITE, 1f), 1);
        setScore(0);

        countBanner = new InfoBanner(this,
                gameWidth - 310, bannersAndButtonsPositionSmartphone("banner"),
                gameWidth / 2 - 300, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_WHITE, 1f), 0);
        // TypeGame
        setCountBanner();
    }

    private void setUpButtonsSmartphone() {
        backButton = new MenuButton(this,
                100, bannersAndButtonsPositionSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        replayButton = new MenuButton(this,
                gameWidth / 2 - 185, bannersAndButtonsPositionSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        lightningButton = new MenuButton(this,
                gameWidth / 2 + 65, bannersAndButtonsPositionSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.lightningButtonUp, true, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        multicolorButton = new MenuButton(this,
                gameWidth - 225, bannersAndButtonsPositionSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.multicolorButtonUp, true, parseColor(Settings.COLOR_WHITE, 1f));

        menuButtons.add(backButton);
        menuButtons.add(replayButton);
        menuButtons.add(lightningButton);
        menuButtons.add(multicolorButton);

        lightningCounter = new Counter(this,
                gameWidth / 2 + Settings.GAME_BUTTON_SIZE + 15, bannersAndButtonsPositionSmartphone("button") + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_BLUEGREY_100, 1f), "resol", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorCounter = new Counter(this,
                gameWidth + Settings.GAME_BUTTON_SIZE - 275, bannersAndButtonsPositionSmartphone("button") + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_BLUEGREY_100, 1f), "multicolor", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);
    }

    private void setUpBoardSmartphone() {

        initialLeft = 99;
        board = new Board44(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                gameWidth - 80, gameWidth - 70,
                AssetLoader.board44, 4, 4, getPositionsArrayList(), null, 6);
        auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 90, gameWidth - 80, gameWidth - 70, AssetLoader.board44, parseColor(Settings.COLOR_BOARD, 1f));
        auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        board.fadeIn(0.5f, getDelay(1f));
        setLeft(initialLeft);
    }

    public float bannersAndButtonsPositionSmartphone(String itemTypeName) {

        // Board Cuadrat
        if (itemTypeName.equals("banner")) {
            return gameHeight - 160 - 90;
        } else if(itemTypeName.equals("button")) {
            return gameHeight / 3 - 345;
        }

        return 0;
    }

    // ----------- TABLET 16/10 ------------
    private void setUpInfoBannersTablet1610() {
        checksLeftBanner = new InfoBanner(this,
                100, bannersAndButtonsPositionTablet1610("banner"),
                gameWidth / 2 - 310, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_LEFTBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontB, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_WHITE, 1f), 0);

        scoreBanner = new InfoBanner(this,
                gameWidth / 2 - 95, bannersAndButtonsPositionTablet1610("banner") + 65,
                gameWidth / 2 - 310, 155,
                AssetLoader.banner,
                getLevelColor(), Settings.GAME_SCOREBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontS, parseColor(Settings.COLOR_WHITE, 1f),
                parseColor(Settings.COLOR_WHITE, 1f), 1);
        setScore(0);

        countBanner = new InfoBanner(this,
                gameWidth - 310, bannersAndButtonsPositionTablet1610("banner"),
                gameWidth / 2 - 300, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontB, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_WHITE, 1f), 0);
        // TypeGame
        setCountBanner();
    }

    private void setUpButtonsTablet1610() {
        backButton = new MenuButton(this,
                100, bannersAndButtonsPositionTablet1610("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        replayButton = new MenuButton(this,
                gameWidth / 2 - 185, bannersAndButtonsPositionTablet1610("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        lightningButton = new MenuButton(this,
                gameWidth / 2 + 65, bannersAndButtonsPositionTablet1610("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.lightningButtonUp, true, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        multicolorButton = new MenuButton(this,
                gameWidth - 225, bannersAndButtonsPositionTablet1610("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.multicolorButtonUp, true, parseColor(Settings.COLOR_WHITE, 1f));

        menuButtons.add(backButton);
        menuButtons.add(replayButton);
        menuButtons.add(lightningButton);
        menuButtons.add(multicolorButton);

        lightningCounter = new Counter(this,
                gameWidth / 2 + Settings.GAME_BUTTON_SIZE + 15, bannersAndButtonsPositionTablet1610("button") + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_BLUEGREY_100, 1f), "resol", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorCounter = new Counter(this,
                gameWidth + Settings.GAME_BUTTON_SIZE - 275, bannersAndButtonsPositionTablet1610("button") + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_BLUEGREY_100, 1f), "multicolor", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);
    }

    private void setUpBoardTablet1610() {
        initialLeft = 8;
        board = new Board44(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                gameWidth - 80, gameWidth - 70,
                AssetLoader.board44, 4, 4, getPositionsArrayList(), null, 6);
        auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 90, gameWidth - 80, gameWidth - 70, AssetLoader.board44, parseColor(Settings.COLOR_BOARD, 1f));
        auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        board.fadeIn(0.5f, getDelay(1f));
        setLeft(initialLeft);
    }

    public float bannersAndButtonsPositionTablet1610(String itemTypeName) {

        // Board Cuadrat
        if (itemTypeName.equals("banner")) {
            return gameHeight - 160 - 60;
        } else if(itemTypeName.equals("button")) {
            return gameHeight / 3 - 345;
        }

        return 0;
    }

    // ----------- SMALL SMARTPHONE ------------
    private void setUpInfoBannersSmallSmartphone() {
        checksLeftBanner = new InfoBanner(this,
                100, bannersAndButtonsPositionSmallSmartphone("banner"),
                gameWidth / 2 - 310, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_LEFTBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontB, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_WHITE, 1f), 0);

        scoreBanner = new InfoBanner(this,
                gameWidth / 2 - 95, bannersAndButtonsPositionSmallSmartphone("banner") + 65,
                gameWidth / 2 - 310, 155,
                AssetLoader.banner,
                getLevelColor(), Settings.GAME_SCOREBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontS, parseColor(Settings.COLOR_WHITE, 1f),
                parseColor(Settings.COLOR_WHITE, 1f), 1);
        setScore(0);

        countBanner = new InfoBanner(this,
                gameWidth - 310, bannersAndButtonsPositionSmallSmartphone("banner"),
                gameWidth / 2 - 300, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontB, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_WHITE, 1f), 0);
        // TypeGame
        setCountBanner();
    }

    private void setUpButtonsSmallSmartphone() {
        backButton = new MenuButton(this,
                100, bannersAndButtonsPositionSmallSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        replayButton = new MenuButton(this,
                gameWidth / 2 - 185, bannersAndButtonsPositionSmallSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        lightningButton = new MenuButton(this,
                gameWidth / 2 + 65, bannersAndButtonsPositionSmallSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.lightningButtonUp, true, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        multicolorButton = new MenuButton(this,
                gameWidth - 225, bannersAndButtonsPositionSmallSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.multicolorButtonUp, true, parseColor(Settings.COLOR_WHITE, 1f));

        menuButtons.add(backButton);
        menuButtons.add(replayButton);
        menuButtons.add(lightningButton);
        menuButtons.add(multicolorButton);

        lightningCounter = new Counter(this,
                gameWidth / 2 + Settings.GAME_BUTTON_SIZE + 15, bannersAndButtonsPositionSmallSmartphone("button") + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_BLUEGREY_100, 1f), "resol", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorCounter = new Counter(this,
                gameWidth + Settings.GAME_BUTTON_SIZE - 275, bannersAndButtonsPositionSmallSmartphone("button") + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_BLUEGREY_100, 1f), "multicolor", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);
    }

    private void setUpBoardSmallSmartphone() {
        initialLeft = 8;
        board = new Board44(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                gameWidth - 80, gameWidth - 70,
                AssetLoader.board44, 4, 4, getPositionsArrayList(), null, 6);
        auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 90, gameWidth - 80, gameWidth - 70, AssetLoader.board44, parseColor(Settings.COLOR_BOARD, 1f));
        auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        board.fadeIn(0.5f, getDelay(1f));
        setLeft(initialLeft);
    }

    public float bannersAndButtonsPositionSmallSmartphone(String itemTypeName) {

        // Board Cuadrat
        if (itemTypeName.equals("banner")) {
            return gameHeight - 160 - 60;
        } else if(itemTypeName.equals("button")) {
            return gameHeight / 3 - 345;
        }

        return 0;
    }

    // ----------- TABLET 4/3 ------------
    private void setUpInfoBannersTablet43() {
        checksLeftBanner = new InfoBanner(this,
                150, bannersAndButtonsPositionTablet43("banner"),
                gameWidth / 2 - 350, 120,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_LEFTBANNER_TEXT,
                AssetLoader.fontXXXS, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_WHITE, 1f), 0);

        scoreBanner = new InfoBanner(this,
                gameWidth / 2 - 95, bannersAndButtonsPositionTablet43("banner") + 65,
                gameWidth / 2 - 365, 120,
                AssetLoader.banner,
                getLevelColor(), Settings.GAME_SCOREBANNER_TEXT,
                AssetLoader.fontXXXS, AssetLoader.fontXS, parseColor(Settings.COLOR_WHITE, 1f),
                parseColor(Settings.COLOR_WHITE, 1f), 1);
        setScore(0);

        countBanner = new InfoBanner(this,
                gameWidth - 340, bannersAndButtonsPositionTablet43("banner"),
                gameWidth / 2 - 350, 120,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXXXS, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_WHITE, 1f), 0);
        // TypeGame
        setCountBanner();
    }

    private void setUpButtonsTablet43() {
        backButton = new MenuButton(this,
                150, bannersAndButtonsPositionTablet43("button"),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        replayButton = new MenuButton(this,
                gameWidth / 2 - 180, bannersAndButtonsPositionTablet43("button"),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        lightningButton = new MenuButton(this,
                gameWidth / 2 + 55, bannersAndButtonsPositionTablet43("button"),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.lightningButtonUp, true, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        multicolorButton = new MenuButton(this,
                gameWidth - 250, bannersAndButtonsPositionTablet43("button"),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.multicolorButtonUp, true, parseColor(Settings.COLOR_WHITE, 1f));

        menuButtons.add(backButton);
        menuButtons.add(replayButton);
        menuButtons.add(lightningButton);
        menuButtons.add(multicolorButton);

        lightningCounter = new Counter(this,
                gameWidth / 2 + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 20, bannersAndButtonsPositionTablet43("button") + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 - 30,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_BLUEGREY_100, 1f), "resol", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorCounter = new Counter(this,
                gameWidth + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 - 280, bannersAndButtonsPositionTablet43("button") + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 - 30,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_BLUEGREY_100, 1f), "multicolor", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);
    }

    private void setUpBoardTablet43() {
        initialLeft = 8;
        board = new Board44(this, 100, gameHeight / 2 - (gameWidth / 2) + 130,
                gameWidth - 200, gameWidth - 175,
                AssetLoader.board44, 4, 4, getPositionsArrayList(), null, 6);
        auxBoard = new GameObject(this, 100, gameHeight / 2 - (gameWidth / 2) + 130, gameWidth - 200, gameWidth - 175, AssetLoader.board44, parseColor(Settings.COLOR_BOARD, 1f));
        auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        board.fadeIn(0.5f, getDelay(1f));
        setLeft(initialLeft);
    }

    public float bannersAndButtonsPositionTablet43(String itemTypeName) {

        // Board Cuadrat
        if (itemTypeName.equals("banner")) {
            return gameHeight - 170;
        } else if(itemTypeName.equals("button")) {
            return gameHeight / 3 - 320;
        }

        return 0;
    }
    // ------------------------------------------------------

    public void fadeInLayer(){
        topWLayer.fadeIn(0.2f, 0f);
    }

    private void fadeInItems(){
        checksLeftBanner.effectsIn();
        countBanner.effectsIn();

        board.fadeIn(0.3f, 0f);
        ArrayList<ColorButton> colorButtons = getBoard().getColorButtons();
        for (ColorButton colorButton : colorButtons){
            colorButton.effectsIn();
        }

        for (MenuButton menuButton : menuButtons){
            menuButton.effectsIn();
        }
        lightningCounter.effectsIn();
        multicolorCounter.effectsIn();
    }

    private void fadeOutItems(){
        checksLeftBanner.effectsOut();
        countBanner.effectsOut();

        auxBoard.fadeOut(.1f, 0f);
        board.fadeOut(0.3f, 0f);
        ArrayList<ColorButton> colorButtons = getBoard().getColorButtons();
        for (ColorButton colorButton : colorButtons){
            colorButton.effectsOut();
        }

        for (MenuButton menuButton : menuButtons){
            menuButton.effectsOut();
        }
        lightningCounter.effectsOut();
        multicolorCounter.effectsOut();
    }

    private void finishEffect(boolean gameComplete){

    }

    private void startEffects(float extraDelay){
        checksLeftBanner.effectX(-300, checksLeftBanner.getPosition().x, 0.6f, getDelay(.2f) + extraDelay);
        countBanner.effectX(gameWidth, countBanner.getPosition().x, 0.6f, getDelay(.2f) + extraDelay);
        scoreBanner.effectY(worldHeight + 200, scoreBanner.getPosition().y, 0.8f, getDelay(0.3f) + extraDelay);

        backButton.effectX(-300, backButton.getPosition().x, 0.6f, getDelay(0.2f) + extraDelay);
        replayButton.effectY(-100, replayButton.getPosition().y, 0.6f, getDelay(0.2f) + extraDelay);
        lightningButton.effectY(-100, lightningButton.getPosition().y, 0.6f, getDelay(0.2f) + extraDelay);
        multicolorButton.effectX(gameWidth, multicolorButton.getPosition().x, 0.6f, getDelay(0.2f) + extraDelay);
        lightningCounter.effectY(-100, lightningCounter.getPosition().y, 0.6f, getDelay(0.2f) + extraDelay);
        multicolorCounter.effectX(gameWidth, multicolorCounter.getPosition().x, 0.6f, getDelay(0.2f) + extraDelay);
    }

    public float getDelay(float baseDelay){
        return baseDelay;
    }

    private void updateStepsBannerColor(){
        if(initialSteps / 2 <= getSteps() && getSteps() > 3){
            countBanner.setScoreFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            countBanner.setColor(parseColor(Settings.COLOR_BOARD, 1f));
        } else if(initialSteps / 2 > getSteps() && initialSteps / 5 <= getSteps() && getSteps() > 3){
            countBanner.setScoreFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            countBanner.setColor(parseColor(Settings.COLOR_RED_100, 1f));
        } else if(initialSteps / 5 > getSteps() && getSteps() > 3){
            countBanner.setScoreFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            countBanner.setColor(parseColor(Settings.COLOR_RED_200, 1f));
        } else if(getSteps() <= 3 || initialSteps / 5 > getSteps()){
            countBanner.setScoreFontColor(parseColor(Settings.COLOR_WHITE, 1f));
            countBanner.setColor(parseColor(Settings.COLOR_RED_300, 1f));
        }
    }

    private void updateLeftBannerColor(){
        if(initialLeft / 2 <= getLeft() && getLeft() > 3){
            checksLeftBanner.setScoreFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            checksLeftBanner.setColor(parseColor(Settings.COLOR_BOARD, 1f));
        } else if(initialLeft / 2 > getLeft() && initialLeft / 5 <= getLeft() && getLeft() > 3){
            checksLeftBanner.setScoreFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            checksLeftBanner.setColor(parseColor(Settings.COLOR_GREEN_100, 1f));
        } else if(initialLeft / 5 > getLeft() && getLeft() > 3){
            checksLeftBanner.setScoreFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            checksLeftBanner.setColor(parseColor(Settings.COLOR_GREEN_200, 1f));
        } else if(getLeft() <= 3 || initialLeft / 5 < getLeft()){
            checksLeftBanner.setScoreFontColor(parseColor(Settings.COLOR_WHITE, 1f));
            checksLeftBanner.setColor(parseColor(Settings.COLOR_GREEN_400, 1f));
        }
    }

    private ArrayList<Integer> getPositionsArrayList(){
        int[] arrayPositions = new int[]{0,1,2,3,
                                         3,0,1,2,
                                         2,3,0,1,
                                         1,2,3,-1};


        ArrayList <Integer> positionsArrayList = new ArrayList<Integer>();
        for (int i = 0; i < arrayPositions.length; i++) {
            if(arrayPositions[i] == -1) {
                positionsArrayList.add(null);
            } else {
                positionsArrayList.add(arrayPositions[i]);
            }
        }
        return positionsArrayList;
    }

    public ArrayList<Integer> getRandomColor(int numberOfColors){

        float random;
        boolean exit = false;
        int colorNumber = 0;
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int i = 0; i < 4; i++){

            while(!exit ){
                random = MathUtils.random(0,15);
                if(random < 10){
                    colorNumber = 0;
                } else if(random >= 10 && random < 20){
                    colorNumber = 1;
                } else if(random >= 20 && random < 30){
                    colorNumber = 2;
                } else if(random >= 30 && random < 40){
                    colorNumber = 3;
                } else if(random >= 40 && random < 50){
                    colorNumber = 4;
                } else if(random >= 50 && random < 60){
                    colorNumber = 5;
                } else if(random >= 60 && random < 70){
                    colorNumber = 6;
                } else if(random >= 70 && random < 80){
                    colorNumber = 7;
                } else if(random >= 80 && random < 90){
                    colorNumber = 8;
                } else if(random >= 90 && random < 100){
                    colorNumber = 9;
                } else if(random >= 100 && random < 110){
                    colorNumber = 10;
                } else if(random >= 110 && random < 120){
                    colorNumber = 11;
                } else if(random >= 120 && random < 130){
                    colorNumber = 12;
                } else if(random >= 130 && random < 140){
                    colorNumber = 13;
                } else if(random >= 140 && random <= 150){
                    colorNumber = 14;
                }

                exit = true;
                for (int j = 0; j < colors.size(); j++){
                    if(colorNumber == colors.get(j)){
                        exit = false;
                    }
                }
            }

            colors.add(colorNumber);
        }

        return colors;
    }

    public Board getBoard(){
        return board;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
        checksLeftBanner.setText(left + "");
    }

    public void restLeft() {
        left--;
        checksLeftBanner.setText(left + "");
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
        initialSteps = steps;
        countBanner.setText(steps + "");
    }

    public void addStep() {
        steps++;
        countBanner.setText(steps + "");
    }

    public void restStep() {
        steps--;
        countBanner.setText(steps + "");
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
        scoreBanner.setText(score + "");
    }

    public void addScore(int score){
        this.score += score;
        scoreBanner.setText(score + "");
    }

    public void restScore(int score){
        this.score -= score;
        scoreBanner.setText(score + "");
    }

    public boolean isRunning() {
        return ButtonsGame.gameState == GameState.RUNNING;
    }

    public ArrayList<MenuButton> getMenuButtons(){
        return menuButtons;
    }

    public void goToHomeScreen() {
        backButton.toHomeScreen(0.6f, 0.1f);
        topWLayer.fadeIn(0.6f, .1f);
    }
}
