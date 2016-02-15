package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class GalleryMetadataView extends LinearLayout {
    private int height;
    private int width;

    public GalleryMetadataView(Context context) {
        super(context);
    }

    public GalleryMetadataView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryMetadataView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.width == 0) {
            this.width = getMeasuredWidth();
            this.height = getMeasuredHeight();
        }
        setMeasuredDimension(this.width, this.height);
        setRight(this.width);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
