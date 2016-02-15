package com.crumby.lib.fragment.producer;

import com.crumby.lib.GalleryImage;
import java.util.List;

public interface GalleryConsumer {
    boolean addImages(List<GalleryImage> list);

    void finishLoading();

    void notifyDataSetChanged();

    void showError(Exception exception);
}
