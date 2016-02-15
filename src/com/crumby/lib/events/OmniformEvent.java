package com.crumby.lib.events;

public class OmniformEvent
{
  public static final String LOGIN = "Login";
  public static final String SEARCH = "Search";
  public String eventName;
  
  public OmniformEvent(String paramString)
  {
    this.eventName = paramString;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/events/OmniformEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */