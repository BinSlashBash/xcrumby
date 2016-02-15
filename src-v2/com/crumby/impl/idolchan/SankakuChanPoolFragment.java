/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package com.crumby.impl.idolchan;

import android.view.ViewGroup;
import com.crumby.impl.idolchan.SankakuChanFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class SankakuChanPoolFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "https://idol.sankakucomplex.com/pool";
    public static final String BREADCRUMB_NAME = "pools";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "pools";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final int SEARCH_FORM_ID = 2130903064;

    static {
        REGEX_BASE = SankakuChanFragment.REGEX_BASE + "/pool";
        REGEX_URL = REGEX_BASE + ".*";
        BREADCRUMB_PARENT_CLASS = SankakuChanFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new UniversalProducer(){

            @Override
            protected String getBaseUrl() {
                return "https://idol.sankakucomplex.com";
            }

            @Override
            protected String getRegexForMatchingId() {
                return null;
            }

            @Override
            protected String getScriptName() {
                return SankakuChanPoolFragment.class.getSimpleName();
            }
        };
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

