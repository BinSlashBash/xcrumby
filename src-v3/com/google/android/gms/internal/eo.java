package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import android.text.TextUtils;

public final class eo {
    public static void m872W(String str) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Namespace cannot be null or empty");
        } else if (str.length() > TransportMediator.FLAG_KEY_MEDIA_NEXT) {
            throw new IllegalArgumentException("Invalid namespace length");
        } else if (!str.startsWith("urn:x-cast:")) {
            throw new IllegalArgumentException("Namespace must begin with the prefix \"urn:x-cast:\"");
        } else if (str.length() == "urn:x-cast:".length()) {
            throw new IllegalArgumentException("Namespace must begin with the prefix \"urn:x-cast:\" and have non-empty suffix");
        }
    }

    public static String m873X(String str) {
        return "urn:x-cast:" + str;
    }

    public static <T> boolean m874a(T t, T t2) {
        return (t == null && t2 == null) || !(t == null || t2 == null || !t.equals(t2));
    }

    public static long m875b(double d) {
        return (long) (1000.0d * d);
    }

    public static double m876m(long j) {
        return ((double) j) / 1000.0d;
    }
}
