/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.analytics;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.m;

class g
implements m {
    private static Object sf = new Object();
    private static g ss;
    protected String so;
    protected String sp;
    protected String sq;
    protected String sr;

    protected g() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private g(Context object) {
        PackageManager packageManager = object.getPackageManager();
        this.sq = object.getPackageName();
        this.sr = packageManager.getInstallerPackageName(this.sq);
        String string2 = this.sq;
        Object var4_5 = null;
        Object object2 = string2;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(object.getPackageName(), 0);
            object = var4_5;
            object2 = string2;
            if (packageInfo != null) {
                object2 = string2;
                object2 = object = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                string2 = packageInfo.versionName;
                object2 = object;
                object = string2;
            }
        }
        catch (PackageManager.NameNotFoundException var1_2) {
            aa.w("Error retrieving package info: appName set to " + (String)object2);
            object = var4_5;
        }
        this.so = object2;
        this.sp = object;
    }

    public static g ca() {
        return ss;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void n(Context context) {
        Object object = sf;
        synchronized (object) {
            if (ss == null) {
                ss = new g(context);
            }
            return;
        }
    }

    public boolean C(String string2) {
        if ("&an".equals(string2) || "&av".equals(string2) || "&aid".equals(string2) || "&aiid".equals(string2)) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public String getValue(String string2) {
        if (string2 == null) {
            return null;
        }
        if (string2.equals("&an")) {
            return this.so;
        }
        if (string2.equals("&av")) {
            return this.sp;
        }
        if (string2.equals("&aid")) {
            return this.sq;
        }
        if (!string2.equals("&aiid")) return null;
        return this.sr;
    }
}

