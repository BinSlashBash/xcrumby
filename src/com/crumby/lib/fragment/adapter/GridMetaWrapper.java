package com.crumby.lib.fragment.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class GridMetaWrapper
  extends RelativeLayout
{
  public GridMetaWrapper(Context paramContext)
  {
    super(paramContext);
  }
  
  public GridMetaWrapper(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public GridMetaWrapper(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void setAlpha(float paramFloat)
  {
    super.setAlpha(paramFloat);
    int i = getChildCount() - 1;
    while (i >= 0)
    {
      getChildAt(i).setAlpha(paramFloat);
      i -= 1;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/adapter/GridMetaWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */