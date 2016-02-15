/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.tbib;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.gelbooru.GelbooruImageProducer;
import com.crumby.impl.tbib.TbibFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class TbibImageFragment
extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "Gelbooru";
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_URL = TbibFragment.REGEX_BASE + "/index\\.php.*&id=([0-9]+)";
        BREADCRUMB_PARENT_CLASS = TbibFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new GelbooruImageProducer("http://tbib.org/", REGEX_URL, "http://tbib.org/index.php?page=dapi&s=post&q=index");
    }

    @Override
    protected String getTagUrl(String string2) {
        return "http://tbib.org/index.php?page=post&s=list&tags=" + Uri.encode((String)string2);
    }
}

