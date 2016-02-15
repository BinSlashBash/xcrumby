package com.crumby.impl.danbooru;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class DanbooruImageProducer
  extends DanbooruGalleryProducer
{
  private boolean done;
  
  private ArrayList<GalleryImage> fetchSingularImage()
    throws IOException
  {
    ArrayList localArrayList = new ArrayList();
    if (this.done) {
      return localArrayList;
    }
    this.done = true;
    localArrayList.add(getGalleryImage(JSON_PARSER.parse(new JsonReader(new StringReader(fetchUrl(getHostUrl() + ".json")))).getAsJsonObject()));
    return localArrayList;
  }
  
  protected ArrayList<GalleryImage> fetchGalleryImages(int paramInt)
    throws Exception
  {
    return fetchSingularImage();
  }
  
  protected boolean fetchMetadata()
    throws IOException
  {
    return false;
  }
  
  protected GalleryImage getGalleryImage(JsonObject paramJsonObject)
  {
    GalleryImage localGalleryImage = super.getGalleryImage(paramJsonObject);
    localGalleryImage.setLinkUrl(null);
    localGalleryImage.setImageUrl(deriveUrl(paramJsonObject.get("file_url"), ""));
    localGalleryImage.setId(paramJsonObject.get("id").getAsString());
    return localGalleryImage;
  }
  
  public String getHostUrl()
  {
    String str = GalleryViewerFragment.matchIdFromUrl(DanbooruImageFragment.REGEX_URL, super.getHostUrl());
    return "http://danbooru.donmai.us/posts/" + str;
  }
  
  public void postIntialize()
  {
    this.apiUrl = convertUrlToApi(getHostUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/danbooru/DanbooruImageProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */