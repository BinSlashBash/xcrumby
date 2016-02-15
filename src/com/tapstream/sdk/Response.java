package com.tapstream.sdk;

public class Response
{
  public String data;
  public String message;
  public int status;
  
  public Response(int paramInt, String paramString1, String paramString2)
  {
    this.status = paramInt;
    this.message = paramString1;
    this.data = paramString2;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/tapstream/sdk/Response.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */