/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 */
package com.google.android.gms.analytics;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.google.android.gms.analytics.m;

class ae
implements m {
    private static Object sf = new Object();
    private static ae vH;
    private final Context mContext;

    protected ae(Context context) {
        this.mContext = context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ae cZ() {
        Object object = sf;
        synchronized (object) {
            return vH;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void n(Context context) {
        Object object = sf;
        synchronized (object) {
            if (vH == null) {
                vH = new ae(context);
            }
            return;
        }
    }

    public boolean C(String string2) {
        return "&sr".equals(string2);
    }

    protected String da() {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        return "" + displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public String getValue(String string2) {
        if (string2 == null || !string2.equals("&sr")) {
            return null;
        }
        return this.da();
    }
}

