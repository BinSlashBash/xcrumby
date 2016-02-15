package android.support.v4.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class GestureDetectorCompat {
    private final GestureDetectorCompatImpl mImpl;

    interface GestureDetectorCompatImpl {
        boolean isLongpressEnabled();

        boolean onTouchEvent(MotionEvent motionEvent);

        void setIsLongpressEnabled(boolean z);

        void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener);
    }

    static class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
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
        private OnDoubleTapListener mDoubleTapListener;
        private int mDoubleTapSlopSquare;
        private float mDownFocusX;
        private float mDownFocusY;
        private final Handler mHandler;
        private boolean mInLongPress;
        private boolean mIsDoubleTapping;
        private boolean mIsLongpressEnabled;
        private float mLastFocusX;
        private float mLastFocusY;
        private final OnGestureListener mListener;
        private int mMaximumFlingVelocity;
        private int mMinimumFlingVelocity;
        private MotionEvent mPreviousUpEvent;
        private boolean mStillDown;
        private int mTouchSlopSquare;
        private VelocityTracker mVelocityTracker;

        private class GestureHandler extends Handler {
            GestureHandler() {
            }

            GestureHandler(Handler handler) {
                super(handler.getLooper());
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GestureDetectorCompatImplBase.SHOW_PRESS /*1*/:
                        GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                    case GestureDetectorCompatImplBase.LONG_PRESS /*2*/:
                        GestureDetectorCompatImplBase.this.dispatchLongPress();
                    case GestureDetectorCompatImplBase.TAP /*3*/:
                        if (GestureDetectorCompatImplBase.this.mDoubleTapListener == null) {
                            return;
                        }
                        if (GestureDetectorCompatImplBase.this.mStillDown) {
                            GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true;
                        } else {
                            GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                        }
                    default:
                        throw new RuntimeException("Unknown message " + msg);
                }
            }
        }

        static {
            LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
            TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
            DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
        }

        public GestureDetectorCompatImplBase(Context context, OnGestureListener listener, Handler handler) {
            if (handler != null) {
                this.mHandler = new GestureHandler(handler);
            } else {
                this.mHandler = new GestureHandler();
            }
            this.mListener = listener;
            if (listener instanceof OnDoubleTapListener) {
                setOnDoubleTapListener((OnDoubleTapListener) listener);
            }
            init(context);
        }

        private void init(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            } else if (this.mListener == null) {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            } else {
                this.mIsLongpressEnabled = true;
                ViewConfiguration configuration = ViewConfiguration.get(context);
                int touchSlop = configuration.getScaledTouchSlop();
                int doubleTapSlop = configuration.getScaledDoubleTapSlop();
                this.mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
                this.mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
                this.mTouchSlopSquare = touchSlop * touchSlop;
                this.mDoubleTapSlopSquare = doubleTapSlop * doubleTapSlop;
            }
        }

        public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
            this.mDoubleTapListener = onDoubleTapListener;
        }

        public void setIsLongpressEnabled(boolean isLongpressEnabled) {
            this.mIsLongpressEnabled = isLongpressEnabled;
        }

        public boolean isLongpressEnabled() {
            return this.mIsLongpressEnabled;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTouchEvent(android.view.MotionEvent r42) {
            /*
            r41 = this;
            r5 = r42.getAction();
            r0 = r41;
            r0 = r0.mVelocityTracker;
            r35 = r0;
            if (r35 != 0) goto L_0x0016;
        L_0x000c:
            r35 = android.view.VelocityTracker.obtain();
            r0 = r35;
            r1 = r41;
            r1.mVelocityTracker = r0;
        L_0x0016:
            r0 = r41;
            r0 = r0.mVelocityTracker;
            r35 = r0;
            r0 = r35;
            r1 = r42;
            r0.addMovement(r1);
            r0 = r5 & 255;
            r35 = r0;
            r36 = 6;
            r0 = r35;
            r1 = r36;
            if (r0 != r1) goto L_0x004e;
        L_0x002f:
            r21 = 1;
        L_0x0031:
            if (r21 == 0) goto L_0x0051;
        L_0x0033:
            r24 = android.support.v4.view.MotionEventCompat.getActionIndex(r42);
        L_0x0037:
            r25 = 0;
            r26 = 0;
            r6 = android.support.v4.view.MotionEventCompat.getPointerCount(r42);
            r17 = 0;
        L_0x0041:
            r0 = r17;
            if (r0 >= r6) goto L_0x0069;
        L_0x0045:
            r0 = r24;
            r1 = r17;
            if (r0 != r1) goto L_0x0054;
        L_0x004b:
            r17 = r17 + 1;
            goto L_0x0041;
        L_0x004e:
            r21 = 0;
            goto L_0x0031;
        L_0x0051:
            r24 = -1;
            goto L_0x0037;
        L_0x0054:
            r0 = r42;
            r1 = r17;
            r35 = android.support.v4.view.MotionEventCompat.getX(r0, r1);
            r25 = r25 + r35;
            r0 = r42;
            r1 = r17;
            r35 = android.support.v4.view.MotionEventCompat.getY(r0, r1);
            r26 = r26 + r35;
            goto L_0x004b;
        L_0x0069:
            if (r21 == 0) goto L_0x0081;
        L_0x006b:
            r11 = r6 + -1;
        L_0x006d:
            r0 = (float) r11;
            r35 = r0;
            r13 = r25 / r35;
            r0 = (float) r11;
            r35 = r0;
            r14 = r26 / r35;
            r16 = 0;
            r0 = r5 & 255;
            r35 = r0;
            switch(r35) {
                case 0: goto L_0x012f;
                case 1: goto L_0x036e;
                case 2: goto L_0x0277;
                case 3: goto L_0x04b1;
                case 4: goto L_0x0080;
                case 5: goto L_0x0083;
                case 6: goto L_0x0097;
                default: goto L_0x0080;
            };
        L_0x0080:
            return r16;
        L_0x0081:
            r11 = r6;
            goto L_0x006d;
        L_0x0083:
            r0 = r41;
            r0.mLastFocusX = r13;
            r0 = r41;
            r0.mDownFocusX = r13;
            r0 = r41;
            r0.mLastFocusY = r14;
            r0 = r41;
            r0.mDownFocusY = r14;
            r41.cancelTaps();
            goto L_0x0080;
        L_0x0097:
            r0 = r41;
            r0.mLastFocusX = r13;
            r0 = r41;
            r0.mDownFocusX = r13;
            r0 = r41;
            r0.mLastFocusY = r14;
            r0 = r41;
            r0.mDownFocusY = r14;
            r0 = r41;
            r0 = r0.mVelocityTracker;
            r35 = r0;
            r36 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r0 = r41;
            r0 = r0.mMaximumFlingVelocity;
            r37 = r0;
            r0 = r37;
            r0 = (float) r0;
            r37 = r0;
            r35.computeCurrentVelocity(r36, r37);
            r27 = android.support.v4.view.MotionEventCompat.getActionIndex(r42);
            r0 = r42;
            r1 = r27;
            r18 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r1);
            r0 = r41;
            r0 = r0.mVelocityTracker;
            r35 = r0;
            r0 = r35;
            r1 = r18;
            r32 = android.support.v4.view.VelocityTrackerCompat.getXVelocity(r0, r1);
            r0 = r41;
            r0 = r0.mVelocityTracker;
            r35 = r0;
            r0 = r35;
            r1 = r18;
            r34 = android.support.v4.view.VelocityTrackerCompat.getYVelocity(r0, r1);
            r17 = 0;
        L_0x00e7:
            r0 = r17;
            if (r0 >= r6) goto L_0x0080;
        L_0x00eb:
            r0 = r17;
            r1 = r27;
            if (r0 != r1) goto L_0x00f4;
        L_0x00f1:
            r17 = r17 + 1;
            goto L_0x00e7;
        L_0x00f4:
            r0 = r42;
            r1 = r17;
            r19 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r1);
            r0 = r41;
            r0 = r0.mVelocityTracker;
            r35 = r0;
            r0 = r35;
            r1 = r19;
            r35 = android.support.v4.view.VelocityTrackerCompat.getXVelocity(r0, r1);
            r31 = r32 * r35;
            r0 = r41;
            r0 = r0.mVelocityTracker;
            r35 = r0;
            r0 = r35;
            r1 = r19;
            r35 = android.support.v4.view.VelocityTrackerCompat.getYVelocity(r0, r1);
            r33 = r34 * r35;
            r12 = r31 + r33;
            r35 = 0;
            r35 = (r12 > r35 ? 1 : (r12 == r35 ? 0 : -1));
            if (r35 >= 0) goto L_0x00f1;
        L_0x0124:
            r0 = r41;
            r0 = r0.mVelocityTracker;
            r35 = r0;
            r35.clear();
            goto L_0x0080;
        L_0x012f:
            r0 = r41;
            r0 = r0.mDoubleTapListener;
            r35 = r0;
            if (r35 == 0) goto L_0x01a6;
        L_0x0137:
            r0 = r41;
            r0 = r0.mHandler;
            r35 = r0;
            r36 = 3;
            r15 = r35.hasMessages(r36);
            if (r15 == 0) goto L_0x0150;
        L_0x0145:
            r0 = r41;
            r0 = r0.mHandler;
            r35 = r0;
            r36 = 3;
            r35.removeMessages(r36);
        L_0x0150:
            r0 = r41;
            r0 = r0.mCurrentDownEvent;
            r35 = r0;
            if (r35 == 0) goto L_0x0263;
        L_0x0158:
            r0 = r41;
            r0 = r0.mPreviousUpEvent;
            r35 = r0;
            if (r35 == 0) goto L_0x0263;
        L_0x0160:
            if (r15 == 0) goto L_0x0263;
        L_0x0162:
            r0 = r41;
            r0 = r0.mCurrentDownEvent;
            r35 = r0;
            r0 = r41;
            r0 = r0.mPreviousUpEvent;
            r36 = r0;
            r0 = r41;
            r1 = r35;
            r2 = r36;
            r3 = r42;
            r35 = r0.isConsideredDoubleTap(r1, r2, r3);
            if (r35 == 0) goto L_0x0263;
        L_0x017c:
            r35 = 1;
            r0 = r35;
            r1 = r41;
            r1.mIsDoubleTapping = r0;
            r0 = r41;
            r0 = r0.mDoubleTapListener;
            r35 = r0;
            r0 = r41;
            r0 = r0.mCurrentDownEvent;
            r36 = r0;
            r35 = r35.onDoubleTap(r36);
            r16 = r16 | r35;
            r0 = r41;
            r0 = r0.mDoubleTapListener;
            r35 = r0;
            r0 = r35;
            r1 = r42;
            r35 = r0.onDoubleTapEvent(r1);
            r16 = r16 | r35;
        L_0x01a6:
            r0 = r41;
            r0.mLastFocusX = r13;
            r0 = r41;
            r0.mDownFocusX = r13;
            r0 = r41;
            r0.mLastFocusY = r14;
            r0 = r41;
            r0.mDownFocusY = r14;
            r0 = r41;
            r0 = r0.mCurrentDownEvent;
            r35 = r0;
            if (r35 == 0) goto L_0x01c7;
        L_0x01be:
            r0 = r41;
            r0 = r0.mCurrentDownEvent;
            r35 = r0;
            r35.recycle();
        L_0x01c7:
            r35 = android.view.MotionEvent.obtain(r42);
            r0 = r35;
            r1 = r41;
            r1.mCurrentDownEvent = r0;
            r35 = 1;
            r0 = r35;
            r1 = r41;
            r1.mAlwaysInTapRegion = r0;
            r35 = 1;
            r0 = r35;
            r1 = r41;
            r1.mAlwaysInBiggerTapRegion = r0;
            r35 = 1;
            r0 = r35;
            r1 = r41;
            r1.mStillDown = r0;
            r35 = 0;
            r0 = r35;
            r1 = r41;
            r1.mInLongPress = r0;
            r35 = 0;
            r0 = r35;
            r1 = r41;
            r1.mDeferConfirmSingleTap = r0;
            r0 = r41;
            r0 = r0.mIsLongpressEnabled;
            r35 = r0;
            if (r35 == 0) goto L_0x0233;
        L_0x0201:
            r0 = r41;
            r0 = r0.mHandler;
            r35 = r0;
            r36 = 2;
            r35.removeMessages(r36);
            r0 = r41;
            r0 = r0.mHandler;
            r35 = r0;
            r36 = 2;
            r0 = r41;
            r0 = r0.mCurrentDownEvent;
            r37 = r0;
            r37 = r37.getDownTime();
            r39 = TAP_TIMEOUT;
            r0 = r39;
            r0 = (long) r0;
            r39 = r0;
            r37 = r37 + r39;
            r39 = LONGPRESS_TIMEOUT;
            r0 = r39;
            r0 = (long) r0;
            r39 = r0;
            r37 = r37 + r39;
            r35.sendEmptyMessageAtTime(r36, r37);
        L_0x0233:
            r0 = r41;
            r0 = r0.mHandler;
            r35 = r0;
            r36 = 1;
            r0 = r41;
            r0 = r0.mCurrentDownEvent;
            r37 = r0;
            r37 = r37.getDownTime();
            r39 = TAP_TIMEOUT;
            r0 = r39;
            r0 = (long) r0;
            r39 = r0;
            r37 = r37 + r39;
            r35.sendEmptyMessageAtTime(r36, r37);
            r0 = r41;
            r0 = r0.mListener;
            r35 = r0;
            r0 = r35;
            r1 = r42;
            r35 = r0.onDown(r1);
            r16 = r16 | r35;
            goto L_0x0080;
        L_0x0263:
            r0 = r41;
            r0 = r0.mHandler;
            r35 = r0;
            r36 = 3;
            r37 = DOUBLE_TAP_TIMEOUT;
            r0 = r37;
            r0 = (long) r0;
            r37 = r0;
            r35.sendEmptyMessageDelayed(r36, r37);
            goto L_0x01a6;
        L_0x0277:
            r0 = r41;
            r0 = r0.mInLongPress;
            r35 = r0;
            if (r35 != 0) goto L_0x0080;
        L_0x027f:
            r0 = r41;
            r0 = r0.mLastFocusX;
            r35 = r0;
            r22 = r35 - r13;
            r0 = r41;
            r0 = r0.mLastFocusY;
            r35 = r0;
            r23 = r35 - r14;
            r0 = r41;
            r0 = r0.mIsDoubleTapping;
            r35 = r0;
            if (r35 == 0) goto L_0x02a9;
        L_0x0297:
            r0 = r41;
            r0 = r0.mDoubleTapListener;
            r35 = r0;
            r0 = r35;
            r1 = r42;
            r35 = r0.onDoubleTapEvent(r1);
            r16 = r16 | r35;
            goto L_0x0080;
        L_0x02a9:
            r0 = r41;
            r0 = r0.mAlwaysInTapRegion;
            r35 = r0;
            if (r35 == 0) goto L_0x0336;
        L_0x02b1:
            r0 = r41;
            r0 = r0.mDownFocusX;
            r35 = r0;
            r35 = r13 - r35;
            r0 = r35;
            r8 = (int) r0;
            r0 = r41;
            r0 = r0.mDownFocusY;
            r35 = r0;
            r35 = r14 - r35;
            r0 = r35;
            r9 = (int) r0;
            r35 = r8 * r8;
            r36 = r9 * r9;
            r10 = r35 + r36;
            r0 = r41;
            r0 = r0.mTouchSlopSquare;
            r35 = r0;
            r0 = r35;
            if (r10 <= r0) goto L_0x0322;
        L_0x02d7:
            r0 = r41;
            r0 = r0.mListener;
            r35 = r0;
            r0 = r41;
            r0 = r0.mCurrentDownEvent;
            r36 = r0;
            r0 = r35;
            r1 = r36;
            r2 = r42;
            r3 = r22;
            r4 = r23;
            r16 = r0.onScroll(r1, r2, r3, r4);
            r0 = r41;
            r0.mLastFocusX = r13;
            r0 = r41;
            r0.mLastFocusY = r14;
            r35 = 0;
            r0 = r35;
            r1 = r41;
            r1.mAlwaysInTapRegion = r0;
            r0 = r41;
            r0 = r0.mHandler;
            r35 = r0;
            r36 = 3;
            r35.removeMessages(r36);
            r0 = r41;
            r0 = r0.mHandler;
            r35 = r0;
            r36 = 1;
            r35.removeMessages(r36);
            r0 = r41;
            r0 = r0.mHandler;
            r35 = r0;
            r36 = 2;
            r35.removeMessages(r36);
        L_0x0322:
            r0 = r41;
            r0 = r0.mTouchSlopSquare;
            r35 = r0;
            r0 = r35;
            if (r10 <= r0) goto L_0x0080;
        L_0x032c:
            r35 = 0;
            r0 = r35;
            r1 = r41;
            r1.mAlwaysInBiggerTapRegion = r0;
            goto L_0x0080;
        L_0x0336:
            r35 = java.lang.Math.abs(r22);
            r36 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r35 = (r35 > r36 ? 1 : (r35 == r36 ? 0 : -1));
            if (r35 >= 0) goto L_0x034a;
        L_0x0340:
            r35 = java.lang.Math.abs(r23);
            r36 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r35 = (r35 > r36 ? 1 : (r35 == r36 ? 0 : -1));
            if (r35 < 0) goto L_0x0080;
        L_0x034a:
            r0 = r41;
            r0 = r0.mListener;
            r35 = r0;
            r0 = r41;
            r0 = r0.mCurrentDownEvent;
            r36 = r0;
            r0 = r35;
            r1 = r36;
            r2 = r42;
            r3 = r22;
            r4 = r23;
            r16 = r0.onScroll(r1, r2, r3, r4);
            r0 = r41;
            r0.mLastFocusX = r13;
            r0 = r41;
            r0.mLastFocusY = r14;
            goto L_0x0080;
        L_0x036e:
            r35 = 0;
            r0 = r35;
            r1 = r41;
            r1.mStillDown = r0;
            r7 = android.view.MotionEvent.obtain(r42);
            r0 = r41;
            r0 = r0.mIsDoubleTapping;
            r35 = r0;
            if (r35 == 0) goto L_0x03e8;
        L_0x0382:
            r0 = r41;
            r0 = r0.mDoubleTapListener;
            r35 = r0;
            r0 = r35;
            r1 = r42;
            r35 = r0.onDoubleTapEvent(r1);
            r16 = r16 | r35;
        L_0x0392:
            r0 = r41;
            r0 = r0.mPreviousUpEvent;
            r35 = r0;
            if (r35 == 0) goto L_0x03a3;
        L_0x039a:
            r0 = r41;
            r0 = r0.mPreviousUpEvent;
            r35 = r0;
            r35.recycle();
        L_0x03a3:
            r0 = r41;
            r0.mPreviousUpEvent = r7;
            r0 = r41;
            r0 = r0.mVelocityTracker;
            r35 = r0;
            if (r35 == 0) goto L_0x03c0;
        L_0x03af:
            r0 = r41;
            r0 = r0.mVelocityTracker;
            r35 = r0;
            r35.recycle();
            r35 = 0;
            r0 = r35;
            r1 = r41;
            r1.mVelocityTracker = r0;
        L_0x03c0:
            r35 = 0;
            r0 = r35;
            r1 = r41;
            r1.mIsDoubleTapping = r0;
            r35 = 0;
            r0 = r35;
            r1 = r41;
            r1.mDeferConfirmSingleTap = r0;
            r0 = r41;
            r0 = r0.mHandler;
            r35 = r0;
            r36 = 1;
            r35.removeMessages(r36);
            r0 = r41;
            r0 = r0.mHandler;
            r35 = r0;
            r36 = 2;
            r35.removeMessages(r36);
            goto L_0x0080;
        L_0x03e8:
            r0 = r41;
            r0 = r0.mInLongPress;
            r35 = r0;
            if (r35 == 0) goto L_0x0404;
        L_0x03f0:
            r0 = r41;
            r0 = r0.mHandler;
            r35 = r0;
            r36 = 3;
            r35.removeMessages(r36);
            r35 = 0;
            r0 = r35;
            r1 = r41;
            r1.mInLongPress = r0;
            goto L_0x0392;
        L_0x0404:
            r0 = r41;
            r0 = r0.mAlwaysInTapRegion;
            r35 = r0;
            if (r35 == 0) goto L_0x0439;
        L_0x040c:
            r0 = r41;
            r0 = r0.mListener;
            r35 = r0;
            r0 = r35;
            r1 = r42;
            r16 = r0.onSingleTapUp(r1);
            r0 = r41;
            r0 = r0.mDeferConfirmSingleTap;
            r35 = r0;
            if (r35 == 0) goto L_0x0392;
        L_0x0422:
            r0 = r41;
            r0 = r0.mDoubleTapListener;
            r35 = r0;
            if (r35 == 0) goto L_0x0392;
        L_0x042a:
            r0 = r41;
            r0 = r0.mDoubleTapListener;
            r35 = r0;
            r0 = r35;
            r1 = r42;
            r0.onSingleTapConfirmed(r1);
            goto L_0x0392;
        L_0x0439:
            r0 = r41;
            r0 = r0.mVelocityTracker;
            r28 = r0;
            r35 = 0;
            r0 = r42;
            r1 = r35;
            r20 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r1);
            r35 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r0 = r41;
            r0 = r0.mMaximumFlingVelocity;
            r36 = r0;
            r0 = r36;
            r0 = (float) r0;
            r36 = r0;
            r0 = r28;
            r1 = r35;
            r2 = r36;
            r0.computeCurrentVelocity(r1, r2);
            r0 = r28;
            r1 = r20;
            r30 = android.support.v4.view.VelocityTrackerCompat.getYVelocity(r0, r1);
            r0 = r28;
            r1 = r20;
            r29 = android.support.v4.view.VelocityTrackerCompat.getXVelocity(r0, r1);
            r35 = java.lang.Math.abs(r30);
            r0 = r41;
            r0 = r0.mMinimumFlingVelocity;
            r36 = r0;
            r0 = r36;
            r0 = (float) r0;
            r36 = r0;
            r35 = (r35 > r36 ? 1 : (r35 == r36 ? 0 : -1));
            if (r35 > 0) goto L_0x0495;
        L_0x0482:
            r35 = java.lang.Math.abs(r29);
            r0 = r41;
            r0 = r0.mMinimumFlingVelocity;
            r36 = r0;
            r0 = r36;
            r0 = (float) r0;
            r36 = r0;
            r35 = (r35 > r36 ? 1 : (r35 == r36 ? 0 : -1));
            if (r35 <= 0) goto L_0x0392;
        L_0x0495:
            r0 = r41;
            r0 = r0.mListener;
            r35 = r0;
            r0 = r41;
            r0 = r0.mCurrentDownEvent;
            r36 = r0;
            r0 = r35;
            r1 = r36;
            r2 = r42;
            r3 = r29;
            r4 = r30;
            r16 = r0.onFling(r1, r2, r3, r4);
            goto L_0x0392;
        L_0x04b1:
            r41.cancel();
            goto L_0x0080;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImplBase.onTouchEvent(android.view.MotionEvent):boolean");
        }

        private void cancel() {
            this.mHandler.removeMessages(SHOW_PRESS);
            this.mHandler.removeMessages(LONG_PRESS);
            this.mHandler.removeMessages(TAP);
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
            this.mHandler.removeMessages(SHOW_PRESS);
            this.mHandler.removeMessages(LONG_PRESS);
            this.mHandler.removeMessages(TAP);
            this.mIsDoubleTapping = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private boolean isConsideredDoubleTap(MotionEvent firstDown, MotionEvent firstUp, MotionEvent secondDown) {
            if (!this.mAlwaysInBiggerTapRegion || secondDown.getEventTime() - firstUp.getEventTime() > ((long) DOUBLE_TAP_TIMEOUT)) {
                return false;
            }
            int deltaX = ((int) firstDown.getX()) - ((int) secondDown.getX());
            int deltaY = ((int) firstDown.getY()) - ((int) secondDown.getY());
            if ((deltaX * deltaX) + (deltaY * deltaY) < this.mDoubleTapSlopSquare) {
                return true;
            }
            return false;
        }

        private void dispatchLongPress() {
            this.mHandler.removeMessages(TAP);
            this.mDeferConfirmSingleTap = false;
            this.mInLongPress = true;
            this.mListener.onLongPress(this.mCurrentDownEvent);
        }
    }

    static class GestureDetectorCompatImplJellybeanMr2 implements GestureDetectorCompatImpl {
        private final GestureDetector mDetector;

        public GestureDetectorCompatImplJellybeanMr2(Context context, OnGestureListener listener, Handler handler) {
            this.mDetector = new GestureDetector(context, listener, handler);
        }

        public boolean isLongpressEnabled() {
            return this.mDetector.isLongpressEnabled();
        }

        public boolean onTouchEvent(MotionEvent ev) {
            return this.mDetector.onTouchEvent(ev);
        }

        public void setIsLongpressEnabled(boolean enabled) {
            this.mDetector.setIsLongpressEnabled(enabled);
        }

        public void setOnDoubleTapListener(OnDoubleTapListener listener) {
            this.mDetector.setOnDoubleTapListener(listener);
        }
    }

    public GestureDetectorCompat(Context context, OnGestureListener listener) {
        this(context, listener, null);
    }

    public GestureDetectorCompat(Context context, OnGestureListener listener, Handler handler) {
        if (VERSION.SDK_INT > 17) {
            this.mImpl = new GestureDetectorCompatImplJellybeanMr2(context, listener, handler);
        } else {
            this.mImpl = new GestureDetectorCompatImplBase(context, listener, handler);
        }
    }

    public boolean isLongpressEnabled() {
        return this.mImpl.isLongpressEnabled();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.mImpl.onTouchEvent(event);
    }

    public void setIsLongpressEnabled(boolean enabled) {
        this.mImpl.setIsLongpressEnabled(enabled);
    }

    public void setOnDoubleTapListener(OnDoubleTapListener listener) {
        this.mImpl.setOnDoubleTapListener(listener);
    }
}
