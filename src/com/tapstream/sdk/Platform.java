package com.tapstream.sdk;

import java.util.Set;
import java.util.concurrent.ThreadFactory;

abstract interface Platform
{
  public abstract String getAdvertisingId();
  
  public abstract String getAppName();
  
  public abstract String getAppVersion();
  
  public abstract Boolean getLimitAdTracking();
  
  public abstract String getLocale();
  
  public abstract String getManufacturer();
  
  public abstract String getModel();
  
  public abstract String getOs();
  
  public abstract String getPackageName();
  
  public abstract Set<String> getProcessSet();
  
  public abstract String getReferrer();
  
  public abstract String getResolution();
  
  public abstract Set<String> loadFiredEvents();
  
  public abstract String loadUuid();
  
  public abstract ThreadFactory makeWorkerThreadFactory();
  
  public abstract Response request(String paramString1, String paramString2, String paramString3);
  
  public abstract void saveFiredEvents(Set<String> paramSet);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/tapstream/sdk/Platform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */