package com.forkstone.tokens.settingsworld;

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
import com.forkstone.tokens.ui.LanguageButton;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;

import java.util.ArrayList;

/**
 * Created by sergi on 10/1/16.
 */
public class SettingsWorld extends GameWorld {

    public static final String TAG = "SettingsWorld";

    // SETTINGS
    private Settings settings;

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public SettingsWorld world;

    //GAME CAMERA
    private GameCam camera;

    // OBJECTS
    private Background background, topWLayer, subWLayer;
    private Text musicText, soundText, languageText, tutorialText;
    private MenuButton homeButton, musicButton, soundButton, languageButton, tutorialButton;
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private ArrayList<Text> languageOptions = new ArrayList<Text>();
    private ArrayList<LanguageButton> languageButtons = new ArrayList<LanguageButton>();
    private String[] languages = new String[] {Settings.LANGUAGE_ENGLISH, Settings.LANGUAGE_SPANISH};

    public enum SettingsState {
        DEFAULT, LANGUAGE
    }

    private SettingsState settingsState;

    public SettingsWorld(ButtonsGame game, ActionResolver actionResolver, float gameWidth, float gameHeight, float worldWidth, float worldHeight) {

        super(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, 0);
        settings = new Settings();
        this.game = game;
        this.actionResolver = actionResolver;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BLUE_900, 1f));
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BLUE_900, 1f));
        subWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BLUE_900, 1f));
        subWLayer.fadeOut(0f, 0f);
        topWLayer.fadeOut(.5f, .1f);

        settingsState = SettingsState.DEFAULT;

        setUpItems();

        // Tracking
        actionResolver.setTrackerScreenName("SettingsScreen");
    }

    public void update(float delta) {
        background.update(delta);
        // Music
        musicText.update(delta);
        musicButton.update(delta);
        // Sound
        soundText.update(delta);
        soundButton.update(delta);
        // Language
        languageText.update(delta);
        languageButton.update(delta);
        // Tutorial
        tutorialText.update(delta);
        tutorialButton.update(delta);

        // Languages Screen
        if (isLanguageState()){
            for (int i = 0; i < languages.length; i++){
                languageOptions.get(i).update(delta);
                languageButtons.get(i).update(delta);
            }
        }

        subWLayer.update(delta);
        homeButton.update(delta);
        topWLayer.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        background.render(batch, shapeRenderer, fontShader, objectShader);
        if(isDefaultState()){
            // Music
            musicText.render(batch, shapeRenderer, fontShader, objectShader);
            musicButton.render(batch, shapeRenderer, fontShader, objectShader);
            // Sound
            soundText.render(batch, shapeRenderer, fontShader, objectShader);
            soundButton.render(batch, shapeRenderer, fontShader, objectShader);
            // Language
            languageText.render(batch, shapeRenderer, fontShader, objectShader);
            languageButton.render(batch, shapeRenderer, fontShader, objectShader);
            // Tutorial
            tutorialText.render(batch, shapeRenderer, fontShader, objectShader);
            tutorialButton.render(batch, shapeRenderer, fontShader, objectShader);
        }

        // Languages Screen
        if(isLanguageState()){
            for (int i = 0; i < languages.length; i++){
                languageOptions.get(i).render(batch, shapeRenderer, fontShader, objectShader);
                languageButtons.get(i).render(batch, shapeRenderer, fontShader, objectShader);
            }
        }

        subWLayer.render(batch, shapeRenderer, fontShader, objectShader);
        homeButton.render(batch, shapeRenderer, fontShader, objectShader);
        topWLayer.render(batch, shapeRenderer, fontShader, objectShader);
    }

    public void setUpItems(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            setSettingsTextsSmartphone();
            setSettingsButtonsSmartphone();
            setHomeButtonSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)){
            setSettingsTextsTablet1610();
            setSettingsButtonsTablet1610();
            setHomeButtonTablet1610();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)){
            setSettingsTextsSmallSmartphone();
            setSettingsButtonsSmallSmartphone();
            setHomeButtonSmallSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            setSettingsTextsTablet43();
            setSettingsButtonsTablet43();
            setHomeButtonTablet43();
        }
    }

    public void setUpItemsLanguageScreen(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            setLanguageOptionsTextSmartphone();
            setLanguageOptionsButtonsSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)){
            setLanguageOptionsTextTablet1610();
            setLanguageOptionsButtonsTablet1610();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)){
            setLanguageOptionsTextSmallSmartphone();
            setLanguageOptionsButtonsSmallSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            setLanguageOptionsTextTablet43();
            setLanguageOptionsButtonsTablet43();
        }
    }

    // --------------- SMARTPHONE ------------------
    public void setHomeButtonSmartphone(){
        homeButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.homeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        menuButtons.add(homeButton);
    }

    public void setSettingsTextsSmartphone(){
        int topPosition = 500;
        int ySeparation = 260;

        musicText = new Text(this, 140, gameHeight/2 + topPosition, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_MUSIC_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        soundText = new Text(this, 140, gameHeight/2 + topPosition - ySeparation, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_SOUND_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        languageText = new Text(this, 140, gameHeight/2 + topPosition - (ySeparation*2), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_LANGUAGE_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        tutorialText = new Text(this, 140, gameHeight/2 + topPosition - (ySeparation*3), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    public void setSettingsButtonsSmartphone(){
        int topPosition = 555;
        int ySeparation = 260;

        // Music Button
        if(AssetLoader.getMusicState()){
            musicButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition,
                    Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.soundOnButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        } else {
            musicButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition,
                    Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.soundOffButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        }

        // Sound Button
        if (AssetLoader.getSoundState()){
            soundButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation),
                    Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.soundOnButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        } else {
            soundButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation),
                    Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.soundOffButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        }

        // Language Button
        languageButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation*2),
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.listButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        // Tutorial Button
        tutorialButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation*3),
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOOK, 1f), AssetLoader.listButtonUp, true, world.parseColor(Settings.COLOR_BOARD, 1f));

        menuButtons.add(musicButton);
        menuButtons.add(soundButton);
        menuButtons.add(languageButton);
        menuButtons.add(tutorialButton);
    }

    public void setLanguageOptionsTextSmartphone(){
        int topPosition = 500;
        int ySeparation = 260;

        languageOptions.clear();
        for (int i = 0; i < languages.length; i++){
            languageOptions.add(new Text(this, gameWidth/2 - 120, gameHeight/2 + topPosition - (ySeparation*i), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                    AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), languages[i],
                    AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left));
        }

        homeButton.setIcon(AssetLoader.backButtonUp);
        homeButton.setIconColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void setLanguageOptionsButtonsSmartphone(){
        int topPosition = 560;
        int ySeparation = 260;

        languageButtons.clear();
        for (int i = 0; i < languages.length; i++){
            if(languages[i].equals(AssetLoader.getLanguage())){
                languageButtons.add(new LanguageButton(this, 200, gameHeight/2 - (Settings.GAME_BUTTON_SIZE + 6)/2 + topPosition - (ySeparation*i),
                        Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                        AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), true));
            } else {
                languageButtons.add(new LanguageButton(this, 200, gameHeight/2 - (Settings.GAME_BUTTON_SIZE + 6)/2 + topPosition - (ySeparation*i),
                        Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                        AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), false));
            }
        }
    }

    // --------------- TABLET 16/10 ------------------
    public void setHomeButtonTablet1610(){
        homeButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.homeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        menuButtons.add(homeButton);
    }

    public void setSettingsTextsTablet1610(){
        int topPosition = 500;
        int ySeparation = 260;

        musicText = new Text(this, 140, gameHeight/2 + topPosition, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_MUSIC_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        soundText = new Text(this, 140, gameHeight/2 + topPosition - ySeparation, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_SOUND_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        languageText = new Text(this, 140, gameHeight/2 + topPosition - (ySeparation*2), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_LANGUAGE_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        tutorialText = new Text(this, 140, gameHeight/2 + topPosition - (ySeparation*3), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    public void setSettingsButtonsTablet1610(){
        int topPosition = 555;
        int ySeparation = 260;

        // Music Button
        if(AssetLoader.getMusicState()){
            musicButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition,
                    Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.soundOnButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        } else {
            musicButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition,
                    Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.soundOffButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        }

        // Sound Button
        if (AssetLoader.getSoundState()){
            soundButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation),
                    Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.soundOnButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        } else {
            soundButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation),
                    Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.soundOffButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        }

        // Language Button
        languageButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation*2),
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.listButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        // Tutorial Button
        tutorialButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation*3),
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOOK, 1f), AssetLoader.listButtonUp, true, world.parseColor(Settings.COLOR_BOARD, 1f));

        menuButtons.add(musicButton);
        menuButtons.add(soundButton);
        menuButtons.add(languageButton);
        menuButtons.add(tutorialButton);
    }

    public void setLanguageOptionsTextTablet1610(){
        int topPosition = 500;
        int ySeparation = 260;

        languageOptions.clear();
        for (int i = 0; i < languages.length; i++){
            languageOptions.add(new Text(this, gameWidth/2 - 120, gameHeight/2 + topPosition - (ySeparation*i), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                    AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), languages[i],
                    AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left));
        }

        homeButton.setIcon(AssetLoader.backButtonUp);
        homeButton.setIconColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void setLanguageOptionsButtonsTablet1610(){
        int topPosition = 560;
        int ySeparation = 260;

        languageButtons.clear();
        for (int i = 0; i < languages.length; i++){
            if(languages[i].equals(AssetLoader.getLanguage())){
                languageButtons.add(new LanguageButton(this, 200, gameHeight/2 - (Settings.GAME_BUTTON_SIZE + 6)/2 + topPosition - (ySeparation*i),
                        Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                        AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), true));
            } else {
                languageButtons.add(new LanguageButton(this, 200, gameHeight/2 - (Settings.GAME_BUTTON_SIZE + 6)/2 + topPosition - (ySeparation*i),
                        Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                        AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), false));
            }
        }
    }

    // --------------- SMALL SMARTPHONE ------------------
    public void setHomeButtonSmallSmartphone(){
        homeButton = new MenuButton(this, 180, 100,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.homeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        menuButtons.add(homeButton);
    }

    public void setSettingsTextsSmallSmartphone(){
        int topPosition = 430;
        int ySeparation = 260;

        musicText = new Text(this, 140, gameHeight/2 + topPosition, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_MUSIC_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        soundText = new Text(this, 140, gameHeight/2 + topPosition - ySeparation, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_SOUND_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        languageText = new Text(this, 140, gameHeight/2 + topPosition - (ySeparation*2), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_LANGUAGE_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        tutorialText = new Text(this, 140, gameHeight/2 + topPosition - (ySeparation*3), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_TUTORIAL_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    public void setSettingsButtonsSmallSmartphone(){
        int topPosition = 485;
        int ySeparation = 260;

        // Music Button
        if(AssetLoader.getMusicState()){
            musicButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition,
                    Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.soundOnButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        } else {
            musicButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition,
                    Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.soundOffButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        }

        // Sound Button
        if (AssetLoader.getSoundState()){
            soundButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation),
                    Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.soundOnButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        } else {
            soundButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation),
                    Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.soundOffButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        }

        // Language Button
        languageButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation*2),
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.listButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        // Tutorial Button
        tutorialButton = new MenuButton(this, gameWidth - Settings.BUTTON_SIZE - 150, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation*3),
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOOK, 1f), AssetLoader.listButtonUp, true, world.parseColor(Settings.COLOR_BOARD, 1f));

        menuButtons.add(musicButton);
        menuButtons.add(soundButton);
        menuButtons.add(languageButton);
        menuButtons.add(tutorialButton);
    }

    public void setLanguageOptionsTextSmallSmartphone(){
        int topPosition = 430;
        int ySeparation = 260;

        languageOptions.clear();
        for (int i = 0; i < languages.length; i++){
            languageOptions.add(new Text(this, gameWidth/2 - 120, gameHeight/2 + topPosition - (ySeparation*i), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                    AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), languages[i],
                    AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left));
        }

        homeButton.setIcon(AssetLoader.backButtonUp);
        homeButton.setIconColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void setLanguageOptionsButtonsSmallSmartphone(){
        int topPosition = 490;
        int ySeparation = 260;

        languageButtons.clear();
        for (int i = 0; i < languages.length; i++){
            if(languages[i].equals(AssetLoader.getLanguage())){
                languageButtons.add(new LanguageButton(this, 200, gameHeight/2 - (Settings.GAME_BUTTON_SIZE + 6)/2 + topPosition - (ySeparation*i),
                        Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                        AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), true));
            } else {
                languageButtons.add(new LanguageButton(this, 200, gameHeight/2 - (Settings.GAME_BUTTON_SIZE + 6)/2 + topPosition - (ySeparation*i),
                        Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                        AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), false));
            }
        }
    }

    // --------------- TABLET 4/3 ------------------
    public void setHomeButtonTablet43(){
        homeButton = new MenuButton(this, 180, 170,
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), AssetLoader.homeButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        menuButtons.add(homeButton);
    }

    public void setSettingsTextsTablet43(){
        int topPosition = 440;
        int ySeparation = 230;

        musicText = new Text(this, 240, gameHeight/2 + topPosition, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_MUSIC_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        soundText = new Text(this, 240, gameHeight/2 + topPosition - ySeparation, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_SOUND_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        languageText = new Text(this, 240, gameHeight/2 + topPosition - (ySeparation*2), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_LANGUAGE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        tutorialText = new Text(this, 240, gameHeight/2 + topPosition - (ySeparation*3), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SETTINGS_TUTORIAL_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    public void setSettingsButtonsTablet43(){
        int topPosition = 505;
        int ySeparation = 230;

        // Music Button
        if(AssetLoader.getMusicState()){
            musicButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 270, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 10)/2 + topPosition,
                    Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.soundOnButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        } else {
            musicButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 270, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 10)/2 + topPosition,
                    Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.soundOffButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        }

        // Sound Button
        if (AssetLoader.getSoundState()){
            soundButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 270, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 10)/2 + topPosition - (ySeparation),
                    Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.soundOnButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        } else {
            soundButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 270, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 10)/2 + topPosition - (ySeparation),
                    Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                    AssetLoader.colorButton, parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.soundOffButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        }

        // Language Button
        languageButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 270, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 10)/2 + topPosition - (ySeparation*2),
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.listButtonUp, true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        // Tutorial Button
        tutorialButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 270, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 10)/2 + topPosition - (ySeparation*3),
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOOK, 1f), AssetLoader.listButtonUp, true, world.parseColor(Settings.COLOR_BOARD, 1f));

        menuButtons.add(musicButton);
        menuButtons.add(soundButton);
        menuButtons.add(languageButton);
        menuButtons.add(tutorialButton);
    }

    public void setLanguageOptionsTextTablet43(){
        int topPosition = 430;
        int ySeparation = 230;

        languageOptions.clear();
        for (int i = 0; i < languages.length; i++){
            languageOptions.add(new Text(this, gameWidth/2 - 40, gameHeight/2 + topPosition - (ySeparation*i), gameWidth - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 40)*2, 150,
                    AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), languages[i],
                    AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left));
        }

        homeButton.setIcon(AssetLoader.backButtonUp);
        homeButton.setIconColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void setLanguageOptionsButtonsTablet43(){
        int topPosition = 500;
        int ySeparation = 230;

        languageButtons.clear();
        for (int i = 0; i < languages.length; i++){
            if(languages[i].equals(AssetLoader.getLanguage())){
                languageButtons.add(new LanguageButton(this, 300, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 6)/2 + topPosition - (ySeparation*i),
                        Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                        AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), true));
            } else {
                languageButtons.add(new LanguageButton(this, 300, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 6)/2 + topPosition - (ySeparation*i),
                        Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                        AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), false));
            }
        }
    }
    // ------------------------------------------------------------------------

    public void setLanguageScreen(){
        settingsState = SettingsState.LANGUAGE;
        setUpItemsLanguageScreen();
        subWLayer.fadeOut(0.5f, .1f);
    }

    public void setDefaultScreen(){
        settingsState = SettingsState.DEFAULT;
        subWLayer.fadeOut(0.5f, .1f);
        // update values
        settings = new Settings();
        musicText.setText(Settings.SETTINGS_MUSIC_TEXT);
        soundText.setText(Settings.SETTINGS_SOUND_TEXT);
        languageText.setText(Settings.SETTINGS_LANGUAGE_TEXT);
        homeButton.setIcon(AssetLoader.homeButtonUp);
        homeButton.setIconColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void changeSettingsScreen(){
        subWLayer.fadeIn(0.5f, 0f);
    }

    public boolean isDefaultState(){
        return settingsState == SettingsState.DEFAULT;
    }

    public boolean isLanguageState(){
        return settingsState == SettingsState.LANGUAGE;
    }

    public void changeSoundButtonState(int position){
        if(position == 0){
            if(AssetLoader.getMusicState()){
                menuButtons.get(position).setIcon(AssetLoader.soundOffButtonUp);
                menuButtons.get(position).setColor(parseColor(Settings.COLOR_RED_500, 1f));
                AssetLoader.setMusicState(false);
                AssetLoader.musicSoundtrack.stop();
            } else {
                menuButtons.get(position).setIcon(AssetLoader.soundOnButtonUp);
                menuButtons.get(position).setColor(parseColor(Settings.COLOR_GREEN_500, 1f));
                AssetLoader.setMusicState(true);
                AssetLoader.musicSoundtrack.play();
            }
        } else if(position == 1){
            if(AssetLoader.getSoundState()){
                menuButtons.get(position).setIcon(AssetLoader.soundOffButtonUp);
                menuButtons.get(position).setColor(parseColor(Settings.COLOR_RED_500, 1f));
                AssetLoader.setSoundState(false);
            } else {
                menuButtons.get(position).setIcon(AssetLoader.soundOnButtonUp);
                menuButtons.get(position).setColor(parseColor(Settings.COLOR_GREEN_500, 1f));
                AssetLoader.setSoundState(true);
            }
        }
    }

    public ArrayList<MenuButton> getMenuButtons(){
        return menuButtons;
    }

    public ArrayList<LanguageButton> getLanguageButtons() {
        return languageButtons;
    }

    public ArrayList<Text> getLanguageOptions() {
        return languageOptions;
    }

    public String[] getLanguages(){
        return languages;
    }

    public void goToHomeScreen() {
        homeButton.toHomeScreen(0.6f, 0.1f);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToTutorialScreen(String type, int part) {
        homeButton.toTutorialScreen(0.6f, 0.1f, type, part);
        topWLayer.setColor(parseColor(Settings.COLOR_BOOK, 1f));
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToTutorial2Screen(String type, int part) {
        homeButton.toTutorial2Screen(0.6f, 0.1f, type, part);
        topWLayer.setColor(parseColor(Settings.COLOR_BOOK, 1f));
        topWLayer.fadeIn(0.6f, .1f);
    }
}
