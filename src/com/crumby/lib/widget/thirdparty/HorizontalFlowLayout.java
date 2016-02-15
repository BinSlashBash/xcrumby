package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RelativeLayout;
import java.util.LinkedList;
import java.util.Queue;

public class HorizontalFlowLayout
  extends RelativeLayout
{
  private Queue<View> lastMeasured = new LinkedList();
  
  public HorizontalFlowLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public HorizontalFlowLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public HorizontalFlowLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramInt4 = getPaddingLeft();
    paramInt2 = getPaddingTop();
    int i = 0;
    int j = 0;
    if (j < getChildCount())
    {
      View localView = getChildAt(j);
      int n = i;
      int m = paramInt4;
      int k = paramInt2;
      int i2;
      int i3;
      int i1;
      if (localView.getVisibility() != 8)
      {
        i2 = localView.getMeasuredWidth();
        i3 = localView.getMeasuredHeight();
        if ((localView.getLayoutParams() == null) || (!(localView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams))) {
          break label239;
        }
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localView.getLayoutParams();
        k = localMarginLayoutParams.leftMargin;
        m = localMarginLayoutParams.rightMargin;
        n = localMarginLayoutParams.topMargin;
        i1 = localMarginLayoutParams.bottomMargin;
        label126:
        if (paramInt4 + k + i2 + m + getPaddingRight() <= paramInt3 - paramInt1) {
          break label254;
        }
        paramInt4 = getPaddingLeft();
        paramInt2 += i;
      }
      label239:
      label254:
      for (i = i3 + n + i1;; i = Math.max(i, n + i3 + i1))
      {
        localView.layout(paramInt4 + k, paramInt2 + n, paramInt4 + k + i2, paramInt2 + n + i3);
        m = paramInt4 + (k + i2 + m);
        k = paramInt2;
        n = i;
        j += 1;
        i = n;
        paramInt4 = m;
        paramInt2 = k;
        break;
        k = 0;
        m = 0;
        n = 0;
        i1 = 0;
        break label126;
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i4 = View.MeasureSpec.getSize(paramInt1);
    int i3 = View.MeasureSpec.getSize(paramInt2);
    int i = getPaddingLeft();
    paramInt1 = getPaddingTop();
    int j = 0;
    int k = 0;
    if (k < getChildCount())
    {
      Object localObject = getChildAt(k);
      int i1 = j;
      int n = i;
      int m = paramInt1;
      int i5;
      int i6;
      int i2;
      if (((View)localObject).getVisibility() != 8)
      {
        i5 = ((View)localObject).getMeasuredWidth();
        i6 = ((View)localObject).getMeasuredHeight();
        if ((((View)localObject).getLayoutParams() == null) || (!(((View)localObject).getLayoutParams() instanceof ViewGroup.MarginLayoutParams))) {
          break label220;
        }
        localObject = (ViewGroup.MarginLayoutParams)((View)localObject).getLayoutParams();
        m = ((ViewGroup.MarginLayoutParams)localObject).leftMargin;
        n = ((ViewGroup.MarginLayoutParams)localObject).rightMargin;
        i2 = ((ViewGroup.MarginLayoutParams)localObject).topMargin;
        i1 = ((ViewGroup.MarginLayoutParams)localObject).bottomMargin;
        label142:
        if (i + m + i5 + n + getPaddingRight() <= i4) {
          break label235;
        }
        i = getPaddingLeft();
        paramInt1 += j;
      }
      label220:
      label235:
      for (j = i2 + i6 + i1;; j = Math.max(j, i2 + i6 + i1))
      {
        n = i + (m + i5 + n);
        m = paramInt1;
        i1 = j;
        k += 1;
        j = i1;
        i = n;
        paramInt1 = m;
        break;
        m = 0;
        n = 0;
        i2 = 0;
        i1 = 0;
        break label142;
      }
    }
    i = paramInt1 + (getPaddingBottom() + j);
    if (View.MeasureSpec.getMode(paramInt2) == 0) {
      paramInt1 = i;
    }
    for (;;)
    {
      setMeasuredDimension(i4, paramInt1);
      return;
      paramInt1 = i3;
      if (View.MeasureSpec.getMode(paramInt2) == Integer.MIN_VALUE)
      {
        paramInt1 = i3;
        if (i < i3) {
          paramInt1 = i;
        }
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/thirdparty/HorizontalFlowLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */