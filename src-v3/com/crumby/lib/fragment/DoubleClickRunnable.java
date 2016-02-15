package com.crumby.lib.fragment;

import android.view.View;
import com.crumby.Analytics;
import com.crumby.lib.GalleryImage;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

public class DoubleClickRunnable implements Runnable {
    private int f2i;
    private GalleryImage image;
    private final GalleryListFragment listFragment;
    private View view;
    private boolean waiting;

    public DoubleClickRunnable(GalleryListFragment listFragment) {
        this.listFragment = listFragment;
    }

    public void set(int i, View view, GalleryImage image) {
        this.f2i = i;
        this.view = view;
        this.image = image;
        this.waiting = true;
    }

    public void run() {
        Analytics.INSTANCE.newNavigationEvent("image click", this.f2i + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.image.getLinkUrl());
        this.listFragment.goToImage(this.view, this.image, this.f2i);
        this.waiting = false;
    }

    public boolean isWaiting() {
        return this.waiting;
    }
}
