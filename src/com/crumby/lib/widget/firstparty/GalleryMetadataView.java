package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class GalleryMetadataView
  extends LinearLayout
{
  private int height;
  private int width;
  
  public GalleryMetadataView(Context paramContext)
  {
    super(paramContext);
  }
  
  public GalleryMetadataView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public GalleryMetadataView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if (this.width == 0)
    {
      this.width = getMeasuredWidth();
      this.height = getMeasuredHeight();
    }
    setMeasuredDimension(this.width, this.height);
    setRight(this.width);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return super.onTouchEvent(paramMotionEvent);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/GalleryMetadataView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */