package com.crumby.impl.imgur;

import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class ImgurFragment extends ImgurBaseFragment {
    public static final String BASE_URL = "http://imgur.com";
    public static final int BREADCRUMB_ICON = 2130837657;
    public static final String BREADCRUMB_NAME = "imgur";
    public static final String DISPLAY_NAME = "Imgur";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "imgur.com";
    public static final int SEARCH_FORM_ID = 2130903094;
    public static final SettingAttributes SETTING_ATTRIBUTES;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote(ROOT_NAME);
        REGEX_URL = REGEX_BASE + "/?" + "[^/]*";
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
        return "q";
    }

    protected GalleryProducer createProducer() {
        return new ImgurProducer();
    }
}
