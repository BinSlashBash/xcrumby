package com.crumby.impl.yandere;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.BooruPoolProducer;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class YanderePoolFragment extends GalleryGridFragment {
    public static final String BASE_URL = "https://yande.re/pool";
    public static final String BREADCRUMB_NAME = "pools";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "Yandere Pools";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final int SEARCH_FORM_ID = 2130903064;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = YandereFragment.REGEX_BASE + "/pool";
        REGEX_URL = REGEX_BASE + CrumbyGalleryFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = YandereFragment.class;
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
        return "query";
    }

    public void applyGalleryImageChange(View view, GalleryImage image, int position) {
        postUrlChangeToBusWithProducer(image);
    }

    protected GalleryProducer createProducer() {
        return new BooruPoolProducer(BASE_URL, YandereFragment.class);
    }

    public static String matchIdFromUrl(String url) {
        return GalleryViewerFragment.matchIdFromUrl(REGEX_URL, url);
    }
}
