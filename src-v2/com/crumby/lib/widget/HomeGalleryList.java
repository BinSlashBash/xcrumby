/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 */
package com.crumby.lib.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.BusProvider;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.fragment.GalleryClickHandler;
import com.crumby.lib.fragment.ImageClickListener;
import com.crumby.lib.fragment.adapter.GalleryList;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.ErrorView;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import com.squareup.otto.Bus;
import twowayview.TwoWayView;

public class HomeGalleryList
extends LinearLayout
implements GalleryList,
GalleryClickHandler {
    private GalleryListAdapter adapter;
    private Button button;
    private boolean clicked;
    private boolean doNotStart;
    private ErrorView errorView;
    private boolean fetchOnVisible;
    private GalleryImage image;
    private TwoWayView list;
    private MultiSelect multiselect;
    private GalleryProducer producer;
    private View progress;

    public HomeGalleryList(Context context) {
        super(context);
    }

    public HomeGalleryList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HomeGalleryList(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public void cancel() {
        this.doNotStart = true;
        if (this.producer != null) {
            this.producer.haltDownload();
        }
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
        return this.producer;
    }

    @Override
    public boolean getUserVisibleHint() {
        return this.isShown();
    }

    @Override
    public void goToImage(View view, GalleryImage galleryImage, int n2) {
        if (this.clicked) {
            return;
        }
        this.clicked = true;
        this.producer.shareAndSetCurrentImageFocus(galleryImage.getPosition());
        BusProvider.BUS.get().post(new UrlChangeEvent(galleryImage.getLinkUrl(), this.producer));
    }

    @Override
    public void indicateProgressChange(float f2) {
        if (f2 == 1.0f) {
            this.progress.setVisibility(8);
            return;
        }
        this.progress.setVisibility(0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void initialize(final GalleryImage galleryImage, GalleryProducer galleryProducer, MultiSelect multiSelect, boolean bl2) {
        if (this.doNotStart) {
            return;
        }
        this.list = (TwoWayView)this.findViewById(2131492986);
        this.list.setOrientation(TwoWayView.Orientation.HORIZONTAL);
        this.button = (Button)this.findViewById(2131492989);
        if (galleryImage.hasIcon()) {
            this.button.setCompoundDrawablesWithIntrinsicBounds(galleryImage.getIcon(), 0, 0, 0);
        }
        if (galleryImage.getLinkUrl() == null) {
            this.button.setEnabled(false);
            this.button.setBackgroundDrawable(null);
        } else {
            this.button.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    Analytics.INSTANCE.newEvent(AnalyticsCategories.MISCELLANEOUS, "horizontal list image click", galleryImage.getLinkUrl());
                    BusProvider.BUS.get().post(new UrlChangeEvent(galleryImage.getLinkUrl()));
                }
            });
        }
        this.button.setText((CharSequence)galleryImage.getTitle());
        this.button.setVisibility(0);
        this.errorView = (ErrorView)this.findViewById(2131492959);
        this.progress = this.findViewById(2131492990);
        this.image = galleryImage;
        this.producer = galleryProducer;
        this.adapter = new GalleryListAdapter();
        this.adapter.initialize(this);
        if (bl2) {
            this.adapter.startFetchingAndThenFinish();
        }
        this.list.setAdapter((ListAdapter)this.adapter);
        this.list.setOnItemClickListener((AdapterView.OnItemClickListener)new ImageClickListener(this, multiSelect, "horizontal"));
    }

    protected void onDetachedFromWindow() {
        if (this.producer != null) {
            this.producer.removeConsumer(this.adapter);
            if (this.list != null) {
                this.list.setAdapter((ListAdapter)null);
            }
        }
        super.onDetachedFromWindow();
    }

    protected void onVisibilityChanged(View view, int n2) {
        super.onVisibilityChanged(view, n2);
    }

    @Override
    public void showError(DisplayError displayError, String string2, String string3) {
        if (this.errorView != null) {
            this.errorView.show(displayError, string2, string3);
        }
    }

    public void start() {
        this.adapter.startFetchingAndThenFinish();
    }

}

