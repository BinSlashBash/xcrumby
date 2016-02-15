package com.crumby.impl.rule34xxx;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.gelbooru.GelbooruImageProducer;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class Rule34xxxImageFragment
  extends BooruImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = Rule34xxxFragment.class;
  public static final String REGEX_URL = Rule34xxxFragment.REGEX_BASE + "/index\\.php.*&id=([0-9]+)";
  
  protected GalleryProducer createProducer()
  {
    return new GelbooruImageProducer("https://rule34.xxx", REGEX_URL, "https://rule34.xxx//index.php?page=dapi&s=post&q=index");
  }
  
  protected String getTagUrl(String paramString)
  {
    return "https://rule34.xxx/index.php?page=post&s=list&tags=" + Uri.encode(paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/rule34xxx/Rule34xxxImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */