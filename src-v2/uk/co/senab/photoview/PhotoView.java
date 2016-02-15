/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Matrix
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.util.AttributeSet
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.View
 *  android.view.View$OnLongClickListener
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package uk.co.senab.photoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import uk.co.senab.photoview.IPhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoView
extends ImageView
implements IPhotoView {
    private int fakeBottom;
    private final PhotoViewAttacher mAttacher;
    private ImageView.ScaleType mPendingScaleType;

    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PhotoView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        super.setScaleType(ImageView.ScaleType.MATRIX);
        this.mAttacher = new PhotoViewAttacher(this);
        if (this.mPendingScaleType != null) {
            this.setScaleType(this.mPendingScaleType);
            this.mPendingScaleType = null;
        }
    }

    @Override
    public boolean canZoom() {
        return this.mAttacher.canZoom();
    }

    @Override
    public Matrix getDisplayMatrix() {
        return this.mAttacher.getDrawMatrix();
    }

    @Override
    public RectF getDisplayRect() {
        return this.mAttacher.getDisplayRect();
    }

    public int getFakeBottom() {
        return this.fakeBottom;
    }

    @Override
    public IPhotoView getIPhotoViewImplementation() {
        return this.mAttacher;
    }

    @Deprecated
    @Override
    public float getMaxScale() {
        return this.getMaximumScale();
    }

    @Override
    public float getMaximumScale() {
        return this.mAttacher.getMaximumScale();
    }

    @Override
    public float getMediumScale() {
        return this.mAttacher.getMediumScale();
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
        return this.mAttacher.getMinimumScale();
    }

    @Override
    public PhotoViewAttacher.OnPhotoTapListener getOnPhotoTapListener() {
        return this.mAttacher.getOnPhotoTapListener();
    }

    @Override
    public PhotoViewAttacher.OnViewTapListener getOnViewTapListener() {
        return this.mAttacher.getOnViewTapListener();
    }

    @Override
    public float getScale() {
        return this.mAttacher.getScale();
    }

    @Override
    public ImageView.ScaleType getScaleType() {
        return this.mAttacher.getScaleType();
    }

    @Override
    public Bitmap getVisibleRectangleBitmap() {
        return this.mAttacher.getVisibleRectangleBitmap();
    }

    protected void onDetachedFromWindow() {
        this.mAttacher.cleanup();
        super.onDetachedFromWindow();
    }

    @Override
    public void setAllowParentInterceptOnEdge(boolean bl2) {
        this.mAttacher.setAllowParentInterceptOnEdge(bl2);
    }

    @Override
    public boolean setDisplayMatrix(Matrix matrix) {
        return this.mAttacher.setDisplayMatrix(matrix);
    }

    public void setFakeBottom(int n2) {
        this.fakeBottom = n2;
    }

    public void setImageDrawable(Drawable drawable2) {
        super.setImageDrawable(drawable2);
        if (this.mAttacher != null) {
            this.mAttacher.update();
        }
    }

    public void setImageResource(int n2) {
        super.setImageResource(n2);
        if (this.mAttacher != null) {
            this.mAttacher.update();
        }
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (this.mAttacher != null) {
            this.mAttacher.update();
        }
    }

    @Deprecated
    @Override
    public void setMaxScale(float f2) {
        this.setMaximumScale(f2);
    }

    @Override
    public void setMaximumScale(float f2) {
        this.mAttacher.setMaximumScale(f2);
    }

    @Override
    public void setMediumScale(float f2) {
        this.mAttacher.setMediumScale(f2);
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
        this.mAttacher.setMinimumScale(f2);
    }

    @Override
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.mAttacher.setOnDoubleTapListener(onDoubleTapListener);
    }

    @Override
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mAttacher.setOnLongClickListener(onLongClickListener);
    }

    @Override
    public void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener onMatrixChangedListener) {
        this.mAttacher.setOnMatrixChangeListener(onMatrixChangedListener);
    }

    @Override
    public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener onPhotoTapListener) {
        this.mAttacher.setOnPhotoTapListener(onPhotoTapListener);
    }

    @Override
    public void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener onViewTapListener) {
        this.mAttacher.setOnViewTapListener(onViewTapListener);
    }

    @Override
    public void setPhotoViewRotation(float f2) {
        this.mAttacher.setRotationTo(f2);
    }

    @Override
    public void setRotationBy(float f2) {
        this.mAttacher.setRotationBy(f2);
    }

    @Override
    public void setRotationTo(float f2) {
        this.mAttacher.setRotationTo(f2);
    }

    @Override
    public void setScale(float f2) {
        this.mAttacher.setScale(f2);
    }

    @Override
    public void setScale(float f2, float f3, float f4, boolean bl2) {
        this.mAttacher.setScale(f2, f3, f4, bl2);
    }

    @Override
    public void setScale(float f2, boolean bl2) {
        this.mAttacher.setScale(f2, bl2);
    }

    @Override
    public void setScaleType(ImageView.ScaleType scaleType) {
        if (this.mAttacher != null) {
            this.mAttacher.setScaleType(scaleType);
            return;
        }
        this.mPendingScaleType = scaleType;
    }

    @Override
    public void setZoomTransitionDuration(int n2) {
        this.mAttacher.setZoomTransitionDuration(n2);
    }

    @Override
    public void setZoomable(boolean bl2) {
        this.mAttacher.setZoomable(bl2);
    }
}

