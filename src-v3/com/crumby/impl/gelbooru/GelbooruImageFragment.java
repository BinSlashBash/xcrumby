package com.crumby.impl.gelbooru;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class GelbooruImageFragment extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "Gelbooru";
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_URL = GelbooruFragment.REGEX_BASE + "/index\\.php.*&id=([0-9]+)";
        BREADCRUMB_PARENT_CLASS = GelbooruFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new GelbooruImageProducer("http://gelbooru.com/", REGEX_URL, GelbooruFragment.API_ROOT);
    }

    protected String getTagUrl(String tag) {
        return "http://gelbooru.com/index.php?page=post&s=list&tags=" + Uri.encode(tag);
    }
}
