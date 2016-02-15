package com.crumby.impl.konachan;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.RestrictedBooruImageProducer;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class KonachanImageFragment extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "konachan";
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_URL = KonachanFragment.REGEX_BASE + "/post/show/([0-9]+).*";
        BREADCRUMB_PARENT_CLASS = KonachanFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new RestrictedBooruImageProducer(KonachanFragment.BASE_URL, REGEX_URL);
    }

    protected String getTagUrl(String tag) {
        return "http://konachan.com/post.json?&tags=" + Uri.encode(tag);
    }
}
