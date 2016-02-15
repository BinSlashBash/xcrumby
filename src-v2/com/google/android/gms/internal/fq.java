/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.os.Looper;
import android.text.TextUtils;

public final class fq {
    public static void a(boolean bl2, Object object) {
        if (!bl2) {
            throw new IllegalStateException(String.valueOf(object));
        }
    }

    public static /* varargs */ void a(boolean bl2, String string2, Object ... arrobject) {
        if (!bl2) {
            throw new IllegalArgumentException(String.format(string2, arrobject));
        }
    }

    public static void aj(String string2) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException(string2);
        }
    }

    public static void ak(String string2) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException(string2);
        }
    }

    public static String ap(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("Given String is empty or null");
        }
        return string2;
    }

    public static <T> T b(T t2, Object object) {
        if (t2 == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t2;
    }

    public static String b(String string2, Object object) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return string2;
    }

    public static void b(boolean bl2, Object object) {
        if (!bl2) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
    }

    public static <T> T f(T t2) {
        if (t2 == null) {
            throw new NullPointerException("null reference");
        }
        return t2;
    }

    public static void x(boolean bl2) {
        if (!bl2) {
            throw new IllegalStateException();
        }
    }

    public static void z(boolean bl2) {
        if (!bl2) {
            throw new IllegalArgumentException();
        }
    }
}

