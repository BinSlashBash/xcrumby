package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

public class FormTabButton
  extends ImageButton
{
  private View view;
  
  public FormTabButton(Context paramContext)
  {
    super(paramContext);
  }
  
  public FormTabButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public FormTabButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void activate()
  {
    getBackground().setAlpha(255);
    getDrawable().setAlpha(255);
    this.view.setVisibility(0);
  }
  
  public void deactivate()
  {
    getBackground().setAlpha(0);
    getDrawable().setAlpha(200);
    if (this.view != null) {
      this.view.setVisibility(8);
    }
  }
  
  public void hide()
  {
    setVisibility(8);
    if (this.view != null) {
      this.view.setVisibility(8);
    }
  }
  
  public void initialize(TabManager paramTabManager)
  {
    setOnClickListener(paramTabManager);
    setImageDrawable(getDrawable().mutate());
    setBackgroundDrawable(getBackground().mutate());
  }
  
  public void setView(View paramView)
  {
    this.view = paramView;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/FormTabButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */