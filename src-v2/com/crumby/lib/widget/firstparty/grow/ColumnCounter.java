/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.widget.TextView
 */
package com.crumby.lib.widget.firstparty.grow;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.TextView;

public class ColumnCounter
extends TextView {
    private int columnMax;
    private int columnNormal;
    private boolean outOfBounds;

    public ColumnCounter(Context context) {
        super(context);
        this.columnMax = this.getResources().getColor(2131427501);
        this.columnNormal = this.getResources().getColor(2131427356);
    }

    public ColumnCounter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.columnMax = this.getResources().getColor(2131427501);
        this.columnNormal = this.getResources().getColor(2131427356);
    }

    public ColumnCounter(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.columnMax = this.getResources().getColor(2131427501);
        this.columnNormal = this.getResources().getColor(2131427356);
    }

    public void hide() {
        this.setVisibility(8);
    }

    public void indicateFree() {
        this.setTextColor(this.columnNormal);
        this.setPressed(false);
        this.outOfBounds = false;
    }

    public void indicateLimit() {
        this.setPressed(true);
        this.setTextColor(this.columnMax);
        this.outOfBounds = true;
    }

    public void set(int n2) {
        String string2 = "";
        for (int i2 = 0; i2 < n2; ++i2) {
            string2 = string2 + " \u25a0";
        }
        this.setText((CharSequence)(string2 + " "));
    }

    public void show() {
        this.setVisibility(0);
    }
}

