/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package com.crumby.impl.rule34paheal;

import android.view.ViewGroup;
import com.crumby.impl.rule34paheal.Rule34PahealProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class Rule34PahealFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "http://rule34.paheal.net";
    public static final int BREADCRUMB_ICON = 2130837682;
    public static final String BREADCRUMB_NAME = "rule34 paheal";
    public static final String DISPLAY_NAME = "rule34 paheal";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote("rule34.paheal.net");
    public static final String REGEX_URL = REGEX_BASE + "/?" + ".*";
    public static final String ROOT_NAME = "rule34.paheal.net";
    public static final int SEARCH_FORM_ID = 2130903103;
    public static final boolean SUGGESTABLE = true;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String getDisplayUrl(String string2) {
        if (string2.contains("?tags=")) {
            return string2.replace("?tags=", "/post/list" + "/");
        }
        String string3 = string2;
        if (string2.contains("/post/list")) return string3;
        return string2 + "/post/list";
    }

    @Override
    protected GalleryProducer createProducer() {
        return new Rule34PahealProducer();
    }

    @Override
    public String getDisplayUrl() {
        return Rule34PahealFragment.getDisplayUrl(this.getUrl());
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
        return "rule34 paheal";
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

