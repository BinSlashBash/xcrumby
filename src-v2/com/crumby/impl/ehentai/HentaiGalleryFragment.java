/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package com.crumby.impl.ehentai;

import android.view.ViewGroup;
import com.crumby.impl.ehentai.HentaiGalleryProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class HentaiGalleryFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "http://g.e-hentai.org";
    public static final String BREADCRUMB_NAME = "e-hentai";
    public static final String DISPLAY_NAME = "E-hentai";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote("g.e-hentai.org");
    public static final String REGEX_URL = REGEX_BASE + ".*";
    public static final String ROOT_NAME = "g.e-hentai.org";
    public static final int SEARCH_FORM_ID = 2130903065;
    public static final boolean SUGGESTABLE = true;

    @Override
    protected GalleryProducer createProducer() {
        return new HentaiGalleryProducer();
    }

    @Override
    protected int getHeaderLayout() {
        return 2130903105;
    }

    @Override
    public String getSearchArgumentName() {
        return "f_search";
    }

    @Override
    public String getSearchPrefix() {
        return "e-hentai";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

