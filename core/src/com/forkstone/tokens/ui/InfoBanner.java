package com.forkstone.tokens.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.utils.Align;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.GameObject;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;

/**
 * Created by sergi on 1/12/15.
 */
public class InfoBanner extends GameObject {

    private String bannerTitle = "";
    private String text = "0";
    private Color fontColor, smallFontColor;
    private int titlePosition; // 0 top, 1 bottom
    private GameObject dot;
    private Text titleText, scoreText;
    private float x, y, width, height;
    private GameWorld world;

    public InfoBanner(GameWorld world, float x, float y, float width, float height, TextureRegion texture, Color color, String bannerTitle,
                      BitmapFont titleFont, BitmapFont bannerFont, Color fontColor, Color titleFontColor, int titlePosition) {

        super(world, x, y-20, width, height-25, texture, color);
        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bannerTitle = bannerTitle; // Activar Title
        this.fontColor = fontColor;
        smallFontColor = world.parseColor(Settings.COLOR_WHITE, 1f);
        this.titlePosition = titlePosition;

        titleText = new Text(world, x, getTitlePosition(),
                width, 50,
                AssetLoader.square, world.parseColor(Settings.COLOR_BLACK, 0f), bannerTitle,
                titleFont, titleFontColor, 60, Align.center);

        if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            scoreText = new Text(world, x, y + height - 20,
                    width, 55,
                    AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), "",
                    bannerFont, fontColor, 60, Align.center);
        } else {
            scoreText = new Text(world, x, y + height - 52,
                    width, 50,
                    AssetLoader.square, world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f), "",
                    bannerFont, fontColor, 60, Align.center);
        }

        dot = new GameObject(world, x + width/2 - 50, y + height/2 - 50, 100, 100, AssetLoader.dot, Color.WHITE);
        dot.getSprite().setAlpha(0f);
    }

    public void update(float delta) {
        //getManager().update(delta);
        dot.update(delta);
        super.update(delta);
        titleText.update(delta);
        titleText.setPosition(getPosition().x, getTitlePosition());
        scoreText.update(delta);
        scoreText.setPosition(getPosition().x, getPosition().y + height - 50 + 5);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        dot.render(batch, shapeRenderer, fontShader, objectShader);
        super.render(batch, shapeRenderer, fontShader, objectShader);

        if(world.getGameOver() == null){
            titleText.render(batch, shapeRenderer, fontShader, objectShader);
            scoreText.render(batch, shapeRenderer, fontShader, objectShader);
        } else {
            if(!world.isGameOver()){
                titleText.render(batch, shapeRenderer, fontShader, objectShader);
                scoreText.render(batch, shapeRenderer, fontShader, objectShader);
            }
        }
    }

    public void setText(String text){
        scoreText.setText(text);
    }

    public void setBannerTitle(String title) {
        titleText.setText(title); // Activar Title
    }

    public void setTitleFontColor(Color fontColor){
        titleText.setFontColor(fontColor);
    }

    public void setScoreFontColor(Color fontColor) {
        this.fontColor = fontColor;
        scoreText.setFontColor(fontColor);
    }

    public Color getFontColor(){
        return fontColor;
    }

    public BitmapFont getFont() {
        return AssetLoader.fontB;
    }

    public float getTitlePosition(){
        if(AssetLoader.getDevice().equals(Settings.DEVICE_TABLET_43)){
            if(titlePosition == 0){
                return getPosition().y + height + 35;
            } else {
                return getPosition().y - 10;
            }
        } else {
            if(titlePosition == 0){
                return getPosition().y + height + 55;
            } else {
                return getPosition().y - 15;
            }
        }
    }

    public void effectWin(){
        dot.setColor(world.parseColor(Settings.COLOR_GREEN_500, .3f));
        dot.scale(0, 50, .3f, 0f);
        dot.fadeOut(1f, 0f, .3f);
    }

    public void effectLose(){
        dot.setColor(world.parseColor(Settings.COLOR_RED_500, .3f));
        dot.scale(0, 50, .3f, 0f);
        dot.fadeOut(1f, 0f, .3f);
    }

    public void effectMemoryActive(){
        dot.setColor(world.parseColor(Settings.COLOR_BLUEGREY_400, .3f));
        dot.scale(0, 50, .3f, 0f);
        dot.fadeOut(1f, 0f, .3f);
    }

    public void effectMemoryInactive(){
        dot.setColor(world.getLevelColor());
        dot.scale(0, 50, .3f, 0f);
        dot.fadeOut(1f, 0f, .3f);
    }

    public void effectChangeMovable(){
        dot.setColor(world.getLevelColor());
        dot.scale(0, 50, .3f, 0f);
        dot.fadeOut(1f, 0f, .3f);
    }

    public void effectsIn(){
        fadeIn(0.3f, 0f);
        setTitleFontColor(world.parseColor(Settings.COLOR_WHITE, 1f));
        setScoreFontColor(world.parseColor(Settings.COLOR_WHITE, 1f));
    }

    public void effectsOut(){
        fadeOut(0.3f, 0f);
        setTitleFontColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f));
        setScoreFontColor(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f));
    }
}
