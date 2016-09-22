package com.forkstone.tokens.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;

/**
 * Created by sergi on 24/12/15.
 */
public class Counter extends GameObject {
    private final BitmapFont font;
    private Color fontColor;
    private float distance;
    private String text, helpName;
    private int halign;
    private boolean fromGameScreen;

    public Counter(GameWorld world, float x, float y, float width, float height, BitmapFont font, Color fontColor, String helpName, boolean fromGameScreen, Color color, float distance) {

        super(world, x, y, width, height, AssetLoader.dot, color);
        this.font = font;
        this.fontColor = fontColor;
        this.distance = distance;
//        if(font == AssetLoader.fontXS) this.distance = 16;
//        else if(font == AssetLoader.fontS) this.distance = 23;
//        else this.distance = 10;
        this.halign = Align.center;
        this.text = getTextByHelpName(helpName);
        this.helpName = helpName;
        this.fromGameScreen = fromGameScreen;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        text = getTextByHelpName(helpName);
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontshader, ShaderProgram objectShader) {
        if (getTexture() != AssetLoader.transparent) {
            super.render(batch, shapeRenderer, fontshader, objectShader);
        }
        if(world.isRunning() || !fromGameScreen || world.isTutorial()){
            batch.setShader(fontshader);
            font.setColor(fontColor);
            font.draw(batch, text, getRectangle().x + 40,
                    getRectangle().y + getRectangle().height - distance, getRectangle().width - 80,
                    halign, true);
            font.setColor(fontColor);
            batch.setShader(null);
        }
    }

    public void setHelpName(String helpName){
        this.helpName = helpName;
    }

    public String getTextByHelpName(String helpName){
        if(helpName.equals("moreMoves")) {
            return Integer.toString(AssetLoader.getMoreMoves());
        } else if(helpName.equals("moreTime")) {
            return Integer.toString(AssetLoader.getMoreTime());
        } else if(helpName.equals("resol")) {
            return Integer.toString(AssetLoader.getResol());
        } else if(helpName.equals("multicolor")) {
            return Integer.toString(AssetLoader.getMulticolor());
        } else if(helpName.equals("moreMovesShop")) {
            return Integer.toString(Settings.SHOP_MOREMOVES_PACK);
        } else if(helpName.equals("moreTimeShop")) {
            return Integer.toString(Settings.SHOP_MORETIME_PACK);
        } else if(helpName.equals("multicolorShop")) {
            return Integer.toString(Settings.SHOP_MULTICOLOR_PACK);
        } else if(helpName.equals("resolShop")) {
            return Integer.toString(Settings.SHOP_RESOL_PACK);
        } else if(helpName.equals("memoryCountdown")) {
            return Integer.toString(world.getMemoryCountdown());
        } else if(helpName.equals("movableCountdown")) {
            return Integer.toString(world.getMovableCountdown());
        } else if(helpName.equals("colorChangeCountdown")) {
            return Integer.toString(world.getColorChangeCountdown());
        }
        return "";
    }

    public void effectsIn() {
        fadeIn(0.3f, 0f);
        fontColor.set(world.parseColor(Settings.COLOR_WHITE, 1f));
    }

    public void effectsOut(){
        fadeOut(0.3f, 0f);
        fontColor.set(world.parseColor(Settings.COLOR_BACKGROUND_COLOR, 0f));
    }
}
