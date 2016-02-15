package com.crumby.lib.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import com.crumby.Analytics;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.widget.firstparty.grow.ImagePressWrapper;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

public class ImageClickListener implements OnItemClickListener, OnItemLongClickListener {
    private static final String ACTION = "image click";
    private static final long WRAPPER_CLICK_TIME = 100;
    private final GalleryClickHandler clickHandler;
    private boolean disabled;
    private MultiSelect multiSelect;
    private final String source;

    /* renamed from: com.crumby.lib.fragment.ImageClickListener.1 */
    class C01031 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$v;

        /* renamed from: com.crumby.lib.fragment.ImageClickListener.1.1 */
        class C01021 extends AnimatorListenerAdapter {

            /* renamed from: com.crumby.lib.fragment.ImageClickListener.1.1.1 */
            class C01011 extends AnimatorListenerAdapter {
                C01011() {
                }

                public void onAnimationEnd(Animator animation) {
                }
            }

            C01021() {
            }

            public void onAnimationEnd(Animator animation) {
                C01031.this.val$v.animate().rotation(0.0f).setDuration(ImageClickListener.WRAPPER_CLICK_TIME).setListener(new C01011());
            }
        }

        C01031(View view) {
            this.val$v = view;
        }

        public void onAnimationEnd(Animator animation) {
            this.val$v.animate().rotation(-5.0f).setDuration(ImageClickListener.WRAPPER_CLICK_TIME).setListener(new C01021());
        }
    }

    public ImageClickListener(GalleryClickHandler clickHandler, MultiSelect multiSelect, String source) {
        this.multiSelect = multiSelect;
        this.clickHandler = clickHandler;
        this.source = source;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (this.multiSelect.isOpen()) {
            onItemLongClick(adapterView, view, i, l);
        } else if (view.getTag() != null) {
            GalleryImage image = ((GridImageWrapper) view.getTag()).getImage();
            if (image == null) {
                Analytics.INSTANCE.newNavigationEvent("not loaded", i + UnsupportedUrlFragment.DISPLAY_NAME);
                wiggleView(view);
                return;
            }
            Analytics.INSTANCE.newNavigationEvent(this.source + " image click", i + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + image.getLinkUrl());
            this.clickHandler.goToImage(view, image, i);
        }
    }

    private void wiggleView(View v) {
        v.animate().rotation(5.0f).setDuration(WRAPPER_CLICK_TIME).setListener(new C01031(v));
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (!(view instanceof ImagePressWrapper)) {
            return false;
        }
        ImagePressWrapper wrapper = (ImagePressWrapper) view;
        if (this.disabled || wrapper.getImage() == null) {
            return false;
        }
        wrapper.toggle();
        if (wrapper.isChecked()) {
            this.multiSelect.add(wrapper.getImage());
        } else {
            this.multiSelect.remove(wrapper.getImage());
        }
        return true;
    }

    public void disable() {
        this.disabled = true;
    }

    public void enable() {
        this.disabled = false;
    }
}
