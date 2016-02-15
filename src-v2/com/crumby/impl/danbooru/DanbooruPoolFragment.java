/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.danbooru;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.danbooru.DanbooruGalleryFragment;
import com.crumby.impl.danbooru.DanbooruPoolProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class DanbooruPoolFragment
extends GalleryGridFragment {
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
        REGEX_URL = REGEX_BASE + ".*";
        BREADCRUMB_PARENT_CLASS = DanbooruGalleryFragment.class;
    }

    public static String matchIdFromUrl(String string2) {
        return DanbooruPoolFragment.matchIdFromUrl(REGEX_URL, string2);
    }

    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        this.postUrlChangeToBusWithProducer(galleryImage);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new DanbooruPoolProducer();
    }

    @Override
    protected int getHeaderLayout() {
        return 2130903105;
    }

    @Override
    public String getSearchArgumentName() {
        return "search[name_matches]";
    }

    @Override
    public String getSearchPrefix() {
        return "pools";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

