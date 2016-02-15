/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.sankakuchan;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.GalleryViewer;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.deviantart.DeviantArtAttributes;
import com.crumby.impl.sankakuchan.SankakuChanFragment;
import com.crumby.impl.sankakuchan.SankakuChanImageProducer;
import com.crumby.lib.ExtraAttributes;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.CachedProducer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.HomeGalleryList;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SankakuChanImageFragment
extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    protected HomeGalleryList homeGalleryList;

    static {
        REGEX_URL = SankakuChanFragment.REGEX_BASE + "/post/show/([0-9]+).*";
        BREADCRUMB_PARENT_CLASS = SankakuChanFragment.class;
    }

    @Override
    public boolean addImages(List<GalleryImage> list) {
        boolean bl2 = super.addImages(list);
        if (list.size() > 0) {
            list.remove(0);
            ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
            arrayList.addAll(list);
            this.getImage().setAttributes(new DeviantArtAttributes(arrayList));
        }
        return bl2;
    }

    @Override
    protected void alternateFillMetadata() {
        ArrayList<GalleryImage> arrayList;
        try {
            arrayList = ((DeviantArtAttributes)this.getImage().attr()).getSimilarImages();
        }
        catch (NullPointerException var1_2) {
            this.homeGalleryList.setVisibility(8);
            return;
        }
        if (arrayList == null || arrayList.isEmpty()) {
            this.homeGalleryList.setVisibility(8);
            return;
        }
        GalleryImage galleryImage = new GalleryImage("", null, "Similar Images");
        this.homeGalleryList.initialize(galleryImage, new CachedProducer(arrayList), this.getViewer().getMultiSelect(), true);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new SankakuChanImageProducer();
    }

    @Override
    protected void fillMetadataView() {
        if (this.reloading) {
            return;
        }
        super.fillMetadataView();
    }

    @Override
    protected int getBooruLayout() {
        return 2130903108;
    }

    @Override
    protected String getTagUrl(String string2) {
        return "https://chan.sankakucomplex.com?&tags=" + Uri.encode((String)string2.replace(" ", "_"));
    }

    @Override
    protected ViewGroup inflateMetadataLayout(LayoutInflater layoutInflater) {
        layoutInflater = super.inflateMetadataLayout(layoutInflater);
        this.homeGalleryList = (HomeGalleryList)layoutInflater.findViewById(2131492988);
        return layoutInflater;
    }
}

