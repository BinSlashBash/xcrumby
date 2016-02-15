package com.crumby.lib.widget;

import android.content.res.Resources;
import android.view.View;
import android.view.animation.Transformation;
import com.crumby.lib.widget.thirdparty.ResizeHeightAnimation;

public class PhotoViewContainerResize
  extends ResizeHeightAnimation
{
  private int diff;
  private final int endPaddingTop;
  private final int startPaddingTop;
  
  public PhotoViewContainerResize(View paramView, int paramInt)
  {
    super(paramView, paramInt);
    this.startPaddingTop = paramView.getPaddingTop();
    this.diff = ((int)paramView.getResources().getDimension(2131165196));
    if (this.startPaddingTop == 0)
    {
      this.endPaddingTop = this.diff;
      return;
    }
    this.endPaddingTop = 0;
    this.diff = (-this.diff);
  }
  
  protected void applyTransformation(float paramFloat, Transformation paramTransformation)
  {
    int i = this.startPaddingTop;
    int j = (int)(this.diff * paramFloat);
    getView().setPadding(getView().getPaddingLeft(), i + j, getView().getPaddingRight(), getView().getPaddingBottom());
    super.applyTransformation(paramFloat, paramTransformation);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/PhotoViewContainerResize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */