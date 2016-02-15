/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.e621;

import com.crumby.impl.BooruPoolGalleryProducer;
import com.crumby.impl.e621.e621Fragment;
import com.crumby.impl.e621.e621PoolFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class e621PoolGalleryFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "https://e621.net/pool/show";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "pools";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = e621PoolFragment.REGEX_BASE + "/show/";
        REGEX_URL = REGEX_BASE + "([0-9]+)*";
        BREADCRUMB_PARENT_CLASS = e621PoolFragment.class;
    }

    public static String matchIdFromUrl(String string2) {
        return e621PoolGalleryFragment.matchIdFromUrl(REGEX_URL, string2);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new BooruPoolGalleryProducer("https://e621.net/pool/show", e621Fragment.class, REGEX_URL, "https://e621.net", false);
    }
}

