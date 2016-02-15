/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.widget.RelativeLayout
 */
package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class ImageViewContainer
extends RelativeLayout {
    public ImageViewContainer(Context context) {
        super(context);
    }

    public ImageViewContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ImageViewContainer(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }
}

