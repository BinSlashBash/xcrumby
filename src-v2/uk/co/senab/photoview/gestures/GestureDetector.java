/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 */
package uk.co.senab.photoview.gestures;

import android.view.MotionEvent;
import uk.co.senab.photoview.gestures.OnGestureListener;

public interface GestureDetector {
    public boolean isScaling();

    public boolean onTouchEvent(MotionEvent var1);

    public void setOnGestureListener(OnGestureListener var1);
}

