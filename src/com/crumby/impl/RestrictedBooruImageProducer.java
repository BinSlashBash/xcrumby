package com.crumby.impl;

public class RestrictedBooruImageProducer
  extends BooruImageProducer
{
  public RestrictedBooruImageProducer(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  protected String getApiUrlForImage(String paramString)
  {
    return this.baseUrl + "/post.xml?tags=id:" + paramString;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/RestrictedBooruImageProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */