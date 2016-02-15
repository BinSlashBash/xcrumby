package com.crumby.lib.fragment;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.CropTransformation;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.GalleryImageView;
import com.crumby.lib.widget.ImageScrollView;
import com.crumby.lib.widget.OnImageScaleListener;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.ErrorView;
import com.crumby.lib.widget.firstparty.GalleryImageTutorial;
import com.crumby.lib.widget.firstparty.fragment_options.OverflowImageButton;
import com.crumby.lib.widget.firstparty.fragment_options.SaveImageButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import it.sephiroth.android.library.tooltip.TooltipManager;
import it.sephiroth.android.library.tooltip.TooltipManager.Builder;
import it.sephiroth.android.library.tooltip.TooltipManager.ClosePolicy;
import it.sephiroth.android.library.tooltip.TooltipManager.Gravity;
import java.util.List;
import uk.co.senab.photoview.PhotoView;

public class GalleryImageFragment
  extends GalleryViewerFragment
  implements GalleryImageTutorial, GalleryConsumer, GalleryImageView, View.OnTouchListener
{
  public static final int BREADCRUMB_ICON = 2130837634;
  private static final int TAP_TO_ZOOM_TOOLTIP = 3;
  private static boolean shownImageTutorial;
  private boolean alreadyRenderedImage;
  private boolean alreadyRenderedMetadata;
  private TextView description;
  private ErrorView error;
  private View fragmentOptions;
  private OnImageScaleListener imageScaleListener;
  private ImageScrollView imageScrollView;
  private PhotoView imageView;
  private TextView metadataError;
  private boolean metadataIsFilled;
  private ViewGroup metadataView;
  private boolean neverLoadAgain;
  private OverflowImageButton overflow;
  private View progress;
  protected boolean reloading;
  private boolean renderedThumbnail;
  private SaveImageButton save;
  private ImageView tempImageView;
  private TextView title;
  private WebView webView;
  
  public static final String getWebViewImgHtml(String paramString)
  {
    return "<html style='height:100%;\n   width: 100%;\n   margin: 0;\n   padding: 0;\n   border: 0;'>\n<body style='height:100%;\n   width: 100%;\n   margin: 0;\n   padding: 0;\n   border: 0;'>\n   <table style='height:100%;\n   width: 100%;\n   margin: 0;\n   padding: 0;\n   border: 0;'>\n      <tr>\n         <td style='vertical-align: middle;\n   text-align: center;'><img style='max-width:100%;' src='" + paramString + "' alt=\"\" /></td>\n" + "      </tr>\n" + "   </table>\n" + "</body>\n" + "</html>";
  }
  
  private void hideLoading()
  {
    this.tempImageView.setVisibility(8);
    this.progress.clearAnimation();
    this.progress.setVisibility(8);
  }
  
  private void indicateImageRenderError(String paramString)
  {
    hideLoading();
    indicateProgressChange(1.0F);
    this.imageView.setVisibility(8);
    if (!this.error.shownError()) {
      this.error.show(DisplayError.IMAGE_NOT_RENDERING, paramString, getDisplayUrl());
    }
  }
  
  protected static void loadWebViewHtml(String paramString, WebView paramWebView)
  {
    paramWebView.loadData("<html><head><style type=\"text/css\">body{color: #e0e0e0; font-size: 10pt}</style></head><body>" + paramString + "</body></html>", "text/html", "utf-8");
  }
  
  private void render()
  {
    GalleryImage localGalleryImage = getImage();
    if ((localGalleryImage == null) || (this.save == null) || (getActivity() == null) || (this.reloading)) {}
    do
    {
      do
      {
        return;
        showAndSet(this.title, localGalleryImage.getTitle());
        this.imageScrollView.render(getImage().getHeight());
        renderMetadata();
      } while (this.alreadyRenderedImage);
      renderThumbnail();
    } while (!getUserVisibleHint());
    this.save.initialize(localGalleryImage);
    this.overflow.initialize(localGalleryImage);
    renderImage();
  }
  
  private void renderImage()
  {
    if (this.neverLoadAgain) {
      return;
    }
    this.alreadyRenderedImage = true;
    if (getImage().hasError())
    {
      getImage().setReload(true);
      indicateImageRenderError(getImage().printError());
      return;
    }
    if (getImage().isAnimated())
    {
      hideLoading();
      this.webView.setBackgroundColor(0);
      this.webView.setLayerType(1, null);
      this.webView.getSettings().setBuiltInZoomControls(true);
      this.webView.getSettings().setDisplayZoomControls(false);
      this.webView.getSettings().setSupportZoom(true);
      this.webView.setVisibility(0);
      localObject = getWebViewImgHtml(getImage().getImageUrl());
      this.webView.loadDataWithBaseURL(null, (String)localObject, "text/html", "UTF-8", null);
      indicateProgressChange(1.0F);
      return;
    }
    if (getImage().getImageUrl() == null)
    {
      indicateImageRenderError("Invalid/unparseable image url.");
      return;
    }
    if (getImage().getImageUrl().endsWith(".swf"))
    {
      if (getActivity() != null) {}
      indicateImageRenderError("SWF/Flash files are not supported in Android :-(");
      return;
    }
    CrDb.d("image fragment", "will render image");
    int i = getScreenWidth();
    int j = getResources().getDisplayMetrics().heightPixels;
    Object localObject = Picasso.with(getActivity()).load(getImage().getImageUrl());
    int k = getImage().getWidth();
    int m = getImage().getHeight();
    if ((i < k) || (j < m)) {
      ((RequestCreator)localObject).resize(i, j).centerInside();
    }
    this.progress.animate().setStartDelay(1000L).alpha(1.0F);
    if (!shownImageTutorial) {
      TooltipManager.getInstance(getActivity()).remove(3);
    }
    ((RequestCreator)localObject).noFade().into(this.imageView, new ImageCallback(null));
  }
  
  private void renderMetadata()
  {
    if ((this.alreadyRenderedMetadata) || (this.metadataView == null) || (!getUserVisibleHint()) || (ImageScrollView.userWantsFullScreen) || (getImageScrollView().isInFullScreen())) {
      return;
    }
    this.alreadyRenderedMetadata = true;
    fillMetadataView();
  }
  
  private void renderThumbnail()
  {
    if ((this.renderedThumbnail) || (getImage() == null) || (getActivity() == null)) {}
    while ((getImage().isAnimated()) || (getImage().getThumbnailUrl() == null)) {
      return;
    }
    RequestCreator localRequestCreator2 = Picasso.with(getActivity()).load(getImage().getThumbnailUrl());
    RequestCreator localRequestCreator1 = localRequestCreator2;
    if (getImage().isSplit()) {
      localRequestCreator1 = localRequestCreator2.transform(new CropTransformation(getImage(), getImage().getPosition()));
    }
    localRequestCreator1.into(this.tempImageView);
    this.renderedThumbnail = true;
  }
  
  private void showAndSet(TextView paramTextView, String paramString)
  {
    if ((paramString == null) || (paramString.equals("")) || (paramString.equals(paramTextView.getText()))) {
      return;
    }
    paramTextView.setVisibility(0);
    paramTextView.setText(paramString);
  }
  
  private void showLoginPrompt() {}
  
  public boolean addImages(List<GalleryImage> paramList)
  {
    this.reloading = false;
    if (paramList.size() == 0)
    {
      CrDb.d("image fragment", "no images!");
      return true;
    }
    setImage((GalleryImage)paramList.get(0));
    this.alreadyRenderedMetadata = false;
    render();
    return false;
  }
  
  public void clearTutorial()
  {
    if ((getActivity() == null) || (this.imageView == null) || (shownImageTutorial)) {
      return;
    }
    TooltipManager.getInstance(getActivity()).remove(3);
    shownImageTutorial = true;
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.imageView.getContext()).edit();
    localEditor.putBoolean("shownImageTutorial", true);
    localEditor.commit();
  }
  
  public View createFragmentView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramViewGroup = paramLayoutInflater.inflate(2130903089, null);
    this.imageView = ((PhotoView)paramViewGroup.findViewById(2131493031));
    this.description = ((TextView)paramViewGroup.findViewById(2131493036));
    this.description.setLinksClickable(true);
    this.progress = paramViewGroup.findViewById(2131493028);
    this.tempImageView = ((ImageView)paramViewGroup.findViewById(2131493027));
    this.title = ((TextView)paramViewGroup.findViewById(2131493040));
    this.imageScaleListener = new OnImageScaleListener()
    {
      public void onContract()
      {
        GalleryImageFragment.this.renderMetadata();
        GalleryImageFragment.this.fragmentOptions.setVisibility(0);
        if (GalleryImageFragment.this.getUserVisibleHint()) {
          GalleryImageFragment.this.getViewer().showOmnibar();
        }
      }
      
      public void onExpand()
      {
        GalleryImageFragment.this.fragmentOptions.setVisibility(8);
        if (GalleryImageFragment.this.getUserVisibleHint()) {
          GalleryImageFragment.this.getViewer().hideOmnibar();
        }
      }
    };
    this.fragmentOptions = paramViewGroup.findViewById(2131492967);
    this.title.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        GalleryImageFragment.this.getImageScrollView().expand();
      }
    });
    this.metadataError = ((TextView)paramViewGroup.findViewById(2131493038));
    this.metadataView = inflateMetadataLayout(paramLayoutInflater);
    this.error = ((ErrorView)paramViewGroup.findViewById(2131492959));
    if (this.metadataView != null)
    {
      paramLayoutInflater = new RelativeLayout.LayoutParams(-1, -2);
      ((RelativeLayout)paramViewGroup.findViewById(2131493035)).addView(this.metadataView, paramLayoutInflater);
    }
    this.producer = getProducer();
    this.imageView.setAllowParentInterceptOnEdge(true);
    this.webView = ((WebView)paramViewGroup.findViewById(2131493030));
    paramViewGroup.findViewById(2131493029).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        GalleryImageFragment.this.imageScrollView.toggle();
      }
    });
    indicateProgressChange(0.05F);
    this.save = ((SaveImageButton)paramViewGroup.findViewById(2131492970));
    this.overflow = ((OverflowImageButton)paramViewGroup.findViewById(2131492971));
    this.overflow.initialize(getUrl());
    this.imageScrollView = ((ImageScrollView)paramViewGroup.findViewById(2131493025));
    if (!shownImageTutorial)
    {
      if (!PreferenceManager.getDefaultSharedPreferences(this.save.getContext()).getBoolean("shownImageTutorial", false)) {
        break label424;
      }
      shownImageTutorial = true;
    }
    for (;;)
    {
      this.imageScrollView.setOnImageScaleListener(this.imageScaleListener);
      if ((getImage() != null) && (!getImage().needsToBeReloadedInImageFragment())) {
        break;
      }
      if (getImage() != null)
      {
        getImage().setReload(false);
        this.imageScrollView.render(getImage().getHeight());
        renderThumbnail();
      }
      this.reloading = true;
      this.producer.clearInitialized();
      this.producer.initialize(this, getImage(), null, true);
      return paramViewGroup;
      label424:
      this.imageScrollView.setTutorial(this);
    }
    render();
    return paramViewGroup;
  }
  
  protected GalleryProducer createProducer()
  {
    return null;
  }
  
  public void deferSetDescription(String paramString) {}
  
  protected void fillMetadataView() {}
  
  public void finishLoading()
  {
    this.imageView.setVisibility(8);
    this.error.setVisibility(0);
  }
  
  public GalleryConsumer getConsumer()
  {
    return this;
  }
  
  protected ImageScrollView getImageScrollView()
  {
    return this.imageScrollView;
  }
  
  protected ViewGroup getMetadataView()
  {
    return this.metadataView;
  }
  
  public void goToImage(View paramView, GalleryImage paramGalleryImage, int paramInt) {}
  
  public void hideClutter() {}
  
  protected void indicateMetadataError(String paramString, Exception paramException)
  {
    if (this.metadataView == null) {}
    do
    {
      return;
      this.metadataView.setVisibility(8);
      this.metadataError.setVisibility(0);
    } while (paramException == null);
    Analytics.INSTANCE.newError(DisplayError.COULD_NOT_FETCH_IMAGE_METADATA, paramException.getMessage());
    this.metadataError.setText(paramString + ": " + paramException.getMessage());
  }
  
  protected void indicateMetadataNotFound()
  {
    indicateMetadataError(null, null);
  }
  
  protected ViewGroup inflateMetadataLayout(LayoutInflater paramLayoutInflater)
  {
    return null;
  }
  
  protected void invalidateAlreadyRenderedMetadataFlag()
  {
    this.alreadyRenderedMetadata = false;
  }
  
  public void notifyDataSetChanged() {}
  
  public void omniSearchIsNotShowingHack()
  {
    if (getImageScrollView() != null) {
      getImageScrollView().omniSearchIsNotShowingHack();
    }
  }
  
  public void omniSearchIsShowingHack()
  {
    if (getImageScrollView() != null) {
      getImageScrollView().omniSearchIsShowingHack(getUserVisibleHint());
    }
  }
  
  public void onDestroyView()
  {
    this.progress.clearAnimation();
    Picasso.with(getActivity()).cancelRequest(this.tempImageView);
    Picasso.with(getActivity()).cancelRequest(this.imageView);
    if (this.producer != null) {
      this.producer.removeConsumer(this);
    }
    super.onDestroyView();
    murderWebview(this.webView);
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    return false;
  }
  
  public void prepareForRefresh() {}
  
  public void resume() {}
  
  public void setImage(GalleryImage paramGalleryImage)
  {
    super.setImage(paramGalleryImage);
    getImage().addView(this);
  }
  
  public void setUserVisibleHint(boolean paramBoolean)
  {
    super.setUserVisibleHint(paramBoolean);
    render();
  }
  
  public void showClutter() {}
  
  public void showError(Exception paramException)
  {
    if ((getImage() != null) && (getImage().hasError()))
    {
      indicateImageRenderError(getImage().printError());
      return;
    }
    this.imageView.setVisibility(8);
    this.error.show(DisplayError.IMAGE_NOT_LOADING, paramException.getMessage(), getDisplayUrl());
  }
  
  public void stopLoading()
  {
    Picasso.with(getActivity()).cancelRequest(this.imageView);
  }
  
  public boolean undo()
  {
    boolean bool = true;
    if ((this.imageScrollView == null) || (this.error == null)) {
      bool = false;
    }
    do
    {
      return bool;
      if (this.imageScrollView.contract())
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "undo full screen", null);
        return true;
      }
    } while (this.error.close());
    return false;
  }
  
  public void update()
  {
    render();
  }
  
  public boolean willAllowPaging(MotionEvent paramMotionEvent)
  {
    return false;
  }
  
  private class ImageCallback
    implements Callback
  {
    private ImageCallback() {}
    
    public void onError(Exception paramException)
    {
      Object localObject = "";
      if (paramException != null) {
        localObject = paramException.toString();
      }
      paramException = (Exception)localObject;
      if (((String)localObject).contains(":")) {
        paramException = ((String)localObject).substring(((String)localObject).indexOf(":") + 1);
      }
      if (!paramException.contains("PICASSO"))
      {
        localObject = paramException;
        if (!paramException.contains("Picasso")) {}
      }
      else
      {
        localObject = "Crumby ran out memory. Please restart the app";
      }
      if (((String)localObject).contains("com.crumby.c")) {
        GalleryImageFragment.access$702(GalleryImageFragment.this, true);
      }
      GalleryImageFragment.this.indicateImageRenderError((String)localObject);
    }
    
    public void onSuccess()
    {
      if (GalleryImageFragment.this.getActivity() == null) {
        return;
      }
      GalleryImageFragment.this.indicateProgressChange(1.0F);
      if (!GalleryImageFragment.shownImageTutorial)
      {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "tap to zoom on image", null);
        TooltipManager.getInstance(GalleryImageFragment.this.getActivity()).create(3).anchor(GalleryImageFragment.this.imageView, TooltipManager.Gravity.BOTTOM).closePolicy(TooltipManager.ClosePolicy.None, 10000L).activateDelay(500L).withStyleId(2131361823).text("Try tapping on this image to zoom in!").maxWidth(1000).show();
      }
      GalleryImageFragment.this.hideLoading();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/GalleryImageFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */