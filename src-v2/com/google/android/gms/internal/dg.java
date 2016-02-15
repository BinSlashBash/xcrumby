/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ResolveInfo
 *  android.content.res.Resources
 *  android.media.AudioManager
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.Uri
 *  android.telephony.TelephonyManager
 *  android.util.DisplayMetrics
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.dv;
import java.util.Locale;

public final class dg {
    public final int pZ;
    public final boolean qa;
    public final boolean qb;
    public final String qc;
    public final String qd;
    public final boolean qe;
    public final boolean qf;
    public final boolean qg;
    public final String qh;
    public final String qi;
    public final int qj;
    public final int qk;
    public final int ql;
    public final int qm;
    public final int qn;
    public final int qo;
    public final float qp;
    public final int qq;
    public final int qr;

    /*
     * Enabled aggressive block sorting
     */
    public dg(Context context) {
        boolean bl2 = true;
        AudioManager audioManager = (AudioManager)context.getSystemService("audio");
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Locale locale = Locale.getDefault();
        PackageManager packageManager = context.getPackageManager();
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        this.pZ = audioManager.getMode();
        boolean bl3 = dg.a(packageManager, "geo:0,0?q=donuts") != null;
        this.qa = bl3;
        bl3 = dg.a(packageManager, "http://www.google.com") != null ? bl2 : false;
        this.qb = bl3;
        this.qc = telephonyManager.getNetworkOperator();
        this.qd = locale.getCountry();
        this.qe = dv.bC();
        this.qf = audioManager.isMusicActive();
        this.qg = audioManager.isSpeakerphoneOn();
        this.qh = locale.getLanguage();
        this.qi = dg.a(packageManager);
        this.qj = audioManager.getStreamVolume(3);
        this.qk = dg.a(context, connectivityManager, packageManager);
        this.ql = telephonyManager.getNetworkType();
        this.qm = telephonyManager.getPhoneType();
        this.qn = audioManager.getRingerMode();
        this.qo = audioManager.getStreamVolume(2);
        this.qp = displayMetrics.density;
        this.qq = displayMetrics.widthPixels;
        this.qr = displayMetrics.heightPixels;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int a(Context context, ConnectivityManager connectivityManager, PackageManager packageManager) {
        int n2 = -2;
        if (!dq.a(packageManager, context.getPackageName(), "android.permission.ACCESS_NETWORK_STATE")) return n2;
        context = connectivityManager.getActiveNetworkInfo();
        if (context == null) return -1;
        return context.getType();
    }

    private static ResolveInfo a(PackageManager packageManager, String string2) {
        return packageManager.resolveActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)string2)), 65536);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String a(PackageManager object) {
        ResolveInfo resolveInfo = dg.a((PackageManager)object, "market://details?id=com.google.android.gms.ads");
        if (resolveInfo == null) {
            return null;
        }
        resolveInfo = resolveInfo.activityInfo;
        if (resolveInfo == null) return null;
        try {
            if ((object = object.getPackageInfo(resolveInfo.packageName, 0)) == null) return null;
        }
        catch (PackageManager.NameNotFoundException var0_1) {
            return null;
        }
        return "" + object.versionCode + "." + resolveInfo.packageName;
    }
}

