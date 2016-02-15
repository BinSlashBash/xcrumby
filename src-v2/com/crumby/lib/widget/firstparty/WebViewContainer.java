/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.widget.FrameLayout
 */
package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class WebViewContainer
extends FrameLayout {
    public WebViewContainer(Context context) {
        super(context);
    }

    public WebViewContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WebViewContainer(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }
}

