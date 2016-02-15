/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.graphics.Paint
 *  android.view.View
 */
package android.support.v4.view;

import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.view.View;

class ViewCompatHC {
    ViewCompatHC() {
    }

    public static float getAlpha(View view) {
        return view.getAlpha();
    }

    static long getFrameTime() {
        return ValueAnimator.getFrameDelay();
    }

    public static int getLayerType(View view) {
        return view.getLayerType();
    }

    public static int getMeasuredHeightAndState(View view) {
        return view.getMeasuredHeightAndState();
    }

    public static int getMeasuredState(View view) {
        return view.getMeasuredState();
    }

    public static int getMeasuredWidthAndState(View view) {
        return view.getMeasuredWidthAndState();
    }

    public static int resolveSizeAndState(int n2, int n3, int n4) {
        return View.resolveSizeAndState((int)n2, (int)n3, (int)n4);
    }

    public static void setLayerType(View view, int n2, Paint paint) {
        view.setLayerType(n2, paint);
    }
}

