package com.forkstone.tokens.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameworld.GameWorld;

/**
 * Created by sergi on 1/12/15.
 */
public class Board extends GameObject {

    private static final String TAG = "Board";
    private int gapPosition;
    private ArrayList<ColorButton> colorButtons = new ArrayList<ColorButton>();
    private ArrayList<Vector2> tokenPositions = new ArrayList<Vector2>();
    private int boardSize;
    private int sameColor;
    private ArrayList<Counter> movableCounters = new ArrayList<Counter>();

    public Board(final GameWorld world, float x, float y, float width, float height,
                 TextureRegion texture, int rows, int columns, ArrayList<Integer> tokens, int sameColor) {
        super(world, x, y, width, height, texture, world.parseColor(Settings.COLOR_BOARD,1f));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        super.render(batch, shapeRenderer, fontShader, objectShader);
    }

    public void startGame() {}

    public void finishGame() {}

    public void reset() {}

    public void setupBoard() {}

    public void setupBoard(ArrayList<Integer> GameOverTokens) {}

    public void setupTokenPositions(float spaceBetweenTokens) {}

    public void setupMovableCountdownCounters(){}

    public ArrayList<Counter> getMovableCountdownCounters(){
        return movableCounters;
    }

    public int getButtonNumberByTouchDown(int screenX, int screenY) {
        return -1;
    }

    public int getButtonTablePositionByTouchDown(int screenX, int screenY) {
        return -1;
    }

    public void checkIfButtonsTouchUp(int screenX, int screenY) { }

    public ArrayList<ColorButton> getColorButtons() {
        return colorButtons;
    }

    public ArrayList<Vector2> getTokenPositions() {return tokenPositions;}

    public ColorButton getColorButtonByTablePosition(int tablePosition) {
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

    public int getSameColorNumber(){
        return sameColor;
    }

    public int ableToMove(int buttonNumber) { return 0; }

    public boolean moveToken(int initialBoardPosition, int colorButtonNumber, int actualX, int actualY) {
        return false;
    }

    public void checkOkSpritesThree(){}

    public void checkOkSprites(){}

    public void checkOkSpritesFour(){}

    public void setCheckSpritesOff(){}

    public void checkGameComplete() {}

    public boolean isGameComplete() {return false;}

}
