package com.forkstone.tokens.submenuworld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.Background;
import com.forkstone.tokens.gameworld.GameCam;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;

import java.util.ArrayList;

/**
 * Created by sergi on 12/2/16.
 */
public class SubmenuWorld extends GameWorld {

    public static final String TAG = "SubmenuWorld";

    // SETTINGS
    private Settings settings;

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public SubmenuWorld world;

    //GAME CAMERA
    private GameCam camera;

    // OBJECTS
    private Background background, drawedBack, topWLayer;
    private MenuButton backButton, arcadeButton, challengeButton;
    private Text arcadeButtonText, challengeButtonText;
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();

    // VARIABLES

    public SubmenuWorld(ButtonsGame game, ActionResolver actionResolver, float gameWidth, float gameHeight, float worldWidth, float worldHeight) {

        super(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, 0);
        settings = new Settings();
        this.game = game;
        this.actionResolver = actionResolver;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMap, world.parseColor(Settings.COLOR_WHITE, 1f));
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        topWLayer.fadeOut(.5f, .1f);

        setUpItems();

        // Tracking
        actionResolver.setTrackerScreenName("ShopScreen");

        // Music
        setMusic();
    }

    public void update(float delta) {
        background.update(delta);
        drawedBack.update(delta);

        // Buttons
        arcadeButton.update(delta);
        challengeButton.update(delta);
        // Text
        arcadeButtonText.update(delta);
        challengeButtonText.update(delta);

        backButton.update(delta);
        topWLayer.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        background.render(batch, shapeRenderer, fontShader, objectShader);
        drawedBack.render(batch, shapeRenderer, fontShader, objectShader);

        // Buttons
        arcadeButton.render(batch, shapeRenderer, fontShader, objectShader);
        challengeButton.render(batch, shapeRenderer, fontShader, objectShader);
        // Text
        arcadeButtonText.render(batch, shapeRenderer, fontShader, objectShader);
        challengeButtonText.render(batch, shapeRenderer, fontShader, objectShader);

        backButton.render(batch, shapeRenderer, fontShader, objectShader);
        topWLayer.render(batch, shapeRenderer, fontShader, objectShader);
    }

    // Music
    public void setMusic() {
        if (AssetLoader.getMusicState()) {
            AssetLoader.musicJungle.stop();
            AssetLoader.musicField.stop();
            AssetLoader.musicDesert.stop();
            AssetLoader.musicMountain.stop();
            AssetLoader.musicMenu.setLooping(true);
            AssetLoader.musicMenu.play();
            AssetLoader.musicMenu.setVolume(0.15f);
        }
    }

    public void setUpItems(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            setSubmenuButtonsSmartphone();
            setBackButtonSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)){
            setSubmenuButtonsTablet1610();
            setBackButtonTablet1610();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            setSubmenuButtonsTablet43();
            setBackButtonTablet43();
        }
    }

    // -------------- SMARTPHONE -----------------
    public void setBackButtonSmartphone(){
        backButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.homeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        menuButtons.add(backButton);
    }

    public void setSubmenuButtonsSmartphone(){
        arcadeButton = new MenuButton(this, gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2),
                gameHeight*2 / 3 - (Settings.PLAY_BUTTON_SIZE / 2) + 80,
                Settings.PLAY_BUTTON_SIZE,
                Settings.PLAY_BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_PLAY_BUTTON, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        arcadeButtonText = new Text(this, gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2),
                arcadeButton.getPosition().y - 30,
                Settings.PLAY_BUTTON_SIZE, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SUBMENU_ARCADE,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        challengeButton = new MenuButton(this, gameWidth / 2 - (Settings.GAMEOVER_BUTTON_SIZE / 2),
                gameHeight / 3 - (Settings.GAMEOVER_BUTTON_SIZE / 2) + 90,
                Settings.GAMEOVER_BUTTON_SIZE,
                Settings.GAMEOVER_BUTTON_SIZE + 10, AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_ORANGE_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        challengeButtonText = new Text(this, gameWidth / 2 - 50,
                challengeButton.getPosition().y - 30,
                100, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SUBMENU_CHALLENGE,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        menuButtons.add(arcadeButton);
        menuButtons.add(challengeButton);
    }

    // -------------- TABLET 16/10 -----------------
    public void setBackButtonTablet1610(){
        backButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.homeButton, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        menuButtons.add(backButton);
    }

    public void setSubmenuButtonsTablet1610(){

    }


    // -------------- TABLET 4/3 -----------------
    public void setBackButtonTablet43(){
        backButton = new MenuButton(this, 180, 170,
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.homeButton, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        menuButtons.add(backButton);
    }

    public void setSubmenuButtonsTablet43(){

    }

    // ---------------------------------------------------------

    public ArrayList<MenuButton> getMenuButtons(){
        return menuButtons;
    }

    public void goToHomeScreen() {
        menuButtons.get(0).toHomeScreen(0.6f, 0.1f);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToLevelScreen() {
        menuButtons.get(0).toLevelScreen(0.6f, 0.1f, 0);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToCharacterScreen(int part) {
        menuButtons.get(0).toCharacterScreen(0.6f, 0.1f, part);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToChallengeScreen() {
        menuButtons.get(0).toChallengeScreen(0.6f, 0.1f);
        topWLayer.fadeIn(0.6f, .1f);
    }
}
