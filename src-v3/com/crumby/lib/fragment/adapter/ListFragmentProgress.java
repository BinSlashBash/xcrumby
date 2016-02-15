package com.crumby.lib.fragment.adapter;

import android.widget.ImageView;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.squareup.picasso.Callback;
import java.util.HashSet;
import java.util.Set;

class ListFragmentProgress {
    private static final int FETCHING = 10;
    private static final int NOT_FETCHING = 0;
    private boolean done;
    private GalleryList fragment;
    private int lastProgress;
    private Set<GalleryImage> loadingImages;
    private int maxProgress;
    private boolean stopped;

    private class LoadingImageCallback implements Callback {
        GalleryImage image;
        ImageView thumbnail;

        public LoadingImageCallback(GalleryImage image, ImageView thumbnail) {
            this.image = image;
            this.thumbnail = thumbnail;
        }

        public void onSuccess() {
            ListFragmentProgress.this.removeLoadingImage(this.image);
            this.thumbnail = null;
        }

        public void onError(Exception e) {
            ListFragmentProgress.this.removeLoadingImage(this.image);
            this.image = null;
            if (this.thumbnail.getResources() != null) {
                this.thumbnail.setBackgroundColor(this.thumbnail.getResources().getColor(C0065R.color.WrapperBackground));
            }
            this.thumbnail = null;
        }
    }

    public ListFragmentProgress(GalleryList fragment) {
        this.done = false;
        this.stopped = false;
        this.fragment = fragment;
        this.loadingImages = new HashSet();
    }

    private void postProgress(int fetching) {
        if (!this.stopped) {
            if (this.done) {
                fetching = 0;
            }
            int progress = this.loadingImages.size() + fetching;
            if (progress != this.lastProgress) {
                CrDb.m0d("adapter", "Loading images:" + this.loadingImages.size() + " Fetching:" + fetching + "  Posting progress:" + progress);
                signal(progress);
                this.lastProgress = progress;
            }
        }
    }

    public void stop() {
        this.stopped = true;
        signal(0);
    }

    private void signal(int progress) {
        if (this.fragment == null) {
            this.fragment = null;
            this.done = true;
            return;
        }
        this.maxProgress = Math.max(progress, this.maxProgress);
        if (this.maxProgress == 0) {
            this.maxProgress = 1;
        }
        float completed = GalleryViewer.PROGRESS_COMPLETED - (((float) progress) / ((float) this.maxProgress));
        if (completed == 0.0f) {
            completed = GalleryViewer.PROGRESS_STARTED;
        }
        this.fragment.indicateProgressChange(completed);
    }

    public void signalFetching() {
        postProgress(FETCHING);
    }

    public void done() {
        signal(0);
        this.done = true;
    }

    public void removeLoadingImage(GalleryImage image) {
        if (this.loadingImages.remove(image)) {
            postProgress(0);
        }
    }

    public void addLoadingImage(GalleryImage image) {
        this.loadingImages.add(image);
    }

    public LoadingImageCallback getCallback(GalleryImage image, ImageView thumbnail) {
        return new LoadingImageCallback(image, thumbnail);
    }

    public void resume() {
        this.stopped = false;
    }
}
