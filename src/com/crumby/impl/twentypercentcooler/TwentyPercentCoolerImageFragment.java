package com.crumby.impl.twentypercentcooler;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.BooruImageProducer;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class TwentyPercentCoolerImageFragment
  extends BooruImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = TwentyPercentCoolerDFragment.class;
  public static final String REGEX_URL = TwentyPercentCoolerDFragment.REGEX_BASE + "/post/show/([0-9]+).*";
  public static final boolean SUGGESTABLE = true;
  
  protected GalleryProducer createProducer()
  {
    return new BooruImageProducer("http://twentypercentcooler.net", REGEX_URL);
  }
  
  protected String getTagUrl(String paramString)
  {
    return "http://twentypercentcooler.net/post/index.xml?&tags=" + Uri.encode(paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/twentypercentcooler/TwentyPercentCoolerImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */