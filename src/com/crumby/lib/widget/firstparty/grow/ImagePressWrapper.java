package com.crumby.lib.widget.firstparty.grow;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.widget.firstparty.multiselect.ImageCheckable;

public class ImagePressWrapper
  extends FrameLayout
  implements ImageCheckable
{
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  private boolean checked;
  
  public ImagePressWrapper(Context paramContext)
  {
    super(paramContext);
  }
  
  public ImagePressWrapper(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ImagePressWrapper(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public GalleryImage getImage()
  {
    return ((GridImageWrapper)getTag()).getImage();
  }
  
  public boolean isChecked()
  {
    return this.checked;
  }
  
  protected int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 2);
    if (isChecked()) {
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET);
    }
    return arrayOfInt;
  }
  
  public void setChecked(boolean paramBoolean)
  {
    this.checked = paramBoolean;
    if (getImage() != null) {
      getImage().setChecked(paramBoolean);
    }
    refreshDrawableState();
  }
  
  public void toggle()
  {
    if (!this.checked) {}
    for (boolean bool = true;; bool = false)
    {
      setChecked(bool);
      return;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/grow/ImagePressWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */