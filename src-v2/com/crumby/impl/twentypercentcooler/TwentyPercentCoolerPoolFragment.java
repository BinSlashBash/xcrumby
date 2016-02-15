/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.twentypercentcooler;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.BooruPoolProducer;
import com.crumby.impl.twentypercentcooler.TwentyPercentCoolerDFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class TwentyPercentCoolerPoolFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "http://twentypercentcooler.net/pool";
    public static final String BREADCRUMB_NAME = "pools";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "20%  cooler - Pools";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final int SEARCH_FORM_ID = 2130903064;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = TwentyPercentCoolerDFragment.REGEX_BASE + "/pool";
        REGEX_URL = REGEX_BASE + ".*";
        BREADCRUMB_PARENT_CLASS = TwentyPercentCoolerDFragment.class;
    }

    public static String matchIdFromUrl(String string2) {
        return TwentyPercentCoolerPoolFragment.matchIdFromUrl(REGEX_URL, string2);
    }

    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        this.postUrlChangeToBusWithProducer(galleryImage);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new BooruPoolProducer("http://twentypercentcooler.net/pool", TwentyPercentCoolerPoolFragment.class);
    }

    @Override
    protected int getHeaderLayout() {
        return 2130903105;
    }

    @Override
    public String getSearchArgumentName() {
        return "query";
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

