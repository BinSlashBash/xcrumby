/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.widget.LinearLayout
 */
package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class GalleryMetadataView
extends LinearLayout {
    private int height;
    private int width;

    public GalleryMetadataView(Context context) {
        super(context);
    }

    public GalleryMetadataView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GalleryMetadataView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    protected void onMeasure(int n2, int n3) {
        super.onMeasure(n2, n3);
        if (this.width == 0) {
            this.width = this.getMeasuredWidth();
            this.height = this.getMeasuredHeight();
        }
        this.setMeasuredDimension(this.width, this.height);
        this.setRight(this.width);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }
}

