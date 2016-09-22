package com.forkstone.tokens.helpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.ui.MenuButton;

import java.util.ArrayList;

/**
 * Created by sergi on 1/12/15.
 */
public class InputHandler implements InputProcessor {

    private static final String TAG = "InputHandler";
    private GameWorld world;
    private float scaleFactorX;
    private float scaleFactorY;
    private Rectangle rectangle;
    private int buttonPosition = -1, buttonNumber = -1;
    private int initialScreenX, initialScreenY;
    private ArrayList<MenuButton> menuButtons, gameOverButtons;

    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        rectangle = new Rectangle(0, 0, 200, 200);
        menuButtons = world.getMenuButtons();
        gameOverButtons = world.getGameOver().getMenuButtons();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        for (int i = 0; i < menuButtons.size(); i++) {
            if (menuButtons.get(i).isTouchDown(screenX, screenY)) {
                if(AssetLoader.getSoundState())
                    AssetLoader.soundClick.play();
            }
        }
        for (int i = 0; i < gameOverButtons.size(); i++) {
            if(gameOverButtons.get(i).isTouchDown(screenX, screenY) && world.isGameOver()) {
                if(AssetLoader.getSoundState())
                    AssetLoader.soundClick.play();
            }
        }
        // Roulette
        if(world.getGameOver().getRouletteButton().isTouchDown(screenX, screenY) && world.isGameOver()){
            if(AssetLoader.getSoundState())
                AssetLoader.soundClick.play();
        }
        // Rate
        if(world.getGameOver().getRateButton().isTouchDown(screenX, screenY) && world.isGameOver()){
            if(AssetLoader.getSoundState())
                AssetLoader.soundClick.play();
        }

        buttonPosition = world.getBoard().getButtonTablePositionByTouchDown(screenX, screenY);
        buttonNumber = world.getBoard().getButtonNumberByTouchDown(screenX, screenY);

        // Initial Screen Position
        initialScreenX = screenX;
        initialScreenY = screenY;

//        Gdx.app.log(TAG, "touchDown position: (" + initialScreenX + ", " + initialScreenY + ")");

        // Set checkSprite transparent when is touching down
        if(buttonNumber != -1 && buttonPosition != -1) {
            world.getBoard().getColorButtonByTablePosition(buttonPosition).setOkSpriteOff();
            if(world.isMulticolor()){
                world.getBoard().getColorButtonByTablePosition(buttonPosition).setMulticolor();
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        boolean memory = false, changePosition = false, changeColor = false, changeBlocked = false;

        if (rectangle.contains(screenX, screenY)) {
            if (Settings.DEBUG) world.startGame();
        }

        // Buttons
        if(world.isGameOver()) {
            if(gameOverButtons.get(0).isTouchUp(screenX, screenY) && world.getGameOver().isGameComplete()) {
                world.getGameOver().finish();
                if(!AssetLoader.getTutorialMemory()) {
                    for (int l = 0; l < Settings.MEMORY_LEVELS.length; l++){
                        if(world.getLevel() == Settings.MEMORY_LEVELS[l]-1) {
                            memory = true;
                        }
                    }
                }
                if(!AssetLoader.getTutorialPositionChange()) {
                    for (int l = 0; l < Settings.CHANGEPOSITION_LEVELS.length; l++) {
                        if (world.getLevel() == Settings.CHANGEPOSITION_LEVELS[l]-1) {
                            changePosition = true;
                        }
                    }
                }
                if(!AssetLoader.getTutorialColorChange()) {
                    for (int l = 0; l < Settings.CHANGECOLOR_LEVELS.length; l++) {
                        if (world.getLevel() == Settings.CHANGECOLOR_LEVELS[l]-1) {
                            changeColor = true;
                        }
                    }
                }
                if(!AssetLoader.getTutorialBlockedChange()) {
                    for (int l = 0; l < Settings.CHANGEBLOCKED_LEVELS.length; l++) {
                        if (world.getLevel() == Settings.CHANGEBLOCKED_LEVELS[l]-1) {
                            changeBlocked = true;
                        }
                    }
                }

                if(world.getLevel() == Settings.FIRST_BLOCK_TOKEN_LEVEL - 1 &&
                        AssetLoader.getTutorialPart() == 1){
                    world.goToTutorialScreen("BLOCK", Settings.FIRST_BLOCK_TOKEN_LEVEL);
                } else if(world.getLevel() == Settings.FIRST_TIME_LEVEL - 1 &&
                        AssetLoader.getTutorialPart() == 2){
                    world.goToTutorialScreen("TIME", Settings.FIRST_TIME_LEVEL);
                } else if(world.getLevel() == Settings.DISTRICTONE_LASTLEVEL &&
                        AssetLoader.getMedalPart() == 0){
                    world.goToMedalScreen(1);
                } else if(world.getLevel() == Settings.DISTRICTTWO_LASTLEVEL &&
                        AssetLoader.getMedalPart() == 1){
                    world.goToMedalScreen(2);
                } else if(world.getLevel() == Settings.DISTRICTTHREE_LASTLEVEL &&
                        AssetLoader.getMedalPart() == 2){
                    world.goToMedalScreen(3);
                } else if(world.getLevel() == Settings.DISTRICTFOUR_LASTLEVEL &&
                        AssetLoader.getMedalPart() == 3){
                    world.goToMedalScreen(4);
                } else if(world.getLevel() == Settings.DISTRICTFIVE_LASTLEVEL &&
                        AssetLoader.getMedalPart() == 4){
                    world.goToMedalScreen(5);
                } else if(world.getLevel() == Settings.DISTRICTSIX_LASTLEVEL &&
                        AssetLoader.getMedalPart() == 5){
                    world.goToMedalScreen(6);
                } else if(world.getLevel() == Settings.DISTRICTSEVEN_LASTLEVEL &&
                        AssetLoader.getMedalPart() == 6){
                    world.goToMedalScreen(7);
                } else if(world.getLevel() == Settings.DISTRICTEIGHT_LASTLEVEL &&
                        AssetLoader.getMedalPart() == 7){
                    world.goToMedalScreen(8);
                } else if(world.getLevel() == Settings.DISTRICTNINE_LASTLEVEL &&
                        AssetLoader.getMedalPart() == 8){
                    world.goToMedalScreen(9);
                } else if(world.getLevel() == Settings.DISTRICTTEN_LASTLEVEL &&
                        AssetLoader.getMedalPart() == 9){
                    world.goToMedalScreen(10);
                } else if(memory) {
                    world.goToTutorialScreen("MEMORY", world.getLevel()+1);
                } else if(changePosition) {
                    world.goToTutorialScreen("CHANGEPOSITION", world.getLevel()+1);
                } else if(changeColor) {
                    world.goToTutorialScreen("CHANGECOLOR", world.getLevel()+1);
                } else if(changeBlocked) {
                    world.goToTutorialScreen("CHANGEBLOCKED", world.getLevel()+1);
                } else {
                    world.goToGameScreen(world.getLevel());
                }
            } else if(gameOverButtons.get(1).isTouchUp(screenX, screenY) && !world.getGameOver().isGameComplete()) {
                world.getGameOver().finish();
                world.playGameScreen(world.getLevel());
            } else if(gameOverButtons.get(2).isTouchUp(screenX, screenY)) {
                world.getGameOver().finish();
                world.goToLevelScreen(0);
            } else if(gameOverButtons.get(3).isTouchUp(screenX, screenY)) {
                if(world.getGameOver().isGameComplete()){
                    world.getGameOver().finish();
                    world.playGameScreen(world.getLevel());
                } else {
                    if(AssetLoader.getResol() > 0){
                        world.getGameOver().finish();
                        world.goToGameScreen(world.getLevel());
                        AssetLoader.restResol(1);
                    }
                }
            } else if(gameOverButtons.get(4).isTouchUp(screenX, screenY)) {
                if(world.isTimeGame()){
                    if(AssetLoader.getMoreTime() > 0){
                        world.getGameOver().finish();
                        world.startMoreGame();
                        AssetLoader.restMoreTime(1);
                    }
                } else {
                    if(AssetLoader.getMoreMoves() > 0){
                        world.getGameOver().finish();
                        world.startMoreGame();
                        AssetLoader.restMoreMoves(1);
                    }
                }
            } else if(world.getGameOver().getRouletteButton().isTouchUp(screenX, screenY) && world.getGameOver().isRouletteActive()){
                if(world.isVideoAdActive()){
                    world.actionResolver.viewVideoAd();
//                    world.goToRandomScreen();
                }
            } else if(world.getGameOver().getRateButton().isTouchUp(screenX, screenY) && world.getGameOver().isRateActive()){
                world.actionResolver.rateGame();
                AssetLoader.setIsRated(true);
            }
        } else {
            if (menuButtons.get(0).isTouchUp(screenX, screenY)) {
                world.goToLevelScreen(0);
            } else if (menuButtons.get(1).isTouchUp(screenX, screenY)) {
                world.restartGame();
            } else if (menuButtons.get(2).isTouchUp(screenX, screenY)) {
                if(AssetLoader.getResol() > 0){
                    world.finishGame(true);
                    AssetLoader.restResol(1);
                }
            } else if (menuButtons.get(3).isTouchUp(screenX, screenY)) {
                if(AssetLoader.getMulticolor() > 0 && !world.isMulticolor()){
                    menuButtons.get(3).setColor(world.parseColor(Settings.COLOR_RED_500,1f));
                    world.setMulticolorUp();
                    AssetLoader.restMulticolor(1);
                }
            } else {
                // Not Buttons (Game)
                // Screen Position
                if(initialScreenX != screenX || initialScreenY != screenY){
                    if(buttonNumber != -1 && buttonPosition != -1 && !world.getBoard().getColorButtons().get(buttonNumber).isStatic()) {
                        if(world.getSteps()>0 || world.getTime()>0) {
//                            Gdx.app.log(TAG, "touchUp position: (" + screenX + ", " + screenY + ")");
//                            Gdx.app.log(TAG, "Gap Position: (" + world.getBoard().getTokenPositions().get(world.getBoard().getGapPositions()).x + ", " + world.getBoard().getTokenPositions().get(world.getBoard().getGapPositions()).y + ")");
                            world.getBoard().moveToken(buttonPosition, buttonNumber, screenX, screenY);
                        }
                    }

                    if(world.getBoard().getSameColorNumber() == 3) {world.getBoard().checkOkSpritesThree();}
                    if(world.getBoard().getSameColorNumber() >= 4) {world.getBoard().checkOkSpritesFour();}
                }
            }
        }

        // IsTouchUp
        world.getBoard().checkIfButtonsTouchUp(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        if(buttonNumber != -1 && !world.getBoard().getColorButtons().get(buttonNumber).isStatic()) {
            if(world.getSteps()>0 || world.getTime()>0) {

                if(world.getBoard().ableToMove(buttonPosition) == 1 &&
                        world.getBoard().getTokenPositions().get(buttonPosition).x >= (screenX - ((Settings.BUTTON_SIZE * 1.5f) / 2)) &&
                        world.getBoard().getTokenPositions().get(world.getBoard().getGapPositions()).x <= (screenX - ((Settings.BUTTON_SIZE * 1.5f) / 2))) {
                    world.getBoard().getColorButtons().get(buttonNumber).setPositionPointX(screenX - ((Settings.BUTTON_SIZE * 1.5f) / 2));
                } else if(world.getBoard().ableToMove(buttonPosition) == 2 &&
                        world.getBoard().getTokenPositions().get(buttonPosition).y <= (screenY - (((Settings.BUTTON_SIZE - 10) * 1.5f) / 2)) &&
                        world.getBoard().getTokenPositions().get(world.getBoard().getGapPositions()).y >= (screenY - (((Settings.BUTTON_SIZE + 10) * 1.5f) / 2))) {
                    world.getBoard().getColorButtons().get(buttonNumber).setPositionPointY(screenY - ((Settings.BUTTON_SIZE * 1.5f) / 2));
                } else if(world.getBoard().ableToMove(buttonPosition) == 3 &&
                        world.getBoard().getTokenPositions().get(buttonPosition).x <= (screenX - ((Settings.BUTTON_SIZE * 1.5f) / 2)) &&
                        world.getBoard().getTokenPositions().get(world.getBoard().getGapPositions()).x >= (screenX - ((Settings.BUTTON_SIZE * 1.5f) / 2))) {
                    world.getBoard().getColorButtons().get(buttonNumber).setPositionPointX(screenX - ((Settings.BUTTON_SIZE * 1.5f) / 2));
                } else if(world.getBoard().ableToMove(buttonPosition) == 4 &&
                        world.getBoard().getTokenPositions().get(buttonPosition).y >= (screenY - (((Settings.BUTTON_SIZE + 10) * 1.5f) / 2)) &&
                        world.getBoard().getTokenPositions().get(world.getBoard().getGapPositions()).y <= (screenY - (((Settings.BUTTON_SIZE - 10) * 1.5f) / 2))) {
                    world.getBoard().getColorButtons().get(buttonNumber).setPositionPointY(screenY - ((Settings.BUTTON_SIZE * 1.5f) / 2));
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (world.gameHeight - screenY / scaleFactorY);
    }
}
