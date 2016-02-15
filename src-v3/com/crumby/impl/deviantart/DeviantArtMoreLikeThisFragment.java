package com.crumby.impl.deviantart;

import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;

public class DeviantArtMoreLikeThisFragment extends GalleryGridFragment {
    public static final String BASE_URL = "http://deviantart.com/morelikethis/";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837628;
    public static final String BREADCRUMB_NAME = "deviantArt";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "deviantart.com/morelikethis/";
    public static final String SUFFIX = "/morelikethis/";

    static {
        REGEX_URL = DeviantArtFragment.REGEX_BASE + SUFFIX + "([0-9]*)";
        BREADCRUMB_PARENT_CLASS = DeviantArtFragment.class;
    }

    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbList) {
        alterThisBreadcrumbText(breadcrumbList.getChildren(), "more like this", GalleryViewerFragment.matchIdFromUrl(REGEX_URL, getUrl()));
        super.setBreadcrumbs(breadcrumbList);
    }

    protected GalleryProducer createProducer() {
        return new DeviantArtMoreLikeThisProducer();
    }
}
