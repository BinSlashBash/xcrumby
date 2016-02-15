/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.FrameLayout
 */
package com.crumby.lib.widget.firstparty.grow;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.widget.firstparty.multiselect.ImageCheckable;

public class ImagePressWrapper
extends FrameLayout
implements ImageCheckable {
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    private boolean checked;

    public ImagePressWrapper(Context context) {
        super(context);
    }

    public ImagePressWrapper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ImagePressWrapper(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public GalleryImage getImage() {
        return ((GridImageWrapper)this.getTag()).getImage();
    }

    public boolean isChecked() {
        return this.checked;
    }

    protected int[] onCreateDrawableState(int n2) {
        int[] arrn = super.onCreateDrawableState(n2 + 2);
        if (this.isChecked()) {
            ImagePressWrapper.mergeDrawableStates((int[])arrn, (int[])CHECKED_STATE_SET);
        }
        return arrn;
    }

    public void setChecked(boolean bl2) {
        this.checked = bl2;
        if (this.getImage() != null) {
            this.getImage().setChecked(bl2);
        }
        this.refreshDrawableState();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void toggle() {
        boolean bl2 = !this.checked;
        this.setChecked(bl2);
    }
}

