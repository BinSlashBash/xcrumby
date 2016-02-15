/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 */
package com.crumby.lib.widget.thirdparty;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ResizeHeightAnimation
extends Animation {
    int diff;
    int endH;
    int startH;
    View view;

    public ResizeHeightAnimation(View view, int n2) {
        this.view = view;
        this.startH = view.getLayoutParams().height;
        this.endH = n2;
        this.diff = this.endH - this.startH;
    }

    protected void applyTransformation(float f2, Transformation transformation) {
        this.view.getLayoutParams().height = this.startH + (int)((float)this.diff * f2);
        this.view.requestLayout();
    }

    protected View getView() {
        return this.view;
    }

    public void initialize(int n2, int n3, int n4, int n5) {
        super.initialize(n2, n3, n4, n5);
    }

    public boolean willChangeBounds() {
        return true;
    }
}

