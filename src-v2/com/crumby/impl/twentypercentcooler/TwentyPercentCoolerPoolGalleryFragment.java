/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.twentypercentcooler;

import com.crumby.impl.BooruPoolGalleryProducer;
import com.crumby.impl.twentypercentcooler.TwentyPercentCoolerPoolFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class TwentyPercentCoolerPoolGalleryFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "http://twentypercentcooler.net/pool/show";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "pools";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = TwentyPercentCoolerPoolFragment.REGEX_BASE + "/show/";
        REGEX_URL = REGEX_BASE + "([0-9]+)*";
        BREADCRUMB_PARENT_CLASS = TwentyPercentCoolerPoolFragment.class;
    }

    public static String matchIdFromUrl(String string2) {
        return TwentyPercentCoolerPoolGalleryFragment.matchIdFromUrl(REGEX_URL, string2);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new BooruPoolGalleryProducer("http://twentypercentcooler.net/pool/show", TwentyPercentCoolerPoolFragment.class, REGEX_URL, "http://twentypercentcooler.net", true);
    }
}

