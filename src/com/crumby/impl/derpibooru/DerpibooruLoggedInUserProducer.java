package com.crumby.impl.derpibooru;

import com.google.gson.JsonArray;

public abstract class DerpibooruLoggedInUserProducer
  extends DerpibooruProducer
{
  protected JsonArray getDerpImages(int paramInt)
    throws Exception
  {
    if (paramInt == 0) {}
    for (String str = "";; str = "?page=" + (paramInt + 1)) {
      return getDerpImages("https://derpibooru.org" + getSuffix() + ".json" + str, "images");
    }
  }
  
  protected abstract String getSuffix();
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/DerpibooruLoggedInUserProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */