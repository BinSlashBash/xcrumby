package com.crumby.lib.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.CrumbyApp;
import com.crumby.lib.widget.firstparty.GalleryImageTutorial;
import com.crumby.lib.widget.firstparty.ScreenCapture;
import com.crumby.lib.widget.thirdparty.ObservableScrollView;
import uk.co.senab.photoview.ExpandContainerScaleListener;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageScrollView
  extends ObservableScrollView
  implements ExpandContainerScaleListener
{
  public static boolean userWantsFullScreen;
  private boolean animating;
  int imageHeight;
  private View imageView;
  private View imageViewContainer;
  private boolean inFullScreen;
  boolean measuredHeightIsDeadWrong;
  private View metadata;
  private boolean omniSearchHackActive;
  private OnImageScaleListener onImageScaleListener;
  private View revertFromFullScreen;
  private float savedHeight;
  private float savedTopY;
  private ScreenCapture screenCapture;
  private TextView title;
  private GalleryImageTutorial tutorial;
  
  public ImageScrollView(Context paramContext)
  {
    super(paramContext);
  }
  
  public ImageScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ImageScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void forceHeight(View paramView, int paramInt)
  {
    if (paramView.getLayoutParams().height == paramInt) {
      return;
    }
    paramView.getLayoutParams().height = paramInt;
  }
  
  private void omniSearchHackReset()
  {
    this.omniSearchHackActive = false;
    this.metadata.setMinimumHeight(0);
    this.screenCapture.setPadding(0, 0, 0, 0);
    this.screenCapture.hide();
    findViewById(2131493035).setVisibility(0);
  }
  
  private void startAnimation(View paramView, final float paramFloat)
  {
    PhotoViewContainerResize localPhotoViewContainerResize = new PhotoViewContainerResize(paramView, (int)paramFloat);
    this.animating = true;
    localPhotoViewContainerResize.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        if (paramFloat == ImageScrollView.this.savedHeight) {
          ImageScrollView.this.metadata.setVisibility(0);
        }
        for (;;)
        {
          ImageScrollView.access$202(ImageScrollView.this, false);
          return;
          ImageScrollView.this.metadata.setVisibility(8);
        }
      }
      
      public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public void onAnimationStart(Animation paramAnonymousAnimation) {}
    });
    localPhotoViewContainerResize.setInterpolator(getContext(), 17563654);
    localPhotoViewContainerResize.setDuration(300L);
    paramView.startAnimation(localPhotoViewContainerResize);
  }
  
  public boolean contract()
  {
    return contract(true);
  }
  
  public boolean contract(boolean paramBoolean)
  {
    if ((this.inFullScreen) && (!this.animating))
    {
      View localView = findViewById(2131493039);
      localView.clearAnimation();
      if (paramBoolean)
      {
        startAnimation(this.imageViewContainer, this.savedHeight);
        if (this.savedTopY != 0.0F) {
          localView.animate().y(this.savedTopY);
        }
      }
      for (;;)
      {
        this.metadata.setVisibility(0);
        this.revertFromFullScreen.setVisibility(8);
        this.inFullScreen = false;
        userWantsFullScreen = false;
        this.title.setSingleLine(true);
        if (this.onImageScaleListener != null) {
          this.onImageScaleListener.onContract();
        }
        return true;
        forceHeight(this.imageViewContainer, (int)this.savedHeight);
        this.imageViewContainer.setPadding(this.imageViewContainer.getPaddingLeft(), (int)getResources().getDimension(2131165196), this.imageViewContainer.getPaddingRight(), this.imageViewContainer.getPaddingBottom());
        if (this.savedTopY != 0.0F) {
          localView.setY(this.savedTopY);
        }
      }
    }
    return false;
  }
  
  public boolean expand()
  {
    return expand(true);
  }
  
  public boolean expand(boolean paramBoolean)
  {
    if ((!this.inFullScreen) && (!this.animating))
    {
      if (this.onImageScaleListener != null) {
        this.onImageScaleListener.onExpand();
      }
      int i = getMeasuredHeight();
      if (i != 0)
      {
        if (this.tutorial != null) {
          this.tutorial.clearTutorial();
        }
        View localView = findViewById(2131493039);
        localView.clearAnimation();
        if (this.savedTopY == 0.0F) {
          this.savedTopY = localView.getY();
        }
        if (paramBoolean)
        {
          smoothScrollTo(0, 0);
          startAnimation(this.imageViewContainer, i + CrumbyApp.convertDpToPx(getResources(), 30.0F));
          localView.animate().y(0.0F);
        }
        for (;;)
        {
          this.revertFromFullScreen.setVisibility(0);
          this.inFullScreen = true;
          userWantsFullScreen = true;
          this.title.setSingleLine(false);
          return true;
          forceHeight(this.imageViewContainer, i);
          this.imageViewContainer.setPadding(this.imageViewContainer.getPaddingLeft(), 0, this.imageViewContainer.getPaddingRight(), this.imageViewContainer.getPaddingBottom());
          this.metadata.setVisibility(8);
          localView.setY(0.0F);
        }
      }
      this.measuredHeightIsDeadWrong = true;
    }
    return false;
  }
  
  public int getDistanceFromBottom()
  {
    return getLastChildBottom();
  }
  
  public int getLastChildBottom()
  {
    return getChildAt(getChildCount() - 1).getBottom();
  }
  
  public boolean isInFullScreen()
  {
    return this.inFullScreen;
  }
  
  public void omniSearchIsNotShowingHack()
  {
    if ((!this.inFullScreen) && (this.omniSearchHackActive)) {
      omniSearchHackReset();
    }
  }
  
  public void omniSearchIsShowingHack(boolean paramBoolean)
  {
    if ((!this.inFullScreen) && (!this.omniSearchHackActive))
    {
      this.omniSearchHackActive = true;
      this.metadata.setMinimumHeight(this.metadata.getMeasuredHeight());
      View localView = findViewById(2131493035);
      int i = getScrollY() - this.metadata.getTop();
      CrDb.d("image scroll view", getScrollY() + " " + this.metadata.getTop());
      int j = Math.min(localView.getHeight(), getResources().getDisplayMetrics().heightPixels);
      if (i > 0) {
        this.screenCapture.setPadding(0, i, 0, 0);
      }
      if (paramBoolean) {
        this.screenCapture.captureAndShowScreenAsync(i, i + j);
      }
      findViewById(2131493035).setVisibility(8);
    }
  }
  
  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    this.measuredHeightIsDeadWrong = true;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.onImageScaleListener = null;
    CrDb.d("image scroll view", "detached!");
    if (this.screenCapture != null)
    {
      this.screenCapture.recycle();
      this.screenCapture = null;
    }
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    this.imageView = findViewById(2131493031);
    ((PhotoViewAttacher)((PhotoView)this.imageView).getIPhotoViewImplementation()).setExpandContainerScaleListener(this);
    this.imageViewContainer = findViewById(2131493026);
    this.metadata = findViewById(2131493033);
    this.revertFromFullScreen = findViewById(2131493032);
    this.revertFromFullScreen.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "revert from full screen", null);
        ImageScrollView.this.contract();
      }
    });
    this.imageViewContainer.setClickable(true);
    this.screenCapture = new ScreenCapture(findViewById(2131493035), (ImageView)findViewById(2131493037));
    this.title = ((TextView)findViewById(2131493040));
    this.savedHeight = getResources().getDimension(2131165193);
    if (userWantsFullScreen) {
      expand(false);
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.measuredHeightIsDeadWrong)
    {
      postDelayed(new Runnable()
      {
        public void run()
        {
          int i = ImageScrollView.this.imageHeight;
          ImageScrollView.this.imageHeight = 0;
          ImageScrollView.this.render(i);
        }
      }, 0L);
      this.measuredHeightIsDeadWrong = false;
    }
    if ((this.omniSearchHackActive) && (paramInt1 != paramInt3)) {
      omniSearchHackReset();
    }
  }
  
  public void render(int paramInt)
  {
    if ((paramInt == this.imageHeight) && (userWantsFullScreen == this.inFullScreen)) {
      return;
    }
    this.imageHeight = paramInt;
    float f = getResources().getDisplayMetrics().heightPixels * 0.7F;
    this.savedHeight = ((int)Math.min(f, Math.max(getResources().getDimension(2131165193), Math.min(this.imageHeight, f))));
    if (userWantsFullScreen)
    {
      this.inFullScreen = false;
      expand(false);
      return;
    }
    this.inFullScreen = true;
    contract(false);
  }
  
  public void setOnImageScaleListener(OnImageScaleListener paramOnImageScaleListener)
  {
    this.onImageScaleListener = paramOnImageScaleListener;
  }
  
  public void setTutorial(GalleryImageTutorial paramGalleryImageTutorial)
  {
    this.tutorial = paramGalleryImageTutorial;
  }
  
  public void toggle()
  {
    Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "toggle full screen", null);
    if (this.inFullScreen)
    {
      contract();
      return;
    }
    expand();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/ImageScrollView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */