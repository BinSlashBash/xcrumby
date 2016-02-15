package com.crumby.lib.widget.firstparty.multiselect;

import com.crumby.lib.GalleryImage;
import java.util.Collection;

public interface MultiSelect {
    void add(GalleryImage galleryImage);

    void add(Collection<GalleryImage> collection);

    boolean isOpen();

    void remove(Collection<GalleryImage> collection);

    boolean remove(GalleryImage galleryImage);
}
