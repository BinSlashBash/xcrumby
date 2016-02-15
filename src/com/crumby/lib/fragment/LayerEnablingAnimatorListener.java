package com.crumby.lib.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class LayerEnablingAnimatorListener
  extends AnimatorListenerAdapter
{
  private int mLayerType;
  private final View mTargetView;
  
  public LayerEnablingAnimatorListener(View paramView)
  {
    this.mTargetView = paramView;
  }
  
  public View getTargetView()
  {
    return this.mTargetView;
  }
  
  public void onAnimationEnd(Animator paramAnimator)
  {
    super.onAnimationEnd(paramAnimator);
    this.mTargetView.setLayerType(this.mLayerType, null);
  }
  
  public void onAnimationStart(Animator paramAnimator)
  {
    super.onAnimationStart(paramAnimator);
    this.mLayerType = this.mTargetView.getLayerType();
    this.mTargetView.setLayerType(2, null);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/LayerEnablingAnimatorListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */