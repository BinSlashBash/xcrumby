/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ImageButton
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.google.android.gms.internal.dv;

public final class ch
extends FrameLayout
implements View.OnClickListener {
    private final Activity nS;
    private final ImageButton oB;

    public ch(Activity activity, int n2) {
        super((Context)activity);
        this.nS = activity;
        this.setOnClickListener((View.OnClickListener)this);
        this.oB = new ImageButton((Context)activity);
        this.oB.setImageResource(17301527);
        this.oB.setBackgroundColor(0);
        this.oB.setOnClickListener((View.OnClickListener)this);
        this.oB.setPadding(0, 0, 0, 0);
        n2 = dv.a((Context)activity, n2);
        this.addView((View)this.oB, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(n2, n2, 17));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void i(boolean bl2) {
        ImageButton imageButton = this.oB;
        int n2 = bl2 ? 4 : 0;
        imageButton.setVisibility(n2);
    }

    public void onClick(View view) {
        this.nS.finish();
    }
}

