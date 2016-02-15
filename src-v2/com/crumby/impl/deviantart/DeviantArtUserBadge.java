/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.crumby.impl.deviantart;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.GalleryImageView;

public class DeviantArtUserBadge
extends LinearLayout
implements GalleryImageView {
    private ImageView avatar;
    private TextView badge;
    private GalleryImage image;

    public DeviantArtUserBadge(Context context) {
        super(context);
    }

    public DeviantArtUserBadge(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DeviantArtUserBadge(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public void initialize(GalleryImage galleryImage) {
    }

    @Override
    public void update() {
    }
}

