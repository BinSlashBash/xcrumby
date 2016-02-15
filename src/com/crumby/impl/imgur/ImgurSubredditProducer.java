package com.crumby.impl.imgur;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;

public class ImgurSubredditProducer
  extends ImgurProducer
{
  String subredditId;
  
  protected boolean fetchMetadata()
    throws IOException
  {
    JsonObject localJsonObject = JSON_PARSER.parse(fetchUrl("http://www.reddit.com/r/" + this.subredditId + "/about.json")).getAsJsonObject().get("data").getAsJsonObject();
    String str = localJsonObject.get("description").getAsString();
    setGalleryMetadata(localJsonObject.get("title").getAsString(), str.substring(0, Math.min(200, str.length())));
    return false;
  }
  
  protected String getApiUrl(int paramInt)
  {
    return "https://imgur-apiv3.p.mashape.com/3/gallery/r/" + this.subredditId + "/time/" + paramInt;
  }
  
  public void postInitialize()
  {
    this.subredditId = ImgurSubredditFragment.matchIdFromUrl(getHostUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/imgur/ImgurSubredditProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */