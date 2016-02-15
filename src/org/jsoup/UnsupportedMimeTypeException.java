package org.jsoup;

import java.io.IOException;

public class UnsupportedMimeTypeException
  extends IOException
{
  private String mimeType;
  private String url;
  
  public UnsupportedMimeTypeException(String paramString1, String paramString2, String paramString3)
  {
    super(paramString1);
    this.mimeType = paramString2;
    this.url = paramString3;
  }
  
  public String getMimeType()
  {
    return this.mimeType;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public String toString()
  {
    return super.toString() + ". Mimetype=" + this.mimeType + ", URL=" + this.url;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/UnsupportedMimeTypeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */