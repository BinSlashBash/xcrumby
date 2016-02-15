/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.idolchan;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.GalleryViewer;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.idolchan.SankakuChanFragment;
import com.crumby.impl.idolchan.SankakuChanImageProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
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
    private HomeGalleryList homeGalleryList;
    private ArrayList<GalleryImage> images;

    static {
        REGEX_URL = SankakuChanFragment.REGEX_BASE + "/post/show/([0-9]+).*";
        BREADCRUMB_PARENT_CLASS = SankakuChanFragment.class;
    }

    @Override
    public boolean addImages(List<GalleryImage> list) {
        boolean bl2 = super.addImages(list);
        if (list.size() > 0) {
            this.images = new ArrayList();
            this.images.addAll(list);
            this.images.remove(0);
        }
        return bl2;
    }

    @Override
    protected void alternateFillMetadata() {
        if (this.images == null || this.images.isEmpty()) {
            this.homeGalleryList.setVisibility(8);
            return;
        }
        GalleryImage galleryImage = new GalleryImage("", null, "Similar Images");
        this.homeGalleryList.initialize(galleryImage, new SingleGalleryProducer(){

            @Override
            protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> arrayList) throws Exception {
                arrayList.addAll(SankakuChanImageFragment.this.images);
            }
        }, this.getViewer().getMultiSelect(), true);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new SankakuChanImageProducer();
    }

    @Override
    protected int getBooruLayout() {
        return 2130903108;
    }

    @Override
    protected String getTagUrl(String string2) {
        return "https://idol.sankakucomplex.com?&tags=" + Uri.encode((String)string2.replace(" ", "_"));
    }

    @Override
    protected ViewGroup inflateMetadataLayout(LayoutInflater layoutInflater) {
        layoutInflater = super.inflateMetadataLayout(layoutInflater);
        this.homeGalleryList = (HomeGalleryList)layoutInflater.findViewById(2131492988);
        return layoutInflater;
    }

}

