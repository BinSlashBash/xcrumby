package com.crumby;

import android.content.Context;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.impl.crumby.HomeFragment;
import com.crumby.lib.download.ImageDownload;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders.AppViewBuilder;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.HitBuilders.ExceptionBuilder;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.HitBuilders.TimingBuilder;
import com.google.android.gms.analytics.Tracker;

public enum Analytics
{
  INSTANCE;
  
  private static final int NO_VALUE = -99999999;
  private final String PRODUCTION_TRACKER = "UA-27893558-3";
  private final String TESTING_TRACKER = "UA-27893558-2";
  long lastNavTime;
  String lastScreenName;
  String screenName;
  private Tracker tracker;
  
  private Analytics() {}
  
  public void createTracker(Context paramContext)
  {
    this.tracker = GoogleAnalytics.getInstance(paramContext).newTracker("UA-27893558-2");
    this.tracker.send(((HitBuilders.AppViewBuilder)new HitBuilders.AppViewBuilder().setNewSession()).build());
    this.lastNavTime = System.currentTimeMillis();
    new ExceptionReporter(this.tracker, Thread.getDefaultUncaughtExceptionHandler(), paramContext);
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
    {
      public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable) {}
    });
  }
  
  public void end() {}
  
  public Tracker get()
  {
    return this.tracker;
  }
  
  public void newError(DisplayError paramDisplayError, String paramString)
  {
    this.tracker.send(new HitBuilders.ExceptionBuilder().setDescription(paramDisplayError.getErrorCode() + " " + paramString).setFatal(false).build());
  }
  
  public void newEvent(AnalyticsCategories paramAnalyticsCategories, String paramString1, String paramString2)
  {
    newEvent(paramAnalyticsCategories, paramString1, paramString2, -99999999L);
  }
  
  public void newEvent(AnalyticsCategories paramAnalyticsCategories, String paramString1, String paramString2, long paramLong)
  {
    paramAnalyticsCategories = new HitBuilders.EventBuilder().setCategory(paramAnalyticsCategories.getName()).setLabel(paramString2).setAction(paramString1);
    if (paramLong != -99999999L) {
      paramAnalyticsCategories.setValue(paramLong);
    }
    this.tracker.send(paramAnalyticsCategories.build());
  }
  
  public void newException(Exception paramException)
  {
    this.tracker.send(new HitBuilders.ExceptionBuilder().setDescription(paramException.getMessage() + " " + paramException.getStackTrace()).setFatal(false).build());
  }
  
  public void newImageDownloadEvent(ImageDownload paramImageDownload, String paramString)
  {
    newEvent(AnalyticsCategories.DOWNLOADS, paramString, paramImageDownload.getDownloadUri());
  }
  
  public void newImageDownloadEvent(ImageDownload paramImageDownload, String paramString, long paramLong)
  {
    newTimingEvent(AnalyticsCategories.DOWNLOADS, paramLong, paramString, paramImageDownload.getDownloadUri());
  }
  
  public void newNavigationEvent(String paramString1, String paramString2)
  {
    try
    {
      long l = System.currentTimeMillis();
      newEvent(AnalyticsCategories.NAVIGATION, paramString1, paramString2, -99999999L);
      this.lastNavTime = l;
      return;
    }
    finally
    {
      paramString1 = finally;
      throw paramString1;
    }
  }
  
  public void newPage(GalleryViewerFragment paramGalleryViewerFragment)
  {
    String str = paramGalleryViewerFragment.getUrl();
    if ((paramGalleryViewerFragment instanceof CrumbyGalleryFragment)) {
      str = "s: " + paramGalleryViewerFragment.getUrl();
    }
    for (;;)
    {
      newScreen(str);
      return;
      if ((paramGalleryViewerFragment instanceof HomeFragment)) {
        str = "home";
      }
    }
  }
  
  public void newScreen(String paramString)
  {
    this.lastScreenName = this.screenName;
    this.screenName = paramString;
    this.tracker.setScreenName(paramString);
    this.tracker.send(new HitBuilders.ScreenViewBuilder().build());
  }
  
  public void newTimingEvent(AnalyticsCategories paramAnalyticsCategories, long paramLong, String paramString1, String paramString2)
  {
    long l = System.currentTimeMillis();
    this.tracker.send(new HitBuilders.TimingBuilder().setCategory(paramAnalyticsCategories.getName()).setValue(l - paramLong).setVariable(paramString1).setLabel(paramString2).build());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/Analytics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */