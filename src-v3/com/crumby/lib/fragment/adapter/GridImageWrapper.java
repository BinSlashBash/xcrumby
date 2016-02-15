package com.crumby.lib.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import org.apache.commons.codec.binary.Hex;

public class GridImageWrapper extends ImageWrapper {
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

    public GridImageWrapper(View convertView, boolean inSequence) {
        this.metaData = (LinearLayout) convertView.findViewById(C0065R.id.wrapper_metadata);
        this.subtitle = (TextView) convertView.findViewById(C0065R.id.image_subtitle);
        this.imageView = (ImageView) convertView.findViewById(C0065R.id.thumbnail);
        this.description = (TextView) convertView.findViewById(C0065R.id.image_description);
        this.title = (TextView) convertView.findViewById(C0065R.id.image_title);
        this.galleryLink = (ImageView) convertView.findViewById(C0065R.id.gallery_link);
        this.loading = convertView.findViewById(C0065R.id.loading_grid_image);
        this.webViewContainer = (FrameLayout) convertView.findViewById(C0065R.id.webview_container);
        this.renderedPosition = -1;
        if (inSequence) {
            this.description.setMaxLines(100);
            this.imageView.setAdjustViewBounds(true);
            this.loading.setVisibility(0);
            return;
        }
        this.description.setMaxLines(1);
    }

    public void clear() {
        CrDb.m0d("wrapper", toString() + " cleared!");
        this.imageView.setVisibility(0);
        this.imageView.setImageBitmap(null);
        setImage(null);
        this.title.setText(UnsupportedUrlFragment.DISPLAY_NAME);
        this.description.setText(UnsupportedUrlFragment.DISPLAY_NAME);
        this.subtitle.setText(UnsupportedUrlFragment.DISPLAY_NAME);
        this.galleryLink.setVisibility(8);
        if (this.webView != null) {
            this.webView.stopLoading();
            this.webView.clearView();
            this.webViewContainer.setVisibility(8);
        }
    }

    private void setAndShow(TextView view, String value) {
        if (value == null) {
            view.setVisibility(8);
        } else {
            value = value.substring(0, Math.min(value.length(), 100));
            view.setVisibility(0);
        }
        view.setText(value);
    }

    public void set(GalleryImage image) {
        int showMetaData;
        setAndShow(this.title, image.getTitle());
        setAndShow(this.description, image.getDescription());
        setAndShow(this.subtitle, image.getSubtitle());
        if (image.getTitle() == null || image.getTitle().equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            showMetaData = 8;
        } else {
            showMetaData = 0;
        }
        this.metaData.setVisibility(showMetaData);
        setImage(image);
        if (image.hasIcon()) {
            this.galleryLink.setVisibility(0);
            if (image.getIconDrawable() == null) {
                this.galleryLink.setImageResource(image.getIcon());
            } else {
                this.galleryLink.setImageDrawable(image.getIconDrawable());
            }
        }
        if (image.isAnimated() && this.webView != null) {
            CrDb.m0d("wrapper", toString() + " set!");
        }
    }

    public void initWebView(Context context) {
        WebView webView = new WebView(context);
        this.webViewContainer.addView(webView);
        this.webView = webView;
        this.webViewContainer.setVisibility(4);
        webView.setFocusable(false);
        webView.setClickable(false);
        webView.setBackgroundColor(0);
        webView.setLayerType(1, null);
    }

    public void loadWithWebView(Context context) {
        if (context != null) {
            if (this.webView == null) {
                initWebView(context);
            }
            CrDb.m0d("wrapper", toString() + " has loaded!");
            this.webView.loadDataWithBaseURL(null, getWebViewImgHtml(this.image.getImageUrl(), false), "text/html", Hex.DEFAULT_CHARSET_NAME, null);
            this.webViewContainer.setVisibility(0);
            this.imageView.setVisibility(8);
        }
    }

    public static final String getWebViewImgHtml(String url, boolean restrictHeight) {
        return "<html><body style='text-align: center;'><img style='max-width:100%; " + (restrictHeight ? "max-height:100%;" : UnsupportedUrlFragment.DISPLAY_NAME) + "' src='" + url + "'/></body></html>";
    }

    public boolean isFilledWith(GalleryImage image) {
        return image == this.image;
    }

    public boolean hasRendered(GalleryImage image) {
        return image != null && image.getPosition() == this.renderedPosition;
    }

    public void setRenderedPosition() {
        this.renderedPosition = this.image.getPosition();
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public void destroy() {
        if (this.webView != null) {
            this.webView.destroy();
        }
    }
}
