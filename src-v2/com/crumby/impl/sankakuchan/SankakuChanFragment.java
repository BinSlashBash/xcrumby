/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package com.crumby.impl.sankakuchan;

import android.view.ViewGroup;
import com.crumby.impl.sankakuchan.SankakuChanProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class SankakuChanFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "https://chan.sankakucomplex.com";
    public static final int BREADCRUMB_ICON = 2130837684;
    public static final String BREADCRUMB_NAME = "sankaku";
    public static final String DISPLAY_NAME = "sankaku";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote("chan.sankakucomplex.com");
    public static final String REGEX_URL = REGEX_BASE + "/?" + "[^/]*";
    public static final String ROOT_NAME = "chan.sankakucomplex.com";
    public static final int SEARCH_FORM_ID = 2130903055;
    public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME_FALSE, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL);
    public static final boolean SUGGESTABLE = true;

    @Override
    protected GalleryProducer createProducer() {
        return new SankakuChanProducer();
    }

    @Override
    protected int getHeaderLayout() {
        return 2130903105;
    }

    @Override
    public String getSearchArgumentName() {
        return "tags";
    }

    @Override
    public String getSearchPrefix() {
        return "sankaku";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

