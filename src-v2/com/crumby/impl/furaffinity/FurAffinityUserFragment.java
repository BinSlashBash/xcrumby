/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.furaffinity;

import com.crumby.impl.furaffinity.FurAffinityFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;

public class FurAffinityUserFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "http://furaffinity.net/user/";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837632;
    public static final String BREADCRUMB_NAME = "furAffinity";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "furAffinity";
    public static final String REGEX_URL;
    public static final String SUFFIX = "/user/";

    static {
        REGEX_URL = FurAffinityFragment.REGEX_BASE + "/user/" + "(.*)";
        BREADCRUMB_PARENT_CLASS = FurAffinityFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new UniversalProducer(){

            @Override
            protected String getBaseUrl() {
                return "http://www.furaffinity.net";
            }

            @Override
            protected String getRegexForMatchingId() {
                return FurAffinityUserFragment.REGEX_URL;
            }

            @Override
            public String getScriptName() {
                return FurAffinityUserFragment.class.getSimpleName();
            }
        };
    }

}

