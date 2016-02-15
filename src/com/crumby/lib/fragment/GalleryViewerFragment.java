package com.crumby.lib.fragment;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Adapter;
import com.crumby.BusProvider;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.GalleryImageClickChange;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import com.squareup.otto.Bus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class GalleryViewerFragment
  extends Fragment
  implements GalleryImageClickChange
{
  protected static final String ALPHANUMERIC = "[a-zA-Z0-9]";
  protected static final String ALPHANUMERIC_REPEATING;
  protected static final String ALPHANUMERIC_WITH_DASH = "[a-zA-Z0-9_\\+\\-]";
  protected static final String ALPHANUMERIC_WITH_EXTRAS = "[a-zA-Z0-9_\\+]";
  protected static final String ALPHANUMERIC_WITH_EXTRAS_REPEATING;
  protected static final String CAPTURE_ALPHANUMERIC_REPEATING;
  protected static final String CAPTURE_ALPHANUMERIC_WITH_EXTRAS_REPEATING;
  protected static final String CAPTURE_NUMERIC_REPEATING;
  protected static final String GENERIC_REGEX_URL_PREFIX = "(?:http://www.|https://www.|https://|http://|www.)?";
  public static final String GENERIC_URL_PREFIX = "http://www.|https://www.|https://|http://|www.";
  protected static final String IGNORE_EVERYTHING_AFTER = "(?:/.*)?";
  protected static final IndexField INCLUDE_IN_HOME;
  protected static final IndexField INCLUDE_IN_HOME_FALSE;
  public static final String INCLUDE_IN_HOME_KEY = "include_in_home";
  protected static final String MUST_HAVE_QUERY_GIBBERISH_OR_NOTHING = "(?:\\?.*)?";
  protected static final String NUMERIC = "[0-9]";
  protected static final String NUMERIC_REPEATING;
  protected static final String OPTIONAL_QUERY_MARK = "\\??";
  protected static final String OPTIONAL_SLASH = "/?";
  private static final float PROGRESS_INIT = 0.025F;
  protected static final IndexField SAFE_IN_TOP_LEVEL;
  public static final String SAFE_IN_TOP_LEVEL_KEY = "safe_in_top_level";
  protected static final IndexField SHOW_IN_SEARCH;
  protected static final IndexField SHOW_IN_SEARCH_FALSE;
  public static final String SHOW_IN_SEARCH_KEY = "show_in_search";
  protected Adapter adapter;
  private List<Breadcrumb> breadcrumbs;
  private boolean doNotResume;
  private GalleryImage image;
  private float lastProgress;
  protected GalleryProducer producer;
  
  static
  {
    if (!GalleryViewerFragment.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      ALPHANUMERIC_REPEATING = rRepeat("[a-zA-Z0-9]");
      ALPHANUMERIC_WITH_EXTRAS_REPEATING = rRepeat("[a-zA-Z0-9_\\+]");
      CAPTURE_ALPHANUMERIC_REPEATING = rCapture(ALPHANUMERIC_REPEATING);
      CAPTURE_ALPHANUMERIC_WITH_EXTRAS_REPEATING = rCapture(ALPHANUMERIC_WITH_EXTRAS_REPEATING);
      NUMERIC_REPEATING = rRepeat("[0-9]");
      CAPTURE_NUMERIC_REPEATING = rCapture(NUMERIC_REPEATING);
      SAFE_IN_TOP_LEVEL = new IndexField("safe_in_top_level", "Attempt to show only 'Work-safe' images in the top-level gallery.", Boolean.valueOf(true));
      SHOW_IN_SEARCH = new IndexField("show_in_search", "Show in search results", Boolean.valueOf(true));
      INCLUDE_IN_HOME = new IndexField("include_in_home", "Include on Home Page", Boolean.valueOf(true));
      INCLUDE_IN_HOME_FALSE = INCLUDE_IN_HOME.differentDefaultValue(Boolean.valueOf(false));
      SHOW_IN_SEARCH_FALSE = SHOW_IN_SEARCH.differentDefaultValue(Boolean.valueOf(false));
      return;
    }
  }
  
  protected static final String buildBasicRegexBase(String paramString)
  {
    return "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote(paramString);
  }
  
  protected static final String captureMinimumLength(String paramString, int paramInt)
  {
    return "(" + paramString + "{" + paramInt + ",})";
  }
  
  public static String matchIdFromUrl(String paramString1, String paramString2)
  {
    Matcher localMatcher = Pattern.compile(paramString1).matcher(paramString2);
    CrDb.d("GalleryViewerFragment", paramString2 + " " + paramString1);
    if (localMatcher.find())
    {
      int i = localMatcher.groupCount();
      while (i >= 0)
      {
        if (localMatcher.group(i) != null) {
          return localMatcher.group(i);
        }
        i -= 1;
      }
    }
    return null;
  }
  
  protected static final String matchOne(String... paramVarArgs)
  {
    String str1 = "(";
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      String str3 = paramVarArgs[i];
      String str2 = str1;
      if (str3 != paramVarArgs[0]) {
        str2 = str1 + "|";
      }
      str1 = str2 + "(" + str3 + ")";
      i += 1;
    }
    return str1 + ")";
  }
  
  private static final String rCapture(String paramString)
  {
    return "(" + paramString + ")";
  }
  
  private static final String rRepeat(String paramString)
  {
    return paramString + "+";
  }
  
  public static String stripUrlPrefix(String paramString)
  {
    Object localObject = Pattern.compile("http://www.|https://www.|https://|http://|www.").matcher(paramString);
    String str = paramString;
    if (((Matcher)localObject).find())
    {
      localObject = ((Matcher)localObject).group(0);
      str = paramString;
      if (paramString.indexOf((String)localObject) == 0) {
        str = paramString.substring(((String)localObject).length());
      }
    }
    return str;
  }
  
  protected static final String zeroOrOneTimes(String paramString)
  {
    return "(" + paramString + ")?";
  }
  
  protected boolean checkIfAllowedToResume()
  {
    boolean bool = false;
    if (this.doNotResume)
    {
      this.doNotResume = false;
      bool = true;
    }
    return bool;
  }
  
  protected void cleanLinkUrl()
  {
    String str = "http://" + stripUrlPrefix(getImage().getLinkUrl());
    getImage().setLinkUrl(str);
  }
  
  public abstract View createFragmentView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle);
  
  protected abstract GalleryProducer createProducer();
  
  public abstract void deferSetDescription(String paramString);
  
  public void destroy()
  {
    this.image.clearViews();
    detachFromProducer();
    if (this.breadcrumbs != null) {
      this.breadcrumbs.clear();
    }
  }
  
  public void detachFromProducer()
  {
    if (this.producer == null) {
      return;
    }
    this.producer.removeConsumer(getConsumer());
    this.producer = null;
  }
  
  public List<Breadcrumb> getBreadcrumbs()
  {
    return this.breadcrumbs;
  }
  
  public abstract GalleryConsumer getConsumer();
  
  public String getDisplayUrl()
  {
    return getUrl();
  }
  
  public GalleryImage getImage()
  {
    assert (this.image != null);
    return this.image;
  }
  
  public float getLastProgress()
  {
    return this.lastProgress;
  }
  
  public GalleryProducer getProducer()
  {
    if (this.producer == null) {
      this.producer = createProducer();
    }
    return this.producer;
  }
  
  public GalleryProducer getProducerIfItExists()
  {
    return this.producer;
  }
  
  protected int getScreenWidth()
  {
    return getResources().getDisplayMetrics().widthPixels;
  }
  
  public String getUrl()
  {
    return this.image.getLinkUrl();
  }
  
  protected GalleryViewer getViewer()
  {
    return (GalleryViewer)getActivity();
  }
  
  public abstract void hideClutter();
  
  public void indicateLastProgressChange()
  {
    setUserVisibleHint(true);
    indicateProgressChange(this.lastProgress);
  }
  
  public void indicateProgressChange(float paramFloat)
  {
    if (getActivity() == null) {}
    do
    {
      return;
      this.lastProgress = paramFloat;
    } while (!getUserVisibleHint());
    ((GalleryViewer)getActivity()).progressChange(paramFloat);
  }
  
  protected void murderWebview(WebView paramWebView)
  {
    if (paramWebView != null)
    {
      if (paramWebView.getParent() != null) {
        ((ViewGroup)paramWebView.getParent()).removeView(paramWebView);
      }
      paramWebView.destroy();
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (this.image == null) {
      this.image = new GalleryImage(getArguments().getString("url"));
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if (getView() == null) {
      return createFragmentView(paramLayoutInflater, paramViewGroup, paramBundle);
    }
    return getView();
  }
  
  public void onDestroy()
  {
    destroy();
    super.onDestroy();
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
  }
  
  protected void overrideBreadcrumbs(List<Breadcrumb> paramList)
  {
    this.breadcrumbs = paramList;
    ((GalleryViewer)getActivity()).overrideBreadcrumbs(paramList);
  }
  
  protected void postUrlChangeToBus(String paramString, Bundle paramBundle)
  {
    BusProvider.BUS.get().post(new UrlChangeEvent(paramString, paramBundle));
  }
  
  protected void postUrlChangeToBusWithProducer(GalleryImage paramGalleryImage)
  {
    this.producer.shareAndSetCurrentImageFocus(paramGalleryImage.getPosition());
    BusProvider.BUS.get().post(new UrlChangeEvent(paramGalleryImage.getLinkUrl(), this.producer));
  }
  
  public abstract void prepareForRefresh();
  
  public void redraw()
  {
    if (getView() != null) {
      getView().invalidate();
    }
  }
  
  public abstract void resume();
  
  public void setBreadcrumbs(BreadcrumbListModifier paramBreadcrumbListModifier)
  {
    assert (this.breadcrumbs == null);
    setBreadcrumbs(paramBreadcrumbListModifier.getChildren());
  }
  
  public void setBreadcrumbs(List<Breadcrumb> paramList)
  {
    if (paramList == null) {
      return;
    }
    this.breadcrumbs = paramList;
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Breadcrumb localBreadcrumb = (Breadcrumb)paramList.next();
      if (!localBreadcrumb.getText().toString().equals("")) {
        localArrayList.add(localBreadcrumb.getText().toString());
      }
    }
    if ((this instanceof GalleryImageFragment)) {
      localArrayList.remove(localArrayList.size() - 1);
    }
    this.image.modifyPath(localArrayList);
  }
  
  public void setImage(GalleryImage paramGalleryImage)
  {
    if (this.image == null)
    {
      this.image = paramGalleryImage;
      cleanLinkUrl();
    }
    for (;;)
    {
      paramGalleryImage.newPath();
      return;
      CrDb.d("viewer fragment", "copying gallery image " + paramGalleryImage.getImageUrl());
      this.image.copy(paramGalleryImage);
    }
  }
  
  public void setProducer(GalleryProducer paramGalleryProducer)
  {
    this.producer = paramGalleryProducer;
  }
  
  public abstract void showClutter();
  
  public abstract void stopLoading();
  
  public abstract boolean undo();
  
  public void waitOnResume()
  {
    this.doNotResume = true;
  }
  
  public abstract boolean willAllowPaging(MotionEvent paramMotionEvent);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/GalleryViewerFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */