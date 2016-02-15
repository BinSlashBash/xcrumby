package com.crumby.impl.imgur;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.ImageComment;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.widget.ImageScrollView;
import com.crumby.lib.widget.firstparty.ImageCommentsView;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import com.crumby.lib.widget.thirdparty.ObservableScrollView;
import com.crumby.lib.widget.thirdparty.ScrollViewListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImgurImageFragment
  extends GalleryImageFragment
  implements ScrollViewListener
{
  public static final String ALBUM_SINGLE_IMAGE_REGEX;
  public static final String BASE_URL = "http://imgur.com/gallery/";
  public static final boolean BREADCRUMB_ALT_NAME = true;
  public static final int BREADCRUMB_ICON = 2130837634;
  public static final String BREADCRUMB_NAME = "i/";
  public static final Class BREADCRUMB_PARENT_CLASS = ImgurAlbumFragment.class;
  public static final String REGEX_URL;
  public static final String ROOT_NAME = "imgur.com/gallery/";
  public static final String SINGLE_IMAGE_REGEX = zeroOrOneTimes("/gallery") + "/" + captureMinimumLength("[a-zA-Z0-9]", 2);
  public static final String SUBREDDIT_SINGLE_IMAGE_REGEX = ImgurSubredditFragment.SUBREDDIT_REGEX + "/" + CAPTURE_ALPHANUMERIC_REPEATING;
  public static final String SUFFIX = "/gallery/";
  int comment;
  private ImageCommentsView imgurComments;
  private long last;
  private View loading;
  private int threshold;
  private View title;
  
  static
  {
    ALBUM_SINGLE_IMAGE_REGEX = ImgurAlbumFragment.ALBUM_REGEX + "/" + CAPTURE_ALPHANUMERIC_REPEATING;
    REGEX_URL = ImgurFragment.REGEX_BASE + matchOne(new String[] { SINGLE_IMAGE_REGEX, SUBREDDIT_SINGLE_IMAGE_REGEX, ALBUM_SINGLE_IMAGE_REGEX }) + "/?" + "\\??" + "[^/]*";
  }
  
  private void alterBreadcrumbs(List<Breadcrumb> paramList, String paramString)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Breadcrumb localBreadcrumb = (Breadcrumb)paramList.next();
      FragmentIndex localFragmentIndex = localBreadcrumb.getFragmentIndex();
      if ((localFragmentIndex.isIndexOf(ImgurAlbumFragment.class)) || (localFragmentIndex.isIndexOf(ImgurSubredditFragment.class))) {
        localBreadcrumb.alter(paramString);
      }
    }
  }
  
  private List<ImageComment> getComments()
  {
    return getImage().getComments();
  }
  
  private boolean isAlbumImage()
  {
    return matchIdFromUrl(ImgurAlbumFragment.REGEX_URL + "/.*", getUrl()) != null;
  }
  
  private boolean isSubredditImage()
  {
    return matchIdFromUrl(ImgurSubredditFragment.REGEX_URL + "/.*", getUrl()) != null;
  }
  
  protected GalleryProducer createProducer()
  {
    return new ImgurImageProducer();
  }
  
  protected void fillMetadataView()
  {
    this.threshold = getImageScrollView().getScrollY();
    getImageScrollView().setScrollViewListener(this);
    if (getComments() == null)
    {
      new ImgurCommentDownloadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[] { GalleryViewerFragment.matchIdFromUrl(REGEX_URL, getUrl()) });
      return;
    }
    this.imgurComments.initialize(getComments());
  }
  
  protected ViewGroup inflateMetadataLayout(LayoutInflater paramLayoutInflater)
  {
    this.imgurComments = ((ImageCommentsView)paramLayoutInflater.inflate(2130903088, null));
    return this.imgurComments;
  }
  
  public void onScrollChanged(ObservableScrollView paramObservableScrollView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramObservableScrollView.getChildAt(paramObservableScrollView.getChildCount() - 1).getBottom() - (paramObservableScrollView.getHeight() + paramObservableScrollView.getScrollY()) < 50) {
      this.imgurComments.addComments(false);
    }
  }
  
  public void setBreadcrumbs(BreadcrumbListModifier paramBreadcrumbListModifier)
  {
    List localList = paramBreadcrumbListModifier.getChildren();
    String str1 = getImage().getLinkUrl();
    String str2 = matchIdFromUrl(ImgurSubredditFragment.REGEX_URL + "/.*", str1);
    if (str2 != null) {
      alterBreadcrumbs(localList, "http://imgur.com/r/" + str2);
    }
    for (;;)
    {
      super.setBreadcrumbs(paramBreadcrumbListModifier);
      return;
      str1 = matchIdFromUrl(ImgurAlbumFragment.REGEX_URL + "/.*", str1);
      if (str1 != null) {
        alterBreadcrumbs(localList, "http://imgur.com/a/" + str1);
      }
    }
  }
  
  class ImgurCommentDownloadTask
    extends AsyncTask<String, Void, List<ImageComment>>
  {
    Exception fetchingException;
    
    ImgurCommentDownloadTask() {}
    
    private ArrayList<ImageComment> recurseComments(JsonArray paramJsonArray)
    {
      localArrayList = new ArrayList();
      try
      {
        paramJsonArray = paramJsonArray.iterator();
        while (paramJsonArray.hasNext())
        {
          JsonObject localJsonObject = ((JsonElement)paramJsonArray.next()).getAsJsonObject();
          localArrayList.add(new ImageComment(localJsonObject.get("author").getAsString(), localJsonObject.get("comment").getAsString(), recurseComments(localJsonObject.get("children").getAsJsonArray())));
        }
        return localArrayList;
      }
      catch (NullPointerException paramJsonArray) {}
    }
    
    protected List<ImageComment> doInBackground(String... paramVarArgs)
    {
      try
      {
        paramVarArgs = GalleryProducer.fetchUrl("https://imgur-apiv3.p.mashape.com/3/gallery/" + paramVarArgs[0] + "/comments/", "Client-ID ac562464e4b98f8", "F7x7cTikVsmshJJ4wubKUnkmJkIyp19IUJKjsnJegrKYFdu0OP");
        paramVarArgs = recurseComments(new JsonParser().parse(paramVarArgs).getAsJsonObject().get("data").getAsJsonArray());
        return paramVarArgs;
      }
      catch (Exception paramVarArgs)
      {
        this.fetchingException = paramVarArgs;
      }
      return null;
    }
    
    protected void onPostExecute(List<ImageComment> paramList)
    {
      if (this.fetchingException != null)
      {
        ImgurImageFragment.this.indicateMetadataError("Error loading comments", this.fetchingException);
        return;
      }
      ImgurImageFragment.this.getImage().setComments(paramList.subList(0, Math.min(paramList.size(), 5)));
      if (ImgurImageFragment.this.getComments().isEmpty())
      {
        ImgurImageFragment.this.indicateMetadataNotFound();
        return;
      }
      ImgurImageFragment.this.imgurComments.initialize(ImgurImageFragment.this.getComments());
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/imgur/ImgurImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */