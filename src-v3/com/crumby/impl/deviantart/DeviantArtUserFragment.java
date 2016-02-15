package com.crumby.impl.deviantart;

import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class DeviantArtUserFragment extends GalleryGridFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837632;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String USER_REGEX;

    static {
        USER_REGEX = GalleryViewerFragment.captureMinimumLength(".", 4) + "\\." + DeviantArtFragment.ROOT_NAME;
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + USER_REGEX;
        REGEX_URL = REGEX_BASE + "/?";
        BREADCRUMB_PARENT_CLASS = DeviantArtFragment.class;
    }

    protected int getHeaderLayout() {
        return C0065R.layout.deviantart_user_header;
    }

    protected void setupHeaderLayout(ViewGroup header) {
        ((OmniformContainer) header.findViewById(C0065R.id.omniform_container)).showAsInGrid("http://deviantart.com?q=by:" + GalleryViewerFragment.matchIdFromUrl(REGEX_URL, getUrl()));
    }

    protected GalleryProducer createProducer() {
        return new DeviantArtUserProducer();
    }
}
