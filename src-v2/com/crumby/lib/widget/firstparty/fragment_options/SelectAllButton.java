/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.SelectAllOnClickListener;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.widget.firstparty.fragment_options.CustomToggleButton;
import java.util.List;

public class SelectAllButton
extends CustomToggleButton
implements GalleryConsumer {
    SelectAllOnClickListener clickListener;

    public SelectAllButton(Context context) {
        super(context);
    }

    public SelectAllButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SelectAllButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    @Override
    public boolean addImages(List<GalleryImage> list) {
        return false;
    }

    @Override
    public void finishLoading() {
    }

    public boolean isChecked() {
        if (this.clickListener == null) {
            return false;
        }
        return this.clickListener.isChecked();
    }

    @Override
    public void notifyDataSetChanged() {
    }

    public void setChecked(boolean bl2) {
        this.clickListener.setChecked(bl2);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.clickListener = (SelectAllOnClickListener)onClickListener;
    }

    @Override
    public void showError(Exception exception) {
    }

    public void toggle() {
        this.clickListener.toggle();
    }
}

