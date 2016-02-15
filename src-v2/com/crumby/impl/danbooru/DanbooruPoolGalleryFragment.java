/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.crumby.impl.danbooru;

import android.view.View;
import com.crumby.impl.danbooru.DanbooruPoolFragment;
import com.crumby.impl.danbooru.DanbooruPoolGalleryProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class DanbooruPoolGalleryFragment
extends GalleryGridFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "pool";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    static {
        REGEX_URL = DanbooruPoolFragment.REGEX_BASE + "/" + CAPTURE_NUMERIC_REPEATING + "/?";
        BREADCRUMB_PARENT_CLASS = DanbooruPoolFragment.class;
    }

    public static String matchIdFromUrl(String string2) {
        return DanbooruPoolGalleryFragment.matchIdFromUrl(REGEX_URL, string2);
    }

    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        this.postUrlChangeToBusWithProducer(galleryImage);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new DanbooruPoolGalleryProducer();
    }
}

