/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.crumby.impl.imgur;

import android.view.View;
import com.crumby.impl.imgur.ImgurFragment;
import com.crumby.impl.imgur.ImgurUserGalleryProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.util.regex.Pattern;

public class ImgurUserGalleryFragment
extends GalleryGridFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837632;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    public static final String REGEX_URL_1;
    public static final String REGEX_URL_2;

    static {
        REGEX_URL_1 = ImgurUserGalleryFragment.captureMinimumLength("[a-zA-Z0-9]", 4) + "\\." + Pattern.quote("imgur.com") + "(?:/.*)?";
        REGEX_URL_2 = Pattern.quote("imgur.com") + "/user/" + CAPTURE_ALPHANUMERIC_REPEATING + "/?";
        REGEX_URL = "(?:http://www.|https://www.|https://|http://|www.)?" + ImgurUserGalleryFragment.matchOne(REGEX_URL_1, REGEX_URL_2);
        BREADCRUMB_PARENT_CLASS = ImgurFragment.class;
    }

    public static String matchIdFromUrl(String string2) {
        return ImgurUserGalleryFragment.matchIdFromUrl(REGEX_URL, string2);
    }

    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        this.postUrlChangeToBusWithProducer(galleryImage);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new ImgurUserGalleryProducer();
    }
}

