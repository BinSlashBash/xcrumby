/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.derpibooru;

import com.crumby.impl.derpibooru.DerpibooruFragment;
import com.crumby.impl.derpibooru.DerpibooruLoggedInUserProducer;
import com.crumby.impl.derpibooru.DerpibooruNeedsLoginFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class DerpibooruWatchedFragment
extends DerpibooruNeedsLoginFragment {
    public static final String BASE_URL = "https://derpibooru.org/images/watched";
    public static final int BREADCRUMB_ICON = 2130837628;
    public static final String BREADCRUMB_NAME = "my watched";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "My Derpibooru Watched Tags";
    public static final String REGEX_URL;
    public static final String SUFFIX = "/images/watched";
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_URL = DerpibooruFragment.REGEX_BASE + "/images/watched" + ".*";
        BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new DerpibooruLoggedInUserProducer(){

            @Override
            protected String getSuffix() {
                return "/images/watched";
            }
        };
    }

}

