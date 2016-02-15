package com.crumby.impl.deviantart;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import java.util.ArrayList;

public class DeviantArtMoreLikeThisProducer extends DeviantArtProducer {
    protected String getDefaultUrl(int pageNumber) {
        return DeviantArtMoreLikeThisFragment.BASE_URL + GalleryViewerFragment.matchIdFromUrl(DeviantArtMoreLikeThisFragment.REGEX_URL, getHostUrl()) + "?offset=" + (pageNumber * 24);
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        ArrayList<GalleryImage> galleryImages = new ArrayList();
        getImagesFromWebPage(galleryImages, pageNumber);
        return galleryImages;
    }
}
