/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Paint
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.view.View
 *  android.webkit.WebView
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.crumby.lib.fragment.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.ImageWrapper;

public class GridImageWrapper
extends ImageWrapper {
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

    public GridImageWrapper(View view, boolean bl2) {
        this.metaData = (LinearLayout)view.findViewById(2131493043);
        this.subtitle = (TextView)view.findViewById(2131493045);
        this.imageView = (ImageView)view.findViewById(2131493006);
        this.description = (TextView)view.findViewById(2131493046);
        this.title = (TextView)view.findViewById(2131493044);
        this.galleryLink = (ImageView)view.findViewById(2131493042);
        this.loading = view.findViewById(2131493005);
        this.webViewContainer = (FrameLayout)view.findViewById(2131493007);
        this.renderedPosition = -1;
        if (bl2) {
            this.description.setMaxLines(100);
            this.imageView.setAdjustViewBounds(true);
            this.loading.setVisibility(0);
            return;
        }
        this.description.setMaxLines(1);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final String getWebViewImgHtml(String string2, boolean bl2) {
        String string3;
        if (bl2) {
            string3 = "max-height:100%;";
            do {
                return "<html><body style='text-align: center;'><img style='max-width:100%; " + string3 + "' src='" + string2 + "'/></body></html>";
                break;
            } while (true);
        }
        string3 = "";
        return "<html><body style='text-align: center;'><img style='max-width:100%; " + string3 + "' src='" + string2 + "'/></body></html>";
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setAndShow(TextView textView, String string2) {
        if (string2 == null) {
            textView.setVisibility(8);
        } else {
            string2 = string2.substring(0, Math.min(string2.length(), 100));
            textView.setVisibility(0);
        }
        textView.setText((CharSequence)string2);
    }

    public void clear() {
        CrDb.d("wrapper", this.toString() + " cleared!");
        this.imageView.setVisibility(0);
        this.imageView.setImageBitmap(null);
        this.setImage(null);
        this.title.setText((CharSequence)"");
        this.description.setText((CharSequence)"");
        this.subtitle.setText((CharSequence)"");
        this.galleryLink.setVisibility(8);
        if (this.webView != null) {
            this.webView.stopLoading();
            this.webView.clearView();
            this.webViewContainer.setVisibility(8);
        }
    }

    public void destroy() {
        if (this.webView != null) {
            this.webView.destroy();
        }
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public boolean hasRendered(GalleryImage galleryImage) {
        if (galleryImage != null && galleryImage.getPosition() == this.renderedPosition) {
            return true;
        }
        return false;
    }

    public void initWebView(Context context) {
        context = new WebView(context);
        this.webViewContainer.addView((View)context);
        this.webView = context;
        this.webViewContainer.setVisibility(4);
        context.setFocusable(false);
        context.setClickable(false);
        context.setBackgroundColor(0);
        context.setLayerType(1, null);
    }

    public boolean isFilledWith(GalleryImage galleryImage) {
        if (galleryImage == this.image) {
            return true;
        }
        return false;
    }

    public void loadWithWebView(Context object) {
        if (object == null) {
            return;
        }
        if (this.webView == null) {
            this.initWebView((Context)object);
        }
        CrDb.d("wrapper", this.toString() + " has loaded!");
        object = GridImageWrapper.getWebViewImgHtml(this.image.getImageUrl(), false);
        this.webView.loadDataWithBaseURL(null, (String)object, "text/html", "UTF-8", null);
        this.webViewContainer.setVisibility(0);
        this.imageView.setVisibility(8);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void set(GalleryImage galleryImage) {
        this.setAndShow(this.title, galleryImage.getTitle());
        this.setAndShow(this.description, galleryImage.getDescription());
        this.setAndShow(this.subtitle, galleryImage.getSubtitle());
        int n2 = galleryImage.getTitle() == null || galleryImage.getTitle().equals("") ? 8 : 0;
        this.metaData.setVisibility(n2);
        this.setImage(galleryImage);
        if (galleryImage.hasIcon()) {
            this.galleryLink.setVisibility(0);
            if (galleryImage.getIconDrawable() == null) {
                this.galleryLink.setImageResource(galleryImage.getIcon());
            } else {
                this.galleryLink.setImageDrawable((Drawable)galleryImage.getIconDrawable());
            }
        }
        if (galleryImage.isAnimated() && this.webView != null) {
            CrDb.d("wrapper", this.toString() + " set!");
        }
    }

    public void setRenderedPosition() {
        this.renderedPosition = this.image.getPosition();
    }
}

