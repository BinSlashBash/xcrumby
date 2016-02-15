/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.graphics.Paint
 *  android.view.View
 */
package com.crumby.lib.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Paint;
import android.view.View;

public class LayerEnablingAnimatorListener
extends AnimatorListenerAdapter {
    private int mLayerType;
    private final View mTargetView;

    public LayerEnablingAnimatorListener(View view) {
        this.mTargetView = view;
    }

    public View getTargetView() {
        return this.mTargetView;
    }

    public void onAnimationEnd(Animator animator) {
        super.onAnimationEnd(animator);
        this.mTargetView.setLayerType(this.mLayerType, null);
    }

    public void onAnimationStart(Animator animator) {
        super.onAnimationStart(animator);
        this.mLayerType = this.mTargetView.getLayerType();
        this.mTargetView.setLayerType(2, null);
    }
}

