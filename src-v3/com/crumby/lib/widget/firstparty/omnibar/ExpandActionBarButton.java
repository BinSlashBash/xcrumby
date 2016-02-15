package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

public class ExpandActionBarButton extends ImageButton {
    private int startY;

    public ExpandActionBarButton(Context context) {
        super(context);
    }

    public ExpandActionBarButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandActionBarButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TranslateAnimation getTranslateAnimation(int starty, int endy) {
        TranslateAnimation anim = new TranslateAnimation(0.0f, 0.0f, (float) starty, (float) endy);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(200);
        return anim;
    }

    public void show() {
        setAnimation(getTranslateAnimation(-getBottom(), 0));
        setVisibility(0);
        bringToFront();
    }

    public void hide() {
        setAnimation(getTranslateAnimation(0, -getBottom()));
        setVisibility(4);
    }
}
