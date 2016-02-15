package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import java.math.BigInteger;
import java.util.Locale;

public final class dn {
    private static final Object px;
    private static String qX;

    static {
        px = new Object();
    }

    public static String m771b(Context context, String str, String str2) {
        String str3;
        synchronized (px) {
            if (qX == null && !TextUtils.isEmpty(str)) {
                m772c(context, str, str2);
            }
            str3 = qX;
        }
        return str3;
    }

    public static String bx() {
        String str;
        synchronized (px) {
            str = qX;
        }
        return str;
    }

    private static void m772c(Context context, String str, String str2) {
        try {
            ClassLoader classLoader = context.createPackageContext(str2, 3).getClassLoader();
            Class cls = Class.forName("com.google.ads.mediation.MediationAdapter", false, classLoader);
            BigInteger bigInteger = new BigInteger(new byte[1]);
            String[] split = str.split(",");
            BigInteger bigInteger2 = bigInteger;
            for (int i = 0; i < split.length; i++) {
                if (dq.m785a(classLoader, cls, split[i])) {
                    bigInteger2 = bigInteger2.setBit(i);
                }
            }
            qX = String.format(Locale.US, "%X", new Object[]{bigInteger2});
        } catch (Throwable th) {
            qX = "err";
        }
    }
}
