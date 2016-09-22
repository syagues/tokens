package com.forkstone.tokens.storyworld;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.Background;
import com.forkstone.tokens.gameobjects.GameObject;
import com.forkstone.tokens.gameworld.GameCam;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;

import java.util.ArrayList;

/**
 * Created by sergi on 21/1/16.
 */
public class StoryWorld extends GameWorld {

    public static final String TAG = "StoryWorld";

    // SETTINGS
    private Settings settings;

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public StoryWorld world;

    //GAME CAMERA
    private GameCam camera;

    // VARIABLES
    public int part;

    // OBJECTS
    private Background background, topWLayer, drawedBack;
    private MenuButton nextButton;
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private Text text, continueText;
    private GameObject medal;

    public StoryWorld(ButtonsGame game, ActionResolver actionResolver, float gameWidth, float gameHeight, float worldWidth, float worldHeight, int part) {

        super(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, 0);
        settings = new Settings();
        this.game = game;
        this.actionResolver = actionResolver;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.part = part;

        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        topWLayer.fadeOut(.5f, .1f);

        setStoryPart(part);

        // Tracking
        actionResolver.setTrackerScreenName("StoryScreen: Part " + part);

        // Music
//        setMusic(part);
    }

    public void update(float delta) {
        background.update(delta);
        drawedBack.update(delta);
        medal.update(delta);
        text.update(delta);
        continueText.update(delta);
        nextButton.update(delta);
        topWLayer.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        background.render(batch, shapeRenderer, fontShader, objectShader);
        drawedBack.render(batch, shapeRenderer, fontShader, objectShader);
        medal.render(batch, shapeRenderer, fontShader, objectShader);
        text.render(batch, shapeRenderer, fontShader, objectShader);
        continueText.render(batch, shapeRenderer, fontShader, objectShader);
        nextButton.render(batch, shapeRenderer, fontShader, objectShader);
        topWLayer.render(batch, shapeRenderer, fontShader, objectShader);
    }

    private void setMusic(int part){
        if (AssetLoader.getMusicState()) {
            AssetLoader.musicMenu.stop();
            Music districtMusic;
            switch (part){
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

    private void setStoryPart(int part){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            switch (part){
                case 1:
                    setDistrictOneDescriptionSmartphone();
                    break;
                case 2:
                    setDistrictTwoDescriptionSmartphone();
                    break;
                case 3:
                    setDistrictThreeDescriptionSmartphone();
                    break;
                case 4:
                    setDistrictFourDescriptionSmartphone();
                    break;
                case 5:
                    setDistrictFiveDescriptionSmartphone();
                    break;
                case 6:
                    setDistrictSixDescriptionSmartphone();
                    break;
                case 7:
                    setDistrictSevenDescriptionSmartphone();
                    break;
                case 8:
                    setDistrictEightDescriptionSmartphone();
                    break;
                case 9:
                    setDistrictNineDescriptionSmartphone();
                    break;
                case 10:
                    setDistrictTenDescriptionSmartphone();
                    break;
            }
            setContinueButtonSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)){
            switch (part){
                case 1:
                    setDistrictOneDescriptionTablet1610();
                    break;
                case 2:
                    setDistrictTwoDescriptionTablet1610();
                    break;
                case 3:
                    setDistrictThreeDescriptionTablet1610();
                    break;
                case 4:
                    setDistrictFourDescriptionTablet1610();
                    break;
                case 5:
                    setDistrictFiveDescriptionTablet1610();
                    break;
                case 6:
                    setDistrictSixDescriptionTablet1610();
                    break;
                case 7:
                    setDistrictSevenDescriptionTablet1610();
                    break;
                case 8:
                    setDistrictEightDescriptionTablet1610();
                    break;
                case 9:
                    setDistrictNineDescriptionTablet1610();
                    break;
                case 10:
                    setDistrictTenDescriptionTablet1610();
                    break;
            }
            setContinueButtonTablet1610();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)){
            switch (part){
                case 1:
                    setDistrictOneDescriptionSmallSmartphone();
                    break;
                case 2:
                    setDistrictTwoDescriptionSmallSmartphone();
                    break;
                case 3:
                    setDistrictThreeDescriptionSmallSmartphone();
                    break;
                case 4:
                    setDistrictFourDescriptionSmallSmartphone();
                    break;
                case 5:
                    setDistrictFiveDescriptionSmallSmartphone();
                    break;
                case 6:
                    setDistrictSixDescriptionSmallSmartphone();
                    break;
                case 7:
                    setDistrictSevenDescriptionSmallSmartphone();
                    break;
                case 8:
                    setDistrictEightDescriptionSmallSmartphone();
                    break;
                case 9:
                    setDistrictNineDescriptionSmallSmartphone();
                    break;
                case 10:
                    setDistrictTenDescriptionSmallSmartphone();
                    break;
            }
            setContinueButtonSmallSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            switch (part){
                case 1:
                    setDistrictOneDescriptionTablet43();
                    break;
                case 2:
                    setDistrictTwoDescriptionTablet43();
                    break;
                case 3:
                    setDistrictThreeDescriptionTablet43();
                    break;
                case 4:
                    setDistrictFourDescriptionTablet43();
                    break;
                case 5:
                    setDistrictFiveDescriptionTablet43();
                    break;
                case 6:
                    setDistrictSixDescriptionTablet43();
                    break;
                case 7:
                    setDistrictSevenDescriptionTablet43();
                    break;
                case 8:
                    setDistrictEightDescriptionTablet43();
                    break;
                case 9:
                    setDistrictNineDescriptionTablet43();
                    break;
                case 10:
                    setDistrictTenDescriptionTablet43();
                    break;
            }
            setContinueButtonTablet43();
        }
    }

    // --------------- SMARTPHONE ----------------
    private void setContinueButtonSmartphone(){
        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        medal.fadeIn(1.5f, 0f);
        text.effectY(-300, text.getPosition().y, 1f, 0f);
    }

    private void setDistrictOneDescriptionSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundJungle, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalJungleGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_ONE_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictTwoDescriptionSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundField, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalFieldGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_TWO_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictThreeDescriptionSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundDesert, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalDesertGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_THREE_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictFourDescriptionSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMountain, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalMountainGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_FOUR_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictFiveDescriptionSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundFishers, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalFishersGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_FIVE_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictSixDescriptionSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMusicians, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalMusiciansGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_SIX_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictSevenDescriptionSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMine, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalMineGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_SEVEN_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictEightDescriptionSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundOriental, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalOrientalGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_EIGHT_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictNineDescriptionSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundIceland, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalIcelandGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_NINE_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictTenDescriptionSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundVikings, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalVikingsGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_TEN_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    // --------------- TABLET 16/10 ----------------
    private void setContinueButtonTablet1610(){
        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        medal.fadeIn(1.5f, 0f);
        text.effectY(-300, text.getPosition().y, 1f, 0f);
    }

    private void setDistrictOneDescriptionTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundJungle, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalJungleGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_ONE_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictTwoDescriptionTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundField, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalFieldGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_TWO_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictThreeDescriptionTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundDesert, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalDesertGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_THREE_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictFourDescriptionTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMountain, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalMountainGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_FOUR_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictFiveDescriptionTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundFishers, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalFishersGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_FIVE_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictSixDescriptionTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMusicians, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalMusiciansGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_SIX_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictSevenDescriptionTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMine, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalMineGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_SEVEN_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictEightDescriptionTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundOriental, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalOrientalGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_EIGHT_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictNineDescriptionTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundIceland, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalIcelandGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_NINE_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictTenDescriptionTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundVikings, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalVikingsGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 50, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_TEN_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    // --------------- SMALL SMARTPHONE ----------------
    private void setContinueButtonSmallSmartphone(){
        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 100,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 60, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        medal.fadeIn(1.5f, 0f);
        text.effectY(-300, text.getPosition().y, 1f, 0f);
    }

    private void setDistrictOneDescriptionSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundJungle, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalJungleGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_ONE_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictTwoDescriptionSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundField, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalFieldGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_TWO_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictThreeDescriptionSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundDesert, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalDesertGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_THREE_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictFourDescriptionSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMountain, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalMountainGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_FOUR_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictFiveDescriptionSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundFishers, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalFishersGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_FIVE_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictSixDescriptionSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMusicians, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalMusiciansGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_SIX_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictSevenDescriptionSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMine, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalMineGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_SEVEN_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictEightDescriptionSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundOriental, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalOrientalGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_EIGHT_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictNineDescriptionSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundIceland, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalIcelandGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_NINE_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictTenDescriptionSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundVikings, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight - 600, 400, 410, AssetLoader.medalVikingsGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_TEN_DESCRIPTION,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    // --------------- TABLET 4/3 ----------------
    private void setContinueButtonTablet43(){
        nextButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 180, 170,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.SMALL_BUTTON_SIZE_TABLET43/2 - 150, nextButton.getPosition().y + (Settings.SMALL_BUTTON_SIZE_TABLET43 + 6)/2 - 70, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        medal.fadeIn(1.5f, 0f);
        text.effectY(-300, text.getPosition().y, 1f, 0f);
    }

    private void setDistrictOneDescriptionTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundJungle, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 150, gameHeight - 520, 300, 307, AssetLoader.medalJungleGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_ONE_DESCRIPTION,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictTwoDescriptionTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundField, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 150, gameHeight - 520, 300, 307, AssetLoader.medalFieldGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_TWO_DESCRIPTION,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictThreeDescriptionTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundDesert, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 150, gameHeight - 520, 300, 307, AssetLoader.medalDesertGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_THREE_DESCRIPTION,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictFourDescriptionTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMountain, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 150, gameHeight - 520, 300, 307, AssetLoader.medalMountainGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_FOUR_DESCRIPTION,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictFiveDescriptionTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundFishers, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 150, gameHeight - 520, 300, 307, AssetLoader.medalFishersGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_FIVE_DESCRIPTION,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictSixDescriptionTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMusicians, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 150, gameHeight - 520, 300, 307, AssetLoader.medalMusiciansGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_SIX_DESCRIPTION,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictSevenDescriptionTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMine, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 150, gameHeight - 520, 300, 307, AssetLoader.medalMineGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_SEVEN_DESCRIPTION,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictEightDescriptionTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundOriental, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 150, gameHeight - 520, 300, 307, AssetLoader.medalOrientalGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_EIGHT_DESCRIPTION,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictNineDescriptionTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundIceland, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 150, gameHeight - 520, 300, 307, AssetLoader.medalIcelandGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_NINE_DESCRIPTION,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    private void setDistrictTenDescriptionTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundVikings, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 150, gameHeight - 520, 300, 307, AssetLoader.medalVikingsGrey, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/2 - 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.DISTRICT_TEN_DESCRIPTION,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }
    // ---------------------------------------------------------------------------

    public int getPart(){
        return part;
    }

    public ArrayList<MenuButton> getMenuButtons(){
        return menuButtons;
    }

    public void goToHomeScreen() {
        nextButton.toHomeScreen(0.6f, 0.1f);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToGameScreen(int i) {
        nextButton.toGameScreen(0.6f, 0.1f, i);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToTutorialScreen(String type, int part) {
        menuButtons.get(0).toTutorialScreen(0.6f, 0.1f, type, part);
        if(isRunning()) topWLayer.fadeIn(0.5f, .1f);
    }
}
