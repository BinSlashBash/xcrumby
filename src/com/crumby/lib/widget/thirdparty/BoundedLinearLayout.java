package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import com.crumby.R.styleable;

public class BoundedLinearLayout
  extends LinearLayout
{
  private final int mBoundedHeight;
  private final int mBoundedWidth;
  
  public BoundedLinearLayout(Context paramContext)
  {
    super(paramContext);
    this.mBoundedWidth = 0;
    this.mBoundedHeight = 0;
  }
  
  public BoundedLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    paramContext = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.BoundedView);
    this.mBoundedWidth = paramContext.getDimensionPixelSize(0, 0);
    this.mBoundedHeight = paramContext.getDimensionPixelSize(1, 0);
    paramContext.recycle();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int j = View.MeasureSpec.getSize(paramInt1);
    int i = paramInt1;
    if (this.mBoundedWidth > 0)
    {
      i = paramInt1;
      if (this.mBoundedWidth < j)
      {
        paramInt1 = View.MeasureSpec.getMode(paramInt1);
        i = View.MeasureSpec.makeMeasureSpec(this.mBoundedWidth, paramInt1);
      }
    }
    j = View.MeasureSpec.getSize(paramInt2);
    paramInt1 = paramInt2;
    if (this.mBoundedHeight > 0)
    {
      paramInt1 = paramInt2;
      if (this.mBoundedHeight < j)
      {
        paramInt1 = View.MeasureSpec.getMode(paramInt2);
        paramInt1 = View.MeasureSpec.makeMeasureSpec(this.mBoundedHeight, paramInt1);
      }
    }
    super.onMeasure(i, paramInt1);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/thirdparty/BoundedLinearLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */