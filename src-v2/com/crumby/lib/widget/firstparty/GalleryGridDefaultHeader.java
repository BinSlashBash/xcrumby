/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.GalleryImageView;

public class GalleryGridDefaultHeader
extends LinearLayout
implements GalleryImageView {
    private TextView description;
    private GalleryImage image;
    private TextView title;

    public GalleryGridDefaultHeader(Context context) {
        super(context);
    }

    public GalleryGridDefaultHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GalleryGridDefaultHeader(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public void initialize(GalleryImage galleryImage) {
        this.image = galleryImage;
        galleryImage.addView(this);
        this.update();
    }

    protected void onDetachedFromWindow() {
        if (this.image != null) {
            this.image.removeView(this);
        }
        super.onDetachedFromWindow();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.title = (TextView)this.findViewById(2131492991);
        this.description = (TextView)this.findViewById(2131492992);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void update() {
        if (this.image == null) {
            return;
        }
        this.title.setText((CharSequence)this.image.getTitle());
        if (this.image.getTitle() != null && !this.image.getTitle().equals("")) {
            this.title.setVisibility(0);
        }
        this.description.setText((CharSequence)this.image.getDescription());
        if (this.image.getDescription() == null) return;
        if (this.image.getDescription().equals("")) return;
        this.description.setVisibility(0);
    }
}

