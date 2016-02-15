package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GalleryViewerTab
  extends LinearLayout
{
  int taskId;
  String url;
  
  public GalleryViewerTab(Context paramContext, int paramInt, String paramString)
  {
    super(paramContext);
    setMinimumHeight(150);
    setMinimumWidth(100);
    paramContext = new TextView(paramContext);
    paramContext.setText(paramString);
    addView(paramContext);
    setClickable(true);
    this.taskId = paramInt;
  }
  
  public int getTaskId()
  {
    return this.taskId;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/GalleryViewerTab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */