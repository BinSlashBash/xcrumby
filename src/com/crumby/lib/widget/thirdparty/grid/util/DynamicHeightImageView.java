package com.crumby.lib.widget.thirdparty.grid.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

public class DynamicHeightImageView
  extends ImageView
{
  private double mHeightRatio;
  
  public DynamicHeightImageView(Context paramContext)
  {
    super(paramContext);
  }
  
  public DynamicHeightImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public double getHeightRatio()
  {
    return this.mHeightRatio;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mHeightRatio > 0.0D)
    {
      paramInt1 = View.MeasureSpec.getSize(paramInt1);
      setMeasuredDimension(paramInt1, (int)(paramInt1 * this.mHeightRatio));
      return;
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void setHeightRatio(double paramDouble)
  {
    if (paramDouble != this.mHeightRatio)
    {
      this.mHeightRatio = paramDouble;
      requestLayout();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/thirdparty/grid/util/DynamicHeightImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */