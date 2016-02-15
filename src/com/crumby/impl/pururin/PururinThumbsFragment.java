package com.crumby.impl.pururin;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;
import java.util.ArrayList;

public class PururinThumbsFragment
  extends GalleryGridFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837613;
  public static final String BREADCRUMB_NAME = "gallery";
  public static final Class BREADCRUMB_PARENT_CLASS = PururinFragment.class;
  public static final String REGEX_URL = "http://pururin.com/gallery/" + CAPTURE_NUMERIC_REPEATING + "/.*";
  
  protected GalleryProducer createProducer()
  {
    new UniversalProducer()
    {
      private boolean done;
      
      protected ArrayList<GalleryImage> fetchGalleryImages(int paramAnonymousInt)
        throws Exception
      {
        if (this.done)
        {
          onFinishedDownloading(new ArrayList(), false);
          return new ArrayList();
        }
        return super.fetchGalleryImages(paramAnonymousInt);
      }
      
      protected String getBaseUrl()
      {
        return "http://pururin.com";
      }
      
      protected String getRegexForMatchingId()
      {
        return PururinThumbsFragment.REGEX_URL;
      }
      
      protected String getScriptName()
      {
        return PururinThumbsFragment.class.getSimpleName();
      }
      
      protected void onFinishedDownloading(ArrayList<GalleryImage> paramAnonymousArrayList, boolean paramAnonymousBoolean)
      {
        if ((paramAnonymousArrayList != null) && (!paramAnonymousArrayList.isEmpty())) {
          this.done = true;
        }
        super.onFinishedDownloading(paramAnonymousArrayList, paramAnonymousBoolean);
      }
    };
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/pururin/PururinThumbsFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */