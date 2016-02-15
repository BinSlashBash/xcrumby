/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.view.View
 *  android.view.animation.Transformation
 */
package com.crumby.lib.widget;

import android.content.res.Resources;
import android.view.View;
import android.view.animation.Transformation;
import com.crumby.lib.widget.thirdparty.ResizeHeightAnimation;

public class PhotoViewContainerResize
extends ResizeHeightAnimation {
    private int diff;
    private final int endPaddingTop;
    private final int startPaddingTop;

    public PhotoViewContainerResize(View view, int n2) {
        super(view, n2);
        this.startPaddingTop = view.getPaddingTop();
        this.diff = (int)view.getResources().getDimension(2131165196);
        if (this.startPaddingTop == 0) {
            this.endPaddingTop = this.diff;
            return;
        }
        this.endPaddingTop = 0;
        this.diff = - this.diff;
    }

    @Override
    protected void applyTransformation(float f2, Transformation transformation) {
        int n2 = this.startPaddingTop;
        int n3 = (int)((float)this.diff * f2);
        this.getView().setPadding(this.getView().getPaddingLeft(), n2 + n3, this.getView().getPaddingRight(), this.getView().getPaddingBottom());
        super.applyTransformation(f2, transformation);
    }
}

