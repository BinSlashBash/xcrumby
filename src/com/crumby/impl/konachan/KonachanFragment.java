package com.crumby.impl.konachan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.BooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class KonachanFragment
  extends GalleryGridFragment
{
  public static final String API_ROOT = "http://konachan.com/post.json?";
  public static final String BASE_URL = "http://konachan.com";
  public static final int BREADCRUMB_ICON = 2130837660;
  public static final String BREADCRUMB_NAME = "konachan";
  public static final String DISPLAY_NAME = "konachan";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + "(" + Pattern.quote("konachan.com") + "|" + Pattern.quote("konachan.net") + ")";
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final String ROOT_NAME = "konachan.com";
  public static final int SEARCH_FORM_ID = 2130903055;
  public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(new IndexField[] { INCLUDE_IN_HOME, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL });
  public static final boolean SUGGESTABLE = true;
  
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    super.applyGalleryImageChange(paramView, paramGalleryImage, paramInt);
  }
  
  protected GalleryProducer createProducer()
  {
    return new BooruProducer("http://konachan.com", KonachanFragment.class);
  }
  
  protected int getHeaderLayout()
  {
    return 2130903105;
  }
  
  public String getSearchArgumentName()
  {
    return "tags";
  }
  
  public String getSearchPrefix()
  {
    return "konachan";
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/konachan/KonachanFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */