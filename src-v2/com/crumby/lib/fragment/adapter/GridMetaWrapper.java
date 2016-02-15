/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.RelativeLayout
 */
package com.crumby.lib.fragment.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class GridMetaWrapper
extends RelativeLayout {
    public GridMetaWrapper(Context context) {
        super(context);
    }

    public GridMetaWrapper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GridMetaWrapper(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public void setAlpha(float f2) {
        super.setAlpha(f2);
        for (int i2 = this.getChildCount() - 1; i2 >= 0; --i2) {
            this.getChildAt(i2).setAlpha(f2);
        }
    }
}

