/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.crumby.lib.fragment;

import android.view.View;
import com.crumby.Analytics;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryListFragment;

public class DoubleClickRunnable
implements Runnable {
    private int i;
    private GalleryImage image;
    private final GalleryListFragment listFragment;
    private View view;
    private boolean waiting;

    public DoubleClickRunnable(GalleryListFragment galleryListFragment) {
        this.listFragment = galleryListFragment;
    }

    public boolean isWaiting() {
        return this.waiting;
    }

    @Override
    public void run() {
        Analytics.INSTANCE.newNavigationEvent("image click", "" + this.i + " " + this.image.getLinkUrl());
        this.listFragment.goToImage(this.view, this.image, this.i);
        this.waiting = false;
    }

    public void set(int n2, View view, GalleryImage galleryImage) {
        this.i = n2;
        this.view = view;
        this.image = galleryImage;
        this.waiting = true;
    }
}

