/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.xbooru;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.gelbooru.GelbooruImageProducer;
import com.crumby.impl.xbooru.XBooruFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class XBooruImageFragment
extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    static {
        REGEX_URL = XBooruFragment.REGEX_BASE + "/index\\.php.*&id=([0-9]+)";
        BREADCRUMB_PARENT_CLASS = XBooruFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new GelbooruImageProducer("https://xbooru.com", REGEX_URL, "https://xbooru.com//index.php?page=dapi&s=post&q=index");
    }

    @Override
    protected String getTagUrl(String string2) {
        return "https://xbooru.com/index.php?page=post&s=list&tags=" + Uri.encode((String)string2);
    }
}

