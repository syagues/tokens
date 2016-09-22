package com.forkstone.tokens.ui;

import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.tweens.Value;
import com.forkstone.tokens.tweens.ValueAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by sergi on 19/12/15.
 */
public class Timer {

    Tween timerTween;
    Value time = new Value();
    TweenManager manager;
    private TweenCallback cbFinish;
    private TweenCallback cbRestart;
    private GameWorld world;
    private float duration;

    public Timer(final GameWorld world, final boolean countdown) {
        this.world = world;
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        cbFinish = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                //world.getCountBanner().flash(0.2f,0f);
                if(!countdown) world.finishGame(false);
            }
        };


    }

    public void update(float delta) {
        manager.update(delta);
    }

    public void finish() {
        if (timerTween != null) {
            timerTween.pause();
        }
    }

    public void start(float from, float to, float duration, float delay) {
        finish();
        this.duration = from;
        time.setValue(from);
        timerTween = Tween.to(time, -1, duration).target(to).setCallback(cbFinish)
                .setCallbackTriggers(TweenCallback.COMPLETE).ease(TweenEquations.easeNone)
                .delay(delay).start(manager);
    }

    public float getTime() {
        return time.getValue();
    }

    public String getTimeFormatted() {
        //String text = String.format("%.01f", time.getValue());
        String text = String.format("%.0f", time.getValue());
        return text;
    }

    public float getDuration(){
        return duration;
    }

    public void restart() {
        finish();
        timerTween = Tween.to(time, -1, 0.3f).target(duration)
                .setCallback(cbRestart)
                .setCallbackTriggers(TweenCallback.COMPLETE).ease(TweenEquations.easeNone)
                .start(manager);
    }

    public void addTime(float seconds) {
        if (world.isRunning()) {
            finish();
            timerTween = Tween.to(time, -1, 0.2f).target(time.getValue() + seconds)
                    .setCallback(cbRestartMethod(time.getValue() + seconds))
                    .setCallbackTriggers(TweenCallback.COMPLETE).ease(TweenEquations.easeNone)
                    .start(manager);
        }
    }

    private TweenCallback cbRestartMethod(final float v) {
        cbRestart = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                start(v, 0, v, 0.1f);
            }
        };
        return cbRestart;
    }
}
