package com.crumby.impl.e621;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.BooruImageProducer;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class e621ImageFragment extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "e621";
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_URL = e621Fragment.REGEX_BASE + "/post/show/([0-9]+).*";
        BREADCRUMB_PARENT_CLASS = e621Fragment.class;
    }

    protected GalleryProducer createProducer() {
        return new BooruImageProducer(e621Fragment.BASE_URL, REGEX_URL);
    }

    protected String getTagUrl(String tag) {
        return "https://e621.net?&tags=" + Uri.encode(tag);
    }
}
