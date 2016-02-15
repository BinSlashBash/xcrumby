/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 */
package com.crumby.impl.crumby;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.crumby.impl.crumby.CrumbyProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import java.util.List;

public class CrumbyGalleryFragment
extends GalleryGridFragment {
    public static final int BREADCRUMB_ICON = 2130837640;
    public static final String BREADCRUMB_NAME = "search results";
    public static final String REGEX_URL = ".*";
    public static final String URL_HOST = "http://bing.com/search?";

    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        this.postUrlChangeToBusWithProducer(galleryImage);
    }

    @Override
    protected void cleanLinkUrl() {
    }

    @Override
    protected GalleryProducer createProducer() {
        return new CrumbyProducer();
    }

    @Override
    protected void initializeAdapter() {
        ((CrumbyProducer)this.getProducer()).setContext(this.getActivity().getApplicationContext());
        super.initializeAdapter();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbListModifier) {
        String string2 = !this.getUrl().equals("") ? "\"" + this.getUrl() + "\"" : "search";
        List<Breadcrumb> list = breadcrumbListModifier.getChildren();
        list.get(0).setText((CharSequence)string2);
        list.get(0).invalidate();
        super.setBreadcrumbs(breadcrumbListModifier);
    }
}

