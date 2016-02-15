package com.crumby.impl.danbooru;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.crumby.impl.BooruImageFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import com.crumby.lib.widget.firstparty.omnibar.UrlCrumb;

public class DanbooruImageFragment
  extends BooruImageFragment
{
  public static final String BASE_URL = "http://danbooru.donmai.us/posts/";
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final String BREADCRUMB_NAME = "s";
  public static final Class BREADCRUMB_PARENT_CLASS = DanbooruGalleryFragment.class;
  public static final String MATCH_POOL_ID;
  public static final String REGEX_BASE = DanbooruGalleryFragment.REGEX_BASE + "/posts/" + CAPTURE_NUMERIC_REPEATING + "/?";
  public static final String REGEX_URL = REGEX_BASE + "\\??" + ".*";
  private ViewGroup artistTags;
  private ViewGroup characterTags;
  private ViewGroup copyrightTags;
  
  static
  {
    MATCH_POOL_ID = REGEX_BASE + "\\?.*" + "pool_id=([0-9]+)" + ".*";
  }
  
  protected void addTags()
    throws ClassCastException
  {
    super.addTags();
    addTags(getView().findViewById(2131492931), this.characterTags, ((DanbooruAttributes)getImage().attr()).getCharacterTags());
    addTags(getView().findViewById(2131492929), this.artistTags, ((DanbooruAttributes)getImage().attr()).getArtistTags());
    addTags(getView().findViewById(2131492927), this.copyrightTags, ((DanbooruAttributes)getImage().attr()).getCopyrightTags());
  }
  
  protected GalleryProducer createProducer()
  {
    return new DanbooruImageProducer();
  }
  
  protected int getBooruLayout()
  {
    return 2130903052;
  }
  
  protected String getTagUrl(String paramString)
  {
    return "http://danbooru.donmai.us?tags=" + Uri.encode(paramString);
  }
  
  protected ViewGroup inflateMetadataLayout(LayoutInflater paramLayoutInflater)
  {
    paramLayoutInflater = super.inflateMetadataLayout(paramLayoutInflater);
    this.characterTags = ((ViewGroup)paramLayoutInflater.findViewById(2131492932));
    this.artistTags = ((ViewGroup)paramLayoutInflater.findViewById(2131492930));
    this.copyrightTags = ((ViewGroup)paramLayoutInflater.findViewById(2131492928));
    ((TextView)paramLayoutInflater.findViewById(2131492905)).setText("General Tags");
    return paramLayoutInflater;
  }
  
  public void setBreadcrumbs(BreadcrumbListModifier paramBreadcrumbListModifier)
  {
    if (getUrl().matches(MATCH_POOL_ID))
    {
      String str = matchIdFromUrl(MATCH_POOL_ID, getUrl());
      paramBreadcrumbListModifier.addNew(new UrlCrumb[] { new UrlCrumb(1, "http://danbooru.donmai.us/pools/"), new UrlCrumb(2, "http://danbooru.donmai.us/pools/" + str) });
    }
    super.setBreadcrumbs(paramBreadcrumbListModifier);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/danbooru/DanbooruImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */