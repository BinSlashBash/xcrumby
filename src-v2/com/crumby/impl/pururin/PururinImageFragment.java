/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.pururin;

import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.pururin.PururinFragment;
import com.crumby.impl.pururin.PururinThumbsFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;

public class PururinImageFragment
extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    static {
        REGEX_URL = PururinFragment.REGEX_BASE + "/view/([0-9]+).*/" + CAPTURE_NUMERIC_REPEATING + "/.*";
        BREADCRUMB_PARENT_CLASS = PururinThumbsFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new UniversalImageProducer(){

            @Override
            protected String getBaseUrl() {
                return "http://pururin.com";
            }

            @Override
            protected String getRegexForMatchingId() {
                return PururinImageFragment.REGEX_URL;
            }

            @Override
            protected String getScriptName() {
                return PururinImageFragment.class.getSimpleName();
            }
        };
    }

    @Override
    protected String getTagUrl(String string2) {
        return null;
    }

}

