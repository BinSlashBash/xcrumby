package org.jsoup;

import java.io.IOException;

public class HttpStatusException
  extends IOException
{
  private int statusCode;
  private String url;
  
  public HttpStatusException(String paramString1, int paramInt, String paramString2)
  {
    super(paramString1);
    this.statusCode = paramInt;
    this.url = paramString2;
  }
  
  public int getStatusCode()
  {
    return this.statusCode;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public String toString()
  {
    return super.toString() + ". Status=" + this.statusCode + ", URL=" + this.url;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/HttpStatusException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */