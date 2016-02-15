package com.crumby.impl.derpibooru;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.GalleryViewer;
import com.crumby.lib.authentication.UserData;
import com.crumby.lib.events.UrlChangeEvent;
import com.squareup.otto.Bus;

public class DerpibooruAccountLayout
  extends LinearLayout
  implements UserData
{
  public DerpibooruAccountLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public DerpibooruAccountLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public DerpibooruAccountLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void fillWith(Object paramObject)
  {
    ((TextView)findViewById(2131492935)).setText("Logged in as " + (String)paramObject);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    findViewById(2131492936).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "user login click", "https://derpibooru.org/images/favourites");
        BusProvider.BUS.get().post(new UrlChangeEvent("https://derpibooru.org/images/favourites", true));
      }
    });
    findViewById(2131492937).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "user login click", "https://derpibooru.org/images/watched");
        BusProvider.BUS.get().post(new UrlChangeEvent("https://derpibooru.org/images/watched", true));
      }
    });
    findViewById(2131492938).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "user login click", "https://derpibooru.org/images/upvoted");
        BusProvider.BUS.get().post(new UrlChangeEvent("https://derpibooru.org/images/upvoted", true));
      }
    });
    findViewById(2131492939).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "user login click", "https://derpibooru.org/images/uploaded");
        BusProvider.BUS.get().post(new UrlChangeEvent("https://derpibooru.org/images/uploaded", true));
      }
    });
    findViewById(2131492940).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.OMNIBAR, "relogin", "derpibooru");
        ((GalleryViewer)DerpibooruAccountLayout.this.getContext()).autoLogin(true);
      }
    });
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/DerpibooruAccountLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */