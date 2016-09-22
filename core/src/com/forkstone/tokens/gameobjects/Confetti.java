package com.forkstone.tokens.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.helpers.FlatColors;
import com.forkstone.tokens.tweens.SpriteAccessor;


import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by sergi on 22/1/16.
 */
public class Confetti {

    private Vector2 position, velocity;
    private Sprite sprite;

    private GameWorld world;
    private int type;
    private float angle, angleInc;
    private TweenManager manager;

    public Confetti(GameWorld world) {
        this.world = world;
        sprite = new Sprite(AssetLoader.square);
        sprite.setRotation((float) (Math.random() * 2));
        float size = (float) Math.random() * 6 + 10;
        sprite.setSize(size, size*2);
//        sprite.setPosition((float) Math.random() * world.worldWidth, (float) Math.random() * (world.worldHeight));
        sprite.setPosition((float) Math.random() * world.worldWidth, world.worldHeight);
        position = new Vector2(sprite.getX(), sprite.getY());
        velocity = new Vector2(MathUtils.random(20, 60), MathUtils.random(100, 800));
        if (Math.random() < 0.5f) {
            velocity.x = velocity.x * -1;
        }
        if (Math.random() < 0.5f) {
            velocity.y = velocity.y * -1;
        }
//        type = Math.random() < 0.5f ? 1 : 0;
        sprite.setColor(setConfettiColor());
//        sprite.setColor(FlatColors.WHITE);
        sprite.setAlpha(1f);
        manager = new TweenManager();
//        angleInc = MathUtils.random(-100, 100);
        angleInc = MathUtils.random(-100, -80);
        sprite.setRotation(angleInc+90);

    }

    public void update(float delta) {
        angle += angleInc * delta;

        manager.update(delta);
        position.add(velocity.cpy().scl(delta));
        sprite.setPosition(position.x, position.y);
        if (sprite.getX() < -100 || sprite.getX() > world.worldWidth + 100) {
            velocity.x = velocity.x * -1;
        }
        if (sprite.getY() < -100 || sprite.getY() > world.worldHeight + 100) {
//            velocity.y = velocity.y * -1;
            resetPosition();
        }
//        sprite.setRotation(angle);

        sprite.setOriginCenter();
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader) {
        sprite.draw(batch);
    }

    private Color setConfettiColor(){

        float colorNum = (float) Math.random();
        if(colorNum < .1f && colorNum > 0){
            return world.parseColor(Settings.COLOR_RED_500, 1f);
        } else if(colorNum < .2f && colorNum > .1f){
            return world.parseColor(Settings.COLOR_PINK_500, 1f);
        } else if(colorNum < .3f && colorNum > .2f){
            return world.parseColor(Settings.COLOR_PURPLE_500, 1f);
        } else if(colorNum < .4f && colorNum > .3f){
            return world.parseColor(Settings.COLOR_BLUE_500, 1f);
        } else if(colorNum < .5f && colorNum > .4f){
            return world.parseColor(Settings.COLOR_CYAN_500, 1f);
        } else if(colorNum < .6f && colorNum > .5f){
            return world.parseColor(Settings.COLOR_TEAL_500, 1f);
        } else if(colorNum < .7f && colorNum > .6f){
            return world.parseColor(Settings.COLOR_LIGHTGREEN_500, 1f);
        } else if(colorNum < .8f && colorNum > .7f){
            return world.parseColor(Settings.COLOR_LIME_500, 1f);
        } else if(colorNum < .9f && colorNum > .8f){
            return world.parseColor(Settings.COLOR_YELLOW_500, 1f);
        } else {
            return world.parseColor(Settings.COLOR_ORANGE_500, 1f);
        }
    }

    private void resetPosition() {
       position.set((float) Math.random() * world.worldWidth, world.gameHeight + 30);
    }

    public void scale(float from, float duration, float delay) {
        sprite.setScale(from);
        Tween.to(sprite, SpriteAccessor.SCALE, duration).target(1).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(manager);
    }
}
