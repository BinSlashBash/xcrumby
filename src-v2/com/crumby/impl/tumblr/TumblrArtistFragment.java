/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.tumblr;

import com.crumby.impl.tumblr.TumblrFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;
import java.util.regex.Pattern;

public class TumblrArtistFragment
extends TumblrFragment {
    public static final String ALT_SEARCH_BASE;
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837632;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;

    static {
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + TumblrArtistFragment.captureMinimumLength("[a-zA-Z0-9_\\+\\-]", 4) + "\\." + Pattern.quote("tumblr.com");
        REGEX_URL = REGEX_BASE + ".*";
        BREADCRUMB_PARENT_CLASS = TumblrFragment.class;
        ALT_SEARCH_BASE = REGEX_BASE;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new UniversalProducer(){

            @Override
            protected String getBaseUrl() {
                return "http://api.tumblr.com";
            }

            @Override
            protected String getRegexForMatchingId() {
                return TumblrArtistFragment.REGEX_URL;
            }

            @Override
            protected String getScriptName() {
                return TumblrArtistFragment.class.getSimpleName();
            }
        };
    }

}

