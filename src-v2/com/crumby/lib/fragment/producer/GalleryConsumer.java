/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.fragment.producer;

import com.crumby.lib.GalleryImage;
import java.util.List;

public interface GalleryConsumer {
    public boolean addImages(List<GalleryImage> var1);

    public void finishLoading();

    public void notifyDataSetChanged();

    public void showError(Exception var1);
}

