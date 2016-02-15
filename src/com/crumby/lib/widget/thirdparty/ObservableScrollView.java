package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ObservableScrollView
  extends ScrollView
{
  private ScrollViewListener scrollViewListener = null;
  
  public ObservableScrollView(Context paramContext)
  {
    super(paramContext);
  }
  
  public ObservableScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ObservableScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void onFinishInflate()
  {
    setOverScrollMode(0);
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.scrollViewListener != null) {
      this.scrollViewListener.onScrollChanged(this, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public void setScrollViewListener(ScrollViewListener paramScrollViewListener)
  {
    this.scrollViewListener = paramScrollViewListener;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/thirdparty/ObservableScrollView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */