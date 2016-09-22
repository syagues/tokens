package com.forkstone.tokens.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.forkstone.tokens.gameworld.GameWorld;

/**
 * Created by sergi on 1/12/15.
 */
public class Background extends GameObject {
    public Background(GameWorld world, float x, float y, float width, float height,
                      TextureRegion texture, Color color) {
        super(world, x, y, width, height, texture, color);
    }
}
