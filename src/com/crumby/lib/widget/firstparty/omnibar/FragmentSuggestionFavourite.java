package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class FragmentSuggestionFavourite
  extends ImageButton
{
  private boolean checked;
  
  public FragmentSuggestionFavourite(Context paramContext)
  {
    super(paramContext);
  }
  
  public FragmentSuggestionFavourite(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public FragmentSuggestionFavourite(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public boolean isChecked()
  {
    return this.checked;
  }
  
  public void setChecked(boolean paramBoolean)
  {
    this.checked = paramBoolean;
    if (paramBoolean)
    {
      setImageDrawable(getResources().getDrawable(2130837627));
      return;
    }
    setImageDrawable(getResources().getDrawable(2130837630));
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


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/FragmentSuggestionFavourite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */