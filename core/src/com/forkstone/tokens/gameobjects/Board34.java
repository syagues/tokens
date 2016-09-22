package com.forkstone.tokens.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.tweens.Value;
import com.forkstone.tokens.ui.MenuButton;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by sergi on 14/12/15.
 */
public class Board34 extends Board {

    private static final String TAG = "Board34";
    private int rows, columns;
    private int gapPosition;
    private ArrayList<Integer> tokens;
    private ArrayList<ColorButton> colorButtons = new ArrayList<ColorButton>();
    private ArrayList<Vector2> tokenPositions = new ArrayList<Vector2>();
    private int boardSize;

    private float buttonSize = Settings.BUTTON_SIZE;
    private Value time = new Value();
    private TweenCallback cbToMenuScreen, setInitialOkSprites, setStaticSprites, cbSetupBoard;
    private boolean gameComplete = false;
    private int[] staticTokens;
    private int sameColor;
    private GameWorld world;
    private ArrayList<Counter> movableCounters = new ArrayList<Counter>();

    public Board34(final GameWorld world, float x, float y, float width, float height, TextureRegion texture, int rows, int columns, ArrayList<Integer> tokens, int[] staticTokens, final int sameColor) {

        super(world, x, y, width, height, texture, rows, columns, tokens, sameColor);

        this.rows = rows;
        this.columns = columns;
        this.tokens = tokens;
        this.staticTokens = staticTokens;
        this.sameColor = sameColor;
        this.world = world;

        cbSetupBoard = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                setupBoard();
                startGame();
            }
        };

        Tween.to(time, -1, 0.6f).target(1).delay(world.getDelay(0f)).setCallback(cbSetupBoard)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());

        cbToMenuScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.finishGame(gameComplete);
            }
        };

        setInitialOkSprites = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                checkOkSprites();
            }
        };

        setStaticSprites = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                setStaticSprites();
                if(world.isMovableLevel()){setupMovableCountdownCounters();}
                if(world.isColorChangeLevel()){setupColorChangeCountdownCounters();}
                if(world.isBlockedChangeLevel()){setupBlockedChangeTokens();}
            }
        };
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).update(delta);
        }
        if(world.isMovableLevel()){
            for (Counter movableCounter : movableCounters) {
                movableCounter.update(delta);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        super.render(batch, shapeRenderer, fontShader, objectShader);
        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).render(batch, shapeRenderer, fontShader, objectShader);
        }
        if(world.isMovableLevel()){
            for (Counter movableCounter : movableCounters) {
                movableCounter.render(batch, shapeRenderer, fontShader, objectShader);
            }
        }
    }

    @Override
    public void startGame() {
        if(world.isTutorial()){
            Tween.to(time, -1, 0.4f).target(1).delay(.0f).setCallback(setInitialOkSprites)
                    .setCallbackTriggers(
                            TweenCallback.COMPLETE).start(getManager());
        } else {
            Tween.to(time, -1, 0.6f).target(1).delay(.6f).setCallback(setInitialOkSprites)
                    .setCallbackTriggers(
                            TweenCallback.COMPLETE).start(getManager());
        }

        Tween.to(time, -1, 0.6f).target(2).delay(.3f).setCallback(setStaticSprites)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    @Override
    public void finishGame() {
        Tween.to(time, -1, 0.1f).target(3).delay(.1f).setCallback(cbToMenuScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    @Override
    public void reset() {
        setCheckSpritesOff();
        setupBoard();
        startGame();
    }

    @Override
    public void setupBoard() {
        float spaceBetweenTokens;
        spaceBetweenTokens = (getRectangle().width - (columns * buttonSize) - 2) / (columns + 1);
        setupTokenPositions(spaceBetweenTokens);
        colorButtons.clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(rows == 3 && columns == 4){
                    boardSize = 34;
                    if(tokens.get(i * columns + j) != null){
                        colorButtons.add(new ColorButton(world,
                                tokenPositions.get(i * columns + j).x,
                                tokenPositions.get(i * columns + j).y,
                                buttonSize * 1.25f, (buttonSize + 10) * 1.25f, com.forkstone.tokens.helpers.AssetLoader.colorButton, Color.BLACK, tokens.get(i * columns + j), i * columns + j, isTokenStatic(i * columns + j)));
                    } else {
                        gapPosition = i * columns + j;
                    }
                }
            }
        }

        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).effectsIn();
        }
    }

    @Override
    public void setupBoard(ArrayList<Integer> gameOverTokens) {
        float spaceBetweenTokens;
        spaceBetweenTokens = (getRectangle().width - (columns * buttonSize) - 2) / (columns + 1);
        setupTokenPositions(spaceBetweenTokens);
        colorButtons.clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(rows == 3 && columns == 4){
                    boardSize = 34;
                    if(gameOverTokens.get(i * columns + j) != null){
                        if(gameOverTokens.get(i * columns + j) == -1){
                            colorButtons.add(new ColorButton(world,
                                    tokenPositions.get(i * columns + j).x,
                                    tokenPositions.get(i * columns + j).y,
                                    buttonSize * 1.25f, (buttonSize + 10) * 1.25f, AssetLoader.colorButton, Color.BLACK, 1, i * columns + j, false));
                            if(gapPosition > (i * columns + j)) colorButtons.get(i * columns + j).setMulticolor();
                            else colorButtons.get((i * columns + j)-1).setMulticolor();
                        } else {
                            colorButtons.add(new ColorButton(world,
                                    tokenPositions.get(i * columns + j).x,
                                    tokenPositions.get(i * columns + j).y,
                                    buttonSize * 1.25f, (buttonSize + 10) * 1.25f, AssetLoader.colorButton, Color.BLACK, gameOverTokens.get(i * columns + j), i * columns + j, isTokenStatic(i * columns + j)));
                        }
                    } else {
                        gapPosition = i * columns + j;
                    }
                }
            }
        }

        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).effectsIn();
        }
    }

    @Override
    public void setupTokenPositions(float spaceBetweenTokens) {
        tokenPositions.clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                tokenPositions.add(new Vector2(
                        (j * buttonSize) + (spaceBetweenTokens * j) + getRectangle().x + (spaceBetweenTokens) - 15,
                        getRectangle().y + (i * buttonSize) + (spaceBetweenTokens * i) + (spaceBetweenTokens) + 105));
            }
        }
    }

    public boolean isTokenStatic(int position){
        if(staticTokens != null) {
            for (int i = 0; i < staticTokens.length; i++) {
                if (position == staticTokens[i]) return true;
            }
        }
        return false;
    }

    // Movable
    public void setupMovableCountdownCounters(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_SMARTPHONE)) {
            movableCounters.clear();
            for (Integer movablePosition : world.getLevels().getMovablePositionsArray()) {
                movableCounters.add(new Counter(world,
                        tokenPositions.get(movablePosition).x, tokenPositions.get(movablePosition).y + Settings.BUTTON_SIZE - 10,
                        65, 65, AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f), "movableCountdown", true, world.getLevelColor(), 13f));
            }
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_1610)) {
            movableCounters.clear();
            for (Integer movablePosition : world.getLevels().getMovablePositionsArray()) {
                movableCounters.add(new Counter(world,
                        tokenPositions.get(movablePosition).x, tokenPositions.get(movablePosition).y + Settings.BUTTON_SIZE - 10,
                        65, 65, AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f), "movableCountdown", true, world.getLevelColor(), 13f));
            }
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_SMALLSMARTPHONE)) {
            movableCounters.clear();
            for (Integer movablePosition : world.getLevels().getMovablePositionsArray()) {
                movableCounters.add(new Counter(world,
                        tokenPositions.get(movablePosition).x, tokenPositions.get(movablePosition).y + Settings.BUTTON_SIZE - 10,
                        65, 65, AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f), "movableCountdown", true, world.getLevelColor(), 13f));
            }
        } else if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)) {
            movableCounters.clear();
            for (Integer movablePosition : world.getLevels().getMovablePositionsArray()) {
                movableCounters.add(new Counter(world,
                        tokenPositions.get(movablePosition).x + 10, tokenPositions.get(movablePosition).y + Settings.BUTTON_SIZE + 5,
                        50, 50, AssetLoader.fontXXXS, world.parseColor(Settings.COLOR_WHITE, 1f), "movableCountdown", true, world.getLevelColor(), 10f));
            }
        }
    }

    public ArrayList<Counter> getMovableCountdownCounters(){
        return movableCounters;
    }

    // ColorChange
    public void setupColorChangeCountdownCounters(){
        for (Integer colorChangePosition : world.getLevels().getColorChangeTokensArray()) {
            getColorButtonByTablePosition(colorChangePosition).setColorChange();
        }
    }

    // BlockedChange
    public void setupBlockedChangeTokens() {
        for (Integer blockedChangePosition : world.getLevels().getBlockedChangeArrayInitial()) {
            getColorButtonByTablePosition(blockedChangePosition).setStatic(true);
        }
    }

    @Override
    public int getButtonNumberByTouchDown(int screenX, int screenY) {
        for (int i = 0; i < colorButtons.size(); i++) {
            if (colorButtons.get(i).isTouchDown(screenX, screenY)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getButtonTablePositionByTouchDown(int screenX, int screenY) {
        for (int i = 0; i < colorButtons.size(); i++) {
            if (colorButtons.get(i).isTouchDown(screenX, screenY)) {
                return colorButtons.get(i).getTablePosition();
            }
        }
        return -1;
    }

    @Override
    public void checkIfButtonsTouchUp(int screenX, int screenY) {
        for (int i = 0; i < colorButtons.size(); i++) {
            if (colorButtons.get(i).isTouchUp(screenX, screenY)) {}
        }
        ArrayList<MenuButton> menuButtons = world.getMenuButtons();
        for (MenuButton menuButton : menuButtons) {
            if(menuButton.isTouchUp(screenX,screenY)){}
        }
        ArrayList<MenuButton> gameOverButtons = world.getGameOver().getMenuButtons();
        for (MenuButton gameOverButton : gameOverButtons) {
            if(gameOverButton.isTouchUp(screenX, screenY)){}
        }
    }

    public ArrayList<ColorButton> getColorButtons() {
        return colorButtons;
    }

    public ArrayList<Vector2> getTokenPositions() {return tokenPositions;}

    @Override
    public ColorButton getColorButtonByTablePosition(int tablePosition) {
        for(ColorButton colorButton : getColorButtons()) {
            if(colorButton.getTablePosition() == tablePosition) {
                return colorButton;
            }
        }
        return null;
    }

    public void setGapPositions(int position) {
        gapPosition = position;
    }

    public int getGapPositions() {
        return gapPosition;
    }

    public int getBoardSize() {
        return boardSize;
    }

    @Override
    public int getSameColorNumber(){
        return sameColor;
    }

    @Override
    public int ableToMove(int buttonNumber) {
        // 1 left, 3 right, 2 up, 4 down
        switch (gapPosition) {
            case 0:
                if(buttonNumber == 1) {return 1;}
                if(buttonNumber == 4) {return 4;}
                break;
            case 1:
                if(buttonNumber == 0) {return 3;}
                if(buttonNumber == 2) {return 1;}
                if(buttonNumber == 5) {return 4;}
                break;
            case 2:
                if(buttonNumber == 1) {return 3;}
                if(buttonNumber == 3) {return 1;}
                if(buttonNumber == 6) {return 4;}
                break;
            case 3:
                if(buttonNumber == 2) {return 3;}
                if(buttonNumber == 7) {return 4;}
                break;
            case 4:
                if(buttonNumber == 0) {return 2;}
                if(buttonNumber == 5) {return 1;}
                if(buttonNumber == 8) {return 4;}
                break;
            case 5:
                if(buttonNumber == 1) {return 2;}
                if(buttonNumber == 4) {return 3;}
                if(buttonNumber == 6) {return 1;}
                if(buttonNumber == 9) {return 4;}
                break;
            case 6:
                if(buttonNumber == 2) {return 2;}
                if(buttonNumber == 5) {return 3;}
                if(buttonNumber == 7) {return 1;}
                if(buttonNumber == 10) {return 4;}
                break;
            case 7:
                if(buttonNumber == 3) {return 2;}
                if(buttonNumber == 6) {return 3;}
                if(buttonNumber == 11) {return 4;}
                break;
            case 8:
                if(buttonNumber == 4) {return 2;}
                if(buttonNumber == 9) {return 1;}
                break;
            case 9:
                if(buttonNumber == 5) {return 2;}
                if(buttonNumber == 8) {return 3;}
                if(buttonNumber == 10) {return 1;}
                break;
            case 10:
                if(buttonNumber == 6) {return 2;}
                if(buttonNumber == 9) {return 3;}
                if(buttonNumber == 11) {return 1;}
                break;
            case 11:
                if(buttonNumber == 7) {return 2;}
                if(buttonNumber == 10) {return 3;}
                break;
        }
        return 0;
    }

    @Override
    public boolean moveToken(int initialBoardPosition, int colorButtonNumber, int actualX, int actualY) {
        int direction = ableToMove(initialBoardPosition);
        float middlePoint = 0;
        boolean changePosition = false;

        // Moves
        if(direction == 1) {
            middlePoint = (getTokenPositions().get(initialBoardPosition).x - getTokenPositions().get(gapPosition).x) / 2 + getTokenPositions().get(gapPosition).x;
            if(middlePoint <= actualX) {
                getColorButtons().get(colorButtonNumber).effectX(actualX, getTokenPositions().get(initialBoardPosition).x);
            } else if(middlePoint > actualX) {
                getColorButtons().get(colorButtonNumber).effectX(actualX, getTokenPositions().get(gapPosition).x);
                changePosition = true;
            }
        } else if(direction == 2) {
            middlePoint = (getTokenPositions().get(gapPosition).y - getTokenPositions().get(initialBoardPosition).y) / 2 + getTokenPositions().get(initialBoardPosition).y;
            if(middlePoint >= actualY) {
                getColorButtons().get(colorButtonNumber).effectY(actualY, getTokenPositions().get(initialBoardPosition).y);
            } else if (middlePoint < actualY) {
                getColorButtons().get(colorButtonNumber).effectY(actualY, getTokenPositions().get(gapPosition).y);
                changePosition = true;
            }
        } else if(direction == 3) {
            middlePoint = (getTokenPositions().get(gapPosition).x - getTokenPositions().get(initialBoardPosition).x) / 2 + getTokenPositions().get(initialBoardPosition).x;
            if(middlePoint >= actualX) {
                getColorButtons().get(colorButtonNumber).effectX(actualX, getTokenPositions().get(initialBoardPosition).x);
            } else if (middlePoint < actualX) {
                getColorButtons().get(colorButtonNumber).effectX(actualX, getTokenPositions().get(gapPosition).x);
                changePosition = true;
            }
        } else if (direction == 4) {
            middlePoint = (getTokenPositions().get(initialBoardPosition).y - getTokenPositions().get(gapPosition).y) / 2 + getTokenPositions().get(gapPosition).y;
            if(middlePoint <= actualY) {
                getColorButtons().get(colorButtonNumber).effectY(actualY, getTokenPositions().get(initialBoardPosition).y);
            } else if(middlePoint > actualY) {
                getColorButtons().get(colorButtonNumber).effectY(actualY, getTokenPositions().get(gapPosition).y);
                changePosition = true;
            }
        }

        // Change position
        if (changePosition) {
            if(AssetLoader.getSoundState()) AssetLoader.soundDrag.play();

            getColorButtons().get(colorButtonNumber).setTablePosition(getGapPositions());
            setGapPositions(initialBoardPosition);

            world.restStep();
            if(world.isMemoryLevel()) {
                checkMemoryGame();
                checkOkSprites();
                checkGameComplete();
            } else if(world.isMovableLevel()) {
                checkMovableGame();
                checkOkSprites();
                checkGameComplete();
            } else if(world.isColorChangeLevel()) {
                checkColorChangeGame();
                checkOkSprites();
                checkGameComplete();
            } else if(world.isBlockedChangeLevel()) {
                checkBlockChangeGame(colorButtonNumber);
                checkOkSprites();
                checkGameComplete();
            } else {
                checkOkSprites();
                checkGameComplete();
            }
        }

        return changePosition;
    }

    @Override
    public void checkOkSprites(){
        if(sameColor == 3) checkOkSpritesThree();
        if(sameColor >= 4) checkOkSpritesFour();
    }

    @Override
    public void checkOkSpritesThree(){
        ArrayList<ColorButton> colorButtons = getColorButtons();
        world.setLeft(colorButtons.size());
        for(ColorButton colorButton : colorButtons) {
            if(colorButton.checkOkSprite() == ColorButton.TokenState.OK) world.restLeft();
        }
    }

    @Override
    public void checkOkSpritesFour(){
        ArrayList<ColorButton> colorButtons = getColorButtons();
        ArrayList<Integer> positions = new ArrayList<Integer>();
        world.setLeft(colorButtons.size());
        int coincidences;
        boolean alreadyOk;

        // Initial State
        for (ColorButton colorButton : colorButtons){
            colorButton.setInitialState();
        }

        // First check
        for(ColorButton colorButton : colorButtons) {
            coincidences = 0;
            positions.clear();
            if(colorButton.getTokenState() == ColorButton.TokenState.INITIAL){

                if(colorButton.getTablePosition() > 3){
                    if(getGapPositions() != colorButton.getTablePosition()-4){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()-4).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()-4).isMulticolor()){
                            coincidences++;
                            positions.add(colorButton.getTablePosition()-4);
                        }
                    }
                }
                if(colorButton.getTablePosition() < 8){
                    if(getGapPositions() != colorButton.getTablePosition()+4){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()+4).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()+4).isMulticolor()){
                            coincidences++;
                            positions.add(colorButton.getTablePosition()+4);
                        }
                    }
                }
                if(colorButton.getTablePosition()%4 != 0){
                    if(getGapPositions() != colorButton.getTablePosition()-1){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()-1).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()-1).isMulticolor()){
                            coincidences++;
                            positions.add(colorButton.getTablePosition()-1);
                        }
                    }
                }
                if((colorButton.getTablePosition()+1)%4 != 0){
                    if(getGapPositions() != colorButton.getTablePosition()+1){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()+1).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()+1).isMulticolor()){
                            coincidences++;
                            positions.add(colorButton.getTablePosition()+1);
                        }
                    }
                }

                if(positions.size() >=2){
                    //Gdx.app.log(TAG, "Primer check: " + colorButton.getTablePosition());
                    colorButton.setOkState();
                    world.restLeft();
                    for (int position : positions){
                        if(getColorButtonByTablePosition(position).getTokenState() == ColorButton.TokenState.INITIAL){
                            //Gdx.app.log(TAG, "Primer check*: " + position);
                            getColorButtonByTablePosition(position).setOkState();
                            world.restLeft();
                        }
                    }
                } else {
                    colorButton.setNotOkState();
                }
            }
        }

        // Second check
        for(ColorButton colorButton : colorButtons) {
            alreadyOk = false;
            if(colorButton.getTokenState() == ColorButton.TokenState.NOTOK){

                if(colorButton.getTablePosition() > 3){
                    if(getGapPositions() != colorButton.getTablePosition()-4){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()-4).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()-4).isMulticolor()){
                            if(getColorButtonByTablePosition(colorButton.getTablePosition()-4).getTokenState() == ColorButton.TokenState.OK){
                                alreadyOk = true;
                            }
                        }
                    }
                }
                if(colorButton.getTablePosition() < 8){
                    if(getGapPositions() != colorButton.getTablePosition()+4){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()+4).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()+4).isMulticolor()){
                            if(getColorButtonByTablePosition(colorButton.getTablePosition()+4).getTokenState() == ColorButton.TokenState.OK){
                                alreadyOk = true;
                            }
                        }
                    }
                }
                if(colorButton.getTablePosition()%4 != 0){
                    if(getGapPositions() != colorButton.getTablePosition()-1){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()-1).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()-1).isMulticolor()){
                            if(getColorButtonByTablePosition(colorButton.getTablePosition()-1).getTokenState() == ColorButton.TokenState.OK){
                                alreadyOk = true;
                            }
                        }
                    }
                }
                if((colorButton.getTablePosition()+1)%4 != 0){
                    if(getGapPositions() != colorButton.getTablePosition()+1){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()+1).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()+1).isMulticolor()){
                            if(getColorButtonByTablePosition(colorButton.getTablePosition()+1).getTokenState() == ColorButton.TokenState.OK){
                                alreadyOk = true;
                            }
                        }
                    }
                }

                if(alreadyOk){
                    colorButton.setOkState();
                    world.restLeft();
                } else {
                    colorButton.setNotOkState();
                }
            }
        }

        // Setting Sprites
        for (ColorButton colorButton : colorButtons){
            if(colorButton.getTokenState() == ColorButton.TokenState.OK && !colorButton.isStatic()){
                colorButton.setOkSpriteOn();
            } else {
                colorButton.setOkSpriteOff();
            }
        }
    }

    @Override
    public void setCheckSpritesOff(){
        ArrayList<ColorButton> colorButtons = getColorButtons();
        for(ColorButton colorButton : colorButtons) {
            colorButton.effectsOut();
        }
    }

    public void setStaticSprites(){
        ArrayList<ColorButton> colorButtons = getColorButtons();
        for(ColorButton colorButton : colorButtons) {
            colorButton.setStaticSprite();
        }
    }

    // Memory
    public void checkMemoryGame(){
        world.restMemoryCountdown();
        if(world.getMemoryCountdown() == 0){
            if(world.getSteps() > 1){
                if(!world.isMemoryActive()){
                    ArrayList<ColorButton> colorButtons = getColorButtons();
                    for(ColorButton colorButton : colorButtons) {
                        colorButton.setColorOfButtonsMemory();
                    }
                } else {
                    ArrayList<ColorButton> colorButtons = getColorButtons();
                    for(ColorButton colorButton : colorButtons) {
                        colorButton.setColorOfButtons();
                    }
                }
                world.changeMemoryState(!world.isMemoryActive());
            }
        }
    }

    // Movable
    public void checkMovableGame(){
        world.restMovableCountdown();
        if(world.getMovableCountdown() == 0 && world.getLeft() != 0 && world.getSteps() != 0){
            world.changeMovableState();
            world.setMovableCountdown(world.getLevels().getMovableSteps());
            ArrayList<Integer> colorsArray = new ArrayList<Integer>();
            for (int i = 0; i < world.getLevels().getMovablePositionsArray().length; i++){
                if(world.getLevels().getMovablePositionsArray()[i] != getGapPositions()){
                    colorsArray.add(getColorButtonByTablePosition(world.getLevels().getMovablePositionsArray()[i]).getType());
                } else {
                    colorsArray.add(null);
                }
            }
            boolean changed = false;
            for (int i = 0; i < world.getLevels().getMovablePositionsArray().length; i++){
                if(world.getLevels().getMovablePositionsArray()[i] == getGapPositions() && !changed){
                    if(i == world.getLevels().getMovablePositionsArray().length - 1) {
                        getColorButtonByTablePosition(world.getLevels().getMovablePositionsArray()[0]).setPositionPointX(getTokenPositions().get(world.getLevels().getMovablePositionsArray()[world.getLevels().getMovablePositionsArray().length - 1]).x);
                        getColorButtonByTablePosition(world.getLevels().getMovablePositionsArray()[0]).setPositionPointY(getTokenPositions().get(world.getLevels().getMovablePositionsArray()[world.getLevels().getMovablePositionsArray().length - 1]).y);
                        getColorButtonByTablePosition(world.getLevels().getMovablePositionsArray()[0]).setTablePosition(world.getLevels().getMovablePositionsArray()[world.getLevels().getMovablePositionsArray().length - 1]);
                        setGapPositions(world.getLevels().getMovablePositionsArray()[0]);
                    } else {
                        getColorButtonByTablePosition(world.getLevels().getMovablePositionsArray()[i + 1]).setPositionPointX(getTokenPositions().get(world.getLevels().getMovablePositionsArray()[i]).x);
                        getColorButtonByTablePosition(world.getLevels().getMovablePositionsArray()[i + 1]).setPositionPointY(getTokenPositions().get(world.getLevels().getMovablePositionsArray()[i]).y);
                        getColorButtonByTablePosition(world.getLevels().getMovablePositionsArray()[i + 1]).setTablePosition(world.getLevels().getMovablePositionsArray()[i]);
                        setGapPositions(world.getLevels().getMovablePositionsArray()[i + 1]);
                    }
                    changed = true;
                }
            }
            for (int i = 0; i < world.getLevels().getMovablePositionsArray().length; i++){
                if(world.getLevels().getMovablePositionsArray()[i] != getGapPositions()){
                    if(i == 0){
                        getColorButtonByTablePosition(world.getLevels().getMovablePositionsArray()[0]).setType(colorsArray.get(world.getLevels().getMovablePositionsArray().length - 1));
                    } else {
                        getColorButtonByTablePosition(world.getLevels().getMovablePositionsArray()[i]).setType(colorsArray.get(i - 1));
                    }
                }
            }
            for (int i = 0; i < world.getLevels().getMovablePositionsArray().length; i++){
                if(world.getLevels().getMovablePositionsArray()[i] != getGapPositions()){
                    getColorButtonByTablePosition(world.getLevels().getMovablePositionsArray()[i]).effectsOut(.2f, 0f);
                }
            }
            for (int i = 0; i < world.getLevels().getMovablePositionsArray().length; i++){
                if(world.getLevels().getMovablePositionsArray()[i] != getGapPositions()){
                    getColorButtonByTablePosition(world.getLevels().getMovablePositionsArray()[i]).effectsIn(.2f, .2f);
                }
            }
        }
    }

    // Color Change
    public void checkColorChangeGame(){
        world.restColorChangeCountdown();
        if(world.getColorChangeCountdown() == 0){
            for (ColorButton colorButton : colorButtons){
                if (colorButton.isColorChange()){
                    colorButton.changeColorChange(world.getNewRandomColor(colorButton.getType()));
                }
            }
            world.setColorChangeCountdown(world.getLevels().getColorChangeSteps());
        }
    }

    // Block Change
    public void checkBlockChangeGame(int colorButtonNumber){
        int tokenMovedPosition = getColorButtons().get(colorButtonNumber).getTablePosition();
        int[] blockedPositions = world.getLevels().getBlockedChangeArray();
        int[] auxArray = new int[blockedPositions.length];
        int randomPosition, i = 0;
        ArrayList<Integer> actualPositions = new ArrayList<Integer>();
        for (ColorButton colorButton : getColorButtons()){
            actualPositions.add(colorButton.getTablePosition());
        }
        for (Integer position : blockedPositions){
            getColorButtonByTablePosition(position).setStatic(false);
            randomPosition = world.changeBlockedPosition(position, tokenMovedPosition, getGapPositions(), actualPositions);
            getColorButtonByTablePosition(randomPosition).setStatic(true);
            auxArray[i] = randomPosition;
            i++;
        }
        world.getLevels().setBlockedChangeArray(auxArray);
    }

    @Override
    public void checkGameComplete() {
        if(isGameComplete() || (world.isNormalGame() && world.getSteps() == 0)) {
//            finishGame();
            float delay;
            if(world.isMovableLevel() && (world.getMovableCountdown() == 0 || world.getMovableCountdown() == world.getLevels().getMovableSteps())) delay = .6f;
            else delay = .2f;

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    world.finishGame(gameComplete);
                }
            }, delay);
        }
    }

    @Override
    public boolean isGameComplete() {
        ArrayList<ColorButton> colorButtons = getColorButtons();
        for(ColorButton colorButton : colorButtons) {
            if(colorButton.getTokenState() == ColorButton.TokenState.NOTOK) {
                gameComplete = false;
                return false;
            }
        }

        gameComplete = true;
        return true;
    }
}
