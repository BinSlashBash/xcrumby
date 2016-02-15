/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.content.Context
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.RelativeLayout
 */
package com.crumby.lib.widget.firstparty;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryClickHandler;
import com.crumby.lib.fragment.adapter.GalleryList;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.ErrorView;
import twowayview.TwoWayView;

public class GalleryHorizontalList
extends RelativeLayout
implements GalleryList,
GalleryClickHandler {
    private ErrorView errorView;
    private Fragment fragment;
    private GalleryImage image;
    private TwoWayView list;
    private GalleryProducer producer;

    public GalleryHorizontalList(Context context) {
        super(context);
    }

    public GalleryHorizontalList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GalleryHorizontalList(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    @Override
    public Bundle getArguments() {
        return null;
    }

    @Override
    public GalleryImage getImage() {
        return this.image;
    }

    @Override
    public AdapterView getList() {
        return this.list;
    }

    @Override
    public GalleryProducer getProducer() {
        return null;
    }

    @Override
    public boolean getUserVisibleHint() {
        return this.fragment.getUserVisibleHint();
    }

    @Override
    public void goToImage(View view, GalleryImage galleryImage, int n2) {
    }

    @Override
    public void indicateProgressChange(float f2) {
    }

    public void initialize(GalleryImage galleryImage, GalleryProducer galleryProducer, Fragment fragment) {
        this.image = galleryImage;
        this.producer = galleryProducer;
        this.fragment = fragment;
    }

    @Override
    public void showError(DisplayError displayError, String string2, String string3) {
        if (this.errorView != null) {
            this.errorView.show(displayError, string2, string3);
        }
    }
}

