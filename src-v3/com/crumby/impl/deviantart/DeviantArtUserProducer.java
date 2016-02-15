package com.crumby.impl.deviantart;

import android.net.Uri;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import java.util.ArrayList;

public class DeviantArtUserProducer extends DeviantArtProducer {
    private String getUserId() {
        return GalleryViewerFragment.matchIdFromUrl(DeviantArtUserFragment.REGEX_URL, getHostUrl());
    }

    protected String getRssUrl(int pageNumber) {
        return "http://backend.deviantart.com/rss.xml?offset=" + (pageNumber * 60) + "&q=by:" + Uri.encode(getUserId());
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        ArrayList<GalleryImage> images = new ArrayList();
        getImagesFromReallyStupidSyndication(images, pageNumber);
        return images;
    }
}
