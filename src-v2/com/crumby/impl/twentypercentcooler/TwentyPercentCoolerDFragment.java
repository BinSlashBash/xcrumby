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
import com.crumby.impl.BooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class TwentyPercentCoolerDFragment
extends GalleryGridFragment {
    public static final String API_ROOT = "http://twentypercentcooler.net/post/index.xml?";
    public static final String BASE_URL = "http://twentypercentcooler.net";
    public static final int BREADCRUMB_ICON = 2130837698;
    public static final String BREADCRUMB_NAME = "20% cooler";
    public static final String DISPLAY_NAME = "20% cooler";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote("twentypercentcooler.net") + ")";
    public static final String REGEX_URL = REGEX_BASE + ".*";
    public static final String ROOT_NAME = "twentypercentcooler.net";
    public static final int SEARCH_FORM_ID = 2130903055;
    public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME_FALSE, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL);
    public static final boolean SUGGESTABLE = true;

    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        super.applyGalleryImageChange(view, galleryImage, n2);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new BooruProducer("http://twentypercentcooler.net", TwentyPercentCoolerDFragment.class, true);
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
        return "20% cooler";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

