package com.forkstone.tokens.tutorial2world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.Background;
import com.forkstone.tokens.gameobjects.Board;
import com.forkstone.tokens.gameobjects.Board33;
import com.forkstone.tokens.gameobjects.ColorButton;
import com.forkstone.tokens.gameobjects.Counter;
import com.forkstone.tokens.gameobjects.GameObject;
import com.forkstone.tokens.gameworld.GameCam;
import com.forkstone.tokens.gameworld.GameState;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.InfoBanner;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;

import java.util.ArrayList;

/**
 * Created by sergi on 21/2/16.
 */
public class Tutorial2World extends GameWorld {

    public static final String TAG = "Tutorial2World";

    // SETTINGS
    private Settings settings;

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public final Tutorial2World world = this;

    //GAME CAMERA
    private GameCam camera;

    // VARIABLES
    public static int stateNum = 0;
    public static String stateName = "";
    public int initialLeft, initialSteps, steps, left;
    public int originScreen;
    public boolean countBannerActive = false, checkLeftBannerActive = false;

    // MULTICOLOR HELP
    private boolean multicolor = false;

    // OBJECTS
    private Background background, topWLayer, semiTransLayer;
    private MenuButton replayButton, backButton, lightningButton, multicolorButton, continueButton;
    private Counter lightningCounter, multicolorCounter;
    private MenuButton nextButton;
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private InfoBanner checksLeftBanner, countBanner;
    private Board board;
    private GameObject auxBoard, boxMessage, finger, exampleBoard1, exampleBoard2, exampleBoard3;
    private Text textMessage, continueButtonText, lightningText, multicolorText, moreStepsText;
    private MenuButton lightningIcon, multicolorIcon, moreStepsIcon;

    public Tutorial2World(ButtonsGame game, ActionResolver actionResolver, float gameWidth, float gameHeight, float worldWidth, float worldHeight, int originScreen) {

        super(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, 0);
        settings = new Settings();
        this.game = game;
        this.actionResolver = actionResolver;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.originScreen = originScreen;

        ButtonsGame.gameState = GameState.TUTORIAL;

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_SHEET, 1f));
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BOOK, 1f));
        topWLayer.fadeOut(.5f, .1f);

        semiTransLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BLACK, .8f));

        stateNum = 1;

        setInitialHelps();
        setUpItems();

        // Tracking
        actionResolver.setTrackerScreenName("TutorialScreen");
    }

    public void update(float delta) {
        background.update(delta);

        //Banners
        checksLeftBanner.update(delta);
        countBanner.update(delta);

        //Buttons
        backButton.update(delta);
        replayButton.update(delta);
        lightningButton.update(delta);
        multicolorButton.update(delta);
        lightningCounter.update(delta);
        multicolorCounter.update(delta);

        //Board
        board.update(delta);
        auxBoard.update(delta);

        //Finger
        finger.update(delta);

        //Box Messages
        semiTransLayer.update(delta);
        boxMessage.update(delta);
        textMessage.update(delta);
        continueButton.update(delta);
        continueButtonText.update(delta);
        lightningText.update(delta);
        multicolorText.update(delta);
        moreStepsText.update(delta);
        lightningIcon.update(delta);
        multicolorIcon.update(delta);
        moreStepsIcon.update(delta);
        exampleBoard1.update(delta);
        exampleBoard2.update(delta);
        exampleBoard3.update(delta);

        topWLayer.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        background.render(batch, shapeRenderer, fontShader, objectShader);

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

        // Finger
        finger.render(batch, shapeRenderer, fontShader, objectShader);

        if(stateNum == 1){
            // Banners
            checksLeftBanner.render(batch, shapeRenderer, fontShader, objectShader);
            countBanner.render(batch, shapeRenderer, fontShader, objectShader);
            // Layer
            semiTransLayer.render(batch, shapeRenderer, fontShader, objectShader);
        } else if(stateNum == 2){
            checksLeftBanner.render(batch, shapeRenderer, fontShader, objectShader);
            semiTransLayer.render(batch, shapeRenderer, fontShader, objectShader);
            if(countBannerActive) countBanner.render(batch, shapeRenderer, fontShader, objectShader);
        } else if(stateNum == 3){
            countBanner.render(batch, shapeRenderer, fontShader, objectShader);
            semiTransLayer.render(batch, shapeRenderer, fontShader, objectShader);
            if(checkLeftBannerActive) checksLeftBanner.render(batch, shapeRenderer, fontShader, objectShader);
        } else {
            checksLeftBanner.render(batch, shapeRenderer, fontShader, objectShader);
            countBanner.render(batch, shapeRenderer, fontShader, objectShader);
            semiTransLayer.render(batch, shapeRenderer, fontShader, objectShader);
        }

        // Box Messages
        boxMessage.render(batch, shapeRenderer, fontShader, objectShader);
        textMessage.render(batch, shapeRenderer, fontShader, objectShader);
        continueButton.render(batch, shapeRenderer, fontShader, objectShader);
        continueButtonText.render(batch, shapeRenderer, fontShader, objectShader);
        lightningText.render(batch, shapeRenderer, fontShader, objectShader);
        multicolorText.render(batch, shapeRenderer, fontShader, objectShader);
        moreStepsText.render(batch, shapeRenderer, fontShader, objectShader);
        lightningIcon.render(batch, shapeRenderer, fontShader, objectShader);
        multicolorIcon.render(batch, shapeRenderer, fontShader, objectShader);
        moreStepsIcon.render(batch, shapeRenderer, fontShader, objectShader);
        exampleBoard1.render(batch, shapeRenderer, fontShader, objectShader);
        exampleBoard2.render(batch, shapeRenderer, fontShader, objectShader);
        exampleBoard3.render(batch, shapeRenderer, fontShader, objectShader);

        topWLayer.render(batch, shapeRenderer, fontShader, objectShader);
    }

    public void finishGame(boolean gameComplete){
        continueTutorial();
    }

    private void setInitialHelps(){
        if(originScreen == 1){
            AssetLoader.setMoreMoves(1);
            AssetLoader.setMulticolor(2);
            AssetLoader.setResol(1);
            AssetLoader.setMoreTime(1);
        } else {
            AssetLoader.setMulticolor(1);
        }
    }

    public void setUpItems(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            switch (stateNum){
                case 1:
                    setUpInitialSmartphone();
                    setInitialBoxMessageSmartphone();
                    setOtherItemsSmartphone();
                    startEffects(0);
                    break;
                case 2:
                    setPartTwoBoxMessageSmartphone();
                    break;
                case 3:
                    setPartThreeBoxMessageSmartphone();
                    break;
                case 4:
                    setPartFourBoxMessageSmartphone();
                    break;
                case 5:
                    setPartFiveBoxMessageSmartphone();
                    break;
                case 6:
                    setPartSixBoxMessageSmartphone();
                    break;
                case 7:
                    setPartSevenBoxMessageSmartphone();
                    break;
                case 8:
                    setPartEightBoxMessageSmartphone();
                    break;
                case 9:
                    setPartNineBoxMessageSmartphone();
                    break;
                case 10:
                    setPartTenBoxMessageSmartphone();
                    break;
                case 11:
                    setPartElevenBoxMessageSmartphone();
                    break;
                case 12:
                    setPartTwelveBoxMessageSmartphone();
                    break;
                case 13:
                    setPartThirteenBoxMessageSmartphone();
                    break;
            }
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)){
            switch (stateNum){
                case 1:
                    setUpInitialTablet1610();
                    setInitialBoxMessageTablet1610();
                    setOtherItemsTablet1610();
                    startEffects(0);
                    break;
                case 2:
                    setPartTwoBoxMessageTablet1610();
                    break;
                case 3:
                    setPartThreeBoxMessageTablet1610();
                    break;
                case 4:
                    setPartFourBoxMessageTablet1610();
                    break;
                case 5:
                    setPartFiveBoxMessageTablet1610();
                    break;
                case 6:
                    setPartSixBoxMessageTablet1610();
                    break;
                case 7:
                    setPartSevenBoxMessageTablet1610();
                    break;
                case 8:
                    setPartEightBoxMessageTablet1610();
                    break;
                case 9:
                    setPartNineBoxMessageTablet1610();
                    break;
                case 10:
                    setPartTenBoxMessageTablet1610();
                    break;
                case 11:
                    setPartElevenBoxMessageTablet1610();
                    break;
                case 12:
                    setPartTwelveBoxMessageTablet1610();
                    break;
                case 13:
                    setPartThirteenBoxMessageTablet1610();
                    break;
            }
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)){
            switch (stateNum){
                case 1:
                    setUpInitialSmallSmartphone();
                    setInitialBoxMessageSmallSmartphone();
                    setOtherItemsSmallSmartphone();
                    startEffects(0);
                    break;
                case 2:
                    setPartTwoBoxMessageSmallSmartphone();
                    break;
                case 3:
                    setPartThreeBoxMessageSmallSmartphone();
                    break;
                case 4:
                    setPartFourBoxMessageSmallSmartphone();
                    break;
                case 5:
                    setPartFiveBoxMessageSmallSmartphone();
                    break;
                case 6:
                    setPartSixBoxMessageSmallSmartphone();
                    break;
                case 7:
                    setPartSevenBoxMessageSmallSmartphone();
                    break;
                case 8:
                    setPartEightBoxMessageSmallSmartphone();
                    break;
                case 9:
                    setPartNineBoxMessageSmallSmartphone();
                    break;
                case 10:
                    setPartTenBoxMessageSmallSmartphone();
                    break;
                case 11:
                    setPartElevenBoxMessageSmallSmartphone();
                    break;
                case 12:
                    setPartTwelveBoxMessageSmallSmartphone();
                    break;
                case 13:
                    setPartThirteenBoxMessageSmallSmartphone();
                    break;
            }
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            switch (stateNum){
                case 1:
                    setUpInitialTablet43();
                    setInitialBoxMessageTablet43();
                    setOtherItemsTablet43();
                    startEffects(0);
                    break;
                case 2:
                    setPartTwoBoxMessageTablet43();
                    break;
                case 3:
                    setPartThreeBoxMessageTablet43();
                    break;
                case 4:
                    setPartFourBoxMessageTablet43();
                    break;
                case 5:
                    setPartFiveBoxMessageTablet43();
                    break;
                case 6:
                    setPartSixBoxMessageTablet43();
                    break;
                case 7:
                    setPartSevenBoxMessageTablet43();
                    break;
                case 8:
                    setPartEightBoxMessageTablet43();
                    break;
                case 9:
                    setPartNineBoxMessageTablet43();
                    break;
                case 10:
                    setPartTenBoxMessageTablet43();
                    break;
                case 11:
                    setPartElevenBoxMessageTablet43();
                    break;
                case 12:
                    setPartTwelveBoxMessageTablet43();
                    break;
                case 13:
                    setPartThirteenBoxMessageTablet43();
                    break;
            }
        }
    }

    // ------------ SMARTPHONE ------------
    public void setUpInitialSmartphone(){
        // Banners
        checksLeftBanner = new InfoBanner(this,
                100, bannersAndButtonsPositionSmartphone("banner"),
                gameWidth / 2 - 310, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_LEFTBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontB, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 0);

        countBanner = new InfoBanner(this,
                gameWidth - 310, bannersAndButtonsPositionSmartphone("banner"),
                gameWidth / 2 - 300, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontB, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 0);
        setSteps(3);

        // Buttons
        backButton = new MenuButton(this,
                100, bannersAndButtonsPositionSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        backButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        replayButton = new MenuButton(this,
                gameWidth / 2 - 185, bannersAndButtonsPositionSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        replayButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        lightningButton = new MenuButton(this,
                gameWidth / 2 + 65, bannersAndButtonsPositionSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.lightningButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        multicolorButton = new MenuButton(this,
                gameWidth - 225, bannersAndButtonsPositionSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.multicolorButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        multicolorButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

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

        // Board
        initialLeft = 8;
        board = new Board33(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                gameWidth - 80, gameWidth - 70,
                AssetLoader.board33, 3, 3, getPositionsArrayList(1), null, 3);
        getBoard().checkOkSprites();
        auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 90, gameWidth - 80, gameWidth - 70, AssetLoader.board33, parseColor(Settings.COLOR_BOARD, 1f));
        auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, 0f);
        board.fadeIn(0.5f, 1f);
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

    public void setInitialBoxMessageSmartphone(){
        boxMessage = new GameObject(this, 100, gameHeight/2 - 250, gameWidth - 200, 500,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOARD, 1f));
        textMessage = new Text(this, 120, gameHeight/2 - 270, gameWidth - 240, 460,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT1, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.center);
        continueButton = new MenuButton(this, gameWidth/2 + 50, gameHeight/2 - 190,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        continueButton.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        continueButtonText = new Text(this, gameWidth/2 - 270, gameHeight/2 - 150, 300, 50,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f),
                Settings.CONTINUE_TEXT, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);

        exampleBoard1 = new GameObject(this, 150, gameHeight - 500, 200, 202,
                AssetLoader.exampleBoard1, parseColor(Settings.COLOR_WHITE, 1f));
        exampleBoard2 = new GameObject(this, gameWidth/2 - 100, gameHeight - 500, 200, 202,
                AssetLoader.exampleBoard2, parseColor(Settings.COLOR_WHITE, 1f));
        exampleBoard3 = new GameObject(this, gameWidth - 350, gameHeight - 500, 200, 202,
                AssetLoader.exampleBoard3, parseColor(Settings.COLOR_WHITE, 1f));

        semiTransLayer.fadeIn(.5f, 1f, 0.8f);
        boxMessage.effectY(- boxMessage.getPosition().y, boxMessage.getPosition().y, .5f, 1f);
        textMessage.effectY(- textMessage.getPosition().y, textMessage.getPosition().y, .5f, 1f);
        continueButton.effectY(- continueButton.getPosition().y, continueButton.getPosition().y, .5f, 1f);
        continueButtonText.effectY(- continueButtonText.getPosition().y, continueButtonText.getPosition().y, .5f, 1f);
        exampleBoard1.effectY(gameHeight + exampleBoard1.getPosition().y, exampleBoard1.getPosition().y, .5f, 1.5f);
        exampleBoard2.effectY(gameHeight + exampleBoard2.getPosition().y, exampleBoard2.getPosition().y, .5f, 1.55f);
        exampleBoard3.effectY(gameHeight + exampleBoard3.getPosition().y, exampleBoard3.getPosition().y, .5f, 1.6f);
    }

    public void setOtherItemsSmartphone(){
        finger = new GameObject(this, gameWidth/2, -300, 150, 150,
                AssetLoader.finger, parseColor(Settings.COLOR_WHITE, 1f));

        lightningText = new Text(this, gameWidth/2 - 220, -(gameHeight/2 + 50), gameWidth, 200,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT10, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);
        multicolorText = new Text(this, gameWidth/2 - 220, -(gameHeight/2 - 80), 700, 200,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT8, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);
        moreStepsText = new Text(this, gameWidth/2 - 220, -(gameHeight/2 - 250), 700, 200,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT9, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);

        lightningIcon = new MenuButton(this, 180, -(gameHeight/2 + 160),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.lightningButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningIcon.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        multicolorIcon = new MenuButton(this, 180, -(gameHeight/2 + 10),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.multicolorButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        multicolorIcon.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreStepsIcon = new MenuButton(this, 180, -(gameHeight/2 - 150),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.plusThreeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreStepsIcon.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void setPartTwoBoxMessageSmartphone(){
        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT5);
        countBanner.setTitleFontColor(parseColor(Settings.COLOR_WHITE, 1f));

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                countBanner.fadeIn(.2f, 0f);
                countBannerActive = true;
            }
        }, 0.6f);
        exampleBoard1.effectX(exampleBoard1.getPosition().x, -exampleBoard1.getPosition().x*2, .4f, 0f);
        exampleBoard2.effectX(exampleBoard2.getPosition().x, -exampleBoard2.getPosition().x, .4f, .05f);
        exampleBoard3.effectX(exampleBoard3.getPosition().x, -exampleBoard3.getPosition().x, .4f, .1f);
    }

    public void setPartThreeBoxMessageSmartphone(){
        countBanner.setTitleFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT6);
        checksLeftBanner.setTitleFontColor(parseColor(Settings.COLOR_WHITE, 1f));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                checksLeftBanner.fadeIn(.2f, 0f);
                checkLeftBannerActive = true;
            }
        }, 0.2f);
    }

    public void setPartFourBoxMessageSmartphone(){
        checksLeftBanner.setTitleFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT61);
    }

    public void setPartFiveBoxMessageSmartphone(){
        semiTransLayer.fadeOut(.9f, 0f, .8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y, .6f, .1f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y, .6f, .1f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .6f, .1f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .6f, .1f);

        finger.setPosition(gameWidth/2 - 75, gameHeight/2 - 75);
        finger.fadeIn(.5f, .9f);
        finger.effectX(finger.getPosition().x, finger.getPosition().x + 295, .6f, 1.5f, 10, .2f);
    }

    public void setPartSixBoxMessageSmartphone(){
        finger.fadeOut(.5f, 0f);
        finger.setPosition(gameWidth / 2 - 75, gameHeight / 2 + 225);
        finger.fadeIn(.5f, .6f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y - 295, .6f, 1.2f, 10, .2f);
    }

    public void setPartSevenBoxMessageSmartphone(){
        finger.fadeOut(.5f, 0f);
        finger.setPosition(gameWidth / 2 + 225, gameHeight / 2 + 225);
        finger.fadeIn(.5f, .6f);
        finger.effectX(finger.getPosition().x, finger.getPosition().x - 295, .6f, 1.2f, 10, .2f);
    }

    public void setPartEightBoxMessageSmartphone(){
        boxMessage = new GameObject(this, 100, -(gameHeight/2 - 400)*2, gameWidth - 200, 900,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOARD, 1f));
        textMessage = new Text(this, 120, -(gameHeight/2 - 10), gameWidth - 240, 460,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT1, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.center);
        continueButton.setPosition(gameWidth / 2 + 50, -(gameHeight / 2 - 320));
        continueButtonText.setPosition(gameWidth / 2 - 270, -(gameHeight / 2 - 290));


        finger.fadeOut(.5f, 0f);
        semiTransLayer.fadeIn(.5f, .2f, 0.8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y / 2, .5f, .3f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y, .5f, .5f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .5f, .5f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .5f, .5f);

        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT7);

        lightningText.effectY(lightningText.getPosition().y, -lightningText.getPosition().y, .5f, .5f);
        multicolorText.effectY(multicolorText.getPosition().y, - multicolorText.getPosition().y, .5f, .5f);
        moreStepsText.effectY(moreStepsText.getPosition().y, - moreStepsText.getPosition().y, .5f, .5f);
        lightningIcon.effectY(lightningIcon.getPosition().y, - lightningIcon.getPosition().y, .5f, .5f);
        multicolorIcon.effectY(multicolorIcon.getPosition().y, - multicolorIcon.getPosition().y, .5f, .5f);
        moreStepsIcon.effectY(moreStepsIcon.getPosition().y, - moreStepsIcon.getPosition().y, .5f, .5f);
    }

    public void setPartNineBoxMessageSmartphone(){
        board.fadeOut(.5f, .2f);
        board = new Board33(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                gameWidth - 80, gameWidth - 70,
                AssetLoader.board33, 3, 3, getPositionsArrayList(2), null, 3);
        getBoard().checkOkSprites();
        board.fadeIn(.5f, .7f);
        setSteps(1);

        lightningText.effectX(lightningText.getPosition().x, -lightningText.getPosition().x * 3, .5f, .1f);
        multicolorText.effectX(multicolorText.getPosition().x, multicolorText.getPosition().x * 8, .5f, .1f);
        moreStepsText.effectX(moreStepsText.getPosition().x, -moreStepsText.getPosition().x * 3, .5f, .1f);
        lightningIcon.effectX(lightningIcon.getPosition().x, -lightningIcon.getPosition().x * 3, .5f, .1f);
        multicolorIcon.effectX(multicolorIcon.getPosition().x, multicolorIcon.getPosition().x * 8, .5f, .1f);
        moreStepsIcon.effectX(moreStepsIcon.getPosition().x, -moreStepsIcon.getPosition().x * 3, .5f, .1f);

        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT101);

        textMessage.effectY(textMessage.getPosition().y, gameHeight / 2 - 280, .3f, .1f);
        continueButton.effectY(continueButton.getPosition().y, gameHeight/2 - 190, .3f, .1f);
        continueButtonText.effectY(continueButtonText.getPosition().y, gameHeight / 2 - 150, .3f, .1f);

        //Showing interstitial
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                // Do your work
                boxMessage = new GameObject(world, 100, gameHeight / 2 - 250, gameWidth - 200, 500,
                        AssetLoader.bigBanner, parseColor(Settings.COLOR_BOARD, 1f));
            }
        }, 0.5f);

    }

    public void setPartTenBoxMessageSmartphone(){
        semiTransLayer.fadeOut(.9f, 0f, .8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y*2, .5f, .3f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y, .5f, .3f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .5f, .3f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .5f, .3f);
//        lightningText.effectY(lightningText.getPosition().y, -lightningText.getPosition().y, .5f, .3f);
//        multicolorText.effectY(multicolorText.getPosition().y, -multicolorText.getPosition().y, .5f, .3f);
//        moreStepsText.effectY(moreStepsText.getPosition().y, -moreStepsText.getPosition().y, .5f, .3f);
//        lightningIcon.effectY(lightningIcon.getPosition().y, - lightningIcon.getPosition().y, .5f, .3f);
//        multicolorIcon.effectY(multicolorIcon.getPosition().y, - multicolorIcon.getPosition().y, .5f, .3f);
//        moreStepsIcon.effectY(moreStepsIcon.getPosition().y, -moreStepsIcon.getPosition().y, .5f, .3f);

        finger.setPosition(gameWidth / 2 + 300, gameHeight / 2 - 750);
        finger.fadeIn(.5f, .9f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y + 50, .3f, 1.5f, 12, .1f);
    }

    public void setPartElevenBoxMessageSmartphone(){
        finger.fadeOut(.2f, 0f);
        finger.setPosition(gameWidth / 2 - 75, gameHeight / 2 + 225);
        finger.fadeIn(.3f, .3f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y - 30, .2f, .9f, 10, .1f);
    }

    public void setPartTwelveBoxMessageSmartphone(){
        finger.fadeOut(.2f, 0f);
        finger.setPosition(gameWidth / 2 + 225, gameHeight / 2 - 350);
        finger.fadeIn(.3f, .3f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y + 295, .6f, .9f, 10, .2f);
    }

    public void setPartThirteenBoxMessageSmartphone(){
        finger.fadeOut(.5f, 0f);
        semiTransLayer.fadeIn(.5f, .2f, 0.8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y / 2, .5f, .3f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y, .5f, .5f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .5f, .5f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .5f, .5f);

        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT11);
    }

    // ------------ TABLET 16/10 ------------
    public void setUpInitialTablet1610(){
        // Banners
        checksLeftBanner = new InfoBanner(this,
                100, bannersAndButtonsPositionTablet1610("banner"),
                gameWidth / 2 - 310, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_LEFTBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontB, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 0);

        countBanner = new InfoBanner(this,
                gameWidth - 310, bannersAndButtonsPositionTablet1610("banner"),
                gameWidth / 2 - 300, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontB, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 0);
        setSteps(3);

        // Buttons
        backButton = new MenuButton(this,
                100, bannersAndButtonsPositionTablet1610("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        backButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        replayButton = new MenuButton(this,
                gameWidth / 2 - 185, bannersAndButtonsPositionTablet1610("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        replayButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        lightningButton = new MenuButton(this,
                gameWidth / 2 + 65, bannersAndButtonsPositionTablet1610("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.lightningButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        multicolorButton = new MenuButton(this,
                gameWidth - 225, bannersAndButtonsPositionTablet1610("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.multicolorButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        multicolorButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

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

        // Board
        initialLeft = 8;
        board = new Board33(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                gameWidth - 80, gameWidth - 70,
                AssetLoader.board33, 3, 3, getPositionsArrayList(1), null, 3);
        getBoard().checkOkSprites();
        auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 90, gameWidth - 80, gameWidth - 70, AssetLoader.board33, parseColor(Settings.COLOR_BOARD, 1f));
        auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, 0f);
        board.fadeIn(0.5f, 1f);
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

    public void setInitialBoxMessageTablet1610(){
        boxMessage = new GameObject(this, 100, gameHeight/2 - 250, gameWidth - 200, 500,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOARD, 1f));
        textMessage = new Text(this, 120, gameHeight/2 - 270, gameWidth - 240, 460,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT1, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.center);
        continueButton = new MenuButton(this, gameWidth/2 + 50, gameHeight/2 - 190,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        continueButton.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        continueButtonText = new Text(this, gameWidth/2 - 270, gameHeight/2 - 150, 300, 50,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f),
                Settings.CONTINUE_TEXT, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);

        exampleBoard1 = new GameObject(this, 150, gameHeight - 500, 200, 202,
                AssetLoader.exampleBoard1, parseColor(Settings.COLOR_WHITE, 1f));
        exampleBoard2 = new GameObject(this, gameWidth/2 - 100, gameHeight - 500, 200, 202,
                AssetLoader.exampleBoard2, parseColor(Settings.COLOR_WHITE, 1f));
        exampleBoard3 = new GameObject(this, gameWidth - 350, gameHeight - 500, 200, 202,
                AssetLoader.exampleBoard3, parseColor(Settings.COLOR_WHITE, 1f));

        semiTransLayer.fadeIn(.5f, 1f, 0.8f);
        boxMessage.effectY(- boxMessage.getPosition().y, boxMessage.getPosition().y, .5f, 1f);
        textMessage.effectY(- textMessage.getPosition().y, textMessage.getPosition().y, .5f, 1f);
        continueButton.effectY(- continueButton.getPosition().y, continueButton.getPosition().y, .5f, 1f);
        continueButtonText.effectY(- continueButtonText.getPosition().y, continueButtonText.getPosition().y, .5f, 1f);
        exampleBoard1.effectY(gameHeight + exampleBoard1.getPosition().y, exampleBoard1.getPosition().y, .5f, 1.5f);
        exampleBoard2.effectY(gameHeight + exampleBoard2.getPosition().y, exampleBoard2.getPosition().y, .5f, 1.55f);
        exampleBoard3.effectY(gameHeight + exampleBoard3.getPosition().y, exampleBoard3.getPosition().y, .5f, 1.6f);
    }

    public void setOtherItemsTablet1610(){
        finger = new GameObject(this, gameWidth/2, -300, 150, 150,
                AssetLoader.finger, parseColor(Settings.COLOR_WHITE, 1f));

        lightningText = new Text(this, gameWidth/2 - 240, -(gameHeight/2 + 50), gameWidth, 200,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT10, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);
        multicolorText = new Text(this, gameWidth/2 - 240, -(gameHeight/2 - 80), 700, 200,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT8, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);
        moreStepsText = new Text(this, gameWidth/2 - 240, -(gameHeight/2 - 250), 700, 200,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT9, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);

        lightningIcon = new MenuButton(this, 180, -(gameHeight/2 + 160),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.lightningButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningIcon.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        multicolorIcon = new MenuButton(this, 180, -(gameHeight/2 + 10),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.multicolorButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        multicolorIcon.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreStepsIcon = new MenuButton(this, 180, -(gameHeight/2 - 150),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.plusThreeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreStepsIcon.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void setPartTwoBoxMessageTablet1610(){
        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT5);
        countBanner.setTitleFontColor(parseColor(Settings.COLOR_WHITE, 1f));

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                countBanner.fadeIn(.2f, 0f);
                countBannerActive = true;
            }
        }, 0.6f);
        exampleBoard1.effectX(exampleBoard1.getPosition().x, -exampleBoard1.getPosition().x*2, .4f, 0f);
        exampleBoard2.effectX(exampleBoard2.getPosition().x, -exampleBoard2.getPosition().x, .4f, .05f);
        exampleBoard3.effectX(exampleBoard3.getPosition().x, -exampleBoard3.getPosition().x, .4f, .1f);
    }

    public void setPartThreeBoxMessageTablet1610(){
        countBanner.setTitleFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT6);
        checksLeftBanner.setTitleFontColor(parseColor(Settings.COLOR_WHITE, 1f));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                checksLeftBanner.fadeIn(.2f, 0f);
                checkLeftBannerActive = true;
            }
        }, 0.2f);
    }

    public void setPartFourBoxMessageTablet1610(){
        checksLeftBanner.setTitleFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT61);
    }

    public void setPartFiveBoxMessageTablet1610(){
        semiTransLayer.fadeOut(.9f, 0f, .8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y, .6f, .1f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y, .6f, .1f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .6f, .1f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .6f, .1f);

        finger.setPosition(gameWidth/2 - 75, gameHeight/2 - 75);
        finger.fadeIn(.5f, .9f);
        finger.effectX(finger.getPosition().x, finger.getPosition().x + 295, .6f, 1.5f, 10, .2f);
    }

    public void setPartSixBoxMessageTablet1610(){
        finger.fadeOut(.5f, 0f);
        finger.setPosition(gameWidth / 2 - 75, gameHeight / 2 + 225);
        finger.fadeIn(.5f, .6f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y - 295, .6f, 1.2f, 10, .2f);
    }

    public void setPartSevenBoxMessageTablet1610(){
        finger.fadeOut(.5f, 0f);
        finger.setPosition(gameWidth / 2 + 225, gameHeight / 2 + 225);
        finger.fadeIn(.5f, .6f);
        finger.effectX(finger.getPosition().x, finger.getPosition().x - 295, .6f, 1.2f, 10, .2f);
    }

    public void setPartEightBoxMessageTablet1610(){
        boxMessage = new GameObject(this, 100, -(gameHeight/2 - 400)*2, gameWidth - 200, 900,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOARD, 1f));
        textMessage = new Text(this, 120, -(gameHeight/2 - 10), gameWidth - 240, 460,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT1, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.center);
        continueButton.setPosition(gameWidth / 2 + 50, -(gameHeight / 2 - 320));
        continueButtonText.setPosition(gameWidth / 2 - 270, -(gameHeight / 2 - 290));


        finger.fadeOut(.5f, 0f);
        semiTransLayer.fadeIn(.5f, .2f, 0.8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y / 2, .5f, .3f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y, .5f, .5f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .5f, .5f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .5f, .5f);

        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT7);

        lightningText.effectY(lightningText.getPosition().y, -lightningText.getPosition().y, .5f, .5f);
        multicolorText.effectY(multicolorText.getPosition().y, - multicolorText.getPosition().y, .5f, .5f);
        moreStepsText.effectY(moreStepsText.getPosition().y, - moreStepsText.getPosition().y, .5f, .5f);
        lightningIcon.effectY(lightningIcon.getPosition().y, - lightningIcon.getPosition().y, .5f, .5f);
        multicolorIcon.effectY(multicolorIcon.getPosition().y, - multicolorIcon.getPosition().y, .5f, .5f);
        moreStepsIcon.effectY(moreStepsIcon.getPosition().y, - moreStepsIcon.getPosition().y, .5f, .5f);
    }

    public void setPartNineBoxMessageTablet1610(){
        board.fadeOut(.5f, .2f);
        board = new Board33(this, 40, gameHeight / 2 - (gameWidth / 2) + 90,
                gameWidth - 80, gameWidth - 70,
                AssetLoader.board33, 3, 3, getPositionsArrayList(2), null, 3);
        getBoard().checkOkSprites();
        board.fadeIn(.5f, .7f);
        setSteps(1);

        lightningText.effectX(lightningText.getPosition().x, -lightningText.getPosition().x * 3, .5f, .1f);
        multicolorText.effectX(multicolorText.getPosition().x, multicolorText.getPosition().x * 8, .5f, .1f);
        moreStepsText.effectX(moreStepsText.getPosition().x, -moreStepsText.getPosition().x * 3, .5f, .1f);
        lightningIcon.effectX(lightningIcon.getPosition().x, -lightningIcon.getPosition().x * 3, .5f, .1f);
        multicolorIcon.effectX(multicolorIcon.getPosition().x, multicolorIcon.getPosition().x * 8, .5f, .1f);
        moreStepsIcon.effectX(moreStepsIcon.getPosition().x, -moreStepsIcon.getPosition().x * 3, .5f, .1f);

        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT101);

        textMessage.effectY(textMessage.getPosition().y, gameHeight / 2 - 280, .3f, .1f);
        continueButton.effectY(continueButton.getPosition().y, gameHeight/2 - 190, .3f, .1f);
        continueButtonText.effectY(continueButtonText.getPosition().y, gameHeight / 2 - 150, .3f, .1f);

        //Showing interstitial
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                // Do your work
                boxMessage = new GameObject(world, 100, gameHeight / 2 - 250, gameWidth - 200, 500,
                        AssetLoader.bigBanner, parseColor(Settings.COLOR_BOARD, 1f));
            }
        }, 0.5f);

    }

    public void setPartTenBoxMessageTablet1610(){
        semiTransLayer.fadeOut(.9f, 0f, .8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y*2, .5f, .3f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y, .5f, .3f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .5f, .3f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .5f, .3f);
//        lightningText.effectY(lightningText.getPosition().y, -lightningText.getPosition().y, .5f, .3f);
//        multicolorText.effectY(multicolorText.getPosition().y, -multicolorText.getPosition().y, .5f, .3f);
//        moreStepsText.effectY(moreStepsText.getPosition().y, -moreStepsText.getPosition().y, .5f, .3f);
//        lightningIcon.effectY(lightningIcon.getPosition().y, - lightningIcon.getPosition().y, .5f, .3f);
//        multicolorIcon.effectY(multicolorIcon.getPosition().y, - multicolorIcon.getPosition().y, .5f, .3f);
//        moreStepsIcon.effectY(moreStepsIcon.getPosition().y, -moreStepsIcon.getPosition().y, .5f, .3f);

        finger.setPosition(gameWidth / 2 + 300, gameHeight / 2 - 750);
        finger.fadeIn(.5f, .9f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y + 50, .3f, 1.5f, 12, .1f);
    }

    public void setPartElevenBoxMessageTablet1610(){
        finger.fadeOut(.2f, 0f);
        finger.setPosition(gameWidth / 2 - 75, gameHeight / 2 + 225);
        finger.fadeIn(.3f, .3f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y - 30, .2f, .9f, 10, .1f);
    }

    public void setPartTwelveBoxMessageTablet1610(){
        finger.fadeOut(.2f, 0f);
        finger.setPosition(gameWidth / 2 + 225, gameHeight / 2 - 350);
        finger.fadeIn(.3f, .3f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y + 295, .6f, .9f, 10, .2f);
    }

    public void setPartThirteenBoxMessageTablet1610(){
        finger.fadeOut(.5f, 0f);
        semiTransLayer.fadeIn(.5f, .2f, 0.8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y / 2, .5f, .3f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y, .5f, .5f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .5f, .5f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .5f, .5f);

        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT11);
    }

    // ------------ SMALL SMARTPHONE ------------
    public void setUpInitialSmallSmartphone(){
        // Banners
        checksLeftBanner = new InfoBanner(this,
                100, bannersAndButtonsPositionSmallSmartphone("banner"),
                gameWidth / 2 - 310, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_LEFTBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontB, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 0);

        countBanner = new InfoBanner(this,
                gameWidth - 310, bannersAndButtonsPositionSmallSmartphone("banner"),
                gameWidth / 2 - 300, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontB, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 0);
        setSteps(3);

        // Buttons
        backButton = new MenuButton(this,
                100, bannersAndButtonsPositionSmallSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        backButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        replayButton = new MenuButton(this,
                gameWidth / 2 - 185, bannersAndButtonsPositionSmallSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        replayButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        lightningButton = new MenuButton(this,
                gameWidth / 2 + 65, bannersAndButtonsPositionSmallSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.lightningButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        multicolorButton = new MenuButton(this,
                gameWidth - 225, bannersAndButtonsPositionSmallSmartphone("button"),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.multicolorButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        multicolorButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

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

        // Board
        initialLeft = 8;
        board = new Board33(this, 40, gameHeight / 2 - (gameWidth / 2) + 20,
                gameWidth - 80, gameWidth - 70,
                AssetLoader.board33, 3, 3, getPositionsArrayList(1), null, 3);
        getBoard().checkOkSprites();
        auxBoard = new GameObject(this, 40, gameHeight / 2 - (gameWidth / 2) + 20, gameWidth - 80, gameWidth - 70, AssetLoader.board33, parseColor(Settings.COLOR_BOARD, 1f));
        auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, 0f);
        board.fadeIn(0.5f, 1f);
        setLeft(initialLeft);
    }

    public float bannersAndButtonsPositionSmallSmartphone(String itemTypeName) {

        // Board Cuadrat
        if (itemTypeName.equals("banner")) {
            return gameHeight - 240;
        } else if(itemTypeName.equals("button")) {
            return 90;
        }

        return 0;
    }

    public void setInitialBoxMessageSmallSmartphone(){
        boxMessage = new GameObject(this, 100, gameHeight/2 - 300, gameWidth - 200, 500,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOARD, 1f));
        textMessage = new Text(this, 120, gameHeight/2 - 320, gameWidth - 240, 460,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT1, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.center);
        continueButton = new MenuButton(this, gameWidth/2 + 50, gameHeight/2 - 240,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        continueButton.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        continueButtonText = new Text(this, gameWidth/2 - 270, gameHeight/2 - 200, 300, 50,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f),
                Settings.CONTINUE_TEXT, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);

        exampleBoard1 = new GameObject(this, 150, gameHeight - 550, 200, 202,
                AssetLoader.exampleBoard1, parseColor(Settings.COLOR_WHITE, 1f));
        exampleBoard2 = new GameObject(this, gameWidth/2 - 100, gameHeight - 550, 200, 202,
                AssetLoader.exampleBoard2, parseColor(Settings.COLOR_WHITE, 1f));
        exampleBoard3 = new GameObject(this, gameWidth - 350, gameHeight - 550, 200, 202,
                AssetLoader.exampleBoard3, parseColor(Settings.COLOR_WHITE, 1f));

        semiTransLayer.fadeIn(.5f, 1f, 0.8f);
        boxMessage.effectY(- boxMessage.getPosition().y, boxMessage.getPosition().y, .5f, 1f);
        textMessage.effectY(- textMessage.getPosition().y, textMessage.getPosition().y, .5f, 1f);
        continueButton.effectY(- continueButton.getPosition().y, continueButton.getPosition().y, .5f, 1f);
        continueButtonText.effectY(- continueButtonText.getPosition().y, continueButtonText.getPosition().y, .5f, 1f);
        exampleBoard1.effectY(gameHeight + exampleBoard1.getPosition().y, exampleBoard1.getPosition().y, .5f, 1.5f);
        exampleBoard2.effectY(gameHeight + exampleBoard2.getPosition().y, exampleBoard2.getPosition().y, .5f, 1.55f);
        exampleBoard3.effectY(gameHeight + exampleBoard3.getPosition().y, exampleBoard3.getPosition().y, .5f, 1.6f);
    }

    public void setOtherItemsSmallSmartphone(){
        finger = new GameObject(this, gameWidth/2, -300, 150, 150,
                AssetLoader.finger, parseColor(Settings.COLOR_WHITE, 1f));

        lightningText = new Text(this, gameWidth/2 - 240, -(gameHeight/2 + 50), gameWidth, 200,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT10, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);
        multicolorText = new Text(this, gameWidth/2 - 240, -(gameHeight/2 - 80), 700, 200,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT8, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);
        moreStepsText = new Text(this, gameWidth/2 - 240, -(gameHeight/2 - 250), 700, 200,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT9, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);

        lightningIcon = new MenuButton(this, 180, -(gameHeight/2 + 160),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.lightningButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningIcon.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        multicolorIcon = new MenuButton(this, 180, -(gameHeight/2 + 10),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.multicolorButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        multicolorIcon.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreStepsIcon = new MenuButton(this, 180, -(gameHeight/2 - 150),
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.plusThreeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreStepsIcon.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void setPartTwoBoxMessageSmallSmartphone(){
        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT5);
        countBanner.setTitleFontColor(parseColor(Settings.COLOR_WHITE, 1f));

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                countBanner.fadeIn(.2f, 0f);
                countBannerActive = true;
            }
        }, 0.6f);
        exampleBoard1.effectX(exampleBoard1.getPosition().x, -exampleBoard1.getPosition().x*2, .4f, 0f);
        exampleBoard2.effectX(exampleBoard2.getPosition().x, -exampleBoard2.getPosition().x, .4f, .05f);
        exampleBoard3.effectX(exampleBoard3.getPosition().x, -exampleBoard3.getPosition().x, .4f, .1f);
    }

    public void setPartThreeBoxMessageSmallSmartphone(){
        countBanner.setTitleFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT6);
        checksLeftBanner.setTitleFontColor(parseColor(Settings.COLOR_WHITE, 1f));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                checksLeftBanner.fadeIn(.2f, 0f);
                checkLeftBannerActive = true;
            }
        }, 0.2f);
    }

    public void setPartFourBoxMessageSmallSmartphone(){
        checksLeftBanner.setTitleFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT61);
    }

    public void setPartFiveBoxMessageSmallSmartphone(){
        semiTransLayer.fadeOut(.9f, 0f, .8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y, .6f, .1f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y, .6f, .1f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .6f, .1f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .6f, .1f);

        finger.setPosition(gameWidth/2 - 75, gameHeight/2 - 145);
        finger.fadeIn(.5f, .9f);
        finger.effectX(finger.getPosition().x, finger.getPosition().x + 295, .6f, 1.5f, 10, .2f);
    }

    public void setPartSixBoxMessageSmallSmartphone(){
        finger.fadeOut(.5f, 0f);
        finger.setPosition(gameWidth / 2 - 75, gameHeight / 2 + 155);
        finger.fadeIn(.5f, .6f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y - 295, .6f, 1.2f, 10, .2f);
    }

    public void setPartSevenBoxMessageSmallSmartphone(){
        finger.fadeOut(.5f, 0f);
        finger.setPosition(gameWidth / 2 + 225, gameHeight / 2 + 155);
        finger.fadeIn(.5f, .6f);
        finger.effectX(finger.getPosition().x, finger.getPosition().x - 295, .6f, 1.2f, 10, .2f);
    }

    public void setPartEightBoxMessageSmallSmartphone(){
        boxMessage = new GameObject(this, 100, -(gameHeight/2 - 400)*2, gameWidth - 200, 900,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOARD, 1f));
        textMessage = new Text(this, 120, -(gameHeight/2 - 10), gameWidth - 240, 460,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT1, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.center);
        continueButton.setPosition(gameWidth / 2 + 50, -(gameHeight / 2 - 320));
        continueButtonText.setPosition(gameWidth / 2 - 270, -(gameHeight / 2 - 290));


        finger.fadeOut(.5f, 0f);
        semiTransLayer.fadeIn(.5f, .2f, 0.8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y / 2, .5f, .3f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y, .5f, .5f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .5f, .5f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .5f, .5f);

        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT7);

        lightningText.effectY(lightningText.getPosition().y, -lightningText.getPosition().y, .5f, .5f);
        multicolorText.effectY(multicolorText.getPosition().y, - multicolorText.getPosition().y, .5f, .5f);
        moreStepsText.effectY(moreStepsText.getPosition().y, - moreStepsText.getPosition().y, .5f, .5f);
        lightningIcon.effectY(lightningIcon.getPosition().y, - lightningIcon.getPosition().y, .5f, .5f);
        multicolorIcon.effectY(multicolorIcon.getPosition().y, - multicolorIcon.getPosition().y, .5f, .5f);
        moreStepsIcon.effectY(moreStepsIcon.getPosition().y, - moreStepsIcon.getPosition().y, .5f, .5f);
    }

    public void setPartNineBoxMessageSmallSmartphone(){
        board.fadeOut(.5f, .2f);
        board = new Board33(this, 40, gameHeight / 2 - (gameWidth / 2) + 20,
                gameWidth - 80, gameWidth - 70,
                AssetLoader.board33, 3, 3, getPositionsArrayList(2), null, 3);
        getBoard().checkOkSprites();
        board.fadeIn(.5f, .7f);
        setSteps(1);

        lightningText.effectX(lightningText.getPosition().x, -lightningText.getPosition().x * 3, .5f, .1f);
        multicolorText.effectX(multicolorText.getPosition().x, multicolorText.getPosition().x * 8, .5f, .1f);
        moreStepsText.effectX(moreStepsText.getPosition().x, -moreStepsText.getPosition().x * 3, .5f, .1f);
        lightningIcon.effectX(lightningIcon.getPosition().x, -lightningIcon.getPosition().x * 3, .5f, .1f);
        multicolorIcon.effectX(multicolorIcon.getPosition().x, multicolorIcon.getPosition().x * 8, .5f, .1f);
        moreStepsIcon.effectX(moreStepsIcon.getPosition().x, -moreStepsIcon.getPosition().x * 3, .5f, .1f);

        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT101);

        textMessage.effectY(textMessage.getPosition().y, gameHeight / 2 - 280, .3f, .1f);
        continueButton.effectY(continueButton.getPosition().y, gameHeight/2 - 190, .3f, .1f);
        continueButtonText.effectY(continueButtonText.getPosition().y, gameHeight / 2 - 150, .3f, .1f);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                // Do your work
                boxMessage = new GameObject(world, 100, gameHeight / 2 - 250, gameWidth - 200, 500,
                        AssetLoader.bigBanner, parseColor(Settings.COLOR_BOARD, 1f));
            }
        }, 0.5f);

    }

    public void setPartTenBoxMessageSmallSmartphone(){
        semiTransLayer.fadeOut(.9f, 0f, .8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y*2, .5f, .3f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y, .5f, .3f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .5f, .3f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .5f, .3f);

        finger.setPosition(gameWidth / 2 + 300, gameHeight / 2 - 820);
        finger.fadeIn(.5f, .9f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y + 50, .3f, 1.5f, 12, .1f);
    }

    public void setPartElevenBoxMessageSmallSmartphone(){
        finger.fadeOut(.2f, 0f);
        finger.setPosition(gameWidth / 2 - 75, gameHeight / 2 + 155);
        finger.fadeIn(.3f, .3f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y - 30, .2f, .9f, 10, .1f);
    }

    public void setPartTwelveBoxMessageSmallSmartphone(){
        finger.fadeOut(.2f, 0f);
        finger.setPosition(gameWidth / 2 + 225, gameHeight / 2 - 420);
        finger.fadeIn(.3f, .3f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y + 295, .6f, .9f, 10, .2f);
    }

    public void setPartThirteenBoxMessageSmallSmartphone(){
        finger.fadeOut(.5f, 0f);
        semiTransLayer.fadeIn(.5f, .2f, 0.8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y / 2, .5f, .3f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y, .5f, .5f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .5f, .5f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .5f, .5f);

        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT11);
    }

    // ------------ TABLET 4/3 ------------
    public void setUpInitialTablet43(){
        // Banners
        checksLeftBanner = new InfoBanner(this,
                150, bannersAndButtonsPositionTablet43("banner"),
                gameWidth / 2 - 350, 120,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_LEFTBANNER_TEXT,
                AssetLoader.fontXXXS, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 0);

        countBanner = new InfoBanner(this,
                gameWidth - 340, bannersAndButtonsPositionTablet43("banner"),
                gameWidth / 2 - 350, 120,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXXXS, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 0);
        setSteps(3);

        // Buttons
        backButton = new MenuButton(world,
                150, bannersAndButtonsPositionTablet43("button"),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        backButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        replayButton = new MenuButton(world,
                gameWidth / 2 - 180, bannersAndButtonsPositionTablet43("button"),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.replayButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        replayButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        lightningButton = new MenuButton(world,
                gameWidth / 2 + 55, bannersAndButtonsPositionTablet43("button"),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.lightningButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        multicolorButton = new MenuButton(world,
                gameWidth - 250, bannersAndButtonsPositionTablet43("button"),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.multicolorButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        multicolorButton.setBackColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        menuButtons.add(backButton);
        menuButtons.add(replayButton);
        menuButtons.add(lightningButton);
        menuButtons.add(multicolorButton);

        lightningCounter = new Counter(world,
                gameWidth / 2 + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 20, bannersAndButtonsPositionTablet43("button") + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 - 30,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_BLUEGREY_100, 1f), "resol", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorCounter = new Counter(world,
                gameWidth + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 - 280, bannersAndButtonsPositionTablet43("button") + Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 - 30,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_BLUEGREY_100, 1f), "multicolor", true,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        // Board
        initialLeft = 8;
        board = new Board33(this, 100, gameHeight / 2 - (gameWidth / 2) + 130,
                gameWidth - 200, gameWidth - 175,
                AssetLoader.board33, 3, 3, getPositionsArrayList(1), null, 3);
        auxBoard = new GameObject(this, 100, gameHeight / 2 - (gameWidth / 2) + 130, gameWidth - 200, gameWidth - 175, AssetLoader.board33, parseColor(Settings.COLOR_BOARD, 1f));
        auxBoard.effectY(auxBoard.getPosition().y - gameHeight, auxBoard.getPosition().y, 0.5f, 0f);
        board.fadeIn(0.5f, 1f);
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

    public void setInitialBoxMessageTablet43(){
        boxMessage = new GameObject(this, 100, gameHeight/2 - 200, gameWidth - 200, 400,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOARD, 1f));
        textMessage = new Text(this, 120, gameHeight/2 - 320, gameWidth - 240, 460,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT1, AssetLoader.fontXXXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.center);
        continueButton = new MenuButton(this, gameWidth/2 + 40, gameHeight/2 - 140,
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        continueButton.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        continueButtonText = new Text(this, gameWidth/2 - 200, gameHeight/2 - 120, 300, 50,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f),
                Settings.CONTINUE_TEXT, AssetLoader.fontXXXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);

        exampleBoard1 = new GameObject(this, 150, gameHeight - 430, 200, 202,
                AssetLoader.exampleBoard1, parseColor(Settings.COLOR_WHITE, 1f));
        exampleBoard2 = new GameObject(this, gameWidth/2 - 100, gameHeight - 430, 200, 202,
                AssetLoader.exampleBoard2, parseColor(Settings.COLOR_WHITE, 1f));
        exampleBoard3 = new GameObject(this, gameWidth - 350, gameHeight - 430, 200, 202,
                AssetLoader.exampleBoard3, parseColor(Settings.COLOR_WHITE, 1f));

        semiTransLayer.fadeIn(.5f, 1f, 0.8f);
        boxMessage.effectY(- boxMessage.getPosition().y * 2, boxMessage.getPosition().y, .5f, 1f);
        textMessage.effectY(- textMessage.getPosition().y * 2, textMessage.getPosition().y, .5f, 1f);
        continueButton.effectY(- continueButton.getPosition().y * 2, continueButton.getPosition().y, .5f, 1f);
        continueButtonText.effectY(- continueButtonText.getPosition().y * 2, continueButtonText.getPosition().y, .5f, 1f);
        exampleBoard1.effectY(gameHeight + exampleBoard1.getPosition().y, exampleBoard1.getPosition().y, .5f, 1.5f);
        exampleBoard2.effectY(gameHeight + exampleBoard2.getPosition().y, exampleBoard2.getPosition().y, .5f, 1.55f);
        exampleBoard3.effectY(gameHeight + exampleBoard3.getPosition().y, exampleBoard3.getPosition().y, .5f, 1.6f);
    }

    public void setOtherItemsTablet43(){
        finger = new GameObject(this, gameWidth/2, -300, 150, 150,
                AssetLoader.finger, parseColor(Settings.COLOR_WHITE, 1f));

        lightningText = new Text(this, gameWidth/2 - 220, -(gameHeight/2 - 40), gameWidth, 200,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT10, AssetLoader.fontXXXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);
        multicolorText = new Text(this, gameWidth/2 - 220, -(gameHeight/2 - 160), 700, 200,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT8, AssetLoader.fontXXXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);
        moreStepsText = new Text(this, gameWidth/2 - 220, -(gameHeight/2 - 280), 700, 200,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT9, AssetLoader.fontXXXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.left);

        lightningIcon = new MenuButton(this, 200, -(gameHeight/2 + 100),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.lightningButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningIcon.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        multicolorIcon = new MenuButton(this, 200, -(gameHeight/2 - 25),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.multicolorButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        multicolorIcon.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreStepsIcon = new MenuButton(this, 200, -(gameHeight/2 - 150),
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.plusThreeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreStepsIcon.setBackColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void setPartTwoBoxMessageTablet43(){
        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT5);
        countBanner.setTitleFontColor(parseColor(Settings.COLOR_WHITE, 1f));

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                countBanner.fadeIn(.2f, 0f);
                countBannerActive = true;
            }
        }, 0.6f);
        exampleBoard1.effectX(exampleBoard1.getPosition().x, -exampleBoard1.getPosition().x*2, .4f, 0f);
        exampleBoard2.effectX(exampleBoard2.getPosition().x, -exampleBoard2.getPosition().x, .4f, .05f);
        exampleBoard3.effectX(exampleBoard3.getPosition().x, -exampleBoard3.getPosition().x, .4f, .1f);
    }

    public void setPartThreeBoxMessageTablet43(){
        countBanner.setTitleFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT6);
        checksLeftBanner.setTitleFontColor(parseColor(Settings.COLOR_WHITE, 1f));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                checksLeftBanner.fadeIn(.2f, 0f);
                checkLeftBannerActive = true;
            }
        }, 0.2f);
    }

    public void setPartFourBoxMessageTablet43(){
        checksLeftBanner.setTitleFontColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT61);
    }

    public void setPartFiveBoxMessageTablet43(){
        semiTransLayer.fadeOut(.9f, 0f, .8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y * 2, .6f, .1f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y * 2, .6f, .1f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y * 2, .6f, .1f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y * 2, .6f, .1f);

        finger.setPosition(gameWidth/2 - 75, gameHeight/2 - 80);
        finger.fadeIn(.5f, .9f);
        finger.effectX(finger.getPosition().x, finger.getPosition().x + 270, .6f, 1.5f, 10, .2f);
    }

    public void setPartSixBoxMessageTablet43(){
        finger.fadeOut(.5f, 0f);
        finger.setPosition(gameWidth / 2 - 75, gameHeight / 2 + 210);
        finger.fadeIn(.5f, .6f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y - 295, .6f, 1.2f, 10, .2f);
    }

    public void setPartSevenBoxMessageTablet43(){
        finger.fadeOut(.5f, 0f);
        finger.setPosition(gameWidth / 2 + 190, gameHeight / 2 + 180);
        finger.fadeIn(.5f, .6f);
        finger.effectX(finger.getPosition().x, finger.getPosition().x - 270, .6f, 1.2f, 10, .2f);
    }

    public void setPartEightBoxMessageTablet43(){
        boxMessage = new GameObject(this, 100, -(gameHeight/2 - 350)*2, gameWidth - 200, 700,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOARD, 1f));
        textMessage = new Text(this, 120, -(gameHeight/2 - 160) * 2, gameWidth - 240, 460,
                AssetLoader.bigBanner, parseColor(Settings.COLOR_BOOK, 0f),
                Settings.BEGINING_TUTORIAL_TEXT1, AssetLoader.fontXXXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                0, Align.center);
        continueButton.setPosition(gameWidth / 2 + 50, -(gameHeight / 2 - 290));
        continueButtonText.setPosition(gameWidth/2 - 200, -(gameHeight / 2 - 270));


        finger.fadeOut(.5f, 0f);
        semiTransLayer.fadeIn(.5f, .2f, 0.8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y / 2, .5f, .3f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y / 2, .5f, .5f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .5f, .5f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .5f, .5f);

        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT7);

        lightningText.effectY(lightningText.getPosition().y, -lightningText.getPosition().y, .5f, .5f);
        multicolorText.effectY(multicolorText.getPosition().y, - multicolorText.getPosition().y, .5f, .5f);
        moreStepsText.effectY(moreStepsText.getPosition().y, - moreStepsText.getPosition().y, .5f, .5f);
        lightningIcon.effectY(lightningIcon.getPosition().y, - lightningIcon.getPosition().y, .5f, .5f);
        multicolorIcon.effectY(multicolorIcon.getPosition().y, - multicolorIcon.getPosition().y, .5f, .5f);
        moreStepsIcon.effectY(moreStepsIcon.getPosition().y, - moreStepsIcon.getPosition().y, .5f, .5f);
    }

    public void setPartNineBoxMessageTablet43(){
        board.fadeOut(.5f, .2f);
        board = new Board33(this, 100, gameHeight / 2 - (gameWidth / 2) + 130,
                gameWidth - 200, gameWidth - 175,
                AssetLoader.board33, 3, 3, getPositionsArrayList(2), null, 3);
        getBoard().checkOkSprites();
        board.fadeIn(.5f, .7f);
        setSteps(1);

        lightningText.effectX(lightningText.getPosition().x, -lightningText.getPosition().x * 3, .5f, .1f);
        multicolorText.effectX(multicolorText.getPosition().x, multicolorText.getPosition().x * 6, .5f, .1f);
        moreStepsText.effectX(moreStepsText.getPosition().x, -moreStepsText.getPosition().x * 3, .5f, .1f);
        lightningIcon.effectX(lightningIcon.getPosition().x, -lightningIcon.getPosition().x * 3, .5f, .1f);
        multicolorIcon.effectX(multicolorIcon.getPosition().x, multicolorIcon.getPosition().x * 6, .5f, .1f);
        moreStepsIcon.effectX(moreStepsIcon.getPosition().x, -moreStepsIcon.getPosition().x * 3, .5f, .1f);

        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT101);

        textMessage.effectY(textMessage.getPosition().y, gameHeight/2 - 320, .3f, .1f);
        continueButton.effectY(continueButton.getPosition().y, gameHeight/2 - 140, .3f, .1f);
        continueButtonText.effectY(continueButtonText.getPosition().y, gameHeight / 2 - 120, .3f, .1f);

        //Showing interstitial
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                // Do your work
                boxMessage = new GameObject(world, 100, gameHeight/2 - 200, gameWidth - 200, 400,
                        AssetLoader.bigBanner, parseColor(Settings.COLOR_BOARD, 1f));
            }
        }, 0.5f);

    }

    public void setPartTenBoxMessageTablet43(){
        semiTransLayer.fadeOut(.9f, 0f, .8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y*2, .5f, .3f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y*2, .5f, .3f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .5f, .3f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .5f, .3f);
//        lightningText.effectY(lightningText.getPosition().y, -lightningText.getPosition().y, .5f, .3f);
//        multicolorText.effectY(multicolorText.getPosition().y, -multicolorText.getPosition().y, .5f, .3f);
//        moreStepsText.effectY(moreStepsText.getPosition().y, -moreStepsText.getPosition().y, .5f, .3f);
//        lightningIcon.effectY(lightningIcon.getPosition().y, - lightningIcon.getPosition().y, .5f, .3f);
//        multicolorIcon.effectY(multicolorIcon.getPosition().y, - multicolorIcon.getPosition().y, .5f, .3f);
//        moreStepsIcon.effectY(moreStepsIcon.getPosition().y, -moreStepsIcon.getPosition().y, .5f, .3f);

        finger.setPosition(gameWidth / 2 + 265, gameHeight / 2 - 680);
        finger.fadeIn(.5f, .9f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y + 50, .3f, 1.5f, 12, .1f);
    }

    public void setPartElevenBoxMessageTablet43(){
        finger.fadeOut(.2f, 0f);
        finger.setPosition(gameWidth / 2 - 75, gameHeight / 2 + 190);
        finger.fadeIn(.3f, .3f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y - 30, .2f, .9f, 10, .1f);
    }

    public void setPartTwelveBoxMessageTablet43(){
        finger.fadeOut(.2f, 0f);
        finger.setPosition(gameWidth / 2 + 180, gameHeight / 2 - 370);
        finger.fadeIn(.3f, .3f);
        finger.effectY(finger.getPosition().y, finger.getPosition().y + 295, .6f, .9f, 10, .2f);
    }

    public void setPartThirteenBoxMessageTablet43(){
        finger.fadeOut(.5f, 0f);
        semiTransLayer.fadeIn(.5f, .2f, 0.8f);
        boxMessage.effectY(boxMessage.getPosition().y, -boxMessage.getPosition().y / 2, .5f, .3f);
        textMessage.effectY(textMessage.getPosition().y, -textMessage.getPosition().y/2, .5f, .5f);
        continueButton.effectY(continueButton.getPosition().y, -continueButton.getPosition().y, .5f, .5f);
        continueButtonText.effectY(continueButtonText.getPosition().y, -continueButtonText.getPosition().y, .5f, .5f);

        textMessage.setText(Settings.BEGINING_TUTORIAL_TEXT11);
    }

    // ---------------------------------------------

    private void startEffects(float extraDelay){
        checksLeftBanner.effectX(-300, checksLeftBanner.getPosition().x, 0.6f, .2f + extraDelay);
        countBanner.effectX(gameWidth, countBanner.getPosition().x, 0.6f, .2f + extraDelay);

        backButton.effectX(-300, backButton.getPosition().x, 0.6f, .2f + extraDelay);
        replayButton.effectY(-100, replayButton.getPosition().y, 0.6f, .2f + extraDelay);
        lightningButton.effectY(-100, lightningButton.getPosition().y, 0.6f, .2f + extraDelay);
        multicolorButton.effectX(gameWidth, multicolorButton.getPosition().x, 0.6f, .2f + extraDelay);
        lightningCounter.effectY(-100, lightningCounter.getPosition().y, 0.6f, .2f + extraDelay);
        multicolorCounter.effectX(gameWidth, multicolorCounter.getPosition().x, 0.6f, .2f + extraDelay);
    }

    public ArrayList<Integer> getPositionsArrayList(int number){
        ArrayList<Integer> tokens = new ArrayList<Integer>();
        if(number == 1){
            int[] array = new int[]{9, 9, 8, 1, 8, -1, 1, 9, 1};

            for (int i : array){
                if(i != -1) tokens.add(i);
                else tokens.add(null);
            }
        } else if(number == 2) {
            int[] array = new int[]{9, 9, 8, 1, 8, -1, 1, 9, 1};

            for (int i : array){
                if(i != -1) tokens.add(i);
                else tokens.add(null);
            }
        }

        return tokens;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<MenuButton> getMenuButtons() {
        return menuButtons;
    }

    public MenuButton getContinueButton(){
        return continueButton;
    }

    public boolean isTutorial(){
        return ButtonsGame.gameState == GameState.TUTORIAL;
    }

    // State
    public int getStateNum(){
        return stateNum;
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

    public void restStep() {
        steps--;
        countBanner.setText(steps + "");
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

    //Multicolor
    public void setMulticolorUp(){
        multicolor = true;
    }

    public void setMulticolorDown(){
        getBoard().checkOkSprites();
        getBoard().checkGameComplete();

        multicolor = false;
    }

    public boolean isMulticolor(){
        return multicolor;
    }

    // Events
    public void continueTutorial(){
        stateNum++;
        if(stateNum <= 13){
            setUpItems();
        } else {
            if(originScreen == 1){
                goToCharacterScreen(1);
                AssetLoader.setTutorialPart(1);
            } else if (originScreen == 2){
                goToSettingsScreen();
            }
        }
    }

    public void goToTutorialScreen(String type, int part) {
        menuButtons.get(0).toTutorialScreen(0.6f, 0.1f, type, part);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToLevelScreen() {
        menuButtons.get(0).toLevelScreen(0.6f, 0.1f, 0);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToGameScreen(int i) {
        menuButtons.get(0).toGameScreen(0.6f, 0.1f, i);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToStoryScreen(int storyPart) {
        menuButtons.get(0).toStoryScreen(0.6f, 0.1f, storyPart);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToSettingsScreen() {
        menuButtons.get(0).toSettingsScreen(0.6f, 0.1f);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToCharacterScreen(int part) {
        menuButtons.get(0).toCharacterScreen(0.6f, 0.1f, part);
        topWLayer.fadeIn(0.6f, .1f);
    }
}
