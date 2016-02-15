/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.deviantart;

import android.net.Uri;
import com.crumby.impl.deviantart.DeviantArtProducer;
import com.crumby.impl.deviantart.DeviantArtUserFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import java.util.ArrayList;

public class DeviantArtUserProducer
extends DeviantArtProducer {
    private String getUserId() {
        return GalleryViewerFragment.matchIdFromUrl(DeviantArtUserFragment.REGEX_URL, this.getHostUrl());
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        this.getImagesFromReallyStupidSyndication(arrayList, n2);
        return arrayList;
    }

    @Override
    protected String getRssUrl(int n2) {
        return "http://backend.deviantart.com/rss.xml?offset=" + n2 * 60 + "&q=by:" + Uri.encode((String)this.getUserId());
    }
}

