package com.crumby;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.TypedValue;
import com.squareup.okhttp.OkHttpClient;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.UserVoice;
import java.util.concurrent.TimeUnit;

public class CrumbyApp
  extends Application
{
  private static final OkHttpClient client = new OkHttpClient();
  
  public static float convertDpToPx(Resources paramResources, float paramFloat)
  {
    return TypedValue.applyDimension(1, paramFloat, paramResources.getDisplayMetrics());
  }
  
  public static OkHttpClient getHttpClient()
  {
    return client;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }
  
  public void onCreate()
  {
    UserVoice.init(new Config("crumby.uservoice.com"), this);
    Analytics.INSTANCE.createTracker(getApplicationContext());
    client.setFollowSslRedirects(true);
    client.setConnectTimeout(15L, TimeUnit.SECONDS);
    com.crumby.lib.fragment.GalleryListFragment.THUMBNAIL_HEIGHT = (int)getResources().getDimension(2131165191);
    com.crumby.lib.fragment.GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH = (int)getResources().getDimension(2131165190);
  }
  
  public void onLowMemory()
  {
    super.onLowMemory();
  }
  
  public void onTerminate()
  {
    super.onTerminate();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/CrumbyApp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */