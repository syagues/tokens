package com.forkstone.tokens.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.tweens.SpriteAccessor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by sergi on 17/3/16.
 */
public class MapLights {

    private Vector2 position;
    private Sprite sprite;

    private GameWorld world;
    private TweenManager manager;

    public MapLights(GameWorld world) {
        this.world = world;
        sprite = new Sprite(AssetLoader.dot1);
        float size = (float) Math.random() * 6;
        sprite.setSize(size, size);
        sprite.setPosition((float) Math.random() * world.worldWidth, (float) Math.random() * world.worldHeight);
        position = new Vector2(sprite.getX(), sprite.getY());
        sprite.setColor(world.parseColor(Settings.COLOR_YELLOW_500, 1f));
        sprite.setAlpha(.5f);
        manager = new TweenManager();

        fadeOut(2f, (float) Math.random() * 40f, 0.5f);
    }

    public void update(float delta) {
        manager.update(delta);
        sprite.setPosition(position.x, position.y);

        sprite.setOriginCenter();
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader) {
        sprite.draw(batch);
    }

    public void scale(float from, float to, float duration, float delay) {
        sprite.setScale(from);
        Tween.to(sprite, SpriteAccessor.SCALE, duration).target(to).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(manager);
    }

    public void fadeOut(float duration, float delay, float alpha) {
        sprite.setAlpha(alpha);
        Tween.to(sprite, SpriteAccessor.ALPHA, duration).target(0).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(manager);
    }
}
