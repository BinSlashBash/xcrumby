/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.webkit.WebView
 */
package com.crumby.impl.furaffinity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.crumby.impl.furaffinity.FurAffinityFragment;
import com.crumby.impl.furaffinity.FurAffinityUserFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.ImageComment;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;
import com.crumby.lib.widget.firstparty.ImageCommentsView;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import java.util.List;

public class FurAffinityImageFragment
extends GalleryImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String MATCH_USER_ID = ".*#(.*)";
    public static final String REGEX_URL;
    private ImageCommentsView commentsView;
    private WebView description;

    static {
        REGEX_URL = FurAffinityFragment.REGEX_BASE + "/view/([0-9]+)#?.*";
        BREADCRUMB_PARENT_CLASS = FurAffinityUserFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new UniversalImageProducer(){

            @Override
            protected String getBaseUrl() {
                return "http://www.furaffinity.net";
            }

            @Override
            protected String getRegexForMatchingId() {
                return FurAffinityImageFragment.REGEX_URL;
            }

            @Override
            public String getScriptName() {
                return FurAffinityImageFragment.class.getSimpleName();
            }
        };
    }

    @Override
    protected void fillMetadataView() {
        FurAffinityImageFragment.loadWebViewHtml(this.getImage().getDescription(), this.description);
        this.commentsView.initialize(this.getImage().getComments());
    }

    @Override
    protected ViewGroup inflateMetadataLayout(LayoutInflater layoutInflater) {
        layoutInflater = (ViewGroup)layoutInflater.inflate(2130903072, null);
        this.description = (WebView)layoutInflater.findViewById(2131492981);
        this.description.setBackgroundColor(0);
        this.commentsView = (ImageCommentsView)layoutInflater.findViewById(2131493021);
        return layoutInflater;
    }

    @Override
    public void onDestroyView() {
        this.murderWebview(this.description);
        super.onDestroyView();
    }

    @Override
    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbListModifier) {
        if (this.getUrl().matches(".*#(.*)")) {
            String string2 = FurAffinityImageFragment.matchIdFromUrl(".*#(.*)", this.getUrl());
            breadcrumbListModifier.getChildren().get(1).alter("http://furaffinity.net/user/" + string2);
        }
        super.setBreadcrumbs(breadcrumbListModifier);
    }

}

