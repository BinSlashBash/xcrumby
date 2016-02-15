package com.crumby.impl.imgur;

import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class ImgurAlbumFragment extends ImgurBaseFragment {
    public static final String ALBUM_REGEX;
    public static final String BASE_URL = "http://imgur.com/a/";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "/a/";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "imgur.com/a/";
    public static final String SUFFIX = "/a/";

    static {
        ALBUM_REGEX = SUFFIX + CAPTURE_ALPHANUMERIC_REPEATING;
        REGEX_BASE = ImgurFragment.REGEX_BASE + ALBUM_REGEX;
        REGEX_URL = REGEX_BASE + "/?";
        BREADCRUMB_PARENT_CLASS = ImgurUserGalleryFragment.class;
    }

    public static String matchIdFromUrl(String url) {
        return GalleryViewerFragment.matchIdFromUrl(REGEX_URL, url);
    }

    protected GalleryProducer createProducer() {
        return new ImgurAlbumProducer();
    }
}
