package com.crumby.impl.danbooru;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class DanbooruGalleryFragment extends GalleryGridFragment {
    public static final String BASE_URL = "http://danbooru.donmai.us";
    public static final int BREADCRUMB_ICON = 2130837565;
    public static final String BREADCRUMB_NAME = "danbooru";
    public static final String DISPLAY_NAME = "Danbooru - Anime & Manga";
    public static final String POSTS_URL = "http://danbooru.donmai.us/posts";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "danbooru.donmai.us";
    public static final int SEARCH_FORM_ID = 2130903055;
    public static final SettingAttributes SETTING_ATTRIBUTES;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = GalleryViewerFragment.buildBasicRegexBase(ROOT_NAME);
        REGEX_URL = REGEX_BASE + "/?" + "(?:\\?.*)?";
        SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL);
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
        return "tags";
    }

    protected GalleryProducer createProducer() {
        return new DanbooruGalleryProducer();
    }

    public void applyGalleryImageChange(View view, GalleryImage image, int position) {
        postUrlChangeToBusWithProducer(image);
    }
}
