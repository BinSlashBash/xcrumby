/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import com.google.android.gms.internal.cn;
import com.google.android.gms.internal.dj;
import com.google.android.gms.internal.du;
import com.google.android.gms.internal.dw;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public final class cm
extends cn.a {
    private String lh;
    private Context mContext;
    private String oD;
    private ArrayList<String> oE;

    public cm(String string2, ArrayList<String> arrayList, Context context, String string3) {
        this.oD = string2;
        this.oE = arrayList;
        this.lh = string3;
        this.mContext = context;
    }

    private void aX() {
        try {
            this.mContext.getClassLoader().loadClass("com.google.ads.conversiontracking.IAPConversionReporter").getDeclaredMethod("reportWithProductId", Context.class, String.class, String.class, Boolean.TYPE).invoke(null, new Object[]{this.mContext, this.oD, "", true});
            return;
        }
        catch (ClassNotFoundException var1_1) {
            dw.z("Google Conversion Tracking SDK 1.2.0 or above is required to report a conversion.");
            return;
        }
        catch (NoSuchMethodException var1_2) {
            dw.z("Google Conversion Tracking SDK 1.2.0 or above is required to report a conversion.");
            return;
        }
        catch (Exception var1_3) {
            dw.c("Fail to report a conversion.", var1_3);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected String a(String string2, HashMap<String, String> hashMap) {
        String string3;
        String string4 = this.mContext.getPackageName();
        try {
            string3 = this.mContext.getPackageManager().getPackageInfo((String)string4, (int)0).versionName;
        }
        catch (PackageManager.NameNotFoundException var7_5) {
            dw.c("Error to retrieve app version", (Throwable)var7_5);
            string3 = "";
        }
        long l2 = SystemClock.elapsedRealtime();
        long l3 = dj.bu().bw();
        Iterator<String> iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()) {
            String string5 = iterator.next();
            string2 = string2.replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", string5), String.format("$1%s$2", hashMap.get(string5)));
        }
        return string2.replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "sessionid"), String.format("$1%s$2", dj.qK)).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "appid"), String.format("$1%s$2", string4)).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "osversion"), String.format("$1%s$2", String.valueOf(Build.VERSION.SDK_INT))).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "sdkversion"), String.format("$1%s$2", this.lh)).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "appversion"), String.format("$1%s$2", string3)).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "timestamp"), String.format("$1%s$2", String.valueOf(l2 - l3))).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", "[^@]+"), String.format("$1%s$2", "")).replaceAll("@@", "@");
    }

    @Override
    public String getProductId() {
        return this.oD;
    }

    protected int j(int n2) {
        if (n2 == 0) {
            return 1;
        }
        if (n2 == 1) {
            return 2;
        }
        if (n2 == 4) {
            return 3;
        }
        return 0;
    }

    @Override
    public void recordPlayBillingResolution(int n2) {
        if (n2 == 0) {
            this.aX();
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("google_play_status", String.valueOf(n2));
        hashMap.put("sku", this.oD);
        hashMap.put("status", String.valueOf(this.j(n2)));
        for (String string2 : this.oE) {
            new du(this.mContext, this.lh, this.a(string2, hashMap)).start();
        }
    }

    @Override
    public void recordResolution(int n2) {
        if (n2 == 1) {
            this.aX();
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("status", String.valueOf(n2));
        hashMap.put("sku", this.oD);
        for (String string2 : this.oE) {
            new du(this.mContext, this.lh, this.a(string2, hashMap)).start();
        }
    }
}

