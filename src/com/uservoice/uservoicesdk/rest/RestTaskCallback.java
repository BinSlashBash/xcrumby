package com.uservoice.uservoicesdk.rest;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class RestTaskCallback
{
  private final Callback<?> callback;
  
  public RestTaskCallback(Callback<?> paramCallback)
  {
    this.callback = paramCallback;
  }
  
  public abstract void onComplete(JSONObject paramJSONObject)
    throws JSONException;
  
  public void onError(RestResult paramRestResult)
  {
    this.callback.onError(paramRestResult);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/rest/RestTaskCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */