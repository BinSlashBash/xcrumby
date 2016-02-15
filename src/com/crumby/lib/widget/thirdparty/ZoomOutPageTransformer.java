package com.crumby.lib.widget.thirdparty;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class ZoomOutPageTransformer
  implements ViewPager.PageTransformer
{
  private static final float MIN_ALPHA = 0.5F;
  private static final float MIN_SCALE = 0.85F;
  
  public void transformPage(View paramView, float paramFloat)
  {
    int i = paramView.getWidth();
    int j = paramView.getHeight();
    if (paramFloat < -1.0F)
    {
      paramView.setAlpha(0.0F);
      return;
    }
    if (paramFloat <= 1.0F)
    {
      float f1 = Math.max(0.85F, 1.0F - Math.abs(paramFloat));
      float f2 = j * (1.0F - f1) / 2.0F;
      float f3 = i * (1.0F - f1) / 2.0F;
      if (paramFloat < 0.0F) {
        paramView.setTranslationX(f3 - f2 / 2.0F);
      }
      for (;;)
      {
        paramView.setScaleX(f1);
        paramView.setScaleY(f1);
        paramView.setAlpha((f1 - 0.85F) / 0.14999998F * 0.5F + 0.5F);
        return;
        paramView.setTranslationX(-f3 + f2 / 2.0F);
      }
    }
    paramView.setAlpha(0.0F);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/thirdparty/ZoomOutPageTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */