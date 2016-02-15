/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageButton
 */
package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.download.ImageDownloadManager;

public class SaveImageButton
extends ImageButton
implements View.OnClickListener {
    private GalleryImage image;

    public SaveImageButton(Context context) {
        super(context);
    }

    public SaveImageButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SaveImageButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public void hide() {
        this.setVisibility(8);
    }

    public void initialize(GalleryImage galleryImage) {
        this.image = galleryImage;
        this.setOnClickListener((View.OnClickListener)this);
        this.render();
    }

    public void onClick(View view) {
        this.save();
    }

    public void render() {
        if (this.image.getImageUrl() == null) {
            this.hide();
            return;
        }
        this.show();
    }

    public void save() {
        ImageDownloadManager.INSTANCE.downloadOne(this.image);
    }

    public void show() {
        this.setVisibility(0);
    }
}

