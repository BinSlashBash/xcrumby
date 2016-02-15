/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.widget.RelativeLayout
 */
package com.crumby.lib.widget.thirdparty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import java.util.LinkedList;
import java.util.Queue;

public class HorizontalFlowLayout
extends RelativeLayout {
    private Queue<View> lastMeasured = new LinkedList<View>();

    public HorizontalFlowLayout(Context context) {
        super(context);
    }

    public HorizontalFlowLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HorizontalFlowLayout(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        n5 = this.getPaddingLeft();
        n3 = this.getPaddingTop();
        int n6 = 0;
        int n7 = 0;
        while (n7 < this.getChildCount()) {
            View view = this.getChildAt(n7);
            int n8 = n6;
            int n9 = n5;
            int n10 = n3;
            if (view.getVisibility() != 8) {
                int n11;
                int n12 = view.getMeasuredWidth();
                int n13 = view.getMeasuredHeight();
                if (view.getLayoutParams() != null && view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
                    n10 = marginLayoutParams.leftMargin;
                    n9 = marginLayoutParams.rightMargin;
                    n8 = marginLayoutParams.topMargin;
                    n11 = marginLayoutParams.bottomMargin;
                } else {
                    n10 = 0;
                    n9 = 0;
                    n8 = 0;
                    n11 = 0;
                }
                if (n5 + n10 + n12 + n9 + this.getPaddingRight() > n4 - n2) {
                    n5 = this.getPaddingLeft();
                    n3 += n6;
                    n6 = n13 + n8 + n11;
                } else {
                    n6 = Math.max(n6, n8 + n13 + n11);
                }
                view.layout(n5 + n10, n3 + n8, n5 + n10 + n12, n3 + n8 + n13);
                n9 = n5 + (n10 + n12 + n9);
                n10 = n3;
                n8 = n6;
            }
            ++n7;
            n6 = n8;
            n5 = n9;
            n3 = n10;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        super.onMeasure(n2, n3);
        int n4 = View.MeasureSpec.getSize((int)n2);
        int n5 = View.MeasureSpec.getSize((int)n3);
        int n6 = this.getPaddingLeft();
        n2 = this.getPaddingTop();
        int n7 = 0;
        for (int i2 = 0; i2 < this.getChildCount(); ++i2) {
            View view = this.getChildAt(i2);
            int n8 = n7;
            int n9 = n6;
            int n10 = n2;
            if (view.getVisibility() != 8) {
                int n11;
                int n12 = view.getMeasuredWidth();
                int n13 = view.getMeasuredHeight();
                if (view.getLayoutParams() != null && view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    view = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
                    n10 = view.leftMargin;
                    n9 = view.rightMargin;
                    n11 = view.topMargin;
                    n8 = view.bottomMargin;
                } else {
                    n10 = 0;
                    n9 = 0;
                    n11 = 0;
                    n8 = 0;
                }
                if (n6 + n10 + n12 + n9 + this.getPaddingRight() > n4) {
                    n6 = this.getPaddingLeft();
                    n2 += n7;
                    n7 = n11 + n13 + n8;
                } else {
                    n7 = Math.max(n7, n11 + n13 + n8);
                }
                n9 = n6 + (n10 + n12 + n9);
                n10 = n2;
                n8 = n7;
            }
            n7 = n8;
            n6 = n9;
            n2 = n10;
        }
        n6 = n2 + (this.getPaddingBottom() + n7);
        if (View.MeasureSpec.getMode((int)n3) == 0) {
            n2 = n6;
        } else {
            n2 = n5;
            if (View.MeasureSpec.getMode((int)n3) == Integer.MIN_VALUE) {
                n2 = n5;
                if (n6 < n5) {
                    n2 = n6;
                }
            }
        }
        this.setMeasuredDimension(n4, n2);
    }
}

