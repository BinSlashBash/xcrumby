package com.crumby.lib.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;

public class GridImageWrapper
  extends ImageWrapper
{
  TextView description;
  ImageView galleryLink;
  public ImageView imageView;
  View loading;
  LinearLayout metaData;
  private int renderedPosition;
  TextView subtitle;
  TextView title;
  WebView webView;
  FrameLayout webViewContainer;
  
  public GridImageWrapper(View paramView, boolean paramBoolean)
  {
    this.metaData = ((LinearLayout)paramView.findViewById(2131493043));
    this.subtitle = ((TextView)paramView.findViewById(2131493045));
    this.imageView = ((ImageView)paramView.findViewById(2131493006));
    this.description = ((TextView)paramView.findViewById(2131493046));
    this.title = ((TextView)paramView.findViewById(2131493044));
    this.galleryLink = ((ImageView)paramView.findViewById(2131493042));
    this.loading = paramView.findViewById(2131493005);
    this.webViewContainer = ((FrameLayout)paramView.findViewById(2131493007));
    this.renderedPosition = -1;
    if (paramBoolean)
    {
      this.description.setMaxLines(100);
      this.imageView.setAdjustViewBounds(true);
      this.loading.setVisibility(0);
      return;
    }
    this.description.setMaxLines(1);
  }
  
  public static final String getWebViewImgHtml(String paramString, boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (String str = "max-height:100%;";; str = "") {
      return "<html><body style='text-align: center;'><img style='max-width:100%; " + str + "' src='" + paramString + "'/></body></html>";
    }
  }
  
  private void setAndShow(TextView paramTextView, String paramString)
  {
    if (paramString == null) {
      paramTextView.setVisibility(8);
    }
    for (;;)
    {
      paramTextView.setText(paramString);
      return;
      paramString = paramString.substring(0, Math.min(paramString.length(), 100));
      paramTextView.setVisibility(0);
    }
  }
  
  public void clear()
  {
    CrDb.d("wrapper", toString() + " cleared!");
    this.imageView.setVisibility(0);
    this.imageView.setImageBitmap(null);
    setImage(null);
    this.title.setText("");
    this.description.setText("");
    this.subtitle.setText("");
    this.galleryLink.setVisibility(8);
    if (this.webView != null)
    {
      this.webView.stopLoading();
      this.webView.clearView();
      this.webViewContainer.setVisibility(8);
    }
  }
  
  public void destroy()
  {
    if (this.webView != null) {
      this.webView.destroy();
    }
  }
  
  public ImageView getImageView()
  {
    return this.imageView;
  }
  
  public boolean hasRendered(GalleryImage paramGalleryImage)
  {
    return (paramGalleryImage != null) && (paramGalleryImage.getPosition() == this.renderedPosition);
  }
  
  public void initWebView(Context paramContext)
  {
    paramContext = new WebView(paramContext);
    this.webViewContainer.addView(paramContext);
    this.webView = paramContext;
    this.webViewContainer.setVisibility(4);
    paramContext.setFocusable(false);
    paramContext.setClickable(false);
    paramContext.setBackgroundColor(0);
    paramContext.setLayerType(1, null);
  }
  
  public boolean isFilledWith(GalleryImage paramGalleryImage)
  {
    return paramGalleryImage == this.image;
  }
  
  public void loadWithWebView(Context paramContext)
  {
    if (paramContext == null) {
      return;
    }
    if (this.webView == null) {
      initWebView(paramContext);
    }
    CrDb.d("wrapper", toString() + " has loaded!");
    paramContext = getWebViewImgHtml(this.image.getImageUrl(), false);
    this.webView.loadDataWithBaseURL(null, paramContext, "text/html", "UTF-8", null);
    this.webViewContainer.setVisibility(0);
    this.imageView.setVisibility(8);
  }
  
  public void set(GalleryImage paramGalleryImage)
  {
    setAndShow(this.title, paramGalleryImage.getTitle());
    setAndShow(this.description, paramGalleryImage.getDescription());
    setAndShow(this.subtitle, paramGalleryImage.getSubtitle());
    int i;
    if ((paramGalleryImage.getTitle() == null) || (paramGalleryImage.getTitle().equals("")))
    {
      i = 8;
      this.metaData.setVisibility(i);
      setImage(paramGalleryImage);
      if (paramGalleryImage.hasIcon())
      {
        this.galleryLink.setVisibility(0);
        if (paramGalleryImage.getIconDrawable() != null) {
          break label151;
        }
        this.galleryLink.setImageResource(paramGalleryImage.getIcon());
      }
    }
    for (;;)
    {
      if ((paramGalleryImage.isAnimated()) && (this.webView != null)) {
        CrDb.d("wrapper", toString() + " set!");
      }
      return;
      i = 0;
      break;
      label151:
      this.galleryLink.setImageDrawable(paramGalleryImage.getIconDrawable());
    }
  }
  
  public void setRenderedPosition()
  {
    this.renderedPosition = this.image.getPosition();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/adapter/GridImageWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */