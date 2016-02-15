package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import com.crumby.lib.widget.firstparty.multiselect.ImageCheckable;

public abstract class CustomToggleButton
  extends ImageButton
  implements ImageCheckable
{
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  
  public CustomToggleButton(Context paramContext)
  {
    super(paramContext);
  }
  
  public CustomToggleButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public CustomToggleButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 2);
    if (isChecked()) {
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET);
    }
    return arrayOfInt;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/fragment_options/CustomToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */