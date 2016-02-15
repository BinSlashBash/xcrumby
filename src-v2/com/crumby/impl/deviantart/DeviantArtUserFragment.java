/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.deviantart;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.deviantart.DeviantArtFragment;
import com.crumby.impl.deviantart.DeviantArtUserProducer;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class DeviantArtUserFragment
extends GalleryGridFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837632;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String USER_REGEX;

    static {
        USER_REGEX = DeviantArtUserFragment.captureMinimumLength(".", 4) + "\\." + "deviantart.com";
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + USER_REGEX;
        REGEX_URL = REGEX_BASE + "/?";
        BREADCRUMB_PARENT_CLASS = DeviantArtFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new DeviantArtUserProducer();
    }

    @Override
    protected int getHeaderLayout() {
        return 2130903060;
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup.findViewById(2131493079)).showAsInGrid("http://deviantart.com?q=by:" + GalleryViewerFragment.matchIdFromUrl(REGEX_URL, this.getUrl()));
    }
}

