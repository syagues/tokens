package com.forkstone.tokens.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.forkstone.tokens.gameobjects.GameObject;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;

/**
 * Created by sergi on 1/12/15.
 */
public class Text extends GameObject {
    private final BitmapFont font;
    private final Color fontColor;
    private final float distance;
    private String text;
    private int halign;

    public Text(GameWorld world, float x, float y, float width, float height,
                TextureRegion texture, Color color, String text, BitmapFont font, Color fontColor,
                float distance, int halign) {
        super(world, x, y, width, height, texture, color);
        this.font = font;
        this.text = text;
        this.fontColor = fontColor;
        this.distance = distance;
        this.halign = halign;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontshader, ShaderProgram objectShader) {
        if (getTexture() != AssetLoader.transparent) {
            super.render(batch, shapeRenderer, fontshader, objectShader);
        }
        batch.setShader(fontshader);
        font.setColor(fontColor);
        font.draw(batch, text, getRectangle().x + 40,
                getRectangle().y + getRectangle().height - distance, getRectangle().width - 80,
                halign, true);
        font.setColor(Color.WHITE);
        batch.setShader(null);
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

    public void setText(String text) {
        this.text = text;
    }

    public BitmapFont getFont(){
        return font;
    }

    public void setFontColor(Color color){
        fontColor.set(color);
    }

}
