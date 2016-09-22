package com.forkstone.tokens.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.forkstone.tokens.tweens.Value;

import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by sergi on 11/1/16.
 */
public class PriceButton extends GameObject {

    private Sprite icon;
    private Color color;
    private Value time = new Value();
    private TweenCallback cbGameScreen, cbHomeScreen, cbLevelScreen;
    private Sprite backSprite;
    private float width, height;
    private int level, gameLevel;
    private Text priceText;
    private BitmapFont font;

    public PriceButton(final GameWorld world, float x, float y, float width, float height, TextureRegion texture, Color color, BitmapFont font, String price) {

        super(world, x, y, width, height, texture, color);
        this.width = width;
        this.height = height;
        this.font = font;

        // Back Sprite for Menu Buttons
        backSprite = new Sprite(AssetLoader.dot);
        backSprite.setColor(world.parseColor(Settings.COLOR_BOARD, 1f));
        backSprite.setPosition(getSprite().getX(), getSprite().getY());
        backSprite.setSize(getSprite().getWidth(), getSprite().getHeight() - 10);
        backSprite.setScale(1.1f);
        backSprite.setAlpha(1f);
        backSprite.setOriginCenter();
        this.color = color;

        icon = new Sprite(AssetLoader.videoButtonUp);
        icon.setPosition(getPosition().x, getPosition().y + 3);
        icon.setSize(width, height);
        icon.setScale(0.8f, 0.8f);
        icon.setOriginCenter();
        icon.setAlpha(0f);

        if(!price.equals("0")){
            priceText = new Text(world, x - 25, y - 35,
                    width + 50, height, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                    price, font, world.parseColor(Settings.COLOR_WHITE, 1f), 30,
                    Align.center);
        } else {
            priceText = new Text(world, x - 25, y - 35,
                    width + 50, height, AssetLoader.square, world.parseColor(Settings.COLOR_WHITE, 0f),
                    "", font, world.parseColor(Settings.COLOR_WHITE, 1f), 30,
                    Align.center);
            icon.setAlpha(1f);
        }
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
        priceText.render(batch, shapeRenderer, fontShader, objectShader);
        icon.draw(batch);
    }

    @Override
    public void update(float delta) {
        backSprite.setPosition(getSprite().getX(), getSprite().getY());
        priceText.update(delta);
        super.update(delta);
        if (isPressed) {
            if(font == AssetLoader.fontPrice){priceText.setPosition(getPosition().x - 25, getPosition().y - 41);}
            else if(font == AssetLoader.fontPriceS) {priceText.setPosition(getPosition().x - 25, getPosition().y - 28);}
            else if(font == AssetLoader.fontPriceXS) {priceText.setPosition(getPosition().x - 25, getPosition().y - 16);}
            icon.setPosition(getPosition().x, getPosition().y - 3);
        } else {
            if(font == AssetLoader.fontPrice){priceText.setPosition(getPosition().x - 25, getPosition().y - 35);}
            else if(font == AssetLoader.fontPriceS) {priceText.setPosition(getPosition().x - 25, getPosition().y - 22);}
            else if(font == AssetLoader.fontPriceXS) {priceText.setPosition(getPosition().x - 25, getPosition().y - 10);}
            icon.setPosition(getPosition().x, getPosition().y + 3);
        }
    }

    @Override
    public boolean isTouchDown(int screenX, int screenY) {
        // Click animation
        return super.isTouchDown(screenX, screenY);
    }

    @Override
    public boolean isTouchUp(int screenX, int screenY) {
        return super.isTouchUp(screenX, screenY);
    }

    public void setIcon(TextureRegion buttonIcon) {
        icon = new Sprite(buttonIcon);
        icon.setPosition(getPosition().x, getPosition().y + 3);
        icon.setSize(width, height);
        icon.setScale(0.8f, 0.8f);
        icon.setOriginCenter();
    }

    public void setIconColor(Color color){
        icon.setColor(color);
    }
}
