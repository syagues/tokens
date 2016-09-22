package com.forkstone.tokens.configuration;

import com.forkstone.tokens.helpers.AssetLoader;

/**
 * Created by sergi on 1/12/15.
 */
public class Settings {

    public static final String GAME_NAME = "TOKENS";
    public static final boolean DEBUG = false;
    public static final boolean SPLASHSCREEN = true;

    // PLATFORM
    public static final String PLATFORM_IOS = "IOS";
    public static final String PLATFORM_ANDROID = "ANDROID";
    public static final String PLATFORM_HTML = "HTML";
    public static final String PLATFORM_DESKTOP= "DESKTOP";

    // DEVICE
    public static final String DEVICE_SMARTPHONE = "SMARTPHONE";
    public static final String DEVICE_TABLET_1610 = "TABLET1610";
    public static final String DEVICE_SMALLSMARTPHONE = "SMALLSMARTPHONE";
    public static final String DEVICE_TABLET_43 = "TABLET43";

    // MUSIC
    public static final float MUSIC_VOLUME = 0.25f;

    // GAME VARIABLES
    public static final int TOTAL_NUMBER_OF_LEVELS = 250;
    public static final int DISTRICTS_NUMBER = 10;

    public static final int FIRST_BLOCK_TOKEN_LEVEL = 6;
    public static final int FIRST_TIME_LEVEL = 10;
    public static final int[] MEMORY_LEVELS = new int[]{16,17,18,22,23,24,30,33,34,40,41,47,48,51,63,64,67,68,69,85,86,104,224,142,153,160,166,172,182,187,194,201,245,209,219,220,223,229,231,241};
    public static final int[] CHANGEPOSITION_LEVELS = new int[]{79,80,81,82,88,89,90,95,96,101,108,110,111,112,119,123,124,127,129,130,135,141,145,150,154,161,173,177,183,188,195,202,211,221,228,234,236,250,239,247};
    public static final int[] CHANGECOLOR_LEVELS = new int[]{26,28,29,35,27,36,37,42,44,46,49,62,50,71,72,91,93,99,100,105,115,125,138,146,155,157,162,165,241,174,184,189,196,197,203,213,243,128,227,233};
    public static final int[] CHANGEBLOCKED_LEVELS = new int[]{57,58,59,60,61,74,75,77,78,92,97,98,116,117,118,126,131,137,147,156,163,164,175,190,198,204,242,216,226,232};

    public static final int DISTRICTONE_FIRSTLEVEL = 1;
    public static final int DISTRICTONE_LASTLEVEL = 25;
    public static final int DISTRICTTWO_FIRSTLEVEL = 26;
    public static final int DISTRICTTWO_LASTLEVEL = 50;
    public static final int DISTRICTTHREE_FIRSTLEVEL = 51;
    public static final int DISTRICTTHREE_LASTLEVEL = 75;
    public static final int DISTRICTFOUR_FIRSTLEVEL = 76;
    public static final int DISTRICTFOUR_LASTLEVEL = 100;
    public static final int DISTRICTFIVE_FIRSTLEVEL = 101;
    public static final int DISTRICTFIVE_LASTLEVEL = 125;
    public static final int DISTRICTSIX_FIRSTLEVEL = 126;
    public static final int DISTRICTSIX_LASTLEVEL = 150;
    public static final int DISTRICTSEVEN_FIRSTLEVEL = 151;
    public static final int DISTRICTSEVEN_LASTLEVEL = 175;
    public static final int DISTRICTEIGHT_FIRSTLEVEL = 176;
    public static final int DISTRICTEIGHT_LASTLEVEL = 200;
    public static final int DISTRICTNINE_FIRSTLEVEL = 201;
    public static final int DISTRICTNINE_LASTLEVEL = 225;
    public static final int DISTRICTTEN_FIRSTLEVEL = 226;
    public static final int DISTRICTTEN_LASTLEVEL = 250;

    //GAMEPLAY VARIABLES
    public static final float HIGHEST_ADD_TIME = 2.2f;
    public static final float LOWEST_ADD_TIME = 1.6f;
    public static final float START_TIMER = 60;
    public static final int BUTTON_SIZE = 175;
    public static final int PLAY_BUTTON_SIZE = 300;
    public static final int GAME_BUTTON_SIZE = 125;
    public static final int GAMEOVER_BUTTON_SIZE = 200;
    public static final int LEVEL_BUTTON_SIZE = 140;
    public static final int SHOP_BUTTON_SIZE = 150;
    public static final int SHOP_BUTTON_SIZE_TABLET = 140;
    public static final int SHUFFLE_BUTTON_SIZE = 350;
    public static final int BIG_BUTTON_SIZE_TABLET43 = 250;
    public static final int NORMALBIG_BUTTON_SIZE_TABLET43 = 200;
    public static final int NORMAL_BUTTON_SIZE_TABLET43 = 150;
    public static final int SMALL_BUTTON_SIZE_TABLET43 = 110;
    public static final int EXTRASMALL_BUTTON_SIZE_TABLET43 = 100;
    public static final int SHUFFLE_BUTTON_SIZE_TABLET43 = 300;

    // SHOP
    public static final int SHOP_MOREMOVES_PACK = 10;
    public static final int SHOP_MORETIME_PACK = 10;
    public static final int SHOP_MULTICOLOR_PACK = 10;
    public static final int SHOP_RESOL_PACK = 3;

    //COLORS
    // Red
    public static final String COLOR_RED_100 = "#FFCDD2";
    public static final String COLOR_RED_200 = "#EF9A9A";
    public static final String COLOR_RED_300 = "#E57373";
    public static final String COLOR_RED_400 = "#EF5350";
    public static final String COLOR_RED_500 = "#F44336";
    public static final String COLOR_RED_600 = "#E53935";
    public static final String COLOR_RED_700 = "#D32F2F";
    public static final String COLOR_RED_800 = "#C62828";
    public static final String COLOR_RED_900 = "#B71C1C";
    public static final String COLOR_RED_1000 = "#6d0000";
    // Pink
    public static final String COLOR_PINK_100 = "#F8BBD0";
    public static final String COLOR_PINK_200 = "#F48FB1";
    public static final String COLOR_PINK_300 = "#F06292";
    public static final String COLOR_PINK_500 = "#E91E63";
    public static final String COLOR_PINK_800 = "#AD1457";
    public static final String COLOR_PINK_900 = "#880E4F";
    // Purple
    public static final String COLOR_PURPLE_100 = "#E1BEE7";
    public static final String COLOR_PURPLE_200 = "#CE93D8";
    public static final String COLOR_PURPLE_300 = "#BA68C8";
    public static final String COLOR_PURPLE_500 = "#9C27B0";
    public static final String COLOR_PURPLE_800 = "#6A1B9A";
    public static final String COLOR_PURPLE_900 = "#391f68";
    // Deep Purple
    public static final String COLOR_DEEPPURPLE_100 = "#D1C4E9";
    public static final String COLOR_DEEPPURPLE_300 = "#9575CD";
    public static final String COLOR_DEEPPURPLE_500 = "#673AB7";
    public static final String COLOR_DEEPPURPLE_800 = "#4527A0";
    public static final String COLOR_DEEPPURPLE_1000 = "#52127b";
    // Indigo
    public static final String COLOR_INDIGO_100 = "#C5CAE9";
    public static final String COLOR_INDIGO_300 = "#7986CB";
    public static final String COLOR_INDIGO_500 = "#3F51B5";
    public static final String COLOR_INDIGO_700 = "#303F9F";
    public static final String COLOR_INDIGO_800 = "#283593";
    public static final String COLOR_INDIGO_900 = "#1A237E";
    // Blue
    public static final String COLOR_BLUE_100 = "#BBDEFB";
    public static final String COLOR_BLUE_300 = "#64B5F6";
    public static final String COLOR_BLUE_500 = "#2196F3";
    public static final String COLOR_BLUE_800 = "#1565C0";
    public static final String COLOR_BLUE_900 = "#0b5a92";
    // Light Blue
    public static final String COLOR_LIGHTBLUE_100 = "#B3E5FC";
    public static final String COLOR_LIGHTBLUE_300 = "#4FC3F7";
    public static final String COLOR_LIGHTBLUE_500 = "#03A9F4";
    public static final String COLOR_LIGHTBLUE_800 = "#0277BD";
    // Cyan
    public static final String COLOR_CYAN_100 = "#B2EBF2";
    public static final String COLOR_CYAN_300 = "#4DD0E1";
    public static final String COLOR_CYAN_500 = "#00BCD4";
    public static final String COLOR_CYAN_800 = "#00838F";
    // Teal
    public static final String COLOR_TEAL_100 = "#B2DFDB";
    public static final String COLOR_TEAL_300 = "#4DB6AC";
    public static final String COLOR_TEAL_500 = "#009688";
    public static final String COLOR_TEAL_800 = "#00695C";
    // Green
    public static final String COLOR_GREEN_100 = "#C8E6C9";
    public static final String COLOR_GREEN_200 = "#A5D6A7";
    public static final String COLOR_GREEN_300 = "#81C784";
    public static final String COLOR_GREEN_400 = "#66BB6A";
    public static final String COLOR_GREEN_500 = "#4CAF50";
    public static final String COLOR_GREEN_800 = "#2E7D32";
    // Light Green
    public static final String COLOR_LIGHTGREEN_100 = "#DCEDC8";
    public static final String COLOR_LIGHTGREEN_300 = "#AED581";
    public static final String COLOR_LIGHTGREEN_500 = "#8BC34A";
    public static final String COLOR_LIGHTGREEN_800 = "#558B2F";
    // Lime
    public static final String COLOR_LIME_100 = "#F0F4C3";
    public static final String COLOR_LIME_300 = "#DCE775";
    public static final String COLOR_LIME_500 = "#CDDC39";
    public static final String COLOR_LIME_800 = "#9E9D24";
    // Yellow
    public static final String COLOR_YELLOW_100 = "#FFF9C4";
    public static final String COLOR_YELLOW_300 = "#FFF176";
    public static final String COLOR_YELLOW_500 = "#FFEB3B";
    public static final String COLOR_YELLOW_800 = "#F9A825";
    // Amber
    public static final String COLOR_AMBER_100 = "#FFECB3";
    public static final String COLOR_AMBER_200 = "#FFE082";
    public static final String COLOR_AMBER_300 = "#FFD54F";
    public static final String COLOR_AMBER_500 = "#FFC107";
    public static final String COLOR_AMBER_800 = "#FF8F00";
    // Orange
    public static final String COLOR_ORANGE_100 = "#FFE0B2";
    public static final String COLOR_ORANGE_300 = "#FFB74D";
    public static final String COLOR_ORANGE_500 = "#FF9800";
    public static final String COLOR_ORANGE_800 = "#EF6C00";
    // Brown
    public static final String COLOR_BROWN_100 = "#D7CCC8";
    public static final String COLOR_BROWN_300 = "#A1887F";
    public static final String COLOR_BROWN_500 = "#795548";
    public static final String COLOR_BROWN_800 = "#4E342E";
    public static final String COLOR_BROWN_800_CUST = "#583128";
    // Grey
    public static final String COLOR_GREY_300 = "#E0E0E0";
    public static final String COLOR_GREY_500 = "#9E9E9E";
    public static final String COLOR_GREY_800 = "#424242";
    // Blue Grey
    public static final String COLOR_BLUEGREY_100 = "#CFD8DC";
    public static final String COLOR_BLUEGREY_200 = "#B0BEC5";
    public static final String COLOR_BLUEGREY_300 = "#90A4AE";
    public static final String COLOR_BLUEGREY_400 = "#78909C";
    public static final String COLOR_BLUEGREY_500 = "#607D8B";
    public static final String COLOR_BLUEGREY_700 = "#455A64";
    public static final String COLOR_BLUEGREY_800 = "#37474F";
    // Black
    public static final String COLOR_BLACK = "#000000";
    // White
    public static final String COLOR_WHITE = "#FFFFFF";
    // Other colors
    public static final String COLOR_BACKGROUND_COLOR = "#2c3e50";
    public static final String COLOR_SPLASH_BACKGROUND = "#202d39";
    // Menu
    public static final String COLOR_PLAY_BUTTON = COLOR_GREEN_500;
    public static final String COLOR_SHOP_BUTTON = COLOR_DEEPPURPLE_500;
    public static final String COLOR_RATE_BUTTON = COLOR_RED_500;
    public static final String COLOR_ACHIEVEMENTS_BUTTON = COLOR_BLUE_500;
    public static final String COLOR_SHARE_BUTTON = COLOR_AMBER_500;
    // Game
    public static final String COLOR_BOARD = COLOR_BLUEGREY_100;
    // Districts
    public static final String COLOR_DISTRICT1 = COLOR_GREEN_500;
    public static final String COLOR_DISTRICT1_INACTIVE = COLOR_GREEN_300;
    public static final String COLOR_DISTRICT2 = COLOR_BROWN_500;
    public static final String COLOR_DISTRICT2_INACTIVE = COLOR_BROWN_300;
    public static final String COLOR_DISTRICT3 = COLOR_ORANGE_500;
    public static final String COLOR_DISTRICT3_INACTIVE = COLOR_ORANGE_300;
    public static final String COLOR_DISTRICT4 = COLOR_TEAL_500;
    public static final String COLOR_DISTRICT4_INACTIVE = COLOR_TEAL_300;
    public static final String COLOR_DISTRICT5 = COLOR_BLUE_500;
    public static final String COLOR_DISTRICT5_INACTIVE = COLOR_BLUE_300;
    public static final String COLOR_DISTRICT6 = COLOR_PURPLE_500;
    public static final String COLOR_DISTRICT6_INACTIVE = COLOR_PURPLE_300;
    public static final String COLOR_DISTRICT7 = COLOR_INDIGO_500;
    public static final String COLOR_DISTRICT7_INACTIVE = COLOR_INDIGO_300;
    public static final String COLOR_DISTRICT8 = COLOR_AMBER_500;
    public static final String COLOR_DISTRICT8_INACTIVE = COLOR_AMBER_300;
    public static final String COLOR_DISTRICT9 = COLOR_CYAN_500;
    public static final String COLOR_DISTRICT9_INACTIVE = COLOR_CYAN_300;
    public static final String COLOR_DISTRICT10 = COLOR_RED_500;
    public static final String COLOR_DISTRICT10_INACTIVE = COLOR_RED_300;
    // Tutorial
    public static final String COLOR_SHEET = "#dab757";
    public static final String COLOR_BOOK = "#6b2f29";

    //**** Strings ****//

    // LANGUAGES
    public static final String LANGUAGE_ENGLISH = "English";
    public static final String LANGUAGE_SPANISH = "Spanish";

    // HOME
    public static String HOME_PLAYBUTTON = "play";
    public static String HOME_SHOPBUTTON = "shop";
    public static String HOME_RATEBUTTON = "rate";
    public static String HOME_SETTINGSBUTTON = "settings";
    public static String HOME_SHAREBUTTON = "share";

    // SUBMENU
    public static String SUBMENU_ARCADE = "arcade";
    public static String SUBMENU_CHALLENGE = "challenge";

    // SHOP
    public static String SHOP_MORESTEPS_TEXT = "10 extra moves tokens";
    public static String SHOP_MORETIME_TEXT = "10 extra time tokens";
    public static String SHOP_MULTICOLOR_TEXT = "10 multicolor tokens";
    public static String SHOP_RESOL_TEXT = "3 solution tokens";
    public static String SHOP_RANDOM_TEXT = "Random token";
    public static String SHOP_NOADS_TEXT = "Remove the ads";
    public static String SHOP_MORESTEPS_SUBTEXT = "Get 3 extra movements for every extra moves token";
    public static String SHOP_MORETIME_SUBTEXT = "Get 10 seconds extra for every extra time token";
    public static String SHOP_MULTICOLOR_SUBTEXT = "Convert one token to multicolor";
    public static String SHOP_RESOL_SUBTEXT = "Solve the problem and pass to the next level";
    public static String SHOP_RANDOM_SUBTEXT = "Play to the token roulette for free and win supports for the game";
    public static String SHOP_MORESTEPS_PRICE = "0,99";
    public static String SHOP_MORETIME_PRICE = "0,99";
    public static String SHOP_MULTICOLOR_PRICE = "0,99";
    public static String SHOP_RESOL_PRICE = "0,99";
    public static String SHOP_NOADS_PRICE = "0,99";

    // RANDOM GAME
    public static String RANDOM_TITLE = "TOKEN ROULETTE";
    public static String RANDOM_MORESTEPS = "EXTRA MOVES TOKEN!";
    public static String RANDOM_MORETIME = "EXTRA TIME TOKEN!";
    public static String RANDOM_MULTICOLOR = "MULTICOLOR TOKEN!";
    public static String RANDOM_RESOL = "SOLUTION TOKEN!";
    public static String RANDOM_NOTHING = "EMPEROR COIN...";
    public static String RANDOM_PLAY_SUBTEXT = "Welcome to the token roulette, press the play button to begin...";
    public static String RANDOM_STOP_SUBTEXT = "... and press the pause button to stop the roulette";
    public static String RANDOM_MORESTEPS_SUBTEXT = "Congratulations! You've won a extra moves token";
    public static String RANDOM_MORETIME_SUBTEXT = "Congratulations! You've won a extra time token";
    public static String RANDOM_MULTICOLOR_SUBTEXT = "Congratulations! You've won a multicolor token";
    public static String RANDOM_RESOL_SUBTEXT = "Congratulations! You've won a solution token";
    public static String RANDOM_NOTHING_SUBTEXT = "Oh, more luck next time...";

    // SETTINGS
    public static String SETTINGS_MUSIC_TEXT = "Music";
    public static String SETTINGS_SOUND_TEXT = "Sound";
    public static String SETTINGS_LANGUAGE_TEXT = "Language";
    public static String SETTINGS_TUTORIAL_TEXT = "Tutorial";

    //TUTORIAL
    public static String BEGINING_TUTORIAL_TITLE1 = "Welcome to tokens!";
    public static String BEGINING_TUTORIAL_TEXT1 = "Welcome to tokens! the objective of the game is to order the tokens of the same color...";
    public static String BEGINING_TUTORIAL_TEXT2 = "like this...";
    public static String BEGINING_TUTORIAL_TEXT3 = "like this... or like this...";
    public static String BEGINING_TUTORIAL_TEXT4 = "like this... or like this... or like whatever ordered position you imagine!";
    public static String BEGINING_TUTORIAL_TEXT5 = "but be careful, because the movements are limited...";
    public static String BEGINING_TUTORIAL_TEXT6 = "the missing tokens to order will appear here...";
    public static String BEGINING_TUTORIAL_TEXT61 = "Let's solve our first puzzle";
    public static String BEGINING_TUTORIAL_TEXT7 = "Well done! In case need a hand, can use the following items";
    public static String BEGINING_TUTORIAL_TEXT8 = "to convert one token to multicolor";
    public static String BEGINING_TUTORIAL_TEXT9 = "to get more movements";
    public static String BEGINING_TUTORIAL_TEXT10 = "to pass to the next level";
    public static String BEGINING_TUTORIAL_TEXT101 = "Let's try to use one multicolor token";
    public static String BEGINING_TUTORIAL_TEXT11 = "Great! That's all for now, enjoy Tokens!";
    public static String TUTORIAL_REPLAY_TEXT = "See again";
    public static String STATIC_TUTORIAL_TITLE = "blocked token";
    public static String STATIC_TUTORIAL_TEXT = "this is the blocked token, it cannot move";
    public static String TIME_TUTORIAL_TITLE = "chrono level";
    public static String TIME_TUTORIAL_TEXT = "all the moves that you want, but go fast, the time is running";
    public static String TIME_TUTORIAL_TIME_TEXT = "3";
    public static String MEMORY_TUTORIAL_TITLE = "Memory level";
    public static String MEMORY_TUTORIAL_TEXT = "";
    public static String CHANGEPOSITION_TUTORIAL_TITLE = "Position change level";
    public static String CHANGEPOSITION_TUTORIAL_TEXT = "";
    public static String CHANGECOLOR_TUTORIAL_TITLE = "Color change level";
    public static String CHANGECOLOR_TUTORIAL_TEXT = "";
    public static String CHANGEBLOCKED_TUTORIAL_TITLE = "Blocked change level";
    public static String CHANGEBLOCKED_TUTORIAL_TEXT = "";

    // GAME
    public static String GAME_LEFTBANNER_TEXT = "left";
    public static String GAME_LEVELBANNER_TEXT = "level";
    public static String GAME_MOVESBANNER_TEXT = "moves";
    public static String GAME_TIMEBANNER_TEXT = "time";
    public static String GAME_SCOREBANNER_TEXT = "score";
    public static String GAME_GAMEOVER_PASS_TEXT = "Well Done!";
    public static String GAME_GAMEOVER_REPLAY_TEXT = "Try Again";
    public static String GAME_GAMEOVER_ROULETTE_TEXT = "Need a hand? Play to the Token Roulette for free and win supports for the game!";
    public static String GAME_GAMEOVER_RATE_TEXT = "Are you enjoying Tokens? Support us with your rate!";

    // DISTRICTS
    public static String LEVELS_DISTRICT_ONE_TEXT = "DISTRICT ONE";
    public static String LEVELS_DISTRICT_TWO_TEXT = "DISTRICT TWO";
    public static String LEVELS_DISTRICT_THREE_TEXT = "DISTRICT THREE";
    public static String LEVELS_DISTRICT_FOUR_TEXT = "DISTRICT FOUR";
    public static String LEVELS_DISTRICT_FIVE_TEXT = "DISTRICT FIVE";
    public static String LEVELS_DISTRICT_SIX_TEXT = "DISTRICT SIX";
    public static String LEVELS_DISTRICT_SEVEN_TEXT = "DISTRICT SEVEN";
    public static String LEVELS_DISTRICT_EIGHT_TEXT = "DISTRICT EIGHT";
    public static String LEVELS_DISTRICT_NINE_TEXT = "DISTRICT NINE";
    public static String LEVELS_DISTRICT_TEN_TEXT = "DISTRICT TEN";

    // STORY
    public static String DISTRICT_ONE_DESCRIPTION = "";
    public static String DISTRICT_TWO_DESCRIPTION = "";
    public static String DISTRICT_THREE_DESCRIPTION = "";
    public static String DISTRICT_FOUR_DESCRIPTION = "";
    public static String DISTRICT_FIVE_DESCRIPTION = "";
    public static String DISTRICT_SIX_DESCRIPTION = "";
    public static String DISTRICT_SEVEN_DESCRIPTION = "";
    public static String DISTRICT_EIGHT_DESCRIPTION = "";
    public static String DISTRICT_NINE_DESCRIPTION = "";
    public static String DISTRICT_TEN_DESCRIPTION = "";

    // MEDAL
    public static String MEDALONE_TEXT = "DISTRICT ONE SET FREE!";
    public static String MEDALTWO_TEXT = "DISTRICT TWO SET FREE!";
    public static String MEDALTHREE_TEXT = "DISTRICT THREE SET FREE!";
    public static String MEDALFOUR_TEXT = "DISTRICT FOUR SET FREE!";
    public static String MEDALFIVE_TEXT = "DISTRICT FIVE SET FREE!";
    public static String MEDALSIX_TEXT = "DISTRICT SIX SET FREE!";
    public static String MEDALSEVEN_TEXT = "DISTRICT SEVEN SET FREE!";
    public static String MEDALEIGHT_TEXT = "DISTRICT EIGHT SET FREE!";
    public static String MEDALNINE_TEXT = "DISTRICT NINE SET FREE!";
    public static String MEDALTEN_TEXT = "DISTRICT TEN SET FREE!";


    // CHARACTER
    public static String CHARACTER_TEXT_ONE = "";
    public static String CHARACTER_TEXT_TWO = "";
    public static String CHARACTER_TEXT_THREE = "";
    public static String CHARACTER_PART_TWO_TEXT = "";
    public static String CHARACTER_PART_THREE_TEXT = "";
    public static String CHARACTER_PART_THREE_GOTEXT = "";
    public static String CHARACTER_PART_THREE_SKIPTEXT = "";

    // CONTINUE
    public static String CONTINUE_TEXT = "Continue";

    //Share Message
    public static String SHARE_MESSAGE = "Have you tried " + GAME_NAME + "? Best Game EVER! Download here! ";

    public Settings(){
        //*** ENGLISH ***//
        if(AssetLoader.getLanguage().equals(LANGUAGE_ENGLISH)){
            // HOME
            HOME_PLAYBUTTON = "play";
            HOME_SHOPBUTTON = "shop";
            HOME_RATEBUTTON = "rate";
            HOME_SETTINGSBUTTON = "settings";
            HOME_SHAREBUTTON = "share";
            // SUBMENU
            SUBMENU_ARCADE = "arcade";
            SUBMENU_CHALLENGE = "the challenge";
            // SHOP
            SHOP_MORESTEPS_TEXT = "10 extra moves tokens";
            SHOP_MORETIME_TEXT = "10 extra time tokens";
            SHOP_MULTICOLOR_TEXT = "10 multicolor tokens";
            SHOP_RESOL_TEXT = "3 solution tokens";
            SHOP_RANDOM_TEXT = "Random token";
            SHOP_NOADS_TEXT = "Remove the ads";
            SHOP_MORESTEPS_SUBTEXT = "Get 3 movements extra for every extra moves token";
            SHOP_MORETIME_SUBTEXT = "Get 10 seconds extra for every extra time token";
            SHOP_MULTICOLOR_SUBTEXT = "Convert one normal token to multicolor";
            SHOP_RESOL_SUBTEXT = "Solve the problem and pass to the next level";
            SHOP_RANDOM_SUBTEXT = "Play to the token roulette for FREE and win supports for the game!";
            SHOP_MORESTEPS_PRICE = "0,99€";
            SHOP_MORETIME_PRICE = "0,99€";
            SHOP_MULTICOLOR_PRICE = "0,99€";
            SHOP_RESOL_PRICE = "0,99€";
            SHOP_NOADS_PRICE = "0,99€";
            // RANDOM GAME
            RANDOM_TITLE = "TOKEN ROULETTE";
            RANDOM_MORESTEPS = "EXTRA MOVES TOKEN!";
            RANDOM_MORETIME = "EXTRA TIME TOKEN!";
            RANDOM_MULTICOLOR = "MULTICOLOR TOKEN!";
            RANDOM_RESOL = "SOLUTION TOKEN!";
            RANDOM_NOTHING = "EMPEROR COIN...";
            RANDOM_PLAY_SUBTEXT = "Welcome to the token roulette, press the play button to begin...";
            RANDOM_STOP_SUBTEXT = "... and press the pause button to stop the roulette";
            RANDOM_MORESTEPS_SUBTEXT = "Congratulations! You've won a extra moves token";
            RANDOM_MORETIME_SUBTEXT = "Congratulations! You've won a extra time token";
            RANDOM_MULTICOLOR_SUBTEXT = "Congratulations! You've won a multicolor token";
            RANDOM_RESOL_SUBTEXT = "Congratulations! You've won a solution token";
            RANDOM_NOTHING_SUBTEXT = "Oh, more luck next time...";
            // SETTINGS
            SETTINGS_MUSIC_TEXT = "Music";
            SETTINGS_SOUND_TEXT = "Sound";
            SETTINGS_LANGUAGE_TEXT = "Language";
            //TUTORIAL
            BEGINING_TUTORIAL_TITLE1 = "Welcome to tokens!";
            BEGINING_TUTORIAL_TEXT1 = "Welcome to Tokens! The objective of the game is put in order the tokens sliding it... Like the examples above";
            BEGINING_TUTORIAL_TEXT2 = "like this...";
            BEGINING_TUTORIAL_TEXT3 = "like this... or like this...";
            BEGINING_TUTORIAL_TEXT4 = "like this... or like this... or like whatever ordered position you imagine!";
            BEGINING_TUTORIAL_TEXT5 = "But be careful, because the movements are limited, you should find the shortest combination...";
            BEGINING_TUTORIAL_TEXT6 = "The missing tokens to put in order will appear here...";
            BEGINING_TUTORIAL_TEXT61 = "Let's solve our first puzzle";
            BEGINING_TUTORIAL_TEXT7 = "Well done! In case you need a hand, can use the following items";
            BEGINING_TUTORIAL_TEXT8 = "To convert one token to multicolor";
            BEGINING_TUTORIAL_TEXT9 = "To get more movements";
            BEGINING_TUTORIAL_TEXT10 = "To pass to the next level";
            BEGINING_TUTORIAL_TEXT101 = "Let's try to use one multicolor token";
            BEGINING_TUTORIAL_TEXT11 = "Great! That's all for now, enjoy Tokens!";
            TUTORIAL_REPLAY_TEXT = "See again";
            STATIC_TUTORIAL_TITLE = "Blocked Token";
            STATIC_TUTORIAL_TEXT = "This is the blocked token, it cannot move.";
            TIME_TUTORIAL_TITLE = "Chrono Level";
            TIME_TUTORIAL_TEXT = "All the moves that you want, but go fast, the time is running.";
            TIME_TUTORIAL_TIME_TEXT = "3";
            MEMORY_TUTORIAL_TITLE = "Memory Level";
            MEMORY_TUTORIAL_TEXT = "When the green counter reaches zero, all the tokens will lose their color, use your memory to solve the puzzle.";
            CHANGEPOSITION_TUTORIAL_TITLE = "Position change Level";
            CHANGEPOSITION_TUTORIAL_TEXT = "When the counters reach zero, the tokens with counter will interchange their positions, you should anticipate";
            CHANGECOLOR_TUTORIAL_TITLE = "Color change Token";
            CHANGECOLOR_TUTORIAL_TEXT = "This is Token color change, every time their counter reach zero, he changes his color.";
            CHANGEBLOCKED_TUTORIAL_TITLE = "Blocked change Level";
            CHANGEBLOCKED_TUTORIAL_TEXT = "Every movement you do, the blocking will change his position.";
            // GAME
            GAME_LEFTBANNER_TEXT = "left";
            GAME_LEVELBANNER_TEXT = "level";
            GAME_MOVESBANNER_TEXT = "moves";
            GAME_TIMEBANNER_TEXT = "time";
            GAME_SCOREBANNER_TEXT = "score";
            GAME_GAMEOVER_PASS_TEXT = "Well Done!";
            GAME_GAMEOVER_REPLAY_TEXT = "Try Again";
            GAME_GAMEOVER_ROULETTE_TEXT = "Need a hand? Play to the Token Roulette for FREE and win supports for the game!";
            GAME_GAMEOVER_RATE_TEXT = "Are you enjoying Tokens? Support us with your rate!";
            // DISTRICTS
            LEVELS_DISTRICT_ONE_TEXT = "DISTRICT ONE";
            LEVELS_DISTRICT_TWO_TEXT = "DISTRICT TWO";
            LEVELS_DISTRICT_THREE_TEXT = "DISTRICT THREE";
            LEVELS_DISTRICT_FOUR_TEXT = "DISTRICT FOUR";
            LEVELS_DISTRICT_FIVE_TEXT = "DISTRICT FIVE";
            LEVELS_DISTRICT_SIX_TEXT = "DISTRICT SIX";
            LEVELS_DISTRICT_SEVEN_TEXT = "DISTRICT SEVEN";
            LEVELS_DISTRICT_EIGHT_TEXT = "DISTRICT EIGHT";
            LEVELS_DISTRICT_NINE_TEXT = "DISTRICT NINE";
            LEVELS_DISTRICT_TEN_TEXT = "DISTRICT TEN";
            // STORY
            DISTRICT_ONE_DESCRIPTION = "The Emperor controls the tribes that live in the jungle. The Hoimon tribe needs your help to get their awaited freedom and to protect their most valued treasure, their jungle, that is under the control of the empire and destined to disappear...";
            DISTRICT_TWO_DESCRIPTION = "The farmers harvest the land on one of the poorest districts. The Emperor, proprietary of the lands, keeps with big part of the benefits obtained by the people, who fights for maintain the most valued thing, the land...";
            DISTRICT_THREE_DESCRIPTION = "With a great history buried under the sand in the desert, this district is going throw difficult moment. Their people, slaves to the Empire, are obliged to work night and day looking for the valued treasures hidden in the old pyramids on the desert. Will you be able to set them free?";
            DISTRICT_FOUR_DESCRIPTION = "The people who live in the Temple Mountain are peaceful, and their best skill is their knowledge. The Emperor wants to invade their land and remove the wise people of the Temple Mountain, so the knowledge will not be longer available...";
            DISTRICT_FIVE_DESCRIPTION = "The people from the coast, after generations and generations of fishers, are obliged to fish from sunrise to sunset to bring fish to the Emperor. The species are disappearing and the fishers can't bring enough food for his people. This situation will finish with the fishes and the people of this district without your help. Will you able to change this situation?";
            DISTRICT_SIX_DESCRIPTION = "Musicians were a respected people, and they made the life better to the other people around the world. After the rising of the emperor, their music is sad and they can only play the songs of the Empire. Will you able to give back their happiness?";
            DISTRICT_SEVEN_DESCRIPTION = "Diamond extraction! The people of this district must work night and day in the mines to send the gemstones to the emperor. The enslaved people need your help. Are you ready?";
            DISTRICT_EIGHT_DESCRIPTION = "The Oriental district was a peaceful and calm land, where the people was happy enjoying their melancholy landscapes and their millenary traditions. All ended with the war. The Empire destroyed their land and their lives. Now they need your help to revolt against the Emperor.";
            DISTRICT_NINE_DESCRIPTION = "The Nomadic tribes live in the most cold place. They were happy with their simple lifes, hunting and growing as a free people... But now the tribes are pursued for the Empire. The boss of the tribe, Big Mama, paid with her life their fight. Join them!";
            DISTRICT_TEN_DESCRIPTION = "The Viking district live a bad moment since emperor took their lands. The barbarian society mustn't follow her religion and the emperor forbid the vikings to have arms for prevent a rebellion. The emperor has their warriors jailed. Would you help the northern viking tribes?";
            // MEDAL
            MEDALONE_TEXT = "DISTRICT ONE SET FREE!";
            MEDALTWO_TEXT = "DISTRICT TWO SET FREE!";
            MEDALTHREE_TEXT = "DISTRICT THREE SET FREE!";
            MEDALFOUR_TEXT = "DISTRICT FOUR SET FREE!";
            MEDALFIVE_TEXT = "DISTRICT FIVE SET FREE!";
            MEDALSIX_TEXT = "DISTRICT SIX SET FREE!";
            MEDALSEVEN_TEXT = "DISTRICT SEVEN SET FREE!";
            MEDALEIGHT_TEXT = "DISTRICT EIGHT SET FREE!";
            MEDALNINE_TEXT = "DISTRICT NINE SET FREE!";
            MEDALTEN_TEXT = "DISTRICT TEN SET FREE!";
            // CHARACTER
            CHARACTER_TEXT_ONE = "My dear my people, 21 years has passed since the end of the war, and I, as your loved emperor, want to celebrate that in a special way... I will let you vote...";
            CHARACTER_TEXT_TWO = "Meat or lobster. What do you think guests will like more for dinner?";
            CHARACTER_TEXT_THREE = "Why that serious faces? The council found that funny... You see, you really don't like democracy!";
            CHARACTER_PART_TWO_TEXT = "After 10 years of war, the world was divided into districts, under the force of the empire, that oppress their people. Tells the token legend, that the one who able to join all the medals of the districts, will restore the freedom. Will you be able to solve all the puzzles?";
            CHARACTER_PART_THREE_TEXT = "Do you want to see the token handbook?";
            CHARACTER_PART_THREE_GOTEXT = "See";
            CHARACTER_PART_THREE_SKIPTEXT = "Skip";
            // CONTINUE
            CONTINUE_TEXT = "Continue";
            //Share Message
            SHARE_MESSAGE = "Have you tried " + GAME_NAME + "? Best Game EVER! Download here! ";

            //*** SPANISH ***//
        } else if(AssetLoader.getLanguage().equals(LANGUAGE_SPANISH)){
            // HOME
            HOME_PLAYBUTTON = "jugar";
            HOME_SHOPBUTTON = "tienda";
            HOME_RATEBUTTON = "valorar";
            HOME_SETTINGSBUTTON = "opciones";
            HOME_SHAREBUTTON = "compartir";
            // SUBMENU
            SUBMENU_ARCADE = "arcade";
            SUBMENU_CHALLENGE = "el reto";
            // SHOP
            SHOP_MORESTEPS_TEXT = "10 tokens extra pasos";
            SHOP_MORETIME_TEXT = "10 tokens extra tiempo";
            SHOP_MULTICOLOR_TEXT = "10 tokens multicolor";
            SHOP_RESOL_TEXT = "3 tokens solución";
            SHOP_RANDOM_TEXT = "Token al azar";
            SHOP_NOADS_TEXT = "Eliminar los anuncios";
            SHOP_MORESTEPS_SUBTEXT = "Consigue 3 movimientos extra por cada token extra pasos";
            SHOP_MORETIME_SUBTEXT = "Consigue 10 segundos extra por cada token extra tiempo";
            SHOP_MULTICOLOR_SUBTEXT = "Convierte un token normal en multicolor";
            SHOP_RESOL_SUBTEXT = "Resuelve el problema y pasa al siguiente nivel";
            SHOP_RANDOM_SUBTEXT = "Juega a la ruleta token GRATIS y gana objetos ayuda para el juego!";
            SHOP_MORESTEPS_PRICE = "0,99€";
            SHOP_MORETIME_PRICE = "0,99€";
            SHOP_MULTICOLOR_PRICE = "0,99€";
            SHOP_RESOL_PRICE = "0,99€";
            SHOP_NOADS_PRICE = "0,99€";
            // RANDOM GAME
            RANDOM_TITLE = "RULETA TOKEN";
            RANDOM_MORESTEPS = "TOKEN EXTRA PASOS!";
            RANDOM_MORETIME = "TOKEN EXTRA TIEMPO!";
            RANDOM_MULTICOLOR = "TOKEN MULTICOLOR!";
            RANDOM_RESOL = "TOKEN SOLUCIÓN!";
            RANDOM_NOTHING = "MONEDA EMPERADOR...";
            RANDOM_PLAY_SUBTEXT = "Bienvenido a la ruleta token, pulsa el botón play para iniciar...";
            RANDOM_STOP_SUBTEXT = "... y el botón pause para parar la ruleta";
            RANDOM_MORESTEPS_SUBTEXT = "Felicidades! Has ganado un token extra pasos";
            RANDOM_MORETIME_SUBTEXT = "Felicidades! Has ganado un token extra tiempo";
            RANDOM_MULTICOLOR_SUBTEXT = "Felicidades! Has ganado un token multicolor";
            RANDOM_RESOL_SUBTEXT = "Felicidades! Has ganado un token solución";
            RANDOM_NOTHING_SUBTEXT = "Oh, más suerte la próxima vez...";
            // SETTINGS
            SETTINGS_MUSIC_TEXT = "Música";
            SETTINGS_SOUND_TEXT = "Sonido";
            SETTINGS_LANGUAGE_TEXT = "Idioma";
            //TUTORIAL
            BEGINING_TUTORIAL_TITLE1 = "Bienvenido a tokens!";
            BEGINING_TUTORIAL_TEXT1 = "¡Bienvenido a Tokens! El objetivo del juego es ordenar los tokens deslizándolos...Tal y como los ejemplos de más arriba";
            BEGINING_TUTORIAL_TEXT2 = "por ejemplo, así...";
            BEGINING_TUTORIAL_TEXT3 = "por ejemplo, así... o así...";
            BEGINING_TUTORIAL_TEXT4 = "por ejemplo, así... o así... o en cualquier posición ordenada que imagines!";
            BEGINING_TUTORIAL_TEXT5 = "Pero no tan fácil, los movimientos son limitados, asi que deberás encontrar la combinación más corta...";
            BEGINING_TUTORIAL_TEXT6 = "Los tokens que falten por ordenar aparecerán aquí...";
            BEGINING_TUTORIAL_TEXT61 = "Vamos a resolver nuestro primer rompecabezas";
            BEGINING_TUTORIAL_TEXT7 = "¡Bien Hecho! En caso de necesitar una mano, puedes usar los siguientes objetos";
            BEGINING_TUTORIAL_TEXT8 = "Para convertir un token en multicolor";
            BEGINING_TUTORIAL_TEXT9 = "Para conseguir más movimientos";
            BEGINING_TUTORIAL_TEXT10 = "Para pasar al siguiente nivel";
            BEGINING_TUTORIAL_TEXT101 = "Prueba ahora usando un token multicolor";
            BEGINING_TUTORIAL_TEXT11 = "¡Bien! Esto es todo por ahora, ¡Disfruta de Tokens!";
            TUTORIAL_REPLAY_TEXT = "Ver otra vez";
            STATIC_TUTORIAL_TITLE = "Token bloqueado";
            STATIC_TUTORIAL_TEXT = "Este es Token bloqueado, no puede moverse.";
            TIME_TUTORIAL_TITLE = "Nivel contrarreloj";
            TIME_TUTORIAL_TEXT = "Puedes realizar todos los movimientos que quieras, pero rápido, el tiempo corre.";
            TIME_TUTORIAL_TIME_TEXT = "3";
            MEMORY_TUTORIAL_TITLE = "Nivel Memoria";
            MEMORY_TUTORIAL_TEXT = "Cuando el marcador verde llegue a cero, todos los tokens perderán el color, utiliza tu memoria para resolver el puzzle.";
            CHANGEPOSITION_TUTORIAL_TITLE = "Nivel cambio de posición";
            CHANGEPOSITION_TUTORIAL_TEXT = "Atento, cuando lleguen a cero, los tokens con marcadores intercambiarán su posición, deberás anticiparte.";
            CHANGECOLOR_TUTORIAL_TITLE = "Token cambio de color";
            CHANGECOLOR_TUTORIAL_TEXT = "Este es Token cambio de color, cada vez que su marcador llega a cero cambia de color.";
            CHANGEBLOCKED_TUTORIAL_TITLE = "Nivel cambio bloqueado";
            CHANGEBLOCKED_TUTORIAL_TEXT = "Cada vez que realices un movimiento, el bloqueo cambiará de posición.";
            // GAME
            GAME_LEFTBANNER_TEXT = "faltan";
            GAME_LEVELBANNER_TEXT = "nivel";
            GAME_MOVESBANNER_TEXT = "pasos";
            GAME_TIMEBANNER_TEXT = "tiempo";
            GAME_SCOREBANNER_TEXT = "puntos";
            GAME_GAMEOVER_PASS_TEXT = "Bien Hecho!";
            GAME_GAMEOVER_REPLAY_TEXT = "Intenta Otra";
            GAME_GAMEOVER_ROULETTE_TEXT = "¿Necesitas una mano? ¡Juega GRATIS a la Ruleta Token y gana objetos ayuda!";
            GAME_GAMEOVER_RATE_TEXT = "¿Estás disfrutando de Tokens? ¡Apoyanos con tu valoración!";
            // DISTRICTS
            LEVELS_DISTRICT_ONE_TEXT = "DISTRITO UNO";
            LEVELS_DISTRICT_TWO_TEXT = "DISTRITO DOS";
            LEVELS_DISTRICT_THREE_TEXT = "DISTRITO TRES";
            LEVELS_DISTRICT_FOUR_TEXT = "DISTRITO CUATRO";
            LEVELS_DISTRICT_FIVE_TEXT = "DISTRITO CINCO";
            LEVELS_DISTRICT_SIX_TEXT = "DISTRITO SEIS";
            LEVELS_DISTRICT_SEVEN_TEXT = "DISTRITO SIETE";
            LEVELS_DISTRICT_EIGHT_TEXT = "DISTRITO OCHO";
            LEVELS_DISTRICT_NINE_TEXT = "DISTRITO NUEVE";
            LEVELS_DISTRICT_TEN_TEXT = "DISTRITO DIEZ";
            // STORY
            DISTRICT_ONE_DESCRIPTION = "El Emperador tiene bajo su control a las tribus que habitan en la selva. La tribu Hoimon necesita tu ayuda para conseguir su ansiada libertad y proteger su mayor tesoro, su selva y sus tradiciones, que bajo el yugo del Emperador, están abocados a desaparecer...";
            DISTRICT_TWO_DESCRIPTION = "Los campesinos cosechan la tierra en uno de los distritos más pobres. El Emperador, como propietario de los campos, se queda con gran parte del beneficio obtenido por los habitantes, que luchan por mantener su bien más preciado, la tierra...";
            DISTRICT_THREE_DESCRIPTION = "Con una gran historia enterrada bajo las arenas del desierto, este distrito vive ahora sus momentos más duros. Los habitantes, esclavizados por el Emperador, son obligados a trabajar día y noche buscando los preciados tesoros escondidos en las antiguas pirámides. ¿Serás capaz de liberarlos?";
            DISTRICT_FOUR_DESCRIPTION = "El pueblo que vive en la Montaña Templo es pacífico y su mejor don es el poder de la sabiduría. El Emperador quiere invadir su tierra para eliminar a los sabios de la Montaña Templo, destruir la sabíduria, el conocimiento y hacer sucumbir al mundo en la máxima ignorancia...";
            DISTRICT_FIVE_DESCRIPTION = "La gente de la costa, tras generaciones y generaciones de pescadores, son obligados a pescar de sol a sol para el emperador. Las especies están desapareciendo y los pescadores no logran suficiente comida para su gente. Esta situación acabará con los peces y con las personas de este distrito si no les ayudas. ¿Serás capaz de cambiar esta situación?";
            DISTRICT_SIX_DESCRIPTION = "Los músicos eran respetados y hacían la vida mejor a la población. Pero desde que el emperador subió al poder, su música es triste y solo pueden tocar las canciones que el emperador elige, toda canción prohibida que sea interpretada y escuchada, provocarà el castigo de todo aquél que la toque o la escuche.";
            DISTRICT_SEVEN_DESCRIPTION = "¡Extracción de diamantes! La gente de este distrito debe recoger todos los diamantes que se encuentran en las minas para el emperador. El pueblo esclavizado vive en las peores condiciones y deben trabajar todo el día para enriquecer al emperador. ¡Necesitan tu ayuda!";
            DISTRICT_EIGHT_DESCRIPTION = "La paz y la calma reinaba en las tierras orientales en donde la gente era feliz, disfrutando de sus paisajes melancólicos y de sus tradiciones milenarias. Todo esto terminó con la guerra. La dureza del gran emperador destrozó la vida de aquellos que vivían en las tierras de oriente. Ahora necesitan tu ayuda para poder revelarse y recuperar todo aquello que un día les quitaron...";
            DISTRICT_NINE_DESCRIPTION = "La tribus nómadas viven en el lugar más frío conocido, eran felices cuando su vida era sencilla, cazar, amar, crecer... Pero cuando el emperador subió al poder, las tribus nómadas del hielo fueron perseguidas por querer ser libres, la jefa de la tribu, conocida como Gran Mamá, defendió con su vida la resistencia. ¡Únete a ellos!";
            DISTRICT_TEN_DESCRIPTION = "El distrito vikingo vive un mal momento desde que el emperador se hizo con el mando de sus tierras. A la sociedad bárbara no se le permite seguir su religión y se les ha prohibido tener en su poder armas para evitar una rebelión. El emperador tiene a sus guerreros encarcelados. ¿Podrás ayudar a las tribus vikingas del norte?";
            // MEDAL
            MEDALONE_TEXT = "DISTRITO UNO LIBERADO!";
            MEDALTWO_TEXT = "DISTRITO DOS LIBERADO!";
            MEDALTHREE_TEXT = "DISTRITO TRES LIBERADO!";
            MEDALFOUR_TEXT = "DISTRITO CUATRO LIBERADO!";
            MEDALFIVE_TEXT = "DISTRITO CINCO LIBERADO!";
            MEDALSIX_TEXT = "DISTRITO SEIS LIBERADO!";
            MEDALSEVEN_TEXT = "DISTRITO SIETE LIBERADO!";
            MEDALEIGHT_TEXT = "DISTRITO OCHO LIBERADO!";
            MEDALNINE_TEXT = "DISTRITO NUEVE LIBERADO!";
            MEDALTEN_TEXT = "DISTRITO DIEZ LIBERADO!";
            // CHARACTER
            CHARACTER_TEXT_ONE = "Queridos súbditos, hace ya 21 años que acabó la guerra, y como vuestro querido Emperador que soy, quiero celebrarlo de una manera especial... os dejaré votar...";
            CHARACTER_TEXT_TWO = "Carne de buey o langosta escocesa, ¿Que creéis que les gustará más a los invitados?";
            CHARACTER_TEXT_THREE = "¿Por qué esas caras tan serias? Al consejo les pareció divertido... ¡Veis, si en realidad no os gusta la democracia!";
            CHARACTER_PART_TWO_TEXT = "Tras 10 años de guerra, el mundo quedó dividido en distritos, sometidos a la voluntad del Emperador, que oprime a sus pueblos. Cuenta la leyenda token, que aquel que sea capaz de reunir las medallas de cada distrito, restablecerá la libertad. ¿Serás capaz de resolver todos los rompecabezas?";
            CHARACTER_PART_THREE_TEXT = "¿Quieres ver la guía token?";
            CHARACTER_PART_THREE_GOTEXT = "Ver";
            CHARACTER_PART_THREE_SKIPTEXT = "Saltar";
            // CONTINUE
            CONTINUE_TEXT = "Continuar";
            //Share Message
            SHARE_MESSAGE = "Has probado " + GAME_NAME + "? Es GENIAL! Descárgalo aquí! ";
        }
    }
}
