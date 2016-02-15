package uk.co.senab.photoview.gestures;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import uk.co.senab.photoview.Compat;

@TargetApi(5)
public class EclairGestureDetector
  extends CupcakeGestureDetector
{
  private static final int INVALID_POINTER_ID = -1;
  private int mActivePointerId = -1;
  private int mActivePointerIndex = 0;
  
  public EclairGestureDetector(Context paramContext)
  {
    super(paramContext);
  }
  
  float getActiveX(MotionEvent paramMotionEvent)
  {
    try
    {
      float f = paramMotionEvent.getX(this.mActivePointerIndex);
      return f;
    }
    catch (Exception localException) {}
    return paramMotionEvent.getX();
  }
  
  float getActiveY(MotionEvent paramMotionEvent)
  {
    try
    {
      float f = paramMotionEvent.getY(this.mActivePointerIndex);
      return f;
    }
    catch (Exception localException) {}
    return paramMotionEvent.getY();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int j = 0;
    switch (paramMotionEvent.getAction() & 0xFF)
    {
    }
    do
    {
      for (;;)
      {
        i = j;
        if (this.mActivePointerId != -1) {
          i = this.mActivePointerId;
        }
        this.mActivePointerIndex = paramMotionEvent.findPointerIndex(i);
        return super.onTouchEvent(paramMotionEvent);
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
        continue;
        this.mActivePointerId = -1;
      }
      i = Compat.getPointerIndex(paramMotionEvent.getAction());
    } while (paramMotionEvent.getPointerId(i) != this.mActivePointerId);
    if (i == 0) {}
    for (int i = 1;; i = 0)
    {
      this.mActivePointerId = paramMotionEvent.getPointerId(i);
      this.mLastTouchX = paramMotionEvent.getX(i);
      this.mLastTouchY = paramMotionEvent.getY(i);
      break;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/uk/co/senab/photoview/gestures/EclairGestureDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */