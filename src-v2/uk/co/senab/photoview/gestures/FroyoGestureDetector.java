/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.view.MotionEvent
 *  android.view.ScaleGestureDetector
 *  android.view.ScaleGestureDetector$OnScaleGestureListener
 */
package uk.co.senab.photoview.gestures;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import uk.co.senab.photoview.gestures.EclairGestureDetector;
import uk.co.senab.photoview.gestures.OnGestureListener;

@TargetApi(value=8)
public class FroyoGestureDetector
extends EclairGestureDetector {
    protected final ScaleGestureDetector mDetector;

    public FroyoGestureDetector(Context context) {
        super(context);
        this.mDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener(){

            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                float f2 = scaleGestureDetector.getScaleFactor();
                if (Float.isNaN(f2) || Float.isInfinite(f2)) {
                    return false;
                }
                FroyoGestureDetector.this.mListener.onScale(f2, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                return true;
            }

            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                return true;
            }

            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            }
        });
    }

    @Override
    public boolean isScaling() {
        return this.mDetector.isInProgress();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mDetector.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

}

