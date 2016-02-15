package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

public class ExpandActionBarButton
  extends ImageButton
{
  private int startY;
  
  public ExpandActionBarButton(Context paramContext)
  {
    super(paramContext);
  }
  
  public ExpandActionBarButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ExpandActionBarButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public TranslateAnimation getTranslateAnimation(int paramInt1, int paramInt2)
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(0.0F, 0.0F, paramInt1, paramInt2);
    localTranslateAnimation.setInterpolator(new DecelerateInterpolator());
    localTranslateAnimation.setDuration(200L);
    return localTranslateAnimation;
  }
  
  public void hide()
  {
    setAnimation(getTranslateAnimation(0, -getBottom()));
    setVisibility(4);
  }
  
  public void show()
  {
    setAnimation(getTranslateAnimation(-getBottom(), 0));
    setVisibility(0);
    bringToFront();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/ExpandActionBarButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */