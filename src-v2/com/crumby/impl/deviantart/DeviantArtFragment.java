/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package com.crumby.impl.deviantart;

import android.view.ViewGroup;
import com.crumby.impl.deviantart.DeviantArtProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class DeviantArtFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "http://deviantart.com";
    public static final int BREADCRUMB_ICON = 2130837568;
    public static final String BREADCRUMB_NAME = "deviantArt";
    public static final String DISPLAY_NAME = "Deviantart";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote("deviantart.com");
    public static final String REGEX_URL = REGEX_BASE + "/?" + "(?:\\?.*)?";
    public static final String ROOT_NAME = "deviantart.com";
    public static final int SEARCH_FORM_ID = 2130903094;
    public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME, SHOW_IN_SEARCH);
    public static final boolean SUGGESTABLE = true;

    @Override
    protected GalleryProducer createProducer() {
        return new DeviantArtProducer();
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
        return "deviantArt";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

