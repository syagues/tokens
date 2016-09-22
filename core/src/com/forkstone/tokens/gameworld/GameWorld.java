package com.forkstone.tokens.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Configuration;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.Background;
import com.forkstone.tokens.gameobjects.Board;
import com.forkstone.tokens.gameobjects.Board33;
import com.forkstone.tokens.gameobjects.Board34;
import com.forkstone.tokens.gameobjects.Board44;
import com.forkstone.tokens.gameobjects.Board45;
import com.forkstone.tokens.gameobjects.Board55;
import com.forkstone.tokens.gameobjects.ColorButton;
import com.forkstone.tokens.gameobjects.Counter;
import com.forkstone.tokens.gameobjects.GameObject;
import com.forkstone.tokens.gameobjects.GameOver;
import com.forkstone.tokens.gameworld.levels.Levels;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.helpers.FlatColors;
import com.forkstone.tokens.tweens.SpriteAccessor;
import com.forkstone.tokens.ui.InfoBanner;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;
import com.forkstone.tokens.ui.Timer;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;


/**
 * Created by sergi on 1/12/15.
 */

public class GameWorld {

    private static final String TAG = "GameWorld";

    // SETTINGS
    private Settings settings;

    public final float w;
    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public GameWorld world = this;

    //GAME CAMERA
    private GameCam camera;

    //VARIABLES
    private GameState gameState;
    private int level, steps, initialSteps, left, initialLeft, memoryCountdown,
            movableCountdown, colorChangeCountdown, blockedChangeCountdown;
    private float time;
    private boolean restart = false;
    private boolean countSound = false, firstCountSound = false;
    private static boolean videoAdActive = true;
    private boolean memoryActive = false;

    //GAMEOBJECTS
    private Board board;
    private Background background, drawedBack;
    private static Background topWLayer;
    private InfoBanner levelBanner, countBanner, checksLeftBanner;
    private MenuButton backButton, replayButton, lightningButton, multicolorButton;
    private Counter lightningCounter, multicolorCounter, memoryCountdownCounter;
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private static GameOver gameOver;
    private Text countdownText;
    private GameObject chronometer, auxBoard;
    // TOKENS
    private ArrayList<Integer> tokens = new ArrayList<Integer>();
    private ArrayList<Integer> gameOverTokensOrder = new ArrayList<Integer>();

    // LEVELS
    private Levels levels;

    // TIMER
    private Timer timer, countdown;
    private boolean countBannerAnimation = false;

    // MULTICOLOR HELP
    private boolean multicolor = false;

    public enum GameType {
        NORMAL, TIME
    }

    public enum LevelType {
        NORMAL, MEMORY, MOVABLE, COLORCHANGE, BLOCKEDCHANGE
    }

    private GameType gameType;
    private LevelType levelType;

    public GameWorld(ButtonsGame game, ActionResolver actionResolver, float gameWidth,
                     float gameHeight, float worldWidth, float worldHeight, int level) {

        settings = new Settings();
        this.gameWidth = gameWidth;
        this.w = gameHeight / 100;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.game = game;
        this.actionResolver = actionResolver;
        this.level = level;
        timer = new Timer(this, false);
        levels = new Levels(this, level);

        if(AssetLoader.getAds()){
            actionResolver.viewAd(false);
        }else{
            actionResolver.viewAd(true);
        }

        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, getDistrictBackground(level), world.parseColor(Settings.COLOR_WHITE, 1f));

        if(level != 0){

            startGame();

            //GAME OVER
            gameOver = new GameOver(world, 0, 0, world.gameWidth, world.gameHeight, AssetLoader.square,
                    FlatColors.WHITE);

            // Countdown
            countdown = new Timer(this, true);
            countdown.start(3f, 1f, 2f, 0f);
            countdownText = new Text(world, gameWidth/2 - 250, gameHeight/2 - 100,
                    500, 200, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                    countdown.getTimeFormatted(), AssetLoader.font, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 30,
                    Align.center);
            chronometer = new GameObject(this, gameWidth/2 - 200, gameHeight/2 - 190, 400, 440, AssetLoader.chronometer, Color.WHITE);
        }
    }

    public void update(float delta) {
        // Board
        auxBoard.update(delta);
        board.update(delta);
        // Buttons
        backButton.update(delta);
        replayButton.update(delta);
        lightningButton.update(delta);
        multicolorButton.update(delta);
        // Counters
        lightningCounter.update(delta);
        multicolorCounter.update(delta);
        // Banners
        checksLeftBanner.update(delta);
        levelBanner.update(delta);
        countBanner.update(delta);
        topWLayer.update(delta);
        // GameOver
        gameOver.update(delta);
        // Countdown
        countdown.update(delta);
        countdownText.update(delta);
        chronometer.update(delta);
        // Memory Countdown
        if(isMemoryLevel()) memoryCountdownCounter.update(delta);

        // Timer
        timer.update(delta);
        // Banners Color
        if(!isGameOver()){
            updateLeftBannerColor();
            if(isTimeGame()) {
                updateTimeBannerColor();
                countdownText.setText(countdown.getTimeFormatted());
                if(countdown.getTimeFormatted().equals("3") && !firstCountSound){
                    if(AssetLoader.getSoundState()) AssetLoader.soundClock.play();
                    firstCountSound = true;
                }
            } else if(isNormalGame()) {
                updateStepsBannerColor();
            }
        }
    }

    public void render(SpriteBatch batcher, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        background.render(batcher, shapeRenderer, fontShader, objectShader);
        drawedBack.render(batcher, shapeRenderer, fontShader, objectShader);
        // Board
        auxBoard.render(batcher, shapeRenderer, fontShader, objectShader);
        board.render(batcher, shapeRenderer, fontShader, objectShader);
        // Buttons
        backButton.render(batcher, shapeRenderer, fontShader, objectShader);
        replayButton.render(batcher, shapeRenderer, fontShader, objectShader);
        lightningButton.render(batcher, shapeRenderer, fontShader, objectShader);
        multicolorButton.render(batcher, shapeRenderer, fontShader, objectShader);
        // Counters
        lightningCounter.render(batcher, shapeRenderer, fontShader, objectShader);
        multicolorCounter.render(batcher, shapeRenderer, fontShader, objectShader);
        // Banners
        checksLeftBanner.render(batcher, shapeRenderer, fontShader, objectShader);
        levelBanner.render(batcher, shapeRenderer, fontShader, objectShader);
        countBanner.render(batcher, shapeRenderer, fontShader, objectShader);
        // Memory Countdown
        if(isMemoryLevel()) memoryCountdownCounter.render(batcher, shapeRenderer, fontShader, objectShader);
        topWLayer.render(batcher, shapeRenderer, fontShader, objectShader);
        // GameOver
        if(isGameOver()) {gameOver.render(batcher, shapeRenderer, fontShader, objectShader);}
        // Countdown
        if(countdown.getTime() > 1f && isTimeGame()) {
            chronometer.render(batcher, shapeRenderer, fontShader, objectShader);
            countdownText.render(batcher, shapeRenderer, fontShader, objectShader);
        }

        if (Settings.DEBUG) {
            batcher.setShader(fontShader);
            AssetLoader.fontB.draw(batcher, "MANUEL", 0, gameWidth / 2, gameWidth, Align.center, true);
            batcher.setShader(null);
        }
    }

    public void startGame() {
        AssetLoader.soundClock.stop();
        setUpItems();
        beginGame();
        ButtonsGame.gameState = GameState.RUNNING;
        // Tracking
        actionResolver.setTrackerScreenName("GameScreen: Level " + level);
        // Sounds
        countSound = firstCountSound = false;

        // Movable
        if(isMovableLevel()) {
            setMovableCountdown(levels.getMovableSteps());
        }

        // Color Change
        if(isColorChangeLevel()) {
            setColorChangeCountdown(levels.getColorChangeSteps());
        }
    }

    public void beginGame() {
        topWLayer.fadeOut(0.5f, getDelay(0f));
        startEffects(0);
    }

    public void finishGame(boolean gameComplete) {
        finishEffect(gameComplete);
        saveScoreLogic(gameComplete);
        ButtonsGame.gameState = GameState.GAMEOVER;
        setGameOverTokensOrder();
        gameOver.start(gameComplete);
        timer.finish();
        fadeOutItems();
        // Sounds
        firstCountSound = false;

        // Music
        if(AssetLoader.getSoundState()){
            if (gameComplete){
                if(AssetLoader.getSoundState()) AssetLoader.soundWin.play();
                AssetLoader.soundClock.stop();
            } else {
                if(AssetLoader.getSoundState()) AssetLoader.soundLose.play();
            }
        }

        // Attempts
        if(gameComplete){
            AssetLoader.setAttempt(0);
        } else {
            AssetLoader.addAttempt();
        }
    }

    public void restartGame() {
        restart = true;
        //setBanners();
        setCountBanner();
        getBoard().reset();
        // Sounds
        countSound = firstCountSound = false;

        // Memory
        if(isMemoryLevel()){
            setMemoryCountdown(levels.getMemoryVisibleSteps());
            memoryActive = !levels.getMemoryInitialVisibility();
        }

        // Movable
        if(isMovableLevel()){
            setMovableCountdown(levels.getMovableSteps());
        }

        // Color Change
        if(isColorChangeLevel()) {
            setColorChangeCountdown(levels.getColorChangeSteps());
        }
    }

    public void startMoreGame() {
        restart = true;
        fadeInItems();
        if(isTimeGame()){
            timer.start(10f, 0, 10f, 1.5f);
        } else {
            setSteps(3);
        }

        getBoard().setCheckSpritesOff();
        getBoard().setupBoard(gameOverTokensOrder);
        getBoard().startGame();

        topWLayer.fadeOut(.5f, getDelay(.5f));
        startEffects(0f);

        ButtonsGame.gameState = GameState.RUNNING;

        // Sounds
        countSound = firstCountSound = false;

        // Memory
        if(isMemoryLevel()){
            setMemoryCountdown(3);
            memoryActive = false;
        }

        // Movable
        if(isMovableLevel()){
            if(levels.getMovableSteps() >= 3){
                setMovableCountdown(3);
            } else {
                setMovableCountdown(levels.getMovableSteps());
            }
        }

        // Color Change
        if(isColorChangeLevel()) {
            if(levels.getColorChangeSteps() >= 3) {
                setColorChangeCountdown(3);
            } else {
                setColorChangeCountdown(levels.getColorChangeSteps());
            }
        }
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
        checksLeftBanner = new InfoBanner(world,
                100, bannersAndButtonsPositionSmartphone("banner"),
                gameWidth / 2 - 310, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_LEFTBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_WHITE, 1f), 0);

        levelBanner = new InfoBanner(world,
                gameWidth / 2 - 95, bannersAndButtonsPositionSmartphone("banner") + 65,
                gameWidth / 2 - 310, 160,
                AssetLoader.banner,
                getLevelColor(), Settings.GAME_LEVELBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_WHITE, 1f),
                world.parseColor(Settings.COLOR_WHITE, 1f), 1);
        setLevel(level);

        countBanner = new InfoBanner(world,
                gameWidth - 310, bannersAndButtonsPositionSmartphone("banner"),
                gameWidth / 2 - 300, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_WHITE, 1f), 0);
        // TypeGame
        setCountBanner();

        if(isMemoryLevel()){
            memoryCountdownCounter = new Counter(this,
                    gameWidth - 345, bannersAndButtonsPositionSmartphone("banner") - 45,
                    75, 75, AssetLoader.fontXS, parseColor(Settings.COLOR_WHITE, 1f), "memoryCountdown", true,
                    parseColor(Settings.COLOR_GREEN_500, 1f), 16f);
            setMemoryCountdown(levels.getMemoryVisibleSteps());
            memoryActive = !levels.getMemoryInitialVisibility();
        }


        if(isTimeGame()){
            Tween.to(countBanner.getSprite(), com.forkstone.tokens.tweens.SpriteAccessor.SCALE, .4f).target(0.1f)
                    .target(.95f, 1f)
                    .repeatYoyo(1000, 0f)
                    .ease(TweenEquations.easeInOutSine).start(countBanner.getManager());
        }
    }

    private void setUpButtonsSmartphone() {
        backButton = new MenuButton(world,
                100, bannersAndButtonsPositionSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        replayButton = new MenuButton(world,
                gameWidth / 2 - 185, bannersAndButtonsPositionSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        lightningButton = new MenuButton(world,
                gameWidth / 2 + 65, bannersAndButtonsPositionSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.lightningButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        multicolorButton = new MenuButton(world,
                gameWidth - 225, bannersAndButtonsPositionSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.multicolorButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));

        menuButtons.add(backButton);
        menuButtons.add(replayButton);
        menuButtons.add(lightningButton);
        menuButtons.add(multicolorButton);

        lightningCounter = new Counter(world,
                gameWidth / 2 + Settings.GAME_BUTTON_SIZE + 15, bannersAndButtonsPositionSmartphone("button") + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_WHITE, 1f), "resol", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorCounter = new Counter(world,
                gameWidth + Settings.GAME_BUTTON_SIZE - 275, bannersAndButtonsPositionSmartphone("button") + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_WHITE, 1f), "multicolor", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);
    }

    private void setUpBoardSmartphone() {
        if(levels.getBoardSize() == 33) {
            initialLeft = 8;
            board = new Board33(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board33, 3, 3, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 90, gameWidth - 80, gameWidth - 70, AssetLoader.board33, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 34) {
            initialLeft = 11;
            board = new Board34(this, 40, gameHeight / 2 - (gameWidth / 2) + 100,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board34, 3, 4, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 100, gameWidth - 80, gameWidth - 70, AssetLoader.board34, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 44) {
            initialLeft = 15;
            board = new Board44(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board44, 4, 4, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 90, gameWidth - 80, gameWidth - 70, AssetLoader.board44, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 45) {
            initialLeft = 19;
            board = new Board45(this, 40, gameHeight / 2 - (gameWidth / 2) + 100,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board45, 4, 5, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 100, gameWidth - 80, gameWidth - 70, AssetLoader.board45, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 55) {
            initialLeft = 24;
            board = new Board55(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board55, 5, 5, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 90, gameWidth - 80, gameWidth - 70, AssetLoader.board55, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        }
        board.fadeIn(0.5f, getDelay(1f));
        setLeft(initialLeft);
    }

    public float bannersAndButtonsPositionSmartphone(String itemTypeName) {

        // Board Ample
        if(levels.getBoardSize() == 34 || levels.getBoardSize() == 45) {
            if(itemTypeName.equals("banner")) {
                return world.gameHeight - 300;
            } else if(itemTypeName.equals("button")) {
                return gameHeight / 3 - 265;
            }
        } else {
            // Board Cuadrat
            if (itemTypeName.equals("banner")) {
                return world.gameHeight - 160 - 90;
            } else if(itemTypeName.equals("button")) {
                return gameHeight / 3 - 345;
            }
        }

        return 0;
    }

    // ----------- TABLET 16/10 ------------
    private void setUpInfoBannersTablet1610() {
        checksLeftBanner = new InfoBanner(world,
                100, bannersAndButtonsPositionTablet1610("banner"),
                gameWidth / 2 - 310, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_LEFTBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_WHITE, 1f), 0);

        levelBanner = new InfoBanner(world,
                gameWidth / 2 - 95, bannersAndButtonsPositionTablet1610("banner") + 65,
                gameWidth / 2 - 310, 160,
                AssetLoader.banner,
                getLevelColor(), Settings.GAME_LEVELBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_WHITE, 1f),
                world.parseColor(Settings.COLOR_WHITE, 1f), 1);
        setLevel(level);

        countBanner = new InfoBanner(world,
                gameWidth - 310, bannersAndButtonsPositionTablet1610("banner"),
                gameWidth / 2 - 300, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_WHITE, 1f), 0);
        // TypeGame
        setCountBanner();

        if(isMemoryLevel()){
            memoryCountdownCounter = new Counter(this,
                    gameWidth - 345, bannersAndButtonsPositionTablet1610("memoryCounter"),
                    75, 75, AssetLoader.fontXS, parseColor(Settings.COLOR_WHITE, 1f), "memoryCountdown", true,
                    parseColor(Settings.COLOR_GREEN_500, 1f), 16f);
            setMemoryCountdown(levels.getMemoryVisibleSteps());
            memoryActive = !levels.getMemoryInitialVisibility();
        }

        if(isTimeGame()){
            Tween.to(countBanner.getSprite(), SpriteAccessor.SCALE, .4f).target(0.1f)
                    .target(.95f, 1f)
                    .repeatYoyo(1000, 0f)
                    .ease(TweenEquations.easeInOutSine).start(countBanner.getManager());
        }
    }

    private void setUpButtonsTablet1610() {
        backButton = new MenuButton(world,
                100, bannersAndButtonsPositionTablet1610("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        replayButton = new MenuButton(world,
                gameWidth / 2 - 185, bannersAndButtonsPositionTablet1610("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        lightningButton = new MenuButton(world,
                gameWidth / 2 + 65, bannersAndButtonsPositionTablet1610("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.lightningButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        multicolorButton = new MenuButton(world,
                gameWidth - 225, bannersAndButtonsPositionTablet1610("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.multicolorButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));

        menuButtons.add(backButton);
        menuButtons.add(replayButton);
        menuButtons.add(lightningButton);
        menuButtons.add(multicolorButton);

        lightningCounter = new Counter(world,
                gameWidth / 2 + Settings.GAME_BUTTON_SIZE + 15, bannersAndButtonsPositionTablet1610("button") + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_WHITE, 1f), "resol", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorCounter = new Counter(world,
                gameWidth + Settings.GAME_BUTTON_SIZE - 275, bannersAndButtonsPositionTablet1610("button") + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_WHITE, 1f), "multicolor", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);
    }

    private void setUpBoardTablet1610() {
        if(levels.getBoardSize() == 33) {
            initialLeft = 8;
            board = new Board33(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board33, 3, 3, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 90, gameWidth - 80, gameWidth - 70, AssetLoader.board33, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 34) {
            initialLeft = 11;
            board = new Board34(this, 40, gameHeight / 2 - (gameWidth / 2) + 100,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board34, 3, 4, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 100, gameWidth - 80, gameWidth - 70, AssetLoader.board34, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 44) {
            initialLeft = 15;
            board = new Board44(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board44, 4, 4, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 90, gameWidth - 80, gameWidth - 70, AssetLoader.board44, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 45) {
            initialLeft = 19;
            board = new Board45(this, 40, gameHeight / 2 - (gameWidth / 2) + 100,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board45, 4, 5, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 100, gameWidth - 80, gameWidth - 70, AssetLoader.board45, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 55) {
            initialLeft = 24;
            board = new Board55(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board55, 5, 5, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 90, gameWidth - 80, gameWidth - 70, AssetLoader.board55, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        }
        board.fadeIn(0.5f, getDelay(1f));
        setLeft(initialLeft);
    }

    public float bannersAndButtonsPositionTablet1610(String itemTypeName) {

        // Board Ample
        if(levels.getBoardSize() == 34 || levels.getBoardSize() == 45) {
            if(itemTypeName.equals("banner")) {
                return gameHeight - 300;
            } else if(itemTypeName.equals("button")) {
                return gameHeight / 3 - 265;
            } else if(itemTypeName.equals("memoryCounter")) {
                return gameHeight - 300 - 45;
            }
        } else {
            // Board Cuadrat
            if (itemTypeName.equals("banner")) {
                return gameHeight - 160 - 60;
            } else if(itemTypeName.equals("button")) {
                return gameHeight / 3 - 345;
            } else if(itemTypeName.equals("memoryCounter")) {
                return gameHeight - 220 - 45;
            }
        }

        return 0;
    }

    // ----------- SMALL SMARTPHONE ------------
    private void setUpInfoBannersSmallSmartphone() {
        checksLeftBanner = new InfoBanner(world,
                100, bannersAndButtonsPositionSmallSmartphone("banner"),
                gameWidth / 2 - 310, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_LEFTBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_WHITE, 1f), 0);

        levelBanner = new InfoBanner(world,
                gameWidth / 2 - 105, bannersAndButtonsPositionSmallSmartphone("banner") + 65,
                gameWidth / 2 - 300, 160,
                AssetLoader.banner,
                getLevelColor(), Settings.GAME_LEVELBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_WHITE, 1f),
                world.parseColor(Settings.COLOR_WHITE, 1f), 1);
        setLevel(level);

        countBanner = new InfoBanner(world,
                gameWidth - 310, bannersAndButtonsPositionSmallSmartphone("banner"),
                gameWidth / 2 - 300, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_WHITE, 1f), 0);
        // TypeGame
        setCountBanner();

        if(isMemoryLevel()){
            memoryCountdownCounter = new Counter(this,
                    gameWidth - 345, bannersAndButtonsPositionSmallSmartphone("memoryCounter"),
                    75, 75, AssetLoader.fontXS, parseColor(Settings.COLOR_WHITE, 1f), "memoryCountdown", true,
                    parseColor(Settings.COLOR_GREEN_500, 1f), 16f);
            setMemoryCountdown(levels.getMemoryVisibleSteps());
            memoryActive = !levels.getMemoryInitialVisibility();
        }

        if(isTimeGame()){
            Tween.to(countBanner.getSprite(), SpriteAccessor.SCALE, .4f).target(0.1f)
                    .target(.95f, 1f)
                    .repeatYoyo(1000, 0f)
                    .ease(TweenEquations.easeInOutSine).start(countBanner.getManager());
        }
    }

    private void setUpButtonsSmallSmartphone() {
        backButton = new MenuButton(world,
                100, bannersAndButtonsPositionSmallSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        replayButton = new MenuButton(world,
                gameWidth / 2 - 185, bannersAndButtonsPositionSmallSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        lightningButton = new MenuButton(world,
                gameWidth / 2 + 65, bannersAndButtonsPositionSmallSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.lightningButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        multicolorButton = new MenuButton(world,
                gameWidth - 225, bannersAndButtonsPositionSmallSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.multicolorButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));

        menuButtons.add(backButton);
        menuButtons.add(replayButton);
        menuButtons.add(lightningButton);
        menuButtons.add(multicolorButton);

        lightningCounter = new Counter(world,
                gameWidth / 2 + Settings.GAME_BUTTON_SIZE + 15, bannersAndButtonsPositionSmallSmartphone("button") + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_WHITE, 1f), "resol", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorCounter = new Counter(world,
                gameWidth + Settings.GAME_BUTTON_SIZE - 275, bannersAndButtonsPositionSmallSmartphone("button") + Settings.GAME_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_WHITE, 1f), "multicolor", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);
    }

    private void setUpBoardSmallSmartphone() {
        if(levels.getBoardSize() == 33) {
            initialLeft = 8;
            board = new Board33(this, 40, gameHeight / 2 - (gameWidth / 2) + 20,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board33, 3, 3, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 20, gameWidth - 80, gameWidth - 70, AssetLoader.board33, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 34) {
            initialLeft = 11;
            board = new Board34(this, 40, gameHeight / 2 - (gameWidth / 2) + 20,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board34, 3, 4, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 20, gameWidth - 80, gameWidth - 70, AssetLoader.board34, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 44) {
            initialLeft = 15;
            board = new Board44(this, 40, gameHeight / 2 - (gameWidth / 2) + 20,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board44, 4, 4, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 20, gameWidth - 80, gameWidth - 70, AssetLoader.board44, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 45) {
            initialLeft = 19;
            board = new Board45(this, 40, gameHeight / 2 - (gameWidth / 2) + 20,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board45, 4, 5, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 20, gameWidth - 80, gameWidth - 70, AssetLoader.board45, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 55) {
            initialLeft = 24;
            board = new Board55(this, 40, gameHeight / 2 - (gameWidth / 2) + 20,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board55, 5, 5, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 20, gameWidth - 80, gameWidth - 70, AssetLoader.board55, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        }
        board.fadeIn(0.5f, getDelay(1f));
        setLeft(initialLeft);
    }

    public float bannersAndButtonsPositionSmallSmartphone(String itemTypeName) {

        // Board Ample
        if(levels.getBoardSize() == 34 || levels.getBoardSize() == 45) {
            if(itemTypeName.equals("banner")) {
                return gameHeight - 300;
            } else if(itemTypeName.equals("button")) {
                return 160;
            } else if(itemTypeName.equals("memoryCounter")) {
                return gameHeight - 300 - 45;
            }
        } else {
            // Board Cuadrat
            if (itemTypeName.equals("banner")) {
                return gameHeight - 240;
            } else if(itemTypeName.equals("button")) {
                return 90;
            } else if(itemTypeName.equals("memoryCounter")) {
                return gameHeight - 285;
            }
        }

        return 0;
    }

    // ----------- TABLET 4/3 ------------
    private void setUpInfoBannersTablet43() {
        checksLeftBanner = new InfoBanner(world,
                150, bannersAndButtonsPositionTablet43("banner"),
                gameWidth / 2 - 350, 120,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_LEFTBANNER_TEXT,
                AssetLoader.fontXXXS, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_WHITE, 1f), 0);

        levelBanner = new InfoBanner(world,
                gameWidth / 2 - 90, bannersAndButtonsPositionTablet43("banner") + 65,
                gameWidth / 2 - 360, 120,
                AssetLoader.banner,
                getLevelColor(), Settings.GAME_LEVELBANNER_TEXT,
                AssetLoader.fontXXXS, AssetLoader.fontXS, parseColor(Settings.COLOR_WHITE, 1f),
                world.parseColor(Settings.COLOR_WHITE, 1f), 1);
        setLevel(level);

        countBanner = new InfoBanner(world,
                gameWidth - 340, bannersAndButtonsPositionTablet43("banner"),
                gameWidth / 2 - 350, 120,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXXXS, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_WHITE, 1f), 0);
        // TypeGame
        setCountBanner();

        if(isMemoryLevel()){
            memoryCountdownCounter = new Counter(this,
                    gameWidth - 365, bannersAndButtonsPositionTablet43("banner") - 40,
                    50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_WHITE, 1f), "memoryCountdown", true,
                    parseColor(Settings.COLOR_GREEN_500, 1f), 10f);
            setMemoryCountdown(levels.getMemoryVisibleSteps());
            memoryActive = !levels.getMemoryInitialVisibility();
        }

        if(isTimeGame()){
            Tween.to(countBanner.getSprite(), SpriteAccessor.SCALE, .4f).target(0.1f)
                    .target(.95f, 1f)
                    .repeatYoyo(1000, 0f)
                    .ease(TweenEquations.easeInOutSine).start(countBanner.getManager());
        }
    }

    private void setUpButtonsTablet43() {
        backButton = new MenuButton(world,
                150, bannersAndButtonsPositionTablet43("button"),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        replayButton = new MenuButton(world,
                gameWidth / 2 - 180, bannersAndButtonsPositionTablet43("button"),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        lightningButton = new MenuButton(world,
                gameWidth / 2 + 55, bannersAndButtonsPositionTablet43("button"),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.lightningButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        multicolorButton = new MenuButton(world,
                gameWidth - 250, bannersAndButtonsPositionTablet43("button"),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.multicolorButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));

        menuButtons.add(backButton);
        menuButtons.add(replayButton);
        menuButtons.add(lightningButton);
        menuButtons.add(multicolorButton);

        lightningCounter = new Counter(world,
                gameWidth / 2 + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 20, bannersAndButtonsPositionTablet43("button") + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 - 30,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_WHITE, 1f), "resol", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorCounter = new Counter(world,
                gameWidth + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 - 280, bannersAndButtonsPositionTablet43("button") + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 - 30,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_WHITE, 1f), "multicolor", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);
    }

    private void setUpBoardTablet43() {
        if(levels.getBoardSize() == 33) {
            initialLeft = 8;
            board = new Board33(this, 100, gameHeight / 2 - (gameWidth / 2) + 130,
                    gameWidth - 200, gameWidth - 175,
                    AssetLoader.board33, 3, 3, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 100, gameHeight / 2 - (gameWidth / 2) + 130, gameWidth - 200, gameWidth - 175, AssetLoader.board33, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 34) {
            initialLeft = 11;
            board = new Board34(this, 40, gameHeight / 2 - (gameWidth / 2) + 80,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board34, 3, 4, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 80, gameWidth - 80, gameWidth - 70, AssetLoader.board34, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 44) {
            initialLeft = 15;
            board = new Board44(this, 100, gameHeight / 2 - (gameWidth / 2) + 130,
                    gameWidth - 200, gameWidth - 175,
                    AssetLoader.board44, 4, 4, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 100, gameHeight / 2 - (gameWidth / 2) + 130, gameWidth - 200, gameWidth - 175, AssetLoader.board44, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 45) {
            initialLeft = 19;
            board = new Board45(this, 40, gameHeight / 2 - (gameWidth / 2) + 80,
                    gameWidth - 80, gameWidth - 70,
                    AssetLoader.board45, 4, 5, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 80, gameWidth - 80, gameWidth - 70, AssetLoader.board45, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        } else if(levels.getBoardSize() == 55) {
            initialLeft = 24;
            board = new Board55(this, 100, gameHeight / 2 - (gameWidth / 2) + 130,
                    gameWidth - 200, gameWidth - 175,
                    AssetLoader.board55, 5, 5, levels.getPositionsArrayList(), levels.getStaticPositionsArray(), levels.getSameColorNumber());
            auxBoard = new GameObject(this, 100, gameHeight / 2 - (gameWidth / 2) + 130, gameWidth - 200, gameWidth - 175, AssetLoader.board55, parseColor(Settings.COLOR_BOARD, 1f));
            auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, getDelay(0f));
        }
        board.fadeIn(0.5f, getDelay(1f));
        setLeft(initialLeft);
    }

    public float bannersAndButtonsPositionTablet43(String itemTypeName) {

        // Board Ample
        if(levels.getBoardSize() == 34 || levels.getBoardSize() == 45) {
            if(itemTypeName.equals("banner")) {
                return world.gameHeight - 200;
            } else if(itemTypeName.equals("button")) {
                return gameHeight / 3 - 285;
            }
        } else {
            // Board Cuadrat
            if (itemTypeName.equals("banner")) {
                return world.gameHeight - 170;
            } else if(itemTypeName.equals("button")) {
                return gameHeight / 3 - 320;
            }
        }

        return 0;
    }
    // ------------------------------------------------------

    public void fadeInLayer(){
        topWLayer.fadeIn(0.2f, 0f);
    }

    private void fadeInItems(){
        checksLeftBanner.effectsIn();
        levelBanner.effectsIn();
        levelBanner.setColor(getLevelColor());
        countBanner.effectsIn();
        if(isMemoryLevel()) memoryCountdownCounter.effectsIn();

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
        levelBanner.effectsOut();
        countBanner.effectsOut();
        if (isMemoryLevel()) memoryCountdownCounter.effectsOut();

        auxBoard.fadeOut(.1f, 0f);
        board.fadeOut(0.3f, 0f);
        for (Counter counter : getBoard().getMovableCountdownCounters()){
            counter.fadeOut(0.2f, 0f);
        }

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
        if (gameComplete) levelBanner.effectWin();
        else levelBanner.effectLose();
    }

    private void startEffects(float extraDelay){
        checksLeftBanner.effectX(-300, checksLeftBanner.getPosition().x, 0.6f, getDelay(.2f) + extraDelay);
        levelBanner.effectY(worldHeight + 200, levelBanner.getPosition().y, 0.8f, getDelay(0.3f) + extraDelay);
        countBanner.effectX(world.gameWidth, countBanner.getPosition().x, 0.6f, getDelay(.2f) + extraDelay);
        if (isMemoryLevel()) memoryCountdownCounter.effectX(world.gameWidth, memoryCountdownCounter.getPosition().x, 0.6f, getDelay(.2f) + extraDelay);

        backButton.effectX(-300, backButton.getPosition().x, 0.6f, getDelay(0.2f) + extraDelay);
        replayButton.effectY(-100, replayButton.getPosition().y, 0.6f, getDelay(0.2f) + extraDelay);
        lightningButton.effectY(-100, lightningButton.getPosition().y, 0.6f, getDelay(0.2f) + extraDelay);
        multicolorButton.effectX(world.gameWidth, multicolorButton.getPosition().x, 0.6f, getDelay(0.2f) + extraDelay);
        lightningCounter.effectY(-100, lightningCounter.getPosition().y, 0.6f, getDelay(0.2f) + extraDelay);
        multicolorCounter.effectX(world.gameWidth, multicolorCounter.getPosition().x, 0.6f, getDelay(0.2f) + extraDelay);
    }

    public float getDelay(float baseDelay){
        if(isTimeGame() && !restart){
            return baseDelay + 2f;
        } else {
            return baseDelay;
        }
    }

    private void updateTimeBannerColor(){
        countBanner.setText(timer.getTimeFormatted());
        if(timer.getDuration() / 2 <= timer.getTime() && timer.getTime() > 3){
            countBanner.setScoreFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            countBanner.setColor(parseColor(Settings.COLOR_BOARD, 1f));
        } else if(timer.getDuration() / 2 > timer.getTime() && timer.getDuration() / 3 <= timer.getTime() && timer.getTime() > 3){
            countBanner.setScoreFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            countBanner.setColor(parseColor(Settings.COLOR_RED_100, 1f));
        } else if(timer.getDuration() / 5 > timer.getTime() && timer.getTime() > 3){
            countBanner.setScoreFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
            countBanner.setColor(parseColor(Settings.COLOR_RED_200, 1f));
        } else if(timer.getTime() <= 3 || timer.getDuration() / 10f > timer.getTime()){
            countBanner.setScoreFontColor(parseColor(Settings.COLOR_WHITE, 1f));
            countBanner.setColor(parseColor(Settings.COLOR_RED_300, 1f));
            if(!countSound){
                if(AssetLoader.getSoundState()){
                    AssetLoader.soundClock.play();
                    countSound = true;
                }
            }
        }
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

    private void setGameOverTokensOrder(){
        gameOverTokensOrder.clear();
        for (int i = 0; i < getBoard().getColorButtons().size()+1; i++){
            if(getBoard().getColorButtonByTablePosition(i) != null){
                if(getBoard().getColorButtonByTablePosition(i).isMulticolor()){
                    gameOverTokensOrder.add(-1);
                } else {
                    gameOverTokensOrder.add(getBoard().getColorButtonByTablePosition(i).getType());
                }
            } else {
                gameOverTokensOrder.add(null);
            }
        }
    }

    public void setMulticolorUp(){
        multicolor = true;
    }

    public void setMulticolorDown(){
        if(!restart){
            getBoard().checkOkSprites();
            getBoard().checkGameComplete();
        }
        multicolor = false;
    }

    private void saveScoreLogic(boolean gameComplete) {
        // GAMES PLAYED ACHIEVEMENTS!
        if (level > AssetLoader.getLevel()) {
            actionResolver.submitLevel(level);
        }
        if(gameComplete) {checkAchievements();}
    }

    private void checkAchievements() {
        if (actionResolver.isSignedIn()) {
            if(actionResolver.getPlatform().equals(Settings.PLATFORM_ANDROID)) {

                if (level == 1) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_TOKEN);
                if (level == 6) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_STATIC_TOKEN);
                if (level == 10) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_CHRONO_TOKEN);
                if (level == 25) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_DISTRICT_ONE);
                if (level == 50) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_DISTRICT_TWO);
                if (level == 75) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_DISTRICT_THREE);
                if (level == 100) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_DISTRICT_FOUR);
                if (level == 125) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_DISTRICT_FIVE);
                if (level == 150) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_DISTRICT_SIX);
                if (level == 175) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_DISTRICT_SEVEN);
                if (level == 200) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_DISTRICT_EIGHT);
                if (level == 225) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_DISTRICT_NINE);
                if (level == 250) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_DISTRICT_TEN);
                for(int i = 0; i < Settings.MEMORY_LEVELS.length; i++) {
                    if(level == Settings.MEMORY_LEVELS[i]) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_MEMORY_LEVEL);
                }
                for(int i = 0; i < Settings.CHANGEPOSITION_LEVELS.length; i++) {
                    if(level == Settings.CHANGEPOSITION_LEVELS[i]) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_POSITIONCHANGE_LEVEL);
                }
                for(int i = 0; i < Settings.CHANGECOLOR_LEVELS.length; i++) {
                    if(level == Settings.CHANGECOLOR_LEVELS[i]) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_COLORCHANGE_LEVEL);
                }
                for(int i = 0; i < Settings.CHANGEBLOCKED_LEVELS.length; i++) {
                    if(level == Settings.CHANGEBLOCKED_LEVELS[i]) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_BLOCKEDCHANGE_LEVEL);
                }

            } else if(actionResolver.getPlatform().equals(Settings.PLATFORM_IOS)) {

                if (level == 1) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_TOKEN);
                if (level == 6) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_STATIC_TOKEN);
                if (level == 10) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_CHRONO_TOKEN);
                if (level == 25) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_DISTRICT_ONE);
                if (level == 50) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_DISTRICT_TWO);
                if (level == 75) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_DISTRICT_THREE);
                if (level == 100) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_DISTRICT_FOUR);
                if (level == 125) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_DISTRICT_FIVE);
                if (level == 150) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_DISTRICT_SIX);
                if (level == 175) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_DISTRICT_SEVEN);
                if (level == 200) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_DISTRICT_EIGHT);
                if (level == 225) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_DISTRICT_NINE);
                if (level == 250) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_DISTRICT_TEN);
                for(int i = 0; i < Settings.MEMORY_LEVELS.length; i++) {
                    if(level == Settings.MEMORY_LEVELS[i]) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_MEMORY_LEVEL);
                }
                for(int i = 0; i < Settings.CHANGEPOSITION_LEVELS.length; i++) {
                    if(level == Settings.CHANGEPOSITION_LEVELS[i]) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_POSITIONCHANGE_LEVEL);
                }
                for(int i = 0; i < Settings.CHANGECOLOR_LEVELS.length; i++) {
                    if(level == Settings.CHANGECOLOR_LEVELS[i]) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_COLORCHANGE_LEVEL);
                }
                for(int i = 0; i < Settings.CHANGEBLOCKED_LEVELS.length; i++) {
                    if(level == Settings.CHANGEBLOCKED_LEVELS[i]) actionResolver.unlockAchievementGPGS(Configuration.IOS_ACHIEVEMENT_BLOCKEDCHANGE_LEVEL);
                }
            }
        }
    }

    public GameCam getCamera() {
        return camera;
    }

    public Levels getLevels() {
        return levels;
    }

    // Level
    public int getLevel() {
        return level;
    }

    public void setLevel(int i) {
        levelBanner.setText(i + "");
    }

    // Steps
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

    // Time
    public float getTime() {
        return timer.getTime();
    }

    public void setTime(float time) {
        this.time = time;
        countBanner.setText(time + "");
    }

    // Left
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

    // Memory
    public boolean isMemoryActive(){
        return memoryActive;
    }

    public void changeMemoryState(boolean memoryActive){
        this.memoryActive = memoryActive;
        if(!memoryActive){
            levelBanner.effectMemoryInactive();
            setMemoryCountdown(levels.getMemoryVisibleSteps());
            memoryCountdownCounter.setColor(parseColor(Settings.COLOR_GREEN_500, 1f));
        } else {
            levelBanner.effectMemoryActive();
            setMemoryCountdown(levels.getMemoryInvisibleSteps());
            memoryCountdownCounter.setColor(parseColor(Settings.COLOR_RED_500, 1f));
        }
    }

    public int getMemoryCountdown() {
        return memoryCountdown;
    }

    public void setMemoryCountdown(int memoryCountdown){
        this.memoryCountdown = memoryCountdown;
    }

    public void restMemoryCountdown() {
        memoryCountdown--;
    }

    // Movable
    public int getMovableCountdown() {
        return movableCountdown;
    }

    public void setMovableCountdown(int movableCountdown) {
        this.movableCountdown = movableCountdown;
    }

    public void restMovableCountdown() {
        movableCountdown--;
    }

    public void changeMovableState(){
        levelBanner.effectChangeMovable();
    }

    // ColorChange
    public int getColorChangeCountdown() {
        return colorChangeCountdown;
    }

    public void setColorChangeCountdown(int colorChangeCountdown) {
        this.colorChangeCountdown = colorChangeCountdown;
    }

    public void restColorChangeCountdown() {
        colorChangeCountdown--;
    }

    public int getNewRandomColor(int actualType){
        ArrayList<Integer> colorsArray = new ArrayList<Integer>();
        boolean exist;
        for (int i = 0; i < levels.getPositionsArrayList().size(); i++) {
            exist = false;
            if(levels.getPositionsArrayList().get(i) != null) {
                if(colorsArray.size() == 0){
                    colorsArray.add(levels.getPositionsArrayList().get(i));
                } else {
                    for (Integer color : colorsArray){
                        if(color == levels.getPositionsArrayList().get(i)){
                            exist = true;
                        }
                    }
                    if(!exist) colorsArray.add(levels.getPositionsArrayList().get(i));
                }
            }
        }
        int index;
        do {
            index = MathUtils.random(0,colorsArray.size()-1);
        } while (colorsArray.get(index) == actualType);

        return colorsArray.get(index);
    }

    // BlockedChange
    public int changeBlockedPosition(int actualPosition, int movedPosition, int gapPosition, ArrayList<Integer> actualPositions) {
        int newPosition = actualPosition;
        do {
            newPosition++;
            if(newPosition > actualPositions.size()){
                newPosition = 0;
            }

        } while (newPosition == movedPosition || newPosition == gapPosition);


        return newPosition;
    }

    // GameType
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public GameType getGameType() {
        return gameType;
    }

    public boolean isNormalGame(){
        if(gameType == GameType.NORMAL) return true;
        else return false;
    }

    public boolean isTimeGame(){
        if(gameType == GameType.TIME) return true;
        else return false;
    }

    // LevelType
    public void setLevelType(LevelType levelType) {
        this.levelType = levelType;
    }

    public LevelType getLevelType() {
        return levelType;
    }

    public boolean isNormalLevel(){
        if(levelType == LevelType.NORMAL) return true;
        else return false;
    }

    public boolean isMemoryLevel(){
        if(levelType == LevelType.MEMORY) return true;
        else return false;
    }

    public boolean isMovableLevel(){
        if(levelType == LevelType.MOVABLE) return true;
        else return false;
    }

    public boolean isColorChangeLevel(){
        if(levelType == LevelType.COLORCHANGE) return true;
        else return false;
    }

    public boolean isBlockedChangeLevel(){
        if(levelType == LevelType.BLOCKEDCHANGE) return true;
        else return false;
    }

    public boolean isMulticolor(){
        return multicolor;
    }

    public Board getBoard() {
        return board;
    }

    public InfoBanner getCountBanner() {
        return countBanner;
    }

    public ArrayList<MenuButton> getMenuButtons() {return menuButtons;}

    public boolean isHome() {
        return ButtonsGame.gameState == GameState.HOME;
    }

    public boolean isLevels() {
        return ButtonsGame.gameState == GameState.LEVELS;
    }

    public boolean isRunning() {
        return ButtonsGame.gameState == GameState.RUNNING;
    }

    public boolean isGameOver() {
        return ButtonsGame.gameState == GameState.GAMEOVER;
    }

    public boolean isTutorial() {
        return ButtonsGame.gameState == GameState.TUTORIAL;
    }

    public static GameOver getGameOver() {
        return gameOver;
    }

    public ButtonsGame getGame() {
        return game;
    }

    public static void setVideoAdButtonActive(){
        videoAdActive = true;
    }

    public static void setVideoAdButtonInactive(){
        videoAdActive = false;
    }

    public static boolean isVideoAdActive(){
        return videoAdActive;
    }

    public void setCountBanner(){
        if(isTimeGame()){
            getCountBanner().setBannerTitle(Settings.GAME_TIMEBANNER_TEXT);
            timer.start(levels.getTime(), 0, levels.getTime(), getDelay(1f));
        } else if(isNormalGame()){
            setSteps(levels.getSteps());
        }
    }

    public Color getLevelColor(){
        if(level <= 25){
            return parseColor(Settings.COLOR_DISTRICT1, 1f);
        } else if(level > 25 && level <= 50){
            return parseColor(Settings.COLOR_DISTRICT2, 1f);
        } else if(level > 50 && level <= 75){
            return parseColor(Settings.COLOR_DISTRICT3, 1f);
        } else if(level > 75 && level <= 100) {
            return world.parseColor(Settings.COLOR_DISTRICT4, 1f);
        } else if(level > 100 && level <= 125) {
            return world.parseColor(Settings.COLOR_DISTRICT5, 1f);
        } else if(level > 125 && level <= 150) {
            return world.parseColor(Settings.COLOR_DISTRICT6, 1f);
        } else if(level > 150 && level <= 175) {
            return world.parseColor(Settings.COLOR_DISTRICT7, 1f);
        } else if(level > 175 && level <= 200) {
            return world.parseColor(Settings.COLOR_DISTRICT8, 1f);
        } else if(level > 200 && level <= 225) {
            return world.parseColor(Settings.COLOR_DISTRICT9, 1f);
        } else if(level > 225 && level <= 250) {
            return world.parseColor(Settings.COLOR_DISTRICT10, 1f);
        }
        return world.parseColor(Settings.COLOR_BOARD, 1f);
    }

    public TextureRegion getDistrictBackground(int level){
        if(level <= 25){
            return AssetLoader.backgroundJungle;
        } else if(level > 25 && level <= 50){
            return AssetLoader.backgroundField;
        } else if(level > 50 && level <= 75){
            return AssetLoader.backgroundDesert;
        } else if(level > 75 && level <= 100) {
            return AssetLoader.backgroundMountain;
        } else if(level > 100 && level <= 125) {
            return AssetLoader.backgroundFishers;
        } else if(level > 125 && level <= 150) {
            return AssetLoader.backgroundMusicians;
        } else if(level > 150 && level <= 175) {
            return AssetLoader.backgroundMine;
        } else if(level > 175 && level <= 200) {
            return AssetLoader.backgroundOriental;
        } else if(level > 200 && level <= 225) {
            return AssetLoader.backgroundIceland;
        } else if(level > 225 && level <= 250) {
            return AssetLoader.backgroundVikings;
        }
        return AssetLoader.backgroundField;
    }

    public static Color parseColor(String hex, float alpha) {
        String hex1 = hex;
        if (hex1.indexOf("#") != -1) {
            hex1 = hex1.substring(1);
        }
        Color color = Color.valueOf(hex1);
        color.a = alpha;
        return color;
    }

    public void goToHomeScreen() {
        backButton.toHomeScreen(0.6f, 0.1f);
        if(isRunning()) topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToGameScreen(int i) {
        if(i < Settings.TOTAL_NUMBER_OF_LEVELS) { //Total levels
            getGameOver().getMenuButtons().get(0).toGameScreen(0.6f, 0.1f, i + 1);
        } else {
            goToHomeScreen();
        }
    }

    public void playGameScreen(int i) {
        getGameOver().getMenuButtons().get(0).toGameScreen(0.6f, 0.1f, i);
    }

    public void goToLevelScreen(int district) {
        backButton.toLevelScreen(0.5f, 0.1f, district);
        if(isRunning()) topWLayer.fadeIn(0.5f, .1f);
    }

    public void goToTutorialScreen(String type, int part) {
        menuButtons.get(0).toTutorialScreen(0.6f, 0.1f, type, part);
        if(isRunning()) topWLayer.fadeIn(0.5f, .1f);
    }

    public void goToMedalScreen(int district) {
        menuButtons.get(0).toMedalScreen(0.6f, 0.1f, district);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public static void goToRandomScreen() {
        gameOver.getRouletteButton().toRandomScreen(0.6f, 0.1f, 3);
        topWLayer.fadeIn(0.6f, .1f);
    }
}
