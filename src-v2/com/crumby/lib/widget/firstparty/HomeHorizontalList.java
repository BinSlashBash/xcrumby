/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewPropertyAnimator
 *  android.widget.Button
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.BusProvider;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.events.UrlChangeEvent;
import com.crumby.lib.fragment.GalleryListFragment;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.fragment.adapter.ImageWrapper;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.List;

public class HomeHorizontalList
extends LinearLayout
implements GalleryConsumer,
View.OnClickListener {
    private GalleryImage image;
    private GalleryProducer producer;

    public HomeHorizontalList(Context context) {
        super(context);
    }

    public HomeHorizontalList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HomeHorizontalList(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private void clearLoading() {
    }

    @Override
    public boolean addImages(List<GalleryImage> button) {
        this.clearLoading();
        for (int i2 = 0; i2 < Math.min(button.size(), 7); ++i2) {
            View view = this.getChildAt(i2 + 1);
            GridImageWrapper gridImageWrapper = new GridImageWrapper(view, false);
            gridImageWrapper.clear();
            gridImageWrapper.set((GalleryImage)button.get(i2));
            view.setTag((Object)gridImageWrapper);
            view.setOnClickListener((View.OnClickListener)this);
            int n2 = GalleryListFragment.THUMBNAIL_HEIGHT;
            Picasso.with(this.getContext()).load(gridImageWrapper.getImage().getThumbnailUrl()).resize(n2, n2).centerCrop().into(gridImageWrapper.getImageView());
            this.addViewGrow(view, i2 * 400);
        }
        button.add((GalleryImage)this.image);
        button = new Button(this.getContext());
        button.setText((CharSequence)"See more...");
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                BusProvider.BUS.get().post(new UrlChangeEvent(HomeHorizontalList.this.image.getLinkUrl()));
            }
        });
        this.addView((View)button);
        return false;
    }

    public void addViewGrow(View view, long l2) {
        view.setVisibility(0);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.animate().scaleX(1.0f).scaleY(1.0f).setStartDelay(l2).setDuration(400);
    }

    @Override
    public void finishLoading() {
    }

    @Override
    public void notifyDataSetChanged() {
    }

    public void onClick(View object) {
        object = ((ImageWrapper)object.getTag()).getImage();
        this.producer.shareAndSetCurrentImageFocus(object.getPosition());
        BusProvider.BUS.get().post(new UrlChangeEvent(object.getLinkUrl(), this.producer));
    }

    public void setProducer(String string2, GalleryProducer galleryProducer) {
        this.producer = galleryProducer;
        this.image = new GalleryImage(string2);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(string2).getBreadcrumbName());
        this.image.setPath(arrayList);
        galleryProducer.initialize(this, this.image, null, true);
    }

    @Override
    public void showError(Exception exception) {
        this.clearLoading();
        View view = View.inflate((Context)this.getContext(), (int)2130903085, (ViewGroup)null);
        super.addView(view);
        this.addViewGrow(view, 0);
        ((TextView)this.findViewById(2131493017)).setText((CharSequence)exception.getMessage());
        int n2 = Math.min(this.getResources().getDisplayMetrics().widthPixels, this.getResources().getDisplayMetrics().heightPixels);
        view.measure(0, 0);
        if (view.getMeasuredWidth() > n2) {
            view.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(n2, -2));
        }
    }

}

