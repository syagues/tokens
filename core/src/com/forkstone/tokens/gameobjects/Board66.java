package com.forkstone.tokens.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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
 * Created by sergi on 15/2/16.
 */
public class Board66 extends Board {

    private static final String TAG = "Board66";
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

    public Board66(final GameWorld world, float x, float y, float width, float height, TextureRegion texture, int rows, int columns, ArrayList<Integer> tokens, int[] staticTokens, final int sameColor) {

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
            }
        };
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).update(delta);
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        super.render(batch, shapeRenderer, fontShader, objectShader);
        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).render(batch, shapeRenderer, fontShader, objectShader);
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
        spaceBetweenTokens = (getRectangle().width - (columns * buttonSize) - 35) / (columns + 1);
        setupTokenPositions(spaceBetweenTokens);
        colorButtons.clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(rows == 6 && columns == 6){
                    boardSize = 66;
                    if(tokens.get(i * columns + j) != null){
                        colorButtons.add(new ColorButton(world,
                                tokenPositions.get(i * columns + j).x,
                                tokenPositions.get(i * columns + j).y,
                                setTokensSize(buttonSize), setTokensSize(buttonSize + 10), AssetLoader.colorButton, Color.BLACK, tokens.get(i * columns + j), i * columns + j, isTokenStatic(i * columns + j)));
                    } else {
                        setGapPositions(i * columns + j);
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
        spaceBetweenTokens = (getRectangle().width - (columns * buttonSize) - 35) / (columns + 1);
        setupTokenPositions(spaceBetweenTokens);
        colorButtons.clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(rows == 6 && columns == 6){
                    boardSize = 66;
                    if(gameOverTokens.get(i * columns + j) != null){
                        if(gameOverTokens.get(i * columns + j) == -1){
                            colorButtons.add(new ColorButton(world,
                                    tokenPositions.get(i * columns + j).x,
                                    tokenPositions.get(i * columns + j).y,
                                    setTokensSize(buttonSize), setTokensSize(buttonSize + 10), AssetLoader.colorButton, Color.BLACK, 1, i * columns + j, false));
                            if(gapPosition > (i * columns + j)) colorButtons.get(i * columns + j).setMulticolor();
                            else colorButtons.get((i * columns + j)-1).setMulticolor();
                        } else {
                            colorButtons.add(new ColorButton(world,
                                    tokenPositions.get(i * columns + j).x,
                                    tokenPositions.get(i * columns + j).y,
                                    setTokensSize(buttonSize), setTokensSize(buttonSize + 10), AssetLoader.colorButton, Color.BLACK, gameOverTokens.get(i * columns + j), i * columns + j, isTokenStatic(i * columns + j)));
                        }
                    } else {
                        setGapPositions(i * columns + j);
                    }
                }
            }
        }

        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).effectsIn();
        }
    }

    public float setTokensSize(float defaultSize){

        if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            return defaultSize * 0.75f;
        } else {
            return defaultSize * 0.82f;
        }
    }

    @Override
    public void setupTokenPositions(float spaceBetweenTokens) {
        tokenPositions.clear();
        if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    tokenPositions.add(new Vector2(
                            (j * buttonSize) + ((spaceBetweenTokens - 3) * j) + getRectangle().x + (spaceBetweenTokens) + 55,
                            getRectangle().y + (i * buttonSize) + (spaceBetweenTokens * i) + (spaceBetweenTokens) + 65));
                }
            }
        } else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    tokenPositions.add(new Vector2(
                            (j * buttonSize) + ((spaceBetweenTokens - 6) * j) + getRectangle().x + (spaceBetweenTokens) + 48,
                            getRectangle().y + (i * buttonSize) + ((spaceBetweenTokens - 5.3f) * i) + (spaceBetweenTokens) + 58));
                }
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
        if(world.getGameOver() != null){
            ArrayList<MenuButton> gameOverButtons = world.getGameOver().getMenuButtons();
            for (MenuButton gameOverButton : gameOverButtons) {
                if(gameOverButton.isTouchUp(screenX, screenY)){}
            }
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
        int direction = 0;
        // 1 left, 3 right, 2 up, 4 down
        switch (gapPosition) {
            case 0:
                if(buttonNumber == 1) {direction = 1;}
                if(buttonNumber == 6) {direction = 4;}
                break;
            case 1:
                if(buttonNumber == 0) {direction = 3;}
                if(buttonNumber == 2) {direction = 1;}
                if(buttonNumber == 7) {direction = 4;}
                break;
            case 2:
                if(buttonNumber == 1) {direction = 3;}
                if(buttonNumber == 3) {direction = 1;}
                if(buttonNumber == 8) {direction = 4;}
                break;
            case 3:
                if(buttonNumber == 2) {direction = 3;}
                if(buttonNumber == 4) {direction = 1;}
                if(buttonNumber == 9) {direction = 4;}
                break;
            case 4:
                if(buttonNumber == 3) {direction = 3;}
                if(buttonNumber == 5) {direction = 1;}
                if(buttonNumber == 10) {direction = 4;}
                break;
            case 5:
                if(buttonNumber == 4) {direction = 3;}
                if(buttonNumber == 11) {direction = 4;}
                break;
            case 6:
                if(buttonNumber == 0) {direction = 2;}
                if(buttonNumber == 7) {direction = 1;}
                if(buttonNumber == 12) {direction = 4;}
                break;
            case 7:
                if(buttonNumber == 1) {direction = 2;}
                if(buttonNumber == 6) {direction = 3;}
                if(buttonNumber == 8) {direction = 1;}
                if(buttonNumber == 13) {direction = 4;}
                break;
            case 8:
                if(buttonNumber == 2) {direction = 2;}
                if(buttonNumber == 7) {direction = 3;}
                if(buttonNumber == 9) {direction = 1;}
                if(buttonNumber == 14) {direction = 4;}
                break;
            case 9:
                if(buttonNumber == 3) {direction = 2;}
                if(buttonNumber == 8) {direction = 3;}
                if(buttonNumber == 10) {direction = 1;}
                if(buttonNumber == 15) {direction = 4;}
                break;
            case 10:
                if(buttonNumber == 4) {direction = 2;}
                if(buttonNumber == 9) {direction = 3;}
                if(buttonNumber == 11) {direction = 1;}
                if(buttonNumber == 16) {direction = 4;}
                break;
            case 11:
                if(buttonNumber == 5) {direction = 2;}
                if(buttonNumber == 10) {direction = 3;}
                if(buttonNumber == 17) {direction = 4;}
                break;
            case 12:
                if(buttonNumber == 6) {direction = 2;}
                if(buttonNumber == 13) {direction = 1;}
                if(buttonNumber == 18) {direction = 4;}
                break;
            case 13:
                if(buttonNumber == 7) {direction = 2;}
                if(buttonNumber == 12) {direction = 3;}
                if(buttonNumber == 14) {direction = 1;}
                if(buttonNumber == 19) {direction = 4;}
                break;
            case 14:
                if(buttonNumber == 8) {direction = 2;}
                if(buttonNumber == 13) {direction = 3;}
                if(buttonNumber == 15) {direction = 1;}
                if(buttonNumber == 20) {direction = 4;}
                break;
            case 15:
                if(buttonNumber == 9) {direction = 2;}
                if(buttonNumber == 14) {direction = 3;}
                if(buttonNumber == 16) {direction = 1;}
                if(buttonNumber == 21) {direction = 4;}
                break;
            case 16:
                if(buttonNumber == 10) {direction = 2;}
                if(buttonNumber == 15) {direction = 3;}
                if(buttonNumber == 17) {direction = 1;}
                if(buttonNumber == 22) {direction = 4;}
                break;
            case 17:
                if(buttonNumber == 11) {direction = 2;}
                if(buttonNumber == 16) {direction = 3;}
                if(buttonNumber == 23) {direction = 4;}
                break;
            case 18:
                if(buttonNumber == 12) {direction = 2;}
                if(buttonNumber == 19) {direction = 1;}
                if(buttonNumber == 24) {direction = 4;}
                break;
            case 19:
                if(buttonNumber == 13) {direction = 2;}
                if(buttonNumber == 18) {direction = 3;}
                if(buttonNumber == 20) {direction = 1;}
                if(buttonNumber == 25) {direction = 4;}
                break;
            case 20:
                if(buttonNumber == 14) {direction = 2;}
                if(buttonNumber == 19) {direction = 3;}
                if(buttonNumber == 21) {direction = 1;}
                if(buttonNumber == 26) {direction = 4;}
                break;
            case 21:
                if(buttonNumber == 15) {direction = 2;}
                if(buttonNumber == 20) {direction = 3;}
                if(buttonNumber == 22) {direction = 1;}
                if(buttonNumber == 27) {direction = 4;}
                break;
            case 22:
                if(buttonNumber == 16) {direction = 2;}
                if(buttonNumber == 21) {direction = 3;}
                if(buttonNumber == 23) {direction = 1;}
                if(buttonNumber == 28) {direction = 4;}
                break;
            case 23:
                if(buttonNumber == 17) {direction = 2;}
                if(buttonNumber == 22) {direction = 3;}
                if(buttonNumber == 29) {direction = 4;}
                break;
            case 24:
                if(buttonNumber == 18) {direction = 2;}
                if(buttonNumber == 25) {direction = 1;}
                if(buttonNumber == 30) {direction = 4;}
                break;
            case 25:
                if(buttonNumber == 19) {direction = 2;}
                if(buttonNumber == 24) {direction = 3;}
                if(buttonNumber == 26) {direction = 1;}
                if(buttonNumber == 31) {direction = 4;}
                break;
            case 26:
                if(buttonNumber == 20) {direction = 2;}
                if(buttonNumber == 25) {direction = 3;}
                if(buttonNumber == 27) {direction = 1;}
                if(buttonNumber == 32) {direction = 4;}
                break;
            case 27:
                if(buttonNumber == 21) {direction = 2;}
                if(buttonNumber == 26) {direction = 3;}
                if(buttonNumber == 28) {direction = 1;}
                if(buttonNumber == 33) {direction = 4;}
                break;
            case 28:
                if(buttonNumber == 22) {direction = 2;}
                if(buttonNumber == 27) {direction = 3;}
                if(buttonNumber == 29) {direction = 1;}
                if(buttonNumber == 34) {direction = 4;}
                break;
            case 29:
                if(buttonNumber == 23) {direction = 2;}
                if(buttonNumber == 28) {direction = 3;}
                if(buttonNumber == 35) {direction = 4;}
                break;
            case 30:
                if(buttonNumber == 24) {direction = 2;}
                if(buttonNumber == 31) {direction = 1;}
                break;
            case 31:
                if(buttonNumber == 25) {direction = 2;}
                if(buttonNumber == 30) {direction = 3;}
                if(buttonNumber == 32) {direction = 1;}
                break;
            case 32:
                if(buttonNumber == 26) {direction = 2;}
                if(buttonNumber == 31) {direction = 3;}
                if(buttonNumber == 33) {direction = 1;}
                break;
            case 33:
                if(buttonNumber == 27) {direction = 2;}
                if(buttonNumber == 32) {direction = 3;}
                if(buttonNumber == 34) {direction = 1;}
                break;
            case 34:
                if(buttonNumber == 28) {direction = 2;}
                if(buttonNumber == 33) {direction = 3;}
                if(buttonNumber == 35) {direction = 1;}
                break;
            case 35:
                if(buttonNumber == 29) {direction = 2;}
                if(buttonNumber == 34) {direction = 3;}
                break;
        }
                   
        return direction;
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
        } else if(direction == 4) {
            middlePoint = (getTokenPositions().get(initialBoardPosition).y - getTokenPositions().get(gapPosition).y) / 2 + getTokenPositions().get(gapPosition).y;
            if(middlePoint <= actualY) {
                getColorButtons().get(colorButtonNumber).effectY(actualY, getTokenPositions().get(initialBoardPosition).y);
            } else if(middlePoint > actualY) {
                getColorButtons().get(colorButtonNumber).effectY(actualY, getTokenPositions().get(gapPosition).y);
                changePosition = true;
            }
        }

//        Gdx.app.log(TAG, "Initial Board Position: (" + getTokenPositions().get(initialBoardPosition).x + "," +getTokenPositions().get(initialBoardPosition).y + ")");
//        Gdx.app.log(TAG, "Middle point: " + middlePoint);

        // Change position
        if (changePosition) {
            if(AssetLoader.getSoundState()) AssetLoader.soundDrag.play();
            getColorButtons().get(colorButtonNumber).setTablePosition(getGapPositions());
            //setGapPositions(initialBoardPosition);
            gapPosition = initialBoardPosition;

            world.restStep();
            checkOkSprites();
            checkGameComplete();
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

                // Able to move down
                if(colorButton.getTablePosition() > 5){
                    if(getGapPositions() != colorButton.getTablePosition()-6){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()-6).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()-6).isMulticolor()){
                            coincidences++;
                            positions.add(colorButton.getTablePosition()-6);
                        }
                    }
                }
                // Able to move up
                if(colorButton.getTablePosition() < 30){
                    if(getGapPositions() != colorButton.getTablePosition()+6){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()+6).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()+6).isMulticolor()){
                            coincidences++;
                            positions.add(colorButton.getTablePosition()+6);
                        }
                    }
                }
                // Able to move left
                if(colorButton.getTablePosition()%6 != 0){
                    if(getGapPositions() != colorButton.getTablePosition()-1){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()-1).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()-1).isMulticolor()){
                            coincidences++;
                            positions.add(colorButton.getTablePosition()-1);
                        }
                    }
                }
                // Able to move right
                if((colorButton.getTablePosition()+1)%6 != 0){
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

                if(colorButton.getTablePosition() > 5){
                    if(getGapPositions() != colorButton.getTablePosition()-6){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()-6).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()-6).isMulticolor()){
                            if(getColorButtonByTablePosition(colorButton.getTablePosition()-6).getTokenState() == ColorButton.TokenState.OK){
                                alreadyOk = true;
                            }
                        }
                    }
                }
                if(colorButton.getTablePosition() < 30){
                    if(getGapPositions() != colorButton.getTablePosition()+6){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()+6).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()+6).isMulticolor()){
                            if(getColorButtonByTablePosition(colorButton.getTablePosition()+6).getTokenState() == ColorButton.TokenState.OK){
                                alreadyOk = true;
                            }
                        }
                    }
                }
                if(colorButton.getTablePosition()%6 != 0){
                    if(getGapPositions() != colorButton.getTablePosition()-1){
                        if(colorButton.getType() == getColorButtonByTablePosition(colorButton.getTablePosition()-1).getType() ||
                                colorButton.isMulticolor() || getColorButtonByTablePosition(colorButton.getTablePosition()-1).isMulticolor()){
                            if(getColorButtonByTablePosition(colorButton.getTablePosition()-1).getTokenState() == ColorButton.TokenState.OK){
                                alreadyOk = true;
                            }
                        }
                    }
                }
                if((colorButton.getTablePosition()+1)%6 != 0){
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
                    //Gdx.app.log(TAG, "Segon check: " + colorButton.getTablePosition());
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

        //Gdx.app.log(TAG, "-----------------------------------------------");
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

    @Override
    public void checkGameComplete() {
        if(isGameComplete() || (world.isNormalGame() && world.getSteps() == 0)) {
            finishGame();
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