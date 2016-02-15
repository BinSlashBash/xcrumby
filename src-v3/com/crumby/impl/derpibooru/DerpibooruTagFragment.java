package com.crumby.impl.derpibooru;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import java.util.List;

public class DerpibooruTagFragment extends GalleryGridFragment {
    public static final String BASE_URL = "https://derpibooru.org/tags/";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    public static final String SUFFIX = "/tags/";

    static {
        REGEX_URL = DerpibooruFragment.REGEX_BASE + SUFFIX + "(.*)";
        BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
    }

    public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        DerpibooruFragment.addSearchQuery(getUrl(), breadcrumbs);
        super.setBreadcrumbs(breadcrumbs);
    }

    protected GalleryProducer createProducer() {
        return new DerpibooruProducer();
    }

    public void setImage(GalleryImage image) {
        image.setLinkUrl("https://derpibooru.org?search=" + GalleryViewerFragment.matchIdFromUrl(REGEX_URL, image.getLinkUrl()));
        super.setImage(image);
    }
}
