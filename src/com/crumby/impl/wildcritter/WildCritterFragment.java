package com.crumby.impl.wildcritter;

import android.net.Uri;
import android.view.ViewGroup;
import com.crumby.GalleryViewer;
import com.crumby.impl.sankakuchan.SankakuChanFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.universal.UniversalProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class WildCritterFragment
  extends GalleryGridFragment
{
  public static final String BASE_URL = "http://wildcritters.ws";
  public static final int BREADCRUMB_ICON = 2130837565;
  public static final String BREADCRUMB_NAME = "ws";
  public static final String DISPLAY_NAME = "ws";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote("wildcritters.ws") + ")";
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final String ROOT_NAME = "wildcritters.ws";
  public static final int SEARCH_FORM_ID = 2130903055;
  
  protected GalleryProducer createProducer()
  {
    new UniversalProducer()
    {
      protected String getBaseUrl()
      {
        return "http://wildcritters.ws";
      }
      
      public String getHostUrl()
      {
        Object localObject2 = getQueryParameter(Uri.parse(super.getHostUrl()), super.getHostUrl(), "tags");
        String str = getBaseUrl() + "?tags=";
        Object localObject1 = localObject2;
        if (localObject2 == null)
        {
          localObject1 = localObject2;
          if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(WildCritterFragment.class)) {
            localObject1 = "rating:safe";
          }
        }
        localObject2 = str;
        if (localObject1 != null)
        {
          localObject2 = str;
          if (!((String)localObject1).equals("")) {
            localObject2 = str + Uri.encode((String)localObject1);
          }
        }
        return (String)localObject2 + Uri.encode(GalleryViewer.getBlacklist());
      }
      
      protected String getRegexForMatchingId()
      {
        return null;
      }
      
      protected String getScriptName()
      {
        return SankakuChanFragment.class.getSimpleName();
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
    return "ws";
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/wildcritter/WildCritterFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */