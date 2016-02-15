package com.crumby.impl.derpibooru;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.List;
import java.util.regex.Pattern;

public class DerpibooruFragment
  extends GalleryGridFragment
{
  public static final int ACCOUNT_LAYOUT = 2130903057;
  public static final String ACCOUNT_TYPE = "derpibooru";
  public static final String API_ROOT = "https://derpibooru.org/images";
  public static final String BASE_URL = "https://derpibooru.org";
  public static final int BREADCRUMB_ICON = 2130837567;
  public static final String BREADCRUMB_NAME = "derpibooru";
  public static final String DISPLAY_NAME = "Derpibooru";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + "(" + Pattern.quote("derpibooru.org") + "|" + Pattern.quote("derpiboo.ru") + ")";
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final String ROOT_NAME = "derpibooru.org";
  public static final int SEARCH_FORM_ID = 2130903058;
  public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(new IndexField[] { INCLUDE_IN_HOME, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL });
  public static final boolean SUGGESTABLE = true;
  
  public static void addSearchQuery(String paramString, List<Breadcrumb> paramList)
  {
    paramString = GalleryProducer.getQueryParameter(Uri.parse(paramString), paramString, "sbq");
    if (paramString != null)
    {
      paramString = paramString.replace(":", " ");
      ((Breadcrumb)paramList.get(0)).setBreadcrumbText("derpibooru: " + paramString);
    }
  }
  
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    super.applyGalleryImageChange(paramView, paramGalleryImage, paramInt);
  }
  
  protected GalleryProducer createProducer()
  {
    return new DerpibooruProducer();
  }
  
  protected int getHeaderLayout()
  {
    return 2130903105;
  }
  
  public String getSearchArgumentName()
  {
    return "sbq";
  }
  
  public String getSearchPrefix()
  {
    return "derpibooru";
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
  }
  
  public void setBreadcrumbs(List<Breadcrumb> paramList)
  {
    addSearchQuery(getUrl(), paramList);
    super.setBreadcrumbs(paramList);
  }
  
  public void setImage(GalleryImage paramGalleryImage)
  {
    super.setImage(paramGalleryImage);
    if (paramGalleryImage.getLinkUrl().contains("derpibooru.org?sbq=")) {
      paramGalleryImage.setLinkUrl(paramGalleryImage.getLinkUrl().replace("derpibooru.org?sbq=", "derpibooru.org/search?sbq="));
    }
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/DerpibooruFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */