package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class VerticalScrollView
  extends ObservableScrollView
{
  private float lastX;
  private float lastY;
  private Runnable runnable;
  private float xDistance;
  private float yDistance;
  
  public VerticalScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public void loadInitial(Runnable paramRunnable)
  {
    this.runnable = paramRunnable;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    }
    do
    {
      for (;;)
      {
        return super.onInterceptTouchEvent(paramMotionEvent);
        this.yDistance = 0.0F;
        this.xDistance = 0.0F;
        this.lastX = paramMotionEvent.getX();
        this.lastY = paramMotionEvent.getY();
      }
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      this.xDistance += Math.abs(f1 - this.lastX);
      this.yDistance += Math.abs(f2 - this.lastY);
      this.lastX = f1;
      this.lastY = f2;
    } while (this.xDistance <= this.yDistance);
    return false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.runnable != null)
    {
      this.runnable.run();
      this.runnable = null;
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/thirdparty/VerticalScrollView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */