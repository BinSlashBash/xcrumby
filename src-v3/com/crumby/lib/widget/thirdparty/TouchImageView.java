package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Scroller;
import com.crumby.GalleryViewer;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import org.json.zip.JSONzip;
import uk.co.senab.photoview.IPhotoView;

public class TouchImageView extends ImageView {
    private static final String DEBUG = "DEBUG";
    private static final float SUPER_MAX_MULTIPLIER = 1.25f;
    private static final float SUPER_MIN_MULTIPLIER = 0.75f;
    private Context context;
    private Fling fling;
    private float[] f4m;
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

    private class DoubleTapZoom implements Runnable {
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

        DoubleTapZoom(float targetZoom, float focusX, float focusY, boolean stretchImageToSuper) {
            this.interpolator = new AccelerateDecelerateInterpolator();
            TouchImageView.this.setState(State.ANIMATE_ZOOM);
            this.startTime = System.currentTimeMillis();
            this.startZoom = TouchImageView.this.normalizedScale;
            this.targetZoom = targetZoom;
            this.stretchImageToSuper = stretchImageToSuper;
            PointF bitmapPoint = TouchImageView.this.transformCoordTouchToBitmap(focusX, focusY, false);
            this.bitmapX = bitmapPoint.x;
            this.bitmapY = bitmapPoint.y;
            this.startTouch = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            this.endTouch = new PointF((float) (TouchImageView.this.viewWidth / 2), (float) (TouchImageView.this.viewHeight / 2));
        }

        public void run() {
            float t = interpolate();
            TouchImageView.this.scaleImage(calculateDeltaScale(t), this.bitmapX, this.bitmapY, this.stretchImageToSuper);
            translateImageToCenterTouchPosition(t);
            TouchImageView.this.fixScaleTrans();
            TouchImageView.this.setImageMatrix(TouchImageView.this.matrix);
            if (t < GalleryViewer.PROGRESS_COMPLETED) {
                TouchImageView.this.compatPostOnAnimation(this);
            } else {
                TouchImageView.this.setState(State.NONE);
            }
        }

        private void translateImageToCenterTouchPosition(float t) {
            float targetX = this.startTouch.x + ((this.endTouch.x - this.startTouch.x) * t);
            float targetY = this.startTouch.y + ((this.endTouch.y - this.startTouch.y) * t);
            PointF curr = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            TouchImageView.this.matrix.postTranslate(targetX - curr.x, targetY - curr.y);
        }

        private float interpolate() {
            return this.interpolator.getInterpolation(Math.min(GalleryViewer.PROGRESS_COMPLETED, ((float) (System.currentTimeMillis() - this.startTime)) / ZOOM_TIME));
        }

        private float calculateDeltaScale(float t) {
            return (this.startZoom + ((this.targetZoom - this.startZoom) * t)) / TouchImageView.this.normalizedScale;
        }
    }

    private class Fling implements Runnable {
        int currX;
        int currY;
        Scroller scroller;

        Fling(int velocityX, int velocityY) {
            int minX;
            int maxX;
            int minY;
            int maxY;
            TouchImageView.this.setState(State.FLING);
            this.scroller = new Scroller(TouchImageView.this.context);
            TouchImageView.this.matrix.getValues(TouchImageView.this.f4m);
            int startX = (int) TouchImageView.this.f4m[2];
            int startY = (int) TouchImageView.this.f4m[5];
            if (TouchImageView.this.getImageWidth() > ((float) TouchImageView.this.viewWidth)) {
                minX = TouchImageView.this.viewWidth - ((int) TouchImageView.this.getImageWidth());
                maxX = 0;
            } else {
                maxX = startX;
                minX = startX;
            }
            if (TouchImageView.this.getImageHeight() > ((float) TouchImageView.this.viewHeight)) {
                minY = TouchImageView.this.viewHeight - ((int) TouchImageView.this.getImageHeight());
                maxY = 0;
            } else {
                maxY = startY;
                minY = startY;
            }
            this.scroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
            this.currX = startX;
            this.currY = startY;
        }

        public void cancelFling() {
            if (this.scroller != null) {
                TouchImageView.this.setState(State.NONE);
                this.scroller.forceFinished(true);
            }
        }

        public void run() {
            if (this.scroller.isFinished()) {
                this.scroller = null;
            } else if (this.scroller.computeScrollOffset()) {
                int newX = this.scroller.getCurrX();
                int newY = this.scroller.getCurrY();
                int transX = newX - this.currX;
                int transY = newY - this.currY;
                this.currX = newX;
                this.currY = newY;
                TouchImageView.this.matrix.postTranslate((float) transX, (float) transY);
                TouchImageView.this.fixTrans();
                TouchImageView.this.setImageMatrix(TouchImageView.this.matrix);
                TouchImageView.this.compatPostOnAnimation(this);
            }
        }
    }

    private class GestureListener extends SimpleOnGestureListener {
        private GestureListener() {
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            return TouchImageView.this.performClick();
        }

        public void onLongPress(MotionEvent e) {
            TouchImageView.this.performLongClick();
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (TouchImageView.this.fling != null) {
                TouchImageView.this.fling.cancelFling();
            }
            TouchImageView.this.fling = new Fling((int) velocityX, (int) velocityY);
            TouchImageView.this.compatPostOnAnimation(TouchImageView.this.fling);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (TouchImageView.this.state != State.NONE) {
                return false;
            }
            TouchImageView.this.compatPostOnAnimation(new DoubleTapZoom(TouchImageView.this.normalizedScale == TouchImageView.this.minScale ? TouchImageView.this.maxScale : TouchImageView.this.minScale, e.getX(), e.getY(), false));
            return true;
        }
    }

    private class ScaleListener extends SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            TouchImageView.this.setState(State.ZOOM);
            return true;
        }

        public boolean onScale(ScaleGestureDetector detector) {
            TouchImageView.this.scaleImage(detector.getScaleFactor(), detector.getFocusX(), detector.getFocusY(), true);
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
            super.onScaleEnd(detector);
            TouchImageView.this.setState(State.NONE);
            boolean animateToZoomBoundary = false;
            float targetZoom = TouchImageView.this.normalizedScale;
            if (TouchImageView.this.normalizedScale > TouchImageView.this.maxScale) {
                targetZoom = TouchImageView.this.maxScale;
                animateToZoomBoundary = true;
            } else if (TouchImageView.this.normalizedScale < TouchImageView.this.minScale) {
                targetZoom = TouchImageView.this.minScale;
                animateToZoomBoundary = true;
            }
            if (animateToZoomBoundary) {
                TouchImageView.this.compatPostOnAnimation(new DoubleTapZoom(targetZoom, (float) (TouchImageView.this.viewWidth / 2), (float) (TouchImageView.this.viewHeight / 2), true));
            }
        }
    }

    public enum State {
        NONE,
        DRAG,
        ZOOM,
        FLING,
        ANIMATE_ZOOM
    }

    private class TouchImageViewListener implements OnTouchListener {
        private PointF last;

        private TouchImageViewListener() {
            this.last = new PointF();
        }

        public boolean onTouch(View v, MotionEvent event) {
            TouchImageView.this.mScaleDetector.onTouchEvent(event);
            TouchImageView.this.mGestureDetector.onTouchEvent(event);
            PointF curr = new PointF(event.getX(), event.getY());
            if (TouchImageView.this.state == State.NONE || TouchImageView.this.state == State.DRAG || TouchImageView.this.state == State.FLING) {
                switch (event.getAction()) {
                    case JSONzip.zipEmptyObject /*0*/:
                        this.last.set(curr);
                        if (TouchImageView.this.fling != null) {
                            TouchImageView.this.fling.cancelFling();
                        }
                        TouchImageView.this.setState(State.DRAG);
                        break;
                    case Std.STD_FILE /*1*/:
                    case Std.STD_CURRENCY /*6*/:
                        TouchImageView.this.setState(State.NONE);
                        break;
                    case Std.STD_URL /*2*/:
                        if (TouchImageView.this.state == State.DRAG) {
                            float deltaY = curr.y - this.last.y;
                            TouchImageView.this.matrix.postTranslate(TouchImageView.this.getFixDragTrans(curr.x - this.last.x, (float) TouchImageView.this.viewWidth, TouchImageView.this.getImageWidth()), TouchImageView.this.getFixDragTrans(deltaY, (float) TouchImageView.this.viewHeight, TouchImageView.this.getImageHeight()));
                            TouchImageView.this.fixTrans();
                            this.last.set(curr.x, curr.y);
                            break;
                        }
                        break;
                }
            }
            TouchImageView.this.setImageMatrix(TouchImageView.this.matrix);
            return true;
        }
    }

    public TouchImageView(Context context) {
        super(context);
        sharedConstructing(context);
    }

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructing(context);
    }

    public TouchImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        sharedConstructing(context);
    }

    private void sharedConstructing(Context context) {
        super.setClickable(true);
        this.context = context;
        this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        this.mGestureDetector = new GestureDetector(context, new GestureListener());
        this.matrix = new Matrix();
        this.prevMatrix = new Matrix();
        this.f4m = new float[9];
        this.normalizedScale = GalleryViewer.PROGRESS_COMPLETED;
        this.minScale = GalleryViewer.PROGRESS_COMPLETED;
        this.maxScale = IPhotoView.DEFAULT_MAX_SCALE;
        this.superMinScale = SUPER_MIN_MULTIPLIER * this.minScale;
        this.superMaxScale = SUPER_MAX_MULTIPLIER * this.maxScale;
        this.maintainZoomAfterSetImage = true;
        setImageMatrix(this.matrix);
        setScaleType(ScaleType.MATRIX);
        setState(State.NONE);
        setOnTouchListener(new TouchImageViewListener());
    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        setImageCalled();
        savePreviousImageValues();
        fitImageToView();
    }

    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        setImageCalled();
        savePreviousImageValues();
        fitImageToView();
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setImageCalled();
        savePreviousImageValues();
        fitImageToView();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setImageCalled();
        savePreviousImageValues();
        fitImageToView();
    }

    private void setImageCalled() {
        if (!this.maintainZoomAfterSetImage) {
            this.setImageCalledRecenterImage = true;
        }
    }

    private void savePreviousImageValues() {
        if (this.matrix != null) {
            this.matrix.getValues(this.f4m);
            this.prevMatrix.setValues(this.f4m);
            this.prevMatchViewHeight = this.matchViewHeight;
            this.prevMatchViewWidth = this.matchViewWidth;
            this.prevViewHeight = this.viewHeight;
            this.prevViewWidth = this.viewWidth;
        }
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putFloat("saveScale", this.normalizedScale);
        bundle.putFloat("matchViewHeight", this.matchViewHeight);
        bundle.putFloat("matchViewWidth", this.matchViewWidth);
        bundle.putInt("viewWidth", this.viewWidth);
        bundle.putInt("viewHeight", this.viewHeight);
        this.matrix.getValues(this.f4m);
        bundle.putFloatArray("matrix", this.f4m);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.normalizedScale = bundle.getFloat("saveScale");
            this.f4m = bundle.getFloatArray("matrix");
            this.prevMatrix.setValues(this.f4m);
            this.prevMatchViewHeight = bundle.getFloat("matchViewHeight");
            this.prevMatchViewWidth = bundle.getFloat("matchViewWidth");
            this.prevViewHeight = bundle.getInt("viewHeight");
            this.prevViewWidth = bundle.getInt("viewWidth");
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public float getMaxZoom() {
        return this.maxScale;
    }

    public void setMaxZoom(float max) {
        this.maxScale = max;
        this.superMaxScale = SUPER_MAX_MULTIPLIER * this.maxScale;
    }

    public float getMinZoom() {
        return this.minScale;
    }

    public void maintainZoomAfterSetImage(boolean maintainZoom) {
        this.maintainZoomAfterSetImage = maintainZoom;
    }

    public float getCurrentZoom() {
        return this.normalizedScale;
    }

    public void setMinZoom(float min) {
        this.minScale = min;
        this.superMinScale = SUPER_MIN_MULTIPLIER * this.minScale;
    }

    public PointF getDrawablePointFromTouchPoint(float x, float y) {
        return transformCoordTouchToBitmap(x, y, true);
    }

    public PointF getDrawablePointFromTouchPoint(PointF p) {
        return transformCoordTouchToBitmap(p.x, p.y, true);
    }

    private void fixTrans() {
        this.matrix.getValues(this.f4m);
        float transX = this.f4m[2];
        float transY = this.f4m[5];
        float fixTransX = getFixTrans(transX, (float) this.viewWidth, getImageWidth());
        float fixTransY = getFixTrans(transY, (float) this.viewHeight, getImageHeight());
        if (fixTransX != 0.0f || fixTransY != 0.0f) {
            this.matrix.postTranslate(fixTransX, fixTransY);
        }
    }

    private void fixScaleTrans() {
        fixTrans();
        this.matrix.getValues(this.f4m);
        if (getImageWidth() < ((float) this.viewWidth)) {
            this.f4m[2] = (((float) this.viewWidth) - getImageWidth()) / 2.0f;
        }
        if (getImageHeight() < ((float) this.viewHeight)) {
            this.f4m[5] = (((float) this.viewHeight) - getImageHeight()) / 2.0f;
        }
        this.matrix.setValues(this.f4m);
    }

    private float getFixTrans(float trans, float viewSize, float contentSize) {
        float minTrans;
        float maxTrans;
        if (contentSize <= viewSize) {
            minTrans = 0.0f;
            maxTrans = viewSize - contentSize;
        } else {
            minTrans = viewSize - contentSize;
            maxTrans = 0.0f;
        }
        if (trans < minTrans) {
            return (-trans) + minTrans;
        }
        if (trans > maxTrans) {
            return (-trans) + maxTrans;
        }
        return 0.0f;
    }

    private float getFixDragTrans(float delta, float viewSize, float contentSize) {
        if (contentSize <= viewSize) {
            return 0.0f;
        }
        return delta;
    }

    private float getImageWidth() {
        return this.matchViewWidth * this.normalizedScale;
    }

    private float getImageHeight() {
        return this.matchViewHeight * this.normalizedScale;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        this.viewWidth = setViewSize(widthMode, widthSize, drawableWidth);
        this.viewHeight = setViewSize(heightMode, heightSize, drawableHeight);
        setMeasuredDimension(this.viewWidth, this.viewHeight);
        fitImageToView();
    }

    private void fitImageToView() {
        Drawable drawable = getDrawable();
        if (drawable != null && drawable.getIntrinsicWidth() != 0 && drawable.getIntrinsicHeight() != 0 && this.matrix != null && this.prevMatrix != null) {
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();
            float scale = Math.min(((float) this.viewWidth) / ((float) drawableWidth), ((float) this.viewHeight) / ((float) drawableHeight));
            float redundantYSpace = ((float) this.viewHeight) - (((float) drawableHeight) * scale);
            float redundantXSpace = ((float) this.viewWidth) - (((float) drawableWidth) * scale);
            this.matchViewWidth = ((float) this.viewWidth) - redundantXSpace;
            this.matchViewHeight = ((float) this.viewHeight) - redundantYSpace;
            if (this.normalizedScale == GalleryViewer.PROGRESS_COMPLETED || this.setImageCalledRecenterImage) {
                this.matrix.setScale(scale, scale);
                this.matrix.postTranslate(redundantXSpace / 2.0f, redundantYSpace / 2.0f);
                this.normalizedScale = GalleryViewer.PROGRESS_COMPLETED;
                this.setImageCalledRecenterImage = false;
            } else {
                this.prevMatrix.getValues(this.f4m);
                this.f4m[0] = (this.matchViewWidth / ((float) drawableWidth)) * this.normalizedScale;
                this.f4m[4] = (this.matchViewHeight / ((float) drawableHeight)) * this.normalizedScale;
                float transX = this.f4m[2];
                float transY = this.f4m[5];
                translateMatrixAfterRotate(2, transX, this.prevMatchViewWidth * this.normalizedScale, getImageWidth(), this.prevViewWidth, this.viewWidth, drawableWidth);
                translateMatrixAfterRotate(5, transY, this.prevMatchViewHeight * this.normalizedScale, getImageHeight(), this.prevViewHeight, this.viewHeight, drawableHeight);
                this.matrix.setValues(this.f4m);
            }
            setImageMatrix(this.matrix);
        }
    }

    private int setViewSize(int mode, int size, int drawableWidth) {
        switch (mode) {
            case ExploreByTouchHelper.INVALID_ID /*-2147483648*/:
                return Math.min(drawableWidth, size);
            case JSONzip.zipEmptyObject /*0*/:
                return drawableWidth;
            case 1073741824:
                return size;
            default:
                return size;
        }
    }

    private void translateMatrixAfterRotate(int axis, float trans, float prevImageSize, float imageSize, int prevViewSize, int viewSize, int drawableSize) {
        if (imageSize < ((float) viewSize)) {
            this.f4m[axis] = (((float) viewSize) - (((float) drawableSize) * this.f4m[0])) * 0.5f;
        } else if (trans > 0.0f) {
            this.f4m[axis] = -((imageSize - ((float) viewSize)) * 0.5f);
        } else {
            this.f4m[axis] = -((((Math.abs(trans) + (((float) prevViewSize) * 0.5f)) / prevImageSize) * imageSize) - (((float) viewSize) * 0.5f));
        }
    }

    private void setState(State state) {
        this.state = state;
    }

    private void scaleImage(float deltaScale, float focusX, float focusY, boolean stretchImageToSuper) {
        float lowerScale;
        float upperScale;
        if (stretchImageToSuper) {
            lowerScale = this.superMinScale;
            upperScale = this.superMaxScale;
        } else {
            lowerScale = this.minScale;
            upperScale = this.maxScale;
        }
        float origScale = this.normalizedScale;
        this.normalizedScale *= deltaScale;
        if (this.normalizedScale > upperScale) {
            this.normalizedScale = upperScale;
            deltaScale = upperScale / origScale;
        } else if (this.normalizedScale < lowerScale) {
            this.normalizedScale = lowerScale;
            deltaScale = lowerScale / origScale;
        }
        this.matrix.postScale(deltaScale, deltaScale, focusX, focusY);
        fixScaleTrans();
    }

    private PointF transformCoordTouchToBitmap(float x, float y, boolean clipToBitmap) {
        this.matrix.getValues(this.f4m);
        float origW = (float) getDrawable().getIntrinsicWidth();
        float origH = (float) getDrawable().getIntrinsicHeight();
        float transX = this.f4m[2];
        float finalX = ((x - transX) * origW) / getImageWidth();
        float finalY = ((y - this.f4m[5]) * origH) / getImageHeight();
        if (clipToBitmap) {
            finalX = Math.min(Math.max(x, 0.0f), origW);
            finalY = Math.min(Math.max(y, 0.0f), origH);
        }
        return new PointF(finalX, finalY);
    }

    private PointF transformCoordBitmapToTouch(float bx, float by) {
        this.matrix.getValues(this.f4m);
        return new PointF(this.f4m[2] + (getImageWidth() * (bx / ((float) getDrawable().getIntrinsicWidth()))), this.f4m[5] + (getImageHeight() * (by / ((float) getDrawable().getIntrinsicHeight()))));
    }

    private void compatPostOnAnimation(Runnable runnable) {
        if (VERSION.SDK_INT >= 16) {
            postOnAnimation(runnable);
        } else {
            postDelayed(runnable, 16);
        }
    }

    private void printMatrixInfo() {
        this.matrix.getValues(this.f4m);
        Log.d(DEBUG, "Scale: " + this.f4m[0] + " TransX: " + this.f4m[2] + " TransY: " + this.f4m[5]);
    }
}
