package com.crumby.impl.ehentai;

import com.crumby.impl.device.DeviceFragment;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class HentaiSequenceFragment extends GalleryImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final String BREADCRUMB_NAME = "s";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    static {
        BREADCRUMB_PARENT_CLASS = HentaiSubGalleryFragment.class;
        REGEX_URL = HentaiGalleryFragment.REGEX_BASE + "/s/" + ALPHANUMERIC_REPEATING + DeviceFragment.REGEX_BASE + NUMERIC_REPEATING + "-" + CAPTURE_NUMERIC_REPEATING + "/?";
    }

    protected GalleryProducer createProducer() {
        return new HentaiSequenceProducer();
    }
}
