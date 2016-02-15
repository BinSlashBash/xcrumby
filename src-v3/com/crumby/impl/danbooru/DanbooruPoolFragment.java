package com.crumby.impl.danbooru;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class DanbooruPoolFragment extends GalleryGridFragment {
    public static final String BASE_URL = "http://danbooru.donmai.us/pools";
    public static final String BREADCRUMB_NAME = "pools";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "Danbooru - Pools";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final int SEARCH_FORM_ID = 2130903054;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = DanbooruGalleryFragment.REGEX_BASE + "/pools";
        REGEX_URL = REGEX_BASE + CrumbyGalleryFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = DanbooruGalleryFragment.class;
    }

    public String getSearchPrefix() {
        return BREADCRUMB_NAME;
    }

    public String getSearchArgumentName() {
        return "search[name_matches]";
    }

    public void applyGalleryImageChange(View view, GalleryImage image, int position) {
        postUrlChangeToBusWithProducer(image);
    }

    protected int getHeaderLayout() {
        return C0065R.layout.omniform_container;
    }

    protected void setupHeaderLayout(ViewGroup header) {
        ((OmniformContainer) header).showAsInGrid(getImage().getLinkUrl());
    }

    protected GalleryProducer createProducer() {
        return new DanbooruPoolProducer();
    }

    public static String matchIdFromUrl(String url) {
        return GalleryViewerFragment.matchIdFromUrl(REGEX_URL, url);
    }
}
