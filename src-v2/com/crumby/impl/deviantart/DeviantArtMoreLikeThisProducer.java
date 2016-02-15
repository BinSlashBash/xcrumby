/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.deviantart;

import com.crumby.impl.deviantart.DeviantArtMoreLikeThisFragment;
import com.crumby.impl.deviantart.DeviantArtProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import java.util.ArrayList;

public class DeviantArtMoreLikeThisProducer
extends DeviantArtProducer {
    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        this.getImagesFromWebPage(arrayList, n2);
        return arrayList;
    }

    @Override
    protected String getDefaultUrl(int n2) {
        String string2 = GalleryViewerFragment.matchIdFromUrl(DeviantArtMoreLikeThisFragment.REGEX_URL, this.getHostUrl());
        return "http://deviantart.com/morelikethis/" + string2 + "?offset=" + n2 * 24;
    }
}

