/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.rule34paheal;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.rule34paheal.Rule34PahealFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;

public class Rule34PahealImageFragment
extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    static {
        REGEX_URL = Rule34PahealFragment.REGEX_BASE + "/post/view/([0-9]+).*";
        BREADCRUMB_PARENT_CLASS = Rule34PahealFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new UniversalImageProducer(){

            @Override
            protected String getBaseUrl() {
                return "http://rule34.paheal.net";
            }

            @Override
            protected String getRegexForMatchingId() {
                return Rule34PahealImageFragment.REGEX_URL;
            }

            @Override
            protected String getScriptName() {
                return Rule34PahealImageFragment.class.getSimpleName();
            }
        };
    }

    @Override
    protected String getTagUrl(String string2) {
        return "http://rule34.paheal.net?tags=" + Uri.encode((String)string2.replace(" ", "_"));
    }

}

