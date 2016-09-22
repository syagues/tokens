package com.forkstone.tokens.levelworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameworld.GameCam;
import com.forkstone.tokens.helpers.AssetLoader;

/**
 * Created by sergi on 9/12/15.
 */
public class LevelRenderer {

    private final ShapeRenderer shapeRenderer;
    BitmapFont font = new BitmapFont();
    private LevelWorld world;
    private ShaderProgram fontShader, objectShader;
    // GAMEOBJECTS
    private Sprite backSprite, sprite;
    private GameCam camera;
    private SpriteBatch batch;
    private Texture texture;
    private float angle;

    public LevelRenderer(LevelWorld world, float gameWidth, float gameHeight) {
        this.world = world;
        sprite = new Sprite(AssetLoader.square);
        sprite.setPosition(0,0);
        sprite.setSize(world.worldWidth, world.worldHeight);
        camera = world.getCamera();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        initObjects();
        initFont();
    }

    private void initObjects() {
        objectShader = new ShaderProgram(Gdx.files.internal("misc/object.vert"), Gdx.files.internal("misc/object.frag"));
        if (!objectShader.isCompiled()) {
            Gdx.app.error("objectShader", "compilation failed:\n" + objectShader.getLog());
        }
    }

    private void initFont() {
        fontShader = new ShaderProgram(Gdx.files.internal("misc/font.vert"), Gdx.files.internal("misc/font.frag"));
        if (!fontShader.isCompiled()) {
            Gdx.app.error("fontShader", "compilation failed:\n" + fontShader.getLog());
        }
    }

    public void render(float delta, float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        batch.begin();
        camera.render(batch, shapeRenderer);
        world.render(batch, shapeRenderer, fontShader, objectShader);
        batch.end();

        //REMOVE THIS OUTSIDE DEBUGGING
        if (Settings.DEBUG) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.end();
        }
    }

    private boolean cameraInsideWorld() {
        Gdx.app.log("CameraPos", camera.getCamera().position.toString());
        return false;
    }
}
