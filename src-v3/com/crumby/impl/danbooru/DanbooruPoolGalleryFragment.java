package com.crumby.impl.danbooru;

import android.view.View;
import com.crumby.impl.device.DeviceFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class DanbooruPoolGalleryFragment extends GalleryGridFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "pool";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    static {
        REGEX_URL = DanbooruPoolFragment.REGEX_BASE + DeviceFragment.REGEX_BASE + CAPTURE_NUMERIC_REPEATING + "/?";
        BREADCRUMB_PARENT_CLASS = DanbooruPoolFragment.class;
    }

    public void applyGalleryImageChange(View view, GalleryImage image, int position) {
        postUrlChangeToBusWithProducer(image);
    }

    protected GalleryProducer createProducer() {
        return new DanbooruPoolGalleryProducer();
    }

    public static String matchIdFromUrl(String url) {
        return GalleryViewerFragment.matchIdFromUrl(REGEX_URL, url);
    }
}
