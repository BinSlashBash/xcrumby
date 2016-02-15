package com.crumby.impl.gelbooru;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class GelbooruImageFragment
  extends BooruImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = GelbooruFragment.class;
  public static final String DISPLAY_NAME = "Gelbooru";
  public static final String REGEX_URL = GelbooruFragment.REGEX_BASE + "/index\\.php.*&id=([0-9]+)";
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    return new GelbooruImageProducer("http://gelbooru.com/", REGEX_URL, "http://gelbooru.com/index.php?page=dapi&s=post&q=index");
  }
  
  protected String getTagUrl(String paramString)
  {
    return "http://gelbooru.com/index.php?page=post&s=list&tags=" + Uri.encode(paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/gelbooru/GelbooruImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */