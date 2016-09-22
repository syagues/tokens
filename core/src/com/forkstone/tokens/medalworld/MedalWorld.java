package com.forkstone.tokens.medalworld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.Background;
import com.forkstone.tokens.gameobjects.Confetti;
import com.forkstone.tokens.gameobjects.GameObject;
import com.forkstone.tokens.gameworld.GameCam;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.tweens.SpriteAccessor;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.Text;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

/**
 * Created by sergi on 21/1/16.
 */
public class MedalWorld extends GameWorld {

    public static final String TAG = "MedalWorld";

    // SETTINGS
    private Settings settings;

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public MedalWorld world;

    //GAME CAMERA
    private GameCam camera;

    // VARIABLES
    private int numberOfConfettis = 300;
    private int district;

    // OBJECTS
    private Background background, topWLayer, drawedBack;
    private MenuButton nextButton;
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private Text text, continueText;
    private GameObject medal;
    public final ArrayList<Confetti> confettis = new ArrayList<Confetti>();

    public MedalWorld(ButtonsGame game, ActionResolver actionResolver, float gameWidth, float gameHeight, float worldWidth, float worldHeight, int district) {

        super(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, 0);
        settings = new Settings();
        this.game = game;
        this.actionResolver = actionResolver;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.district = district;

        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        topWLayer.fadeOut(.5f, .1f);

        setDistrictMedal(district);

        setConfetti();

        Tween.to(medal.getSprite(), SpriteAccessor.SCALE, 1f).target(0.1f)
                .target(0.98f, 1f)
                .repeatYoyo(1000, 0f)
                .ease(TweenEquations.easeInOutSine).start(medal.getManager());

        if(AssetLoader.getSoundState()) AssetLoader.soundCelebration.play();
    }

    public void update(float delta) {
        background.update(delta);
        drawedBack.update(delta);
        for (Confetti confetti : confettis){
            confetti.update(delta);
        }
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
        for (Confetti confetti : confettis){
            confetti.render(batch, shapeRenderer, fontShader);
        }
        continueText.render(batch, shapeRenderer, fontShader, objectShader);
        nextButton.render(batch, shapeRenderer, fontShader, objectShader);
        topWLayer.render(batch, shapeRenderer, fontShader, objectShader);
    }

    private void setDistrictMedal(int district){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            switch (district){
                case 1:
                    setDistrictOneMedalSmartphone();
                    break;
                case 2:
                    setDistrictTwoMedalSmartphone();
                    break;
                case 3:
                    setDistrictThreeMedalSmartphone();
                    break;
                case 4:
                    setDistrictFourMedalSmartphone();
                    break;
                case 5:
                    setDistrictFiveMedalSmartphone();
                    break;
                case 6:
                    setDistrictSixMedalSmartphone();
                    break;
                case 7:
                    setDistrictSevenMedalSmartphone();
                    break;
                case 8:
                    setDistrictEightMedalSmartphone();
                    break;
                case 9:
                    setDistrictNineMedalSmartphone();
                    break;
                case 10:
                    setDistrictTenMedalSmartphone();
                    break;
            }
            setButtonSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)){
            switch (district){
                case 1:
                    setDistrictOneMedalTablet1610();
                    break;
                case 2:
                    setDistrictTwoMedalTablet1610();
                    break;
                case 3:
                    setDistrictThreeMedalTablet1610();
                    break;
                case 4:
                    setDistrictFourMedalTablet1610();
                    break;
                case 5:
                    setDistrictFiveMedalTablet1610();
                    break;
                case 6:
                    setDistrictSixMedalTablet1610();
                    break;
                case 7:
                    setDistrictSevenMedalTablet1610();
                    break;
                case 8:
                    setDistrictEightMedalTablet1610();
                    break;
                case 9:
                    setDistrictNineMedalTablet1610();
                    break;
                case 10:
                    setDistrictTenMedalTablet1610();
                    break;
            }
            setButtonTablet1610();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)){
            switch (district){
                case 1:
                    setDistrictOneMedalSmallSmartphone();
                    break;
                case 2:
                    setDistrictTwoMedalSmallSmartphone();
                    break;
                case 3:
                    setDistrictThreeMedalSmallSmartphone();
                    break;
                case 4:
                    setDistrictFourMedalSmallSmartphone();
                    break;
                case 5:
                    setDistrictFiveMedalSmallSmartphone();
                    break;
                case 6:
                    setDistrictSixMedalSmallSmartphone();
                    break;
                case 7:
                    setDistrictSevenMedalSmallSmartphone();
                    break;
                case 8:
                    setDistrictEightMedalSmallSmartphone();
                    break;
                case 9:
                    setDistrictNineMedalSmallSmartphone();
                    break;
                case 10:
                    setDistrictTenMedalSmallSmartphone();
                    break;
            }
            setButtonSmallSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            switch (district){
                case 1:
                    setDistrictOneMedalTablet43();
                    break;
                case 2:
                    setDistrictTwoMedalTablet43();
                    break;
                case 3:
                    setDistrictThreeMedalTablet43();
                    break;
                case 4:
                    setDistrictFourMedalTablet43();
                    break;
                case 5:
                    setDistrictFiveMedalTablet43();
                    break;
                case 6:
                    setDistrictSixMedalTablet43();
                    break;
                case 7:
                    setDistrictSevenMedalTablet43();
                    break;
                case 8:
                    setDistrictEightMedalTablet43();
                    break;
                case 9:
                    setDistrictNineMedalTablet43();
                    break;
                case 10:
                    setDistrictTenMedalTablet43();
                    break;
            }
            setButtonTablet43();
        }
    }

    // --------------- SMARTPHONE -----------------
    private void setButtonSmartphone(){
        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictOneMedalSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundJungle, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalJungle, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALONE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictTwoMedalSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundField, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalField, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALTWO_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictThreeMedalSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundDesert, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalDesert, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALTHREE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictFourMedalSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMountain, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalMountain, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALFOUR_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictFiveMedalSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundFishers, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalFishers, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALFIVE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictSixMedalSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMusicians, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalMusicians, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALSIX_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictSevenMedalSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMine, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalMine, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALSEVEN_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictEightMedalSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundOriental, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalOriental, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALEIGHT_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictNineMedalSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundIceland, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalIceland, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALNINE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictTenMedalSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundVikings, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalVikings, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALTEN_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    // --------------- TABLET 16/10 -----------------
    private void setButtonTablet1610(){
        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictOneMedalTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundJungle, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalJungle, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALONE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictTwoMedalTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundField, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalField, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALTWO_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictThreeMedalTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundDesert, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalDesert, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALTHREE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictFourMedalTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMountain, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalMountain, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALFOUR_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictFiveMedalTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundFishers, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalFishers, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALFIVE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictSixMedalTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMusicians, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalMusicians, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALSIX_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictSevenMedalTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMine, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalMine, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALSEVEN_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictEightMedalTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundOriental, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalOriental, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALEIGHT_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictNineMedalTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundIceland, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalIceland, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALNINE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictTenMedalTablet1610(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundVikings, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalVikings, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALTEN_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    // --------------- SMALL SMARTPHONE -----------------
    private void setButtonSmallSmartphone(){
        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 100,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictOneMedalSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundJungle, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalJungle, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALONE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictTwoMedalSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundField, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalField, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALTWO_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictThreeMedalSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundDesert, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalDesert, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALTHREE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictFourMedalSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMountain, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalMountain, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALFOUR_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictFiveMedalSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundFishers, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalFishers, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALFIVE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictSixMedalSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMusicians, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalMusicians, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALSIX_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictSevenMedalSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMine, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalMine, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALSEVEN_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictEightMedalSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundOriental, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalOriental, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALEIGHT_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictNineMedalSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundIceland, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalIceland, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALNINE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictTenMedalSmallSmartphone(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundVikings, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 250, gameHeight*2/3 - 250, 500, 510, AssetLoader.medalVikings, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALTEN_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    // --------------- TABLET 4/3 -----------------
    private void setButtonTablet43(){
        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 170,
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 70, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictOneMedalTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundJungle, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight*2/3 - 150, 400, 408, AssetLoader.medalJungle, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALONE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictTwoMedalTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundField, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight*2/3 - 150, 400, 408, AssetLoader.medalField, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALTWO_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictThreeMedalTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundDesert, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight*2/3 - 150, 400, 408, AssetLoader.medalDesert, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALTHREE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictFourMedalTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMountain, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight*2/3 - 150, 400, 408, AssetLoader.medalMountain, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALFOUR_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictFiveMedalTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundFishers, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight*2/3 - 150, 400, 408, AssetLoader.medalFishers, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALFIVE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictSixMedalTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMusicians, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight*2/3 - 150, 400, 408, AssetLoader.medalMusicians, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALSIX_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictSevenMedalTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMine, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight*2/3 - 150, 400, 408, AssetLoader.medalMine, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALSEVEN_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictEightMedalTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundOriental, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight*2/3 - 150, 400, 408, AssetLoader.medalOriental, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALEIGHT_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictNineMedalTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundIceland, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight*2/3 - 150, 400, 408, AssetLoader.medalIceland, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALNINE_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    private void setDistrictTenMedalTablet43(){
        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundVikings, world.parseColor(Settings.COLOR_WHITE, 1f));

        medal = new GameObject(this, gameWidth/2 - 200, gameHeight*2/3 - 150, 400, 408, AssetLoader.medalVikings, parseColor(Settings.COLOR_WHITE, 1f));

        text = new Text(this, 40, gameHeight/3, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.MEDALTEN_TEXT,
                AssetLoader.steelfishFontL, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }
    // ------------------------------------------------------

    public void setConfetti(){
        for (int i = 0; i < numberOfConfettis; i++){
            confettis.add(new Confetti(this));
        }
    }

    public int getDistrict(){
        return district;
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

    public void goToStoryScreen(int storyPart) {
        nextButton.toStoryScreen(0.6f, 0.1f, storyPart);
        topWLayer.fadeIn(0.6f, .1f);
        AssetLoader.soundCelebration.stop();
    }
}
