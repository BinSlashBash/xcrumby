package com.crumby.lib.widget.firstparty.grow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.GridView;
import android.widget.ImageView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryListFragment;
import com.crumby.lib.fragment.ImageClickListener;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.widget.firstparty.ScreenCapture;
import com.crumby.lib.widget.thirdparty.HeaderGridView;
import com.crumby.lib.widget.thirdparty.HeaderGridView.HeaderViewGridAdapter;
import com.squareup.picasso.Picasso;
import java.lang.reflect.Field;

public class GrowGridView
  extends HeaderGridView
{
  private static int viewGridWidth;
  private volatile boolean canScale = true;
  private ColumnCounter columnCount;
  private int currentWidth;
  private int focus;
  private GalleryImage hostImage;
  private ScaleGestureDetector mScaleDetector;
  private ScreenCapture screenCapture;
  
  public GrowGridView(Context paramContext)
  {
    super(paramContext);
  }
  
  public GrowGridView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public GrowGridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private boolean alterColumnCount(int paramInt)
  {
    int i = getColumns(paramInt);
    this.columnCount.set(i);
    if (hasReachedLimit(paramInt, i))
    {
      paramInt = Math.min(getScreenWidth() - getRequestedHorizontalSpacing(), Math.max(paramInt, GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH));
      this.columnCount.set(getColumns(paramInt));
      this.columnCount.indicateLimit();
      return false;
    }
    this.columnCount.indicateFree();
    return true;
  }
  
  private void animateGridScaleEnd(float paramFloat, int paramInt)
  {
    setScaleX(paramFloat);
    setScaleY(paramFloat);
    int i = 0;
    int j = getChildCount();
    while (i < j)
    {
      getChildAt(i).setAlpha(1.0F);
      i += 1;
    }
    this.currentWidth = paramInt;
    setColumnWidth(this.currentWidth);
    setVisibility(0);
    this.screenCapture.hide();
    animate().setDuration(750L).scaleX(1.0F).scaleY(1.0F).setListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        GrowGridView.access$102(GrowGridView.this, true);
        GrowGridView.this.setVisibility(0);
        GrowGridView.this.screenCapture.hide();
      }
    }).start();
  }
  
  private void endScaling(float paramFloat, boolean paramBoolean)
  {
    int i = (int)(this.currentWidth * paramFloat * paramFloat);
    int j = getColumns(i);
    Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_GRID, "resize", j + "");
    setEnabled(true);
    this.columnCount.hide();
    if (paramBoolean) {
      animateGridScaleEnd(paramFloat, i);
    }
    scrollToCenter(this.focus);
    ((ImageClickListener)getOnItemLongClickListener()).enable();
    unpause();
  }
  
  private View getCenterWrapper()
  {
    Object localObject = getChildAt(0);
    int i = getWidth() / 2;
    int j = getHeight() / 2;
    CrDb.d("grow grid view", "grid center height & width: " + j + " " + i);
    Rect localRect1 = new Rect(i - 50, j - 50, i + 50, j + 50);
    i = 0;
    j = getChildCount();
    while (i < j)
    {
      Rect localRect2 = new Rect();
      View localView = getChildAt(i);
      localView.getHitRect(localRect2);
      localView.clearAnimation();
      localView.setPressed(false);
      if (localView.getBackground() != null)
      {
        localView.setBackgroundDrawable(getResources().getDrawable(2130837656));
        localView.getBackground().invalidateSelf();
      }
      localView.invalidate();
      if (localRect2.intersect(localRect1))
      {
        CrDb.d("grow grid view", "contained at " + i);
        localObject = localView;
      }
      localView.setAlpha(0.2F);
      i += 1;
    }
    ((View)localObject).setPressed(true);
    ((View)localObject).setAlpha(1.0F);
    return (View)localObject;
  }
  
  private int getColumns(int paramInt)
  {
    paramInt = Math.max(paramInt, GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH);
    return getScreenWidth() / (getRequestedHorizontalSpacing() + paramInt);
  }
  
  private GalleryListAdapter getGalleryAdapter()
  {
    return (GalleryListAdapter)((HeaderGridView.HeaderViewGridAdapter)getAdapter()).getWrappedAdapter();
  }
  
  private int getScreenHeight()
  {
    return getResources().getDisplayMetrics().heightPixels;
  }
  
  public static boolean hasReachedLimit(int paramInt1, int paramInt2)
  {
    return (paramInt1 < GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH) || (paramInt2 < 1);
  }
  
  private void pause()
  {
    int j = getChildCount();
    getGalleryAdapter().pause();
    int i = 0;
    while (i < j)
    {
      if (getChildAt(i).getTag() != null)
      {
        GridImageWrapper localGridImageWrapper = (GridImageWrapper)getChildAt(i).getTag();
        Picasso.with(getContext()).cancelRequest(localGridImageWrapper.getImageView());
      }
      i += 1;
    }
  }
  
  private boolean scaleEvent(float paramFloat)
  {
    float f = paramFloat * paramFloat;
    int i = (int)(this.currentWidth * f);
    CrDb.d("grow grid view", "mScaleFactor: " + f);
    if (!alterColumnCount(i)) {
      return false;
    }
    this.screenCapture.scale(paramFloat);
    return true;
  }
  
  private void scrollToCenter(final int paramInt)
  {
    CrDb.d("grow grid view", "focus position 2: " + paramInt + " num columns: " + getNumColumns() + " current width: " + getCurrentWidth() + " screen width:" + getScreenWidth());
    paramInt = getGalleryAdapter().prepareHighlight(paramInt);
    CrDb.d("grow grid view", "grid position:" + paramInt);
    Handler localHandler = new Handler();
    setEnabled(false);
    setSelection(paramInt);
    localHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        GrowGridView.this.setEnabled(true);
        GrowGridView.this.setSelection(paramInt);
      }
    }, 1000L);
  }
  
  private void startScaling()
  {
    View localView = getCenterWrapper();
    int i = 0;
    try
    {
      int j = ((GridImageWrapper)localView.getTag()).getImage().getPosition();
      i = j;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
    startScaling(i);
  }
  
  private void unpause()
  {
    getGalleryAdapter().unpause();
  }
  
  public void checkForPersistence()
  {
    if (getContext() == null) {}
    while ((!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("crumbyPersistGridColumns", true)) || (this.currentWidth == viewGridWidth)) {
      return;
    }
    setColumnWidth(viewGridWidth);
  }
  
  @SuppressLint({"NewApi"})
  public int getColumnWidth()
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getColumnWidth();
    }
    try
    {
      Field localField = GridView.class.getDeclaredField("mColumnWidth");
      localField.setAccessible(true);
      Integer localInteger = (Integer)localField.get(this);
      localField.setAccessible(false);
      int i = localInteger.intValue();
      return i;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      throw new RuntimeException(localNoSuchFieldException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new RuntimeException(localIllegalAccessException);
    }
  }
  
  public int getCurrentColumns()
  {
    return getColumns(this.currentWidth);
  }
  
  public int getCurrentWidth()
  {
    return this.currentWidth;
  }
  
  public int getRequestedHorizontalSpacing()
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getRequestedHorizontalSpacing();
    }
    try
    {
      Field localField = getClass().getDeclaredField("mRequestedHorizontalSpacing");
      localField.setAccessible(true);
      int i = localField.getInt(this);
      return i;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      return 0;
    }
    catch (IllegalAccessException localIllegalAccessException) {}
    return 0;
  }
  
  public int getScreenWidth()
  {
    return getResources().getDisplayMetrics().widthPixels;
  }
  
  public void initialize(ImageView paramImageView, ColumnCounter paramColumnCounter, GalleryImage paramGalleryImage)
  {
    this.columnCount = paramColumnCounter;
    this.screenCapture = new ScreenCapture(this, paramImageView);
    this.hostImage = paramGalleryImage;
    if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("crumbyPersistGridColumns", true)) {}
    for (this.currentWidth = viewGridWidth;; this.currentWidth = paramGalleryImage.getViewGridWidth())
    {
      if (this.currentWidth == 0) {
        this.currentWidth = Math.max(getScreenWidth() / 5, GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH);
      }
      setColumnWidth(this.currentWidth);
      return;
    }
  }
  
  protected void onDetachedFromWindow()
  {
    CrDb.d("grow grid view", "detached!");
    if (this.screenCapture != null)
    {
      this.screenCapture.recycle();
      this.screenCapture = null;
    }
    super.onDetachedFromWindow();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener(null));
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.screenCapture != null) {
      this.screenCapture.setupScreenShot();
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.mScaleDetector.onTouchEvent(paramMotionEvent);
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setColumnWidth(int paramInt)
  {
    this.hostImage.setViewGridWidth(paramInt);
    viewGridWidth = paramInt;
    super.setColumnWidth(paramInt);
  }
  
  public void startScaling(int paramInt)
  {
    ((ImageClickListener)getOnItemLongClickListener()).disable();
    CrDb.d("grow grid view", "focus position 1: " + paramInt);
    this.focus = paramInt;
    this.columnCount.show();
    pause();
    this.canScale = false;
    this.screenCapture.resetScreenshot();
    this.currentWidth = (getColumnWidth() - 10);
    scaleEvent(1.0F);
    setEnabled(false);
  }
  
  public void zoom(float paramFloat1, float paramFloat2, float paramFloat3, final boolean paramBoolean)
  {
    CrDb.d("grow grid view", "real zoom " + getX() + " " + getY());
    int i = (int)(this.currentWidth * paramFloat3);
    i = getScreenWidth() / i;
    this.columnCount.set(i);
    CrDb.d("grow grid view", "to zoom " + paramFloat1 + " " + paramFloat2);
    if (paramBoolean)
    {
      this.currentWidth = ((int)(this.currentWidth * paramFloat3));
      setColumnWidth(this.currentWidth);
    }
    final ImageView localImageView = this.screenCapture.getScreenShot();
    localImageView.animate().scaleX(paramFloat3).scaleY(paramFloat3).x(paramFloat1).y(paramFloat2).setDuration(500L).setListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        paramAnonymousAnimator = GrowGridView.this;
        if (!paramBoolean) {}
        for (boolean bool = true;; bool = false)
        {
          paramAnonymousAnimator.endScaling(1.0F, bool);
          localImageView.setX(0.0F);
          localImageView.setY(0.0F);
          localImageView.setAlpha(0.5F);
          localImageView.setVisibility(8);
          if (paramBoolean)
          {
            GrowGridView.access$102(GrowGridView.this, true);
            GrowGridView.this.setVisibility(0);
          }
          return;
        }
      }
    });
  }
  
  public void zoomIntoSequence(View paramView, int paramInt)
  {
    int[] arrayOfInt = new int[2];
    paramView.getLocationOnScreen(arrayOfInt);
    CrDb.d("grow grid view", "zoom x:" + arrayOfInt[0] + " zoom y:" + arrayOfInt[1]);
    int i = getChildCount() - 1;
    while (i >= 0)
    {
      if (getChildAt(i) != paramView) {
        getChildAt(i).setAlpha(0.0F);
      }
      i -= 1;
    }
    startScaling(paramInt);
    float f1 = getScreenWidth() / this.currentWidth;
    float f3 = getWidth() * Math.abs(1.0F - f1) / 2.0F;
    float f2 = getHeight() * Math.abs(1.0F - f1) / 2.0F;
    f3 -= arrayOfInt[0] * f1;
    float f4 = arrayOfInt[1];
    this.screenCapture.resetAlpha();
    CrDb.d("grow grid view", "factor: " + f1 + " endx: " + f3);
    zoom(f3, f2 - (f4 * f1 - 50.0F * f1), f1, true);
  }
  
  public void zoomOutOfCenter()
  {
    startScaling();
    float f = GalleryGridFragment.MINIMUM_THUMBNAIL_WIDTH / this.currentWidth;
    CrDb.d("grow grid view", "zoom " + f);
    zoom(0.0F, 0.0F, f, false);
  }
  
  private class ScaleListener
    extends ScaleGestureDetector.SimpleOnScaleGestureListener
  {
    private float mScaleFactor = 1.0F;
    
    private ScaleListener() {}
    
    public boolean onScale(ScaleGestureDetector paramScaleGestureDetector)
    {
      float f = this.mScaleFactor * paramScaleGestureDetector.getScaleFactor();
      if (GrowGridView.this.scaleEvent(f))
      {
        this.mScaleFactor = f;
        return true;
      }
      return false;
    }
    
    public boolean onScaleBegin(ScaleGestureDetector paramScaleGestureDetector)
    {
      if (!GrowGridView.this.canScale) {
        return false;
      }
      GrowGridView.this.startScaling();
      this.mScaleFactor = 1.0F;
      return true;
    }
    
    public void onScaleEnd(ScaleGestureDetector paramScaleGestureDetector)
    {
      GrowGridView.this.endScaling(this.mScaleFactor, true);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/grow/GrowGridView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */