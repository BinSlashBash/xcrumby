/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.LinearLayout
 */
package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.crumby.R;

public class BoundedLinearLayout
extends LinearLayout {
    private final int mBoundedHeight;
    private final int mBoundedWidth;

    public BoundedLinearLayout(Context context) {
        super(context);
        this.mBoundedWidth = 0;
        this.mBoundedHeight = 0;
    }

    public BoundedLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = this.getContext().obtainStyledAttributes(attributeSet, R.styleable.BoundedView);
        this.mBoundedWidth = context.getDimensionPixelSize(0, 0);
        this.mBoundedHeight = context.getDimensionPixelSize(1, 0);
        context.recycle();
    }

    protected void onMeasure(int n2, int n3) {
        int n4 = View.MeasureSpec.getSize((int)n2);
        int n5 = n2;
        if (this.mBoundedWidth > 0) {
            n5 = n2;
            if (this.mBoundedWidth < n4) {
                n2 = View.MeasureSpec.getMode((int)n2);
                n5 = View.MeasureSpec.makeMeasureSpec((int)this.mBoundedWidth, (int)n2);
            }
        }
        n4 = View.MeasureSpec.getSize((int)n3);
        n2 = n3;
        if (this.mBoundedHeight > 0) {
            n2 = n3;
            if (this.mBoundedHeight < n4) {
                n2 = View.MeasureSpec.getMode((int)n3);
                n2 = View.MeasureSpec.makeMeasureSpec((int)this.mBoundedHeight, (int)n2);
            }
        }
        super.onMeasure(n5, n2);
    }
}

