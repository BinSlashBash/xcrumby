/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.yandere;

import com.crumby.impl.BooruPoolGalleryProducer;
import com.crumby.impl.yandere.YandereFragment;
import com.crumby.impl.yandere.YanderePoolFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class YanderePoolGalleryFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "https://yande.re/pool/show";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "pools";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = YanderePoolFragment.REGEX_BASE + "/show/";
        REGEX_URL = REGEX_BASE + "([0-9]+)*";
        BREADCRUMB_PARENT_CLASS = YanderePoolFragment.class;
    }

    public static String matchIdFromUrl(String string2) {
        return YanderePoolGalleryFragment.matchIdFromUrl(REGEX_URL, string2);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new BooruPoolGalleryProducer("https://yande.re/pool/show", YandereFragment.class, REGEX_URL, "https://yande.re", false);
    }
}

