/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.twentypercentcooler;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.BooruImageProducer;
import com.crumby.impl.twentypercentcooler.TwentyPercentCoolerDFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class TwentyPercentCoolerImageFragment
extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_URL = TwentyPercentCoolerDFragment.REGEX_BASE + "/post/show/([0-9]+).*";
        BREADCRUMB_PARENT_CLASS = TwentyPercentCoolerDFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new BooruImageProducer("http://twentypercentcooler.net", REGEX_URL);
    }

    @Override
    protected String getTagUrl(String string2) {
        return "http://twentypercentcooler.net/post/index.xml?&tags=" + Uri.encode((String)string2);
    }
}

