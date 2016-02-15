package com.crumby.lib.widget.thirdparty;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ResizeHeightAnimation extends Animation {
    int diff;
    int endH;
    int startH;
    View view;

    public ResizeHeightAnimation(View v, int newh) {
        this.view = v;
        this.startH = v.getLayoutParams().height;
        this.endH = newh;
        this.diff = this.endH - this.startH;
    }

    protected View getView() {
        return this.view;
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
        this.view.getLayoutParams().height = this.startH + ((int) (((float) this.diff) * interpolatedTime));
        this.view.requestLayout();
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    public boolean willChangeBounds() {
        return true;
    }
}
