/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.animation.Animation
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.TranslateAnimation
 *  android.widget.ImageButton
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

public class ExpandActionBarButton
extends ImageButton {
    private int startY;

    public ExpandActionBarButton(Context context) {
        super(context);
    }

    public ExpandActionBarButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ExpandActionBarButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public TranslateAnimation getTranslateAnimation(int n2, int n3) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (float)n2, (float)n3);
        translateAnimation.setInterpolator((Interpolator)new DecelerateInterpolator());
        translateAnimation.setDuration(200);
        return translateAnimation;
    }

    public void hide() {
        this.setAnimation((Animation)this.getTranslateAnimation(0, - this.getBottom()));
        this.setVisibility(4);
    }

    public void show() {
        this.setAnimation((Animation)this.getTranslateAnimation(- this.getBottom(), 0));
        this.setVisibility(0);
        this.bringToFront();
    }
}

