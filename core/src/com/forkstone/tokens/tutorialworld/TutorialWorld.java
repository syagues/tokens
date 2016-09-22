package com.forkstone.tokens.tutorialworld;

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
 * Created by sergi on 4/1/16.
 */
public class TutorialWorld extends GameWorld {

    public static final String TAG = "TutorialWorld";

    // SETTINGS
    private Settings settings;

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public TutorialWorld world;

    //GAME CAMERA
    private GameCam camera;

    // VARIABLES
    public static int stateNum = 0;
    public static String stateName = "";

    // OBJECTS
    private Background background, topWLayer;
    private Text continueText, titleStatic, textStatic, titleTime, textTime, textTimeNumber, titleMemory,
                 titleChangePosition, titleChangeColor, titleChangeBlocked, textMemory, textChangePosition,
                 textChangeColor, textChangeBlocked;
    private ArrayList<GameObject> tutorialObjects = new ArrayList<GameObject>();
    private GameObject memoryButton, changePositionButton1, changePositionButton2, changeColorButton,
                changeBlockedButton, lock;
    private InfoBanner memoryBanner;
    private Text memoryCounter, changePositionCounter1, changePositionCounter2, changeColorCounter;
    private MenuButton staticButton, timeButton;
    private MenuButton nextButton;
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private int level;

    public TutorialWorld(ButtonsGame game, ActionResolver actionResolver, float gameWidth, float gameHeight, float worldWidth, float worldHeight, String tutorialScreen, int level) {

        super(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, 0);
        settings = new Settings();
        this.game = game;
        this.actionResolver = actionResolver;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        stateName = tutorialScreen;
        this.level = level;

        ButtonsGame.gameState = GameState.TUTORIAL;
        setStateTutorial(tutorialScreen);

        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_SHEET, 1f));
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BOOK, 1f));
        topWLayer.fadeOut(.5f, .1f);

        setBeginingTutorialState();

        // Tracking
        actionResolver.setTrackerScreenName("TutorialScreen");
    }

    public void update(float delta) {
        background.update(delta);
        if(stateNum == 1){
            titleStatic.update(delta);
            textStatic.update(delta);
            staticButton.update(delta);
        } else if(stateNum == 2){
            titleTime.update(delta);
            textTime.update(delta);
            timeButton.update(delta);
            textTimeNumber.update(delta);
        } else if(stateNum == 3){
            titleMemory.update(delta);
            memoryBanner.update(delta);
            memoryButton.update(delta);
            memoryCounter.update(delta);
            textMemory.update(delta);
        } else if(stateNum == 4){
            titleChangePosition.update(delta);
            changePositionButton1.update(delta);
            changePositionButton2.update(delta);
            changePositionCounter1.update(delta);
            changePositionCounter2.update(delta);
            textChangePosition.update(delta);
        } else if(stateNum == 5){
            titleChangeColor.update(delta);
            changeColorButton.update(delta);
            changeColorCounter.update(delta);
            textChangeColor.update(delta);
        } else if(stateNum == 6){
            titleChangeBlocked.update(delta);
            textChangeBlocked.update(delta);
            changeBlockedButton.update(delta);
            lock.update(delta);
        }
        continueText.update(delta);
        nextButton.update(delta);
        topWLayer.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        background.render(batch, shapeRenderer, fontShader, objectShader);
        if(stateNum == 1){
            titleStatic.render(batch, shapeRenderer, fontShader, objectShader);
            textStatic.render(batch, shapeRenderer, fontShader, objectShader);
            staticButton.render(batch, shapeRenderer, fontShader, objectShader);
        } else if(stateNum == 2){
            titleTime.render(batch, shapeRenderer, fontShader, objectShader);
            textTime.render(batch, shapeRenderer, fontShader, objectShader);
            timeButton.render(batch, shapeRenderer, fontShader, objectShader);
            textTimeNumber.render(batch, shapeRenderer, fontShader, objectShader);
        } else if(stateNum == 3){
            titleMemory.render(batch, shapeRenderer, fontShader, objectShader);
            memoryBanner.render(batch, shapeRenderer, fontShader, objectShader);
            memoryButton.render(batch, shapeRenderer, fontShader, objectShader);
            memoryCounter.render(batch, shapeRenderer, fontShader, objectShader);
            textMemory.render(batch, shapeRenderer, fontShader, objectShader);
        } else if(stateNum == 4){
            titleChangePosition.render(batch, shapeRenderer, fontShader, objectShader);
            changePositionButton1.render(batch, shapeRenderer, fontShader, objectShader);
            changePositionButton2.render(batch, shapeRenderer, fontShader, objectShader);
            changePositionCounter1.render(batch, shapeRenderer, fontShader, objectShader);
            changePositionCounter2.render(batch, shapeRenderer, fontShader, objectShader);
            textChangePosition.render(batch, shapeRenderer, fontShader, objectShader);
        } else if(stateNum == 5){
            titleChangeColor.render(batch, shapeRenderer, fontShader, objectShader);
            changeColorButton.render(batch, shapeRenderer, fontShader, objectShader);
            changeColorCounter.render(batch, shapeRenderer, fontShader, objectShader);
            textChangeColor.render(batch, shapeRenderer, fontShader, objectShader);
        } else if(stateNum == 6){
            titleChangeBlocked.render(batch, shapeRenderer, fontShader, objectShader);
            textChangeBlocked.render(batch, shapeRenderer, fontShader, objectShader);
            changeBlockedButton.render(batch, shapeRenderer, fontShader, objectShader);
            lock.render(batch, shapeRenderer, fontShader, objectShader);
        }
        continueText.render(batch, shapeRenderer, fontShader, objectShader);
        nextButton.render(batch, shapeRenderer, fontShader, objectShader);
        topWLayer.render(batch, shapeRenderer, fontShader, objectShader);
    }

    public void setStateTutorial(String tutorialScreen){
        if(tutorialScreen.equals("BLOCK")){
            stateNum = 1;
        } else if(tutorialScreen.equals("TIME")){
            stateNum = 2;
        } else if(tutorialScreen.equals("MEMORY")){
            stateNum = 3;
        } else if(tutorialScreen.equals("CHANGEPOSITION")){
            stateNum = 4;
        } else if(tutorialScreen.equals("CHANGECOLOR")){
            stateNum = 5;
        } else if(tutorialScreen.equals("CHANGEBLOCKED")){
            stateNum = 6;
        }
    }

    public void setBeginingTutorialState(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            switch (stateNum) {
                case 1:
                    setStaticTutorialSmartphone();
                    break;
                case 2:
                    setTimeTutorialSmartphone();
                    break;
                case 3:
                    setMemoryTutorialSmartphone();
                    break;
                case 4:
                    setChangePositionTutorialSmartphone();
                    break;
                case 5:
                    setChangeColorTutorialSmartphone();
                    break;
                case 6:
                    setChangeBlockedTutorialSmartphone();
                    break;
            }
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)){
            switch (stateNum) {
                case 1:
                    setStaticTutorialTablet1610();
                    break;
                case 2:
                    setTimeTutorialTablet1610();
                    break;
                case 3:
                    setMemoryTutorialTablet1610();
                    break;
                case 4:
                    setChangePositionTutorialTablet1610();
                    break;
                case 5:
                    setChangeColorTutorialTablet1610();
                    break;
                case 6:
                    setChangeBlockedTutorialTablet1610();
                    break;
            }
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)){
            switch (stateNum) {
                case 1:
                    setStaticTutorialSmallSmartphone();
                    break;
                case 2:
                    setTimeTutorialSmallSmartphone();
                    break;
                case 3:
                    setMemoryTutorialSmallSmartphone();
                    break;
                case 4:
                    setChangePositionTutorialSmallSmartphone();
                    break;
                case 5:
                    setChangeColorTutorialSmallSmartphone();
                    break;
                case 6:
                    setChangeBlockedTutorialSmallSmartphone();
                    break;
            }
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            switch (stateNum) {
                case 1:
                    setStaticTutorialTablet43();
                    break;
                case 2:
                    setTimeTutorialTablet43();
                    break;
                case 3:
                    setMemoryTutorialTablet43();
                    break;
                case 4:
                    setChangePositionTutorialTablet43();
                    break;
                case 5:
                    setChangeColorTutorialTablet43();
                    break;
                case 6:
                    setChangeBlockedTutorialTablet43();
                    break;
            }
        }
    }

    // ------------ SMARTPHONE ------------
    public void setStaticTutorialSmartphone(){

        tutorialObjects.clear();

        titleStatic = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.STATIC_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        textStatic = new Text(this, 390, gameHeight/2 + 80, gameWidth - 420, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.STATIC_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        staticButton = new MenuButton(this, 50, gameHeight/2 - 100,
                Settings.PLAY_BUTTON_SIZE, Settings.PLAY_BUTTON_SIZE + 10,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_PINK_500, 1f), AssetLoader.staticTR, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        menuButtons.add(staticButton);
    }

    public void setTimeTutorialSmartphone(){

        tutorialObjects.clear();

        titleTime = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.TIME_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        textTime = new Text(this, 390, gameHeight/2 + 100, gameWidth - 420, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.TIME_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        timeButton = new MenuButton(this, 50, gameHeight/2 - 95,
                Settings.PLAY_BUTTON_SIZE, Settings.PLAY_BUTTON_SIZE + 30,
                AssetLoader.chronometer,
                world.parseColor(Settings.COLOR_WHITE, 1f), AssetLoader.transparent, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        textTimeNumber = new Text(this, Settings.PLAY_BUTTON_SIZE/2 - 30, gameHeight/2 - 110 + (Settings.PLAY_BUTTON_SIZE+30)/2, 100, 100,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.TIME_TUTORIAL_TIME_TEXT,
                AssetLoader.fontXL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        menuButtons.add(timeButton);
    }

    public void setMemoryTutorialSmartphone(){

        tutorialObjects.clear();

        titleMemory = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEMORY_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        memoryBanner = new InfoBanner(this,
                gameWidth/2 - 330, gameHeight/2 + 210,
                240, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 0);
        memoryBanner.setText("8");

        memoryCounter = new Text(this,
                gameWidth/2 - 365, gameHeight/2 + 175,
                75, 75, AssetLoader.dot1, parseColor(Settings.COLOR_GREEN_500, 1f), "1",
                AssetLoader.fontXS,
                parseColor(Settings.COLOR_WHITE, 1f), 16,
                Align.center);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                memoryBanner.effectMemoryActive();
                memoryBanner.setText("7");
                memoryCounter.setText("4");
                memoryCounter.setColor(parseColor(Settings.COLOR_RED_500, 1f));
                memoryButton.setColor(parseColor(Settings.COLOR_BLUEGREY_400, 1f));
            }
        }, 2f);

        memoryButton = new GameObject(this, gameWidth/2 + 80, gameHeight/2 + 160,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f));

        textMemory = new Text(this, 50, gameHeight/2 - 140, gameWidth - 100, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEMORY_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    public void setChangePositionTutorialSmartphone(){

        tutorialObjects.clear();

        titleChangePosition = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEPOSITION_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        changePositionButton1 = new GameObject(this, gameWidth/2 - 280, gameHeight/2 + 160,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_PINK_500, 1f));

        changePositionButton2 = new GameObject(this, gameWidth/2 + 80, gameHeight/2 + 160,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_CYAN_500, 1f));

        changePositionCounter1 = new Text(this,
                gameWidth/2 - 290, gameHeight/2 + 300,
                75, 75, AssetLoader.dot1, parseColor(Settings.COLOR_INDIGO_500, 1f), "1",
                AssetLoader.fontXS,
                parseColor(Settings.COLOR_WHITE, 1f), 16,
                Align.center);

        changePositionCounter2 = new Text(this,
                gameWidth/2 + 70, gameHeight/2 + 300,
                75, 75, AssetLoader.dot1, parseColor(Settings.COLOR_INDIGO_500, 1f), "1",
                AssetLoader.fontXS,
                parseColor(Settings.COLOR_WHITE, 1f), 16,
                Align.center);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changePositionCounter1.setText("3");
                changePositionCounter2.setText("3");
                changePositionButton1.fadeOut(.3f, 0f);
                changePositionButton2.fadeOut(.3f, 0f);
                changePositionButton1.fadeIn(.3f, .3f);
                changePositionButton2.fadeIn(.3f, .3f);
            }
        }, 2f);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changePositionButton1.setColor(parseColor(Settings.COLOR_CYAN_500, 1f));
                changePositionButton2.setColor(parseColor(Settings.COLOR_PINK_500, 1f));
            }
        }, 2.3f);

        textChangePosition = new Text(this, 50, gameHeight/2 - 140, gameWidth - 100, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEPOSITION_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    public void setChangeColorTutorialSmartphone(){

        tutorialObjects.clear();

        titleChangeColor = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGECOLOR_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        changeColorButton = new GameObject(this, gameWidth/2 - 100, gameHeight/2 + 160,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_PURPLE_500, 1f));

        changeColorCounter = new Text(this,
                gameWidth/2 - 37, gameHeight/2 + 226,
                75, 75, AssetLoader.dot1, parseColor(Settings.COLOR_PURPLE_800, 1f), "1",
                AssetLoader.fontXS,
                parseColor(Settings.COLOR_WHITE, 1f), 16,
                Align.center);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changeColorCounter.setText("3");
                changeColorCounter.setColor(parseColor(Settings.COLOR_TEAL_800, 1f));
                changeColorButton.setColor(parseColor(Settings.COLOR_TEAL_500, 1f));
            }
        }, 2f);

        textChangeColor = new Text(this, 50, gameHeight/2 - 140, gameWidth - 100, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGECOLOR_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    public void setChangeBlockedTutorialSmartphone(){

        tutorialObjects.clear();

        titleChangeBlocked = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEBLOCKED_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        changeBlockedButton = new GameObject(this, gameWidth/2 - 100, gameHeight/2 + 160,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_BLUE_500, 1f));

        lock = new GameObject(this, gameWidth/2 - 100, gameHeight/2 + 160,
                200, 208, AssetLoader.staticTR, parseColor(Settings.COLOR_BLUE_100, 1f));

        textChangeBlocked = new Text(this, 40, gameHeight/2 - 140, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEBLOCKED_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    // ------------ TABLET 16/10 ------------
    public void setStaticTutorialTablet1610(){

        tutorialObjects.clear();

        titleStatic = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.STATIC_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        textStatic = new Text(this, 390, gameHeight/2 + 60, gameWidth - 420, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.STATIC_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        staticButton = new MenuButton(this, 50, gameHeight/2 - 100,
                Settings.PLAY_BUTTON_SIZE, Settings.PLAY_BUTTON_SIZE + 10,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_PINK_500, 1f), AssetLoader.staticTR, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        menuButtons.add(staticButton);
    }

    public void setTimeTutorialTablet1610(){

        tutorialObjects.clear();

        titleTime = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.TIME_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        textTime = new Text(this, 390, gameHeight/2 + 100, gameWidth - 420, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.TIME_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        timeButton = new MenuButton(this, 50, gameHeight/2 - 95,
                Settings.PLAY_BUTTON_SIZE, Settings.PLAY_BUTTON_SIZE + 30,
                AssetLoader.chronometer,
                world.parseColor(Settings.COLOR_WHITE, 1f), AssetLoader.transparent, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        textTimeNumber = new Text(this, Settings.PLAY_BUTTON_SIZE/2 - 30, gameHeight/2 - 110 + (Settings.PLAY_BUTTON_SIZE+30)/2, 100, 100,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.TIME_TUTORIAL_TIME_TEXT,
                AssetLoader.fontXL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        menuButtons.add(timeButton);
    }

    public void setMemoryTutorialTablet1610(){

        tutorialObjects.clear();

        titleMemory = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEMORY_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        memoryBanner = new InfoBanner(this,
                gameWidth/2 - 330, gameHeight/2 + 210,
                240, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXXS, AssetLoader.fontS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 0);
        memoryBanner.setText("8");

        memoryCounter = new Text(this,
                gameWidth/2 - 365, gameHeight/2 + 175,
                75, 75, AssetLoader.dot1, parseColor(Settings.COLOR_GREEN_500, 1f), "1",
                AssetLoader.fontXXS,
                parseColor(Settings.COLOR_WHITE, 1f), 16,
                Align.center);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                memoryBanner.effectMemoryActive();
                memoryBanner.setText("7");
                memoryCounter.setText("4");
                memoryCounter.setColor(parseColor(Settings.COLOR_RED_500, 1f));
                memoryButton.setColor(parseColor(Settings.COLOR_BLUEGREY_400, 1f));
            }
        }, 2f);

        memoryButton = new GameObject(this, gameWidth/2 + 80, gameHeight/2 + 160,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f));

        textMemory = new Text(this, 50, gameHeight/2 - 100, gameWidth - 100, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEMORY_TUTORIAL_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    public void setChangePositionTutorialTablet1610(){

        tutorialObjects.clear();

        titleChangePosition = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEPOSITION_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        changePositionButton1 = new GameObject(this, gameWidth/2 - 280, gameHeight/2 + 160,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_PINK_500, 1f));

        changePositionButton2 = new GameObject(this, gameWidth/2 + 80, gameHeight/2 + 160,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_CYAN_500, 1f));

        changePositionCounter1 = new Text(this,
                gameWidth/2 - 290, gameHeight/2 + 300,
                75, 75, AssetLoader.dot1, parseColor(Settings.COLOR_INDIGO_500, 1f), "1",
                AssetLoader.fontXXS,
                parseColor(Settings.COLOR_WHITE, 1f), 16,
                Align.center);

        changePositionCounter2 = new Text(this,
                gameWidth/2 + 70, gameHeight/2 + 300,
                75, 75, AssetLoader.dot1, parseColor(Settings.COLOR_INDIGO_500, 1f), "1",
                AssetLoader.fontXXS,
                parseColor(Settings.COLOR_WHITE, 1f), 16,
                Align.center);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changePositionCounter1.setText("3");
                changePositionCounter2.setText("3");
                changePositionButton1.fadeOut(.3f, 0f);
                changePositionButton2.fadeOut(.3f, 0f);
                changePositionButton1.fadeIn(.3f, .3f);
                changePositionButton2.fadeIn(.3f, .3f);
            }
        }, 2f);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changePositionButton1.setColor(parseColor(Settings.COLOR_CYAN_500, 1f));
                changePositionButton2.setColor(parseColor(Settings.COLOR_PINK_500, 1f));
            }
        }, 2.3f);

        textChangePosition = new Text(this, 50, gameHeight/2 - 140, gameWidth - 100, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEPOSITION_TUTORIAL_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    public void setChangeColorTutorialTablet1610(){

        tutorialObjects.clear();

        titleChangeColor = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGECOLOR_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        changeColorButton = new GameObject(this, gameWidth/2 - 100, gameHeight/2 + 160,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_PURPLE_500, 1f));

        changeColorCounter = new Text(this,
                gameWidth/2 - 37, gameHeight/2 + 226,
                75, 75, AssetLoader.dot1, parseColor(Settings.COLOR_PURPLE_800, 1f), "1",
                AssetLoader.fontXXS,
                parseColor(Settings.COLOR_WHITE, 1f), 16,
                Align.center);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changeColorCounter.setText("3");
                changeColorCounter.setColor(parseColor(Settings.COLOR_TEAL_800, 1f));
                changeColorButton.setColor(parseColor(Settings.COLOR_TEAL_500, 1f));
            }
        }, 2f);

        textChangeColor = new Text(this, 50, gameHeight/2 - 140, gameWidth - 100, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGECOLOR_TUTORIAL_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    public void setChangeBlockedTutorialTablet1610(){

        tutorialObjects.clear();

        titleChangeBlocked = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEBLOCKED_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        changeBlockedButton = new GameObject(this, gameWidth/2 - 100, gameHeight/2 + 160,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_BLUE_500, 1f));

        lock = new GameObject(this, gameWidth/2 - 100, gameHeight/2 + 160,
                200, 208, AssetLoader.staticTR, parseColor(Settings.COLOR_BLUE_100, 1f));

        textChangeBlocked = new Text(this, 40, gameHeight/2 - 140, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEBLOCKED_TUTORIAL_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    // ------------ SMALL SMARTPHONE ------------
    public void setStaticTutorialSmallSmartphone(){

        tutorialObjects.clear();

        titleStatic = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.STATIC_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        textStatic = new Text(this, 430, gameHeight/2 + 60, gameWidth - 420, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.STATIC_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        staticButton = new MenuButton(this, 80, gameHeight/2 - 100,
                Settings.PLAY_BUTTON_SIZE, Settings.PLAY_BUTTON_SIZE + 10,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_PINK_500, 1f), AssetLoader.staticTR, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 120,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        menuButtons.add(staticButton);
    }

    public void setTimeTutorialSmallSmartphone(){

        tutorialObjects.clear();

        titleTime = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.TIME_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        textTime = new Text(this, 420, gameHeight/2 + 100, gameWidth - 420, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.TIME_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        timeButton = new MenuButton(this, 80, gameHeight/2 - 95,
                Settings.PLAY_BUTTON_SIZE, Settings.PLAY_BUTTON_SIZE + 30,
                AssetLoader.chronometer,
                world.parseColor(Settings.COLOR_WHITE, 1f), AssetLoader.transparent, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        textTimeNumber = new Text(this, Settings.PLAY_BUTTON_SIZE/2, gameHeight/2 - 110 + (Settings.PLAY_BUTTON_SIZE+30)/2, 100, 100,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.TIME_TUTORIAL_TIME_TEXT,
                AssetLoader.fontXL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 120,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        menuButtons.add(timeButton);
    }

    public void setMemoryTutorialSmallSmartphone(){

        tutorialObjects.clear();

        titleMemory = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEMORY_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        memoryBanner = new InfoBanner(this,
                gameWidth/2 - 330, gameHeight/2 + 170,
                240, 160,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXS, AssetLoader.fontM, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 0);
        memoryBanner.setText("8");

        memoryCounter = new Text(this,
                gameWidth/2 - 365, gameHeight/2 + 135,
                75, 75, AssetLoader.dot1, parseColor(Settings.COLOR_GREEN_500, 1f), "1",
                AssetLoader.fontXXS,
                parseColor(Settings.COLOR_WHITE, 1f), 16,
                Align.center);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                memoryBanner.effectMemoryActive();
                memoryBanner.setText("7");
                memoryCounter.setText("4");
                memoryCounter.setColor(parseColor(Settings.COLOR_RED_500, 1f));
                memoryButton.setColor(parseColor(Settings.COLOR_BLUEGREY_400, 1f));
            }
        }, 2f);

        memoryButton = new GameObject(this, gameWidth/2 + 80, gameHeight/2 + 120,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f));

        textMemory = new Text(this, 50, gameHeight/2 - 180, gameWidth - 100, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEMORY_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 120,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    public void setChangePositionTutorialSmallSmartphone(){

        tutorialObjects.clear();

        titleChangePosition = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEPOSITION_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        changePositionButton1 = new GameObject(this, gameWidth/2 - 280, gameHeight/2 + 120,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_PINK_500, 1f));

        changePositionButton2 = new GameObject(this, gameWidth/2 + 80, gameHeight/2 + 120,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_CYAN_500, 1f));

        changePositionCounter1 = new Text(this,
                gameWidth/2 - 290, gameHeight/2 + 260,
                75, 75, AssetLoader.dot1, parseColor(Settings.COLOR_INDIGO_500, 1f), "1",
                AssetLoader.fontXXS,
                parseColor(Settings.COLOR_WHITE, 1f), 16,
                Align.center);

        changePositionCounter2 = new Text(this,
                gameWidth/2 + 70, gameHeight/2 + 260,
                75, 75, AssetLoader.dot1, parseColor(Settings.COLOR_INDIGO_500, 1f), "1",
                AssetLoader.fontXXS,
                parseColor(Settings.COLOR_WHITE, 1f), 16,
                Align.center);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changePositionCounter1.setText("3");
                changePositionCounter2.setText("3");
                changePositionButton1.fadeOut(.3f, 0f);
                changePositionButton2.fadeOut(.3f, 0f);
                changePositionButton1.fadeIn(.3f, .3f);
                changePositionButton2.fadeIn(.3f, .3f);
            }
        }, 2f);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changePositionButton1.setColor(parseColor(Settings.COLOR_CYAN_500, 1f));
                changePositionButton2.setColor(parseColor(Settings.COLOR_PINK_500, 1f));
            }
        }, 2.3f);

        textChangePosition = new Text(this, 50, gameHeight/2 - 180, gameWidth - 100, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEPOSITION_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 120,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    public void setChangeColorTutorialSmallSmartphone(){

        tutorialObjects.clear();

        titleChangeColor = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGECOLOR_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        changeColorButton = new GameObject(this, gameWidth/2 - 100, gameHeight/2 + 120,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_PURPLE_500, 1f));

        changeColorCounter = new Text(this,
                gameWidth/2 - 37, gameHeight/2 + 186,
                75, 75, AssetLoader.dot1, parseColor(Settings.COLOR_PURPLE_800, 1f), "1",
                AssetLoader.fontXXS,
                parseColor(Settings.COLOR_WHITE, 1f), 16,
                Align.center);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changeColorCounter.setText("3");
                changeColorCounter.setColor(parseColor(Settings.COLOR_TEAL_800, 1f));
                changeColorButton.setColor(parseColor(Settings.COLOR_TEAL_500, 1f));
            }
        }, 2f);

        textChangeColor = new Text(this, 50, gameHeight/2 - 180, gameWidth - 100, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGECOLOR_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 120,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    public void setChangeBlockedTutorialSmallSmartphone(){

        tutorialObjects.clear();

        titleChangeBlocked = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 250, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEBLOCKED_TUTORIAL_TITLE,
                AssetLoader.fontL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        changeBlockedButton = new GameObject(this, gameWidth/2 - 100, gameHeight/2 + 120,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_BLUE_500, 1f));

        lock = new GameObject(this, gameWidth/2 - 100, gameHeight/2 + 120,
                200, 208, AssetLoader.staticTR, parseColor(Settings.COLOR_BLUE_100, 1f));

        textChangeBlocked = new Text(this, 40, gameHeight/2 - 180, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEBLOCKED_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 120,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    // ------------ TABLET 4/3 ------------
    public void setStaticTutorialTablet43(){

        tutorialObjects.clear();

        titleStatic = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 200, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.STATIC_TUTORIAL_TITLE,
                AssetLoader.fontM, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        textStatic = new Text(this, 390, gameHeight/2 + 30, gameWidth - 420, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.STATIC_TUTORIAL_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        staticButton = new MenuButton(this, 50, gameHeight/2 - 100,
                Settings.PLAY_BUTTON_SIZE, Settings.PLAY_BUTTON_SIZE + 10,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_PINK_500, 1f), AssetLoader.staticTR, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        nextButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 180, 170,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 70, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        menuButtons.add(staticButton);
    }

    public void setTimeTutorialTablet43(){

        tutorialObjects.clear();

        titleTime = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 200, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.TIME_TUTORIAL_TITLE,
                AssetLoader.fontM, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        textTime = new Text(this, 390, gameHeight/2 + 40, gameWidth - 420, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.TIME_TUTORIAL_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        timeButton = new MenuButton(this, 50, gameHeight/2 - 95,
                Settings.PLAY_BUTTON_SIZE, Settings.PLAY_BUTTON_SIZE + 30,
                AssetLoader.chronometer,
                world.parseColor(Settings.COLOR_WHITE, 1f), AssetLoader.transparent, false, world.parseColor(Settings.COLOR_WHITE, 1f));

        textTimeNumber = new Text(this, Settings.PLAY_BUTTON_SIZE/2 - 30, gameHeight/2 - 110 + (Settings.PLAY_BUTTON_SIZE+30)/2, 100, 100,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.TIME_TUTORIAL_TIME_TEXT,
                AssetLoader.fontXL, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 180, 170,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 70, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        menuButtons.add(timeButton);
    }

    public void setMemoryTutorialTablet43(){

        tutorialObjects.clear();

        titleMemory = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 200, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEMORY_TUTORIAL_TITLE,
                AssetLoader.fontM, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        memoryBanner = new InfoBanner(this,
                gameWidth/2 - 330, gameHeight/2 + 190,
                200, 120,
                AssetLoader.banner,
                parseColor(Settings.COLOR_BOARD, 1f), Settings.GAME_MOVESBANNER_TEXT,
                AssetLoader.fontXXXS, AssetLoader.fontXS, parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f),
                world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 0);
        memoryBanner.setText("8");

        memoryCounter = new Text(this,
                gameWidth/2 - 355, gameHeight/2 + 150,
                50, 50, AssetLoader.dot1, parseColor(Settings.COLOR_GREEN_500, 1f), "1",
                AssetLoader.fontXXXS,
                parseColor(Settings.COLOR_WHITE, 1f), 10f,
                Align.center);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                memoryBanner.effectMemoryActive();
                memoryBanner.setText("7");
                memoryCounter.setText("4");
                memoryCounter.setColor(parseColor(Settings.COLOR_RED_500, 1f));
                memoryButton.setColor(parseColor(Settings.COLOR_BLUEGREY_400, 1f));
            }
        }, 2f);

        memoryButton = new GameObject(this, gameWidth/2 + 80, gameHeight/2 + 120,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f));

        textMemory = new Text(this, 50, gameHeight/2 - 180, gameWidth - 100, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEMORY_TUTORIAL_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 180, 170,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 70, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    public void setChangePositionTutorialTablet43(){

        tutorialObjects.clear();

        titleChangePosition = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 200, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEPOSITION_TUTORIAL_TITLE,
                AssetLoader.fontM, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        changePositionButton1 = new GameObject(this, gameWidth/2 - 280, gameHeight/2 + 120,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_PINK_500, 1f));

        changePositionButton2 = new GameObject(this, gameWidth/2 + 80, gameHeight/2 + 120,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_CYAN_500, 1f));

        changePositionCounter1 = new Text(this,
                gameWidth/2 - 290, gameHeight/2 + 260,
                50, 50, AssetLoader.dot1, parseColor(Settings.COLOR_INDIGO_500, 1f), "1",
                AssetLoader.fontXXXS,
                parseColor(Settings.COLOR_WHITE, 1f), 10,
                Align.center);

        changePositionCounter2 = new Text(this,
                gameWidth/2 + 70, gameHeight/2 + 260,
                50, 50, AssetLoader.dot1, parseColor(Settings.COLOR_INDIGO_500, 1f), "1",
                AssetLoader.fontXXXS,
                parseColor(Settings.COLOR_WHITE, 1f), 10,
                Align.center);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changePositionCounter1.setText("3");
                changePositionCounter2.setText("3");
                changePositionButton1.fadeOut(.3f, 0f);
                changePositionButton2.fadeOut(.3f, 0f);
                changePositionButton1.fadeIn(.3f, .3f);
                changePositionButton2.fadeIn(.3f, .3f);
            }
        }, 2f);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changePositionButton1.setColor(parseColor(Settings.COLOR_CYAN_500, 1f));
                changePositionButton2.setColor(parseColor(Settings.COLOR_PINK_500, 1f));
            }
        }, 2.3f);

        textChangePosition = new Text(this, 50, gameHeight/2 - 180, gameWidth - 100, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEPOSITION_TUTORIAL_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 180, 170,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 70, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    public void setChangeColorTutorialTablet43(){

        tutorialObjects.clear();

        titleChangeColor = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 200, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGECOLOR_TUTORIAL_TITLE,
                AssetLoader.fontM, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        changeColorButton = new GameObject(this, gameWidth/2 - 100, gameHeight/2 + 120,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_PURPLE_500, 1f));

        changeColorCounter = new Text(this,
                gameWidth/2 - 52, gameHeight/2 + 175,
                100, 100, AssetLoader.dot1, parseColor(Settings.COLOR_PURPLE_800, 1f), "1",
                AssetLoader.fontXS,
                parseColor(Settings.COLOR_WHITE, 1f), 30,
                Align.center);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changeColorCounter.setText("3");
                changeColorCounter.setColor(parseColor(Settings.COLOR_TEAL_800, 1f));
                changeColorButton.setColor(parseColor(Settings.COLOR_TEAL_500, 1f));
            }
        }, 2f);

        textChangeColor = new Text(this, 50, gameHeight/2 - 180, gameWidth - 100, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGECOLOR_TUTORIAL_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 180, 170,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 70, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }

    public void setChangeBlockedTutorialTablet43(){

        tutorialObjects.clear();

        titleChangeBlocked = new Text(this, gameWidth/2 - (gameWidth - 80)/2, gameHeight - 200, gameWidth - 80, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEBLOCKED_TUTORIAL_TITLE,
                AssetLoader.fontM, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);

        changeBlockedButton = new GameObject(this, gameWidth/2 - 100, gameHeight/2 + 120,
                200, 208, AssetLoader.colorButton, parseColor(Settings.COLOR_BLUE_500, 1f));

        lock = new GameObject(this, gameWidth/2 - 100, gameHeight/2 + 120,
                200, 208, AssetLoader.staticTR, parseColor(Settings.COLOR_BLUE_100, 1f));

        textChangeBlocked = new Text(this, 40, gameHeight/2 - 180, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHANGEBLOCKED_TUTORIAL_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        nextButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 180, 170,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        nextButton.setBackColor(parseColor(Settings.COLOR_GREY_800, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 70, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.center);
    }
    // ---------------------------------------------

    public ArrayList<MenuButton> getMenuButtons() {
        return menuButtons;
    }

    public int getLevel(){
        return level;
    }

    public void goToTutorialScreen(String type, int level) {
        menuButtons.get(0).toTutorialScreen(0.6f, 0.1f, type, level);
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
}
