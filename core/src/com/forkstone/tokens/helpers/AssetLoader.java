package com.forkstone.tokens.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.screens.MenuScreen;

/**
 * Created by sergi on 1/12/15.
 */
public class AssetLoader {

    public static TextureRegion logo, square, dot, dot1, colorButton, board33, board34, board44, board45, board55, colorButton_pressed, highscoreBanner, bigBanner,
            transparent, titleBanner, homeButton, replayButton, check, lightningButton, lightningButtonUp, gOReplayButtonUp, gONextButtonUp, lockButton,
            banner, lifeBanner, staticTR, backgroundField, backgroundDesert, backgroundJungle, backgroundMountain, backgroundMap, backgroundMine, leftButton, rightButton,
            homeButtonUp, backgroundFishers, backgroundIceland, backgroundMusicians, backgroundVikings, backgroundOriental, medalMine, medalMineGrey, medalFishers, medalFishersGrey,
            medalIceland, medalIcelandGrey, medalMusicians, medalMusiciansGrey, medalVikings, medalVikingsGrey, medalOriental, medalOrientalGrey,
            districtBanner, chronometer, medalJungle, medalField, medalDesert, medalMountain, medalJungleGrey, medalFieldGrey, medalDesertGrey, medalMountainGrey,
            characterDictator, characterBox, characterMap, characterHandbook, presents, loadingBar, exampleBoard1, exampleBoard2, exampleBoard3, board66, finger, arrowButton,
            loopButton, loop2Button;

    //BUTTONS
    public static TextureRegion playButtonUp, rankButtonUp, achievementButtonUp, shareButtonUp, rateButtonUp, pauseButton, shopButtonUp, backButtonUp, replayButtonUp, multicolorButtonUp,
            plusThreeButtonUp, plusTenButtonUp, emperarorButtonUp, settingsButtonUp, noAdsButtonUp, soundOnButtonUp, soundOffButtonUp, listButtonUp, lifeButtonUp, stopButtonUp,
            videoButtonUp, shuffleButtonUp;

    public static BitmapFont font, fontS, fontXXS, fontXS, fontXXXS, fontXXXXS, fontM, fontB, fontL, fontXL, fontLevelButton, fontPrice, fontPriceS, fontPriceXS, steelfishFontM, steelfishFontL, steelfishFontS;
    private static Preferences prefs;

    public static Music musicJungle, musicField, musicDesert, musicMountain, musicMenu, soundClick, soundClock, soundWin, soundLose, soundDrag, soundCelebration, musicSoundtrack;

    private static TextureAtlas atlasSplash, atlasGame, atlasGame2, atlasGame3, atlasMenu, atlasBackgrounds, atlasBackgrounds2, atlasMedals, atlasCharacters, atlasButtons;

    private static Texture tSansFont, tSansFont2, tSteelfishFont;

    public static void load1(ButtonsGame game) {

        game.manager.load("data/splash.atlas", TextureAtlas.class);
        game.manager.finishLoading();

        atlasSplash = game.manager.get("data/splash.atlas", TextureAtlas.class);

        logo = atlasSplash.findRegion("logo");
        square = atlasSplash.findRegion("square");
        presents = atlasSplash.findRegion("presents");
        loadingBar = atlasSplash.findRegion("loading_bar");

        //PREFERENCES - SAVE DATA IN FILE
        prefs = Gdx.app.getPreferences(Settings.GAME_NAME);
    }

    public static void loadAssets(ButtonsGame game){
        // Images
        game.manager.load("data/game.atlas", TextureAtlas.class);
        game.manager.load("data/game2.atlas", TextureAtlas.class);
        game.manager.load("data/game3.atlas", TextureAtlas.class);
        game.manager.load("data/menu.atlas", TextureAtlas.class);
        game.manager.load("data/medals.atlas", TextureAtlas.class);
        game.manager.load("data/characters.atlas", TextureAtlas.class);
        game.manager.load("data/buttons.atlas", TextureAtlas.class);

        // Fonts
        game.manager.load("misc/sans_ext2.png", Texture.class);
        game.manager.load("misc/sans_price.png", Texture.class);
        game.manager.load("misc/steelfish.png", Texture.class);
        BitmapFontParameter bitmapFontParameter = new BitmapFontParameter();
        bitmapFontParameter.flip = true;
        bitmapFontParameter.magFilter = TextureFilter.Linear;
        bitmapFontParameter.genMipMaps = true;
        game.manager.load("misc/sans_ext2.fnt", BitmapFont.class, bitmapFontParameter);
        game.manager.load("misc/sans_price.fnt", BitmapFont.class, bitmapFontParameter);
        game.manager.load("misc/steelfish.fnt", BitmapFont.class, bitmapFontParameter);

        // Sounds
        game.manager.load("sounds/click.mp3", Music.class);
        game.manager.load("sounds/clock.mp3", Music.class);
        game.manager.load("sounds/win.mp3", Music.class);
        game.manager.load("sounds/lose.mp3", Music.class);
        game.manager.load("sounds/drag.mp3", Music.class);
        game.manager.load("sounds/celebration.mp3", Music.class);

        // Music
//        game.manager.load("music/jungle1.mp3", Music.class);
//        game.manager.load("music/field1.mp3", Music.class);
//        game.manager.load("music/desert1.mp3", Music.class);
//        game.manager.load("music/mountain1.mp3", Music.class);
//        game.manager.load("music/menu2Long.mp3", Music.class);
        game.manager.load("music/soundtrack.mp3", Music.class);
    }

    public static void loadNoTabletAssets(ButtonsGame game){
        game.manager.load("data/backgrounds.atlas", TextureAtlas.class);
        game.manager.load("data/backgrounds2.atlas", TextureAtlas.class);
    }

    public static void loadTabletAssets(ButtonsGame game){
        game.manager.load("backgroundsTablet/backgroundMapTablet.png", Texture.class);
        game.manager.load("backgroundsTablet/backgroundJungleTablet.png", Texture.class);
        game.manager.load("backgroundsTablet/backgroundFieldTablet.png", Texture.class);
        game.manager.load("backgroundsTablet/backgroundDesertTablet.png", Texture.class);
        game.manager.load("backgroundsTablet/backgroundMountainTablet.png", Texture.class);
        game.manager.load("backgroundsTablet/backgroundMineTablet.png", Texture.class);
        game.manager.load("backgroundsTablet/backgroundFishersTablet.png", Texture.class);
        game.manager.load("backgroundsTablet/backgroundIcelandTablet.png", Texture.class);
        game.manager.load("backgroundsTablet/backgroundMusiciansTablet.png", Texture.class);
        game.manager.load("backgroundsTablet/backgroundVikingsTablet.png", Texture.class);
        game.manager.load("backgroundsTablet/backgroundOrientalTablet.png", Texture.class);
    }

    public static void setAssets(ButtonsGame game, ActionResolver actionResolver) {
        atlasGame = game.manager.get("data/game.atlas", TextureAtlas.class);

        board33 = atlasGame.findRegion("board4");
        board44 = atlasGame.findRegion("board5");
        board55 = atlasGame.findRegion("board6");
        board34 = atlasGame.findRegion("board7");

        atlasGame2 = game.manager.get("data/game2.atlas", TextureAtlas.class);

        dot = atlasGame2.findRegion("dot");
        dot1 = atlasGame2.findRegion("dot1");
        transparent = atlasGame2.findRegion("transparent");
        board45 = atlasGame2.findRegion("board8");
        colorButton = atlasGame2.findRegion("colorButton");
        colorButton_pressed = atlasGame2.findRegion("colorButton_pressed");
        chronometer = atlasGame2.findRegion("chronometer");
        titleBanner = atlasGame2.findRegion("title");
        banner = atlasGame2.findRegion("banner");
        lifeBanner = atlasGame2.findRegion("lifeBanner");
        districtBanner = atlasGame2.findRegion("levelBanner");
        check = atlasGame2.findRegion("okButton");
        staticTR = atlasGame2.findRegion("staticButtonMark");
        finger = atlasGame2.findRegion("finger");

        atlasGame3 = game.manager.get("data/game3.atlas", TextureAtlas.class);

        board66 = atlasGame3.findRegion("board66");
        exampleBoard1 = atlasGame3.findRegion("exampleBoard1");
        exampleBoard2 = atlasGame3.findRegion("exampleBoard2");
        exampleBoard3 = atlasGame3.findRegion("exampleBoard3");

        atlasMenu = game.manager.get("data/menu.atlas", TextureAtlas.class);

        bigBanner = atlasMenu.findRegion("bigBanner");
        homeButton = atlasMenu.findRegion("homeButton");
        replayButton = atlasMenu.findRegion("replayButton");
        lightningButton = atlasMenu.findRegion("flashButton");
        lockButton = atlasMenu.findRegion("lockButtonBlueGrey");
        leftButton = atlasMenu.findRegion("leftArrowButton");
        rightButton = atlasMenu.findRegion("rightArrowButton");
        arrowButton = atlasMenu.findRegion("next2Button");
        loopButton = atlasMenu.findRegion("loopButton");
        loop2Button = atlasMenu.findRegion("loop2Button");

        if(getDevice().equals(Settings.DEVICE_TABLET_43)){
            backgroundField = new TextureRegion(game.manager.get("backgroundsTablet/backgroundFieldTablet.png", Texture.class));
            backgroundDesert = new TextureRegion(game.manager.get("backgroundsTablet/backgroundDesertTablet.png", Texture.class));
            backgroundJungle = new TextureRegion(game.manager.get("backgroundsTablet/backgroundJungleTablet.png", Texture.class));
            backgroundMountain = new TextureRegion(game.manager.get("backgroundsTablet/backgroundMountainTablet.png", Texture.class));
            backgroundMap = new TextureRegion(game.manager.get("backgroundsTablet/backgroundMapTablet.png", Texture.class));
            backgroundMine = new TextureRegion(game.manager.get("backgroundsTablet/backgroundMineTablet.png", Texture.class));
            backgroundFishers = new TextureRegion(game.manager.get("backgroundsTablet/backgroundFishersTablet.png", Texture.class));
            backgroundIceland = new TextureRegion(game.manager.get("backgroundsTablet/backgroundIcelandTablet.png", Texture.class));
            backgroundMusicians = new TextureRegion(game.manager.get("backgroundsTablet/backgroundMusiciansTablet.png", Texture.class));
            backgroundVikings = new TextureRegion(game.manager.get("backgroundsTablet/backgroundVikingsTablet.png", Texture.class));
            backgroundOriental = new TextureRegion(game.manager.get("backgroundsTablet/backgroundOrientalTablet.png", Texture.class));
        } else {
            atlasBackgrounds = game.manager.get("data/backgrounds.atlas", TextureAtlas.class);

            backgroundField = atlasBackgrounds.findRegion("backgroundField");
            backgroundDesert = atlasBackgrounds.findRegion("backgroundDesert");
            backgroundJungle = atlasBackgrounds.findRegion("backgroundJungle");
            backgroundMountain = atlasBackgrounds.findRegion("backgroundMountain");
            backgroundMap = atlasBackgrounds.findRegion("backgroundMap");
            backgroundMine = atlasBackgrounds.findRegion("backgroundMine");

            atlasBackgrounds2 = game.manager.get("data/backgrounds2.atlas", TextureAtlas.class);

            backgroundFishers = atlasBackgrounds2.findRegion("backgroundFishers");
            backgroundIceland = atlasBackgrounds2.findRegion("backgroundIceland");
            backgroundMusicians = atlasBackgrounds2.findRegion("backgroundMusicians");
            backgroundVikings = atlasBackgrounds2.findRegion("backgroundVikings");
            backgroundOriental = atlasBackgrounds2.findRegion("backgroundOriental");
        }

        atlasMedals = game.manager.get("data/medals.atlas", TextureAtlas.class);

        medalJungle = atlasMedals.findRegion("medalJungle");
        medalField = atlasMedals.findRegion("medalField");
        medalDesert = atlasMedals.findRegion("medalDesert");
        medalMountain = atlasMedals.findRegion("medalMountain");
        medalMine = atlasMedals.findRegion("medalMine");
        medalFishers = atlasMedals.findRegion("medalFishers");
        medalIceland = atlasMedals.findRegion("medalIceland");
        medalMusicians = atlasMedals.findRegion("medalMusicians");
        medalVikings = atlasMedals.findRegion("medalVikings");
        medalOriental = atlasMedals.findRegion("medalOriental");
        medalJungleGrey = atlasMedals.findRegion("medalJungleGrey");
        medalFieldGrey = atlasMedals.findRegion("medalFieldGrey");
        medalDesertGrey = atlasMedals.findRegion("medalDesertGrey");
        medalMountainGrey = atlasMedals.findRegion("medalMountainGrey");
        medalMineGrey = atlasMedals.findRegion("medalMineGrey");
        medalFishersGrey = atlasMedals.findRegion("medalFishersGrey");
        medalIcelandGrey = atlasMedals.findRegion("medalIcelandGrey");
        medalMusiciansGrey = atlasMedals.findRegion("medalMusiciansGrey");
        medalVikingsGrey = atlasMedals.findRegion("medalVikingsGrey");
        medalOrientalGrey = atlasMedals.findRegion("medalOrientalGrey");

        atlasCharacters = game.manager.get("data/characters.atlas", TextureAtlas.class);

        characterBox = atlasCharacters.findRegion("box");
        characterDictator = atlasCharacters.findRegion("character1");
        characterMap = atlasCharacters.findRegion("map");
        characterHandbook = atlasCharacters.findRegion("handbook");

        atlasButtons = game.manager.get("data/buttons.atlas", TextureAtlas.class);

        playButtonUp = new TextureRegion(atlasButtons.findRegion("buttons2"), 0, 0, 240, 240);
        shopButtonUp = new TextureRegion(atlasButtons.findRegion("buttons2"), 1200, 0, 240, 240);
        rateButtonUp = new TextureRegion(atlasButtons.findRegion("buttons2"), 240, 0, 240, 240);
        achievementButtonUp = new TextureRegion(atlasButtons.findRegion("buttons2"), 720, 0, 240, 240);
        shareButtonUp = new TextureRegion(atlasButtons.findRegion("buttons2"), 960, 0, 240, 240);
        homeButtonUp = new TextureRegion(atlasButtons.findRegion("buttons2"), 1680, 0, 240, 240);
        soundOnButtonUp = new TextureRegion(atlasButtons.findRegion("buttons2"), 1440, 0, 240, 240);
        soundOffButtonUp = new TextureRegion(atlasButtons.findRegion("buttons2"), 480, 0, 240, 240);

        backButtonUp = new TextureRegion(atlasButtons.findRegion("buttons3"), 0, 0, 240, 240);
        replayButtonUp = new TextureRegion(atlasButtons.findRegion("buttons3"), 240, 0, 240, 240);
        lightningButtonUp = new TextureRegion(atlasButtons.findRegion("buttons3"), 480, 0, 240, 240);
        gOReplayButtonUp = new TextureRegion(atlasButtons.findRegion("buttons3"), 960, 0, 240, 240);
        gONextButtonUp = new TextureRegion(atlasButtons.findRegion("buttons3"), 1200, 0, 240, 240);
        multicolorButtonUp = new TextureRegion(atlasButtons.findRegion("buttons3"), 1440, 0, 240, 240);
        plusThreeButtonUp = new TextureRegion(atlasButtons.findRegion("buttons3"), 720, 0, 240, 240);
        plusTenButtonUp = new TextureRegion(atlasButtons.findRegion("buttons3"), 1680, 0, 240, 240);

        emperarorButtonUp = new TextureRegion(atlasButtons.findRegion("buttons4"), 0, 0, 240, 240);
        settingsButtonUp = new TextureRegion(atlasButtons.findRegion("buttons4"), 240, 0, 240, 240);
        noAdsButtonUp = new TextureRegion(atlasButtons.findRegion("buttons4"), 480, 0, 240, 240);
        listButtonUp = new TextureRegion(atlasButtons.findRegion("buttons4"), 720, 0, 240, 240);
        lifeButtonUp = new TextureRegion(atlasButtons.findRegion("buttons4"), 960, 0, 240, 240);
        stopButtonUp = new TextureRegion(atlasButtons.findRegion("buttons4"), 1200, 0, 240, 240);
        videoButtonUp = new TextureRegion(atlasButtons.findRegion("buttons4"), 1440, 0, 240, 240);
        shuffleButtonUp = new TextureRegion(atlasButtons.findRegion("buttons4"), 1680, 0, 240, 240);

        // FONTS
        tSansFont = game.manager.get("misc/sans_ext2.png", Texture.class);

        font = new BitmapFont(game.manager.get("misc/sans_ext2.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont), true);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        font.getData().setScale(2.5f, -2.5f);
        font.setColor(FlatColors.WHITE);

        fontXL = new BitmapFont(game.manager.get("misc/sans_ext2.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont), true);
        fontXL.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontXL.getData().setScale(1.7f, -1.7f);
        fontXL.setColor(FlatColors.WHITE);

        fontL = new BitmapFont(game.manager.get("misc/sans_ext2.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont), true);
        fontL.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontL.getData().setScale(1.6f, -1.6f);
        fontL.setColor(FlatColors.WHITE);

        fontB = new BitmapFont(game.manager.get("misc/sans_ext2.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont), true);
        fontB.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontB.getData().setScale(1.14f, -1.14f);
        fontB.setColor(FlatColors.WHITE);

        fontM = new BitmapFont(game.manager.get("misc/sans_ext2.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont), true);
        fontM.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontM.getData().setScale(1.05f, -1.05f);
        fontM.setColor(FlatColors.WHITE);

        fontS = new BitmapFont(game.manager.get("misc/sans_ext2.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont), true);
        fontS.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontS.getData().setScale(.9f, -.9f);
        fontS.setColor(FlatColors.WHITE);

        fontXS = new BitmapFont(game.manager.get("misc/sans_ext2.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont), true);
        fontXS.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontXS.getData().setScale(0.7f, -0.7f);
        fontXS.setColor(FlatColors.WHITE);

        fontXXS = new BitmapFont(game.manager.get("misc/sans_ext2.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont), true);
        fontXXS.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontXXS.getData().setScale(0.62f, -0.62f);
        fontXXS.setColor(FlatColors.WHITE);

        fontXXXS = new BitmapFont(game.manager.get("misc/sans_ext2.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont), true);
        fontXXXS.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontXXXS.getData().setScale(0.5f, -0.5f);
        fontXXXS.setColor(FlatColors.WHITE);

        fontXXXXS = new BitmapFont(game.manager.get("misc/sans_ext2.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont), true);
        fontXXXXS.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontXXXXS.getData().setScale(0.4f, -0.4f);
        fontXXXXS.setColor(FlatColors.WHITE);

        fontLevelButton = new BitmapFont(game.manager.get("misc/sans_ext2.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont), true);
        fontLevelButton.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontLevelButton.getData().setScale(.8f, -.8f);
        fontLevelButton.setColor(FlatColors.WHITE);

        tSansFont2 = game.manager.get("misc/sans_price.png", Texture.class);

        fontPrice = new BitmapFont(game.manager.get("misc/sans_price.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont2), true);
        fontPrice.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontPrice.getData().setScale(0.7f, -0.7f);
        fontPrice.setColor(FlatColors.WHITE);

        fontPriceS = new BitmapFont(game.manager.get("misc/sans_price.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont2), true);
        fontPriceS.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontPriceS.getData().setScale(0.55f, -0.55f);
        fontPriceS.setColor(FlatColors.WHITE);

        fontPriceXS = new BitmapFont(game.manager.get("misc/sans_price.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSansFont2), true);
        fontPriceXS.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fontPriceXS.getData().setScale(0.42f, -0.42f);
        fontPriceXS.setColor(FlatColors.WHITE);

        tSteelfishFont = game.manager.get("misc/steelfish.png", Texture.class);

        steelfishFontM = new BitmapFont(game.manager.get("misc/steelfish.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSteelfishFont), true);
        steelfishFontM.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        steelfishFontM.getData().setScale(1.13f, -1.13f);
        steelfishFontM.setColor(FlatColors.WHITE);

        steelfishFontL = new BitmapFont(game.manager.get("misc/steelfish.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSteelfishFont), true);
        steelfishFontL.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        steelfishFontL.getData().setScale(1.5f, -1.5f);
        steelfishFontL.setColor(FlatColors.WHITE);

        steelfishFontS = new BitmapFont(game.manager.get("misc/steelfish.fnt", BitmapFont.class).getData().getFontFile(), new TextureRegion(tSteelfishFont), true);
        steelfishFontS.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        steelfishFontS.getData().setScale(0.75f, -0.75f);
        steelfishFontS.setColor(FlatColors.WHITE);

        // Sounds
        soundClick = game.manager.get("sounds/click.mp3", Music.class);
        soundClick.setVolume(0.15f);
        soundClock = game.manager.get("sounds/clock.mp3", Music.class);
        soundClock.setVolume(0.25f);
        soundWin = game.manager.get("sounds/win.mp3", Music.class);
        soundWin.setVolume(0.25f);
        soundLose = game.manager.get("sounds/lose.mp3", Music.class);
        soundLose.setVolume(0.35f);
        soundDrag = game.manager.get("sounds/drag.mp3", Music.class);
        soundDrag.setVolume(0.6f);
        soundCelebration = game.manager.get("sounds/celebration.mp3", Music.class);
        soundCelebration.setVolume(0.6f);

        // Music
//        musicJungle = game.manager.get("music/jungle1.mp3", Music.class);
//        musicField = game.manager.get("music/field1.mp3", Music.class);
//        musicDesert = game.manager.get("music/desert1.mp3", Music.class);
//        musicMountain = game.manager.get("music/mountain1.mp3", Music.class);
//        musicMenu = game.manager.get("music/menu2Long.mp3", Music.class);
        musicSoundtrack = game.manager.get("music/soundtrack.mp3", Music.class);

        game.setScreen(new MenuScreen(game, actionResolver, true));
    }

    public static void dispose() {
        atlasSplash.dispose();
        atlasGame.dispose();
        atlasGame2.dispose();
        atlasGame3.dispose();
        atlasMenu.dispose();
        atlasBackgrounds.dispose();
        atlasBackgrounds2.dispose();
        atlasMedals.dispose();
        atlasCharacters.dispose();

        tSansFont.dispose();
        tSansFont2.dispose();
        tSteelfishFont.dispose();
        font.dispose();
        fontXL.dispose();
        fontL.dispose();
        fontB.dispose();
        fontM.dispose();
        fontS.dispose();
        fontXS.dispose();
        fontXXS.dispose();
        fontXXXS.dispose();
        fontLevelButton.dispose();
        fontPrice.dispose();
        fontPriceS.dispose();
        steelfishFontM.dispose();
        steelfishFontL.dispose();
        steelfishFontS.dispose();

        soundClick.dispose();
        soundClock.dispose();
        soundWin.dispose();
        soundLose.dispose();
        soundDrag.dispose();
        soundCelebration.dispose();

//        musicJungle.dispose();
//        musicField.dispose();
//        musicDesert.dispose();
//        musicMountain.dispose();
//        musicMenu.dispose();
        musicSoundtrack.dispose();
    }

    public static void setDevice(String deviceType){
        prefs.putString("device", deviceType);
        prefs.flush();
    }

    public static String getDevice(){
        return prefs.getString("device", Settings.DEVICE_SMARTPHONE);
    }

    public static void setCharacterPart(int part){
        prefs.putInteger("character", part);
        prefs.flush();
    }

    public static int getCharacterPart(){
        return prefs.getInteger("character", 0);
    }

    public static void setTutorialPart(int part){
        prefs.putInteger("tutorial", part);
        prefs.flush();
    }

    public static int getTutorialPart(){
        return prefs.getInteger("tutorial", 0);
    }

    public static void setTutorialMemory(boolean state){
        prefs.putBoolean("tutorial_memory", state);
        prefs.flush();
    }

    public static boolean getTutorialMemory(){
        return prefs.getBoolean("tutorial_memory", false);
    }

    public static void setTutorialPositionChange(boolean state){
        prefs.putBoolean("tutorial_positionchange", state);
        prefs.flush();
    }

    public static boolean getTutorialPositionChange(){
        return prefs.getBoolean("tutorial_positionchange", false);
    }

    public static void setTutorialColorChange(boolean state){
        prefs.putBoolean("tutorial_colorchange", state);
        prefs.flush();
    }

    public static boolean getTutorialColorChange(){
        return prefs.getBoolean("tutorial_colorchange", false);
    }

    public static void setTutorialBlockedChange(boolean state){
        prefs.putBoolean("tutorial_blockedchange", state);
        prefs.flush();
    }

    public static boolean getTutorialBlockedChange(){
        return prefs.getBoolean("tutorial_blockedchange", false);
    }

    public static void setStoryPart(int part){
        prefs.putInteger("story", part);
        prefs.flush();
    }

    public static int getStoryPart(){
        return prefs.getInteger("story", 0);
    }

    public static void setMedalPart(int part){
        prefs.putInteger("medal", part);
        prefs.flush();
    }

    public static int getMedalPart(){
        return prefs.getInteger("medal", 0);
    }

    public static void setLevel(int level) {
        prefs.putInteger("level", level);
        prefs.flush();
    }

    public static void addLevel() {
        prefs.putInteger("level", prefs.getInteger("level", 1) + 1);
        prefs.flush();
    }

    public static int getLevel() {
        return prefs.getInteger("level", 1);
    }

    public static void setAttempt(int attempt){
        prefs.putInteger("attempt", attempt);
        prefs.flush();
    }

    public static void addAttempt(){
        prefs.putInteger("attempt", prefs.getInteger("attempt", 1) + 1);
        prefs.flush();
    }

    public static int getAttempt() {
        return prefs.getInteger("attempt", 1);
    }

    public static void setMoreMoves(int cuant) {
        prefs.putInteger("moreMoves", cuant);
        prefs.flush();
    }

    public static void addMoreMoves(int cuant) {
        prefs.putInteger("moreMoves", prefs.getInteger("moreMoves", 0) + cuant);
        prefs.flush();
    }

    public static void restMoreMoves(int cuant) {
        prefs.putInteger("moreMoves", prefs.getInteger("moreMoves", 0) - cuant);
        prefs.flush();
    }

    public static int getMoreMoves() {
        return prefs.getInteger("moreMoves", 0);
    }

    public static void setMoreTime(int cuant) {
        prefs.putInteger("moreTime", cuant);
        prefs.flush();
    }

    public static void addMoreTime(int cuant) {
        prefs.putInteger("moreTime", prefs.getInteger("moreTime", 0) + cuant);
        prefs.flush();
    }

    public static void restMoreTime(int cuant) {
        prefs.putInteger("moreTime", prefs.getInteger("moreTime", 0) - cuant);
        prefs.flush();
    }

    public static int getMoreTime() {
        return prefs.getInteger("moreTime", 0);
    }

    public static void setResol(int cuant) {
        prefs.putInteger("resol", cuant);
        prefs.flush();
    }

    public static void addResol(int cuant) {
        prefs.putInteger("resol", prefs.getInteger("resol", 0) + cuant);
        prefs.flush();
    }

    public static void restResol(int cuant) {
        prefs.putInteger("resol", prefs.getInteger("resol", 0) - cuant);
        prefs.flush();
    }

    public static int getResol() {
        return prefs.getInteger("resol", 0);
    }

    public static void setMulticolor(int cuant) {
        prefs.putInteger("multicolor", cuant);
        prefs.flush();
    }

    public static void addMulticolor(int cuant) {
        prefs.putInteger("multicolor", prefs.getInteger("multicolor", 0) + cuant);
        prefs.flush();
    }

    public static void restMulticolor(int cuant) {
        prefs.putInteger("multicolor", prefs.getInteger("multicolor", 0) - cuant);
        prefs.flush();
    }

    public static int getMulticolor() {
        return prefs.getInteger("multicolor", 0);
    }

    public static void setIsRated(boolean isRated){
        prefs.putBoolean("isRated", isRated);
        prefs.flush();
    }

    public static boolean getIsRated() {
        return prefs.getBoolean("isRated", false);
    }

    public static void setAds(boolean removeAdsVersion) {
        prefs = Gdx.app.getPreferences(Settings.GAME_NAME);
        prefs.putBoolean("ads", removeAdsVersion);
        prefs.flush();
    }

    public static boolean getAds() {
        Gdx.app.log("ads", prefs.getBoolean("ads") + "");
        return prefs.getBoolean("ads", false);
    }

    public static void setMusicState(boolean state){
        prefs.putBoolean("music", state);
        prefs.flush();
    }

    public static boolean getMusicState(){
        return prefs.getBoolean("music", true);
    }

    public static void setSoundState(boolean state){
        prefs.putBoolean("sound", state);
        prefs.flush();
    }

    public static boolean getSoundState(){
        return prefs.getBoolean("sound", true);
    }

    public static void setLanguage(String language){
        prefs.putString("language", language);
        prefs.flush();
    }

    public static String getLanguage(){
        return prefs.getString("language", Settings.LANGUAGE_ENGLISH);
    }
}
