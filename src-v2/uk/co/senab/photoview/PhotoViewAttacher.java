/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Matrix
 *  android.graphics.Matrix$ScaleToFit
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.util.FloatMath
 *  android.util.Log
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnLongClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewParent
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package uk.co.senab.photoview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import java.lang.ref.WeakReference;
import uk.co.senab.photoview.Compat;
import uk.co.senab.photoview.DefaultOnDoubleTapListener;
import uk.co.senab.photoview.ExpandContainerScaleListener;
import uk.co.senab.photoview.IPhotoView;
import uk.co.senab.photoview.gestures.GestureDetector;
import uk.co.senab.photoview.gestures.OnGestureListener;
import uk.co.senab.photoview.gestures.VersionedGestureDetector;
import uk.co.senab.photoview.log.LogManager;
import uk.co.senab.photoview.scrollerproxy.ScrollerProxy;

public class PhotoViewAttacher
implements IPhotoView,
View.OnTouchListener,
OnGestureListener,
ViewTreeObserver.OnGlobalLayoutListener {
    private static final boolean DEBUG = Log.isLoggable((String)"PhotoViewAttacher", (int)3);
    static final int EDGE_BOTH = 2;
    static final int EDGE_LEFT = 0;
    static final int EDGE_NONE = -1;
    static final int EDGE_RIGHT = 1;
    private static final String LOG_TAG = "PhotoViewAttacher";
    static final Interpolator sInterpolator = new AccelerateDecelerateInterpolator();
    int ZOOM_DURATION = 200;
    ExpandContainerScaleListener expandContainerScaleListener;
    private boolean mAllowParentInterceptOnEdge = true;
    private final Matrix mBaseMatrix = new Matrix();
    private FlingRunnable mCurrentFlingRunnable;
    private final RectF mDisplayRect = new RectF();
    private final Matrix mDrawMatrix = new Matrix();
    private android.view.GestureDetector mGestureDetector;
    private WeakReference<ImageView> mImageView;
    private int mIvBottom;
    private int mIvLeft;
    private int mIvRight;
    private int mIvTop;
    private View.OnLongClickListener mLongClickListener;
    private OnMatrixChangedListener mMatrixChangeListener;
    private final float[] mMatrixValues = new float[9];
    private float mMaxScale = 3.0f;
    private float mMidScale = 1.75f;
    private float mMinScale = 1.0f;
    private OnPhotoTapListener mPhotoTapListener;
    private GestureDetector mScaleDragDetector;
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;
    private int mScrollEdge = 2;
    private final Matrix mSuppMatrix = new Matrix();
    private OnViewTapListener mViewTapListener;
    private boolean mZoomEnabled;

    @TargetApi(value=3)
    public PhotoViewAttacher(ImageView imageView) {
        this.mImageView = new WeakReference<ImageView>(imageView);
        imageView.setDrawingCacheEnabled(true);
        imageView.setOnTouchListener((View.OnTouchListener)this);
        ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
        }
        PhotoViewAttacher.setImageViewScaleTypeMatrix(imageView);
        if (imageView.isInEditMode()) {
            return;
        }
        this.mScaleDragDetector = VersionedGestureDetector.newInstance(imageView.getContext(), this);
        this.mGestureDetector = new android.view.GestureDetector(imageView.getContext(), (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener(){

            public void onLongPress(MotionEvent motionEvent) {
                if (PhotoViewAttacher.this.mLongClickListener != null) {
                    PhotoViewAttacher.this.mLongClickListener.onLongClick((View)PhotoViewAttacher.this.getImageView());
                }
            }
        });
        this.mGestureDetector.setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)new DefaultOnDoubleTapListener(this));
        this.setZoomable(true);
    }

    private void cancelFling() {
        if (this.mCurrentFlingRunnable != null) {
            this.mCurrentFlingRunnable.cancelFling();
            this.mCurrentFlingRunnable = null;
        }
    }

    private void checkAndDisplayMatrix() {
        if (this.checkMatrixBounds()) {
            this.setImageViewMatrix(this.getDrawMatrix());
        }
    }

    private void checkImageViewScaleType() {
        ImageView imageView = this.getImageView();
        if (imageView != null && !(imageView instanceof IPhotoView) && !ImageView.ScaleType.MATRIX.equals((Object)imageView.getScaleType())) {
            throw new IllegalStateException("The ImageView's ScaleType has been changed since attaching a PhotoViewAttacher");
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean checkMatrixBounds() {
        var6_1 = this.getImageView();
        if (var6_1 == null) {
            return false;
        }
        var7_2 = this.getDisplayRect(this.getDrawMatrix());
        if (var7_2 == null) return false;
        var4_3 = var7_2.height();
        var3_4 = var7_2.width();
        var2_5 = 0.0f;
        var1_6 = 0.0f;
        var5_7 = this.getImageViewHeight(var6_1);
        if (var4_3 > (float)var5_7) ** GOTO lbl23
        switch (.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
            default: {
                var1_6 = ((float)var5_7 - var4_3) / 2.0f - var7_2.top;
                ** GOTO lbl28
            }
            case 2: {
                var1_6 = - var7_2.top;
                ** break;
            }
            case 3: 
        }
        var1_6 = (float)var5_7 - var4_3 - var7_2.top;
        ** break;
lbl22: // 2 sources:
        ** GOTO lbl28
lbl23: // 1 sources:
        if (var7_2.top > 0.0f) {
            var1_6 = - var7_2.top;
        } else if (var7_2.bottom < (float)var5_7) {
            var1_6 = (float)var5_7 - var7_2.bottom;
        }
lbl28: // 6 sources:
        if (var3_4 <= (float)(var5_7 = this.getImageViewWidth(var6_1))) {
            switch (.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
                default: {
                    var2_5 = ((float)var5_7 - var3_4) / 2.0f - var7_2.left;
                    break;
                }
                case 2: {
                    var2_5 = - var7_2.left;
                    break;
                }
                case 3: {
                    var2_5 = (float)var5_7 - var3_4 - var7_2.left;
                }
            }
            this.mScrollEdge = 2;
        } else if (var7_2.left > 0.0f) {
            this.mScrollEdge = 0;
            var2_5 = - var7_2.left;
        } else if (var7_2.right < (float)var5_7) {
            var2_5 = (float)var5_7 - var7_2.right;
            this.mScrollEdge = 1;
        } else {
            this.mScrollEdge = -1;
        }
        this.mSuppMatrix.postTranslate(var2_5, var1_6);
        return true;
    }

    private static void checkZoomLevels(float f2, float f3, float f4) {
        if (f2 >= f3) {
            throw new IllegalArgumentException("MinZoom has to be less than MidZoom");
        }
        if (f3 >= f4) {
            throw new IllegalArgumentException("MidZoom has to be less than MaxZoom");
        }
    }

    private RectF getDisplayRect(Matrix matrix) {
        ImageView imageView = this.getImageView();
        if (imageView != null && (imageView = imageView.getDrawable()) != null) {
            this.mDisplayRect.set(0.0f, 0.0f, (float)imageView.getIntrinsicWidth(), (float)imageView.getIntrinsicHeight());
            matrix.mapRect(this.mDisplayRect);
            return this.mDisplayRect;
        }
        return null;
    }

    private int getImageViewHeight(ImageView imageView) {
        if (imageView == null) {
            return 0;
        }
        return imageView.getHeight() - imageView.getPaddingTop() - imageView.getPaddingBottom();
    }

    private int getImageViewWidth(ImageView imageView) {
        if (imageView == null) {
            return 0;
        }
        return imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
    }

    private float getValue(Matrix matrix, int n2) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[n2];
    }

    private static boolean hasDrawable(ImageView imageView) {
        if (imageView != null && imageView.getDrawable() != null) {
            return true;
        }
        return false;
    }

    private static boolean isSupportedScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            return false;
        }
        switch (scaleType) {
            default: {
                return true;
            }
            case MATRIX: 
        }
        throw new IllegalArgumentException(scaleType.name() + " is not supported in PhotoView");
    }

    private void resetMatrix() {
        this.mSuppMatrix.reset();
        this.setImageViewMatrix(this.getDrawMatrix());
        this.checkMatrixBounds();
    }

    private void setImageViewMatrix(Matrix matrix) {
        ImageView imageView = this.getImageView();
        if (imageView != null) {
            this.checkImageViewScaleType();
            imageView.setImageMatrix(matrix);
            if (this.mMatrixChangeListener != null && (matrix = this.getDisplayRect(matrix)) != null) {
                this.mMatrixChangeListener.onMatrixChanged((RectF)matrix);
            }
        }
    }

    private static void setImageViewScaleTypeMatrix(ImageView imageView) {
        if (imageView != null && !(imageView instanceof IPhotoView) && !ImageView.ScaleType.MATRIX.equals((Object)imageView.getScaleType())) {
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateBaseMatrix(Drawable drawable2) {
        ImageView imageView = this.getImageView();
        if (imageView == null || drawable2 == null) {
            return;
        }
        float f2 = this.getImageViewWidth(imageView);
        float f3 = this.getImageViewHeight(imageView);
        int n2 = drawable2.getIntrinsicWidth();
        int n3 = drawable2.getIntrinsicHeight();
        this.mBaseMatrix.reset();
        float f4 = f2 / (float)n2;
        float f5 = f3 / (float)n3;
        if (this.mScaleType == ImageView.ScaleType.CENTER) {
            this.mBaseMatrix.postTranslate((f2 - (float)n2) / 2.0f, (f3 - (float)n3) / 2.0f);
        } else if (this.mScaleType == ImageView.ScaleType.CENTER_CROP) {
            f4 = Math.max(f4, f5);
            this.mBaseMatrix.postScale(f4, f4);
            this.mBaseMatrix.postTranslate((f2 - (float)n2 * f4) / 2.0f, (f3 - (float)n3 * f4) / 2.0f);
        } else if (this.mScaleType == ImageView.ScaleType.CENTER_INSIDE) {
            f4 = Math.min(1.0f, Math.min(f4, f5));
            this.mBaseMatrix.postScale(f4, f4);
            this.mBaseMatrix.postTranslate((f2 - (float)n2 * f4) / 2.0f, (f3 - (float)n3 * f4) / 2.0f);
        } else {
            drawable2 = new RectF(0.0f, 0.0f, (float)n2, (float)n3);
            imageView = new RectF(0.0f, 0.0f, f2, f3);
            switch (.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
                default: {
                    break;
                }
                case 2: {
                    this.mBaseMatrix.setRectToRect((RectF)drawable2, (RectF)imageView, Matrix.ScaleToFit.START);
                    break;
                }
                case 4: {
                    this.mBaseMatrix.setRectToRect((RectF)drawable2, (RectF)imageView, Matrix.ScaleToFit.CENTER);
                    break;
                }
                case 3: {
                    this.mBaseMatrix.setRectToRect((RectF)drawable2, (RectF)imageView, Matrix.ScaleToFit.END);
                    break;
                }
                case 5: {
                    this.mBaseMatrix.setRectToRect((RectF)drawable2, (RectF)imageView, Matrix.ScaleToFit.FILL);
                }
            }
        }
        this.resetMatrix();
    }

    @Override
    public boolean canZoom() {
        return this.mZoomEnabled;
    }

    public void cleanup() {
        if (this.mImageView == null) {
            return;
        }
        ImageView imageView = this.mImageView.get();
        if (imageView != null) {
            ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
            if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
                viewTreeObserver.removeGlobalOnLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
            }
            imageView.setOnTouchListener(null);
            this.cancelFling();
        }
        if (this.mGestureDetector != null) {
            this.mGestureDetector.setOnDoubleTapListener(null);
        }
        this.mMatrixChangeListener = null;
        this.mPhotoTapListener = null;
        this.mViewTapListener = null;
        this.mImageView = null;
    }

    @Override
    public Matrix getDisplayMatrix() {
        return new Matrix(this.getDrawMatrix());
    }

    @Override
    public RectF getDisplayRect() {
        this.checkMatrixBounds();
        return this.getDisplayRect(this.getDrawMatrix());
    }

    public Matrix getDrawMatrix() {
        this.mDrawMatrix.set(this.mBaseMatrix);
        this.mDrawMatrix.postConcat(this.mSuppMatrix);
        return this.mDrawMatrix;
    }

    @Override
    public IPhotoView getIPhotoViewImplementation() {
        return this;
    }

    public ImageView getImageView() {
        ImageView imageView = null;
        if (this.mImageView != null) {
            imageView = this.mImageView.get();
        }
        if (imageView == null) {
            this.cleanup();
            Log.i((String)"PhotoViewAttacher", (String)"ImageView no longer exists. You should not use this PhotoViewAttacher any more.");
        }
        return imageView;
    }

    @Deprecated
    @Override
    public float getMaxScale() {
        return this.getMaximumScale();
    }

    @Override
    public float getMaximumScale() {
        return this.mMaxScale;
    }

    @Override
    public float getMediumScale() {
        return this.mMidScale;
    }

    @Deprecated
    @Override
    public float getMidScale() {
        return this.getMediumScale();
    }

    @Deprecated
    @Override
    public float getMinScale() {
        return this.getMinimumScale();
    }

    @Override
    public float getMinimumScale() {
        return this.mMinScale;
    }

    @Override
    public OnPhotoTapListener getOnPhotoTapListener() {
        return this.mPhotoTapListener;
    }

    @Override
    public OnViewTapListener getOnViewTapListener() {
        return this.mViewTapListener;
    }

    @Override
    public float getScale() {
        return FloatMath.sqrt((float)((float)Math.pow(this.getValue(this.mSuppMatrix, 0), 2.0) + (float)Math.pow(this.getValue(this.mSuppMatrix, 3), 2.0)));
    }

    @Override
    public ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    @Override
    public Bitmap getVisibleRectangleBitmap() {
        ImageView imageView = this.getImageView();
        if (imageView == null) {
            return null;
        }
        return imageView.getDrawingCache();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onDrag(float f2, float f3) {
        if (this.mScaleDragDetector.isScaling()) return;
        if (DEBUG) {
            LogManager.getLogger().d("PhotoViewAttacher", String.format("onDrag: dx: %.2f. dy: %.2f", Float.valueOf(f2), Float.valueOf(f3)));
        }
        ImageView imageView = this.getImageView();
        this.mSuppMatrix.postTranslate(f2, f3);
        this.checkAndDisplayMatrix();
        imageView = imageView.getParent();
        if (this.mAllowParentInterceptOnEdge && !this.mScaleDragDetector.isScaling()) {
            if (this.mScrollEdge != 2 && (this.mScrollEdge != 0 || f2 < 1.0f) && (this.mScrollEdge != 1 || f2 > -1.0f) || imageView == null) return;
            {
                imageView.requestDisallowInterceptTouchEvent(false);
                return;
            }
        }
        if (imageView == null) {
            return;
        }
        imageView.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onFling(float f2, float f3, float f4, float f5) {
        if (DEBUG) {
            LogManager.getLogger().d("PhotoViewAttacher", "onFling. sX: " + f2 + " sY: " + f3 + " Vx: " + f4 + " Vy: " + f5);
        }
        ImageView imageView = this.getImageView();
        this.mCurrentFlingRunnable = new FlingRunnable(imageView.getContext());
        this.mCurrentFlingRunnable.fling(this.getImageViewWidth(imageView), this.getImageViewHeight(imageView), (int)f4, (int)f5);
        imageView.post((Runnable)this.mCurrentFlingRunnable);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onGlobalLayout() {
        ImageView imageView = this.getImageView();
        if (imageView == null) return;
        if (this.mZoomEnabled) {
            int n2 = imageView.getTop();
            int n3 = imageView.getRight();
            int n4 = imageView.getBottom();
            int n5 = imageView.getLeft();
            if (n2 == this.mIvTop && n4 == this.mIvBottom && n5 == this.mIvLeft && n3 == this.mIvRight) return;
            this.updateBaseMatrix(imageView.getDrawable());
            this.mIvTop = n2;
            this.mIvRight = n3;
            this.mIvBottom = n4;
            this.mIvLeft = n5;
            return;
        }
        this.updateBaseMatrix(imageView.getDrawable());
    }

    @Override
    public void onScale(float f2, float f3, float f4) {
        if (DEBUG) {
            LogManager.getLogger().d("PhotoViewAttacher", String.format("onScale: scale: %.2f. fX: %.2f. fY: %.2f", Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4)));
        }
        if (this.expandContainerScaleListener.expand()) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "scale to full screen", null);
        }
        if (this.getScale() < this.mMaxScale || f2 < 1.0f) {
            this.mSuppMatrix.postScale(f2, f2, f3, f4);
            this.checkAndDisplayMatrix();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = bl2;
        if (!this.mZoomEnabled) return bl4;
        bl4 = bl2;
        if (!PhotoViewAttacher.hasDrawable((ImageView)view)) return bl4;
        ViewParent viewParent = view.getParent();
        bl4 = bl3;
        switch (motionEvent.getAction()) {
            default: {
                bl4 = bl3;
                break;
            }
            case 0: {
                if (viewParent != null) {
                    viewParent.requestDisallowInterceptTouchEvent(true);
                } else {
                    Log.i((String)"PhotoViewAttacher", (String)"onTouch getParent() returned null");
                }
                this.cancelFling();
                bl4 = bl3;
            }
            case 2: {
                break;
            }
            case 1: 
            case 3: {
                bl4 = bl3;
                if (this.getScale() >= this.mMinScale) break;
                viewParent = this.getDisplayRect();
                bl4 = bl3;
                if (viewParent == null) break;
                view.post((Runnable)new AnimatedZoomRunnable(this.getScale(), this.mMinScale, viewParent.centerX(), viewParent.centerY()));
                bl4 = true;
            }
        }
        bl3 = bl4;
        if (this.mScaleDragDetector != null) {
            bl3 = bl4;
            if (this.mScaleDragDetector.onTouchEvent(motionEvent)) {
                bl3 = true;
            }
        }
        bl4 = bl3;
        if (this.mGestureDetector == null) return bl4;
        bl4 = bl3;
        if (!this.mGestureDetector.onTouchEvent(motionEvent)) return bl4;
        return true;
    }

    @Override
    public void setAllowParentInterceptOnEdge(boolean bl2) {
        this.mAllowParentInterceptOnEdge = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean setDisplayMatrix(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        }
        ImageView imageView = this.getImageView();
        if (imageView == null || imageView.getDrawable() == null) {
            return false;
        }
        this.mSuppMatrix.set(matrix);
        this.setImageViewMatrix(this.getDrawMatrix());
        this.checkMatrixBounds();
        return true;
    }

    public void setExpandContainerScaleListener(ExpandContainerScaleListener expandContainerScaleListener) {
        this.expandContainerScaleListener = expandContainerScaleListener;
    }

    @Deprecated
    @Override
    public void setMaxScale(float f2) {
        this.setMaximumScale(f2);
    }

    @Override
    public void setMaximumScale(float f2) {
        PhotoViewAttacher.checkZoomLevels(this.mMinScale, this.mMidScale, f2);
        this.mMaxScale = f2;
    }

    @Override
    public void setMediumScale(float f2) {
        PhotoViewAttacher.checkZoomLevels(this.mMinScale, f2, this.mMaxScale);
        this.mMidScale = f2;
    }

    @Deprecated
    @Override
    public void setMidScale(float f2) {
        this.setMediumScale(f2);
    }

    @Deprecated
    @Override
    public void setMinScale(float f2) {
        this.setMinimumScale(f2);
    }

    @Override
    public void setMinimumScale(float f2) {
        PhotoViewAttacher.checkZoomLevels(f2, this.mMidScale, this.mMaxScale);
        this.mMinScale = f2;
    }

    @Override
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        if (onDoubleTapListener != null) {
            this.mGestureDetector.setOnDoubleTapListener(onDoubleTapListener);
            return;
        }
        this.mGestureDetector.setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)new DefaultOnDoubleTapListener(this));
    }

    @Override
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mLongClickListener = onLongClickListener;
    }

    @Override
    public void setOnMatrixChangeListener(OnMatrixChangedListener onMatrixChangedListener) {
        this.mMatrixChangeListener = onMatrixChangedListener;
    }

    @Override
    public void setOnPhotoTapListener(OnPhotoTapListener onPhotoTapListener) {
        this.mPhotoTapListener = onPhotoTapListener;
    }

    @Override
    public void setOnViewTapListener(OnViewTapListener onViewTapListener) {
        this.mViewTapListener = onViewTapListener;
    }

    @Override
    public void setPhotoViewRotation(float f2) {
        this.mSuppMatrix.setRotate(f2 % 360.0f);
        this.checkAndDisplayMatrix();
    }

    @Override
    public void setRotationBy(float f2) {
        this.mSuppMatrix.postRotate(f2 % 360.0f);
        this.checkAndDisplayMatrix();
    }

    @Override
    public void setRotationTo(float f2) {
        this.mSuppMatrix.setRotate(f2 % 360.0f);
        this.checkAndDisplayMatrix();
    }

    @Override
    public void setScale(float f2) {
        this.setScale(f2, false);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void setScale(float f2, float f3, float f4, boolean bl2) {
        ImageView imageView = this.getImageView();
        if (imageView == null) return;
        if (f2 < this.mMinScale || f2 > this.mMaxScale) {
            LogManager.getLogger().i("PhotoViewAttacher", "Scale must be within the range of minScale and maxScale");
            return;
        }
        if (bl2) {
            imageView.post((Runnable)new AnimatedZoomRunnable(this.getScale(), f2, f3, f4));
            return;
        }
        this.mSuppMatrix.setScale(f2, f2, f3, f4);
        this.checkAndDisplayMatrix();
    }

    @Override
    public void setScale(float f2, boolean bl2) {
        ImageView imageView = this.getImageView();
        if (imageView != null) {
            this.setScale(f2, imageView.getRight() / 2, imageView.getBottom() / 2, bl2);
        }
    }

    @Override
    public void setScaleType(ImageView.ScaleType scaleType) {
        if (PhotoViewAttacher.isSupportedScaleType(scaleType) && scaleType != this.mScaleType) {
            this.mScaleType = scaleType;
            this.update();
        }
    }

    @Override
    public void setZoomTransitionDuration(int n2) {
        int n3 = n2;
        if (n2 < 0) {
            n3 = 200;
        }
        this.ZOOM_DURATION = n3;
    }

    @Override
    public void setZoomable(boolean bl2) {
        this.mZoomEnabled = bl2;
        this.update();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void update() {
        ImageView imageView = this.getImageView();
        if (imageView == null) return;
        if (this.mZoomEnabled) {
            PhotoViewAttacher.setImageViewScaleTypeMatrix(imageView);
            this.updateBaseMatrix(imageView.getDrawable());
            return;
        }
        this.resetMatrix();
    }

    private class AnimatedZoomRunnable
    implements Runnable {
        private final float mFocalX;
        private final float mFocalY;
        private final long mStartTime;
        private final float mZoomEnd;
        private final float mZoomStart;

        public AnimatedZoomRunnable(float f2, float f3, float f4, float f5) {
            this.mFocalX = f4;
            this.mFocalY = f5;
            this.mStartTime = System.currentTimeMillis();
            this.mZoomStart = f2;
            this.mZoomEnd = f3;
        }

        private float interpolate() {
            float f2 = Math.min(1.0f, (float)(System.currentTimeMillis() - this.mStartTime) * 1.0f / (float)PhotoViewAttacher.this.ZOOM_DURATION);
            return PhotoViewAttacher.sInterpolator.getInterpolation(f2);
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            ImageView imageView = PhotoViewAttacher.this.getImageView();
            if (imageView == null) {
                return;
            }
            float f2 = this.interpolate();
            float f3 = (this.mZoomStart + (this.mZoomEnd - this.mZoomStart) * f2) / PhotoViewAttacher.this.getScale();
            PhotoViewAttacher.this.mSuppMatrix.postScale(f3, f3, this.mFocalX, this.mFocalY);
            PhotoViewAttacher.this.checkAndDisplayMatrix();
            if (f2 >= 1.0f) return;
            Compat.postOnAnimation((View)imageView, this);
        }
    }

    private class FlingRunnable
    implements Runnable {
        private int mCurrentX;
        private int mCurrentY;
        private final ScrollerProxy mScroller;

        public FlingRunnable(Context context) {
            this.mScroller = ScrollerProxy.getScroller(context);
        }

        public void cancelFling() {
            if (DEBUG) {
                LogManager.getLogger().d("PhotoViewAttacher", "Cancel Fling");
            }
            this.mScroller.forceFinished(true);
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public void fling(int n2, int n3, int n4, int n5) {
            int n6;
            int n7;
            int n8;
            RectF rectF = PhotoViewAttacher.this.getDisplayRect();
            if (rectF == null) {
                return;
            }
            int n9 = Math.round(- rectF.left);
            if ((float)n2 < rectF.width()) {
                n8 = 0;
                n7 = Math.round(rectF.width() - (float)n2);
                n2 = n8;
            } else {
                n7 = n9;
                n2 = n9;
            }
            n8 = Math.round(- rectF.top);
            if ((float)n3 < rectF.height()) {
                int n10 = 0;
                n6 = Math.round(rectF.height() - (float)n3);
                n3 = n10;
            } else {
                n6 = n8;
                n3 = n8;
            }
            this.mCurrentX = n9;
            this.mCurrentY = n8;
            if (DEBUG) {
                LogManager.getLogger().d("PhotoViewAttacher", "fling. StartX:" + n9 + " StartY:" + n8 + " MaxX:" + n7 + " MaxY:" + n6);
            }
            if (n9 == n7) {
                if (n8 == n6) return;
            }
            this.mScroller.fling(n9, n8, n4, n5, n2, n7, n3, n6, 0, 0);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void run() {
            ImageView imageView;
            if (this.mScroller.isFinished() || (imageView = PhotoViewAttacher.this.getImageView()) == null || !this.mScroller.computeScrollOffset()) {
                return;
            }
            int n2 = this.mScroller.getCurrX();
            int n3 = this.mScroller.getCurrY();
            if (DEBUG) {
                LogManager.getLogger().d("PhotoViewAttacher", "fling run(). CurrentX:" + this.mCurrentX + " CurrentY:" + this.mCurrentY + " NewX:" + n2 + " NewY:" + n3);
            }
            PhotoViewAttacher.this.mSuppMatrix.postTranslate((float)(this.mCurrentX - n2), (float)(this.mCurrentY - n3));
            PhotoViewAttacher.this.setImageViewMatrix(PhotoViewAttacher.this.getDrawMatrix());
            this.mCurrentX = n2;
            this.mCurrentY = n3;
            Compat.postOnAnimation((View)imageView, this);
        }
    }

    public static interface OnMatrixChangedListener {
        public void onMatrixChanged(RectF var1);
    }

    public static interface OnPhotoTapListener {
        public void onPhotoTap(View var1, float var2, float var3);
    }

    public static interface OnViewTapListener {
        public void onViewTap(View var1, float var2, float var3);
    }

}

