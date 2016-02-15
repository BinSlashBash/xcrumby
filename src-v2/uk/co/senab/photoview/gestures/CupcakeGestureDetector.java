/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.FloatMath
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.ViewConfiguration
 */
package uk.co.senab.photoview.gestures;

import android.content.Context;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import uk.co.senab.photoview.gestures.GestureDetector;
import uk.co.senab.photoview.gestures.OnGestureListener;

public class CupcakeGestureDetector
implements GestureDetector {
    private static final String LOG_TAG = "CupcakeGestureDetector";
    private boolean mIsDragging;
    float mLastTouchX;
    float mLastTouchY;
    protected OnGestureListener mListener;
    final float mMinimumVelocity;
    final float mTouchSlop;
    private VelocityTracker mVelocityTracker;

    public CupcakeGestureDetector(Context context) {
        context = ViewConfiguration.get((Context)context);
        this.mMinimumVelocity = context.getScaledMinimumFlingVelocity();
        this.mTouchSlop = context.getScaledTouchSlop();
    }

    float getActiveX(MotionEvent motionEvent) {
        return motionEvent.getX();
    }

    float getActiveY(MotionEvent motionEvent) {
        return motionEvent.getY();
    }

    @Override
    public boolean isScaling() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean bl2 = false;
        switch (motionEvent.getAction()) {
            case 0: {
                this.mVelocityTracker = VelocityTracker.obtain();
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.addMovement(motionEvent);
                } else {
                    Log.i((String)"CupcakeGestureDetector", (String)"Velocity tracker is null");
                }
                this.mLastTouchX = this.getActiveX(motionEvent);
                this.mLastTouchY = this.getActiveY(motionEvent);
                this.mIsDragging = false;
                return true;
            }
            case 2: {
                float f2 = this.getActiveX(motionEvent);
                float f3 = this.getActiveY(motionEvent);
                float f4 = f2 - this.mLastTouchX;
                float f5 = f3 - this.mLastTouchY;
                if (!this.mIsDragging) {
                    if (FloatMath.sqrt((float)(f4 * f4 + f5 * f5)) >= this.mTouchSlop) {
                        bl2 = true;
                    }
                    this.mIsDragging = bl2;
                }
                if (!this.mIsDragging) return true;
                {
                    this.mListener.onDrag(f4, f5);
                    this.mLastTouchX = f2;
                    this.mLastTouchY = f3;
                    if (this.mVelocityTracker == null) return true;
                    {
                        this.mVelocityTracker.addMovement(motionEvent);
                        return true;
                    }
                }
            }
            case 3: {
                if (this.mVelocityTracker == null) return true;
                {
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                    return true;
                }
            }
            default: {
                return true;
            }
            case 1: 
        }
        if (this.mIsDragging && this.mVelocityTracker != null) {
            this.mLastTouchX = this.getActiveX(motionEvent);
            this.mLastTouchY = this.getActiveY(motionEvent);
            this.mVelocityTracker.addMovement(motionEvent);
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float f6 = this.mVelocityTracker.getXVelocity();
            float f7 = this.mVelocityTracker.getYVelocity();
            if (Math.max(Math.abs(f6), Math.abs(f7)) >= this.mMinimumVelocity) {
                this.mListener.onFling(this.mLastTouchX, this.mLastTouchY, - f6, - f7);
            }
        }
        if (this.mVelocityTracker == null) return true;
        {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            return true;
        }
    }

    @Override
    public void setOnGestureListener(OnGestureListener onGestureListener) {
        this.mListener = onGestureListener;
    }
}

