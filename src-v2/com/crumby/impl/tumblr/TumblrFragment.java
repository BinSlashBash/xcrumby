/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package com.crumby.impl.tumblr;

import android.view.ViewGroup;
import com.crumby.impl.tumblr.TumblrProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class TumblrFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "http://tumblr.com";
    public static final int BREADCRUMB_ICON = 2130837697;
    public static final String BREADCRUMB_NAME = "tumblr";
    public static final String DISPLAY_NAME = "Tumblr";
    public static final String REGEX_BASE = TumblrFragment.buildBasicRegexBase("tumblr.com");
    public static final String REGEX_URL = REGEX_BASE + ".*";
    public static final String ROOT_NAME = "tumblr.com";
    public static final int SEARCH_FORM_ID = 2130903122;
    public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME, SHOW_IN_SEARCH);

    public static String getDisplayUrl(String string2) {
        String string3 = string2;
        if (string2.contains("tumblr.com?tagged=")) {
            string3 = string2.replace("tumblr.com?tagged=", "tumblr.com/tagged/");
        }
        return string3;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new TumblrProducer();
    }

    @Override
    public String getDisplayUrl() {
        return TumblrFragment.getDisplayUrl(this.getUrl());
    }

    @Override
    protected int getHeaderLayout() {
        return 2130903105;
    }

    @Override
    public String getSearchArgumentName() {
        return "tagged";
    }

    @Override
    public String getSearchPrefix() {
        return "tumblr";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

