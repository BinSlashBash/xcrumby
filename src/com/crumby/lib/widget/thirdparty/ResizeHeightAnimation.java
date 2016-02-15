package com.crumby.lib.widget.thirdparty;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ResizeHeightAnimation
  extends Animation
{
  int diff;
  int endH;
  int startH;
  View view;
  
  public ResizeHeightAnimation(View paramView, int paramInt)
  {
    this.view = paramView;
    this.startH = paramView.getLayoutParams().height;
    this.endH = paramInt;
    this.diff = (this.endH - this.startH);
  }
  
  protected void applyTransformation(float paramFloat, Transformation paramTransformation)
  {
    this.view.getLayoutParams().height = (this.startH + (int)(this.diff * paramFloat));
    this.view.requestLayout();
  }
  
  protected View getView()
  {
    return this.view;
  }
  
  public void initialize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.initialize(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public boolean willChangeBounds()
  {
    return true;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/thirdparty/ResizeHeightAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */