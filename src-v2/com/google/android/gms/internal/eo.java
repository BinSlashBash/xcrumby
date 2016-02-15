/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.text.TextUtils;

public final class eo {
    public static void W(String string2) throws IllegalArgumentException {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("Namespace cannot be null or empty");
        }
        if (string2.length() > 128) {
            throw new IllegalArgumentException("Invalid namespace length");
        }
        if (!string2.startsWith("urn:x-cast:")) {
            throw new IllegalArgumentException("Namespace must begin with the prefix \"urn:x-cast:\"");
        }
        if (string2.length() == "urn:x-cast:".length()) {
            throw new IllegalArgumentException("Namespace must begin with the prefix \"urn:x-cast:\" and have non-empty suffix");
        }
    }

    public static String X(String string2) {
        return "urn:x-cast:" + string2;
    }

    public static <T> boolean a(T t2, T t3) {
        if (t2 == null && t3 == null || t2 != null && t3 != null && t2.equals(t3)) {
            return true;
        }
        return false;
    }

    public static long b(double d2) {
        return (long)(1000.0 * d2);
    }

    public static double m(long l2) {
        return (double)l2 / 1000.0;
    }
}

