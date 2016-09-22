package com.forkstone.tokens.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;

/**
 * Created by sergi on 1/12/15.
 */
public class ColorButton extends GameObject {

    private static final String TAG = "ColorButton";
    private int type, tablePosition;
    private float width, height;
    private float x, y;
    private Color color;
    private Sprite backSprite, checkSprite, staticSprite, multicolorSprite, colorChangeSprite;
    private Vector2 positionPoint, position;
    private TweenCallback cbClickCorrect, cbFadeInNew;
    private ButtonState buttonState;
    private TokenState tokenState;
    private boolean ok, isStatic, multicolor, colorChange;
    private boolean extrasActive = true;
    private Counter colorChangeCounter;

    private enum ButtonState {
        IDLE, CHANGING
    }

    public enum TokenState {
        INITIAL, OK, NOTOK
    }

    public ColorButton(final GameWorld world, float x, float y, float width, float height,
                       TextureRegion texture, Color color, int type, int tablePosition, boolean isStatic) {


        super(world, x, y, width, height, texture, color);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        positionPoint = new Vector2(x, y);
        position = new Vector2(x, y);
        position.set(getPosition());
        this.type = type;
        this.tablePosition = tablePosition;
        this.isStatic = isStatic;
        setColorOfButtons();
        getRectangle().set(x - 15, y - 15, width + 30, height + 30);

        buttonState = ButtonState.IDLE;
        tokenState = TokenState.INITIAL;

        // Static
        staticSprite = new Sprite(AssetLoader.staticTR);
        staticSprite.setAlpha(0);

        // Check
        checkSprite = new Sprite(AssetLoader.check);
        checkSprite.setAlpha(0);

        // Multicolor
        multicolorSprite = new Sprite(AssetLoader.multicolorButtonUp);
        multicolorSprite.setAlpha(0);

        // ColorChange
        colorChangeSprite = new Sprite(AssetLoader.rateButtonUp);
        colorChangeSprite.setAlpha(0);
        if(world.isColorChangeLevel()){
            setupColorChange();
        }

        cbFadeInNew = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                buttonState = ButtonState.IDLE;
            }
        };
    }

    public void fadeInNew(float duration, float delay) {
        getSprite().setAlpha(0);
        Tween.to(getSprite(), com.forkstone.tokens.tweens.SpriteAccessor.ALPHA, duration).target(1).delay(delay)
                .setCallback(cbFadeInNew).setCallbackTriggers(TweenCallback.COMPLETE)
                .ease(TweenEquations.easeInOutSine).start(getManager());
    }

    @Override
    public void update(float delta) {
        setOkSpritePosition();
        super.update(delta);

        // Movement
        position.x += positionPoint.x - position.x;
        position.y += positionPoint.y - position.y;
        getRectangle().setPosition(position.x, position.y);
        getSprite().setPosition(position.x, position.y);
        // Static
        if(isStatic()){
            setStaticPositionSprite();
        }
        // Multicolor
        if(isMulticolor()){
            setMulticolorSprite();
        }
        // Color Change
        if (isColorChange()) {
            setColorChangeSprite();
            colorChangeCounter.update(delta);
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        if (isPressed) {
            getSprite().setRegion(AssetLoader.colorButton_pressed);
            getFlashSprite().setRegion(AssetLoader.colorButton_pressed);
        } else {
            getSprite().setRegion(AssetLoader.colorButton);
            getFlashSprite().setRegion(AssetLoader.colorButton);
        }
        super.render(batch, shapeRenderer, fontShader, objectShader);
        if(extrasActive && !world.isGameOver()) {
            staticSprite.draw(batch);
            if(!isColorChange() && !isStatic()) checkSprite.draw(batch);
            multicolorSprite.draw(batch);
            colorChangeSprite.draw(batch);
            if(isColorChange()){colorChangeCounter.render(batch, shapeRenderer, fontShader, objectShader);}
        }
    }

    @Override
    public boolean isTouchDown(int screenX, int screenY) {
        // Click animation
        if ((buttonState == ButtonState.IDLE && world.isRunning() && !isStatic()) ||
                (buttonState == ButtonState.IDLE && world.isTutorial())) {
            return super.isTouchDown(screenX, screenY);
        } else return false;
    }

    @Override
    public boolean isTouchUp(int screenX, int screenY) {
        if ((world.isRunning() && !isStatic()) || (world.isTutorial())) {
            if (super.isTouchUp(screenX, screenY)) {
                return true;
            }
        }
        return false;
    }

    public void setColorOfButtons() {
        switch (type) {
            case 0:
                color = world.parseColor(Settings.COLOR_GREEN_500,1f);
                break;
            case 1:
                color = world.parseColor(Settings.COLOR_RED_500,1f);
                break;
            case 2:
                color = world.parseColor(Settings.COLOR_BLUE_500,1f);
                break;
            case 3:
                color = world.parseColor(Settings.COLOR_AMBER_500,1f);
                break;
            case 4:
                color = world.parseColor(Settings.COLOR_PURPLE_500,1f);
                break;
            case 5:
                color = world.parseColor(Settings.COLOR_PINK_500,1f);
                break;
            case 6:
                color = world.parseColor(Settings.COLOR_CYAN_500,1f);
                break;
            case 7:
                color = world.parseColor(Settings.COLOR_LIME_500,1f);
                break;
            case 8:
                color = world.parseColor(Settings.COLOR_INDIGO_500,1f);
                break;
            case 9:
                color = world.parseColor(Settings.COLOR_TEAL_500,1f);
                break;
            case 10:
                color = world.parseColor(Settings.COLOR_ORANGE_500,1f);
                break;
            case 11:
                color = world.parseColor(Settings.COLOR_YELLOW_500,1f);
                break;
            case 12:
                color = world.parseColor(Settings.COLOR_LIGHTGREEN_500,1f);
                break;
            case 13:
                color = world.parseColor(Settings.COLOR_BROWN_500,1f);
                break;
        }
        setColor(color);
    }

    public void setColorOfButtonsMemory(){
        setColor(world.parseColor(Settings.COLOR_BLUEGREY_400, 1f));
    }

    public void setType(int typ) {
        type = typ;
        setColorOfButtons();
    }

    public int getType() {
        return type;
    }

    public void setTablePosition(int position){
        tablePosition = position;
    }

    public int getTablePosition() {
        return tablePosition;
    }

    public void effectsIn() {
        if(world.isTutorial()){
            fadeIn(.3f, 0f);
        } else {
            fadeIn(0.6f, 0.6f);
        }
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (isColorChange()) {
                    colorChangeCounter.setColor(getColorChangeCounter());
                    colorChangeCounter.setHelpName("colorChangeCountdown");
                }
            }
        }, 1.2f);

        effectY(getPosition().y - world.gameHeight, getPosition().y, 0.48f, 0.0f);
    }

    public void effectsOut() {
        fadeOut(0.2f, 0.0f);
        if (isColorChange()) {
            colorChangeCounter.fadeOut(0.2f, 0f);
        }
        checkSprite.setAlpha(0f);
        staticSprite.setAlpha(0f);
        multicolorSprite.setAlpha(0f);
        colorChangeSprite.setAlpha(0f);
        //effectY(getPosition().y, getPosition().y - world.gameHeight, 0.5f, 0.26f);
    }

    public void effectsIn(float duration, float delay) {
        fadeIn(duration, delay);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                extrasActive = true;
                setStaticSprite();
                if (isMulticolor()) setMulticolor();
                if (isColorChange()) {
                    colorChangeCounter.setColor(getColorChangeCounter());
                    colorChangeCounter.setHelpName("colorChangeCountdown");
                }
            }
        }, duration + delay);
    }

    public void effectsOut(float duration, float delay){
        fadeOut(duration, delay);
        if (isColorChange())
            colorChangeCounter.fadeOut(duration, delay);
        checkSprite.setAlpha(0f);
        staticSprite.setAlpha(0f);
        multicolorSprite.setAlpha(0f);
        extrasActive = false;
    }

    public void effectsResetIn() {
        fadeIn(0.6f, 0f);
        effectY(getPosition().y - world.gameHeight, getPosition().y, 0.48f, 0.0f);
    }

    public void effectX(float originPointX, float finalPointX) {
        effectX(originPointX, finalPointX, .3f, .0f);
        setPositionPointX(finalPointX);
    }

    public void effectY(float originPointY, float finalPointY) {
        effectY(originPointY, finalPointY, .3f, .0f);
        setPositionPointY(finalPointY);
    }

    public void setPositionPointX(float p) {
        this.positionPoint.x = p;
    }

    public void setPositionPointY(float p) {
        this.positionPoint.y = p;
    }

    public float getPositionPointX() {
        return positionPoint.x;
    }

    public void setStatic(boolean state) {
        isStatic = state;
        if (state) {
            setStaticSprite();
            if(world.isBlockedChangeLevel()){staticSprite.setColor(getBlockedMovableColor());}
        } else {
            staticSprite.setAlpha(0f);
        }
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStaticSprite(){
        if(isStatic){
            staticSprite.set(new Sprite(com.forkstone.tokens.helpers.AssetLoader.staticTR));
            staticSprite.setPosition(position.x, position.y-5);
            staticSprite.setSize(getSprite().getWidth(), getSprite().getHeight());
            staticSprite.setColor(Color.WHITE);
            staticSprite.setAlpha(1f);
            staticSprite.setOriginCenter();
        }
    }

    public void setStaticPositionSprite(){
        if(world.getBoard().getBoardSize() == 33) {
            staticSprite.setPosition(position.x, position.y - 2);
        } else if(world.getBoard().getBoardSize() == 34) {
            staticSprite.setPosition(position.x, position.y - 2);
        } else if(world.getBoard().getBoardSize() == 44) {
            staticSprite.setPosition(position.x, position.y - 2);
        } else if(world.getBoard().getBoardSize() == 45) {
            staticSprite.setPosition(position.x, position.y - 2);
        } else if(world.getBoard().getBoardSize() == 55) {
            staticSprite.setPosition(position.x, position.y - 2);
        } else if(world.getBoard().getBoardSize() == 66) {
            staticSprite.setPosition(position.x, position.y - 2);
        }
    }

    public Color getBlockedMovableColor(){
        switch (type) {
            case 0:
                color = world.parseColor(Settings.COLOR_GREEN_100,1f);
                break;
            case 1:
                color = world.parseColor(Settings.COLOR_RED_100,1f);
                break;
            case 2:
                color = world.parseColor(Settings.COLOR_BLUE_100,1f);
                break;
            case 3:
                color = world.parseColor(Settings.COLOR_AMBER_100,1f);
                break;
            case 4:
                color = world.parseColor(Settings.COLOR_PURPLE_100,1f);
                break;
            case 5:
                color = world.parseColor(Settings.COLOR_PINK_100,1f);
                break;
            case 6:
                color = world.parseColor(Settings.COLOR_CYAN_100,1f);
                break;
            case 7:
                color = world.parseColor(Settings.COLOR_LIME_100,1f);
                break;
            case 8:
                color = world.parseColor(Settings.COLOR_INDIGO_100,1f);
                break;
            case 9:
                color = world.parseColor(Settings.COLOR_TEAL_100,1f);
                break;
            case 10:
                color = world.parseColor(Settings.COLOR_ORANGE_100,1f);
                break;
            case 11:
                color = world.parseColor(Settings.COLOR_YELLOW_100,1f);
                break;
            case 12:
                color = world.parseColor(Settings.COLOR_LIGHTGREEN_100,1f);
                break;
            case 13:
                color = world.parseColor(Settings.COLOR_BROWN_100,1f);
                break;
        }
        return color;
    }

    // Multicolor
    public boolean isMulticolor(){
        return multicolor;
    }

    public void setMulticolor(){
        multicolor = true;
        multicolorSprite.set(new Sprite(com.forkstone.tokens.helpers.AssetLoader.multicolorButtonUp));
        multicolorSprite.setSize(getSprite().getWidth(), getSprite().getHeight() - 8);
        multicolorSprite.setScale(1.55f, 1.55f);
        multicolorSprite.setAlpha(1f);
        multicolorSprite.setOriginCenter();
        setColor(world.parseColor(Settings.COLOR_RED_500, 1f));
        world.getMenuButtons().get(3).setColor(world.parseColor(Settings.COLOR_BOARD, 1f));
        world.setMulticolorDown();
    }

    public void setMulticolorSprite(){
        if(isPressed) {
            if(world.getBoard().getBoardSize() == 33) {
                multicolorSprite.setPosition(position.x + 3, position.y + 3);
            } else if(world.getBoard().getBoardSize() == 34) {
                multicolorSprite.setPosition(position.x + 3, position.y + 2);
            } else if(world.getBoard().getBoardSize() == 44) {
                multicolorSprite.setPosition(position.x + 3, position.y + 2);
            } else if(world.getBoard().getBoardSize() == 45) {
                multicolorSprite.setPosition(position.x + 3, position.y + 0);
            } else if(world.getBoard().getBoardSize() == 55) {
                multicolorSprite.setPosition(position.x + 3, position.y + 0);
            } else if(world.getBoard().getBoardSize() == 66) {
                multicolorSprite.setPosition(position.x + 3, position.y + 0);
            }
        } else {
            if(world.getBoard().getBoardSize() == 33) {
                multicolorSprite.setPosition(position.x + 3, position.y + 13);
            } else if(world.getBoard().getBoardSize() == 34) {
                multicolorSprite.setPosition(position.x + 3, position.y + 12);
            } else if(world.getBoard().getBoardSize() == 44) {
                multicolorSprite.setPosition(position.x + 3, position.y + 12);
            } else if(world.getBoard().getBoardSize() == 45) {
                multicolorSprite.setPosition(position.x + 3, position.y + 10);
            } else if(world.getBoard().getBoardSize() == 55) {
                multicolorSprite.setPosition(position.x + 3, position.y + 10);
            } else if(world.getBoard().getBoardSize() == 66) {
                multicolorSprite.setPosition(position.x + 3, position.y + 10);
            }
        }
    }

    // Color Change
    public void setupColorChange() {
        if(world.getBoard().getBoardSize() == 33) {
            colorChangeCounter = new Counter(world, x + width/2 - 50, y + height/2 - 50,
                    100, 100, AssetLoader.fontS, world.parseColor(Settings.COLOR_WHITE, 1f),
                    "", true, world.parseColor(Settings.COLOR_WHITE, 0f), 23f);
        } else if(world.getBoard().getBoardSize() == 34) {
            colorChangeCounter = new Counter(world, x + width/2 - 50, y + height/2 - 42,
                    90, 90, AssetLoader.fontXS, world.parseColor(Settings.COLOR_WHITE, 1f),
                    "", true, world.parseColor(Settings.COLOR_WHITE, 0f), 25f);
        } else if(world.getBoard().getBoardSize() == 44) {
            colorChangeCounter = new Counter(world, x + width/2 - 50, y + height/2 - 42,
                    90, 90, AssetLoader.fontXS, world.parseColor(Settings.COLOR_WHITE, 1f),
                    "", true, world.parseColor(Settings.COLOR_WHITE, 0f), 25f);
        } else if(world.getBoard().getBoardSize() == 45) {
            colorChangeCounter = new Counter(world, x + width/2 - 50, y + height/2 - 45,
                    80, 80, AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f),
                    "", true, world.parseColor(Settings.COLOR_WHITE, 0f), 23f);
        } else if(world.getBoard().getBoardSize() == 55) {
            colorChangeCounter = new Counter(world, x + width/2 - 50, y + height/2 - 45,
                    80, 80, AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f),
                    "", true, world.parseColor(Settings.COLOR_WHITE, 0f), 23f);
        }
    }

    public boolean isColorChange(){
        return colorChange;
    }

    public void setColorChange(){
        colorChange = true;
        colorChangeSprite.set(new Sprite(AssetLoader.rateButtonUp));
        colorChangeSprite.setSize(getSprite().getWidth(), getSprite().getHeight() - 8);
        colorChangeSprite.setScale(1f, 1f);
        colorChangeSprite.setColor(world.parseColor(Settings.COLOR_WHITE, 0f));
        colorChangeSprite.setOriginCenter();
    }

    public void setColorChangeSprite(){
        if(isPressed) {
            if(world.getBoard().getBoardSize() == 33) {
                colorChangeSprite.setPosition(position.x + 3, position.y + 3);
                colorChangeCounter.setPosition(
                        position.x + width/2 - colorChangeCounter.getRectangle().getWidth()/2,
                        position.y + height/2 - colorChangeCounter.getRectangle().getHeight()/2 - 6);
            } else if(world.getBoard().getBoardSize() == 34) {
                colorChangeSprite.setPosition(position.x + 3, position.y + 2);
                colorChangeCounter.setPosition(
                        position.x + width/2 - colorChangeCounter.getRectangle().getWidth()/2,
                        position.y + height/2 - colorChangeCounter.getRectangle().getHeight()/2 - 6);
            } else if(world.getBoard().getBoardSize() == 44) {
                colorChangeSprite.setPosition(position.x + 3, position.y + 2);
                colorChangeCounter.setPosition(
                        position.x + width/2 - colorChangeCounter.getRectangle().getWidth()/2,
                        position.y + height/2 - colorChangeCounter.getRectangle().getHeight()/2 - 6);
            } else if(world.getBoard().getBoardSize() == 45) {
                colorChangeSprite.setPosition(position.x + 3, position.y + 0);
                colorChangeCounter.setPosition(
                        position.x + width/2 - colorChangeCounter.getRectangle().getWidth()/2,
                        position.y + height/2 - colorChangeCounter.getRectangle().getHeight()/2 - 6);
            } else if(world.getBoard().getBoardSize() == 55) {
                colorChangeSprite.setPosition(position.x + 3, position.y + 0);
                colorChangeCounter.setPosition(
                        position.x + width/2 - colorChangeCounter.getRectangle().getWidth()/2,
                        position.y + height/2 - colorChangeCounter.getRectangle().getHeight()/2 - 6);
            } else if(world.getBoard().getBoardSize() == 66) {
                colorChangeSprite.setPosition(position.x + 3, position.y + 0);
                colorChangeCounter.setPosition(
                        position.x + width/2 - colorChangeCounter.getRectangle().getWidth()/2,
                        position.y + height/2 - colorChangeCounter.getRectangle().getHeight()/2 - 6);
            }
        } else {
            if(world.getBoard().getBoardSize() == 33) {
                colorChangeSprite.setPosition(position.x + 3, position.y + 13);
                colorChangeCounter.setPosition(
                        position.x + width/2 - colorChangeCounter.getRectangle().getWidth()/2,
                        position.y + height/2 - colorChangeCounter.getRectangle().getHeight()/2);
            } else if(world.getBoard().getBoardSize() == 34) {
                colorChangeSprite.setPosition(position.x + 3, position.y + 12);
                colorChangeCounter.setPosition(
                        position.x + width/2 - colorChangeCounter.getRectangle().getWidth()/2,
                        position.y + height/2 - colorChangeCounter.getRectangle().getHeight()/2);
            } else if(world.getBoard().getBoardSize() == 44) {
                colorChangeSprite.setPosition(position.x + 3, position.y + 12);
                colorChangeCounter.setPosition(
                        position.x + width/2 - colorChangeCounter.getRectangle().getWidth()/2,
                        position.y + height/2 - colorChangeCounter.getRectangle().getHeight()/2);
            } else if(world.getBoard().getBoardSize() == 45) {
                colorChangeSprite.setPosition(position.x + 3, position.y + 10);
                colorChangeCounter.setPosition(
                        position.x + width/2 - colorChangeCounter.getRectangle().getWidth()/2,
                        position.y + height/2 - colorChangeCounter.getRectangle().getHeight()/2);
            } else if(world.getBoard().getBoardSize() == 55) {
                colorChangeSprite.setPosition(position.x + 3, position.y + 10);
                colorChangeCounter.setPosition(
                        position.x + width/2 - colorChangeCounter.getRectangle().getWidth()/2,
                        position.y + height/2 - colorChangeCounter.getRectangle().getHeight()/2);
            } else if(world.getBoard().getBoardSize() == 66) {
                colorChangeSprite.setPosition(position.x + 3, position.y + 10);
                colorChangeCounter.setPosition(
                        position.x + width/2 - colorChangeCounter.getRectangle().getWidth()/2,
                        position.y + height/2 - colorChangeCounter.getRectangle().getHeight()/2);
            }
        }
    }

    public void changeColorChange(int newType) {
        setType(newType);
        colorChangeCounter.setColor(getColorChangeCounter());
        world.getBoard().checkOkSprites();
        world.getBoard().checkGameComplete();
    }

    public Color getColorChangeCounter() {
        switch (type) {
            case 0:
                color = world.parseColor(Settings.COLOR_GREEN_800,1f);
                break;
            case 1:
                color = world.parseColor(Settings.COLOR_RED_800,1f);
                break;
            case 2:
                color = world.parseColor(Settings.COLOR_BLUE_800,1f);
                break;
            case 3:
                color = world.parseColor(Settings.COLOR_AMBER_800,1f);
                break;
            case 4:
                color = world.parseColor(Settings.COLOR_PURPLE_800,1f);
                break;
            case 5:
                color = world.parseColor(Settings.COLOR_PINK_800,1f);
                break;
            case 6:
                color = world.parseColor(Settings.COLOR_CYAN_800,1f);
                break;
            case 7:
                color = world.parseColor(Settings.COLOR_LIME_800,1f);
                break;
            case 8:
                color = world.parseColor(Settings.COLOR_INDIGO_800,1f);
                break;
            case 9:
                color = world.parseColor(Settings.COLOR_TEAL_800,1f);
                break;
            case 10:
                color = world.parseColor(Settings.COLOR_ORANGE_800,1f);
                break;
            case 11:
                color = world.parseColor(Settings.COLOR_YELLOW_800,1f);
                break;
            case 12:
                color = world.parseColor(Settings.COLOR_LIGHTGREEN_800,1f);
                break;
            case 13:
                color = world.parseColor(Settings.COLOR_BROWN_800,1f);
                break;
        }
        return color;
    }

    public TokenState checkOkSprite() {
        boolean checkIsOk = false;
        if(world.getBoard().getBoardSize() == 33) {
            checkIsOk = checkIsOk33();
        } else if(world.getBoard().getBoardSize() == 34) {
            checkIsOk = checkIsOk34();
        } else if(world.getBoard().getBoardSize() == 44) {
            checkIsOk = checkIsOk44();
        } else if(world.getBoard().getBoardSize() == 45) {
            checkIsOk = checkIsOk45();
        } else if(world.getBoard().getBoardSize() == 55) {
            checkIsOk = checkIsOk55();
        } else if(world.getBoard().getBoardSize() == 66) {
            checkIsOk = checkIsOk66();
        }

        if(checkIsOk) {
            if(!isStatic()) setOkSpriteOn();
            setOkState();
        } else {
            setOkSpriteOff();
            setNotOkState();
        }

        return getTokenState();
    }

    public void setOkSpriteOn() {
        Vector2 position = world.getBoard().getTokenPositions().get(getTablePosition());
        checkSprite.set(new Sprite(com.forkstone.tokens.helpers.AssetLoader.check));
        checkSprite.setColor(com.forkstone.tokens.helpers.FlatColors.WHITE);
        checkSprite.setPosition(position.x, position.y);
        checkSprite.setSize(getSprite().getWidth() / 3, getSprite().getHeight() / 3);
        checkSprite.setAlpha(1f);
        checkSprite.setOriginCenter();
    }

    public void setOkSpriteOff() {
        checkSprite.setAlpha(0);
    }

    public void setOkSpritePosition() {
        int boardSize = world.getBoard().getBoardSize();
        if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            if (boardSize == 33) {
                checkSprite.setX(getSprite().getX() + getSprite().getWidth() / 2 - 35);
                checkSprite.setY(getSprite().getY() + getSprite().getHeight() / 2 - 35);
            } else if (boardSize == 34) {
                checkSprite.setX(getSprite().getX() + getSprite().getWidth() / 2 - 35);
                checkSprite.setY(getSprite().getY() + getSprite().getHeight() / 2 - 35);
            } else if (boardSize == 44) {
                checkSprite.setX(getSprite().getX() + getSprite().getWidth() / 2 - 35);
                checkSprite.setY(getSprite().getY() + getSprite().getHeight() / 2 - 35);
            } else if (boardSize == 45) {
                checkSprite.setX(getSprite().getX() + getSprite().getWidth() / 2 - 30);
                checkSprite.setY(getSprite().getY() + getSprite().getHeight() / 2 - 30);
            } else if (boardSize == 55) {
                checkSprite.setX(getSprite().getX() + getSprite().getWidth() / 2 - 30);
                checkSprite.setY(getSprite().getY() + getSprite().getHeight() / 2 - 30);
            } else if (boardSize == 66) {
                checkSprite.setX(getSprite().getX() + getSprite().getWidth() / 2 - 25);
                checkSprite.setY(getSprite().getY() + getSprite().getHeight() / 2 - 25);
            }
        } else {
            if (boardSize == 33) {
                checkSprite.setX(getSprite().getX() + getSprite().getWidth() / 2 - 45);
                checkSprite.setY(getSprite().getY() + getSprite().getHeight() / 2 - 45);
            } else if (boardSize == 34) {
                checkSprite.setX(getSprite().getX() + getSprite().getWidth() / 2 - 35);
                checkSprite.setY(getSprite().getY() + getSprite().getHeight() / 2 - 35);
            } else if (boardSize == 44) {
                checkSprite.setX(getSprite().getX() + getSprite().getWidth() / 2 - 35);
                checkSprite.setY(getSprite().getY() + getSprite().getHeight() / 2 - 35);
            } else if (boardSize == 45) {
                checkSprite.setX(getSprite().getX() + getSprite().getWidth() / 2 - 30);
                checkSprite.setY(getSprite().getY() + getSprite().getHeight() / 2 - 30);
            } else if (boardSize == 55) {
                checkSprite.setX(getSprite().getX() + getSprite().getWidth() / 2 - 30);
                checkSprite.setY(getSprite().getY() + getSprite().getHeight() / 2 - 30);
            } else if (boardSize == 66) {
                checkSprite.setX(getSprite().getX() + getSprite().getWidth() / 2 - 25);
                checkSprite.setY(getSprite().getY() + getSprite().getHeight() / 2 - 25);
            }
        }
    }

    public void setOkState(){
        tokenState = TokenState.OK;
    }

    public void setNotOkState(){
        tokenState = TokenState.NOTOK;
    }

    public void setInitialState(){
        tokenState = TokenState.INITIAL;
    }

    public TokenState getTokenState() {
        return tokenState;
    }

    public boolean checkIsOk33() {
        int positions[];
        int tablePosition = getTablePosition();
//        Gdx.app.log(TAG, "Table Position Switch: " + tablePosition);
        switch (tablePosition){
            case 0:
                positions = new int []{1,3};
                break;
            case 1:
                positions = new int []{0,2,4};
                break;
            case 2:
                positions = new int []{1,5};
                break;
            case 3:
                positions = new int []{0,4,6};
                break;
            case 4:
                positions = new int []{1,3,5,7};
                break;
            case 5:
                positions = new int []{2,4,8};
                break;
            case 6:
                positions = new int []{3,7};
                break;
            case 7:
                positions = new int []{4,6,8};
                break;
            default:
                positions = new int []{5,7};
                break;
        }
        return checkIsOkPosition(positions);
    }

    public boolean checkIsOk34() {
        int positions[];
        int tablePosition = getTablePosition();
        switch (tablePosition){
            case 0:
                positions = new int []{1,4};
                break;
            case 1:
                positions = new int []{0,2,5};
                break;
            case 2:
                positions = new int []{1,3,6};
                break;
            case 3:
                positions = new int []{2,7};
                break;
            case 4:
                positions = new int []{0,5,8};
                break;
            case 5:
                positions = new int []{1,4,6,9};
                break;
            case 6:
                positions = new int []{2,5,7,10};
                break;
            case 7:
                positions = new int []{3,6,11};
                break;
            case 8:
                positions = new int []{4,9};
                break;
            case 9:
                positions = new int []{5,8,10};
                break;
            case 10:
                positions = new int []{6,9,11};
                break;
            default:
                positions = new int []{7,10};
                break;
        }
        return checkIsOkPosition(positions);
    }

    public boolean checkIsOk44() {
        int positions[];
        int tablePosition = getTablePosition();
        switch (tablePosition){
            case 0:
                positions = new int []{1,4};
                break;
            case 1:
                positions = new int []{0,2,5};
                break;
            case 2:
                positions = new int []{1,3,6};
                break;
            case 3:
                positions = new int []{2,7};
                break;
            case 4:
                positions = new int []{0,5,8};
                break;
            case 5:
                positions = new int []{1,4,6,9};
                break;
            case 6:
                positions = new int []{2,5,7,10};
                break;
            case 7:
                positions = new int []{3,6,11};
                break;
            case 8:
                positions = new int []{4,9,12};
                break;
            case 9:
                positions = new int []{5,8,10,13};
                break;
            case 10:
                positions = new int []{6,9,11,14};
                break;
            case 11:
                positions = new int []{7,10,15};
                break;
            case 12:
                positions = new int []{8,13};
                break;
            case 13:
                positions = new int []{9,12,14};
                break;
            case 14:
                positions = new int []{10,13,15};
                break;
            default:
                positions = new int []{11,14};
                break;
        }
        return checkIsOkPosition(positions);
    }

    public boolean checkIsOk45() {
        int positions[];
        int tablePosition = getTablePosition();
        switch (tablePosition){
            case 0:
                positions = new int []{1,5};
                break;
            case 1:
                positions = new int []{0,2,6};
                break;
            case 2:
                positions = new int []{1,3,7};
                break;
            case 3:
                positions = new int []{2,4,8};
                break;
            case 4:
                positions = new int []{3,9};
                break;
            case 5:
                positions = new int []{0,6,10};
                break;
            case 6:
                positions = new int []{1,5,7,11};
                break;
            case 7:
                positions = new int []{2,6,8,12};
                break;
            case 8:
                positions = new int []{3,7,9,13};
                break;
            case 9:
                positions = new int []{4,8,14};
                break;
            case 10:
                positions = new int []{5,11,15};
                break;
            case 11:
                positions = new int []{6,10,12,16};
                break;
            case 12:
                positions = new int []{7,11,13,17};
                break;
            case 13:
                positions = new int []{8,12,14,18};
                break;
            case 14:
                positions = new int []{9,13,19};
                break;
            case 15:
                positions = new int []{10,16};
                break;
            case 16:
                positions = new int []{11,15,17};
                break;
            case 17:
                positions = new int []{12,16,18};
                break;
            case 18:
                positions = new int []{13,17,19};
                break;
            default:
                positions = new int []{14,18};
                break;
        }
        return checkIsOkPosition(positions);
    }

    public boolean checkIsOk55() {
        int positions[];
        int tablePosition = getTablePosition();
        switch (tablePosition){
            case 0:
                positions = new int []{1,5};
                break;
            case 1:
                positions = new int []{0,2,6};
                break;
            case 2:
                positions = new int []{1,3,7};
                break;
            case 3:
                positions = new int []{2,4,8};
                break;
            case 4:
                positions = new int []{3,9};
                break;
            case 5:
                positions = new int []{0,6,10};
                break;
            case 6:
                positions = new int []{1,5,7,11};
                break;
            case 7:
                positions = new int []{2,6,8,12};
                break;
            case 8:
                positions = new int []{3,7,9,13};
                break;
            case 9:
                positions = new int []{4,8,14};
                break;
            case 10:
                positions = new int []{5,11,15};
                break;
            case 11:
                positions = new int []{6,10,12,16};
                break;
            case 12:
                positions = new int []{7,11,13,17};
                break;
            case 13:
                positions = new int []{8,12,14,18};
                break;
            case 14:
                positions = new int []{9,13,19};
                break;
            case 15:
                positions = new int []{10,16,20};
                break;
            case 16:
                positions = new int []{11,15,17,21};
                break;
            case 17:
                positions = new int []{12,16,18,22};
                break;
            case 18:
                positions = new int []{13,17,19,23};
                break;
            case 19:
                positions = new int []{14,18,24};
                break;
            case 20:
                positions = new int []{15,21};
                break;
            case 21:
                positions = new int []{16,20,22};
                break;
            case 22:
                positions = new int []{17,21,23};
                break;
            case 23:
                positions = new int []{18,22,24};
                break;
            default:
                positions = new int []{19,23};
                break;
        }
        return checkIsOkPosition(positions);
    }

    public boolean checkIsOk66(){
        int positions[];
        int tablePosition = getTablePosition();
        switch (tablePosition){
            case 0:
                positions = new int []{1,6};
                break;
            case 1:
                positions = new int []{0,2,7};
                break;
            case 2:
                positions = new int []{1,3,8};
                break;
            case 3:
                positions = new int []{2,4,9};
                break;
            case 4:
                positions = new int []{3,5,10};
                break;
            case 5:
                positions = new int []{4,11};
                break;
            case 6:
                positions = new int []{0,7,12};
                break;
            case 7:
                positions = new int []{1,6,8,13};
                break;
            case 8:
                positions = new int []{2,7,9,14};
                break;
            case 9:
                positions = new int []{3,8,10,15};
                break;
            case 10:
                positions = new int []{4,9,11,16};
                break;
            case 11:
                positions = new int []{5,10,17};
                break;
            case 12:
                positions = new int []{6,13,18};
                break;
            case 13:
                positions = new int []{7,12,14,19};
                break;
            case 14:
                positions = new int []{8,13,15,20};
                break;
            case 15:
                positions = new int []{9,14,16,21};
                break;
            case 16:
                positions = new int []{10,15,17,22};
                break;
            case 17:
                positions = new int []{11,16,23};
                break;
            case 18:
                positions = new int []{12,19,24};
                break;
            case 19:
                positions = new int []{13,18,20,25};
                break;
            case 20:
                positions = new int []{14,19,21,26};
                break;
            case 21:
                positions = new int []{15,20,22,27};
                break;
            case 22:
                positions = new int []{16,21,23,28};
                break;
            case 23:
                positions = new int []{17,22,29};
                break;
            case 24:
                positions = new int []{18,25,30};
                break;
            case 25:
                positions = new int []{19,24,26,31};
                break;
            case 26:
                positions = new int []{20,25,27,32};
                break;
            case 27:
                positions = new int []{21,26,28,33};
                break;
            case 28:
                positions = new int []{22,27,29,34};
                break;
            case 29:
                positions = new int []{23,28,35};
                break;
            case 30:
                positions = new int []{24,31};
                break;
            case 31:
                positions = new int []{25,30,32};
                break;
            case 32:
                positions = new int []{26,31,33};
                break;
            case 33:
                positions = new int []{27,32,34};
                break;
            case 34:
                positions = new int []{28,33,35};
                break;
            default:
                positions = new int []{29,34};
                break;
        }
        return checkIsOkPosition(positions);
    }

    public boolean checkIsOkPosition(int[] positions) {
        for (int i = 0; i < positions.length; i++) {
            if(world.getBoard().getGapPositions() != positions[i]){
                if(world.getBoard().getColorButtonByTablePosition(positions[i]).getType() == getType() ||
                        isMulticolor() || world.getBoard().getColorButtonByTablePosition(positions[i]).isMulticolor()) {
                    return true;
                }
            }
        }
        return false;
    }
}
