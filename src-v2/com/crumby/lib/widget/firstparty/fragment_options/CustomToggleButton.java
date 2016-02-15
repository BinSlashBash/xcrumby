/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ImageButton
 */
package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import com.crumby.lib.widget.firstparty.multiselect.ImageCheckable;

public abstract class CustomToggleButton
extends ImageButton
implements ImageCheckable {
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};

    public CustomToggleButton(Context context) {
        super(context);
    }

    public CustomToggleButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomToggleButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public int[] onCreateDrawableState(int n2) {
        int[] arrn = super.onCreateDrawableState(n2 + 2);
        if (this.isChecked()) {
            CustomToggleButton.mergeDrawableStates((int[])arrn, (int[])CHECKED_STATE_SET);
        }
        return arrn;
    }
}

