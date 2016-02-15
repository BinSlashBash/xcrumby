package uk.co.senab.photoview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import java.lang.ref.WeakReference;
import uk.co.senab.photoview.gestures.OnGestureListener;
import uk.co.senab.photoview.gestures.VersionedGestureDetector;
import uk.co.senab.photoview.log.LogManager;
import uk.co.senab.photoview.log.Logger;
import uk.co.senab.photoview.scrollerproxy.ScrollerProxy;

public class PhotoViewAttacher
  implements IPhotoView, View.OnTouchListener, OnGestureListener, ViewTreeObserver.OnGlobalLayoutListener
{
  private static final boolean DEBUG = Log.isLoggable("PhotoViewAttacher", 3);
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
  private float mMaxScale = 3.0F;
  private float mMidScale = 1.75F;
  private float mMinScale = 1.0F;
  private OnPhotoTapListener mPhotoTapListener;
  private uk.co.senab.photoview.gestures.GestureDetector mScaleDragDetector;
  private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;
  private int mScrollEdge = 2;
  private final Matrix mSuppMatrix = new Matrix();
  private OnViewTapListener mViewTapListener;
  private boolean mZoomEnabled;
  
  @TargetApi(3)
  public PhotoViewAttacher(ImageView paramImageView)
  {
    this.mImageView = new WeakReference(paramImageView);
    paramImageView.setDrawingCacheEnabled(true);
    paramImageView.setOnTouchListener(this);
    ViewTreeObserver localViewTreeObserver = paramImageView.getViewTreeObserver();
    if (localViewTreeObserver != null) {
      localViewTreeObserver.addOnGlobalLayoutListener(this);
    }
    setImageViewScaleTypeMatrix(paramImageView);
    if (paramImageView.isInEditMode()) {
      return;
    }
    this.mScaleDragDetector = VersionedGestureDetector.newInstance(paramImageView.getContext(), this);
    this.mGestureDetector = new android.view.GestureDetector(paramImageView.getContext(), new GestureDetector.SimpleOnGestureListener()
    {
      public void onLongPress(MotionEvent paramAnonymousMotionEvent)
      {
        if (PhotoViewAttacher.this.mLongClickListener != null) {
          PhotoViewAttacher.this.mLongClickListener.onLongClick(PhotoViewAttacher.this.getImageView());
        }
      }
    });
    this.mGestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
    setZoomable(true);
  }
  
  private void cancelFling()
  {
    if (this.mCurrentFlingRunnable != null)
    {
      this.mCurrentFlingRunnable.cancelFling();
      this.mCurrentFlingRunnable = null;
    }
  }
  
  private void checkAndDisplayMatrix()
  {
    if (checkMatrixBounds()) {
      setImageViewMatrix(getDrawMatrix());
    }
  }
  
  private void checkImageViewScaleType()
  {
    ImageView localImageView = getImageView();
    if ((localImageView != null) && (!(localImageView instanceof IPhotoView)) && (!ImageView.ScaleType.MATRIX.equals(localImageView.getScaleType()))) {
      throw new IllegalStateException("The ImageView's ScaleType has been changed since attaching a PhotoViewAttacher");
    }
  }
  
  private boolean checkMatrixBounds()
  {
    ImageView localImageView = getImageView();
    if (localImageView == null) {}
    RectF localRectF;
    do
    {
      return false;
      localRectF = getDisplayRect(getDrawMatrix());
    } while (localRectF == null);
    float f4 = localRectF.height();
    float f3 = localRectF.width();
    float f2 = 0.0F;
    float f1 = 0.0F;
    int i = getImageViewHeight(localImageView);
    if (f4 <= i) {
      switch (this.mScaleType)
      {
      default: 
        f1 = (i - f4) / 2.0F - localRectF.top;
        i = getImageViewWidth(localImageView);
        if (f3 <= i) {
          switch (this.mScaleType)
          {
          default: 
            f2 = (i - f3) / 2.0F - localRectF.left;
            label174:
            this.mScrollEdge = 2;
          }
        }
        break;
      }
    }
    for (;;)
    {
      this.mSuppMatrix.postTranslate(f2, f1);
      return true;
      f1 = -localRectF.top;
      break;
      f1 = i - f4 - localRectF.top;
      break;
      if (localRectF.top > 0.0F)
      {
        f1 = -localRectF.top;
        break;
      }
      if (localRectF.bottom >= i) {
        break;
      }
      f1 = i - localRectF.bottom;
      break;
      f2 = -localRectF.left;
      break label174;
      f2 = i - f3 - localRectF.left;
      break label174;
      if (localRectF.left > 0.0F)
      {
        this.mScrollEdge = 0;
        f2 = -localRectF.left;
      }
      else if (localRectF.right < i)
      {
        f2 = i - localRectF.right;
        this.mScrollEdge = 1;
      }
      else
      {
        this.mScrollEdge = -1;
      }
    }
  }
  
  private static void checkZoomLevels(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 >= paramFloat2) {
      throw new IllegalArgumentException("MinZoom has to be less than MidZoom");
    }
    if (paramFloat2 >= paramFloat3) {
      throw new IllegalArgumentException("MidZoom has to be less than MaxZoom");
    }
  }
  
  private RectF getDisplayRect(Matrix paramMatrix)
  {
    Object localObject = getImageView();
    if (localObject != null)
    {
      localObject = ((ImageView)localObject).getDrawable();
      if (localObject != null)
      {
        this.mDisplayRect.set(0.0F, 0.0F, ((Drawable)localObject).getIntrinsicWidth(), ((Drawable)localObject).getIntrinsicHeight());
        paramMatrix.mapRect(this.mDisplayRect);
        return this.mDisplayRect;
      }
    }
    return null;
  }
  
  private int getImageViewHeight(ImageView paramImageView)
  {
    if (paramImageView == null) {
      return 0;
    }
    return paramImageView.getHeight() - paramImageView.getPaddingTop() - paramImageView.getPaddingBottom();
  }
  
  private int getImageViewWidth(ImageView paramImageView)
  {
    if (paramImageView == null) {
      return 0;
    }
    return paramImageView.getWidth() - paramImageView.getPaddingLeft() - paramImageView.getPaddingRight();
  }
  
  private float getValue(Matrix paramMatrix, int paramInt)
  {
    paramMatrix.getValues(this.mMatrixValues);
    return this.mMatrixValues[paramInt];
  }
  
  private static boolean hasDrawable(ImageView paramImageView)
  {
    return (paramImageView != null) && (paramImageView.getDrawable() != null);
  }
  
  private static boolean isSupportedScaleType(ImageView.ScaleType paramScaleType)
  {
    if (paramScaleType == null) {
      return false;
    }
    switch (paramScaleType)
    {
    default: 
      return true;
    }
    throw new IllegalArgumentException(paramScaleType.name() + " is not supported in PhotoView");
  }
  
  private void resetMatrix()
  {
    this.mSuppMatrix.reset();
    setImageViewMatrix(getDrawMatrix());
    checkMatrixBounds();
  }
  
  private void setImageViewMatrix(Matrix paramMatrix)
  {
    ImageView localImageView = getImageView();
    if (localImageView != null)
    {
      checkImageViewScaleType();
      localImageView.setImageMatrix(paramMatrix);
      if (this.mMatrixChangeListener != null)
      {
        paramMatrix = getDisplayRect(paramMatrix);
        if (paramMatrix != null) {
          this.mMatrixChangeListener.onMatrixChanged(paramMatrix);
        }
      }
    }
  }
  
  private static void setImageViewScaleTypeMatrix(ImageView paramImageView)
  {
    if ((paramImageView != null) && (!(paramImageView instanceof IPhotoView)) && (!ImageView.ScaleType.MATRIX.equals(paramImageView.getScaleType()))) {
      paramImageView.setScaleType(ImageView.ScaleType.MATRIX);
    }
  }
  
  private void updateBaseMatrix(Drawable paramDrawable)
  {
    Object localObject = getImageView();
    if ((localObject == null) || (paramDrawable == null)) {
      return;
    }
    float f1 = getImageViewWidth((ImageView)localObject);
    float f2 = getImageViewHeight((ImageView)localObject);
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    this.mBaseMatrix.reset();
    float f3 = f1 / i;
    float f4 = f2 / j;
    if (this.mScaleType == ImageView.ScaleType.CENTER) {
      this.mBaseMatrix.postTranslate((f1 - i) / 2.0F, (f2 - j) / 2.0F);
    }
    for (;;)
    {
      resetMatrix();
      return;
      if (this.mScaleType == ImageView.ScaleType.CENTER_CROP)
      {
        f3 = Math.max(f3, f4);
        this.mBaseMatrix.postScale(f3, f3);
        this.mBaseMatrix.postTranslate((f1 - i * f3) / 2.0F, (f2 - j * f3) / 2.0F);
      }
      else if (this.mScaleType == ImageView.ScaleType.CENTER_INSIDE)
      {
        f3 = Math.min(1.0F, Math.min(f3, f4));
        this.mBaseMatrix.postScale(f3, f3);
        this.mBaseMatrix.postTranslate((f1 - i * f3) / 2.0F, (f2 - j * f3) / 2.0F);
      }
      else
      {
        paramDrawable = new RectF(0.0F, 0.0F, i, j);
        localObject = new RectF(0.0F, 0.0F, f1, f2);
        switch (this.mScaleType)
        {
        default: 
          break;
        case ???: 
          this.mBaseMatrix.setRectToRect(paramDrawable, (RectF)localObject, Matrix.ScaleToFit.START);
          break;
        case ???: 
          this.mBaseMatrix.setRectToRect(paramDrawable, (RectF)localObject, Matrix.ScaleToFit.CENTER);
          break;
        case ???: 
          this.mBaseMatrix.setRectToRect(paramDrawable, (RectF)localObject, Matrix.ScaleToFit.END);
          break;
        case ???: 
          this.mBaseMatrix.setRectToRect(paramDrawable, (RectF)localObject, Matrix.ScaleToFit.FILL);
        }
      }
    }
  }
  
  public boolean canZoom()
  {
    return this.mZoomEnabled;
  }
  
  public void cleanup()
  {
    if (this.mImageView == null) {
      return;
    }
    ImageView localImageView = (ImageView)this.mImageView.get();
    if (localImageView != null)
    {
      ViewTreeObserver localViewTreeObserver = localImageView.getViewTreeObserver();
      if ((localViewTreeObserver != null) && (localViewTreeObserver.isAlive())) {
        localViewTreeObserver.removeGlobalOnLayoutListener(this);
      }
      localImageView.setOnTouchListener(null);
      cancelFling();
    }
    if (this.mGestureDetector != null) {
      this.mGestureDetector.setOnDoubleTapListener(null);
    }
    this.mMatrixChangeListener = null;
    this.mPhotoTapListener = null;
    this.mViewTapListener = null;
    this.mImageView = null;
  }
  
  public Matrix getDisplayMatrix()
  {
    return new Matrix(getDrawMatrix());
  }
  
  public RectF getDisplayRect()
  {
    checkMatrixBounds();
    return getDisplayRect(getDrawMatrix());
  }
  
  public Matrix getDrawMatrix()
  {
    this.mDrawMatrix.set(this.mBaseMatrix);
    this.mDrawMatrix.postConcat(this.mSuppMatrix);
    return this.mDrawMatrix;
  }
  
  public IPhotoView getIPhotoViewImplementation()
  {
    return this;
  }
  
  public ImageView getImageView()
  {
    ImageView localImageView = null;
    if (this.mImageView != null) {
      localImageView = (ImageView)this.mImageView.get();
    }
    if (localImageView == null)
    {
      cleanup();
      Log.i("PhotoViewAttacher", "ImageView no longer exists. You should not use this PhotoViewAttacher any more.");
    }
    return localImageView;
  }
  
  @Deprecated
  public float getMaxScale()
  {
    return getMaximumScale();
  }
  
  public float getMaximumScale()
  {
    return this.mMaxScale;
  }
  
  public float getMediumScale()
  {
    return this.mMidScale;
  }
  
  @Deprecated
  public float getMidScale()
  {
    return getMediumScale();
  }
  
  @Deprecated
  public float getMinScale()
  {
    return getMinimumScale();
  }
  
  public float getMinimumScale()
  {
    return this.mMinScale;
  }
  
  public OnPhotoTapListener getOnPhotoTapListener()
  {
    return this.mPhotoTapListener;
  }
  
  public OnViewTapListener getOnViewTapListener()
  {
    return this.mViewTapListener;
  }
  
  public float getScale()
  {
    return FloatMath.sqrt((float)Math.pow(getValue(this.mSuppMatrix, 0), 2.0D) + (float)Math.pow(getValue(this.mSuppMatrix, 3), 2.0D));
  }
  
  public ImageView.ScaleType getScaleType()
  {
    return this.mScaleType;
  }
  
  public Bitmap getVisibleRectangleBitmap()
  {
    ImageView localImageView = getImageView();
    if (localImageView == null) {
      return null;
    }
    return localImageView.getDrawingCache();
  }
  
  public void onDrag(float paramFloat1, float paramFloat2)
  {
    if (this.mScaleDragDetector.isScaling()) {}
    Object localObject;
    do
    {
      do
      {
        return;
        if (DEBUG) {
          LogManager.getLogger().d("PhotoViewAttacher", String.format("onDrag: dx: %.2f. dy: %.2f", new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2) }));
        }
        localObject = getImageView();
        this.mSuppMatrix.postTranslate(paramFloat1, paramFloat2);
        checkAndDisplayMatrix();
        localObject = ((ImageView)localObject).getParent();
        if ((!this.mAllowParentInterceptOnEdge) || (this.mScaleDragDetector.isScaling())) {
          break;
        }
      } while (((this.mScrollEdge != 2) && ((this.mScrollEdge != 0) || (paramFloat1 < 1.0F)) && ((this.mScrollEdge != 1) || (paramFloat1 > -1.0F))) || (localObject == null));
      ((ViewParent)localObject).requestDisallowInterceptTouchEvent(false);
      return;
    } while (localObject == null);
    ((ViewParent)localObject).requestDisallowInterceptTouchEvent(true);
  }
  
  public void onFling(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (DEBUG) {
      LogManager.getLogger().d("PhotoViewAttacher", "onFling. sX: " + paramFloat1 + " sY: " + paramFloat2 + " Vx: " + paramFloat3 + " Vy: " + paramFloat4);
    }
    ImageView localImageView = getImageView();
    this.mCurrentFlingRunnable = new FlingRunnable(localImageView.getContext());
    this.mCurrentFlingRunnable.fling(getImageViewWidth(localImageView), getImageViewHeight(localImageView), (int)paramFloat3, (int)paramFloat4);
    localImageView.post(this.mCurrentFlingRunnable);
  }
  
  public void onGlobalLayout()
  {
    ImageView localImageView = getImageView();
    if (localImageView != null)
    {
      if (!this.mZoomEnabled) {
        break label107;
      }
      int i = localImageView.getTop();
      int j = localImageView.getRight();
      int k = localImageView.getBottom();
      int m = localImageView.getLeft();
      if ((i != this.mIvTop) || (k != this.mIvBottom) || (m != this.mIvLeft) || (j != this.mIvRight))
      {
        updateBaseMatrix(localImageView.getDrawable());
        this.mIvTop = i;
        this.mIvRight = j;
        this.mIvBottom = k;
        this.mIvLeft = m;
      }
    }
    return;
    label107:
    updateBaseMatrix(localImageView.getDrawable());
  }
  
  public void onScale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (DEBUG) {
      LogManager.getLogger().d("PhotoViewAttacher", String.format("onScale: scale: %.2f. fX: %.2f. fY: %.2f", new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2), Float.valueOf(paramFloat3) }));
    }
    if (this.expandContainerScaleListener.expand()) {
      Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "scale to full screen", null);
    }
    if ((getScale() < this.mMaxScale) || (paramFloat1 < 1.0F))
    {
      this.mSuppMatrix.postScale(paramFloat1, paramFloat1, paramFloat2, paramFloat3);
      checkAndDisplayMatrix();
    }
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    boolean bool3 = false;
    boolean bool2 = false;
    boolean bool1 = bool3;
    Object localObject;
    if (this.mZoomEnabled)
    {
      bool1 = bool3;
      if (hasDrawable((ImageView)paramView))
      {
        localObject = paramView.getParent();
        bool1 = bool2;
        switch (paramMotionEvent.getAction())
        {
        default: 
          bool1 = bool2;
        }
      }
    }
    for (;;)
    {
      bool2 = bool1;
      if (this.mScaleDragDetector != null)
      {
        bool2 = bool1;
        if (this.mScaleDragDetector.onTouchEvent(paramMotionEvent)) {
          bool2 = true;
        }
      }
      bool1 = bool2;
      if (this.mGestureDetector != null)
      {
        bool1 = bool2;
        if (this.mGestureDetector.onTouchEvent(paramMotionEvent)) {
          bool1 = true;
        }
      }
      return bool1;
      if (localObject != null) {
        ((ViewParent)localObject).requestDisallowInterceptTouchEvent(true);
      }
      for (;;)
      {
        cancelFling();
        bool1 = bool2;
        break;
        Log.i("PhotoViewAttacher", "onTouch getParent() returned null");
      }
      bool1 = bool2;
      if (getScale() < this.mMinScale)
      {
        localObject = getDisplayRect();
        bool1 = bool2;
        if (localObject != null)
        {
          paramView.post(new AnimatedZoomRunnable(getScale(), this.mMinScale, ((RectF)localObject).centerX(), ((RectF)localObject).centerY()));
          bool1 = true;
        }
      }
    }
  }
  
  public void setAllowParentInterceptOnEdge(boolean paramBoolean)
  {
    this.mAllowParentInterceptOnEdge = paramBoolean;
  }
  
  public boolean setDisplayMatrix(Matrix paramMatrix)
  {
    if (paramMatrix == null) {
      throw new IllegalArgumentException("Matrix cannot be null");
    }
    ImageView localImageView = getImageView();
    if (localImageView == null) {}
    while (localImageView.getDrawable() == null) {
      return false;
    }
    this.mSuppMatrix.set(paramMatrix);
    setImageViewMatrix(getDrawMatrix());
    checkMatrixBounds();
    return true;
  }
  
  public void setExpandContainerScaleListener(ExpandContainerScaleListener paramExpandContainerScaleListener)
  {
    this.expandContainerScaleListener = paramExpandContainerScaleListener;
  }
  
  @Deprecated
  public void setMaxScale(float paramFloat)
  {
    setMaximumScale(paramFloat);
  }
  
  public void setMaximumScale(float paramFloat)
  {
    checkZoomLevels(this.mMinScale, this.mMidScale, paramFloat);
    this.mMaxScale = paramFloat;
  }
  
  public void setMediumScale(float paramFloat)
  {
    checkZoomLevels(this.mMinScale, paramFloat, this.mMaxScale);
    this.mMidScale = paramFloat;
  }
  
  @Deprecated
  public void setMidScale(float paramFloat)
  {
    setMediumScale(paramFloat);
  }
  
  @Deprecated
  public void setMinScale(float paramFloat)
  {
    setMinimumScale(paramFloat);
  }
  
  public void setMinimumScale(float paramFloat)
  {
    checkZoomLevels(paramFloat, this.mMidScale, this.mMaxScale);
    this.mMinScale = paramFloat;
  }
  
  public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
  {
    if (paramOnDoubleTapListener != null)
    {
      this.mGestureDetector.setOnDoubleTapListener(paramOnDoubleTapListener);
      return;
    }
    this.mGestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
  }
  
  public void setOnLongClickListener(View.OnLongClickListener paramOnLongClickListener)
  {
    this.mLongClickListener = paramOnLongClickListener;
  }
  
  public void setOnMatrixChangeListener(OnMatrixChangedListener paramOnMatrixChangedListener)
  {
    this.mMatrixChangeListener = paramOnMatrixChangedListener;
  }
  
  public void setOnPhotoTapListener(OnPhotoTapListener paramOnPhotoTapListener)
  {
    this.mPhotoTapListener = paramOnPhotoTapListener;
  }
  
  public void setOnViewTapListener(OnViewTapListener paramOnViewTapListener)
  {
    this.mViewTapListener = paramOnViewTapListener;
  }
  
  public void setPhotoViewRotation(float paramFloat)
  {
    this.mSuppMatrix.setRotate(paramFloat % 360.0F);
    checkAndDisplayMatrix();
  }
  
  public void setRotationBy(float paramFloat)
  {
    this.mSuppMatrix.postRotate(paramFloat % 360.0F);
    checkAndDisplayMatrix();
  }
  
  public void setRotationTo(float paramFloat)
  {
    this.mSuppMatrix.setRotate(paramFloat % 360.0F);
    checkAndDisplayMatrix();
  }
  
  public void setScale(float paramFloat)
  {
    setScale(paramFloat, false);
  }
  
  public void setScale(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean)
  {
    ImageView localImageView = getImageView();
    if (localImageView != null)
    {
      if ((paramFloat1 < this.mMinScale) || (paramFloat1 > this.mMaxScale)) {
        LogManager.getLogger().i("PhotoViewAttacher", "Scale must be within the range of minScale and maxScale");
      }
    }
    else {
      return;
    }
    if (paramBoolean)
    {
      localImageView.post(new AnimatedZoomRunnable(getScale(), paramFloat1, paramFloat2, paramFloat3));
      return;
    }
    this.mSuppMatrix.setScale(paramFloat1, paramFloat1, paramFloat2, paramFloat3);
    checkAndDisplayMatrix();
  }
  
  public void setScale(float paramFloat, boolean paramBoolean)
  {
    ImageView localImageView = getImageView();
    if (localImageView != null) {
      setScale(paramFloat, localImageView.getRight() / 2, localImageView.getBottom() / 2, paramBoolean);
    }
  }
  
  public void setScaleType(ImageView.ScaleType paramScaleType)
  {
    if ((isSupportedScaleType(paramScaleType)) && (paramScaleType != this.mScaleType))
    {
      this.mScaleType = paramScaleType;
      update();
    }
  }
  
  public void setZoomTransitionDuration(int paramInt)
  {
    int i = paramInt;
    if (paramInt < 0) {
      i = 200;
    }
    this.ZOOM_DURATION = i;
  }
  
  public void setZoomable(boolean paramBoolean)
  {
    this.mZoomEnabled = paramBoolean;
    update();
  }
  
  public void update()
  {
    ImageView localImageView = getImageView();
    if (localImageView != null)
    {
      if (this.mZoomEnabled)
      {
        setImageViewScaleTypeMatrix(localImageView);
        updateBaseMatrix(localImageView.getDrawable());
      }
    }
    else {
      return;
    }
    resetMatrix();
  }
  
  private class AnimatedZoomRunnable
    implements Runnable
  {
    private final float mFocalX;
    private final float mFocalY;
    private final long mStartTime;
    private final float mZoomEnd;
    private final float mZoomStart;
    
    public AnimatedZoomRunnable(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this.mFocalX = paramFloat3;
      this.mFocalY = paramFloat4;
      this.mStartTime = System.currentTimeMillis();
      this.mZoomStart = paramFloat1;
      this.mZoomEnd = paramFloat2;
    }
    
    private float interpolate()
    {
      float f = Math.min(1.0F, (float)(System.currentTimeMillis() - this.mStartTime) * 1.0F / PhotoViewAttacher.this.ZOOM_DURATION);
      return PhotoViewAttacher.sInterpolator.getInterpolation(f);
    }
    
    public void run()
    {
      ImageView localImageView = PhotoViewAttacher.this.getImageView();
      if (localImageView == null) {}
      float f1;
      do
      {
        return;
        f1 = interpolate();
        float f2 = (this.mZoomStart + (this.mZoomEnd - this.mZoomStart) * f1) / PhotoViewAttacher.this.getScale();
        PhotoViewAttacher.this.mSuppMatrix.postScale(f2, f2, this.mFocalX, this.mFocalY);
        PhotoViewAttacher.this.checkAndDisplayMatrix();
      } while (f1 >= 1.0F);
      Compat.postOnAnimation(localImageView, this);
    }
  }
  
  private class FlingRunnable
    implements Runnable
  {
    private int mCurrentX;
    private int mCurrentY;
    private final ScrollerProxy mScroller;
    
    public FlingRunnable(Context paramContext)
    {
      this.mScroller = ScrollerProxy.getScroller(paramContext);
    }
    
    public void cancelFling()
    {
      if (PhotoViewAttacher.DEBUG) {
        LogManager.getLogger().d("PhotoViewAttacher", "Cancel Fling");
      }
      this.mScroller.forceFinished(true);
    }
    
    public void fling(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      RectF localRectF = PhotoViewAttacher.this.getDisplayRect();
      if (localRectF == null) {
        return;
      }
      int i = Math.round(-localRectF.left);
      int k;
      int j;
      label56:
      int n;
      int m;
      if (paramInt1 < localRectF.width())
      {
        k = 0;
        j = Math.round(localRectF.width() - paramInt1);
        paramInt1 = k;
        k = Math.round(-localRectF.top);
        if (paramInt2 >= localRectF.height()) {
          break label223;
        }
        n = 0;
        m = Math.round(localRectF.height() - paramInt2);
      }
      for (paramInt2 = n;; paramInt2 = k)
      {
        this.mCurrentX = i;
        this.mCurrentY = k;
        if (PhotoViewAttacher.DEBUG) {
          LogManager.getLogger().d("PhotoViewAttacher", "fling. StartX:" + i + " StartY:" + k + " MaxX:" + j + " MaxY:" + m);
        }
        if ((i == j) && (k == m)) {
          break;
        }
        this.mScroller.fling(i, k, paramInt3, paramInt4, paramInt1, j, paramInt2, m, 0, 0);
        return;
        j = i;
        paramInt1 = i;
        break label56;
        label223:
        m = k;
      }
    }
    
    public void run()
    {
      if (this.mScroller.isFinished()) {}
      ImageView localImageView;
      do
      {
        return;
        localImageView = PhotoViewAttacher.this.getImageView();
      } while ((localImageView == null) || (!this.mScroller.computeScrollOffset()));
      int i = this.mScroller.getCurrX();
      int j = this.mScroller.getCurrY();
      if (PhotoViewAttacher.DEBUG) {
        LogManager.getLogger().d("PhotoViewAttacher", "fling run(). CurrentX:" + this.mCurrentX + " CurrentY:" + this.mCurrentY + " NewX:" + i + " NewY:" + j);
      }
      PhotoViewAttacher.this.mSuppMatrix.postTranslate(this.mCurrentX - i, this.mCurrentY - j);
      PhotoViewAttacher.this.setImageViewMatrix(PhotoViewAttacher.this.getDrawMatrix());
      this.mCurrentX = i;
      this.mCurrentY = j;
      Compat.postOnAnimation(localImageView, this);
    }
  }
  
  public static abstract interface OnMatrixChangedListener
  {
    public abstract void onMatrixChanged(RectF paramRectF);
  }
  
  public static abstract interface OnPhotoTapListener
  {
    public abstract void onPhotoTap(View paramView, float paramFloat1, float paramFloat2);
  }
  
  public static abstract interface OnViewTapListener
  {
    public abstract void onViewTap(View paramView, float paramFloat1, float paramFloat2);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/uk/co/senab/photoview/PhotoViewAttacher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */