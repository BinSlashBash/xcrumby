package com.crumby.impl.boorus;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.gelbooru.GelbooruImageProducer;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;

public class BoorusImageFragment
  extends BooruImageFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final Class BREADCRUMB_PARENT_CLASS = BoorusFragment.class;
  public static final String DISPLAY_NAME = "Gelbooru";
  public static final String REGEX_URL = BoorusFragment.REGEX_BASE + "/index\\.php.*&id=([0-9]+)";
  public static final boolean SUGGESTABLE = true;
  private boolean mustUseUniversal;
  
  protected GalleryProducer createProducer()
  {
    final String str = BoorusFragment.getBaseUrl(getUrl());
    if (!this.mustUseUniversal) {
      return new GelbooruImageProducer(str + "/", REGEX_URL, str + "/index.php?page=dapi&s=post&q=index");
    }
    new UniversalImageProducer()
    {
      protected String getBaseUrl()
      {
        return str;
      }
      
      protected String getRegexForMatchingId()
      {
        return GalleryViewerFragment.matchIdFromUrl(BoorusImageFragment.REGEX_URL, BoorusImageFragment.this.getUrl());
      }
      
      protected String getScriptName()
      {
        return BoorusImageFragment.class.getSimpleName();
      }
    };
  }
  
  protected String getTagUrl(String paramString)
  {
    return BoorusFragment.getBaseUrl(getUrl()) + "/index.php?page=post&s=list&tags=" + Uri.encode(paramString);
  }
  
  public void showError(Exception paramException)
  {
    if (!this.mustUseUniversal)
    {
      this.mustUseUniversal = true;
      this.reloading = true;
      this.producer = createProducer();
      this.producer.initialize(this, getImage(), null, true);
      return;
    }
    super.showError(paramException);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/boorus/BoorusImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */