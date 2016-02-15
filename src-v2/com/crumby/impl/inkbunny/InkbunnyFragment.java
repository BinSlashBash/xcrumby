/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.crumby.impl.inkbunny;

import android.view.View;
import com.crumby.impl.inkbunny.InkbunnyProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import java.util.regex.Pattern;

public class InkbunnyFragment
extends GalleryGridFragment {
    public static final String API_ROOT = "https://inkbunny.net/api_";
    public static final String BASE_URL = "https://inkbunny.net";
    public static final int BREADCRUMB_ICON = 2130837659;
    public static final String BREADCRUMB_NAME = "inkbunny";
    public static final String DISPLAY_NAME = "Inkbunny";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote("inkbunny.net") + ")";
    public static final String REGEX_URL = REGEX_BASE + ".*";
    public static final String ROOT_NAME = "inkbunny.net";
    public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME_FALSE);
    public static final boolean SUGGESTABLE = true;

    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        super.applyGalleryImageChange(view, galleryImage, n2);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new InkbunnyProducer();
    }

    @Override
    public String getSearchArgumentName() {
        return "keywords";
    }

    @Override
    public String getSearchPrefix() {
        return "inkbunny";
    }
}

