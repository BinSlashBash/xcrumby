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
import twowayview.TwoWayView;

public class GalleryHorizontalList extends RelativeLayout implements GalleryList, GalleryClickHandler {
    private ErrorView errorView;
    private Fragment fragment;
    private GalleryImage image;
    private TwoWayView list;
    private GalleryProducer producer;

    public GalleryHorizontalList(Context context) {
        super(context);
    }

    public GalleryHorizontalList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryHorizontalList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize(GalleryImage image, GalleryProducer producer, Fragment fragment) {
        this.image = image;
        this.producer = producer;
        this.fragment = fragment;
    }

    public void goToImage(View view, GalleryImage image, int i) {
    }

    public AdapterView getList() {
        return this.list;
    }

    public GalleryProducer getProducer() {
        return null;
    }

    public GalleryImage getImage() {
        return this.image;
    }

    public Bundle getArguments() {
        return null;
    }

    public boolean getUserVisibleHint() {
        return this.fragment.getUserVisibleHint();
    }

    public void indicateProgressChange(float completed) {
    }

    public void showError(DisplayError error, String reason, String url) {
        if (this.errorView != null) {
            this.errorView.show(error, reason, url);
        }
    }
}
