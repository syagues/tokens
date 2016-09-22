package com.forkstone.tokens.shopworld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.Background;
import com.forkstone.tokens.gameobjects.Counter;
import com.forkstone.tokens.gameworld.GameCam;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.ui.MenuButton;
import com.forkstone.tokens.ui.PriceButton;
import com.forkstone.tokens.ui.Text;

import java.util.ArrayList;

/**
 * Created by sergi on 10/1/16.
 */
public class ShopWorld extends GameWorld {

    public static final String TAG = "ShopWorld";

    // SETTINGS
    private Settings settings;

    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public ButtonsGame game;
    public ShopWorld world;

    //GAME CAMERA
    private GameCam camera;

    // OBJECTS
    private Background background;
    private static Background topWLayer;
    private static MenuButton backButton;
    private MenuButton moreStepsButton, moreTimeButton, multicolorButton, lightningButton, shuffleButton, noAdsButton;
    private Counter moreStepsCounter, moreTimeCounter, multicolorCounter, lightningCounter;
    private Text moreStepsText, moreTimeText, multicolorText, lightningText, shuffleText, noAdsText,
            moreStepsText2, moreTimeText2, multicolorText2, lightningText2, shuffleText2;
    private PriceButton moreStepsPriceButton, moreTimePriceButton, multicolorPriceButton, lightningPriceButton, noAdsPriceButton, shufflePriceButton;
    private ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();
    private ArrayList<PriceButton> priceButtons = new ArrayList<PriceButton>();

    // VARIABLES
    private static int backScreen;
    private static boolean videoAdActive = true;
    private boolean isAdsPurchaseInactive = false;

    public ShopWorld(ButtonsGame game, ActionResolver actionResolver, float gameWidth, float gameHeight, float worldWidth, float worldHeight, int backScreen) {

        super(game, actionResolver, gameWidth, gameHeight, worldWidth, worldHeight, 0);
        settings = new Settings();
        this.game = game;
        this.actionResolver = actionResolver;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.backScreen = backScreen;

        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_PURPLE_900, 1f));
        topWLayer = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, world.parseColor(Settings.COLOR_PURPLE_900, 1f));
        topWLayer.fadeOut(.5f, .1f);

        if(AssetLoader.getAds()) isAdsPurchaseInactive = true;

        setUpItems();

        // Tracking
        actionResolver.setTrackerScreenName("ShopScreen");

        // Music
//        setMusic();
    }

    public void update(float delta) {
        background.update(delta);
        // MoreSteps
        moreStepsButton.update(delta);
        moreStepsText.update(delta);
        moreStepsText2.update(delta);
        moreStepsPriceButton.update(delta);
        moreStepsCounter.update(delta);
        // MoreTime
        moreTimeButton.update(delta);
        moreTimeText.update(delta);
        moreTimeText2.update(delta);
        moreTimePriceButton.update(delta);
        moreTimeCounter.update(delta);
        // Multicolor
        multicolorButton.update(delta);
        multicolorText.update(delta);
        multicolorText2.update(delta);
        multicolorPriceButton.update(delta);
        multicolorCounter.update(delta);
        // Lightning
        lightningButton.update(delta);
        lightningText.update(delta);
        lightningText2.update(delta);
        lightningPriceButton.update(delta);
        lightningCounter.update(delta);
        // Shuffle
        shuffleButton.update(delta);
        shufflePriceButton.update(delta);
        shuffleText.update(delta);
        shuffleText2.update(delta);
        // No Ads
        if(!isAdsPurchaseInactive) {
            noAdsButton.update(delta);
            noAdsText.update(delta);
            noAdsPriceButton.update(delta);
        }

        backButton.update(delta);
        topWLayer.update(delta);

        if(videoAdActive){
            shufflePriceButton.setColor(parseColor(Settings.COLOR_GREEN_500, 1f));
            shufflePriceButton.setIconColor(parseColor(Settings.COLOR_WHITE, 1f));
        } else {
            shufflePriceButton.setColor(parseColor(Settings.COLOR_GREEN_300, 1f));
            shufflePriceButton.setIconColor(parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f));
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        background.render(batch, shapeRenderer, fontShader, objectShader);
        // MoreSteps
        moreStepsButton.render(batch, shapeRenderer, fontShader, objectShader);
        moreStepsText.render(batch, shapeRenderer, fontShader, objectShader);
        moreStepsText2.render(batch, shapeRenderer, fontShader, objectShader);
        moreStepsPriceButton.render(batch, shapeRenderer, fontShader, objectShader);
        moreStepsCounter.render(batch, shapeRenderer, fontShader, objectShader);
        // MoreTime
        moreTimeButton.render(batch, shapeRenderer, fontShader, objectShader);
        moreTimeText.render(batch, shapeRenderer, fontShader, objectShader);
        moreTimeText2.render(batch, shapeRenderer, fontShader, objectShader);
        moreTimePriceButton.render(batch, shapeRenderer, fontShader, objectShader);
        moreTimeCounter.render(batch, shapeRenderer, fontShader, objectShader);
        // Multicolor
        multicolorButton.render(batch, shapeRenderer, fontShader, objectShader);
        multicolorText.render(batch, shapeRenderer, fontShader, objectShader);
        multicolorText2.render(batch, shapeRenderer, fontShader, objectShader);
        multicolorPriceButton.render(batch, shapeRenderer, fontShader, objectShader);
        multicolorCounter.render(batch, shapeRenderer, fontShader, objectShader);
        // Lightning
        lightningButton.render(batch, shapeRenderer, fontShader, objectShader);
        lightningText.render(batch, shapeRenderer, fontShader, objectShader);
        lightningText2.render(batch, shapeRenderer, fontShader, objectShader);
        lightningPriceButton.render(batch, shapeRenderer, fontShader, objectShader);
        lightningCounter.render(batch, shapeRenderer, fontShader, objectShader);
        //Shuffle
        shuffleButton.render(batch, shapeRenderer, fontShader, objectShader);
        shufflePriceButton.render(batch, shapeRenderer, fontShader, objectShader);
        shuffleText.render(batch, shapeRenderer, fontShader, objectShader);
        shuffleText2.render(batch, shapeRenderer, fontShader, objectShader);
        // No Ads
        if(!isAdsPurchaseInactive) {
            noAdsButton.render(batch, shapeRenderer, fontShader, objectShader);
            noAdsText.render(batch, shapeRenderer, fontShader, objectShader);
            noAdsPriceButton.render(batch, shapeRenderer, fontShader, objectShader);
        }

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
            setItemIconsSmartphone();
            setItemsTextsSmartphone();
            setPriceButtonsSmartphone();
            setBackButtonSmartphone();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)){
            setItemIconsTablet1610();
            setItemsTextsTablet1610();
            setPriceButtonsTablet1610();
            setBackButtonTablet1610();
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)){
            setItemIconsSmallSmartphone();
            setItemsTextsSmallSmartphone();
            setPriceButtonsSmallSmartphone();
            setBackButtonSmallSmartphone();
            isAdsPurchaseInactive = true;
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            setItemIconsTablet43();
            setItemsTextsTablet43();
            setPriceButtonsTablet43();
            setBackButtonTablet43();
        }
    }

    // ------------ SMARTPHONE --------------
    public void setBackButtonSmartphone(){
        backButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), getBackIcon(), true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        menuButtons.add(backButton);
    }

    public void setItemIconsSmartphone(){
        int topPosition = 725;
        int ySeparation = 230;
        moreStepsButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE + 5)/2 + topPosition,
                Settings.SHOP_BUTTON_SIZE, Settings.SHOP_BUTTON_SIZE + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.plusThreeButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreStepsCounter = new Counter(this, moreStepsButton.getPosition().x + Settings.SHOP_BUTTON_SIZE - 45,
                moreStepsButton.getPosition().y + Settings.SHOP_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_WHITE, 1f), "moreMovesShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        moreTimeButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE + 5)/2 + topPosition - (ySeparation),
                Settings.SHOP_BUTTON_SIZE, Settings.SHOP_BUTTON_SIZE + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.plusTenButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreTimeCounter = new Counter(this, moreTimeButton.getPosition().x + Settings.SHOP_BUTTON_SIZE - 45,
                moreTimeButton.getPosition().y + Settings.SHOP_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_WHITE, 1f), "moreTimeShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE + 5)/2 + topPosition - (ySeparation*2),
                Settings.SHOP_BUTTON_SIZE, Settings.SHOP_BUTTON_SIZE + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.multicolorButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));
        multicolorCounter = new Counter(this, multicolorButton.getPosition().x + Settings.SHOP_BUTTON_SIZE - 45,
                multicolorButton.getPosition().y + Settings.SHOP_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_WHITE, 1f), "multicolorShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        lightningButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE + 5)/2 + topPosition - (ySeparation*3),
                Settings.SHOP_BUTTON_SIZE, Settings.SHOP_BUTTON_SIZE + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.lightningButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningCounter = new Counter(this, lightningButton.getPosition().x + Settings.SHOP_BUTTON_SIZE - 45,
                lightningButton.getPosition().y + Settings.SHOP_BUTTON_SIZE - 35,
                60, 60, AssetLoader.fontXXS, parseColor(Settings.COLOR_WHITE, 1f), "resolShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        shuffleButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE + 5)/2 + topPosition - (ySeparation*4),
                Settings.SHOP_BUTTON_SIZE, Settings.SHOP_BUTTON_SIZE + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.shuffleButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        noAdsButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE + 5)/2 + topPosition - (ySeparation*5),
                Settings.SHOP_BUTTON_SIZE, Settings.SHOP_BUTTON_SIZE + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.noAdsButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void setItemsTextsSmartphone(){
        int topPosition = 720;
        int ySeparation = 230;
        int textSeparation = 75;
        moreStepsText = new Text(this, Settings.SHOP_BUTTON_SIZE + 60, gameHeight/2 + topPosition, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORESTEPS_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        moreStepsText2 = new Text(this, Settings.SHOP_BUTTON_SIZE + 60, moreStepsText.getPosition().y - textSeparation, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORESTEPS_SUBTEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        moreTimeText = new Text(this, Settings.SHOP_BUTTON_SIZE + 60, gameHeight/2 + topPosition - ySeparation, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORETIME_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        moreTimeText2 = new Text(this, Settings.SHOP_BUTTON_SIZE + 60, moreTimeText.getPosition().y - textSeparation, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORETIME_SUBTEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        multicolorText = new Text(this, Settings.SHOP_BUTTON_SIZE + 60, gameHeight/2 + topPosition - (ySeparation*2), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MULTICOLOR_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        multicolorText2 = new Text(this, Settings.SHOP_BUTTON_SIZE + 60, multicolorText.getPosition().y - textSeparation, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MULTICOLOR_SUBTEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        lightningText = new Text(this, Settings.SHOP_BUTTON_SIZE + 60, gameHeight/2 + topPosition - (ySeparation*3), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RESOL_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        lightningText2 = new Text(this, Settings.SHOP_BUTTON_SIZE + 60, lightningText.getPosition().y - textSeparation, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RESOL_SUBTEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        shuffleText = new Text(this, Settings.SHOP_BUTTON_SIZE + 60, gameHeight/2 + topPosition - (ySeparation*4), gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RANDOM_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        shuffleText2 = new Text(this, Settings.SHOP_BUTTON_SIZE + 60, shuffleText.getPosition().y - textSeparation + 10, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RANDOM_SUBTEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        noAdsText = new Text(this, Settings.SHOP_BUTTON_SIZE + 60, gameHeight/2 + topPosition - (ySeparation*5) - 60, gameWidth - (Settings.BUTTON_SIZE + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_NOADS_TEXT,
                AssetLoader.fontXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    public void setPriceButtonsSmartphone(){
        int topPosition = 730;
        int ySeparation = 230;
        moreStepsPriceButton = new PriceButton(this, gameWidth - Settings.BUTTON_SIZE - 50, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition,
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPrice, Settings.SHOP_MORESTEPS_PRICE);

        moreTimePriceButton = new PriceButton(this, gameWidth - Settings.BUTTON_SIZE - 50, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation),
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPrice, Settings.SHOP_MORETIME_PRICE);

        multicolorPriceButton = new PriceButton(this, gameWidth - Settings.BUTTON_SIZE - 50, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation*2),
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPrice, Settings.SHOP_MULTICOLOR_PRICE);

        lightningPriceButton = new PriceButton(this, gameWidth - Settings.BUTTON_SIZE - 50, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation*3),
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPrice, Settings.SHOP_RESOL_PRICE);

        shufflePriceButton = new PriceButton(this, gameWidth - Settings.BUTTON_SIZE - 50, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation*4) - 15,
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPrice, "0");

        noAdsPriceButton = new PriceButton(this, gameWidth - Settings.BUTTON_SIZE - 50, gameHeight/2 - (Settings.BUTTON_SIZE + 10)/2 + topPosition - (ySeparation*5) - 15,
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE + 10,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPrice, Settings.SHOP_NOADS_PRICE);

        priceButtons.add(moreStepsPriceButton);
        priceButtons.add(moreTimePriceButton);
        priceButtons.add(multicolorPriceButton);
        priceButtons.add(lightningPriceButton);
        priceButtons.add(shufflePriceButton);
        priceButtons.add(noAdsPriceButton);
    }

    // ------------ TABLET 16/10 --------------
    public void setBackButtonTablet1610(){
        backButton = new MenuButton(this, 180, 205,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), getBackIcon(), true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        menuButtons.add(backButton);
    }

    public void setItemIconsTablet1610(){
        int topPosition = 700;
        int ySeparation = 210;
        moreStepsButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 5)/2 + topPosition,
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.plusThreeButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreStepsCounter = new Counter(this, moreStepsButton.getPosition().x + Settings.SHOP_BUTTON_SIZE_TABLET - 45,
                moreStepsButton.getPosition().y + Settings.SHOP_BUTTON_SIZE_TABLET - 35,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_WHITE, 1f), "moreMovesShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        moreTimeButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 5)/2 + topPosition - (ySeparation),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.plusTenButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreTimeCounter = new Counter(this, moreTimeButton.getPosition().x + Settings.SHOP_BUTTON_SIZE_TABLET - 45,
                moreTimeButton.getPosition().y + Settings.SHOP_BUTTON_SIZE_TABLET - 35,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_WHITE, 1f), "moreTimeShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 5)/2 + topPosition - (ySeparation*2),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.multicolorButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));
        multicolorCounter = new Counter(this, multicolorButton.getPosition().x + Settings.SHOP_BUTTON_SIZE_TABLET - 45,
                multicolorButton.getPosition().y + Settings.SHOP_BUTTON_SIZE_TABLET - 35,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_WHITE, 1f), "multicolorShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        lightningButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 5)/2 + topPosition - (ySeparation*3),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.lightningButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningCounter = new Counter(this, lightningButton.getPosition().x + Settings.SHOP_BUTTON_SIZE_TABLET - 45,
                lightningButton.getPosition().y + Settings.SHOP_BUTTON_SIZE_TABLET - 35,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_WHITE, 1f), "resolShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        shuffleButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 5)/2 + topPosition - (ySeparation*4),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.shuffleButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        noAdsButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 5)/2 + topPosition - (ySeparation*5),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.noAdsButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void setItemsTextsTablet1610(){
        int topPosition = 690;
        int ySeparation = 210;
        int textSeparation = 65;
        moreStepsText = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, gameHeight/2 + topPosition, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORESTEPS_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        moreStepsText2 = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, moreStepsText.getPosition().y - textSeparation, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORESTEPS_SUBTEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        moreTimeText = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, gameHeight/2 + topPosition - ySeparation, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORETIME_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        moreTimeText2 = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, moreTimeText.getPosition().y - textSeparation, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORETIME_SUBTEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        multicolorText = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, gameHeight/2 + topPosition - (ySeparation*2), gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MULTICOLOR_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        multicolorText2 = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, multicolorText.getPosition().y - textSeparation, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MULTICOLOR_SUBTEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        lightningText = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, gameHeight/2 + topPosition - (ySeparation*3), gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RESOL_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        lightningText2 = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, lightningText.getPosition().y - textSeparation, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RESOL_SUBTEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        shuffleText = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, gameHeight/2 + topPosition - (ySeparation*4), gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RANDOM_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        shuffleText2 = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, shuffleText.getPosition().y - textSeparation, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RANDOM_SUBTEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        noAdsText = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, gameHeight/2 + topPosition - (ySeparation*5) - 60, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_NOADS_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    public void setPriceButtonsTablet1610(){
        int topPosition = 700;
        int ySeparation = 210;
        moreStepsPriceButton = new PriceButton(this, gameWidth - Settings.SHOP_BUTTON_SIZE_TABLET - 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 6)/2 + topPosition,
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceS, Settings.SHOP_MORESTEPS_PRICE);

        moreTimePriceButton = new PriceButton(this, gameWidth - Settings.SHOP_BUTTON_SIZE_TABLET - 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 6)/2 + topPosition - (ySeparation),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceS, Settings.SHOP_MORETIME_PRICE);

        multicolorPriceButton = new PriceButton(this, gameWidth - Settings.SHOP_BUTTON_SIZE_TABLET - 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 6)/2 + topPosition - (ySeparation*2),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceS, Settings.SHOP_MULTICOLOR_PRICE);

        lightningPriceButton = new PriceButton(this, gameWidth - Settings.SHOP_BUTTON_SIZE_TABLET - 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 6)/2 + topPosition - (ySeparation*3),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceS, Settings.SHOP_RESOL_PRICE);

        shufflePriceButton = new PriceButton(this, gameWidth - Settings.SHOP_BUTTON_SIZE_TABLET - 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 6)/2 + topPosition - (ySeparation*4),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceS, "0");

        noAdsPriceButton = new PriceButton(this, gameWidth - Settings.SHOP_BUTTON_SIZE_TABLET - 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 6)/2 + topPosition - (ySeparation*5),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceS, Settings.SHOP_NOADS_PRICE);

        priceButtons.add(moreStepsPriceButton);
        priceButtons.add(moreTimePriceButton);
        priceButtons.add(multicolorPriceButton);
        priceButtons.add(lightningPriceButton);
        priceButtons.add(shufflePriceButton);
        priceButtons.add(noAdsPriceButton);
    }

    // ------------ SMALL SMARTPHONE --------------
    public void setBackButtonSmallSmartphone(){
        backButton = new MenuButton(this, 180, 100,
                Settings.GAME_BUTTON_SIZE, Settings.GAME_BUTTON_SIZE + 6,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), getBackIcon(), true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        menuButtons.add(backButton);
    }

    public void setItemIconsSmallSmartphone(){
        int topPosition = 630;
        int ySeparation = 210;
        moreStepsButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 5)/2 + topPosition,
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.plusThreeButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreStepsCounter = new Counter(this, moreStepsButton.getPosition().x + Settings.SHOP_BUTTON_SIZE_TABLET - 45,
                moreStepsButton.getPosition().y + Settings.SHOP_BUTTON_SIZE_TABLET - 35,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_WHITE, 1f), "moreMovesShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        moreTimeButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 5)/2 + topPosition - (ySeparation),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.plusTenButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreTimeCounter = new Counter(this, moreTimeButton.getPosition().x + Settings.SHOP_BUTTON_SIZE_TABLET - 45,
                moreTimeButton.getPosition().y + Settings.SHOP_BUTTON_SIZE_TABLET - 35,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_WHITE, 1f), "moreTimeShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 5)/2 + topPosition - (ySeparation*2),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.multicolorButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));
        multicolorCounter = new Counter(this, multicolorButton.getPosition().x + Settings.SHOP_BUTTON_SIZE_TABLET - 45,
                multicolorButton.getPosition().y + Settings.SHOP_BUTTON_SIZE_TABLET - 35,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_WHITE, 1f), "multicolorShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        lightningButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 5)/2 + topPosition - (ySeparation*3),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.lightningButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningCounter = new Counter(this, lightningButton.getPosition().x + Settings.SHOP_BUTTON_SIZE_TABLET - 45,
                lightningButton.getPosition().y + Settings.SHOP_BUTTON_SIZE_TABLET - 35,
                50, 50, AssetLoader.fontXXXS, parseColor(Settings.COLOR_WHITE, 1f), "resolShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        shuffleButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 5)/2 + topPosition - (ySeparation*4),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.shuffleButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

//        noAdsButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 5)/2 + topPosition - (ySeparation*5),
//                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 5,
//                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.noAdsButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void setItemsTextsSmallSmartphone(){
        int topPosition = 620;
        int ySeparation = 210;
        int textSeparation = 65;
        moreStepsText = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, gameHeight/2 + topPosition, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORESTEPS_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        moreStepsText2 = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, moreStepsText.getPosition().y - textSeparation, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORESTEPS_SUBTEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        moreTimeText = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, gameHeight/2 + topPosition - ySeparation, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORETIME_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        moreTimeText2 = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, moreTimeText.getPosition().y - textSeparation, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORETIME_SUBTEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        multicolorText = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, gameHeight/2 + topPosition - (ySeparation*2), gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MULTICOLOR_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        multicolorText2 = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, multicolorText.getPosition().y - textSeparation, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MULTICOLOR_SUBTEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        lightningText = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, gameHeight/2 + topPosition - (ySeparation*3), gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RESOL_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        lightningText2 = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, lightningText.getPosition().y - textSeparation, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RESOL_SUBTEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        shuffleText = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, gameHeight/2 + topPosition - (ySeparation*4), gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RANDOM_TEXT,
                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        shuffleText2 = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, shuffleText.getPosition().y - textSeparation, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RANDOM_SUBTEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

//        noAdsText = new Text(this, Settings.SHOP_BUTTON_SIZE_TABLET + 60, gameHeight/2 + topPosition - (ySeparation*5) - 60, gameWidth - (Settings.SHOP_BUTTON_SIZE_TABLET + 40)*2, 150,
//                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_NOADS_TEXT,
//                AssetLoader.fontXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    public void setPriceButtonsSmallSmartphone(){
        int topPosition = 630;
        int ySeparation = 210;
        moreStepsPriceButton = new PriceButton(this, gameWidth - Settings.SHOP_BUTTON_SIZE_TABLET - 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 6)/2 + topPosition,
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceS, Settings.SHOP_MORESTEPS_PRICE);

        moreTimePriceButton = new PriceButton(this, gameWidth - Settings.SHOP_BUTTON_SIZE_TABLET - 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 6)/2 + topPosition - (ySeparation),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceS, Settings.SHOP_MORETIME_PRICE);

        multicolorPriceButton = new PriceButton(this, gameWidth - Settings.SHOP_BUTTON_SIZE_TABLET - 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 6)/2 + topPosition - (ySeparation*2),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceS, Settings.SHOP_MULTICOLOR_PRICE);

        lightningPriceButton = new PriceButton(this, gameWidth - Settings.SHOP_BUTTON_SIZE_TABLET - 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 6)/2 + topPosition - (ySeparation*3),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceS, Settings.SHOP_RESOL_PRICE);

        shufflePriceButton = new PriceButton(this, gameWidth - Settings.SHOP_BUTTON_SIZE_TABLET - 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 6)/2 + topPosition - (ySeparation*4),
                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 6,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceS, "0");

//        noAdsPriceButton = new PriceButton(this, gameWidth - Settings.SHOP_BUTTON_SIZE_TABLET - 50, gameHeight/2 - (Settings.SHOP_BUTTON_SIZE_TABLET + 6)/2 + topPosition - (ySeparation*5),
//                Settings.SHOP_BUTTON_SIZE_TABLET, Settings.SHOP_BUTTON_SIZE_TABLET + 6,
//                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceS, Settings.SHOP_NOADS_PRICE);

        priceButtons.add(moreStepsPriceButton);
        priceButtons.add(moreTimePriceButton);
        priceButtons.add(multicolorPriceButton);
        priceButtons.add(lightningPriceButton);
        priceButtons.add(shufflePriceButton);
//        priceButtons.add(noAdsPriceButton);
    }

    // ------------ TABLET 4/3 --------------
    public void setBackButtonTablet43(){
        backButton = new MenuButton(this, 180, 170,
                Settings.EXTRASMALL_BUTTON_SIZE_TABLET43, Settings.EXTRASMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton,
                world.parseColor(Settings.COLOR_BLUEGREY_100, 1f), getBackIcon(), true, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        menuButtons.add(backButton);
    }

    public void setItemIconsTablet43(){
        int topPosition = 600;
        int ySeparation = 180;
        moreStepsButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 5)/2 + topPosition,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.plusThreeButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreStepsCounter = new Counter(this, moreStepsButton.getPosition().x + Settings.SMALL_BUTTON_SIZE_TABLET43 - 40,
                moreStepsButton.getPosition().y + Settings.SMALL_BUTTON_SIZE_TABLET43 - 30,
                45, 45, AssetLoader.fontXXXXS, parseColor(Settings.COLOR_WHITE, 1f), "moreMovesShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        moreTimeButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 5)/2 + topPosition - (ySeparation),
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.plusTenButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        moreTimeCounter = new Counter(this, moreTimeButton.getPosition().x + Settings.SMALL_BUTTON_SIZE_TABLET43 - 40,
                moreTimeButton.getPosition().y + Settings.SMALL_BUTTON_SIZE_TABLET43 - 30,
                45, 45, AssetLoader.fontXXXXS, parseColor(Settings.COLOR_WHITE, 1f), "moreTimeShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        multicolorButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 5)/2 + topPosition - (ySeparation*2),
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.multicolorButtonUp, false, world.parseColor(Settings.COLOR_WHITE, 1f));
        multicolorCounter = new Counter(this, multicolorButton.getPosition().x + Settings.SMALL_BUTTON_SIZE_TABLET43 - 40,
                multicolorButton.getPosition().y + Settings.SMALL_BUTTON_SIZE_TABLET43 - 30,
                45, 45, AssetLoader.fontXXXXS, parseColor(Settings.COLOR_WHITE, 1f), "multicolorShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        lightningButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 5)/2 + topPosition - (ySeparation*3),
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.lightningButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
        lightningCounter = new Counter(this, lightningButton.getPosition().x + Settings.SMALL_BUTTON_SIZE_TABLET43 - 40,
                lightningButton.getPosition().y + Settings.SMALL_BUTTON_SIZE_TABLET43 - 30,
                45, 45, AssetLoader.fontXXXXS, parseColor(Settings.COLOR_WHITE, 1f), "resolShop", false,
                parseColor(Settings.COLOR_RED_500, 1f), 10f);

        shuffleButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 5)/2 + topPosition - (ySeparation*4),
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.shuffleButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));

        noAdsButton = new MenuButton(this, 50, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 5)/2 + topPosition - (ySeparation*5),
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_BOARD, 1f), AssetLoader.noAdsButtonUp, false, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f));
    }

    public void setItemsTextsTablet43(){
        int topPosition = 550;
        int ySeparation = 180;
        int textSeparation = 45;
        moreStepsText = new Text(this, Settings.SMALL_BUTTON_SIZE_TABLET43 + 50, gameHeight/2 + topPosition, gameWidth - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORESTEPS_TEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        moreStepsText2 = new Text(this, Settings.SMALL_BUTTON_SIZE_TABLET43 + 50, moreStepsText.getPosition().y - textSeparation, gameWidth - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORESTEPS_SUBTEXT,
                AssetLoader.fontXXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        moreTimeText = new Text(this, Settings.SMALL_BUTTON_SIZE_TABLET43 + 50, gameHeight/2 + topPosition - ySeparation, gameWidth - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORETIME_TEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        moreTimeText2 = new Text(this, Settings.SMALL_BUTTON_SIZE_TABLET43 + 50, moreTimeText.getPosition().y - textSeparation, gameWidth - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MORETIME_SUBTEXT,
                AssetLoader.fontXXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        multicolorText = new Text(this, Settings.SMALL_BUTTON_SIZE_TABLET43 + 50, gameHeight/2 + topPosition - (ySeparation*2), gameWidth - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MULTICOLOR_TEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        multicolorText2 = new Text(this, Settings.SMALL_BUTTON_SIZE_TABLET43 + 50, multicolorText.getPosition().y - textSeparation, gameWidth - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_MULTICOLOR_SUBTEXT,
                AssetLoader.fontXXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        lightningText = new Text(this, Settings.SMALL_BUTTON_SIZE_TABLET43 + 50, gameHeight/2 + topPosition - (ySeparation*3), gameWidth - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RESOL_TEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        lightningText2 = new Text(this, Settings.SMALL_BUTTON_SIZE_TABLET43 + 50, lightningText.getPosition().y - textSeparation, gameWidth - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RESOL_SUBTEXT,
                AssetLoader.fontXXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        shuffleText = new Text(this, Settings.SMALL_BUTTON_SIZE_TABLET43 + 50, gameHeight/2 + topPosition - (ySeparation*4) + 5, gameWidth - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RANDOM_TEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
        shuffleText2 = new Text(this, Settings.SMALL_BUTTON_SIZE_TABLET43 + 50, shuffleText.getPosition().y - textSeparation + 5, gameWidth - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_RANDOM_SUBTEXT,
                AssetLoader.fontXXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);

        noAdsText = new Text(this, Settings.SMALL_BUTTON_SIZE_TABLET43 + 50, gameHeight/2 + topPosition - (ySeparation*5) - 25, gameWidth - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 40)*2, 150,
                AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), Settings.SHOP_NOADS_TEXT,
                AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_BOARD, 1f), 60, Align.left);
    }

    public void setPriceButtonsTablet43(){
        int topPosition = 595;
        int ySeparation = 180;
        moreStepsPriceButton = new PriceButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 50, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 6)/2 + topPosition,
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceXS, Settings.SHOP_MORESTEPS_PRICE);

        moreTimePriceButton = new PriceButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 50, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 6)/2 + topPosition - (ySeparation),
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceXS, Settings.SHOP_MORETIME_PRICE);

        multicolorPriceButton = new PriceButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 50, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 6)/2 + topPosition - (ySeparation*2),
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceXS, Settings.SHOP_MULTICOLOR_PRICE);

        lightningPriceButton = new PriceButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 50, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 6)/2 + topPosition - (ySeparation*3),
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceXS, Settings.SHOP_RESOL_PRICE);

        shufflePriceButton = new PriceButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 50, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 6)/2 + topPosition - (ySeparation*4),
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceXS, "0");

        noAdsPriceButton = new PriceButton(this, gameWidth - Settings.SMALL_BUTTON_SIZE_TABLET43 - 50, gameHeight/2 - (Settings.SMALL_BUTTON_SIZE_TABLET43 + 6)/2 + topPosition - (ySeparation*5),
                Settings.SMALL_BUTTON_SIZE_TABLET43, Settings.SMALL_BUTTON_SIZE_TABLET43 + 5,
                AssetLoader.colorButton, parseColor(Settings.COLOR_GREEN_500, 1f), AssetLoader.fontPriceXS, Settings.SHOP_NOADS_PRICE);

        priceButtons.add(moreStepsPriceButton);
        priceButtons.add(moreTimePriceButton);
        priceButtons.add(multicolorPriceButton);
        priceButtons.add(lightningPriceButton);
        priceButtons.add(shufflePriceButton);
        priceButtons.add(noAdsPriceButton);
    }
    // ---------------------------------------------

    public TextureRegion getBackIcon(){
        if(backScreen == 1){
            return AssetLoader.homeButtonUp;
        } else {
            return AssetLoader.backButtonUp;
        }
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

    public int getBackScreen(){
        return backScreen;
    }

    public ArrayList<MenuButton> getMenuButtons(){
        return menuButtons;
    }

    public ArrayList<PriceButton> getPriceButtons() {
        return priceButtons;
    }

    public void goToHomeScreen() {
        backButton.toHomeScreen(0.6f, 0.1f);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public void goToLevelScreen() {
        backButton.toLevelScreen(0.6f, 0.1f, 0);
        topWLayer.fadeIn(0.6f, .1f);
    }

    public static void goToRandomScreen() {
        backButton.toRandomScreen(0.6f, 0.1f, backScreen);
        topWLayer.fadeIn(0.6f, .1f);
    }
}
