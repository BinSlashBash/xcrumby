/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.konachan;

import com.crumby.impl.BooruPoolGalleryProducer;
import com.crumby.impl.konachan.KonachanFragment;
import com.crumby.impl.konachan.KonachanPoolFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class KonachanPoolGalleryFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "http://konachan.com/pool/show/";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "pools";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = KonachanPoolFragment.REGEX_BASE + "/show/";
        REGEX_URL = REGEX_BASE + "([0-9]+)*";
        BREADCRUMB_PARENT_CLASS = KonachanPoolFragment.class;
    }

    public static String matchIdFromUrl(String string2) {
        return KonachanPoolGalleryFragment.matchIdFromUrl(REGEX_URL, string2);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new BooruPoolGalleryProducer("http://konachan.com/pool/show/", KonachanFragment.class, REGEX_URL, "http://konachan.com", false);
    }
}

