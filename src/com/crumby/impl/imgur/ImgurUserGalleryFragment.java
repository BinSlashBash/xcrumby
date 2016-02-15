package com.crumby.impl.imgur;

import android.view.View;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.util.regex.Pattern;

public class ImgurUserGalleryFragment
  extends GalleryGridFragment
{
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837632;
  public static final Class BREADCRUMB_PARENT_CLASS = ImgurFragment.class;
  public static final String REGEX_URL;
  public static final String REGEX_URL_1 = captureMinimumLength("[a-zA-Z0-9]", 4) + "\\." + Pattern.quote("imgur.com") + "(?:/.*)?";
  public static final String REGEX_URL_2 = Pattern.quote("imgur.com") + "/user/" + CAPTURE_ALPHANUMERIC_REPEATING + "/?";
  
  static
  {
    REGEX_URL = "(?:http://www.|https://www.|https://|http://|www.)?" + matchOne(new String[] { REGEX_URL_1, REGEX_URL_2 });
  }
  
  public static String matchIdFromUrl(String paramString)
  {
    return matchIdFromUrl(REGEX_URL, paramString);
  }
  
  public void applyGalleryImageChange(View paramView, GalleryImage paramGalleryImage, int paramInt)
  {
    postUrlChangeToBusWithProducer(paramGalleryImage);
  }
  
  protected GalleryProducer createProducer()
  {
    return new ImgurUserGalleryProducer();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/imgur/ImgurUserGalleryFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */