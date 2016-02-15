package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.crumby.BusProvider;
import com.crumby.lib.events.OmniformEvent;
import com.crumby.lib.widget.thirdparty.HorizontalFlowLayout;
import com.squareup.otto.Bus;

public class GalleryGridDefaultTopLevelHeader
  extends HorizontalFlowLayout
{
  private View openLogin;
  private View openSearch;
  
  public GalleryGridDefaultTopLevelHeader(Context paramContext)
  {
    super(paramContext);
  }
  
  public GalleryGridDefaultTopLevelHeader(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public GalleryGridDefaultTopLevelHeader(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.openLogin = findViewById(2131492934);
    this.openLogin.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        BusProvider.BUS.get().post(new OmniformEvent("Login"));
      }
    });
    this.openSearch = findViewById(2131492933);
    this.openSearch.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        BusProvider.BUS.get().post(new OmniformEvent("Search"));
      }
    });
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/GalleryGridDefaultTopLevelHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */