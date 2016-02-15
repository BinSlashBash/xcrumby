package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class ImageViewContainer extends RelativeLayout {
    public ImageViewContainer(Context context) {
        super(context);
    }

    public ImageViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
