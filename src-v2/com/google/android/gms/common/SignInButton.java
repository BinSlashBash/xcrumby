/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.FrameLayout
 */
package com.google.android.gms.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.dynamic.g;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.fr;
import com.google.android.gms.internal.fs;

public final class SignInButton
extends FrameLayout
implements View.OnClickListener {
    public static final int COLOR_DARK = 0;
    public static final int COLOR_LIGHT = 1;
    public static final int SIZE_ICON_ONLY = 2;
    public static final int SIZE_STANDARD = 0;
    public static final int SIZE_WIDE = 1;
    private int Av;
    private View Aw;
    private View.OnClickListener Ax = null;
    private int mSize;

    public SignInButton(Context context) {
        this(context, null);
    }

    public SignInButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SignInButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.setStyle(0, 0);
    }

    private static Button a(Context context, int n2, int n3) {
        fs fs2 = new fs(context);
        fs2.a(context.getResources(), n2, n3);
        return fs2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void v(Context context) {
        if (this.Aw != null) {
            this.removeView(this.Aw);
        }
        try {
            this.Aw = fr.b(context, this.mSize, this.Av);
        }
        catch (g.a var2_2) {
            Log.w((String)"SignInButton", (String)"Sign in button not found, using placeholder instead");
            this.Aw = SignInButton.a(context, this.mSize, this.Av);
        }
        this.addView(this.Aw);
        this.Aw.setEnabled(this.isEnabled());
        this.Aw.setOnClickListener((View.OnClickListener)this);
    }

    public void onClick(View view) {
        if (this.Ax != null && view == this.Aw) {
            this.Ax.onClick((View)this);
        }
    }

    public void setColorScheme(int n2) {
        this.setStyle(this.mSize, n2);
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        this.Aw.setEnabled(bl2);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.Ax = onClickListener;
        if (this.Aw != null) {
            this.Aw.setOnClickListener((View.OnClickListener)this);
        }
    }

    public void setSize(int n2) {
        this.setStyle(n2, this.Av);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setStyle(int n2, int n3) {
        boolean bl2 = true;
        boolean bl3 = n2 >= 0 && n2 < 3;
        fq.a(bl3, "Unknown button size " + n2);
        bl3 = n3 >= 0 && n3 < 2 ? bl2 : false;
        fq.a(bl3, "Unknown color scheme " + n3);
        this.mSize = n2;
        this.Av = n3;
        this.v(this.getContext());
    }
}

