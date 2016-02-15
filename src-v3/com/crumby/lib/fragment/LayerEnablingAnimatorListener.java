package com.crumby.lib.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class LayerEnablingAnimatorListener extends AnimatorListenerAdapter {
    private int mLayerType;
    private final View mTargetView;

    public LayerEnablingAnimatorListener(View targetView) {
        this.mTargetView = targetView;
    }

    public View getTargetView() {
        return this.mTargetView;
    }

    public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);
        this.mLayerType = this.mTargetView.getLayerType();
        this.mTargetView.setLayerType(2, null);
    }

    public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        this.mTargetView.setLayerType(this.mLayerType, null);
    }
}
