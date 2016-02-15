/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.derpibooru;

import com.crumby.impl.derpibooru.DerpibooruFragment;
import com.crumby.impl.derpibooru.DerpibooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import java.util.List;

public class DerpibooruTagFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "https://derpibooru.org/tags/";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    public static final String SUFFIX = "/tags/";

    static {
        REGEX_URL = DerpibooruFragment.REGEX_BASE + "/tags/" + "(.*)";
        BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new DerpibooruProducer();
    }

    @Override
    public void setBreadcrumbs(List<Breadcrumb> list) {
        DerpibooruFragment.addSearchQuery(this.getUrl(), list);
        super.setBreadcrumbs(list);
    }

    @Override
    public void setImage(GalleryImage galleryImage) {
        String string2 = GalleryViewerFragment.matchIdFromUrl(REGEX_URL, galleryImage.getLinkUrl());
        galleryImage.setLinkUrl("https://derpibooru.org?search=" + string2);
        super.setImage(galleryImage);
    }
}

