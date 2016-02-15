/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.ViewConfiguration
 */
package android.support.v4.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class GestureDetectorCompat {
    private final GestureDetectorCompatImpl mImpl;

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
        if (Build.VERSION.SDK_INT > 17) {
            this.mImpl = new GestureDetectorCompatImplJellybeanMr2(context, onGestureListener, handler);
            return;
        }
        this.mImpl = new GestureDetectorCompatImplBase(context, onGestureListener, handler);
    }

    public boolean isLongpressEnabled() {
        return this.mImpl.isLongpressEnabled();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mImpl.onTouchEvent(motionEvent);
    }

    public void setIsLongpressEnabled(boolean bl2) {
        this.mImpl.setIsLongpressEnabled(bl2);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.mImpl.setOnDoubleTapListener(onDoubleTapListener);
    }

    static interface GestureDetectorCompatImpl {
        public boolean isLongpressEnabled();

        public boolean onTouchEvent(MotionEvent var1);

        public void setIsLongpressEnabled(boolean var1);

        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener var1);
    }

    static class GestureDetectorCompatImplBase
    implements GestureDetectorCompatImpl {
        private static final int DOUBLE_TAP_TIMEOUT;
        private static final int LONGPRESS_TIMEOUT;
        private static final int LONG_PRESS = 2;
        private static final int SHOW_PRESS = 1;
        private static final int TAP = 3;
        private static final int TAP_TIMEOUT;
        private boolean mAlwaysInBiggerTapRegion;
        private boolean mAlwaysInTapRegion;
        private MotionEvent mCurrentDownEvent;
        private boolean mDeferConfirmSingleTap;
        private GestureDetector.OnDoubleTapListener mDoubleTapListener;
        private int mDoubleTapSlopSquare;
        private float mDownFocusX;
        private float mDownFocusY;
        private final Handler mHandler;
        private boolean mInLongPress;
        private boolean mIsDoubleTapping;
        private boolean mIsLongpressEnabled;
        private float mLastFocusX;
        private float mLastFocusY;
        private final GestureDetector.OnGestureListener mListener;
        private int mMaximumFlingVelocity;
        private int mMinimumFlingVelocity;
        private MotionEvent mPreviousUpEvent;
        private boolean mStillDown;
        private int mTouchSlopSquare;
        private VelocityTracker mVelocityTracker;

        static {
            LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
            TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
            DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
        }

        /*
         * Enabled aggressive block sorting
         */
        public GestureDetectorCompatImplBase(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            this.mHandler = handler != null ? new GestureHandler(this, handler) : new GestureHandler(this);
            this.mListener = onGestureListener;
            if (onGestureListener instanceof GestureDetector.OnDoubleTapListener) {
                this.setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)onGestureListener);
            }
            this.init(context);
        }

        private void cancel() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            this.mIsDoubleTapping = false;
            this.mStillDown = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private void cancelTaps() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mIsDoubleTapping = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private void dispatchLongPress() {
            this.mHandler.removeMessages(3);
            this.mDeferConfirmSingleTap = false;
            this.mInLongPress = true;
            this.mListener.onLongPress(this.mCurrentDownEvent);
        }

        private void init(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            }
            if (this.mListener == null) {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            }
            this.mIsLongpressEnabled = true;
            context = ViewConfiguration.get((Context)context);
            int n2 = context.getScaledTouchSlop();
            int n3 = context.getScaledDoubleTapSlop();
            this.mMinimumFlingVelocity = context.getScaledMinimumFlingVelocity();
            this.mMaximumFlingVelocity = context.getScaledMaximumFlingVelocity();
            this.mTouchSlopSquare = n2 * n2;
            this.mDoubleTapSlopSquare = n3 * n3;
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean isConsideredDoubleTap(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
            int n2;
            int n3;
            if (!this.mAlwaysInBiggerTapRegion || motionEvent3.getEventTime() - motionEvent2.getEventTime() > (long)DOUBLE_TAP_TIMEOUT || (n3 = (int)motionEvent.getX() - (int)motionEvent3.getX()) * n3 + (n2 = (int)motionEvent.getY() - (int)motionEvent3.getY()) * n2 >= this.mDoubleTapSlopSquare) {
                return false;
            }
            return true;
        }

        @Override
        public boolean isLongpressEnabled() {
            return this.mIsLongpressEnabled;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public boolean onTouchEvent(MotionEvent var1_1) {
            var10_2 = var1_1.getAction();
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(var1_1);
            var6_3 = (var10_2 & 255) == 6 ? 1 : 0;
            var7_4 = var6_3 != 0 ? MotionEventCompat.getActionIndex(var1_1) : -1;
            var3_5 = 0.0f;
            var2_6 = 0.0f;
            var9_7 = MotionEventCompat.getPointerCount(var1_1);
            for (var8_8 = 0; var8_8 < var9_7; ++var8_8) {
                if (var7_4 == var8_8) continue;
                var3_5 += MotionEventCompat.getX(var1_1, var8_8);
                var2_6 += MotionEventCompat.getY(var1_1, var8_8);
            }
            var6_3 = var6_3 != 0 ? var9_7 - 1 : var9_7;
            var3_5 /= (float)var6_3;
            var2_6 /= (float)var6_3;
            var7_4 = 0;
            var13_9 = false;
            var14_10 = false;
            var12_12 = var11_11 = false;
            switch (var10_2 & 255) {
                default: {
                    var12_12 = var11_11;
                }
                case 4: {
                    return var12_12;
                }
                case 5: {
                    this.mLastFocusX = var3_5;
                    this.mDownFocusX = var3_5;
                    this.mLastFocusY = var2_6;
                    this.mDownFocusY = var2_6;
                    this.cancelTaps();
                    return false;
                }
                case 6: {
                    this.mLastFocusX = var3_5;
                    this.mDownFocusX = var3_5;
                    this.mLastFocusY = var2_6;
                    this.mDownFocusY = var2_6;
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumFlingVelocity);
                    var7_4 = MotionEventCompat.getActionIndex(var1_1);
                    var6_3 = MotionEventCompat.getPointerId(var1_1, var7_4);
                    var2_6 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, var6_3);
                    var3_5 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, var6_3);
                    var6_3 = 0;
                    do {
                        var12_12 = var11_11;
                        if (var6_3 >= var9_7) return var12_12;
                        if (var6_3 != var7_4 && var2_6 * VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, var8_8 = MotionEventCompat.getPointerId(var1_1, var6_3)) + var3_5 * VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, var8_8) < 0.0f) {
                            this.mVelocityTracker.clear();
                            return false;
                        }
                        ++var6_3;
                    } while (true);
                }
                case 0: {
                    var6_3 = var7_4;
                    if (this.mDoubleTapListener != null) {
                        var11_11 = this.mHandler.hasMessages(3);
                        if (var11_11) {
                            this.mHandler.removeMessages(3);
                        }
                        if (this.mCurrentDownEvent != null && this.mPreviousUpEvent != null && var11_11 && this.isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, var1_1)) {
                            this.mIsDoubleTapping = true;
                            var6_3 = false | this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | this.mDoubleTapListener.onDoubleTapEvent(var1_1);
                        } else {
                            this.mHandler.sendEmptyMessageDelayed(3, (long)GestureDetectorCompatImplBase.DOUBLE_TAP_TIMEOUT);
                            var6_3 = var7_4;
                        }
                    }
                    this.mLastFocusX = var3_5;
                    this.mDownFocusX = var3_5;
                    this.mLastFocusY = var2_6;
                    this.mDownFocusY = var2_6;
                    if (this.mCurrentDownEvent != null) {
                        this.mCurrentDownEvent.recycle();
                    }
                    this.mCurrentDownEvent = MotionEvent.obtain((MotionEvent)var1_1);
                    this.mAlwaysInTapRegion = true;
                    this.mAlwaysInBiggerTapRegion = true;
                    this.mStillDown = true;
                    this.mInLongPress = false;
                    this.mDeferConfirmSingleTap = false;
                    if (this.mIsLongpressEnabled) {
                        this.mHandler.removeMessages(2);
                        this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + (long)GestureDetectorCompatImplBase.TAP_TIMEOUT + (long)GestureDetectorCompatImplBase.LONGPRESS_TIMEOUT);
                    }
                    this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + (long)GestureDetectorCompatImplBase.TAP_TIMEOUT);
                    return (boolean)(var6_3 | this.mListener.onDown(var1_1));
                }
                case 2: {
                    var12_12 = var11_11;
                    if (this.mInLongPress != false) return var12_12;
                    var4_13 = this.mLastFocusX - var3_5;
                    var5_14 = this.mLastFocusY - var2_6;
                    if (this.mIsDoubleTapping) {
                        return false | this.mDoubleTapListener.onDoubleTapEvent(var1_1);
                    }
                    if (this.mAlwaysInTapRegion) {
                        var6_3 = (int)(var3_5 - this.mDownFocusX);
                        var7_4 = (int)(var2_6 - this.mDownFocusY);
                        var6_3 = var6_3 * var6_3 + var7_4 * var7_4;
                        var11_11 = var13_9;
                        if (var6_3 > this.mTouchSlopSquare) {
                            var11_11 = this.mListener.onScroll(this.mCurrentDownEvent, var1_1, var4_13, var5_14);
                            this.mLastFocusX = var3_5;
                            this.mLastFocusY = var2_6;
                            this.mAlwaysInTapRegion = false;
                            this.mHandler.removeMessages(3);
                            this.mHandler.removeMessages(1);
                            this.mHandler.removeMessages(2);
                        }
                        var12_12 = var11_11;
                        if (var6_3 <= this.mTouchSlopSquare) return var12_12;
                        this.mAlwaysInBiggerTapRegion = false;
                        return var11_11;
                    }
                    if (Math.abs(var4_13) < 1.0f) {
                        var12_12 = var11_11;
                        if (Math.abs(var5_14) < 1.0f) return var12_12;
                    }
                    var11_11 = this.mListener.onScroll(this.mCurrentDownEvent, var1_1, var4_13, var5_14);
                    this.mLastFocusX = var3_5;
                    this.mLastFocusY = var2_6;
                    return var11_11;
                }
                case 1: {
                    this.mStillDown = false;
                    var15_15 = MotionEvent.obtain((MotionEvent)var1_1);
                    if (!this.mIsDoubleTapping) ** GOTO lbl119
                    var11_11 = false | this.mDoubleTapListener.onDoubleTapEvent(var1_1);
                    ** GOTO lbl141
lbl119: // 1 sources:
                    if (!this.mInLongPress) ** GOTO lbl124
                    this.mHandler.removeMessages(3);
                    this.mInLongPress = false;
                    var11_11 = var14_10;
                    ** GOTO lbl141
lbl124: // 1 sources:
                    if (!this.mAlwaysInTapRegion) ** GOTO lbl132
                    var11_11 = var12_12 = this.mListener.onSingleTapUp(var1_1);
                    if (this.mDeferConfirmSingleTap) {
                        var11_11 = var12_12;
                        if (this.mDoubleTapListener != null) {
                            this.mDoubleTapListener.onSingleTapConfirmed(var1_1);
                            var11_11 = var12_12;
                        }
                    }
                    ** GOTO lbl141
lbl132: // 1 sources:
                    var16_16 = this.mVelocityTracker;
                    var6_3 = MotionEventCompat.getPointerId(var1_1, 0);
                    var16_16.computeCurrentVelocity(1000, (float)this.mMaximumFlingVelocity);
                    var2_6 = VelocityTrackerCompat.getYVelocity(var16_16, var6_3);
                    var3_5 = VelocityTrackerCompat.getXVelocity(var16_16, var6_3);
                    if (Math.abs(var2_6) > (float)this.mMinimumFlingVelocity) ** GOTO lbl-1000
                    var11_11 = var14_10;
                    if (Math.abs(var3_5) > (float)this.mMinimumFlingVelocity) lbl-1000: // 2 sources:
                    {
                        var11_11 = this.mListener.onFling(this.mCurrentDownEvent, var1_1, var3_5, var2_6);
                    }
lbl141: // 6 sources:
                    if (this.mPreviousUpEvent != null) {
                        this.mPreviousUpEvent.recycle();
                    }
                    this.mPreviousUpEvent = var15_15;
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }
                    this.mIsDoubleTapping = false;
                    this.mDeferConfirmSingleTap = false;
                    this.mHandler.removeMessages(1);
                    this.mHandler.removeMessages(2);
                    return var11_11;
                }
                case 3: 
            }
            this.cancel();
            return false;
        }

        @Override
        public void setIsLongpressEnabled(boolean bl2) {
            this.mIsLongpressEnabled = bl2;
        }

        @Override
        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.mDoubleTapListener = onDoubleTapListener;
        }

        private class GestureHandler
        extends Handler {
            final /* synthetic */ GestureDetectorCompatImplBase this$0;

            GestureHandler(GestureDetectorCompatImplBase gestureDetectorCompatImplBase) {
                this.this$0 = gestureDetectorCompatImplBase;
            }

            GestureHandler(GestureDetectorCompatImplBase gestureDetectorCompatImplBase, Handler handler) {
                this.this$0 = gestureDetectorCompatImplBase;
                super(handler.getLooper());
            }

            /*
             * Enabled aggressive block sorting
             */
            public void handleMessage(Message message) {
                switch (message.what) {
                    default: {
                        throw new RuntimeException("Unknown message " + (Object)message);
                    }
                    case 1: {
                        this.this$0.mListener.onShowPress(this.this$0.mCurrentDownEvent);
                        return;
                    }
                    case 2: {
                        this.this$0.dispatchLongPress();
                        return;
                    }
                    case 3: {
                        if (this.this$0.mDoubleTapListener == null) return;
                        if (!this.this$0.mStillDown) {
                            this.this$0.mDoubleTapListener.onSingleTapConfirmed(this.this$0.mCurrentDownEvent);
                            return;
                        } else {
                            break;
                        }
                    }
                }
                this.this$0.mDeferConfirmSingleTap = true;
            }
        }

    }

    static class GestureDetectorCompatImplJellybeanMr2
    implements GestureDetectorCompatImpl {
        private final GestureDetector mDetector;

        public GestureDetectorCompatImplJellybeanMr2(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            this.mDetector = new GestureDetector(context, onGestureListener, handler);
        }

        @Override
        public boolean isLongpressEnabled() {
            return this.mDetector.isLongpressEnabled();
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return this.mDetector.onTouchEvent(motionEvent);
        }

        @Override
        public void setIsLongpressEnabled(boolean bl2) {
            this.mDetector.setIsLongpressEnabled(bl2);
        }

        @Override
        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.mDetector.setOnDoubleTapListener(onDoubleTapListener);
        }
    }

}

