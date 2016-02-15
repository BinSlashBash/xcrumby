/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.ehentai;

import com.crumby.impl.ehentai.HentaiGalleryFragment;
import com.crumby.impl.ehentai.HentaiSubGalleryProducer;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class HentaiSubGalleryFragment
extends GalleryGridFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "g";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    static {
        REGEX_URL = HentaiGalleryFragment.REGEX_BASE + "/g/" + NUMERIC_REPEATING + "/" + CAPTURE_ALPHANUMERIC_REPEATING + "/?";
        BREADCRUMB_PARENT_CLASS = HentaiGalleryFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new HentaiSubGalleryProducer();
    }
}

