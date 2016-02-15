/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewPropertyAnimator
 *  android.widget.ImageButton
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.crumby.lib.widget.firstparty.multiselect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.download.ImageDownloadManager;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import it.sephiroth.android.library.tooltip.TooltipManager;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MultiSelectBar
extends LinearLayout
implements View.OnClickListener,
MultiSelect {
    private boolean animating;
    private ImageButton bookmark;
    private ImageButton close;
    private TextView galleries;
    private int galleryCount;
    private int imageCount;
    private LinkedHashSet<GalleryImage> images;
    private ImageButton save;
    private ImageButton share;
    private GalleryViewer viewer;

    public MultiSelectBar(Context context) {
        super(context);
    }

    public MultiSelectBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MultiSelectBar(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private void countImages() {
        this.imageCount = 0;
        this.galleryCount = 0;
        Iterator<GalleryImage> iterator = this.images.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isALinkToGallery()) {
                ++this.galleryCount;
                continue;
            }
            ++this.imageCount;
        }
    }

    private void resetAnimation() {
        this.clearAnimation();
        this.setAlpha(1.0f);
        this.animating = false;
    }

    private void show() {
        if (this.images.isEmpty()) {
            return;
        }
        this.setVisibility(0);
        if (this.animating) {
            this.animating = false;
            this.setVisibility(0);
            this.resetAnimation();
            return;
        }
        this.animating = true;
        this.animate().alpha(1.0f).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator) {
                MultiSelectBar.this.animating = false;
            }
        });
    }

    private void updateText() {
        String string2;
        this.countImages();
        String string3 = "";
        if (this.galleryCount != 0) {
            string3 = string2 = "" + this.galleryCount + " galleries";
            if (this.imageCount != 0) {
                string3 = string2 + ", ";
            }
        }
        string2 = string3;
        if (this.imageCount != 0) {
            string2 = string3 + this.imageCount + " images";
        }
        this.galleries.setText((CharSequence)string2);
    }

    @Override
    public void add(GalleryImage galleryImage) {
        this.images.add(galleryImage);
        this.show();
        this.updateText();
    }

    @Override
    public void add(Collection<GalleryImage> collection) {
        this.images.addAll(collection);
        this.show();
        this.updateText();
    }

    public void clear() {
        if (this.images != null && this.images.isEmpty()) {
            return;
        }
        Iterator<GalleryImage> iterator = this.images.iterator();
        while (iterator.hasNext()) {
            iterator.next().setChecked(false);
        }
        this.images.clear();
        this.hide();
        this.viewer.redrawPage();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean hasImages() {
        if (this.images == null || this.images.isEmpty()) {
            return false;
        }
        return true;
    }

    public void hide() {
        if (this.animating) {
            this.setVisibility(8);
            this.resetAnimation();
            return;
        }
        this.animating = true;
        this.animate().alpha(0.0f).setDuration(300).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator) {
                MultiSelectBar.this.setVisibility(8);
                MultiSelectBar.this.animating = false;
            }
        });
    }

    public void initialize(GalleryViewer galleryViewer) {
        this.setVisibility(4);
        this.setAlpha(0.0f);
        this.images = new LinkedHashSet();
        this.galleries = (TextView)this.findViewById(2131493067);
        this.close = (ImageButton)this.findViewById(2131493066);
        this.close.setOnClickListener((View.OnClickListener)this);
        this.save = (ImageButton)this.findViewById(2131493068);
        this.share = (ImageButton)this.findViewById(2131493069);
        this.bookmark = (ImageButton)this.findViewById(2131493070);
        this.save.setOnClickListener((View.OnClickListener)this);
        this.share.setOnClickListener((View.OnClickListener)this);
        this.bookmark.setOnClickListener((View.OnClickListener)this);
        this.viewer = galleryViewer;
    }

    @Override
    public boolean isOpen() {
        if (this.getVisibility() == 0) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onClick(View view) {
        this.countImages();
        if (view == this.close) {
            this.clear();
            return;
        } else {
            if (view == this.save) {
                TooltipManager.getInstance(this.viewer).remove(0);
                TooltipManager.getInstance(this.viewer).remove(1);
                this.viewer.showOmnibar();
                ImageDownloadManager.INSTANCE.downloadAll(this.images);
                this.clear();
                return;
            }
            if (view == this.share || view != this.bookmark) return;
            return;
        }
    }

    @Override
    public void remove(Collection<GalleryImage> collection) {
        if (this.images.removeAll(collection)) {
            this.updateText();
            if (this.images.isEmpty()) {
                this.hide();
            }
        }
    }

    @Override
    public boolean remove(GalleryImage galleryImage) {
        if (this.images.remove(galleryImage)) {
            this.updateText();
            if (this.images.isEmpty()) {
                this.hide();
                return true;
            }
        }
        return false;
    }

}

