/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.view.View
 *  android.view.ViewPropertyAnimator
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemLongClickListener
 */
package com.crumby.lib.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.AdapterView;
import com.crumby.Analytics;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryClickHandler;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.widget.firstparty.grow.ImagePressWrapper;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;

public class ImageClickListener
implements AdapterView.OnItemClickListener,
AdapterView.OnItemLongClickListener {
    private static final String ACTION = "image click";
    private static final long WRAPPER_CLICK_TIME = 100;
    private final GalleryClickHandler clickHandler;
    private boolean disabled;
    private MultiSelect multiSelect;
    private final String source;

    public ImageClickListener(GalleryClickHandler galleryClickHandler, MultiSelect multiSelect, String string2) {
        this.multiSelect = multiSelect;
        this.clickHandler = galleryClickHandler;
        this.source = string2;
    }

    private void wiggleView(final View view) {
        view.animate().rotation(5.0f).setDuration(100).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator) {
                view.animate().rotation(-5.0f).setDuration(100).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                    public void onAnimationEnd(Animator animator) {
                        view.animate().rotation(0.0f).setDuration(100).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                            public void onAnimationEnd(Animator animator) {
                            }
                        });
                    }

                });
            }

        });
    }

    public void disable() {
        this.disabled = true;
    }

    public void enable() {
        this.disabled = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onItemClick(AdapterView<?> object, View view, int n2, long l2) {
        if (this.multiSelect.isOpen()) {
            this.onItemLongClick(object, view, n2, l2);
            return;
        }
        if (view.getTag() == null) return;
        {
            object = ((GridImageWrapper)view.getTag()).getImage();
            if (object == null) {
                Analytics.INSTANCE.newNavigationEvent("not loaded", "" + n2 + "");
                this.wiggleView(view);
                return;
            }
        }
        Analytics.INSTANCE.newNavigationEvent(this.source + " image click", "" + n2 + " " + object.getLinkUrl());
        this.clickHandler.goToImage(view, (GalleryImage)object, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onItemLongClick(AdapterView<?> object, View view, int n2, long l2) {
        if (!(view instanceof ImagePressWrapper)) {
            return false;
        }
        object = (ImagePressWrapper)view;
        if (this.disabled) return false;
        if (object.getImage() == null) return false;
        object.toggle();
        if (object.isChecked()) {
            this.multiSelect.add(object.getImage());
            return true;
        }
        this.multiSelect.remove(object.getImage());
        return true;
    }

}

