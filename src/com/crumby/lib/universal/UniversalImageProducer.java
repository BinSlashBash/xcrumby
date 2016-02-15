package com.crumby.lib.universal;

import com.crumby.lib.GalleryImage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;

public abstract class UniversalImageProducer
  extends UniversalProducer
{
  protected ArrayList<GalleryImage> getImagesFromJson(JsonNode paramJsonNode)
    throws Exception
  {
    ((ArrayNode)paramJsonNode.get("images")).insert(0, paramJsonNode.get("hostImage"));
    return convertJsonNodeToGalleryImages(paramJsonNode.get("images"));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/universal/UniversalImageProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */