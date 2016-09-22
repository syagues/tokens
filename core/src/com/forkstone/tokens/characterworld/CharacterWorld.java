package com.forkstone.tokens.characterworld;

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
public class CharacterWorld extends GameWorld {

    public static final String TAG = "CharacterWorld";

    // SETTINGS
    private Settings settings;

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public CharacterWorld world;

    //GAME CAMERA
    private GameCam camera;

    // VARIABLES
    private int part;

    // OBJECTS
    private Background background, topWLayer, drawedBack;
    private MenuButton nextButton, exitButton;
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private Text boxText, mapText, handbookText, continueText, skipText;
    private GameObject character, box, map, handbook;

    public CharacterWorld(ButtonsGame game, ActionResolver actionResolver, float gameWidth, float gameHeight, float worldWidth, float worldHeight, int part) {

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

        setPart(part);

        // Tracking
        actionResolver.setTrackerScreenName("CharacterScreen: Part " + part);
    }

    public void update(float delta) {
        background.update(delta);
        drawedBack.update(delta);
        box.update(delta);
        boxText.update(delta);
        character.update(delta);
        mapText.update(delta);
        map.update(delta);
        handbookText.update(delta);
        handbook.update(delta);
        continueText.update(delta);
        skipText.update(delta);
        exitButton.update(delta);
        nextButton.update(delta);
        topWLayer.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        background.render(batch, shapeRenderer, fontShader, objectShader);
        drawedBack.render(batch, shapeRenderer, fontShader, objectShader);
        if(part <= 3){
            box.render(batch, shapeRenderer, fontShader, objectShader);
            boxText.render(batch, shapeRenderer, fontShader, objectShader);
            character.render(batch, shapeRenderer, fontShader, objectShader);
        } else if(part == 4){
            mapText.render(batch, shapeRenderer, fontShader, objectShader);
            map.render(batch, shapeRenderer, fontShader, objectShader);
        } else if(part == 5){
            handbookText.render(batch, shapeRenderer, fontShader, objectShader);
            handbook.render(batch, shapeRenderer, fontShader, objectShader);
        }
        skipText.render(batch, shapeRenderer, fontShader, objectShader);
        exitButton.render(batch, shapeRenderer, fontShader, objectShader);
        continueText.render(batch, shapeRenderer, fontShader, objectShader);
        nextButton.render(batch, shapeRenderer, fontShader, objectShader);
        topWLayer.render(batch, shapeRenderer, fontShader, objectShader);
    }

    private void setPart(int part){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)){
            switch (part){
                case 1:
                    setCharacterPartOneSmartphone();
                    break;
                case 2:
                    setCharacterPartTwoSmartphone();
                    break;
                case 3:
                    setCharacterPartThreeSmartphone();
                    break;
                case 4:
                    setCharacterPartFourSmartphone();
                    break;
                case 5:
                    setCharacterPartFiveSmartphone();
                    break;
            }
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)){
            switch (part){
                case 1:
                    setCharacterPartOneTablet1610();
                    break;
                case 2:
                    setCharacterPartTwoTablet1610();
                    break;
                case 3:
                    setCharacterPartThreeTablet1610();
                    break;
                case 4:
                    setCharacterPartFourTablet1610();
                    break;
                case 5:
                    setCharacterPartFiveTablet1610();
                    break;
            }
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)){
            switch (part){
                case 1:
                    setCharacterPartOneSmallSmartphone();
                    break;
                case 2:
                    setCharacterPartTwoSmallSmartphone();
                    break;
                case 3:
                    setCharacterPartThreeSmallSmartphone();
                    break;
                case 4:
                    setCharacterPartFourSmallSmartphone();
                    break;
                case 5:
                    setCharacterPartFiveSmallSmartphone();
                    break;
            }
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            switch (part){
                case 1:
                    setCharacterPartOneTablet43();
                    break;
                case 2:
                    setCharacterPartTwoTablet43();
                    break;
                case 3:
                    setCharacterPartThreeTablet43();
                    break;
                case 4:
                    setCharacterPartFourTablet43();
                    break;
                case 5:
                    setCharacterPartFiveTablet43();
                    break;
            }
        }
    }

    // --------- SMARTPHONE ---------
    private void setCharacterPartOneSmartphone(){
        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMap, world.parseColor(Settings.COLOR_WHITE, 1f));

        box = new GameObject(this, 150, gameHeight*2/3 + 50, gameWidth - 300, 400, AssetLoader.characterBox, parseColor(Settings.COLOR_WHITE, 1f));
        boxText = new Text(this, 150, partOneTextPositionSmartphone(), gameWidth - 300, 400,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_TEXT_ONE,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        character = new GameObject(this, gameWidth/2 - 400, gameHeight/3 - 150, 800, 800,
                AssetLoader.characterDictator, parseColor(Settings.COLOR_WHITE, 1f));

        setOtherItemsSmartphone();

        exitButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(exitButton);

        skipText = new Text(this, exitButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, exitButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_SKIPTEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

    }

    public float partOneTextPositionSmartphone(){
        if(AssetLoader.getLanguage().equals(Settings.LANGUAGE_ENGLISH)){
            return gameHeight*2/3 + 40;
        } else {
            return gameHeight*2/3 + 60;
        }
    }

    public void setCharacterPartTwoSmartphone(){
        box = new GameObject(this, 150, gameHeight*2/3 + 100, gameWidth - 300, 300, AssetLoader.characterBox, parseColor(Settings.COLOR_WHITE, 1f));
        boxText = new Text(this, 150, partTwoTextPositionSmartphone(), gameWidth - 300, 400,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_TEXT_TWO,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);
    }

    public float partTwoTextPositionSmartphone(){
        if(AssetLoader.getLanguage().equals(Settings.LANGUAGE_ENGLISH)){
            return gameHeight*2/3 - 10;
        } else {
            return gameHeight*2/3 - 10;
        }
    }

    public void setCharacterPartThreeSmartphone(){
        box = new GameObject(this, 150, gameHeight*2/3 + 70, gameWidth - 300, 360, AssetLoader.characterBox, parseColor(Settings.COLOR_WHITE, 1f));
        boxText = new Text(this, 150, gameHeight*2/3 + 10, gameWidth - 300, 400,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_TEXT_THREE,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);
    }

    public void setCharacterPartFourSmartphone(){
        topWLayer.fadeOut(.5f, .5f);

        mapText = new Text(this, 40, gameHeight/2 - 100, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_TWO_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        mapText.effectY(-300, mapText.getPosition().y, 1f, 0f);

        map = new GameObject(this, gameWidth/2 - 400, gameHeight*2/3 - 100, 800, 400,
                AssetLoader.characterMap, parseColor(Settings.COLOR_WHITE, 1f));
        map.fadeIn(.5f, .6f);
    }

    public void setCharacterPartFiveSmartphone(){
        topWLayer.fadeOut(.5f, .5f);

        handbookText = new Text(this, 40, gameHeight*2/3 + 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        handbook = new GameObject(this, gameWidth/2 - 225, gameHeight/2 - 300 + 50, 450, 600,
                AssetLoader.characterHandbook, parseColor(Settings.COLOR_WHITE, 1f));

        exitButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(exitButton);

        continueText.setText(Settings.CHARACTER_PART_THREE_GOTEXT);

        skipText = new Text(this, exitButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, exitButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_SKIPTEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    public void setOtherItemsSmartphone(){
        mapText = new Text(this, 40, -300, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_TWO_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        map = new GameObject(this, gameWidth/2 - 400, gameHeight+500, 800, 400,
                AssetLoader.characterDictator, parseColor(Settings.COLOR_WHITE, 1f));

        handbookText = new Text(this, 40, -300, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_TWO_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        handbook = new GameObject(this, gameWidth/2 - 400, gameHeight+500, 800, 400,
                AssetLoader.characterDictator, parseColor(Settings.COLOR_WHITE, 1f));

        exitButton = new MenuButton(this, -300, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));

        skipText = new Text(this, 40, -300, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_SKIPTEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    // --------- TABLET 16/10 ---------
    private void setCharacterPartOneTablet1610(){
        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMap, world.parseColor(Settings.COLOR_WHITE, 1f));

        box = new GameObject(this, 150, gameHeight*2/3 + 50, gameWidth - 300, 400, AssetLoader.characterBox, parseColor(Settings.COLOR_WHITE, 1f));
        boxText = new Text(this, 150, partOneTextPositionTablet1610(), gameWidth - 300, 400,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_TEXT_ONE,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        character = new GameObject(this, gameWidth/2 - 400, gameHeight/3 - 150, 800, 800,
                AssetLoader.characterDictator, parseColor(Settings.COLOR_WHITE, 1f));

        setOtherItemsTablet1610();

        exitButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(exitButton);

        skipText = new Text(this, exitButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, exitButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_SKIPTEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    public float partOneTextPositionTablet1610(){
        if(AssetLoader.getLanguage().equals(Settings.LANGUAGE_ENGLISH)){
            return gameHeight*2/3 + 40;
        } else {
            return gameHeight*2/3 + 60;
        }
    }

    public void setCharacterPartTwoTablet1610(){
        box = new GameObject(this, 150, gameHeight*2/3 + 100, gameWidth - 300, 300, AssetLoader.characterBox, parseColor(Settings.COLOR_WHITE, 1f));
        boxText = new Text(this, 150, partTwoTextPositionTablet1610(), gameWidth - 300, 400,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_TEXT_TWO,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);
    }

    public float partTwoTextPositionTablet1610(){
        if(AssetLoader.getLanguage().equals(Settings.LANGUAGE_ENGLISH)){
            return gameHeight*2/3 - 10;
        } else {
            return gameHeight*2/3 - 10;
        }
    }

    public void setCharacterPartThreeTablet1610(){
        box = new GameObject(this, 150, gameHeight*2/3 + 70, gameWidth - 300, 360, AssetLoader.characterBox, parseColor(Settings.COLOR_WHITE, 1f));
        boxText = new Text(this, 150, gameHeight*2/3 + 10, gameWidth - 300, 400,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_TEXT_THREE,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);
    }

    public void setCharacterPartFourTablet1610(){
        topWLayer.fadeOut(.5f, .5f);

        mapText = new Text(this, 40, gameHeight/2 - 100, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_TWO_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        mapText.effectY(-300, mapText.getPosition().y, 1f, 0f);

        map = new GameObject(this, gameWidth/2 - 400, gameHeight*2/3 - 100, 800, 400,
                AssetLoader.characterMap, parseColor(Settings.COLOR_WHITE, 1f));
        map.fadeIn(.5f, .6f);
    }

    public void setCharacterPartFiveTablet1610(){
        topWLayer.fadeOut(.5f, .5f);

        handbookText = new Text(this, 40, gameHeight*2/3 + 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        handbook = new GameObject(this, gameWidth/2 - 225, gameHeight/2 - 300 + 50, 450, 600,
                AssetLoader.characterHandbook, parseColor(Settings.COLOR_WHITE, 1f));

        exitButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(exitButton);

        continueText.setText(Settings.CHARACTER_PART_THREE_GOTEXT);

        skipText = new Text(this, exitButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, exitButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_SKIPTEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    public void setOtherItemsTablet1610(){
        mapText = new Text(this, 40, -300, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_TWO_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        map = new GameObject(this, gameWidth/2 - 400, gameHeight+500, 800, 400,
                AssetLoader.characterDictator, parseColor(Settings.COLOR_WHITE, 1f));

        handbookText = new Text(this, 40, -300, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_TWO_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        handbook = new GameObject(this, gameWidth/2 - 400, gameHeight+500, 800, 400,
                AssetLoader.characterDictator, parseColor(Settings.COLOR_WHITE, 1f));

        exitButton = new MenuButton(this, -300, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));

        skipText = new Text(this, 40, -300, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_SKIPTEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    // --------- SMALL SMARTPHONE ---------
    private void setCharacterPartOneSmallSmartphone(){
        nextButton = new MenuButton(this, gameWidth - Settings.GAME_BUTTON_SIZE - 180, 100,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, nextButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMap, world.parseColor(Settings.COLOR_WHITE, 1f));

        box = new GameObject(this, 150, gameHeight*2/3 + 50, gameWidth - 300, 400, AssetLoader.characterBox, parseColor(Settings.COLOR_WHITE, 1f));
        boxText = new Text(this, 150, partOneTextPositionTablet1610(), gameWidth - 300, 400,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_TEXT_ONE,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        character = new GameObject(this, gameWidth/2 - 400, gameHeight/3 - 170, 800, 800,
                AssetLoader.characterDictator, parseColor(Settings.COLOR_WHITE, 1f));

        setOtherItemsSmallSmartphone();

        exitButton = new MenuButton(this, 180, 100,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(exitButton);

        skipText = new Text(this, exitButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, exitButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_SKIPTEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    public float partOneTextPositionSmallSmartphone(){
        if(AssetLoader.getLanguage().equals(Settings.LANGUAGE_ENGLISH)){
            return gameHeight*2/3 + 40;
        } else {
            return gameHeight*2/3 + 60;
        }
    }

    public void setCharacterPartTwoSmallSmartphone(){
        box = new GameObject(this, 150, gameHeight*2/3 + 100, gameWidth - 300, 300, AssetLoader.characterBox, parseColor(Settings.COLOR_WHITE, 1f));
        boxText = new Text(this, 150, partTwoTextPositionTablet1610(), gameWidth - 300, 400,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_TEXT_TWO,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);
    }

    public float partTwoTextPositionSmallSmartphone(){
        if(AssetLoader.getLanguage().equals(Settings.LANGUAGE_ENGLISH)){
            return gameHeight*2/3 - 10;
        } else {
            return gameHeight*2/3 - 10;
        }
    }

    public void setCharacterPartThreeSmallSmartphone(){
        box = new GameObject(this, 150, gameHeight*2/3 + 70, gameWidth - 300, 360, AssetLoader.characterBox, parseColor(Settings.COLOR_WHITE, 1f));
        boxText = new Text(this, 150, gameHeight*2/3 + 10, gameWidth - 300, 400,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_TEXT_THREE,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);
    }

    public void setCharacterPartFourSmallSmartphone(){
        topWLayer.fadeOut(.5f, .5f);

        mapText = new Text(this, 40, gameHeight/2 - 100, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_TWO_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        mapText.effectY(-300, mapText.getPosition().y, 1f, 0f);

        map = new GameObject(this, gameWidth/2 - 400, gameHeight*2/3 - 100, 800, 400,
                AssetLoader.characterMap, parseColor(Settings.COLOR_WHITE, 1f));
        map.fadeIn(.5f, .6f);
    }

    public void setCharacterPartFiveSmallSmartphone(){
        topWLayer.fadeOut(.5f, .5f);

        handbookText = new Text(this, 40, gameHeight*2/3 + 150, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_TEXT,
                AssetLoader.fontS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        handbook = new GameObject(this, gameWidth/2 - 225, gameHeight/2 - 300 + 50, 450, 600,
                AssetLoader.characterHandbook, parseColor(Settings.COLOR_WHITE, 1f));

        exitButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(exitButton);

        continueText.setText(Settings.CHARACTER_PART_THREE_GOTEXT);

        skipText = new Text(this, exitButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 150, exitButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_SKIPTEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    public void setOtherItemsSmallSmartphone(){
        mapText = new Text(this, 40, -300, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_TWO_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        map = new GameObject(this, gameWidth/2 - 400, gameHeight+500, 800, 400,
                AssetLoader.characterDictator, parseColor(Settings.COLOR_WHITE, 1f));

        handbookText = new Text(this, 40, -300, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_TWO_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        handbook = new GameObject(this, gameWidth/2 - 400, gameHeight+500, 800, 400,
                AssetLoader.characterDictator, parseColor(Settings.COLOR_WHITE, 1f));

        exitButton = new MenuButton(this, -300, 100,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));

        skipText = new Text(this, 40, -300, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_SKIPTEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    // --------- TABLET 4/3 ---------
    private void setCharacterPartOneTablet43(){
        nextButton = new MenuButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 180, 170,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.playButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(nextButton);

        continueText = new Text(this, nextButton.getPosition().x + Settings.SMALL_BUTTON_SIZE_TABLET43/2 - 155, nextButton.getPosition().y + (Settings.SMALL_BUTTON_SIZE_TABLET43 + 6)/2 - 70, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CONTINUE_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        drawedBack = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.backgroundMap, world.parseColor(Settings.COLOR_WHITE, 1f));

        box = new GameObject(this, 150, gameHeight*2/3 + 50, gameWidth - 300, 380, AssetLoader.characterBox, parseColor(Settings.COLOR_WHITE, 1f));
        boxText = new Text(this, 150, partOneTextPositionTablet43(), gameWidth - 300, 400,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_TEXT_ONE,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);

        character = new GameObject(this, gameWidth/2 - 300, gameHeight/3 - 100, 600, 600,
                AssetLoader.characterDictator, parseColor(Settings.COLOR_WHITE, 1f));

        setOtherItemsTablet43();

        exitButton = new MenuButton(this, 180, 170,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(exitButton);

        skipText = new Text(this, exitButton.getPosition().x + Settings.SMALL_BUTTON_SIZE_TABLET43/2 - 150, exitButton.getPosition().y + (Settings.SMALL_BUTTON_SIZE_TABLET43 + 6)/2 - 50, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_SKIPTEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    public float partOneTextPositionTablet43(){
        if(AssetLoader.getLanguage().equals(Settings.LANGUAGE_ENGLISH)){
            return gameHeight*2/3 + 10;
        } else {
            return gameHeight*2/3 + 10;
        }
    }

    public void setCharacterPartTwoTablet43(){
        box = new GameObject(this, 150, gameHeight*2/3 + 100, gameWidth - 300, 250, AssetLoader.characterBox, parseColor(Settings.COLOR_WHITE, 1f));
        boxText = new Text(this, 150, partTwoTextPositionTablet43(), gameWidth - 300, 400,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_TEXT_TWO,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);
    }

    public float partTwoTextPositionTablet43(){
        if(AssetLoader.getLanguage().equals(Settings.LANGUAGE_ENGLISH)){
            return gameHeight*2/3 - 70;
        } else {
            return gameHeight*2/3 - 50;
        }
    }

    public void setCharacterPartThreeTablet43(){
        box = new GameObject(this, 150, gameHeight*2/3 + 70, gameWidth - 300, 300, AssetLoader.characterBox, parseColor(Settings.COLOR_WHITE, 1f));
        boxText = new Text(this, 150, partThreeTextPositionTablet43(), gameWidth - 300, 400,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_TEXT_THREE,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f), 60, Align.left);
    }

    public float partThreeTextPositionTablet43(){
        if(AssetLoader.getLanguage().equals(Settings.LANGUAGE_ENGLISH)){
            return gameHeight*2/3 - 60;
        } else {
            return gameHeight*2/3 - 30;
        }
    }

    public void setCharacterPartFourTablet43(){
        topWLayer.fadeOut(.5f, .5f);

        mapText = new Text(this, 40, gameHeight/2 - 120, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_TWO_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        mapText.effectY(-300, mapText.getPosition().y, 1f, 0f);

        map = new GameObject(this, gameWidth/2 - 300, gameHeight*2/3 - 60, 600, 300,
                AssetLoader.characterMap, parseColor(Settings.COLOR_WHITE, 1f));
        map.fadeIn(.5f, .6f);
    }

    public void setCharacterPartFiveTablet43(){
        topWLayer.fadeOut(.5f, .5f);

        handbookText = new Text(this, 40, gameHeight*2/3 + 120, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);

        handbook = new GameObject(this, gameWidth/2 - 225, gameHeight/2 - 300 + 70, 450, 600,
                AssetLoader.characterHandbook, parseColor(Settings.COLOR_WHITE, 1f));

        exitButton = new MenuButton(this, 180, 170,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));
        menuButtons.add(exitButton);

        continueText.setText(Settings.CHARACTER_PART_THREE_GOTEXT);

        skipText = new Text(this, exitButton.getPosition().x + Settings.GAME_BUTTON_SIZE/2 - 160, exitButton.getPosition().y + (Settings.GAME_BUTTON_SIZE + 6)/2 - 70, 300, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_SKIPTEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.center);
    }

    public void setOtherItemsTablet43(){
        mapText = new Text(this, 40, -300, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_TWO_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        map = new GameObject(this, gameWidth/2 - 400, gameHeight+500, 800, 400,
                AssetLoader.characterDictator, parseColor(Settings.COLOR_WHITE, 1f));

        handbookText = new Text(this, 40, -300, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_TWO_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        handbook = new GameObject(this, gameWidth/2 - 400, gameHeight+500, 800, 400,
                AssetLoader.characterDictator, parseColor(Settings.COLOR_WHITE, 1f));

        exitButton = new MenuButton(this, -300, 170,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_RED_500, 1f), AssetLoader.backButtonUp, true, world.parseColor(Settings.COLOR_WHITE, 1f));

        skipText = new Text(this, 40, -300, gameWidth - 80, 250,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.CHARACTER_PART_THREE_SKIPTEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }
    // --------------------------------

    public void goToNextPart(){
        if(part > 2) topWLayer.fadeIn(.5f, 0f);
        part++;
        setPart(part);
    }

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

    public void goToTutorialScreen(String type, int part) {
        nextButton.toTutorialScreen(0.6f, 0.5f, type, part);
        handbook.scale(0, 10, .6f, 0f);
        topWLayer.setColor(parseColor(Settings.COLOR_BOOK, 1f));
        topWLayer.fadeIn(0.4f, .1f);
    }

    public void goToStoryScreen(int storyPart) {
        menuButtons.get(0).toStoryScreen(0.6f, 0.1f, storyPart);
        topWLayer.fadeIn(0.6f, .1f);
    }
}
