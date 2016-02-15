package com.crumby.impl.deviantart;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.crumby.lib.widget.HomeGalleryList;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import java.util.ArrayList;
import java.util.List;

public class DeviantArtImageFragment extends GalleryImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;
    private WebView description;
    private HomeGalleryList homeGalleryList;
    private View loading;

    /* renamed from: com.crumby.impl.deviantart.DeviantArtImageFragment.1 */
    class C00871 implements Runnable {

        /* renamed from: com.crumby.impl.deviantart.DeviantArtImageFragment.1.1 */
        class C12741 extends SingleGalleryProducer {
            C12741() {
            }

            protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> galleryImages) throws Exception {
                galleryImages.addAll(((DeviantArtAttributes) DeviantArtImageFragment.this.getImage().attr()).getSimilarImages());
            }
        }

        C00871() {
        }

        public void run() {
            if (DeviantArtImageFragment.this.getActivity() != null) {
                GalleryImage image = new GalleryImage(UnsupportedUrlFragment.DISPLAY_NAME, DeviantArtMoreLikeThisFragment.BASE_URL + GalleryViewerFragment.matchIdFromUrl(DeviantArtImageFragment.REGEX_URL, DeviantArtImageFragment.this.getImage().getLinkUrl()), "Similar Images");
                if (DeviantArtImageFragment.this.homeGalleryList != null && ((DeviantArtAttributes) DeviantArtImageFragment.this.getImage().attr()).getSimilarImages() != null) {
                    DeviantArtImageFragment.this.homeGalleryList.initialize(image, new C12741(), DeviantArtImageFragment.this.getViewer().getMultiSelect(), DeviantArtImageFragment.SUGGESTABLE);
                }
            }
        }
    }

    static {
        REGEX_URL = DeviantArtUserFragment.REGEX_BASE + "/art/.*\\-([0-9]*)";
        BREADCRUMB_PARENT_CLASS = DeviantArtUserFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new DeviantArtImageProducer();
    }

    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbList) {
        List<Breadcrumb> breadcrumbs = breadcrumbList.getChildren();
        ((Breadcrumb) breadcrumbs.get(1)).alter("http://" + GalleryViewerFragment.matchIdFromUrl(DeviantArtUserFragment.REGEX_URL, getImage().getLinkUrl()) + "." + DeviantArtFragment.ROOT_NAME);
        ((Breadcrumb) breadcrumbs.get(1)).setAlpha(GalleryViewer.PROGRESS_COMPLETED);
        super.setBreadcrumbs(breadcrumbList);
    }

    protected ViewGroup inflateMetadataLayout(LayoutInflater inflater) {
        ViewGroup metadata = (ViewGroup) inflater.inflate(C0065R.layout.deviantart_metadata, null);
        this.description = (WebView) metadata.findViewById(C0065R.id.deviant_description);
        this.description.setBackgroundColor(0);
        this.loading = metadata.findViewById(C0065R.id.metadata_loading);
        this.homeGalleryList = (HomeGalleryList) metadata.findViewById(C0065R.id.gallery_horizontal_list);
        metadata.findViewById(C0065R.id.similar_images_container).bringToFront();
        return metadata;
    }

    private GalleryImageFragment getThis() {
        return this;
    }

    protected void fillMetadataView() {
        if (getImage().attr() != null && getActivity() != null) {
            String htmlDescription = ((DeviantArtAttributes) getImage().attr()).getHtmlDescription();
            if (htmlDescription == null) {
                invalidateAlreadyRenderedMetadataFlag();
                return;
            }
            this.loading.setVisibility(8);
            GalleryImageFragment.loadWebViewHtml(htmlDescription, this.description);
            if (!(getImage().attr() == null || ((DeviantArtAttributes) getImage().attr()).getSimilarImages() == null)) {
                new Handler().postDelayed(new C00871(), 400);
            }
            getImageScrollView().fullScroll(33);
        }
    }

    public void onDestroyView() {
        murderWebview(this.description);
        super.onDestroyView();
    }
}
