/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.ehentai;

import com.crumby.impl.ehentai.HentaiGalleryFragment;
import com.crumby.impl.ehentai.HentaiSequenceProducer;
import com.crumby.impl.ehentai.HentaiSubGalleryFragment;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class HentaiSequenceFragment
extends GalleryImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final String BREADCRUMB_NAME = "s";
    public static final Class BREADCRUMB_PARENT_CLASS = HentaiSubGalleryFragment.class;
    public static final String REGEX_URL = HentaiGalleryFragment.REGEX_BASE + "/s/" + ALPHANUMERIC_REPEATING + "/" + NUMERIC_REPEATING + "-" + CAPTURE_NUMERIC_REPEATING + "/?";

    @Override
    protected GalleryProducer createProducer() {
        return new HentaiSequenceProducer();
    }
}

