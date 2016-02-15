package com.uservoice.uservoicesdk.rest;

public abstract class Callback<T>
{
  public abstract void onError(RestResult paramRestResult);
  
  public abstract void onModel(T paramT);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/rest/Callback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */