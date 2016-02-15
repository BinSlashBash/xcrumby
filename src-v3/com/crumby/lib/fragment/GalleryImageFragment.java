package com.crumby.lib.fragment;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
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
import com.google.android.gms.location.GeofenceStatusCodes;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import it.sephiroth.android.library.tooltip.TooltipManager;
import it.sephiroth.android.library.tooltip.TooltipManager.ClosePolicy;
import it.sephiroth.android.library.tooltip.TooltipManager.Gravity;
import java.util.List;
import org.apache.commons.codec.binary.Hex;
import uk.co.senab.photoview.PhotoView;

public class GalleryImageFragment extends GalleryViewerFragment implements GalleryImageTutorial, GalleryConsumer, GalleryImageView, OnTouchListener {
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

    /* renamed from: com.crumby.lib.fragment.GalleryImageFragment.2 */
    class C00982 implements OnClickListener {
        C00982() {
        }

        public void onClick(View v) {
            GalleryImageFragment.this.getImageScrollView().expand();
        }
    }

    /* renamed from: com.crumby.lib.fragment.GalleryImageFragment.3 */
    class C00993 implements OnClickListener {
        C00993() {
        }

        public void onClick(View v) {
            GalleryImageFragment.this.imageScrollView.toggle();
        }
    }

    /* renamed from: com.crumby.lib.fragment.GalleryImageFragment.1 */
    class C07361 implements OnImageScaleListener {
        C07361() {
        }

        public void onExpand() {
            GalleryImageFragment.this.fragmentOptions.setVisibility(8);
            if (GalleryImageFragment.this.getUserVisibleHint()) {
                GalleryImageFragment.this.getViewer().hideOmnibar();
            }
        }

        public void onContract() {
            GalleryImageFragment.this.renderMetadata();
            GalleryImageFragment.this.fragmentOptions.setVisibility(0);
            if (GalleryImageFragment.this.getUserVisibleHint()) {
                GalleryImageFragment.this.getViewer().showOmnibar();
            }
        }
    }

    private class ImageCallback implements Callback {
        private ImageCallback() {
        }

        public void onSuccess() {
            if (GalleryImageFragment.this.getActivity() != null) {
                GalleryImageFragment.this.indicateProgressChange(GalleryViewer.PROGRESS_COMPLETED);
                if (!GalleryImageFragment.shownImageTutorial) {
                    Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "tap to zoom on image", null);
                    TooltipManager.getInstance(GalleryImageFragment.this.getActivity()).create(GalleryImageFragment.TAP_TO_ZOOM_TOOLTIP).anchor(GalleryImageFragment.this.imageView, Gravity.BOTTOM).closePolicy(ClosePolicy.None, 10000).activateDelay(500).withStyleId(C0065R.style.tutorial_hint).text("Try tapping on this image to zoom in!").maxWidth(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE).show();
                }
                GalleryImageFragment.this.hideLoading();
            }
        }

        public void onError(Exception e) {
            String error = UnsupportedUrlFragment.DISPLAY_NAME;
            if (e != null) {
                error = e.toString();
            }
            if (error.contains(":")) {
                error = error.substring(error.indexOf(":") + 1);
            }
            if (error.contains("PICASSO") || error.contains("Picasso")) {
                error = "Crumby ran out memory. Please restart the app";
            }
            if (error.contains("com.crumby.c")) {
                GalleryImageFragment.this.neverLoadAgain = true;
            }
            GalleryImageFragment.this.indicateImageRenderError(error);
        }
    }

    protected ImageScrollView getImageScrollView() {
        return this.imageScrollView;
    }

    protected static void loadWebViewHtml(String htmlBody, WebView view) {
        view.loadData("<html><head><style type=\"text/css\">body{color: #e0e0e0; font-size: 10pt}</style></head><body>" + htmlBody + "</body></html>", "text/html", "utf-8");
    }

    protected GalleryProducer createProducer() {
        return null;
    }

    public void setImage(GalleryImage image) {
        super.setImage(image);
        getImage().addView(this);
    }

    protected ViewGroup getMetadataView() {
        return this.metadataView;
    }

    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(C0065R.layout.image_view_page, null);
        this.imageView = (PhotoView) view.findViewById(C0065R.id.photo_view);
        this.description = (TextView) view.findViewById(C0065R.id.photo_description);
        this.description.setLinksClickable(true);
        this.progress = view.findViewById(C0065R.id.image_progress);
        this.tempImageView = (ImageView) view.findViewById(C0065R.id.temp_view);
        this.title = (TextView) view.findViewById(C0065R.id.photo_title);
        this.imageScaleListener = new C07361();
        this.fragmentOptions = view.findViewById(C0065R.id.fragment_options);
        this.title.setOnClickListener(new C00982());
        this.metadataError = (TextView) view.findViewById(C0065R.id.metadata_error);
        this.metadataView = inflateMetadataLayout(inflater);
        this.error = (ErrorView) view.findViewById(C0065R.id.error_view);
        if (this.metadataView != null) {
            ((RelativeLayout) view.findViewById(C0065R.id.image_view_metadata_container)).addView(this.metadataView, new LayoutParams(-1, -2));
        }
        this.producer = getProducer();
        this.imageView.setAllowParentInterceptOnEdge(true);
        this.webView = (WebView) view.findViewById(C0065R.id.image_web_view);
        view.findViewById(C0065R.id.web_view_on_click_workaround).setOnClickListener(new C00993());
        indicateProgressChange(GalleryViewer.PROGRESS_STARTED);
        this.save = (SaveImageButton) view.findViewById(C0065R.id.save_current_image);
        this.overflow = (OverflowImageButton) view.findViewById(C0065R.id.overflow_button);
        this.overflow.initialize(getUrl());
        this.imageScrollView = (ImageScrollView) view.findViewById(C0065R.id.image_scroll_view);
        if (!shownImageTutorial) {
            if (PreferenceManager.getDefaultSharedPreferences(this.save.getContext()).getBoolean("shownImageTutorial", false)) {
                shownImageTutorial = true;
            } else {
                this.imageScrollView.setTutorial(this);
            }
        }
        this.imageScrollView.setOnImageScaleListener(this.imageScaleListener);
        if (getImage() == null || getImage().needsToBeReloadedInImageFragment()) {
            if (getImage() != null) {
                getImage().setReload(false);
                this.imageScrollView.render(getImage().getHeight());
                renderThumbnail();
            }
            this.reloading = true;
            this.producer.clearInitialized();
            this.producer.initialize(this, getImage(), null, true);
        } else {
            render();
        }
        return view;
    }

    protected ViewGroup inflateMetadataLayout(LayoutInflater inflater) {
        return null;
    }

    public void stopLoading() {
        Picasso.with(getActivity()).cancelRequest(this.imageView);
    }

    public void resume() {
    }

    public void showClutter() {
    }

    public void hideClutter() {
    }

    public boolean undo() {
        if (this.imageScrollView == null || this.error == null) {
            return false;
        }
        if (this.imageScrollView.contract()) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "undo full screen", null);
            return true;
        } else if (this.error.close()) {
            return true;
        } else {
            return false;
        }
    }

    public void deferSetDescription(String update) {
    }

    public boolean willAllowPaging(MotionEvent ev) {
        return false;
    }

    public void prepareForRefresh() {
    }

    public void showError(Exception e) {
        if (getImage() == null || !getImage().hasError()) {
            this.imageView.setVisibility(8);
            this.error.show(DisplayError.IMAGE_NOT_LOADING, e.getMessage(), getDisplayUrl());
            return;
        }
        indicateImageRenderError(getImage().printError());
    }

    public void finishLoading() {
        this.imageView.setVisibility(8);
        this.error.setVisibility(0);
    }

    protected void invalidateAlreadyRenderedMetadataFlag() {
        this.alreadyRenderedMetadata = false;
    }

    private void showAndSet(TextView view, String value) {
        if (value != null && !value.equals(UnsupportedUrlFragment.DISPLAY_NAME) && !value.equals(view.getText())) {
            view.setVisibility(0);
            view.setText(value);
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        render();
    }

    private void hideLoading() {
        this.tempImageView.setVisibility(8);
        this.progress.clearAnimation();
        this.progress.setVisibility(8);
    }

    public void onDestroyView() {
        this.progress.clearAnimation();
        Picasso.with(getActivity()).cancelRequest(this.tempImageView);
        Picasso.with(getActivity()).cancelRequest(this.imageView);
        if (this.producer != null) {
            this.producer.removeConsumer(this);
        }
        super.onDestroyView();
        murderWebview(this.webView);
    }

    private void render() {
        GalleryImage image = getImage();
        if (image != null && this.save != null && getActivity() != null && !this.reloading) {
            showAndSet(this.title, image.getTitle());
            this.imageScrollView.render(getImage().getHeight());
            renderMetadata();
            if (!this.alreadyRenderedImage) {
                renderThumbnail();
                if (getUserVisibleHint()) {
                    this.save.initialize(image);
                    this.overflow.initialize(image);
                    renderImage();
                }
            }
        }
    }

    private void renderThumbnail() {
        if (!this.renderedThumbnail && getImage() != null && getActivity() != null && !getImage().isAnimated() && getImage().getThumbnailUrl() != null) {
            RequestCreator request = Picasso.with(getActivity()).load(getImage().getThumbnailUrl());
            if (getImage().isSplit()) {
                request = request.transform(new CropTransformation(getImage(), getImage().getPosition()));
            }
            request.into(this.tempImageView);
            this.renderedThumbnail = true;
        }
    }

    protected void fillMetadataView() {
    }

    protected void indicateMetadataError(String prefix, Exception e) {
        if (this.metadataView != null) {
            this.metadataView.setVisibility(8);
            this.metadataError.setVisibility(0);
            if (e != null) {
                Analytics.INSTANCE.newError(DisplayError.COULD_NOT_FETCH_IMAGE_METADATA, e.getMessage());
                this.metadataError.setText(prefix + ": " + e.getMessage());
            }
        }
    }

    private void renderMetadata() {
        if (!this.alreadyRenderedMetadata && this.metadataView != null && getUserVisibleHint() && !ImageScrollView.userWantsFullScreen && !getImageScrollView().isInFullScreen()) {
            this.alreadyRenderedMetadata = true;
            fillMetadataView();
        }
    }

    public static final String getWebViewImgHtml(String url) {
        return "<html style='height:100%;\n   width: 100%;\n   margin: 0;\n   padding: 0;\n   border: 0;'>\n<body style='height:100%;\n   width: 100%;\n   margin: 0;\n   padding: 0;\n   border: 0;'>\n   <table style='height:100%;\n   width: 100%;\n   margin: 0;\n   padding: 0;\n   border: 0;'>\n      <tr>\n         <td style='vertical-align: middle;\n   text-align: center;'><img style='max-width:100%;' src='" + url + "' alt=\"\" /></td>\n" + "      </tr>\n" + "   </table>\n" + "</body>\n" + "</html>";
    }

    private void renderImage() {
        if (!this.neverLoadAgain) {
            this.alreadyRenderedImage = true;
            if (getImage().hasError()) {
                getImage().setReload(true);
                indicateImageRenderError(getImage().printError());
            } else if (getImage().isAnimated()) {
                hideLoading();
                this.webView.setBackgroundColor(0);
                this.webView.setLayerType(1, null);
                this.webView.getSettings().setBuiltInZoomControls(true);
                this.webView.getSettings().setDisplayZoomControls(false);
                this.webView.getSettings().setSupportZoom(true);
                this.webView.setVisibility(0);
                this.webView.loadDataWithBaseURL(null, getWebViewImgHtml(getImage().getImageUrl()), "text/html", Hex.DEFAULT_CHARSET_NAME, null);
                indicateProgressChange(GalleryViewer.PROGRESS_COMPLETED);
            } else if (getImage().getImageUrl() == null) {
                indicateImageRenderError("Invalid/unparseable image url.");
            } else if (getImage().getImageUrl().endsWith(".swf")) {
                if (getActivity() != null) {
                    indicateImageRenderError("SWF/Flash files are not supported in Android :-(");
                } else {
                    indicateImageRenderError("SWF/Flash files are not supported in Android :-(");
                }
            } else {
                CrDb.m0d("image fragment", "will render image");
                int maxWidth = getScreenWidth();
                int maxHeight = getResources().getDisplayMetrics().heightPixels;
                RequestCreator request = Picasso.with(getActivity()).load(getImage().getImageUrl());
                int resizeWidth = getImage().getWidth();
                int resizeHeight = getImage().getHeight();
                if (maxWidth < resizeWidth || maxHeight < resizeHeight) {
                    request.resize(maxWidth, maxHeight).centerInside();
                }
                this.progress.animate().setStartDelay(1000).alpha(GalleryViewer.PROGRESS_COMPLETED);
                if (!shownImageTutorial) {
                    TooltipManager.getInstance(getActivity()).remove(TAP_TO_ZOOM_TOOLTIP);
                }
                request.noFade().into(this.imageView, new ImageCallback());
            }
        }
    }

    private void showLoginPrompt() {
    }

    private void indicateImageRenderError(String reason) {
        hideLoading();
        indicateProgressChange(GalleryViewer.PROGRESS_COMPLETED);
        this.imageView.setVisibility(8);
        if (!this.error.shownError()) {
            this.error.show(DisplayError.IMAGE_NOT_RENDERING, reason, getDisplayUrl());
        }
    }

    protected void indicateMetadataNotFound() {
        indicateMetadataError(null, null);
    }

    public boolean addImages(List<GalleryImage> images) {
        this.reloading = false;
        if (images.size() == 0) {
            CrDb.m0d("image fragment", "no images!");
            return true;
        }
        setImage((GalleryImage) images.get(0));
        this.alreadyRenderedMetadata = false;
        render();
        return false;
    }

    public GalleryConsumer getConsumer() {
        return this;
    }

    public void notifyDataSetChanged() {
    }

    public void update() {
        render();
    }

    public void goToImage(View v, GalleryImage image, int position) {
    }

    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public void clearTutorial() {
        if (getActivity() != null && this.imageView != null && !shownImageTutorial) {
            TooltipManager.getInstance(getActivity()).remove(TAP_TO_ZOOM_TOOLTIP);
            shownImageTutorial = true;
            Editor editor = PreferenceManager.getDefaultSharedPreferences(this.imageView.getContext()).edit();
            editor.putBoolean("shownImageTutorial", true);
            editor.commit();
        }
    }

    public void omniSearchIsShowingHack() {
        if (getImageScrollView() != null) {
            getImageScrollView().omniSearchIsShowingHack(getUserVisibleHint());
        }
    }

    public void omniSearchIsNotShowingHack() {
        if (getImageScrollView() != null) {
            getImageScrollView().omniSearchIsNotShowingHack();
        }
    }
}
