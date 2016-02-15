/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.MotionEvent
 *  android.widget.RelativeLayout
 */
package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class TapAwareRelativeLayout
extends RelativeLayout {
    private final float MOVE_THRESHOLD_DP;
    private float mDownPosX;
    private float mDownPosY;
    private boolean mMoveOccured;

    public TapAwareRelativeLayout(Context context) {
        super(context);
        this.MOVE_THRESHOLD_DP = 20.0f * this.getResources().getDisplayMetrics().density;
    }

    public TapAwareRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.MOVE_THRESHOLD_DP = 20.0f * this.getResources().getDisplayMetrics().density;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onInterceptTouchEvent(MotionEvent var1_1) {
        switch (var1_1.getAction()) {
            case 0: {
                this.mMoveOccured = false;
                this.mDownPosX = var1_1.getX();
                this.mDownPosY = var1_1.getY();
                ** break;
            }
            case 1: {
                if (this.mMoveOccured != false) return super.onInterceptTouchEvent(var1_1);
                this.performClick();
            }
lbl10: // 3 sources:
            default: {
                return super.onInterceptTouchEvent(var1_1);
            }
            case 2: 
        }
        if (Math.abs(var1_1.getX() - this.mDownPosX) <= this.MOVE_THRESHOLD_DP) {
            if (Math.abs(var1_1.getY() - this.mDownPosY) <= this.MOVE_THRESHOLD_DP) return super.onInterceptTouchEvent(var1_1);
        }
        this.mMoveOccured = true;
        return super.onInterceptTouchEvent(var1_1);
    }
}

