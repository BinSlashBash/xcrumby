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
package com.crumby.impl.tumblr;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.impl.sankakuchan.SankakuChanImageFragment;
import com.crumby.impl.tumblr.TumblrArtistFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;
import com.crumby.lib.widget.HomeGalleryList;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import java.util.List;

public class TumblrImageFragment
extends SankakuChanImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    static {
        REGEX_URL = TumblrArtistFragment.REGEX_BASE + "/post/([0-9]+)" + "/?" + ".*";
        BREADCRUMB_PARENT_CLASS = TumblrArtistFragment.class;
    }

    private void alterBreadcrumbs(List<Breadcrumb> list) {
        if (list == null || list.size() < 2 || this.getImage() == null || this.getImage().getLinkUrl() == null) {
            return;
        }
        String string2 = this.getImage().getLinkUrl();
        string2 = GalleryViewerFragment.matchIdFromUrl(TumblrArtistFragment.REGEX_URL, string2);
        list.get(1).alter("http://" + string2 + "." + "tumblr.com");
    }

    @Override
    protected void alternateFillMetadata() {
        super.alternateFillMetadata();
        this.homeGalleryList.findViewById(2131492989).setVisibility(8);
    }

    @Override
    public View createFragmentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = super.createFragmentView(layoutInflater, viewGroup, bundle);
        layoutInflater.findViewById(2131492989).setVisibility(8);
        return layoutInflater;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new UniversalImageProducer(){

            @Override
            protected String getBaseUrl() {
                return "http://api.tumblr.com";
            }

            @Override
            protected String getRegexForMatchingId() {
                return TumblrImageFragment.REGEX_URL;
            }

            @Override
            protected String getScriptName() {
                return TumblrImageFragment.class.getSimpleName();
            }
        };
    }

    @Override
    protected String getTagUrl(String string2) {
        return "http://tumblr.com?tagged=" + Uri.encode((String)string2);
    }

    @Override
    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbListModifier) {
        this.alterBreadcrumbs(breadcrumbListModifier.getChildren());
        super.setBreadcrumbs(breadcrumbListModifier);
    }

    @Override
    public void setUserVisibleHint(boolean bl2) {
        super.setUserVisibleHint(bl2);
    }

}

