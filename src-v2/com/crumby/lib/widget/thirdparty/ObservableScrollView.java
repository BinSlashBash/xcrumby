/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ScrollView
 */
package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import com.crumby.lib.widget.thirdparty.ScrollViewListener;

public class ObservableScrollView
extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ObservableScrollView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public void onFinishInflate() {
        this.setOverScrollMode(0);
    }

    protected void onScrollChanged(int n2, int n3, int n4, int n5) {
        super.onScrollChanged(n2, n3, n4, n5);
        if (this.scrollViewListener != null) {
            this.scrollViewListener.onScrollChanged(this, n2, n3, n4, n5);
        }
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }
}

