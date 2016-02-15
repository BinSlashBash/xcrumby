/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.WindowManager
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class ci
extends aj {
    private static final String ID = a.Q.toString();
    private final Context mContext;

    public ci(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    @Override
    public boolean jX() {
        return true;
    }

    @Override
    public d.a x(Map<String, d.a> displayMetrics) {
        displayMetrics = new DisplayMetrics();
        ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        int n2 = displayMetrics.widthPixels;
        int n3 = displayMetrics.heightPixels;
        return dh.r("" + n2 + "x" + n3);
    }
}

