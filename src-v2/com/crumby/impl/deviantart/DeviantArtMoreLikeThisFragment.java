/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.deviantart;

import com.crumby.impl.deviantart.DeviantArtFragment;
import com.crumby.impl.deviantart.DeviantArtMoreLikeThisProducer;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import java.util.List;

public class DeviantArtMoreLikeThisFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "http://deviantart.com/morelikethis/";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837628;
    public static final String BREADCRUMB_NAME = "deviantArt";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "deviantart.com/morelikethis/";
    public static final String SUFFIX = "/morelikethis/";

    static {
        REGEX_URL = DeviantArtFragment.REGEX_BASE + "/morelikethis/" + "([0-9]*)";
        BREADCRUMB_PARENT_CLASS = DeviantArtFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new DeviantArtMoreLikeThisProducer();
    }

    @Override
    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbListModifier) {
        this.alterThisBreadcrumbText(breadcrumbListModifier.getChildren(), "more like this", GalleryViewerFragment.matchIdFromUrl(REGEX_URL, this.getUrl()));
        super.setBreadcrumbs(breadcrumbListModifier);
    }
}

