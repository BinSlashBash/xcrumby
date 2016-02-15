package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class TapAwareRelativeLayout
  extends RelativeLayout
{
  private final float MOVE_THRESHOLD_DP = 20.0F * getResources().getDisplayMetrics().density;
  private float mDownPosX;
  private float mDownPosY;
  private boolean mMoveOccured;
  
  public TapAwareRelativeLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public TapAwareRelativeLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    }
    for (;;)
    {
      return super.onInterceptTouchEvent(paramMotionEvent);
      this.mMoveOccured = false;
      this.mDownPosX = paramMotionEvent.getX();
      this.mDownPosY = paramMotionEvent.getY();
      continue;
      if (!this.mMoveOccured)
      {
        performClick();
        continue;
        if ((Math.abs(paramMotionEvent.getX() - this.mDownPosX) > this.MOVE_THRESHOLD_DP) || (Math.abs(paramMotionEvent.getY() - this.mDownPosY) > this.MOVE_THRESHOLD_DP)) {
          this.mMoveOccured = true;
        }
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/thirdparty/TapAwareRelativeLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */