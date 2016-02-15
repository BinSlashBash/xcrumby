/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.internal.dq;
import java.math.BigInteger;
import java.util.Locale;

public final class dn {
    private static final Object px = new Object();
    private static String qX;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String b(Context object, String string2, String string3) {
        Object object2 = px;
        synchronized (object2) {
            if (qX != null) return qX;
            if (TextUtils.isEmpty((CharSequence)string2)) return qX;
            dn.c((Context)object, string2, string3);
            return qX;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String bx() {
        Object object = px;
        synchronized (object) {
            return qX;
        }
    }

    private static void c(Context object, String object2, String object3) {
        Class class_;
        String[] arrstring;
        try {
            object3 = object.createPackageContext((String)object3, 3).getClassLoader();
            class_ = Class.forName("com.google.ads.mediation.MediationAdapter", false, (ClassLoader)object3);
            object = new BigInteger(new byte[1]);
            arrstring = object2.split(",");
        }
        catch (Throwable var0_1) {
            qX = "err";
            return;
        }
        for (int i2 = 0; i2 < arrstring.length; ++i2) {
            object2 = object;
            if (dq.a((ClassLoader)object3, class_, arrstring[i2])) {
                object2 = object.setBit(i2);
            }
            object = object2;
        }
        qX = String.format(Locale.US, "%X", object);
    }
}

