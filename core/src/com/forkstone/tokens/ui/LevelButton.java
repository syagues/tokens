package com.forkstone.tokens.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.GameObject;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.screens.GameScreen;
import com.forkstone.tokens.screens.LevelScreen;
import com.forkstone.tokens.screens.MenuScreen;
import com.forkstone.tokens.tweens.Value;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by sergi on 11/12/15.
 */
public class LevelButton extends GameObject {

    private Sprite icon;
    private Color color;
    private Value time = new Value();
    private TweenCallback cbGameScreen, cbHomeScreen, cbLevelScreen;
    private Sprite backSprite;
    private float width, height;
    private int level, gameLevel;
    private Text numberLevel;
    private int district;

    public LevelButton(final GameWorld world, float x, float y, float width, float height, TextureRegion texture, Color color, int gameLevel) {

        super(world, x, y, width, height, texture, color);
        icon = new Sprite(AssetLoader.lockButton);
        icon.setPosition(getPosition().x, getPosition().y + 3);
        icon.setSize(width, height);
        icon.setScale(0.8f, 0.8f);
        icon.setOriginCenter();
        this.width = width;
        this.height = height;

        // Back Sprite for Menu Buttons
        backSprite = new Sprite(AssetLoader.dot);
        backSprite.setColor(world.parseColor(Settings.COLOR_BOARD, 1f));
        backSprite.setPosition(getSprite().getX(), getSprite().getY());
        backSprite.setSize(getSprite().getWidth(), getSprite().getHeight() - 10);
        backSprite.setScale(1.1f);
        backSprite.setAlpha(1f);
        backSprite.setOriginCenter();
        this.color = color;
        this.gameLevel = gameLevel;

        if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            if(gameLevel < 100) {
                numberLevel = new Text(world, x - 15, y - 11,
                        width + 30, height, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                        Integer.toString(gameLevel), AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f), 30,
                        Align.center);
            } else {
                numberLevel = new Text(world, x - 30, y - 11,
                        width + 60, height, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                        Integer.toString(gameLevel), AssetLoader.fontXXS, world.parseColor(Settings.COLOR_WHITE, 1f), 30,
                        Align.center);
            }
        } else {
            if(gameLevel < 100) {
                numberLevel = new Text(world, x - 15, y - 16,
                        width + 30, height, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                        Integer.toString(gameLevel), AssetLoader.fontLevelButton, world.parseColor(Settings.COLOR_WHITE, 1f), 30,
                        Align.center);
            } else {
                numberLevel = new Text(world, x - 31, y - 16,
                        width + 60, height, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                        Integer.toString(gameLevel), AssetLoader.fontLevelButton, world.parseColor(Settings.COLOR_WHITE, 1f), 30,
                        Align.center);
            }
        }

        cbGameScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new GameScreen(world.getGame(), world.actionResolver, level));
            }
        };

        cbHomeScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new MenuScreen(world.getGame(), world.actionResolver, false));
            }
        };

        cbLevelScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new LevelScreen(world.getGame(), world.actionResolver, district));
            }
        };
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        backSprite.draw(batch);
        super.render(batch, shapeRenderer, fontShader, objectShader);
        if (isPressed) {
            getSprite().setRegion(AssetLoader.colorButton_pressed);
            getFlashSprite().setRegion(AssetLoader.colorButton_pressed);
        } else {
            getSprite().setRegion(AssetLoader.colorButton);
            getFlashSprite().setRegion(AssetLoader.colorButton);
        }
        if(gameLevel == -1) icon.draw(batch);
        else numberLevel.render(batch, shapeRenderer, fontShader, objectShader);
    }

    @Override
    public void update(float delta) {
        backSprite.setPosition(getSprite().getX(), getSprite().getY());
        numberLevel.update(delta);
        super.update(delta);
        if (isPressed) {
            icon.setPosition(getPosition().x, getPosition().y - 3);
        } else {
            icon.setPosition(getPosition().x, getPosition().y + 3);
        }
    }

    @Override
    public boolean isTouchDown(int screenX, int screenY) {
        // Click animation
        if(gameLevel != -1) return super.isTouchDown(screenX, screenY);
        else return false;
    }

    @Override
    public boolean isTouchUp(int screenX, int screenY) {
        if(gameLevel != -1) return super.isTouchUp(screenX, screenY);
        else return false;
    }

    public void setIcon(TextureRegion buttonIcon) {
        icon = new Sprite(buttonIcon);
        icon.setPosition(getPosition().x, getPosition().y + 3);
        icon.setSize(width, height);
        icon.setScale(0.8f, 0.8f);
        icon.setOriginCenter();
    }

    public int getGameLevel(){
        return gameLevel;
    }

    public void toGameScreen(float duration, float delay, int level) {
        this.level = level;
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbGameScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toHomeScreen(float duration, float delay) {
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbHomeScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toLevelScreen(float duration, float delay, int district) {
        this.district = district;
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbLevelScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }
}
