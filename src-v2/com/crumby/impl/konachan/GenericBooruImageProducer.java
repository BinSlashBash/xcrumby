/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.konachan;

import com.crumby.impl.konachan.GenericBooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import java.util.ArrayList;

public class GenericBooruImageProducer
extends SingleGalleryProducer {
    private final String apiUrl;
    private final String baseUrl;
    private final String regexUrl;

    public GenericBooruImageProducer(String string2, String string3, String string4) {
        this.regexUrl = string4;
        this.apiUrl = string2;
        this.baseUrl = string3;
    }

    @Override
    protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> arrayList) throws Exception {
        String string2 = GalleryViewerFragment.matchIdFromUrl(this.regexUrl, this.getHostUrl());
        string2 = this.apiUrl + "tags=id:" + string2;
        GenericBooruProducer.convertArrayToGalleryImages(this.baseUrl, GenericBooruImageProducer.fetchUrl(string2), arrayList);
    }
}

