/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.gelbooru;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.gelbooru.GelbooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class GelbooruFragment
extends GalleryGridFragment {
    public static final String API_ROOT = "http://gelbooru.com/index.php?page=dapi&s=post&q=index";
    public static final String BASE_URL = "http://gelbooru.com";
    public static final int BREADCRUMB_ICON = 2130837590;
    public static final String BREADCRUMB_NAME = "gelbooru";
    public static final String DISPLAY_NAME = "Gelbooru";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote("gelbooru.com") + ")";
    public static final String REGEX_URL = REGEX_BASE + ".*";
    public static final String ROOT_NAME = "gelbooru.com";
    public static final String SAFE_API_ROOT = "http://gelbooru.com/index.php?page=dapi&s=post&q=index&tags=rating%3Asafe";
    public static final int SEARCH_FORM_ID = 2130903055;
    public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME_FALSE, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL);
    public static final boolean SUGGESTABLE = true;

    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        super.applyGalleryImageChange(view, galleryImage, n2);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new GelbooruProducer("http://gelbooru.com/", "http://gelbooru.com/index.php?page=dapi&s=post&q=index", "http://gelbooru.com/index.php?page=dapi&s=post&q=index&tags=rating%3Asafe", GelbooruFragment.class);
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
        return "gelbooru";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

