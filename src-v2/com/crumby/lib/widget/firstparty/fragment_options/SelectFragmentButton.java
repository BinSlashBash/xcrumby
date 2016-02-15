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
import com.crumby.lib.fragment.adapter.ImageWrapper;
import com.crumby.lib.widget.firstparty.fragment_options.CustomToggleButton;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;

public class SelectFragmentButton
extends CustomToggleButton
implements View.OnClickListener {
    GalleryImage image;
    private MultiSelect multiSelect;

    public SelectFragmentButton(Context context) {
        super(context);
        this.setOnClickListener((View.OnClickListener)this);
    }

    public SelectFragmentButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setOnClickListener((View.OnClickListener)this);
    }

    public SelectFragmentButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.setOnClickListener((View.OnClickListener)this);
    }

    public void initialize(GalleryImage galleryImage, MultiSelect multiSelect) {
        this.image = galleryImage;
        this.setTag((Object)new ImageWrapper(galleryImage));
        this.multiSelect = multiSelect;
        this.setVisibility(0);
    }

    public boolean isChecked() {
        if (this.image == null) {
            return false;
        }
        return this.image.isChecked();
    }

    public void onClick(View view) {
        this.toggle();
    }

    public void setChecked(boolean bl2) {
        this.image.setChecked(bl2);
        if (this.image.isChecked()) {
            this.multiSelect.add(this.image);
            return;
        }
        this.multiSelect.remove(this.image);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void toggle() {
        boolean bl2 = !this.image.isChecked();
        this.setChecked(bl2);
    }
}

