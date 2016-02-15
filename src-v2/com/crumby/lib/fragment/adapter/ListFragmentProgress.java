/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.widget.ImageView
 */
package com.crumby.lib.fragment.adapter;

import android.content.res.Resources;
import android.widget.ImageView;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.GalleryList;
import com.squareup.picasso.Callback;
import java.util.HashSet;
import java.util.Set;

class ListFragmentProgress {
    private static final int FETCHING = 10;
    private static final int NOT_FETCHING = 0;
    private boolean done = false;
    private GalleryList fragment;
    private int lastProgress;
    private Set<GalleryImage> loadingImages;
    private int maxProgress;
    private boolean stopped = false;

    public ListFragmentProgress(GalleryList galleryList) {
        this.fragment = galleryList;
        this.loadingImages = new HashSet<GalleryImage>();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void postProgress(int n2) {
        int n3;
        if (this.stopped) {
            return;
        }
        if (this.done) {
            n2 = 0;
        }
        if ((n3 = this.loadingImages.size() + n2) == this.lastProgress) return;
        CrDb.d("adapter", "Loading images:" + this.loadingImages.size() + " Fetching:" + n2 + "  Posting progress:" + n3);
        this.signal(n3);
        this.lastProgress = n3;
    }

    private void signal(int n2) {
        float f2;
        if (this.fragment == null) {
            this.fragment = null;
            this.done = true;
            return;
        }
        this.maxProgress = Math.max(n2, this.maxProgress);
        if (this.maxProgress == 0) {
            this.maxProgress = 1;
        }
        float f3 = f2 = 1.0f - (float)n2 / (float)this.maxProgress;
        if (f2 == 0.0f) {
            f3 = 0.05f;
        }
        this.fragment.indicateProgressChange(f3);
    }

    public void addLoadingImage(GalleryImage galleryImage) {
        this.loadingImages.add(galleryImage);
    }

    public void done() {
        this.signal(0);
        this.done = true;
    }

    public LoadingImageCallback getCallback(GalleryImage galleryImage, ImageView imageView) {
        return new LoadingImageCallback(galleryImage, imageView);
    }

    public void removeLoadingImage(GalleryImage galleryImage) {
        if (this.loadingImages.remove(galleryImage)) {
            this.postProgress(0);
        }
    }

    public void resume() {
        this.stopped = false;
    }

    public void signalFetching() {
        this.postProgress(10);
    }

    public void stop() {
        this.stopped = true;
        this.signal(0);
    }

    private class LoadingImageCallback
    implements Callback {
        GalleryImage image;
        ImageView thumbnail;

        public LoadingImageCallback(GalleryImage galleryImage, ImageView imageView) {
            this.image = galleryImage;
            this.thumbnail = imageView;
        }

        @Override
        public void onError(Exception exception) {
            ListFragmentProgress.this.removeLoadingImage(this.image);
            this.image = null;
            if (this.thumbnail.getResources() != null) {
                this.thumbnail.setBackgroundColor(this.thumbnail.getResources().getColor(2131427534));
            }
            this.thumbnail = null;
        }

        @Override
        public void onSuccess() {
            ListFragmentProgress.this.removeLoadingImage(this.image);
            this.thumbnail = null;
        }
    }

}

