/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.sankakuchan;

import com.crumby.impl.e621.e621PoolFragment;
import com.crumby.impl.sankakuchan.SankakuChanPoolFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;

public class SankakuChanPoolGalleryFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "https://chan.sankakucomplex.com/pool/show";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "pools";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = SankakuChanPoolFragment.REGEX_BASE + "/show\\.xml\\?\\&id=";
        REGEX_URL = REGEX_BASE + "([0-9]+)*";
        BREADCRUMB_PARENT_CLASS = e621PoolFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new UniversalProducer(){

            @Override
            protected String getBaseUrl() {
                return "https://chan.sankakucomplex.com";
            }

            @Override
            protected String getRegexForMatchingId() {
                return SankakuChanPoolGalleryFragment.REGEX_URL;
            }

            @Override
            protected String getScriptName() {
                return SankakuChanPoolGalleryFragment.class.getSimpleName();
            }
        };
    }

}

