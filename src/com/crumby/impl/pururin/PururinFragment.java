package com.crumby.impl.pururin;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PururinFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "http://pururin.com";
  public static final int BREADCRUMB_ICON = 2130837680;
  public static final String BREADCRUMB_NAME = "pururin";
  public static final String DISPLAY_NAME = "Pururin";
  public static final String REGEX_BASE = buildBasicRegexBase("pururin.com");
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final String ROOT_NAME = "pururin.com";
  public static final int SEARCH_FORM_ID = 2130903094;
  
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    postUrlChangeToBusWithProducer(paramGalleryImage);
  }
  
  protected GalleryProducer createProducer()
  {
    new UniversalProducer()
    {
      private String tagId;
      
      protected String getBaseUrl()
      {
        return "http://pururin.com";
      }
      
      protected ArrayList<GalleryImage> getImagesFromJson(JsonNode paramAnonymousJsonNode)
        throws Exception
      {
        try
        {
          this.tagId = paramAnonymousJsonNode.get("tagID").asText();
          return super.getImagesFromJson(paramAnonymousJsonNode);
        }
        catch (NullPointerException localNullPointerException)
        {
          for (;;) {}
        }
      }
      
      public String getMatchingId()
      {
        return this.tagId;
      }
      
      protected String getRegexForMatchingId()
      {
        return null;
      }
      
      protected String getScriptName()
      {
        return PururinFragment.class.getSimpleName();
      }
      
      protected void onFinishedDownloading(ArrayList<GalleryImage> paramAnonymousArrayList, boolean paramAnonymousBoolean)
      {
        GalleryImage localGalleryImage1;
        GalleryImage localGalleryImage2;
        do
        {
          Iterator localIterator1 = getImages().iterator();
          Iterator localIterator2;
          while (!localIterator2.hasNext())
          {
            if (!localIterator1.hasNext()) {
              break;
            }
            localGalleryImage1 = (GalleryImage)localIterator1.next();
            localIterator2 = paramAnonymousArrayList.iterator();
          }
          localGalleryImage2 = (GalleryImage)localIterator2.next();
        } while ((localGalleryImage2.getLinkUrl() == null) || (!localGalleryImage2.getLinkUrl().equals(localGalleryImage1.getLinkUrl())));
        super.onFinishedDownloading(new ArrayList(), paramAnonymousBoolean);
        return;
        super.onFinishedDownloading(paramAnonymousArrayList, paramAnonymousBoolean);
      }
    };
  }
  
  protected int getHeaderLayout()
  {
    return 2130903105;
  }
  
  public String getSearchArgumentName()
  {
    return "q";
  }
  
  public String getSearchPrefix()
  {
    return "pururin";
  }
  
  public void setImage(GalleryImage paramGalleryImage)
  {
    super.setImage(paramGalleryImage);
    if (paramGalleryImage.getLinkUrl().contains("pururin.com?q=")) {
      paramGalleryImage.setLinkUrl(paramGalleryImage.getLinkUrl().replace("pururin.com?q=", "pururin.com/search?q="));
    }
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/pururin/PururinFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */