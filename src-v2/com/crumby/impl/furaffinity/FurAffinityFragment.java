/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package com.crumby.impl.furaffinity;

import android.view.ViewGroup;
import com.crumby.impl.furaffinity.FurAffinityProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class FurAffinityFragment
extends GalleryGridFragment {
    public static final int ACCOUNT_LAYOUT = 2130903071;
    public static final String ACCOUNT_TYPE = "furaffinity";
    public static final String BASE_URL = "http://furaffinity.net";
    public static final int BREADCRUMB_ICON = 2130837588;
    public static final String BREADCRUMB_NAME = "furAffinity";
    public static final String DISPLAY_NAME = "furAffinity";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote("furaffinity.net") + ")";
    public static final String REGEX_URL = REGEX_BASE + ".*";
    public static final String ROOT_NAME = "furaffinity.net";
    public static final int SEARCH_FORM_ID = 2130903094;
    public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME_FALSE);
    public static final boolean SUGGESTABLE = true;

    @Override
    protected GalleryProducer createProducer() {
        return new FurAffinityProducer();
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
        return "furAffinity";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

