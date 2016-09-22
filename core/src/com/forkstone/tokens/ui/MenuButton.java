package com.forkstone.tokens.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gameobjects.GameObject;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.screens.ChallengeScreen;
import com.forkstone.tokens.screens.CharacterScreen;
import com.forkstone.tokens.screens.GameScreen;
import com.forkstone.tokens.screens.LevelScreen;
import com.forkstone.tokens.screens.MedalScreen;
import com.forkstone.tokens.screens.MenuScreen;
import com.forkstone.tokens.screens.RandomScreen;
import com.forkstone.tokens.screens.SettingsScreen;
import com.forkstone.tokens.screens.ShopScreen;
import com.forkstone.tokens.screens.StoryScreen;
import com.forkstone.tokens.screens.SubmenuScreen;
import com.forkstone.tokens.screens.Tutorial2Screen;
import com.forkstone.tokens.screens.TutorialScreen;
import com.forkstone.tokens.tweens.Value;

/**
 * Created by sergi on 1/12/15.
 */
public class MenuButton extends GameObject {

    private static final String TAG = "MenuButton";

    private Sprite icon;
    private Color color;
    private Value time = new Value();
    private TweenCallback cbGameScreen, cbHomeScreen, cbLevelScreen, cbTutorialScreen, cbShopScreen,
            cbSettingsScreen, cbStoryScreen, cbMedalScreen, cbCharacterScreen, cbRandomScreen,
            cbSubmenuScreen, cbChallengeScreen, cbTutorial2Screen;
    private Sprite backSprite;
    private boolean showBackSprite;
    private float width, height;
    private int level, district, backScreen, storyPart, part;
    private String tutorialScreen;
    private TextureRegion texture;

    public MenuButton(final GameWorld world, float x, float y, float width, float height, TextureRegion texture,
                      Color color, TextureRegion buttonIcon, boolean showBackSprite, Color iconColor) {
        super(world, x, y, width, height, texture, color);

        icon = new Sprite(buttonIcon);
        icon.setPosition(getPosition().x, getPosition().y + 3);
        icon.setSize(width, height);
        icon.setScale(0.8f, 0.8f);
        icon.setOriginCenter();
        icon.setColor(iconColor);
        if(texture == AssetLoader.chronometer) icon.setAlpha(0f);

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
        this.showBackSprite = showBackSprite;

        cbGameScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new GameScreen(world.getGame(), world.actionResolver, level));
            }
        };

        cbHomeScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new MenuScreen(world.getGame(), world.actionResolver, false));
            }
        };

        cbLevelScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                Gdx.app.log(TAG, "world: " + world);
                world.getGame().setScreen(new LevelScreen(world.getGame(), world.actionResolver, 0));
            }
        };

        cbTutorialScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new TutorialScreen(world.getGame(), world.actionResolver, tutorialScreen, part));
            }
        };

        cbTutorial2Screen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new Tutorial2Screen(world.getGame(), world.actionResolver, tutorialScreen, part));
            }
        };

        cbShopScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new ShopScreen(world.getGame(), world.actionResolver, backScreen));
            }
        };

        cbSettingsScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new SettingsScreen(world.getGame(), world.actionResolver));
            }
        };

        cbStoryScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new StoryScreen(world.getGame(), world.actionResolver, storyPart));
            }
        };

        cbMedalScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new MedalScreen(world.getGame(), world.actionResolver, district));
            }
        };

        cbCharacterScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new CharacterScreen(world.getGame(), world.actionResolver, part));
            }
        };

        cbRandomScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new RandomScreen(world.getGame(), world.actionResolver, backScreen));
            }
        };

        cbSubmenuScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new SubmenuScreen(world.getGame(), world.actionResolver));
            }
        };

        cbChallengeScreen = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.getGame().setScreen(new ChallengeScreen(world.getGame(), world.actionResolver));
            }
        };
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader, ShaderProgram objectShader) {
        if(showBackSprite) backSprite.draw(batch);
        if(texture == AssetLoader.colorButton){
            if (isPressed) {
                getSprite().setRegion(AssetLoader.colorButton_pressed);
                getFlashSprite().setRegion(AssetLoader.colorButton_pressed);
            } else {
                getSprite().setRegion(AssetLoader.colorButton);
                getFlashSprite().setRegion(AssetLoader.colorButton);
            }
        }
        super.render(batch, shapeRenderer, fontShader, objectShader);
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

    public void setBackColor(Color color){
        backSprite.setColor(color);
    }

    public void effectsIn(){
        fadeIn(0.3f, 0f);
        icon.setAlpha(1f);
        backSprite.setAlpha(1f);
    }

    public void effectsOut(){
        fadeOut(0.3f, 0f);
        icon.setAlpha(0f);
        backSprite.setAlpha(0f);
    }

    public Sprite getIcon(){
        return icon;
    }

    public Sprite getBackSprite(){
        return backSprite;
    }

    public void toGameScreen(float duration, float delay, int level) {
        this.level = level;
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbGameScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toHomeScreen(float duration, float delay) {
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbHomeScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toLevelScreen(float duration, float delay, int district) {
        this.district = district;
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbLevelScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toTutorialScreen(float duration, float delay, String type, int part) {
        tutorialScreen = type;
        this.part = part;
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbTutorialScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toTutorial2Screen(float duration, float delay, String type, int part) {
        tutorialScreen = type;
        this.part = part;
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbTutorial2Screen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toShopScreen(float duration, float delay, int backScreen) {
        this.backScreen = backScreen;
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbShopScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toSettingsScreen(float duration, float delay) {
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbSettingsScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toStoryScreen(float duration, float delay, int storyPart) {
        this.storyPart = storyPart;
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbStoryScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toMedalScreen(float duration, float delay, int district) {
        this.district = district;
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbMedalScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toCharacterScreen(float duration, float delay, int part) {
        this.part = part;
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbCharacterScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toRandomScreen(float duration, float delay, int backScreen) {
        this.backScreen = backScreen;
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbRandomScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toSubmenuScreen(float duration, float delay){
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbSubmenuScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }

    public void toChallengeScreen(float duration, float delay){
        Tween.to(time, -1, duration).target(1).delay(delay).setCallback(cbChallengeScreen)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());
    }
}
