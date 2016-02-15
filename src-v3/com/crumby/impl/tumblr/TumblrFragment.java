package com.crumby.impl.tumblr;

import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class TumblrFragment extends GalleryGridFragment {
    public static final String BASE_URL = "http://tumblr.com";
    public static final int BREADCRUMB_ICON = 2130837697;
    public static final String BREADCRUMB_NAME = "tumblr";
    public static final String DISPLAY_NAME = "Tumblr";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "tumblr.com";
    public static final int SEARCH_FORM_ID = 2130903122;
    public static final SettingAttributes SETTING_ATTRIBUTES;

    static {
        REGEX_BASE = GalleryViewerFragment.buildBasicRegexBase(ROOT_NAME);
        REGEX_URL = REGEX_BASE + CrumbyGalleryFragment.REGEX_URL;
        SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME, SHOW_IN_SEARCH);
    }

    protected int getHeaderLayout() {
        return C0065R.layout.omniform_container;
    }

    protected void setupHeaderLayout(ViewGroup header) {
        ((OmniformContainer) header).showAsInGrid(getImage().getLinkUrl());
    }

    public String getSearchPrefix() {
        return BREADCRUMB_NAME;
    }

    public String getSearchArgumentName() {
        return "tagged";
    }

    public String getDisplayUrl() {
        return getDisplayUrl(getUrl());
    }

    public static String getDisplayUrl(String url) {
        if (url.contains("tumblr.com?tagged=")) {
            return url.replace("tumblr.com?tagged=", "tumblr.com/tagged/");
        }
        return url;
    }

    protected GalleryProducer createProducer() {
        return new TumblrProducer();
    }
}
