package com.crumby.impl.imgur;

import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class ImgurSubredditFragment extends ImgurBaseFragment {
    public static final Class ALIAS_CLASS;
    public static final String BASE_URL = "http://imgur.com/r/";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "/r/";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "imgur.com/r/";
    public static final String SUBREDDIT_REGEX;
    public static final String SUFFIX = "/r/";
    public static final boolean SUGGESTABLE = true;

    static {
        SUBREDDIT_REGEX = SUFFIX + CAPTURE_ALPHANUMERIC_WITH_EXTRAS_REPEATING;
        REGEX_BASE = ImgurFragment.REGEX_BASE + SUBREDDIT_REGEX;
        REGEX_URL = REGEX_BASE + "/?";
        BREADCRUMB_PARENT_CLASS = ImgurUserGalleryFragment.class;
        ALIAS_CLASS = ImgurAlbumFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new ImgurSubredditProducer();
    }

    public static String matchIdFromUrl(String url) {
        return GalleryViewerFragment.matchIdFromUrl(REGEX_URL, url);
    }
}
