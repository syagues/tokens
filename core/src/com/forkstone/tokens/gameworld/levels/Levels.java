package com.forkstone.tokens.gameworld.levels;

import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;

import java.util.ArrayList;

/**
 * Created by sergi on 14/1/16.
 */
public class Levels {

    private GameWorld world;
    private int level, boardSize, steps, sameColorNumber;
    private float time;
    private int memoryVisibleSteps, memoryInvisibleSteps;
    private boolean memoryInitialVisiblity;
    private int movableSteps;
    private int[] movablePositionsArray;
    private int colorChangeSteps;
    private int[] colorChangeTokensArray;
    private int[] blockedChangeArrayInitial, blockedChangeArray;
    private int[] positionsArray, staticPositionsArray;
    private ArrayList<Integer> positionArrayList = new ArrayList<Integer>();

    public Levels(GameWorld world, int level){

        this.world = world;
        this.level = level;
        steps = 0;
        time = 0f;
        memoryVisibleSteps = 0;
        memoryInvisibleSteps = 0;
        memoryInitialVisiblity = false;
        movableSteps = 0;
        movablePositionsArray = null;
        staticPositionsArray = null;

        // UNBLOCK LEVELS FOR TESTING
//        AssetLoader.setLevel(Settings.TOTAL_NUMBER_OF_LEVELS);
//        if(AssetLoader.getLevel()< 101){
//            AssetLoader.setLevel(101);
//        }

        setLevel();
    }

    public void setLevel(){

        switch (level){
            case 1:
                positionsArray = new int[]{1,3,-1,2,3,2,1,1,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                steps = 19;
                sameColorNumber = 3;
                break;
            case 2:
                positionsArray = new int[]{0,3,3,2,3,0,2,2,-1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                steps = 22;
                sameColorNumber = 3;
                break;
            case 3:
                positionsArray = new int[]{0,4,3,4,3,3,-1,4,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                steps = 23;
                sameColorNumber = 3;
                break;
            case 4:
                positionsArray = new int[]{2,0,3,0,-1,2,3,0,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                steps = 16;
                sameColorNumber = 3;
                break;
            case 5:
                positionsArray = new int[]{-1,10,6,4,6,4,6,4,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                steps = 19;
                sameColorNumber = 3;
                break;
            case 6:
                positionsArray = new int[]{5,13,-1,6,5,13,6,6,5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                steps = 14;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{4};
                break;
            case 7:
                positionsArray = new int[]{12,12,-1,8,5,5,12,8,5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                steps = 12;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{0,8};
                break;
            case 8:
                positionsArray = new int[]{7,7,-1,10,4,4,4,10,7};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                steps = 21;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{3};
                break;
            case 9:
                positionsArray = new int[]{6,6,8,0,6,0,8,8,-1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                steps = 20;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{7};
                break;
            case 10:
                positionsArray = new int[]{6, -1, 5, 8, 5, 8, 8, 6, 5};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                time = 23f;
                sameColorNumber = 3;
                break;
            case 11:
                positionsArray = new int[]{10, 2, 10, 10, 2, 9, 10, 9, 9, -1, 2, 9};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                time = 30f;
                sameColorNumber = 4;
                break;
            case 12:
                positionsArray = new int[]{0, 4, 10, -1, 10, 0, 0, 4, 10};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                time = 18f;
                sameColorNumber = 3;
                break;
            case 13:
                positionsArray = new int[]{0,0,8,8,5,-1,8,5,5,0,5,8};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                time = 25f;
                sameColorNumber = 4;
                break;
            case 14:
                positionsArray = new int[]{5,5,2,-1,2,5,2,8,8};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                steps = 11;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{7};
                break;
            case 15:
                positionsArray = new int[]{10,10,-1,5,12,5,12,12,10};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 33;
                time = 20f;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{1};
                break;
            case 16:
                positionsArray = new int[]{12,9,9,10,9,12,10,10,-1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 33;
                steps = 22;
                memoryVisibleSteps = 6;
                memoryInvisibleSteps = 10;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{7};
                break;
            case 17:
                positionsArray = new int[]{4,4,2,0,4,0,2,2,-1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 33;
                steps = 14;
                memoryVisibleSteps = 3;
                memoryInvisibleSteps = 4;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                break;
            case 18:
                positionsArray = new int[]{1,10,12,10,12,-1,12,1,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 33;
                steps = 18;
                memoryVisibleSteps = 3;
                memoryInvisibleSteps = 6;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{8};
                break;
            case 19:
                positionsArray = new int[]{7,7,10,10,10,6,6,6,7,6,10,-1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                steps = 18;
                sameColorNumber = 4;
                break;
            case 20:
                positionsArray = new int[]{2, 10, 2, 10, 0, 10, 2, 4, 0, 4, -1, 0};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                time = 22f;
                sameColorNumber = 3;
                break;
            case 21:
                positionsArray = new int[]{2,1,2,0,1,-1,0,1,2,1,2,0};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                time = 19f;
                sameColorNumber = 4;
                break;
            case 22:
                positionsArray = new int[]{7,9,7,0,-1,9,9,7,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 33;
                steps = 19;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 10;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{5};
                break;
            case 23:
                positionsArray = new int[]{4,3,12,3,12,-1,12,4,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 33;
                steps = 20;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 10;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{7};
                break;
            case 24:
                positionsArray = new int[]{8,-1,8,3,0,3,0,3,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 33;
                steps = 16;
                memoryVisibleSteps = 6;
                memoryInvisibleSteps = 10;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                break;
            case 25:
                positionsArray = new int[]{1, 3, -1, 0, 3, 2, 0, 2, 2, 0, 1, 3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                steps = 27;
                sameColorNumber = 3;
                break;
            case 26:
                positionsArray = new int[]{4,3,9,3,9,-1,9,4,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 33;
                steps = 20;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{3};
                sameColorNumber = 3;
                break;
            case 27:
                positionsArray = new int[]{8,-1,8,3,0,3,0,3,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 33;
                steps = 15;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{3,8};
                sameColorNumber = 3;
                break;
            case 28:
                positionsArray = new int[]{12,4,10,-1,10,12,12,4,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 33;
                steps = 18;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{0};
                sameColorNumber = 3;
                break;
            case 29:
                positionsArray = new int[]{12,8,12,9,-1,8,8,12,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 33;
                steps = 25;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{7};
                sameColorNumber = 3;
                break;
            case 30:
                positionsArray = new int[]{12,1,8,8,12,-1,12,8,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 33;
                steps = 14;
                memoryVisibleSteps = 2;
                memoryInvisibleSteps = 12;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                break;
            case 31:
                positionsArray = new int[]{0,8,-1,3,3,1,0,8,0,1,8,1};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                time = 20f;
                sameColorNumber = 3;
                break;
            case 32:
                positionsArray = new int[]{12,-1,2,10,10,2,12,2,2,10,12,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                steps = 29;
                sameColorNumber = 4;
                break;
            case 33:
                positionsArray = new int[]{3,2,0,0,-1,2,3,2,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 33;
                steps = 13;
                memoryVisibleSteps = 2;
                memoryInvisibleSteps = 8;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                break;
            case 34:
                positionsArray = new int[]{7,7,10,7,10,6,6,6,7,6,10,-1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 34;
                steps = 27;
                memoryVisibleSteps = 4;
                memoryInvisibleSteps = 5;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                break;
            case 35:
                positionsArray = new int[]{5,10,12,10,12,-1,12,5,5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 33;
                steps = 19;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{2};
                sameColorNumber = 3;
                break;
            case 36:
                positionsArray = new int[]{12,1,8,8,12,-1,12,8,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 33;
                steps = 22;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{4};
                sameColorNumber = 3;
                break;
            case 37:
                positionsArray = new int[]{4,-1,4,1,4,3,3,4,9,1,9,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 34;
                steps = 35;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{11};
                sameColorNumber = 3;
                staticPositionsArray = new int[]{5};
                break;
            case 38:
                positionsArray = new int[]{4,-1,4,3,0,3,1,1,4,1,0,0};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                time = 50f;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{6};
                break;
            case 39:
                positionsArray = new int[]{1,4,1,9,4,9,1,3,3,-1,4,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                steps = 19;
                sameColorNumber = 3;
                break;
            case 40:
                positionsArray = new int[]{12,4,9,12,9,3,3,4,4,-1,9,12};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 34;
                steps = 34;
                memoryVisibleSteps = 6;
                memoryInvisibleSteps = 6;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{11};
                break;
            case 41:
                positionsArray = new int[]{10,2,9,10,4,9,-1,4,2,4,10,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 34;
                steps = 35;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 5;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{3,8};
                break;
            case 42:
                positionsArray = new int[]{4,1,2,4,2,10,10,1,1,-1,2,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 34;
                steps = 40;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{3};
                sameColorNumber = 3;
                staticPositionsArray = new int[]{11};
                break;
            case 43:
                positionsArray = new int[]{10,0,0,-1,10,8,10,8,8,0,10,8};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                steps = 23;
                sameColorNumber = 4;
                break;
            case 44:
                positionsArray = new int[]{12,2,3,12,9,3,-1,9,2,9,12,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 34;
                steps = 31;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{0,11};
                sameColorNumber = 3;
                staticPositionsArray = new int[]{3};
                break;
            case 45:
                positionsArray = new int[]{9,9,1,4,1,1,3,9,3,4,-1,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                steps = 34;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{5};
                break;
            case 46:
                positionsArray = new int[]{4,1,6,6,8,6,-1,8,1,8,4,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 34;
                steps = 49;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{3};
                sameColorNumber = 3;
                staticPositionsArray = new int[]{9};
                break;
            case 47:
                positionsArray = new int[]{7,4,2,2,12,2,-1,12,4,12,7,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 34;
                steps = 25;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 5;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{3};
                break;
            case 48:
                positionsArray = new int[]{0,4,7,-1,7,9,4,7,9,0,9,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 34;
                steps = 32;
                memoryVisibleSteps = 4;
                memoryInvisibleSteps = 4;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{9};
                break;
            case 49:
                positionsArray = new int[]{7,8,9,-1,9,10,8,9,10,7,10,7};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 34;
                steps = 49;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{5};
                sameColorNumber = 3;
                staticPositionsArray = new int[]{10};
                break;
            case 50:
                positionsArray = new int[]{2,9,-1,10,10,12,2,9,9,2,12,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 34;
                steps = 38;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{11};
                sameColorNumber = 3;
                break;
            case 51:
                positionsArray = new int[]{0,7,9,10,9,-1,7,10,0,10,9,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 34;
                steps = 28;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 5;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{1};
                break;
            case 52:
                positionsArray = new int[]{0,3,6,0,6,3,6,3,0,3,6,-1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                steps = 49;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{6};
                break;
            case 53:
                positionsArray = new int[]{4,-1,4,1,4,3,3,4,9,1,9,9};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                time = 26f;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{5};
                break;
            case 54:
                positionsArray = new int[]{9,9,1,9,4,3,3,1,1,4,-1,4};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                time = 35f;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{6};
                break;
            case 55:
                positionsArray = new int[]{0, 3, 9, -1, 3, 1, 0, 9, 1, 1, 0, 9, 3, 1, 3, 9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 25;
                sameColorNumber = 4;
                break;
            case 56:
                positionsArray = new int[]{10, 7, 4, 10, -1, 10, 4, 7, 9, 2, 7, 2, 9, 2, 9, 4};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                time = 38f;
                sameColorNumber = 3;
                break;
            case 57:
                positionsArray = new int[]{7,1,-1,6,7,1,6,6,7};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 33;
                steps = 12;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{1};
                break;
            case 58:
                positionsArray = new int[]{2,3,4,3,4,-1,4,2,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 33;
                steps = 15;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{3};
                break;
            case 59:
                positionsArray = new int[]{1,6,1,9,-1,6,6,1,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 33;
                steps = 17;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{2};
                break;
            case 60:
                positionsArray = new int[]{8,12,5,12,5,-1,5,8,8};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 33;
                steps = 20;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{7};
                break;
            case 61:
                positionsArray = new int[]{0,-1,4,4,10,10,10,4,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 33;
                steps = 25;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{0};
                break;
            case 62:
                positionsArray = new int[]{13,5,3,13,12,-1,5,12,5,3,12,13};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 34;
                steps = 30;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{3};
                sameColorNumber = 3;
                break;
            case 63:
                positionsArray = new int[]{5,10,5,5,10,11,5,11,11,-1,10,11};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 34;
                steps = 23;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 5;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{6};
                break;
            case 64:
                positionsArray = new int[]{3,4,2,3,13,-1,4,13,4,2,13,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 34;
                steps = 30;
                memoryVisibleSteps = 3;
                memoryInvisibleSteps = 8;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                break;
            case 65:
                positionsArray = new int[]{7, 2, 2, 4, 2, 10, 4, -1, 7, 4, 7, 10, 9, 9, 9, 10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 18;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{11};
                break;
            case 66:
                positionsArray = new int[]{0, 2, 0, 2, 4, 0, 2, 1, 0, 1, 4, 2, 4, 1, -1, 1};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                time = 24f;
                sameColorNumber = 4;
                break;
            case 67:
                positionsArray = new int[]{1,3,-1,4,4,12,1,3,3,1,12,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 34;
                steps = 30;
                memoryVisibleSteps = 3;
                memoryInvisibleSteps = 9;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                break;
            case 68:
                positionsArray = new int[]{0,3,4,0,9,-1,3,9,3,4,9,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 34;
                steps = 29;
                memoryVisibleSteps = 4;
                memoryInvisibleSteps = 6;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                break;
            case 69:
                positionsArray = new int[]{5,8,10,12,10,12,-1,8,8,5,10,5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 34;
                steps = 33;
                memoryVisibleSteps = 4;
                memoryInvisibleSteps = 9;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                break;
            case 70:
                positionsArray = new int[]{10, 10, 2, 9, 9, 2, 4, 7, 10, 4, 2, 9, 4, 7, -1, 7};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 28;
                sameColorNumber = 3;
                break;
            case 71:
                positionsArray = new int[]{9,1,3,9,4,-1,1,4,1,3,4,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 34;
                steps = 24;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{3};
                sameColorNumber = 3;
                break;
            case 72:
                positionsArray = new int[]{1,4,11,10,11,10,-1,4,4,1,11,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 34;
                steps = 49;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{2};
                sameColorNumber = 3;
                break;
            case 73:
                positionsArray = new int[]{4,-1,4,2,0,4,10,4,10,10,2,0,0,10,2,2};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                time = 25f;
                sameColorNumber = 4;
                break;
            case 74:
                positionsArray = new int[]{2,6,8,-1,2,6,6,8,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 33;
                steps = 19;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{6};
                break;
            case 75:
                positionsArray = new int[]{2,12,2,2,12,9,2,9,9,-1,12,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 34;
                steps = 14;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{1};
                break;
            case 76:
                positionsArray = new int[]{0, -1, 0, 8, 3, 8, 0, 5, 0, 3, 5, 8, 3, 5, 3, 5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 25;
                sameColorNumber = 4;
                break;
            case 77:
                positionsArray = new int[]{1,1,1,6,6,8,8,8,-1,1,8,6};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 34;
                steps = 19;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{2};
                break;
            case 78:
                positionsArray = new int[]{3,4,7,8,-1,8,7,4,4,3,3,8};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 34;
                steps = 28;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{10};
                break;
            case 79:
                positionsArray = new int[]{-1,10,6,4,6,4,6,4,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 33;
                steps = 18;
                movableSteps = 2;
                movablePositionsArray = new int[]{0,8};
                sameColorNumber = 3;
                break;
            case 80:
                positionsArray = new int[]{12,-1,13,13,3,3,3,13,12};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 33;
                steps = 24;
                movableSteps = 3;
                movablePositionsArray = new int[]{0,2};
                sameColorNumber = 3;
                staticPositionsArray = new int[]{0};
                break;
            case 81:
                positionsArray = new int[]{1,8,3,-1,1,8,8,3,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 33;
                steps = 21;
                movableSteps = 3;
                movablePositionsArray = new int[]{2,6};
                sameColorNumber = 3;
                staticPositionsArray = new int[]{0};
                break;
            case 82:
                positionsArray = new int[]{6,10,9,9,-1,6,10,10,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 33;
                steps = 17;
                movableSteps = 3;
                movablePositionsArray = new int[]{5,7};
                sameColorNumber = 3;
                break;
            case 83:
                positionsArray = new int[]{2,0,0,-1,2,7,7,0,2,10,10,2,10,10,7,7};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                time = 21f;
                sameColorNumber = 4;
                break;
            case 84:
                positionsArray = new int[]{3,4,4,-1,3,4,6,4,0,6,3,6,0,3,0,6};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 21;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{14};
                break;
            case 85:
                positionsArray = new int[]{0,1,2,3,3,2,-1,1,0,1,3,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 34;
                steps = 34;
                memoryVisibleSteps = 4;
                memoryInvisibleSteps = 8;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                break;
            case 86:
                positionsArray = new int[]{7,2,2,4,2,10,4,-1,7,4,7,10,9,9,9,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 18;
                memoryVisibleSteps = 3;
                memoryInvisibleSteps = 3;
                memoryInitialVisiblity = true;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{11};
                break;
            case 87:
                positionsArray = new int[]{13,12,-1,13,1,3,1,3,3,12,13,12,1,3,13,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 118;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{0,14};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{10};
                break;
            case 88:
                positionsArray = new int[]{10,4,9,4,9,-1,9,10,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 33;
                steps = 19;
                movableSteps = 3;
                movablePositionsArray = new int[]{4,1};
                sameColorNumber = 3;
                break;
            case 89:
                positionsArray = new int[]{9,9,5,9,3,-1,5,3,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 33;
                steps = 9;
                movableSteps = 3;
                movablePositionsArray = new int[]{6,8};
                sameColorNumber = 3;
                break;
            case 90:
                positionsArray = new int[]{3,8,8,12,-1,8,12,12,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 33;
                steps = 10;
                movableSteps = 3;
                movablePositionsArray = new int[]{3,5};
                sameColorNumber = 3;
                staticPositionsArray = new int[]{0};
                break;
            case 91:
                positionsArray = new int[]{7,2,5,9,9,5,-1,2,7,2,9,7};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 34;
                steps = 32;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{3};
                sameColorNumber = 3;
                break;
            case 92:
                positionsArray = new int[]{0,4,4,3,13,-1,3,0,4,3,13,13};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 34;
                steps = 28;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{2};
                break;
            case 93:
                positionsArray = new int[]{2,1,1,3,1,3,-1,0,3,2,0,2,2,0,1,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 43;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{9};
                sameColorNumber = 4;
                break;
            case 94:
                positionsArray = new int[]{-1,0,4,6,0,4,4,6,3,6,3,4,6,0,3,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 29;
                sameColorNumber = 4;
                break;
            case 95:
                positionsArray = new int[]{13,2,13,10,-1,2,2,13,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 33;
                steps = 25;
                movableSteps = 3;
                movablePositionsArray = new int[]{0,2,7};
                sameColorNumber = 3;
                break;
            case 96:
                positionsArray = new int[]{2,10,2,0,10,2,-1,10,10,0,2,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 34;
                steps = 20;
                movableSteps = 5;
                movablePositionsArray = new int[]{0,11};
                sameColorNumber = 4;
                break;
            case 97:
                positionsArray = new int[]{2,3,4,4,4,9,-1,2,3,9,2,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 34;
                steps = 31;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{3};
                break;
            case 98:
                positionsArray = new int[]{3,1,4,8,8,8,-1,3,4,3,1,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 34;
                steps = 35;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{0};
                break;
            case 99:
                positionsArray = new int[]{0,4,3,6,4,8,-1,6,6,0,8,4,3,8,0,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 70;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{3};
                sameColorNumber = 3;
                break;
            case 100:
                positionsArray = new int[]{13,9,8,7,9,1,-1,7,7,13,1,9,8,1,13,8};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 87;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{0};
                sameColorNumber = 3;
                break;
            case 101:
                positionsArray = new int[]{8,8,8,3,3,12,12,12,-1,8,12,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 34;
                steps = 15;
                movableSteps = 3;
                movablePositionsArray = new int[]{3,8};
                sameColorNumber = 3;
                staticPositionsArray = new int[]{4};
                break;
            case 102:
                positionsArray = new int[]{0,13,10,13,13,0,6,10,0,6,-1,10,6,13,10,6};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                time = 28f;
                sameColorNumber = 5;
                break;
            case 103:
                positionsArray = new int[]{6,8,0,3,6,8,6,-1,8,0,0,3,6,8,3,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 23;
                sameColorNumber = 4;
                break;
            case 104:
                positionsArray = new int[]{3,2,3,13,13,9,13,2,2,9,-1,9,13,3,2,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 48;
                memoryVisibleSteps = 3;
                memoryInvisibleSteps = 3;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                break;
            case 105:
                positionsArray = new int[]{4,6,4,8,8,3,8,6,6,3,-1,3,8,4,6,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 73;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{3};
                sameColorNumber = 4;
                break;
            case 106:
                positionsArray = new int[]{2,0,10,0,2,13,2,10,10,4,4,11,13,11,11,-1};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                time = 25f;
                sameColorNumber = 3;
                break;
            case 107:
                positionsArray = new int[]{1,-1,1,12,4,1,4,12,3,2,3,3,13,2,12,13};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 29;
                sameColorNumber = 3;
                break;
            case 108:
                positionsArray = new int[]{3,2,13,9,-1,9,13,2,2,3,3,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 34;
                steps = 29;
                movableSteps = 5;
                movablePositionsArray = new int[]{2,9};
                sameColorNumber = 3;
                staticPositionsArray = new int[]{4};
                break;
            case 109:
                positionsArray = new int[]{6,5,0,5,0,6,5,6,3,0,6,3,0,-1,5,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 32;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{3};
                break;
            case 110:
                positionsArray = new int[]{6,8,8,12,3,-1,12,6,8,12,3,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 34;
                steps = 26;
                movableSteps = 5;
                movablePositionsArray = new int[]{3,8};
                sameColorNumber = 3;
                staticPositionsArray = new int[]{0};
                break;
            case 111:
                positionsArray = new int[]{7,9,8,8,8,6,-1,7,9,6,7,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 34;
                steps = 30;
                movableSteps = 4;
                movablePositionsArray = new int[]{4,7};
                sameColorNumber = 3;
                staticPositionsArray = new int[]{10};
                break;
            case 112:
                positionsArray = new int[]{5,6,13,12,12,12,-1,5,13,5,6,13};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 34;
                steps = 42;
                movableSteps = 3;
                movablePositionsArray = new int[]{5,7};
                sameColorNumber = 3;
                break;
            case 113:
                positionsArray = new int[]{1,1,1,10,12,10,10,2,12,4,1,-1,1,10,12,2,2,2,4,4};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 31f;
                sameColorNumber = 5;
                staticPositionsArray = new int[]{0,16};
                break;
            case 114:
                positionsArray = new int[]{10, 9, 2, 2, 9, 2, -1, 4, 10, 9, 4, 9, 10, 4, 10, 4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 23;
                sameColorNumber = 4;
                break;
            case 115:
                positionsArray = new int[]{3,2,12,3,9,12,-1,9,2,9,12,2,3,2,9,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 53;
                colorChangeSteps = 5;
                colorChangeTokensArray = new int[]{1};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{14,15};
                break;
            case 116:
                positionsArray = new int[]{10,12,12,4,-1,4,10,10,12,12,4,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 34;
                steps = 22;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{2};
                break;
            case 117:
                positionsArray = new int[]{1,1,1,8,-1,8,7,7,7,8,8,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 34;
                steps = 24;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{8};
                break;
            case 118:
                positionsArray = new int[]{1,3,3,4,9,9,-1,1,4,3,1,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 34;
                steps = 39;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{5};
                break;
            case 119:
                positionsArray = new int[]{3,8,8,12,-1,12,3,3,8,8,12,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 34;
                steps = 27;
                movableSteps = 4;
                movablePositionsArray = new int[]{0,11};
                sameColorNumber = 4;
                break;
            case 120:
                positionsArray = new int[]{7, 8, -1, 4, 1, 9, 8, 9, 8, 9, 4, 4, 1, 7, 1, 7};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                time = 37f;
                sameColorNumber = 3;
                break;
            case 121:
                positionsArray = new int[]{8,12,3,12,3,6,12,6,8,-1,6,8,6,3,3,8};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 36;
                sameColorNumber = 4;
                break;
            case 122:
                positionsArray = new int[]{6,4,4,0,4,-1,3,4,3,6,0,6,0,3,6,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 35;
                sameColorNumber = 4;
                break;
            case 123:
                positionsArray = new int[]{4,4,4,7,-1,7,10,10,10,7,7,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 34;
                steps = 21;
                movableSteps = 3;
                movablePositionsArray = new int[]{2,10};
                sameColorNumber = 4;
                break;
            case 124:
                positionsArray = new int[]{13,12,12,5,9,9,-1,13,5,12,13,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 34;
                steps = 45;
                movableSteps = 4;
                movablePositionsArray = new int[]{3,9};
                sameColorNumber = 3;
                break;
            case 125:
                positionsArray = new int[]{12,10,7,8,7,8,-1,12,10,10,12,10,8,12,7,8};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 79;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{11};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{14};
                break;
            case 126:
                positionsArray = new int[]{0,6,8,8,0,8,10,10,6,10,-1,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 34;
                steps = 16;
                sameColorNumber = 3;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{7};
                break;
            case 127:
                positionsArray = new int[]{4,1,2,2,4,2,10,10,1,10,-1,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 34;
                steps = 28;
                movableSteps = 2;
                movablePositionsArray = new int[]{4,8};
                sameColorNumber = 3;
                break;
            case 128:
                positionsArray = new int[]{2,5,5,5,7,7,9,9,9,10,-1,10,2,2,5,7,7,9,10,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 45;
                steps = 77;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{3};
                sameColorNumber = 4;
                break;
            case 129:
                positionsArray = new int[]{3,13,4,1,12,-1,3,2,12,13,3,2,1,12,4,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 38;
                movableSteps = 4;
                movablePositionsArray = new int[]{0,15};
                sameColorNumber = 3;
                break;
            case 130:
                positionsArray = new int[]{6,5,-1,6,9,7,9,7,7,5,6,5,9,7,6,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 65;
                movableSteps = 4;
                movablePositionsArray = new int[]{4,10};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{10};
                break;
            case 131:
                positionsArray = new int[]{10,9,2,2,9,2,-1,4,10,9,4,9,10,4,10,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 44;
                steps = 28;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{15};
                break;
            case 132:
                positionsArray = new int[]{-1,10,2,13,0,13,2,0,2,10,10,2,13,0,13,10};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                time = 35f;
                sameColorNumber = 4;
                break;
            case 133:
                positionsArray = new int[]{12,12,13,2,-1,12,13,10,12,10,13,13,10,2,10,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 29;
                sameColorNumber = 4;
                break;
            case 134:
                positionsArray = new int[]{13,2,-1,13,13,3,0,13,3,2,0,2,3,3,2,0};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                time = 28f;
                sameColorNumber = 4;
                break;
            case 135:
                positionsArray = new int[]{9,-1,9,3,4,3,12,4,12,4,4,12,3,9,12,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 80;
                movableSteps = 3;
                movablePositionsArray = new int[]{0,15};
                sameColorNumber = 4;
                break;
            case 136:
                positionsArray = new int[]{1,13,13,6,12,12,6,-1,1,6,13,6,1,12,1,13};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 24;
                sameColorNumber = 4;
                break;
            case 137:
                positionsArray = new int[]{6,10,5,13,10,8,-1,13,13,6,8,10,5,8,6,5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 44;
                steps = 67;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{12};
                break;
            case 138:
                positionsArray = new int[]{10,9,2,-1,10,10,5,9,5,10,2,2,5,2,9,5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 43;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{2};
                sameColorNumber = 4;
                break;
            case 139:
                positionsArray = new int[]{13,6,6,3,13,13,-1,9,3,9,3,6,13,3,6,9};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                time = 26f;
                sameColorNumber = 4;
                break;
            case 140:
                positionsArray = new int[]{7,13,6,0,6,0,1,0,7,4,1,-1,13,1,4,7};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 41;
                sameColorNumber = 3;
                break;
            case 141:
                positionsArray = new int[]{5,5,13,12,12,13,9,13,-1,9,12,9,9,5,13,5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 52;
                movableSteps = 3;
                movablePositionsArray = new int[]{0,3};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{2};
                break;
            case 142:
                positionsArray = new int[]{4,7,9,10,9,10,-1,4,7,7,4,7,10,4,9,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 60;
                memoryVisibleSteps = 8;
                memoryInvisibleSteps = 10;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{14};
                break;
            case 143:
                positionsArray = new int[]{7,4,13,2,4,1,2,13,7,7,-1,0,0,1,0,1};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                time = 34f;
                sameColorNumber = 3;
                break;
            case 144:
                positionsArray = new int[]{6,9,3,6,3,9,1,1,9,1,1,3,-1,3,9,3,6,9,1,6};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 59f;
                sameColorNumber = 5;
                break;
            case 145:
                positionsArray = new int[]{3,1,6,9,6,3,9,1,3,6,1,9,9,3,6,-1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 52;
                movableSteps = 4;
                movablePositionsArray = new int[]{3,12};
                sameColorNumber = 4;
                break;
            case 146:
                positionsArray = new int[]{4,5,-1,6,5,6,9,4,5,9,5,6,9,4,9,6};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 74;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{10};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{7};
                break;
            case 147:
                positionsArray = new int[]{10,9,6,8,9,12,-1,8,8,10,12,9,6,12,10,6};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 44;
                steps = 64;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{2};
                break;
            case 148:
                positionsArray = new int[]{-1,3,8,1,8,3,4,4,8,1,3,3,12,12,1,12,4,1,4,12};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                steps = 35;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{5,14,19};
                break;
            case 149:
                positionsArray = new int[]{4,3,2,3,4,2,4,4,1,1,2,9,3,3,1,9,2,9,1,-1};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 41f;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{0,5,14};
                break;
            case 150:
                positionsArray = new int[]{13,10,10,13,2,0,0,-1,0,13,10,2,0,2,2,13};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 58;
                movableSteps = 3;
                movablePositionsArray = new int[]{12,15};
                sameColorNumber = 4;
                break;
            case 151:
                positionsArray = new int[]{6,13,6,10,10,10,0,-1,13,13,10,6,13,0,6,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 32;
                sameColorNumber = 4;
                break;
            case 152:
                positionsArray = new int[]{8,5,6,5,6,0,5,-1,8,8,5,6,0,6,8,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 41;
                sameColorNumber = 4;
                break;
            case 153:
                positionsArray = new int[]{8,0,3,-1,8,8,13,0,13,8,3,3,13,3,0,13};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 34;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 5;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                break;
            case 154:
                positionsArray = new int[]{4,6,6,9,9,10,4,9,10,-1,10,9,6,4,6,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 86;
                movableSteps = 5;
                movablePositionsArray = new int[]{0,3,15};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{7};
                break;
            case 155:
                positionsArray = new int[]{12,11,10,4,10,-1,11,4,4,11,11,12,4,10,10,12};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 50;
                colorChangeSteps = 2;
                colorChangeTokensArray = new int[]{6};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{6};
                break;
            case 156:
                positionsArray = new int[]{13,0,1,6,8,13,6,-1,1,6,0,1,0,13,8,8};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 44;
                steps = 45;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{13};
                break;
            case 157:
                positionsArray = new int[]{3,3,5,13,5,-1,9,5,9,13,9,3,3,9,5,13};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 49;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{14};
                sameColorNumber = 4;
                break;
            case 158:
                positionsArray = new int[]{13,8,10,13,7,0,10,8,7,13,10,8,0,13,-1,8,7,0,10,7};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 55f;
                sameColorNumber = 4;
                break;
            case 159:
                positionsArray = new int[]{1,1,13,6,13,6,-1,9,9,13,6,13,9,1,6,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 48;
                sameColorNumber = 4;
                break;
            case 160:
                positionsArray = new int[]{5,12,-1,9,12,9,3,5,12,3,12,9,3,5,3,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 67;
                memoryVisibleSteps = 7;
                memoryInvisibleSteps = 7;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{7};
                break;
            case 161:
                positionsArray = new int[]{2,3,4,9,9,-1,9,3,4,2,2,4,9,4,3,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 57;
                movableSteps = 3;
                movablePositionsArray = new int[]{2,13};
                sameColorNumber = 4;
                break;
            case 162:
                positionsArray = new int[]{10,9,9,5,4,10,5,4,-1,9,9,10,5,4,10,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 63;
                colorChangeSteps = 4;
                colorChangeTokensArray = new int[]{2};
                sameColorNumber = 4;
                break;
            case 163:
                positionsArray = new int[]{2,9,10,5,5,-1,12,9,10,2,10,2,9,12,12,5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 44;
                steps = 48;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{8};
                break;
            case 164:
                positionsArray = new int[]{1,4,-1,6,8,6,11,1,11,1,6,8,4,8,11,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 44;
                steps = 57;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{9};
                break;
            case 165:
                positionsArray = new int[]{7,4,-1,8,10,10,4,8,7,4,10,7,8,10,7,8};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 40;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{4,15};
                sameColorNumber = 4;
                break;
            case 166:
                positionsArray = new int[]{10,4,6,8,6,-1,4,8,8,4,4,10,8,6,6,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 61;
                memoryVisibleSteps = 7;
                memoryInvisibleSteps = 7;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{6};
                break;
            case 167:
                positionsArray = new int[]{8,12,10,8,10,12,5,5,12,5,5,10,-1,10,12,10,8,12,5,8};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 80f;
                sameColorNumber = 5;
                staticPositionsArray = new int[]{8};
                break;
            case 168:
                positionsArray = new int[]{13,13,3,3,1,6,1,3,1,6,3,6,13,12,-1,12,13,12,1,6};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 45f;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{8};
                break;
            case 169:
                positionsArray = new int[]{2,1,4,2,10,1,4,2,-1,9,9,1,10,10,10,1,4,9,4,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                steps = 45;
                sameColorNumber = 4;
                break;
            case 170:
                positionsArray = new int[]{2,3,2,3,1,4,4,4,1,2,2,-1,9,3,1,4,9,3,1,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                steps = 48;
                sameColorNumber = 4;
                break;
            case 171:
                positionsArray = new int[]{10,11,8,12,8,13,12,8,8,-1,11,12,11,13,10,11,13,10,10,13};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 65f;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{8};
                break;
            case 172:
                positionsArray = new int[]{9,9,7,13,7,-1,2,7,2,13,2,9,9,2,7,13};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 52;
                memoryVisibleSteps = 7;
                memoryInvisibleSteps = 7;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                break;
            case 173:
                positionsArray = new int[]{1,4,1,10,-1,10,7,7,7,4,10,1,4,4,10,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 50;
                movableSteps = 4;
                movablePositionsArray = new int[]{1,14};
                sameColorNumber = 4;
                break;
            case 174:
                positionsArray = new int[]{6,-1,6,10,5,10,13,5,13,5,5,13,10,6,13,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 44;
                steps = 55;
                colorChangeSteps = 5;
                colorChangeTokensArray = new int[]{14};
                sameColorNumber = 4;
                break;
            case 175:
                positionsArray = new int[]{12,13,12,10,10,8,-1,6,8,6,6,8,13,12,13,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 44;
                steps = 50;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{5};
                break;
            case 176:
                positionsArray = new int[]{9,9,9,8,1,8,1,6,0,1,0,1,-1,6,8,10,0,10,10,6};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                steps = 38;
                sameColorNumber = 4;
                break;
            case 177:
                positionsArray = new int[]{0,9,7,0,7,0,9,7,9,2,-1,2,2,9,7,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 35;
                movableSteps = 4;
                movablePositionsArray = new int[]{6,9};
                sameColorNumber = 4;
                break;
            case 178:
                positionsArray = new int[]{2,1,1,3,1,3,-1,0,3,2,0,2,2,0,1,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 46;
                sameColorNumber = 4;
                break;
            case 179:
                positionsArray = new int[]{2,13,12,1,3,3,13,-1,2,2,3,12,1,2,1,12,13,3,1,13};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 60f;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{16};
                break;
            case 180:
                positionsArray = new int[]{12,2,2,13,13,1,12,4,13,2,-1,1,3,2,4,12,3,3,13,4,1,4,1,4,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                steps = 65;
                sameColorNumber = 5;
                break;
            case 181:
                positionsArray = new int[]{5,12,13,12,2,13,-1,12,13,3,5,5,2,3,2,13,2,3,5,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                steps = 57;
                sameColorNumber = 4;
                break;
            case 182:
                positionsArray = new int[]{8,10,10,9,12,8,9,12,-1,10,10,8,9,12,8,12};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 40;
                memoryVisibleSteps = 4;
                memoryInvisibleSteps = 4;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                break;
            case 183:
                positionsArray = new int[]{13,-1,12,13,11,12,5,12,13,5,13,5,11,11,12,5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 53;
                movableSteps = 3;
                movablePositionsArray = new int[]{5,10};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{7};
                break;
            case 184:
                positionsArray = new int[]{1,11,4,8,1,11,13,-1,13,11,8,13,1,8,4,4,11,8,4,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 45;
                steps = 108;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{3,5};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{12};
                break;
            case 185:
                positionsArray = new int[]{4,3,4,3,5,6,6,-1,5,4,4,9,6,3,5,6,9,3,9,5};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 85f;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{5,14};
                break;
            case 186:
                positionsArray = new int[]{2,9,7,10,2,4,10,2,1,7,4,2,4,7,1,9,1,10,-1,9,4,10,9,7,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                steps = 61;
                sameColorNumber = 4;
                break;
            case 187:
                positionsArray = new int[]{6,5,-1,9,10,10,5,9,6,5,10,6,9,10,6,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 41;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 4;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                break;
            case 188:
                positionsArray = new int[]{6,0,6,6,9,8,9,8,8,-1,8,0,0,9,0,6};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 81;
                movableSteps = 2;
                movablePositionsArray = new int[]{0,11};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{13};
                break;
            case 189:
                positionsArray = new int[]{2,10,-1,9,2,6,12,6,12,6,10,9,12,9,10,2,6,9,10,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 45;
                steps = 84;
                colorChangeSteps = 4;
                colorChangeTokensArray = new int[]{18};
                sameColorNumber = 4;
                break;
            case 190:
                positionsArray = new int[]{0,1,-1,2,3,4,1,3,2,1,3,2,4,0,0,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 44;
                steps = 58;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{10};
                break;
            case 191:
                positionsArray = new int[]{3,13,4,1,12,-1,3,2,12,13,3,2,1,12,4,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 54;
                sameColorNumber = 3;
                break;
            case 192:
                positionsArray = new int[]{2,10,2,0,10,2,-1,10,10,0,2,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 34;
                steps = 15;
                sameColorNumber = 4;
                break;
            case 193:
                positionsArray = new int[]{6,1,3,13,1,13,3,6,13,6,1,3,1,-1,4,6,9,9,4,4,4,13,3,4,9};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                time = 95f;
                sameColorNumber = 5;
                break;
            case 194:
                positionsArray = new int[]{2,9,10,5,5,-1,12,9,10,2,10,2,9,12,12,5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 53;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 5;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{8};
                break;
            case 195:
                positionsArray = new int[]{3,5,8,5,8,3,12,5,3,12,-1,8,12,3,8,5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 50;
                movableSteps = 4;
                movablePositionsArray = new int[]{3,12,15};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{13,14};
                break;
            case 196:
                positionsArray = new int[]{0,7,3,5,8,3,8,0,-1,7,8,5,5,8,3,7,0,3,7,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 45;
                steps = 97;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{16};
                sameColorNumber = 4;
                break;
            case 197:
                positionsArray = new int[]{1,4,10,11,6,10,6,11,4,1,11,4,1,-1,10,1,10,6,4,6};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 45;
                steps = 75;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{2};
                sameColorNumber = 4;
                break;
            case 198:
                positionsArray = new int[]{2,4,6,7,9,6,-1,2,6,9,7,4,7,4,9,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 44;
                steps = 69;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{0};
                break;
            case 199:
                positionsArray = new int[]{2,12,12,13,5,2,-1,5,13,5,13,2,5,13,2,12};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 50;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{14};
                break;
            case 200:
                positionsArray = new int[]{10,2,11,2,9,2,10,13,9,10,13,11,13,11,9,10,13,2,-1,11};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                steps = 57;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{16};
                break;
            case 201:
                positionsArray = new int[]{1,4,-1,6,8,6,11,1,11,1,6,8,4,8,11,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 82;
                memoryVisibleSteps = 6;
                memoryInvisibleSteps = 6;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{9};
                break;
            case 202:
                positionsArray = new int[]{10,-1,10,10,10,4,5,5,4,12,12,4,12,4,5,12};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 44;
                steps = 43;
                movableSteps = 4;
                movablePositionsArray = new int[]{7,8};
                sameColorNumber = 4;
                break;
            case 203:
                positionsArray = new int[]{2,2,2,-1,8,8,9,9,10,10,10,12,12,9,12,12,10,8,8,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 45;
                steps = 77;
                colorChangeSteps = 5;
                colorChangeTokensArray = new int[]{4};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{8};
                break;
            case 204:
                positionsArray = new int[]{2,3,6,7,8,8,8,-1,2,2,3,3,6,6,7,8,7,6,3,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 45;
                steps = 77;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{4};
                break;
            case 205:
                positionsArray = new int[]{3,1,9,3,4,-1,9,3,4,10,4,10,9,1,3,9,1,10,4,1};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 140f;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{12};
                break;
            case 206:
                positionsArray = new int[]{4,2,4,4,1,9,11,1,9,2,2,9,4,1,10,10,-1,10,11,9,11,2,11,10,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                steps = 60;
                sameColorNumber = 4;
                break;
            case 207:
                positionsArray = new int[]{9,11,13,10,9,6,5,5,13,5,11,6,10,11,9,11,5,5,13,10,6,13,6,-1,10};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                time = 114f;
                sameColorNumber = 5;
                break;
            case 208:
                positionsArray = new int[]{9,4,11,1,11,9,11,9,8,4,8,4,6,0,1,0,1,-1,6,8,10,0,10,10,6};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                steps = 72;
                sameColorNumber = 3;
                break;
            case 209:
                positionsArray = new int[]{0,1,-1,2,3,4,1,3,2,1,3,2,4,0,0,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 67;
                memoryVisibleSteps = 4;
                memoryInvisibleSteps = 4;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{10};
                break;
            case 210:
                positionsArray = new int[]{12,4,2,12,1,2,2,13,3,1,4,13,4,2,12,3,3,4,-1,3,4,13,3,1,13};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                time = 140f;
                sameColorNumber = 5;
                staticPositionsArray = new int[]{8};
                break;
            case 211:
                positionsArray = new int[]{10,2,11,2,9,2,10,13,9,10,13,11,13,11,9,10,13,2,-1,11};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 45;
                steps = 57;
                movableSteps = 3;
                movablePositionsArray = new int[]{3,16};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{16};
                break;
            case 212:
                positionsArray = new int[]{13,4,2,1,4,2,12,12,1,-1,3,13,3,3,12,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 40;
                sameColorNumber = 3;
                break;
            case 213:
                positionsArray = new int[]{2,8,9,10,12,12,12,-1,2,2,8,8,9,9,10,12,10,9,8,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 45;
                steps = 79;
                colorChangeSteps = 4;
                colorChangeTokensArray = new int[]{5};
                sameColorNumber = 4;
                break;
            case 214:
                positionsArray = new int[]{1,4,10,1,9,11,1,9,10,4,10,4,11,9,-1,4,11,9,1,11};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 116f;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{7};
                break;
            case 215:
                positionsArray = new int[]{10,11,10,1,11,1,4,1,10,2,9,1,4,11,-1,4,10,9,9,11,9,2,4,2,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                steps = 78;
                sameColorNumber = 4;
                break;
            case 216:
                positionsArray = new int[]{4,1,1,1,6,6,9,9,9,12,-1,12,4,4,1,6,6,9,12,12};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 45;
                steps = 74;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{5};
                break;
            case 217:
                positionsArray = new int[]{3, 5, 3, 9, -1, 4, 3, 2, 4, 5, 5, 9, 9, 2, 4, 2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 44;
                steps = 46;
                sameColorNumber = 3;
                staticPositionsArray = new int[]{6};
                break;
            case 218:
                positionsArray = new int[]{10,9,5,10,2,2,5,2,12,10,5,12,10,-1,5,2,9,9,5,12};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 180f;
                sameColorNumber = 5;
                staticPositionsArray = new int[]{7,14};
                break;
            case 219:
                positionsArray = new int[]{2,4,6,7,9,6,-1,2,6,9,7,4,7,4,9,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 59;
                memoryVisibleSteps = 4;
                memoryInvisibleSteps = 4;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{0};
                break;
            case 220:
                positionsArray = new int[]{1,3,8,9,8,3,1,13,9,13,-1,1,13,9,8,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 60;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 5;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                break;
            case 221:
                positionsArray = new int[]{0,8,5,7,0,8,10,-1,10,8,7,10,0,7,5,5,8,7,5,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 45;
                steps = 107;
                movableSteps = 4;
                movablePositionsArray = new int[]{0,19};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{12};
                break;
            case 222:
                positionsArray = new int[]{2,2,4,2,9,7,9,9,10,4,-1,7,4,1,9,2,10,1,4,1,7,1,10,7,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                steps = 91;
                sameColorNumber = 4;
                break;
            case 223:
                positionsArray = new int[]{4,6,7,8,7,-1,9,9,8,9,4,6,4,6,7,8};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 58;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 4;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                break;
            case 224:
                positionsArray = new int[]{1,3,6,1,8,6,-1,8,3,8,6,3,1,3,8,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 114;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 5;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{9,10};
                break;
            case 225:
                positionsArray = new int[]{1,10,7,2,7,9,7,10,-1,10,10,1,4,9,2,2,9,1,7,9,1,4,4,2,4};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                time = 120f;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{20};
                break;
            case 226:
                positionsArray = new int[]{12,9,6,6,8,8,3,-1,9,12,12,6,8,3,3,8,6,3,9,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 45;
                steps = 77;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{15};
                break;
            case 227:
                positionsArray = new int[]{1,4,6,6,7,7,3,-1,4,1,1,6,7,3,3,7,6,3,4,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 45;
                steps = 96;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{5};
                sameColorNumber = 4;
                break;
            case 228:
                positionsArray = new int[]{2,3,-1,4,2,8,9,8,9,8,3,4,9,4,3,2,8,4,3,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 45;
                steps = 107;
                movableSteps = 3;
                movablePositionsArray = new int[]{4,15};
                sameColorNumber = 4;
                break;
            case 229:
                positionsArray = new int[]{12,2,-1,12,8,10,8,10,10,2,12,2,8,10,12,8};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 50;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 5;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                break;
            case 230:
                positionsArray = new int[]{3,1,6,6,0,4,1,3,9,9,9,4,-1,0,9,4,3,6,0,1,4,3,1,6,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                steps = 94;
                sameColorNumber = 5;
                break;
            case 231:
                positionsArray = new int[]{13,-1,13,12,5,12,10,5,10,5,5,10,12,13,10,12};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 55;
                memoryVisibleSteps = 5;
                memoryInvisibleSteps = 10;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                break;
            case 232:
                positionsArray = new int[]{2,13,7,4,4,4,2,13,-1,10,7,13,13,7,4,2,2,7,10,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 45;
                steps = 58;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{19};
                break;
            case 233:
                positionsArray = new int[]{3,6,7,13,13,13,3,6,-1,9,7,6,6,7,13,3,3,7,9,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 45;
                steps = 91;
                colorChangeSteps = 3;
                colorChangeTokensArray = new int[]{15};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{3,12};
                break;
            case 234:
                positionsArray = new int[]{0,3,4,8,13,4,13,0,-1,3,13,8,8,13,4,3,0,4,3,0};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 45;
                steps = 99;
                movableSteps = 5;
                movablePositionsArray = new int[]{6,13};
                sameColorNumber = 4;
                break;
            case 236:
                positionsArray = new int[]{2,5,10,6,12,10,12,6,5,2,6,5,2,-1,10,2,10,12,5,12};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 45;
                steps = 99;
                movableSteps = 3;
                movablePositionsArray = new int[]{0,4};
                sameColorNumber = 4;
                break;
            case 235:
                positionsArray = new int[]{11,10,9,9,2,10,-1,5,11,10,5,11,2,10,2,2,5,10,9,5};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                time = 200f;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{5,12};
                break;
            case 237:
                positionsArray = new int[]{3,2,0,3,3,1,0,2,4,1,4,-1,4,1,0,1,2,2,4,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 45;
                steps = 88;
                sameColorNumber = 4;
                break;
            case 238:
                positionsArray = new int[]{9,2,1,-1,2,10,1,11,2,4,11,9,10,4,10,9,11,9,2,4,11,1,10,4,1};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                steps = 74;
                sameColorNumber = 4;
                break;
            case 239:
                positionsArray = new int[]{2,4,6,7,9,9,9,-1,2,2,4,4,6,6,7,9,7,6,4,2};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 45;
                steps = 74;
                movableSteps = 3;
                movablePositionsArray = new int[]{3,16};
                sameColorNumber = 4;
                break;
            case 240:
                positionsArray = new int[]{5,6,3,13,6,13,3,6,13,3,5,9,-1,9,6,4,4,5,9,3,4,5,13,4,4};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                steps = 83;
                sameColorNumber = 5;
                break;
            case 241:
                positionsArray = new int[]{5,5,6,8,8,6,10,6,-1,10,8,10,10,5,6,5};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 47;
                memoryVisibleSteps = 4;
                memoryInvisibleSteps = 12;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                break;
            case 242:
                positionsArray = new int[]{3,5,8,9,12,9,9,-1,3,5,5,8,8,3,12,12,9,8,5,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.BLOCKEDCHANGE);
                boardSize = 45;
                steps = 87;
                sameColorNumber = 4;
                blockedChangeArrayInitial = blockedChangeArray = new int[]{3};
                break;
            case 243:
                positionsArray = new int[]{3,5,4,9,12,9,9,-1,3,5,5,4,4,3,12,12,9,4,5,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.COLORCHANGE);
                boardSize = 45;
                steps = 147;
                colorChangeSteps = 5;
                colorChangeTokensArray = new int[]{10};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{2,17};
                break;
            case 244:
                positionsArray = new int[]{4,10,13,4,4,2,4,2,10,0,2,11,13,10,4,11,0,10,-1,11,13,11,0,2,13};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                steps = 98;
                sameColorNumber = 5;
                break;
            case 245:
                positionsArray = new int[]{12,13,12,10,10,8,-1,6,8,6,6,8,13,12,13,10};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MEMORY);
                boardSize = 44;
                steps = 86;
                memoryVisibleSteps = 9;
                memoryInvisibleSteps = 9;
                memoryInitialVisiblity = true;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{5};
                break;
            case 246:
                positionsArray = new int[]{1,2,1,7,2,7,10,7,1,9,4,7,10,2,-1,10,1,4,4,2,4,9,10,9,9};
                world.setGameType(GameWorld.GameType.TIME);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                time = 79f;
                sameColorNumber = 4;
                break;
            case 247:
                positionsArray = new int[]{3,4,9,12,13,12,12,-1,3,4,4,9,9,3,13,13,12,9,4,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 45;
                steps = 156;
                movableSteps = 5;
                movablePositionsArray = new int[]{5,14};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{2,17};
                break;
            case 248:
                positionsArray = new int[]{2,2,3,5,3,2,3,2,12,8,12,2,3,12,5,12,5,-1,3,8,5,12,5,8,8};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                steps = 161;
                sameColorNumber = 5;
                staticPositionsArray = new int[]{0,8,16};
                break;
            case 249:
                positionsArray = new int[]{4,1,2,11,1,10,9,1,2,10,2,9,11,-1,4,1,11,9,10,1,11,9,4,2,9};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.NORMAL);
                boardSize = 55;
                steps = 185;
                sameColorNumber = 4;
                staticPositionsArray = new int[]{2,4,11};
                break;
            case 250:
                positionsArray = new int[]{1,1,1,-1,8,8,3,3,4,4,4,13,13,3,13,13,4,8,8,3};
                world.setGameType(GameWorld.GameType.NORMAL);
                world.setLevelType(GameWorld.LevelType.MOVABLE);
                boardSize = 45;
                steps = 180;
                movableSteps = 4;
                movablePositionsArray = new int[]{2,17};
                sameColorNumber = 4;
                staticPositionsArray = new int[]{8};
                break;
        }
    }

    public ArrayList<Integer> getPositionsArrayList(){
        positionArrayList.clear();
        if(positionsArray != null) {
            for (int i = 0; i < positionsArray.length; i++) {
                if(positionsArray[i] == -1) {
                    positionArrayList.add(null);
                } else {
                    positionArrayList.add(positionsArray[i]);
                }
            }
        }
        return positionArrayList;
    }

    public int getBoardSize(){
        return boardSize;
    }

    public int getSteps(){
        return steps;
    }

    public float getTime(){
        return time;
    }

    public int getMemoryVisibleSteps() {
        return memoryVisibleSteps;
    }

    public int getMemoryInvisibleSteps() {
        return memoryInvisibleSteps;
    }

    public boolean getMemoryInitialVisibility() {
        return memoryInitialVisiblity;
    }

    public int getMovableSteps() {
        return movableSteps;
    }

    public int[] getMovablePositionsArray() {
        return movablePositionsArray;
    }

    public int getColorChangeSteps() {
        return colorChangeSteps;
    }

    public int[] getColorChangeTokensArray(){
        return colorChangeTokensArray;
    }

    public int getSameColorNumber(){
        return sameColorNumber;
    }

    public int[] getBlockedChangeArrayInitial() {
        return blockedChangeArrayInitial;
    }

    public void setBlockedChangeArray(int[] blockedChangeArray) {
        this.blockedChangeArray = blockedChangeArray;
    }

    public int[] getBlockedChangeArray() {
        return blockedChangeArray;
    }

    public int[] getStaticPositionsArray(){
        return staticPositionsArray;
    }
}
