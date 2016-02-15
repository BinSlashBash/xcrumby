package com.uservoice.uservoicesdk.rest;

import org.json.JSONException;
import org.json.JSONObject;

public class RestResult
{
  private Exception exception;
  private JSONObject object;
  private int statusCode;
  
  public RestResult(int paramInt, JSONObject paramJSONObject)
  {
    this.statusCode = paramInt;
    this.object = paramJSONObject;
  }
  
  public RestResult(Exception paramException)
  {
    this.exception = paramException;
  }
  
  public RestResult(Exception paramException, int paramInt, JSONObject paramJSONObject)
  {
    this.exception = paramException;
    this.statusCode = paramInt;
    this.object = paramJSONObject;
  }
  
  public Exception getException()
  {
    return this.exception;
  }
  
  public String getMessage()
  {
    if (this.exception == null) {}
    for (String str = String.valueOf(this.statusCode);; str = this.exception.getMessage()) {
      return String.format("%s -- %s", new Object[] { str, this.object });
    }
  }
  
  public JSONObject getObject()
  {
    return this.object;
  }
  
  public int getStatusCode()
  {
    return this.statusCode;
  }
  
  public String getType()
  {
    try
    {
      String str = this.object.getJSONObject("errors").getString("type");
      return str;
    }
    catch (JSONException localJSONException) {}
    return null;
  }
  
  public boolean isError()
  {
    return (this.exception != null) || (this.statusCode > 400);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/rest/RestResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */