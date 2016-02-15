package com.tapstream.sdk;

public class ActivityEventSource
{
  protected ActivityListener listener = null;
  
  public void setListener(ActivityListener paramActivityListener)
  {
    this.listener = paramActivityListener;
  }
  
  public static abstract interface ActivityListener
  {
    public abstract void onOpen();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/tapstream/sdk/ActivityEventSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */