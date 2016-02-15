/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package com.crumby.impl.imgur;

import android.view.ViewGroup;
import com.crumby.impl.imgur.ImgurBaseFragment;
import com.crumby.impl.imgur.ImgurProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class ImgurFragment
extends ImgurBaseFragment {
    public static final String BASE_URL = "http://imgur.com";
    public static final int BREADCRUMB_ICON = 2130837657;
    public static final String BREADCRUMB_NAME = "imgur";
    public static final String DISPLAY_NAME = "Imgur";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote("imgur.com");
    public static final String REGEX_URL = REGEX_BASE + "/?" + "[^/]*";
    public static final String ROOT_NAME = "imgur.com";
    public static final int SEARCH_FORM_ID = 2130903094;
    public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME, SHOW_IN_SEARCH);
    public static final boolean SUGGESTABLE = true;

    @Override
    protected GalleryProducer createProducer() {
        return new ImgurProducer();
    }

    @Override
    protected int getHeaderLayout() {
        return 2130903105;
    }

    @Override
    public String getSearchArgumentName() {
        return "q";
    }

    @Override
    public String getSearchPrefix() {
        return "imgur";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

