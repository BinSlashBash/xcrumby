/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.ImageView
 */
package com.crumby.lib.widget.thirdparty.grid.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class DynamicHeightImageView
extends ImageView {
    private double mHeightRatio;

    public DynamicHeightImageView(Context context) {
        super(context);
    }

    public DynamicHeightImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public double getHeightRatio() {
        return this.mHeightRatio;
    }

    protected void onMeasure(int n2, int n3) {
        if (this.mHeightRatio > 0.0) {
            n2 = View.MeasureSpec.getSize((int)n2);
            this.setMeasuredDimension(n2, (int)((double)n2 * this.mHeightRatio));
            return;
        }
        super.onMeasure(n2, n3);
    }

    public void setHeightRatio(double d2) {
        if (d2 != this.mHeightRatio) {
            this.mHeightRatio = d2;
            this.requestLayout();
        }
    }
}

