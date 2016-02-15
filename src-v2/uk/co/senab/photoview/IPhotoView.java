/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Matrix
 *  android.graphics.RectF
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.View
 *  android.view.View$OnLongClickListener
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package uk.co.senab.photoview;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import uk.co.senab.photoview.PhotoViewAttacher;

public interface IPhotoView {
    public static final float DEFAULT_MAX_SCALE = 3.0f;
    public static final float DEFAULT_MID_SCALE = 1.75f;
    public static final float DEFAULT_MIN_SCALE = 1.0f;
    public static final int DEFAULT_ZOOM_DURATION = 200;

    public boolean canZoom();

    public Matrix getDisplayMatrix();

    public RectF getDisplayRect();

    public IPhotoView getIPhotoViewImplementation();

    @Deprecated
    public float getMaxScale();

    public float getMaximumScale();

    public float getMediumScale();

    @Deprecated
    public float getMidScale();

    @Deprecated
    public float getMinScale();

    public float getMinimumScale();

    public PhotoViewAttacher.OnPhotoTapListener getOnPhotoTapListener();

    public PhotoViewAttacher.OnViewTapListener getOnViewTapListener();

    public float getScale();

    public ImageView.ScaleType getScaleType();

    public Bitmap getVisibleRectangleBitmap();

    public void setAllowParentInterceptOnEdge(boolean var1);

    public boolean setDisplayMatrix(Matrix var1);

    @Deprecated
    public void setMaxScale(float var1);

    public void setMaximumScale(float var1);

    public void setMediumScale(float var1);

    @Deprecated
    public void setMidScale(float var1);

    @Deprecated
    public void setMinScale(float var1);

    public void setMinimumScale(float var1);

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener var1);

    public void setOnLongClickListener(View.OnLongClickListener var1);

    public void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener var1);

    public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener var1);

    public void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener var1);

    public void setPhotoViewRotation(float var1);

    public void setRotationBy(float var1);

    public void setRotationTo(float var1);

    public void setScale(float var1);

    public void setScale(float var1, float var2, float var3, boolean var4);

    public void setScale(float var1, boolean var2);

    public void setScaleType(ImageView.ScaleType var1);

    public void setZoomTransitionDuration(int var1);

    public void setZoomable(boolean var1);
}

