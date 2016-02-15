package com.google.android.gms.internal;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.SystemClock;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.google.android.gms.internal.cn.C0856a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class cm extends C0856a {
    private String lh;
    private Context mContext;
    private String oD;
    private ArrayList<String> oE;

    public cm(String str, ArrayList<String> arrayList, Context context, String str2) {
        this.oD = str;
        this.oE = arrayList;
        this.lh = str2;
        this.mContext = context;
    }

    private void aX() {
        try {
            this.mContext.getClassLoader().loadClass("com.google.ads.conversiontracking.IAPConversionReporter").getDeclaredMethod("reportWithProductId", new Class[]{Context.class, String.class, String.class, Boolean.TYPE}).invoke(null, new Object[]{this.mContext, this.oD, UnsupportedUrlFragment.DISPLAY_NAME, Boolean.valueOf(true)});
        } catch (ClassNotFoundException e) {
            dw.m823z("Google Conversion Tracking SDK 1.2.0 or above is required to report a conversion.");
        } catch (NoSuchMethodException e2) {
            dw.m823z("Google Conversion Tracking SDK 1.2.0 or above is required to report a conversion.");
        } catch (Throwable e3) {
            dw.m817c("Fail to report a conversion.", e3);
        }
    }

    protected String m2990a(String str, HashMap<String, String> hashMap) {
        Object obj;
        String packageName = this.mContext.getPackageName();
        Object obj2 = UnsupportedUrlFragment.DISPLAY_NAME;
        try {
            obj = this.mContext.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (Throwable e) {
            dw.m817c("Error to retrieve app version", e);
            obj = obj2;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime() - dj.bu().bw();
        for (String str2 : hashMap.keySet()) {
            str = str.replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", new Object[]{str2}), String.format("$1%s$2", new Object[]{hashMap.get(str2)}));
        }
        return str.replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", new Object[]{"sessionid"}), String.format("$1%s$2", new Object[]{dj.qK})).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", new Object[]{"appid"}), String.format("$1%s$2", new Object[]{packageName})).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", new Object[]{"osversion"}), String.format("$1%s$2", new Object[]{String.valueOf(VERSION.SDK_INT)})).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", new Object[]{"sdkversion"}), String.format("$1%s$2", new Object[]{this.lh})).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", new Object[]{"appversion"}), String.format("$1%s$2", new Object[]{obj})).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", new Object[]{"timestamp"}), String.format("$1%s$2", new Object[]{String.valueOf(elapsedRealtime)})).replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", new Object[]{"[^@]+"}), String.format("$1%s$2", new Object[]{UnsupportedUrlFragment.DISPLAY_NAME})).replaceAll("@@", "@");
    }

    public String getProductId() {
        return this.oD;
    }

    protected int m2991j(int i) {
        return i == 0 ? 1 : i == 1 ? 2 : i == 4 ? 3 : 0;
    }

    public void recordPlayBillingResolution(int billingResponseCode) {
        if (billingResponseCode == 0) {
            aX();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("google_play_status", String.valueOf(billingResponseCode));
        hashMap.put("sku", this.oD);
        hashMap.put("status", String.valueOf(m2991j(billingResponseCode)));
        Iterator it = this.oE.iterator();
        while (it.hasNext()) {
            new du(this.mContext, this.lh, m2990a((String) it.next(), hashMap)).start();
        }
    }

    public void recordResolution(int resolution) {
        if (resolution == 1) {
            aX();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("status", String.valueOf(resolution));
        hashMap.put("sku", this.oD);
        Iterator it = this.oE.iterator();
        while (it.hasNext()) {
            new du(this.mContext, this.lh, m2990a((String) it.next(), hashMap)).start();
        }
    }
}
