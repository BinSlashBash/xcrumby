package com.tapstream.sdk;

public abstract interface Api
{
  public abstract void fireEvent(Event paramEvent);
  
  public abstract void fireHit(Hit paramHit, Hit.CompletionHandler paramCompletionHandler);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/tapstream/sdk/Api.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */