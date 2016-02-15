/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.e621;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.BooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class e621Fragment
extends GalleryGridFragment {
    public static final String BASE_URL = "https://e621.net";
    public static final int BREADCRUMB_ICON = 2130837575;
    public static final String BREADCRUMB_NAME = "e621";
    public static final String DISPLAY_NAME = "e621";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote("e621.net") + ")";
    public static final String REGEX_URL = REGEX_BASE + ".*";
    public static final String ROOT_NAME = "e621.net";
    public static final int SEARCH_FORM_ID = 2130903055;
    public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME_FALSE, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL);
    public static final boolean SUGGESTABLE = true;

    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        super.applyGalleryImageChange(view, galleryImage, n2);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new BooruProducer("https://e621.net", e621Fragment.class);
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
        return "e621";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

