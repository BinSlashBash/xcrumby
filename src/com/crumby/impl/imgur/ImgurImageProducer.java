package com.crumby.impl.imgur;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;

public class ImgurImageProducer
  extends ImgurProducer
{
  private boolean fetched;
  String imageId;
  
  private boolean imageIdIsAlbum()
  {
    boolean bool = false;
    try
    {
      String str = fetchUrl("https://imgur-apiv3.p.mashape.com/3/album/" + this.imageId, "Client-ID ac562464e4b98f8", "F7x7cTikVsmshJJ4wubKUnkmJkIyp19IUJKjsnJegrKYFdu0OP");
      int i = new ObjectMapper().readTree(str).get("status").asInt();
      if (i != 404) {
        bool = true;
      }
      return bool;
    }
    catch (Exception localException) {}
    return false;
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList1 = localArrayList2;
    if (!this.fetched)
    {
      this.fetched = true;
      if (imageIdIsAlbum())
      {
        setSilentRedirectUrl("http://imgur.com/a/" + this.imageId);
        localArrayList1 = null;
      }
    }
    else
    {
      return localArrayList1;
    }
    localArrayList2.add(parseImgurImage(JSON_PARSER.parse(fetchImgurUrl(paramInt)).getAsJsonObject().get("data")));
    return localArrayList2;
  }
  
  protected String getApiUrl(int paramInt)
  {
    return "https://imgur-apiv3.p.mashape.com/3/image/" + this.imageId;
  }
  
  protected GalleryImage parseImgurImage(JsonElement paramJsonElement)
  {
    GalleryImage localGalleryImage = super.parseImgurImage(paramJsonElement);
    paramJsonElement.getAsJsonObject().get("link").getAsString();
    return localGalleryImage;
  }
  
  public void postInitialize()
  {
    this.imageId = GalleryViewerFragment.matchIdFromUrl(ImgurImageFragment.REGEX_URL, getHostUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/imgur/ImgurImageProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */