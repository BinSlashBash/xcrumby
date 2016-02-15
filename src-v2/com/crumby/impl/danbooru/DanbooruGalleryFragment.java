/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.danbooru;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.danbooru.DanbooruGalleryProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class DanbooruGalleryFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "http://danbooru.donmai.us";
    public static final int BREADCRUMB_ICON = 2130837565;
    public static final String BREADCRUMB_NAME = "danbooru";
    public static final String DISPLAY_NAME = "Danbooru - Anime & Manga";
    public static final String POSTS_URL = "http://danbooru.donmai.us/posts";
    public static final String REGEX_BASE = DanbooruGalleryFragment.buildBasicRegexBase("danbooru.donmai.us");
    public static final String REGEX_URL = REGEX_BASE + "/?" + "(?:\\?.*)?";
    public static final String ROOT_NAME = "danbooru.donmai.us";
    public static final int SEARCH_FORM_ID = 2130903055;
    public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL);
    public static final boolean SUGGESTABLE = true;

    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        this.postUrlChangeToBusWithProducer(galleryImage);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new DanbooruGalleryProducer();
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
        return "danbooru";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

