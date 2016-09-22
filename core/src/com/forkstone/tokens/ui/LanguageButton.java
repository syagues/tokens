package com.forkstone.tokens.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.GameObject;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;

/**
 * Created by sergi on 12/1/16.
 */
public class LanguageButton extends GameObject {

    private Sprite icon;
    private Color color;
    private Sprite backSprite;
    private float width, height;
    private TextureRegion texture;
    private boolean isActive;

    public LanguageButton(final GameWorld world, float x, float y, float width, float height, TextureRegion texture, Color color, boolean isActive) {

        super(world, x, y, width, height, texture, color);
        this.width = width;
        this.height = height;
        this.texture = texture;

        // Back Sprite for Menu Buttons
        backSprite = new Sprite(AssetLoader.dot);
        backSprite.setColor(world.parseColor(Settings.COLOR_BOARD, 1f));
        backSprite.setPosition(getSprite().getX(), getSprite().getY());
        backSprite.setSize(getSprite().getWidth(), getSprite().getHeight() - 10);
        backSprite.setScale(1.1f);
        backSprite.setAlpha(1f);
        backSprite.setOriginCenter();
        this.color = color;
        this.isActive = isActive;

        icon = new Sprite(AssetLoader.check);
        icon.setPosition(getPosition().x, getPosition().y + 3);
        icon.setSize(width, height);
        icon.setScale(0.5f, 0.5f);
        icon.setOriginCenter();
        if(isActive) icon.setAlpha(1f);
        else icon.setAlpha(0f);
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
        icon.draw(batch);
    }

    @Override
    public void update(float delta) {
        backSprite.setPosition(getSprite().getX(), getSprite().getY());
        super.update(delta);
        if(texture == AssetLoader.colorButton){
            if (isPressed) {
                icon.setPosition(getPosition().x, getPosition().y - 3);
            } else {
                icon.setPosition(getPosition().x, getPosition().y + 3);
            }
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

    public void setActive(){
        icon.setAlpha(1f);
        setColor(world.parseColor(Settings.COLOR_GREEN_500, 1f));
        isActive = true;
    }

    public void setInactive(){
        icon.setAlpha(0f);
        setColor(world.parseColor(Settings.COLOR_BOARD, 1f));
        isActive = false;
    }

    public boolean isActive(){
        return isActive;
    }
}
