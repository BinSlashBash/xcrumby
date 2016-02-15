package com.crumby.lib.widget.firstparty.main_menu;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;
import com.crumby.lib.download.ImageDownload;
import com.crumby.lib.download.ImageDownloadListener;
import com.crumby.lib.download.ImageDownloadManager;
import java.util.ArrayList;

public class DownloadIndicator
  extends TextView
  implements ImageDownloadListener
{
  public DownloadIndicator(Context paramContext)
  {
    super(paramContext);
  }
  
  public DownloadIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public DownloadIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void setCount(int paramInt)
  {
    setText(paramInt + "");
    setAlpha(1.0F);
    clearAnimation();
    if (paramInt <= 0)
    {
      setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(2130837604), null, null, null);
      setText(null);
      animate().alpha(0.0F).setStartDelay(300L).setDuration(1000L);
    }
    for (;;)
    {
      invalidate();
      return;
      setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }
  }
  
  private void updateCounter()
  {
    post(new Runnable()
    {
      public void run()
      {
        int i = ImageDownloadManager.INSTANCE.getDownloadingCount();
        DownloadIndicator.this.setCount(i);
      }
    });
  }
  
  public void terminate()
  {
    setCount(0);
  }
  
  public void update(ImageDownload paramImageDownload)
  {
    updateCounter();
  }
  
  public void update(ArrayList<ImageDownload> paramArrayList)
  {
    updateCounter();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/main_menu/DownloadIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */