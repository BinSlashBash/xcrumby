/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.graphics.Paint
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewPropertyAnimator
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.widget.ImageView
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  android.widget.TextView
 */
package com.crumby.lib.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
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
import com.squareup.picasso.Transformation;
import it.sephiroth.android.library.tooltip.TooltipManager;
import java.util.List;
import uk.co.senab.photoview.PhotoView;

public class GalleryImageFragment
extends GalleryViewerFragment
implements GalleryImageTutorial,
GalleryConsumer,
GalleryImageView,
View.OnTouchListener {
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

    public static final String getWebViewImgHtml(String string2) {
        return "<html style='height:100%;\n   width: 100%;\n   margin: 0;\n   padding: 0;\n   border: 0;'>\n<body style='height:100%;\n   width: 100%;\n   margin: 0;\n   padding: 0;\n   border: 0;'>\n   <table style='height:100%;\n   width: 100%;\n   margin: 0;\n   padding: 0;\n   border: 0;'>\n      <tr>\n         <td style='vertical-align: middle;\n   text-align: center;'><img style='max-width:100%;' src='" + string2 + "' alt=\"\" /></td>\n" + "      </tr>\n" + "   </table>\n" + "</body>\n" + "</html>";
    }

    private void hideLoading() {
        this.tempImageView.setVisibility(8);
        this.progress.clearAnimation();
        this.progress.setVisibility(8);
    }

    private void indicateImageRenderError(String string2) {
        this.hideLoading();
        this.indicateProgressChange(1.0f);
        this.imageView.setVisibility(8);
        if (!this.error.shownError()) {
            this.error.show(DisplayError.IMAGE_NOT_RENDERING, string2, this.getDisplayUrl());
        }
    }

    protected static void loadWebViewHtml(String string2, WebView webView) {
        webView.loadData("<html><head><style type=\"text/css\">body{color: #e0e0e0; font-size: 10pt}</style></head><body>" + string2 + "</body></html>", "text/html", "utf-8");
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void render() {
        GalleryImage galleryImage = this.getImage();
        if (galleryImage == null) return;
        if (this.save == null) return;
        if (this.getActivity() == null) return;
        if (this.reloading) {
            return;
        }
        this.showAndSet(this.title, galleryImage.getTitle());
        this.imageScrollView.render(this.getImage().getHeight());
        this.renderMetadata();
        if (this.alreadyRenderedImage) return;
        this.renderThumbnail();
        if (!this.getUserVisibleHint()) return;
        this.save.initialize(galleryImage);
        this.overflow.initialize(galleryImage);
        this.renderImage();
    }

    private void renderImage() {
        if (this.neverLoadAgain) {
            return;
        }
        this.alreadyRenderedImage = true;
        if (this.getImage().hasError()) {
            this.getImage().setReload(true);
            this.indicateImageRenderError(this.getImage().printError());
            return;
        }
        if (this.getImage().isAnimated()) {
            this.hideLoading();
            this.webView.setBackgroundColor(0);
            this.webView.setLayerType(1, null);
            this.webView.getSettings().setBuiltInZoomControls(true);
            this.webView.getSettings().setDisplayZoomControls(false);
            this.webView.getSettings().setSupportZoom(true);
            this.webView.setVisibility(0);
            String string2 = GalleryImageFragment.getWebViewImgHtml(this.getImage().getImageUrl());
            this.webView.loadDataWithBaseURL(null, string2, "text/html", "UTF-8", null);
            this.indicateProgressChange(1.0f);
            return;
        }
        if (this.getImage().getImageUrl() == null) {
            this.indicateImageRenderError("Invalid/unparseable image url.");
            return;
        }
        if (this.getImage().getImageUrl().endsWith(".swf")) {
            if (this.getActivity() != null) {
                // empty if block
            }
            this.indicateImageRenderError("SWF/Flash files are not supported in Android :-(");
            return;
        }
        CrDb.d("image fragment", "will render image");
        int n2 = this.getScreenWidth();
        int n3 = this.getResources().getDisplayMetrics().heightPixels;
        RequestCreator requestCreator = Picasso.with((Context)this.getActivity()).load(this.getImage().getImageUrl());
        int n4 = this.getImage().getWidth();
        int n5 = this.getImage().getHeight();
        if (n2 < n4 || n3 < n5) {
            requestCreator.resize(n2, n3).centerInside();
        }
        this.progress.animate().setStartDelay(1000).alpha(1.0f);
        if (!shownImageTutorial) {
            TooltipManager.getInstance(this.getActivity()).remove(3);
        }
        requestCreator.noFade().into(this.imageView, new ImageCallback());
    }

    private void renderMetadata() {
        if (this.alreadyRenderedMetadata || this.metadataView == null || !this.getUserVisibleHint() || ImageScrollView.userWantsFullScreen || this.getImageScrollView().isInFullScreen()) {
            return;
        }
        this.alreadyRenderedMetadata = true;
        this.fillMetadataView();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void renderThumbnail() {
        RequestCreator requestCreator;
        if (this.renderedThumbnail || this.getImage() == null || this.getActivity() == null || this.getImage().isAnimated() || this.getImage().getThumbnailUrl() == null) {
            return;
        }
        RequestCreator requestCreator2 = requestCreator = Picasso.with((Context)this.getActivity()).load(this.getImage().getThumbnailUrl());
        if (this.getImage().isSplit()) {
            requestCreator2 = requestCreator.transform(new CropTransformation(this.getImage(), this.getImage().getPosition()));
        }
        requestCreator2.into(this.tempImageView);
        this.renderedThumbnail = true;
    }

    private void showAndSet(TextView textView, String string2) {
        if (string2 == null || string2.equals("") || string2.equals(textView.getText())) {
            return;
        }
        textView.setVisibility(0);
        textView.setText((CharSequence)string2);
    }

    private void showLoginPrompt() {
    }

    @Override
    public boolean addImages(List<GalleryImage> list) {
        this.reloading = false;
        if (list.size() == 0) {
            CrDb.d("image fragment", "no images!");
            return true;
        }
        this.setImage(list.get(0));
        this.alreadyRenderedMetadata = false;
        this.render();
        return false;
    }

    @Override
    public void clearTutorial() {
        if (this.getActivity() == null || this.imageView == null || shownImageTutorial) {
            return;
        }
        TooltipManager.getInstance(this.getActivity()).remove(3);
        shownImageTutorial = true;
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.imageView.getContext()).edit();
        editor.putBoolean("shownImageTutorial", true);
        editor.commit();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public View createFragmentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        viewGroup = layoutInflater.inflate(2130903089, null);
        this.imageView = (PhotoView)viewGroup.findViewById(2131493031);
        this.description = (TextView)viewGroup.findViewById(2131493036);
        this.description.setLinksClickable(true);
        this.progress = viewGroup.findViewById(2131493028);
        this.tempImageView = (ImageView)viewGroup.findViewById(2131493027);
        this.title = (TextView)viewGroup.findViewById(2131493040);
        this.imageScaleListener = new OnImageScaleListener(){

            @Override
            public void onContract() {
                GalleryImageFragment.this.renderMetadata();
                GalleryImageFragment.this.fragmentOptions.setVisibility(0);
                if (GalleryImageFragment.this.getUserVisibleHint()) {
                    GalleryImageFragment.this.getViewer().showOmnibar();
                }
            }

            @Override
            public void onExpand() {
                GalleryImageFragment.this.fragmentOptions.setVisibility(8);
                if (GalleryImageFragment.this.getUserVisibleHint()) {
                    GalleryImageFragment.this.getViewer().hideOmnibar();
                }
            }
        };
        this.fragmentOptions = viewGroup.findViewById(2131492967);
        this.title.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                GalleryImageFragment.this.getImageScrollView().expand();
            }
        });
        this.metadataError = (TextView)viewGroup.findViewById(2131493038);
        this.metadataView = this.inflateMetadataLayout(layoutInflater);
        this.error = (ErrorView)viewGroup.findViewById(2131492959);
        if (this.metadataView != null) {
            layoutInflater = new RelativeLayout.LayoutParams(-1, -2);
            ((RelativeLayout)viewGroup.findViewById(2131493035)).addView((View)this.metadataView, (ViewGroup.LayoutParams)layoutInflater);
        }
        this.producer = this.getProducer();
        this.imageView.setAllowParentInterceptOnEdge(true);
        this.webView = (WebView)viewGroup.findViewById(2131493030);
        viewGroup.findViewById(2131493029).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                GalleryImageFragment.this.imageScrollView.toggle();
            }
        });
        this.indicateProgressChange(0.05f);
        this.save = (SaveImageButton)viewGroup.findViewById(2131492970);
        this.overflow = (OverflowImageButton)viewGroup.findViewById(2131492971);
        this.overflow.initialize(this.getUrl());
        this.imageScrollView = (ImageScrollView)viewGroup.findViewById(2131493025);
        if (!shownImageTutorial) {
            if (PreferenceManager.getDefaultSharedPreferences((Context)this.save.getContext()).getBoolean("shownImageTutorial", false)) {
                shownImageTutorial = true;
            } else {
                this.imageScrollView.setTutorial(this);
            }
        }
        this.imageScrollView.setOnImageScaleListener(this.imageScaleListener);
        if (this.getImage() != null && !this.getImage().needsToBeReloadedInImageFragment()) {
            this.render();
            return viewGroup;
        }
        if (this.getImage() != null) {
            this.getImage().setReload(false);
            this.imageScrollView.render(this.getImage().getHeight());
            this.renderThumbnail();
        }
        this.reloading = true;
        this.producer.clearInitialized();
        this.producer.initialize(this, this.getImage(), null, true);
        return viewGroup;
    }

    @Override
    protected GalleryProducer createProducer() {
        return null;
    }

    @Override
    public void deferSetDescription(String string2) {
    }

    protected void fillMetadataView() {
    }

    @Override
    public void finishLoading() {
        this.imageView.setVisibility(8);
        this.error.setVisibility(0);
    }

    @Override
    public GalleryConsumer getConsumer() {
        return this;
    }

    protected ImageScrollView getImageScrollView() {
        return this.imageScrollView;
    }

    protected ViewGroup getMetadataView() {
        return this.metadataView;
    }

    @Override
    public void goToImage(View view, GalleryImage galleryImage, int n2) {
    }

    @Override
    public void hideClutter() {
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void indicateMetadataError(String string2, Exception exception) {
        if (this.metadataView == null) {
            return;
        }
        this.metadataView.setVisibility(8);
        this.metadataError.setVisibility(0);
        if (exception == null) return;
        Analytics.INSTANCE.newError(DisplayError.COULD_NOT_FETCH_IMAGE_METADATA, exception.getMessage());
        this.metadataError.setText((CharSequence)(string2 + ": " + exception.getMessage()));
    }

    protected void indicateMetadataNotFound() {
        this.indicateMetadataError(null, null);
    }

    protected ViewGroup inflateMetadataLayout(LayoutInflater layoutInflater) {
        return null;
    }

    protected void invalidateAlreadyRenderedMetadataFlag() {
        this.alreadyRenderedMetadata = false;
    }

    @Override
    public void notifyDataSetChanged() {
    }

    public void omniSearchIsNotShowingHack() {
        if (this.getImageScrollView() != null) {
            this.getImageScrollView().omniSearchIsNotShowingHack();
        }
    }

    public void omniSearchIsShowingHack() {
        if (this.getImageScrollView() != null) {
            this.getImageScrollView().omniSearchIsShowingHack(this.getUserVisibleHint());
        }
    }

    @Override
    public void onDestroyView() {
        this.progress.clearAnimation();
        Picasso.with((Context)this.getActivity()).cancelRequest(this.tempImageView);
        Picasso.with((Context)this.getActivity()).cancelRequest(this.imageView);
        if (this.producer != null) {
            this.producer.removeConsumer(this);
        }
        super.onDestroyView();
        this.murderWebview(this.webView);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void prepareForRefresh() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void setImage(GalleryImage galleryImage) {
        super.setImage(galleryImage);
        this.getImage().addView(this);
    }

    public void setUserVisibleHint(boolean bl2) {
        super.setUserVisibleHint(bl2);
        this.render();
    }

    @Override
    public void showClutter() {
    }

    @Override
    public void showError(Exception exception) {
        if (this.getImage() != null && this.getImage().hasError()) {
            this.indicateImageRenderError(this.getImage().printError());
            return;
        }
        this.imageView.setVisibility(8);
        this.error.show(DisplayError.IMAGE_NOT_LOADING, exception.getMessage(), this.getDisplayUrl());
    }

    @Override
    public void stopLoading() {
        Picasso.with((Context)this.getActivity()).cancelRequest(this.imageView);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean undo() {
        boolean bl2 = true;
        if (this.imageScrollView == null) return false;
        if (this.error == null) {
            return false;
        }
        if (this.imageScrollView.contract()) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "undo full screen", null);
            return true;
        }
        if (this.error.close()) return bl2;
        return false;
    }

    @Override
    public void update() {
        this.render();
    }

    @Override
    public boolean willAllowPaging(MotionEvent motionEvent) {
        return false;
    }

    private class ImageCallback
    implements Callback {
        private ImageCallback() {
        }

        @Override
        public void onError(Exception object) {
            Object object2;
            block5 : {
                object2 = "";
                if (object != null) {
                    object2 = object.toString();
                }
                object = object2;
                if (object2.contains(":")) {
                    object = object2.substring(object2.indexOf(":") + 1);
                }
                if (!object.contains("PICASSO")) {
                    object2 = object;
                    if (!object.contains("Picasso")) break block5;
                }
                object2 = "Crumby ran out memory. Please restart the app";
            }
            if (object2.contains("com.crumby.c")) {
                GalleryImageFragment.this.neverLoadAgain = true;
            }
            GalleryImageFragment.this.indicateImageRenderError((String)object2);
        }

        @Override
        public void onSuccess() {
            if (GalleryImageFragment.this.getActivity() == null) {
                return;
            }
            GalleryImageFragment.this.indicateProgressChange(1.0f);
            if (!shownImageTutorial) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "tap to zoom on image", null);
                TooltipManager.getInstance(GalleryImageFragment.this.getActivity()).create(3).anchor((View)GalleryImageFragment.this.imageView, TooltipManager.Gravity.BOTTOM).closePolicy(TooltipManager.ClosePolicy.None, 10000).activateDelay(500).withStyleId(2131361823).text("Try tapping on this image to zoom in!").maxWidth(1000).show();
            }
            GalleryImageFragment.this.hideLoading();
        }
    }

}

