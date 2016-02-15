package com.crumby.impl.yandere;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.BooruImageProducer;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class YandereImageFragment
  extends BooruImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = YandereFragment.class;
  public static final String REGEX_URL = YandereFragment.REGEX_BASE + "/post/show/([0-9]+).*";
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    return new BooruImageProducer("https://yande.re", REGEX_URL);
  }
  
  protected String getTagUrl(String paramString)
  {
    return "https://yande.re/post/index.xml?&tags=" + Uri.encode(paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/yandere/YandereImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */