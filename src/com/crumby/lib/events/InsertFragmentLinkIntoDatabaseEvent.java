package com.crumby.lib.events;

public class InsertFragmentLinkIntoDatabaseEvent
{
  public String thumbnail;
  public String url;
  
  public InsertFragmentLinkIntoDatabaseEvent(String paramString1, String paramString2)
  {
    this.url = paramString1;
    this.thumbnail = paramString2;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/events/InsertFragmentLinkIntoDatabaseEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */