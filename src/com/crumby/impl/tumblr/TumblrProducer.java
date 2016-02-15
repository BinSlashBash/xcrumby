package com.crumby.impl.tumblr;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.universal.UniversalProducer;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;

class TumblrProducer
  extends UniversalProducer
{
  String timestamp = "null";
  
  protected String getBaseUrl()
  {
    return "http://api.tumblr.com";
  }
  
  protected String getExtraScript()
  {
    return "var tumblrTimeStamp = " + this.timestamp + ";";
  }
  
  public String getHostUrl()
  {
    return TumblrFragment.getDisplayUrl(super.getHostUrl());
  }
  
  protected ArrayList<GalleryImage> getImagesFromJson(JsonNode paramJsonNode)
    throws Exception
  {
    try
    {
      this.timestamp = paramJsonNode.get("tumblrTimeStamp").asText();
      return super.getImagesFromJson(paramJsonNode);
    }
    catch (NullPointerException paramJsonNode) {}
    return new ArrayList();
  }
  
  protected String getRegexForMatchingId()
  {
    return TumblrFragment.REGEX_URL;
  }
  
  protected String getScriptName()
  {
    return TumblrFragment.class.getSimpleName();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/tumblr/TumblrProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */