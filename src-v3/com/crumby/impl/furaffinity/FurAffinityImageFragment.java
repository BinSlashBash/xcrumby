package com.crumby.impl.furaffinity;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.crumby.C0065R;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;
import com.crumby.lib.widget.firstparty.ImageCommentsView;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;

public class FurAffinityImageFragment extends GalleryImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String MATCH_USER_ID = ".*#(.*)";
    public static final String REGEX_URL;
    private ImageCommentsView commentsView;
    private WebView description;

    /* renamed from: com.crumby.impl.furaffinity.FurAffinityImageFragment.1 */
    class C14651 extends UniversalImageProducer {
        C14651() {
        }

        public String getScriptName() {
            return FurAffinityImageFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return "http://www.furaffinity.net";
        }

        protected String getRegexForMatchingId() {
            return FurAffinityImageFragment.REGEX_URL;
        }
    }

    static {
        REGEX_URL = FurAffinityFragment.REGEX_BASE + "/view/([0-9]+)#?.*";
        BREADCRUMB_PARENT_CLASS = FurAffinityUserFragment.class;
    }

    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbList) {
        if (getUrl().matches(MATCH_USER_ID)) {
            ((Breadcrumb) breadcrumbList.getChildren().get(1)).alter(FurAffinityUserFragment.BASE_URL + GalleryViewerFragment.matchIdFromUrl(MATCH_USER_ID, getUrl()));
        }
        super.setBreadcrumbs(breadcrumbList);
    }

    protected GalleryProducer createProducer() {
        return new C14651();
    }

    protected ViewGroup inflateMetadataLayout(LayoutInflater inflater) {
        ViewGroup view = (ViewGroup) inflater.inflate(C0065R.layout.furaffinity_metadata, null);
        this.description = (WebView) view.findViewById(C0065R.id.furaffinity_description);
        this.description.setBackgroundColor(0);
        this.commentsView = (ImageCommentsView) view.findViewById(C0065R.id.comments_view);
        return view;
    }

    protected void fillMetadataView() {
        GalleryImageFragment.loadWebViewHtml(getImage().getDescription(), this.description);
        this.commentsView.initialize(getImage().getComments());
    }

    public void onDestroyView() {
        murderWebview(this.description);
        super.onDestroyView();
    }
}
