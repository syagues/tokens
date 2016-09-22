package com.forkstone.tokens.ui;

import com.forkstone.tokens.tweens.Value;
import com.forkstone.tokens.tweens.ValueAccessor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by sergi on 1/12/15.
 */
public class FancyNumbers {

    private Value number = new Value();
    TweenManager manager;
    public FancyNumbers() {

        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
    }

    public void update(float delta) {
        manager.update(delta);
    }

    public String getText() {
        return ((int) number.getValue()) + "";
    }

    public void start(float from, float to, float duration, float delay) {
        number.setValue(from);
        Tween.to(number, -1, duration).target(to).ease(TweenEquations.easeInOutSine).delay(delay).start(manager);
    }
}