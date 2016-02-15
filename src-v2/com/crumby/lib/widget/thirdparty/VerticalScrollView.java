/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 */
package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.crumby.lib.widget.thirdparty.ObservableScrollView;

public class VerticalScrollView
extends ObservableScrollView {
    private float lastX;
    private float lastY;
    private Runnable runnable;
    private float xDistance;
    private float yDistance;

    public VerticalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void loadInitial(Runnable runnable) {
        this.runnable = runnable;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0: {
                this.yDistance = 0.0f;
                this.xDistance = 0.0f;
                this.lastX = motionEvent.getX();
                this.lastY = motionEvent.getY();
            }
            default: {
                return super.onInterceptTouchEvent(motionEvent);
            }
            case 2: {
                float f2 = motionEvent.getX();
                float f3 = motionEvent.getY();
                this.xDistance += Math.abs(f2 - this.lastX);
                this.yDistance += Math.abs(f3 - this.lastY);
                this.lastX = f2;
                this.lastY = f3;
                if (this.xDistance <= this.yDistance) return super.onInterceptTouchEvent(motionEvent);
                return false;
            }
        }
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        super.onLayout(bl2, n2, n3, n4, n5);
        if (this.runnable != null) {
            this.runnable.run();
            this.runnable = null;
        }
    }

    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
    }
}

