package com.crumby.impl.danbooru;

import android.view.View;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class DanbooruPoolGalleryFragment
  extends GalleryGridFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837613;
  public static final String BREADCRUMB_NAME = "pool";
  public static final Class BREADCRUMB_PARENT_CLASS = DanbooruPoolFragment.class;
  public static final String REGEX_URL = DanbooruPoolFragment.REGEX_BASE + "/" + CAPTURE_NUMERIC_REPEATING + "/?";
  
  public static String matchIdFromUrl(String paramString)
  {
    return matchIdFromUrl(REGEX_URL, paramString);
  }
  
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    postUrlChangeToBusWithProducer(paramGalleryImage);
  }
  
  protected GalleryProducer createProducer()
  {
    return new DanbooruPoolGalleryProducer();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/danbooru/DanbooruPoolGalleryFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */