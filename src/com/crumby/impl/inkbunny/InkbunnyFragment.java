package com.crumby.impl.inkbunny;

import android.view.View;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import java.util.regex.Pattern;

public class InkbunnyFragment
  extends GalleryGridFragment
{
  public static final String API_ROOT = "https://inkbunny.net/api_";
  public static final String BASE_URL = "https://inkbunny.net";
  public static final int BREADCRUMB_ICON = 2130837659;
  public static final String BREADCRUMB_NAME = "inkbunny";
  public static final String DISPLAY_NAME = "Inkbunny";
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote("inkbunny.net") + ")";
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final String ROOT_NAME = "inkbunny.net";
  public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(new IndexField[] { INCLUDE_IN_HOME_FALSE });
  public static final boolean SUGGESTABLE = true;
  
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    super.applyGalleryImageChange(paramView, paramGalleryImage, paramInt);
  }
  
  protected GalleryProducer createProducer()
  {
    return new InkbunnyProducer();
  }
  
  public String getSearchArgumentName()
  {
    return "keywords";
  }
  
  public String getSearchPrefix()
  {
    return "inkbunny";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/inkbunny/InkbunnyFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */