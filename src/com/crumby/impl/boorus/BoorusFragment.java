package com.crumby.impl.boorus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.CrDb;
import com.crumby.impl.gelbooru.GelbooruProducer;
import com.crumby.lib.FormSearchHandler;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.SearchForm;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.universal.UniversalProducer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class BoorusFragment
  extends GalleryGridFragment
{
  public static final String API_ROOT_SUFFIX = "/index.php?page=dapi&s=post&q=index";
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837565;
  public static final String BREADCRUMB_NAME = "BOORU";
  public static final boolean DEFAULT_TO_HIDDEN = true;
  public static final FormSearchHandler FORM_SEARCH_HANDLER = new FormSearchHandler()
  {
    public int getIcon(FragmentIndex paramAnonymousFragmentIndex, String paramAnonymousString)
    {
      return 0;
    }
    
    public String getTitle(FragmentIndex paramAnonymousFragmentIndex, String paramAnonymousString)
    {
      return "BOORUS " + BoorusFragment.getSubdomain(paramAnonymousString).toUpperCase();
    }
    
    public String getUrlForSearch(String paramAnonymousString, SearchForm paramAnonymousSearchForm)
    {
      return BoorusFragment.getBaseUrl(paramAnonymousString) + "/index.php?page=post&s=list&" + paramAnonymousSearchForm.encodeFormData();
    }
  };
  public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + captureMinimumLength("[a-zA-Z0-9]", 4) + "\\." + Pattern.quote("booru.org");
  public static final String REGEX_URL = REGEX_BASE + ".*";
  public static final String ROOT_NAME = "booru.org";
  public static final String SAFE_API_ROOT_SUFFIX = "&tags=rating%3Asafe";
  public static final int SEARCH_FORM_ID = 2130903055;
  public static final boolean SUGGESTABLE = true;
  private boolean mustUseUniversal;
  
  public static String getBaseUrl(String paramString)
  {
    return "http://" + getSubdomain(paramString) + ".booru.org";
  }
  
  private static String getSubdomain(String paramString)
  {
    String str = matchIdFromUrl(REGEX_URL, paramString);
    paramString = str;
    if (str == null) {
      paramString = "invalid";
    }
    return paramString;
  }
  
  public View createFragmentView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return super.createFragmentView(paramLayoutInflater, paramViewGroup, paramBundle);
  }
  
  protected GalleryProducer createProducer()
  {
    String str = getBaseUrl(getUrl());
    if (!this.mustUseUniversal) {
      return new GelbooruProducer(str + "/", str + "/index.php?page=dapi&s=post&q=index", str + "/index.php?page=dapi&s=post&q=index" + "&tags=rating%3Asafe", null);
    }
    CrDb.d("boorus fragment", "spinning up universal producer");
    new UniversalProducer()
    {
      protected String getBaseUrl()
      {
        return BoorusFragment.getBaseUrl(BoorusFragment.this.getUrl());
      }
      
      protected String getRegexForMatchingId()
      {
        return null;
      }
      
      protected String getScriptName()
      {
        return BoorusFragment.class.getSimpleName();
      }
    };
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
    return getSubdomain(getUrl());
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
  }
  
  protected void setupHeaderLayout(ViewGroup paramViewGroup)
  {
    ((OmniformContainer)paramViewGroup).showAsInGrid(getImage().getLinkUrl());
  }
  
  public void showError(DisplayError paramDisplayError, String paramString1, String paramString2)
  {
    if ((paramDisplayError.equals(DisplayError.EMPTY_GALLERY)) && (!this.mustUseUniversal))
    {
      this.mustUseUniversal = true;
      this.producer = createProducer();
      this.adapter.initialize(this);
      this.adapter.startFetching();
      return;
    }
    super.showError(paramDisplayError, paramString1, paramString2);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/boorus/BoorusFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */