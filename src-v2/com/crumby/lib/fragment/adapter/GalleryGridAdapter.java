/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.widget.AbsListView
 *  android.widget.AdapterView
 *  android.widget.ImageView
 */
package com.crumby.lib.fragment.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.adapter.CropTransformation;
import com.crumby.lib.fragment.adapter.GalleryList;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.adapter.ListFragmentProgress;
import com.crumby.lib.widget.firstparty.grow.GrowGridView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

public class GalleryGridAdapter
extends GalleryListAdapter {
    private int currentScrollState;
    int lastVisibleItem;

    @Override
    protected int getColumnWidth() {
        return this.getList().getColumnWidth();
    }

    protected GrowGridView getList() {
        return (GrowGridView)super.getList();
    }

    @Override
    protected int getNumColumns() {
        if (this.getList().getNumColumns() != -1) {
            return this.getList().getNumColumns();
        }
        return this.getList().getCurrentColumns();
    }

    @Override
    public void initialize(GalleryList galleryList) {
        super.initialize(galleryList);
        this.imageWrapperId = 2130903082;
        this.padOnScroll = new Runnable(){

            @Override
            public void run() {
                GalleryGridAdapter.this.padBottom();
            }
        };
    }

    @Override
    protected boolean isInSequence() {
        if (this.getList().getNumColumns() == 1) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void loadThumbnailWithPicasso(GalleryImage galleryImage, ImageView imageView, int n2) {
        Object object = this.isInSequence() ? galleryImage.getImageUrl() : (GalleryGridFragment.THUMBNAIL_HEIGHT * this.getColumnWidth() > 60000 ? galleryImage.getThumbnailUrl() : galleryImage.getSmallThumbnailUrl());
        Object object2 = object;
        if (object == null) {
            object2 = galleryImage.getThumbnailUrl();
        }
        if (object2 == null || object2.equals("")) {
            return;
        }
        object2 = Picasso.with(imageView.getContext()).load((String)object2);
        if (!galleryImage.isSplit()) {
            if (!this.isInSequence()) {
                object2.resize(this.getColumnWidth(), GalleryGridFragment.THUMBNAIL_HEIGHT).centerCrop();
                object = object2;
            } else {
                int n3 = galleryImage.getWidth();
                object = object2;
                if (n3 != 0) {
                    int n4 = galleryImage.getHeight();
                    object = object2;
                    if (n4 != 0) {
                        int n5 = imageView.getResources().getDisplayMetrics().widthPixels;
                        int n6 = imageView.getResources().getDisplayMetrics().heightPixels;
                        n2 = n5;
                        int n7 = galleryImage.getHeight();
                        if (n3 > n5) {
                            double d2 = n3 / n4;
                            n7 = (int)((double)n5 * d2);
                        } else {
                            n2 = n3;
                        }
                        n7 = Math.min(n7, n6 * 2);
                        object = object2;
                        if (n7 > 0) {
                            object2.resize(n2, n7).centerInside();
                            object = object2;
                        }
                    }
                }
            }
        } else {
            object = object2.transform(new CropTransformation(galleryImage, n2));
        }
        object.error(2130837617).into(imageView, this.progress.getCallback(galleryImage, imageView));
    }

    @Override
    public void onScroll(AbsListView absListView, int n2, int n3, int n4) {
        super.onScroll(absListView, n2, n3, n4);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onScrollStateChanged(AbsListView absListView, int n2) {
        Context context = this.getGalleryList().getContext();
        int n3 = absListView.getFirstVisiblePosition();
        this.currentScrollState = n2;
        if (context != null && context instanceof GalleryViewer) {
            if (n3 == 0 || this.lastVisibleItem > n3) {
                ((GalleryViewer)context).showOmnibar();
            } else if (this.currentScrollState == 2) {
                ((GalleryViewer)context).hideOmnibar();
            }
            this.lastVisibleItem = n3;
        }
        super.onScrollStateChanged(absListView, n2);
    }

    @Override
    public int prepareHighlight(int n2) {
        return super.prepareHighlight(n2);
    }

}

