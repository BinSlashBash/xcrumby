/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package com.crumby.impl.rule34xxx;

import android.view.ViewGroup;
import com.crumby.impl.gelbooru.GelbooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class Rule34xxxFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "https://rule34.xxx";
    public static final int BREADCRUMB_ICON = 2130837683;
    public static final String BREADCRUMB_NAME = "rule34";
    public static final String DISPLAY_NAME = "rule34";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote("rule34.xxx");
    public static final String REGEX_URL = REGEX_BASE + "/?" + "[^/]*";
    public static final String ROOT_NAME = "rule34.xxx";
    public static final int SEARCH_FORM_ID = 2130903055;
    public static final boolean SUGGESTABLE = true;

    @Override
    protected GalleryProducer createProducer() {
        return new GelbooruProducer("https://rule34.xxx/", "https://rule34.xxx/index.php?page=dapi&s=post&q=index", "https://rule34.xxx/index.php?page=dapi&s=post&q=index", null);
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
        return "rule34";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

