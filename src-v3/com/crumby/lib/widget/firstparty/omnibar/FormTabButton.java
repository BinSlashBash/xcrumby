package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import uk.co.senab.photoview.IPhotoView;

public class FormTabButton extends ImageButton {
    private View view;

    public FormTabButton(Context context) {
        super(context);
    }

    public FormTabButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FormTabButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setView(View view) {
        this.view = view;
    }

    public void hide() {
        setVisibility(8);
        if (this.view != null) {
            this.view.setVisibility(8);
        }
    }

    public void deactivate() {
        getBackground().setAlpha(0);
        getDrawable().setAlpha(IPhotoView.DEFAULT_ZOOM_DURATION);
        if (this.view != null) {
            this.view.setVisibility(8);
        }
    }

    public void initialize(TabManager tabManager) {
        setOnClickListener(tabManager);
        setImageDrawable(getDrawable().mutate());
        setBackgroundDrawable(getBackground().mutate());
    }

    public void activate() {
        getBackground().setAlpha(MotionEventCompat.ACTION_MASK);
        getDrawable().setAlpha(MotionEventCompat.ACTION_MASK);
        this.view.setVisibility(0);
    }
}
