/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.derpibooru;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.derpibooru.DerpibooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.IndexField;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.List;
import java.util.regex.Pattern;

public class DerpibooruFragment
extends GalleryGridFragment {
    public static final int ACCOUNT_LAYOUT = 2130903057;
    public static final String ACCOUNT_TYPE = "derpibooru";
    public static final String API_ROOT = "https://derpibooru.org/images";
    public static final String BASE_URL = "https://derpibooru.org";
    public static final int BREADCRUMB_ICON = 2130837567;
    public static final String BREADCRUMB_NAME = "derpibooru";
    public static final String DISPLAY_NAME = "Derpibooru";
    public static final String REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + "(" + Pattern.quote("derpibooru.org") + "|" + Pattern.quote("derpiboo.ru") + ")";
    public static final String REGEX_URL = REGEX_BASE + ".*";
    public static final String ROOT_NAME = "derpibooru.org";
    public static final int SEARCH_FORM_ID = 2130903058;
    public static final SettingAttributes SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL);
    public static final boolean SUGGESTABLE = true;

    public static void addSearchQuery(String string2, List<Breadcrumb> list) {
        if ((string2 = GalleryProducer.getQueryParameter(Uri.parse((String)string2), string2, "sbq")) != null) {
            string2 = string2.replace(":", " ");
            list.get(0).setBreadcrumbText("derpibooru: " + string2);
        }
    }

    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        super.applyGalleryImageChange(view, galleryImage, n2);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new DerpibooruProducer();
    }

    @Override
    protected int getHeaderLayout() {
        return 2130903105;
    }

    @Override
    public String getSearchArgumentName() {
        return "sbq";
    }

    @Override
    public String getSearchPrefix() {
        return "derpibooru";
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void setBreadcrumbs(List<Breadcrumb> list) {
        DerpibooruFragment.addSearchQuery(this.getUrl(), list);
        super.setBreadcrumbs(list);
    }

    @Override
    public void setImage(GalleryImage galleryImage) {
        super.setImage(galleryImage);
        if (galleryImage.getLinkUrl().contains("derpibooru.org?sbq=")) {
            galleryImage.setLinkUrl(galleryImage.getLinkUrl().replace("derpibooru.org?sbq=", "derpibooru.org/search?sbq="));
        }
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }
}

