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

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

/**
 * Created by sergi on 15/1/16.
 */
public class LifesBanner extends GameObject {

    private String text = "0";
    private Color fontColor;
    private Sprite heart;

    public LifesBanner(GameWorld world, float x, float y, float width, float height, TextureRegion texture, Color color, String text) {

        super(world, x, y, width, height, texture, color);
        fontColor = world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 1f);
        this.text = text;

        heart = new Sprite(AssetLoader.lifeButtonUp);
        if(text.length() == 1){
            heart.setPosition(x - 5, y - heart.getHeight()/4 - 5);
        } else {
            heart.setPosition(x + 5, y - heart.getHeight()/4 - 5);
        }
        heart.setScale(.4f, .4f);
        heart.setColor(world.parseColor(Settings.COLOR_RED_500, 1f));
        heart.setOriginCenter();

        Tween.to(heart, com.forkstone.tokens.tweens.SpriteAccessor.SCALE, .4f).target(0.1f)
                .target(.37f, .41f)
                .repeatYoyo(1000, 0f)
                .ease(TweenEquations.easeInOutSine).start(this.getManager());
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        super.render(batch, shapeRenderer, fontShader, objectShader);
        batch.setShader(fontShader);
        AssetLoader.fontLevelButton.setColor(fontColor);
        AssetLoader.fontLevelButton.draw(batch, text, getRectangle().x - 30,
                getRectangle().y + getRectangle().height - 23, getRectangle().width,
                Align.center, true);
        batch.setShader(null);
        heart.draw(batch);
    }

    public void setText(String text){
        this.text = text;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
        AssetLoader.fontLevelButton.setColor(fontColor);
    }

    public Color getFontColor(){
        return fontColor;
    }

    public BitmapFont getFont() {
        return AssetLoader.fontB;
    }
}
