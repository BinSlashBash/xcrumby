package com.crumby.lib.widget.firstparty.grow;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.TextView;

public class ColumnCounter
  extends TextView
{
  private int columnMax = getResources().getColor(2131427501);
  private int columnNormal = getResources().getColor(2131427356);
  private boolean outOfBounds;
  
  public ColumnCounter(Context paramContext)
  {
    super(paramContext);
  }
  
  public ColumnCounter(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ColumnCounter(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void hide()
  {
    setVisibility(8);
  }
  
  public void indicateFree()
  {
    setTextColor(this.columnNormal);
    setPressed(false);
    this.outOfBounds = false;
  }
  
  public void indicateLimit()
  {
    setPressed(true);
    setTextColor(this.columnMax);
    this.outOfBounds = true;
  }
  
  public void set(int paramInt)
  {
    String str = "";
    int i = 0;
    while (i < paramInt)
    {
      str = str + " ■";
      i += 1;
    }
    setText(str + " ");
  }
  
  public void show()
  {
    setVisibility(0);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/grow/ColumnCounter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */