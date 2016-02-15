/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.ScaleGestureDetector
 *  android.view.ScaleGestureDetector$OnScaleGestureListener
 *  android.view.ScaleGestureDetector$SimpleOnScaleGestureListener
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnTouchListener
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  android.widget.Scroller
 */
package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;

public class TouchImageView
extends ImageView {
    private static final String DEBUG = "DEBUG";
    private static final float SUPER_MAX_MULTIPLIER = 1.25f;
    private static final float SUPER_MIN_MULTIPLIER = 0.75f;
    private Context context;
    private Fling fling;
    private float[] m;
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleDetector;
    private boolean maintainZoomAfterSetImage;
    private float matchViewHeight;
    private float matchViewWidth;
    private Matrix matrix;
    private float maxScale;
    private float minScale;
    private float normalizedScale;
    private float prevMatchViewHeight;
    private float prevMatchViewWidth;
    private Matrix prevMatrix;
    private int prevViewHeight;
    private int prevViewWidth;
    private boolean setImageCalledRecenterImage;
    private State state;
    private float superMaxScale;
    private float superMinScale;
    private int viewHeight;
    private int viewWidth;

    public TouchImageView(Context context) {
        super(context);
        this.sharedConstructing(context);
    }

    public TouchImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.sharedConstructing(context);
    }

    public TouchImageView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.sharedConstructing(context);
    }

    static /* synthetic */ GestureDetector access$1000(TouchImageView touchImageView) {
        return touchImageView.mGestureDetector;
    }

    static /* synthetic */ float access$1400(TouchImageView touchImageView, float f2, float f3, float f4) {
        return touchImageView.getFixDragTrans(f2, f3, f4);
    }

    static /* synthetic */ ScaleGestureDetector access$900(TouchImageView touchImageView) {
        return touchImageView.mScaleDetector;
    }

    private void compatPostOnAnimation(Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.postOnAnimation(runnable);
            return;
        }
        this.postDelayed(runnable, 16);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void fitImageToView() {
        Drawable drawable2 = this.getDrawable();
        if (drawable2 == null || drawable2.getIntrinsicWidth() == 0 || drawable2.getIntrinsicHeight() == 0 || this.matrix == null || this.prevMatrix == null) {
            return;
        }
        int n2 = drawable2.getIntrinsicWidth();
        int n3 = drawable2.getIntrinsicHeight();
        float f2 = Math.min((float)this.viewWidth / (float)n2, (float)this.viewHeight / (float)n3);
        float f3 = (float)this.viewHeight - (float)n3 * f2;
        float f4 = (float)this.viewWidth - (float)n2 * f2;
        this.matchViewWidth = (float)this.viewWidth - f4;
        this.matchViewHeight = (float)this.viewHeight - f3;
        if (this.normalizedScale == 1.0f || this.setImageCalledRecenterImage) {
            this.matrix.setScale(f2, f2);
            this.matrix.postTranslate(f4 / 2.0f, f3 / 2.0f);
            this.normalizedScale = 1.0f;
            this.setImageCalledRecenterImage = false;
        } else {
            this.prevMatrix.getValues(this.m);
            this.m[0] = this.matchViewWidth / (float)n2 * this.normalizedScale;
            this.m[4] = this.matchViewHeight / (float)n3 * this.normalizedScale;
            f2 = this.m[2];
            f3 = this.m[5];
            this.translateMatrixAfterRotate(2, f2, this.prevMatchViewWidth * this.normalizedScale, this.getImageWidth(), this.prevViewWidth, this.viewWidth, n2);
            this.translateMatrixAfterRotate(5, f3, this.prevMatchViewHeight * this.normalizedScale, this.getImageHeight(), this.prevViewHeight, this.viewHeight, n3);
            this.matrix.setValues(this.m);
        }
        this.setImageMatrix(this.matrix);
    }

    private void fixScaleTrans() {
        this.fixTrans();
        this.matrix.getValues(this.m);
        if (this.getImageWidth() < (float)this.viewWidth) {
            this.m[2] = ((float)this.viewWidth - this.getImageWidth()) / 2.0f;
        }
        if (this.getImageHeight() < (float)this.viewHeight) {
            this.m[5] = ((float)this.viewHeight - this.getImageHeight()) / 2.0f;
        }
        this.matrix.setValues(this.m);
    }

    private void fixTrans() {
        this.matrix.getValues(this.m);
        float f2 = this.m[2];
        float f3 = this.m[5];
        f2 = this.getFixTrans(f2, this.viewWidth, this.getImageWidth());
        f3 = this.getFixTrans(f3, this.viewHeight, this.getImageHeight());
        if (f2 != 0.0f || f3 != 0.0f) {
            this.matrix.postTranslate(f2, f3);
        }
    }

    private float getFixDragTrans(float f2, float f3, float f4) {
        if (f4 <= f3) {
            f2 = 0.0f;
        }
        return f2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private float getFixTrans(float f2, float f3, float f4) {
        if (f4 <= f3) {
            float f5 = 0.0f;
            f3 -= f4;
            f4 = f5;
        } else {
            f4 = f3 - f4;
            f3 = 0.0f;
        }
        if (f2 < f4) {
            return - f2 + f4;
        }
        if (f2 > f3) {
            return - f2 + f3;
        }
        return 0.0f;
    }

    private float getImageHeight() {
        return this.matchViewHeight * this.normalizedScale;
    }

    private float getImageWidth() {
        return this.matchViewWidth * this.normalizedScale;
    }

    private void printMatrixInfo() {
        this.matrix.getValues(this.m);
        Log.d((String)"DEBUG", (String)("Scale: " + this.m[0] + " TransX: " + this.m[2] + " TransY: " + this.m[5]));
    }

    private void savePreviousImageValues() {
        if (this.matrix != null) {
            this.matrix.getValues(this.m);
            this.prevMatrix.setValues(this.m);
            this.prevMatchViewHeight = this.matchViewHeight;
            this.prevMatchViewWidth = this.matchViewWidth;
            this.prevViewHeight = this.viewHeight;
            this.prevViewWidth = this.viewWidth;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void scaleImage(float f2, float f3, float f4, boolean bl2) {
        float f5;
        float f6;
        if (bl2) {
            f6 = this.superMinScale;
            f5 = this.superMaxScale;
        } else {
            f6 = this.minScale;
            f5 = this.maxScale;
        }
        float f7 = this.normalizedScale;
        this.normalizedScale *= f2;
        if (this.normalizedScale > f5) {
            this.normalizedScale = f5;
            f2 = f5 / f7;
        } else if (this.normalizedScale < f6) {
            this.normalizedScale = f6;
            f2 = f6 / f7;
        }
        this.matrix.postScale(f2, f2, f3, f4);
        this.fixScaleTrans();
    }

    private void setImageCalled() {
        if (!this.maintainZoomAfterSetImage) {
            this.setImageCalledRecenterImage = true;
        }
    }

    private void setState(State state) {
        this.state = state;
    }

    private int setViewSize(int n2, int n3, int n4) {
        switch (n2) {
            default: {
                return n3;
            }
            case 1073741824: {
                return n3;
            }
            case Integer.MIN_VALUE: {
                return Math.min(n4, n3);
            }
            case 0: 
        }
        return n4;
    }

    private void sharedConstructing(Context context) {
        super.setClickable(true);
        this.context = context;
        this.mScaleDetector = new ScaleGestureDetector(context, (ScaleGestureDetector.OnScaleGestureListener)new ScaleListener());
        this.mGestureDetector = new GestureDetector(context, (GestureDetector.OnGestureListener)new GestureListener());
        this.matrix = new Matrix();
        this.prevMatrix = new Matrix();
        this.m = new float[9];
        this.normalizedScale = 1.0f;
        this.minScale = 1.0f;
        this.maxScale = 3.0f;
        this.superMinScale = 0.75f * this.minScale;
        this.superMaxScale = 1.25f * this.maxScale;
        this.maintainZoomAfterSetImage = true;
        this.setImageMatrix(this.matrix);
        this.setScaleType(ImageView.ScaleType.MATRIX);
        this.setState(State.NONE);
        this.setOnTouchListener((View.OnTouchListener)new TouchImageViewListener());
    }

    private PointF transformCoordBitmapToTouch(float f2, float f3) {
        this.matrix.getValues(this.m);
        float f4 = this.getDrawable().getIntrinsicWidth();
        float f5 = this.getDrawable().getIntrinsicHeight();
        return new PointF(this.m[2] + this.getImageWidth() * (f2 /= f4), this.m[5] + this.getImageHeight() * (f3 /= f5));
    }

    private PointF transformCoordTouchToBitmap(float f2, float f3, boolean bl2) {
        this.matrix.getValues(this.m);
        float f4 = this.getDrawable().getIntrinsicWidth();
        float f5 = this.getDrawable().getIntrinsicHeight();
        float f6 = this.m[2];
        float f7 = this.m[5];
        f6 = (f2 - f6) * f4 / this.getImageWidth();
        f7 = (f3 - f7) * f5 / this.getImageHeight();
        if (bl2) {
            f6 = Math.min(Math.max(f2, 0.0f), f4);
            f7 = Math.min(Math.max(f3, 0.0f), f5);
        }
        return new PointF(f6, f7);
    }

    private void translateMatrixAfterRotate(int n2, float f2, float f3, float f4, int n3, int n4, int n5) {
        if (f4 < (float)n4) {
            this.m[n2] = ((float)n4 - (float)n5 * this.m[0]) * 0.5f;
            return;
        }
        if (f2 > 0.0f) {
            this.m[n2] = - (f4 - (float)n4) * 0.5f;
            return;
        }
        f2 = (Math.abs(f2) + (float)n3 * 0.5f) / f3;
        this.m[n2] = - f2 * f4 - (float)n4 * 0.5f;
    }

    public float getCurrentZoom() {
        return this.normalizedScale;
    }

    public PointF getDrawablePointFromTouchPoint(float f2, float f3) {
        return this.transformCoordTouchToBitmap(f2, f3, true);
    }

    public PointF getDrawablePointFromTouchPoint(PointF pointF) {
        return this.transformCoordTouchToBitmap(pointF.x, pointF.y, true);
    }

    public float getMaxZoom() {
        return this.maxScale;
    }

    public float getMinZoom() {
        return this.minScale;
    }

    public void maintainZoomAfterSetImage(boolean bl2) {
        this.maintainZoomAfterSetImage = bl2;
    }

    protected void onMeasure(int n2, int n3) {
        Drawable drawable2 = this.getDrawable();
        if (drawable2 == null || drawable2.getIntrinsicWidth() == 0 || drawable2.getIntrinsicHeight() == 0) {
            this.setMeasuredDimension(0, 0);
            return;
        }
        int n4 = drawable2.getIntrinsicWidth();
        int n5 = drawable2.getIntrinsicHeight();
        int n6 = View.MeasureSpec.getSize((int)n2);
        n2 = View.MeasureSpec.getMode((int)n2);
        int n7 = View.MeasureSpec.getSize((int)n3);
        n3 = View.MeasureSpec.getMode((int)n3);
        this.viewWidth = this.setViewSize(n2, n6, n4);
        this.viewHeight = this.setViewSize(n3, n7, n5);
        this.setMeasuredDimension(this.viewWidth, this.viewHeight);
        this.fitImageToView();
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            parcelable = (Bundle)parcelable;
            this.normalizedScale = parcelable.getFloat("saveScale");
            this.m = parcelable.getFloatArray("matrix");
            this.prevMatrix.setValues(this.m);
            this.prevMatchViewHeight = parcelable.getFloat("matchViewHeight");
            this.prevMatchViewWidth = parcelable.getFloat("matchViewWidth");
            this.prevViewHeight = parcelable.getInt("viewHeight");
            this.prevViewWidth = parcelable.getInt("viewWidth");
            super.onRestoreInstanceState(parcelable.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putFloat("saveScale", this.normalizedScale);
        bundle.putFloat("matchViewHeight", this.matchViewHeight);
        bundle.putFloat("matchViewWidth", this.matchViewWidth);
        bundle.putInt("viewWidth", this.viewWidth);
        bundle.putInt("viewHeight", this.viewHeight);
        this.matrix.getValues(this.m);
        bundle.putFloatArray("matrix", this.m);
        return bundle;
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        this.setImageCalled();
        this.savePreviousImageValues();
        this.fitImageToView();
    }

    public void setImageDrawable(Drawable drawable2) {
        super.setImageDrawable(drawable2);
        this.setImageCalled();
        this.savePreviousImageValues();
        this.fitImageToView();
    }

    public void setImageResource(int n2) {
        super.setImageResource(n2);
        this.setImageCalled();
        this.savePreviousImageValues();
        this.fitImageToView();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        this.setImageCalled();
        this.savePreviousImageValues();
        this.fitImageToView();
    }

    public void setMaxZoom(float f2) {
        this.maxScale = f2;
        this.superMaxScale = 1.25f * this.maxScale;
    }

    public void setMinZoom(float f2) {
        this.minScale = f2;
        this.superMinScale = 0.75f * this.minScale;
    }

    private class DoubleTapZoom
    implements Runnable {
        private static final float ZOOM_TIME = 500.0f;
        private float bitmapX;
        private float bitmapY;
        private PointF endTouch;
        private AccelerateDecelerateInterpolator interpolator;
        private long startTime;
        private PointF startTouch;
        private float startZoom;
        private boolean stretchImageToSuper;
        private float targetZoom;

        DoubleTapZoom(float f2, float f3, float f4, boolean bl2) {
            this.interpolator = new AccelerateDecelerateInterpolator();
            TouchImageView.this.setState(State.ANIMATE_ZOOM);
            this.startTime = System.currentTimeMillis();
            this.startZoom = TouchImageView.this.normalizedScale;
            this.targetZoom = f2;
            this.stretchImageToSuper = bl2;
            PointF pointF = TouchImageView.this.transformCoordTouchToBitmap(f3, f4, false);
            this.bitmapX = pointF.x;
            this.bitmapY = pointF.y;
            this.startTouch = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            this.endTouch = new PointF((float)(TouchImageView.this.viewWidth / 2), (float)(TouchImageView.this.viewHeight / 2));
        }

        private float calculateDeltaScale(float f2) {
            return (this.startZoom + (this.targetZoom - this.startZoom) * f2) / TouchImageView.this.normalizedScale;
        }

        private float interpolate() {
            float f2 = Math.min(1.0f, (float)(System.currentTimeMillis() - this.startTime) / 500.0f);
            return this.interpolator.getInterpolation(f2);
        }

        private void translateImageToCenterTouchPosition(float f2) {
            float f3 = this.startTouch.x;
            float f4 = this.endTouch.x;
            float f5 = this.startTouch.x;
            float f6 = this.startTouch.y;
            float f7 = this.endTouch.y;
            float f8 = this.startTouch.y;
            PointF pointF = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            TouchImageView.this.matrix.postTranslate(f3 + (f4 - f5) * f2 - pointF.x, f6 + (f7 - f8) * f2 - pointF.y);
        }

        @Override
        public void run() {
            float f2 = this.interpolate();
            float f3 = this.calculateDeltaScale(f2);
            TouchImageView.this.scaleImage(f3, this.bitmapX, this.bitmapY, this.stretchImageToSuper);
            this.translateImageToCenterTouchPosition(f2);
            TouchImageView.this.fixScaleTrans();
            TouchImageView.this.setImageMatrix(TouchImageView.this.matrix);
            if (f2 < 1.0f) {
                TouchImageView.this.compatPostOnAnimation(this);
                return;
            }
            TouchImageView.this.setState(State.NONE);
        }
    }

    private class Fling
    implements Runnable {
        int currX;
        int currY;
        Scroller scroller;

        /*
         * Enabled aggressive block sorting
         */
        Fling(int n2, int n3) {
            int n4;
            int n5;
            int n6;
            int n7;
            TouchImageView.this.setState(State.FLING);
            this.scroller = new Scroller(TouchImageView.this.context);
            TouchImageView.this.matrix.getValues(TouchImageView.this.m);
            int n8 = (int)TouchImageView.this.m[2];
            int n9 = (int)TouchImageView.this.m[5];
            if (TouchImageView.this.getImageWidth() > (float)TouchImageView.this.viewWidth) {
                n7 = TouchImageView.this.viewWidth - (int)TouchImageView.this.getImageWidth();
                n5 = 0;
            } else {
                n5 = n8;
                n7 = n8;
            }
            if (TouchImageView.this.getImageHeight() > (float)TouchImageView.this.viewHeight) {
                n6 = TouchImageView.this.viewHeight - (int)TouchImageView.this.getImageHeight();
                n4 = 0;
            } else {
                n4 = n9;
                n6 = n9;
            }
            this.scroller.fling(n8, n9, n2, n3, n7, n5, n6, n4);
            this.currX = n8;
            this.currY = n9;
        }

        public void cancelFling() {
            if (this.scroller != null) {
                TouchImageView.this.setState(State.NONE);
                this.scroller.forceFinished(true);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void run() {
            if (this.scroller.isFinished()) {
                this.scroller = null;
                return;
            } else {
                if (!this.scroller.computeScrollOffset()) return;
                {
                    int n2 = this.scroller.getCurrX();
                    int n3 = this.scroller.getCurrY();
                    int n4 = this.currX;
                    int n5 = this.currY;
                    this.currX = n2;
                    this.currY = n3;
                    TouchImageView.this.matrix.postTranslate((float)(n2 - n4), (float)(n3 - n5));
                    TouchImageView.this.fixTrans();
                    TouchImageView.this.setImageMatrix(TouchImageView.this.matrix);
                    TouchImageView.this.compatPostOnAnimation(this);
                    return;
                }
            }
        }
    }

    private class GestureListener
    extends GestureDetector.SimpleOnGestureListener {
        private GestureListener() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onDoubleTap(MotionEvent object) {
            boolean bl2 = false;
            if (TouchImageView.this.state != State.NONE) return bl2;
            float f2 = TouchImageView.this.normalizedScale == TouchImageView.this.minScale ? TouchImageView.this.maxScale : TouchImageView.this.minScale;
            object = new DoubleTapZoom(f2, object.getX(), object.getY(), false);
            TouchImageView.this.compatPostOnAnimation((Runnable)object);
            return true;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
            if (TouchImageView.this.fling != null) {
                TouchImageView.this.fling.cancelFling();
            }
            TouchImageView.this.fling = new Fling((int)f2, (int)f3);
            TouchImageView.this.compatPostOnAnimation(TouchImageView.this.fling);
            return super.onFling(motionEvent, motionEvent2, f2, f3);
        }

        public void onLongPress(MotionEvent motionEvent) {
            TouchImageView.this.performLongClick();
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return TouchImageView.this.performClick();
        }
    }

    private class ScaleListener
    extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            TouchImageView.this.scaleImage(scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), true);
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            TouchImageView.this.setState(State.ZOOM);
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onScaleEnd(ScaleGestureDetector object) {
            super.onScaleEnd((ScaleGestureDetector)object);
            TouchImageView.this.setState(State.NONE);
            boolean bl2 = false;
            float f2 = TouchImageView.this.normalizedScale;
            if (TouchImageView.this.normalizedScale > TouchImageView.this.maxScale) {
                f2 = TouchImageView.this.maxScale;
                bl2 = true;
            } else if (TouchImageView.this.normalizedScale < TouchImageView.this.minScale) {
                f2 = TouchImageView.this.minScale;
                bl2 = true;
            }
            if (bl2) {
                object = new DoubleTapZoom(f2, TouchImageView.this.viewWidth / 2, TouchImageView.this.viewHeight / 2, true);
                TouchImageView.this.compatPostOnAnimation((Runnable)object);
            }
        }
    }

    public static enum State {
        NONE,
        DRAG,
        ZOOM,
        FLING,
        ANIMATE_ZOOM;
        

        private State() {
        }
    }

    private class TouchImageViewListener
    implements View.OnTouchListener {
        private PointF last;

        private TouchImageViewListener() {
            this.last = new PointF();
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public boolean onTouch(View var1_1, MotionEvent var2_2) {
            block8 : {
                TouchImageView.access$900(TouchImageView.this).onTouchEvent(var2_2);
                TouchImageView.access$1000(TouchImageView.this).onTouchEvent(var2_2);
                var1_1 = new PointF(var2_2.getX(), var2_2.getY());
                if (TouchImageView.access$500(TouchImageView.this) == State.NONE || TouchImageView.access$500(TouchImageView.this) == State.DRAG || TouchImageView.access$500(TouchImageView.this) == State.FLING) {
                    switch (var2_2.getAction()) {
                        case 0: {
                            this.last.set((PointF)var1_1);
                            if (TouchImageView.access$300(TouchImageView.this) != null) {
                                TouchImageView.access$300(TouchImageView.this).cancelFling();
                            }
                            TouchImageView.access$1100(TouchImageView.this, State.DRAG);
                            ** break;
                        }
                        case 2: {
                            if (TouchImageView.access$500(TouchImageView.this) == State.DRAG) {
                                var5_3 = var1_1.x;
                                var6_4 = this.last.x;
                                var3_5 = var1_1.y;
                                var4_6 = this.last.y;
                                var5_3 = TouchImageView.access$1400(TouchImageView.this, var5_3 - var6_4, TouchImageView.access$1200(TouchImageView.this), TouchImageView.access$1300(TouchImageView.this));
                                var3_5 = TouchImageView.access$1400(TouchImageView.this, var3_5 - var4_6, TouchImageView.access$1500(TouchImageView.this), TouchImageView.access$1600(TouchImageView.this));
                                TouchImageView.access$1700(TouchImageView.this).postTranslate(var5_3, var3_5);
                                TouchImageView.access$1800(TouchImageView.this);
                                this.last.set(var1_1.x, var1_1.y);
                            }
                        }
lbl23: // 5 sources:
                        default: {
                            break block8;
                        }
                        case 1: 
                        case 6: 
                    }
                    TouchImageView.access$1100(TouchImageView.this, State.NONE);
                }
            }
            TouchImageView.this.setImageMatrix(TouchImageView.access$1700(TouchImageView.this));
            return true;
        }
    }

}

