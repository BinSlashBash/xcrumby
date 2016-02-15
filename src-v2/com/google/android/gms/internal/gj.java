/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 */
package com.google.android.gms.internal;

import android.util.Base64;

public final class gj {
    public static String d(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        return Base64.encodeToString((byte[])arrby, (int)0);
    }

    public static String e(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        return Base64.encodeToString((byte[])arrby, (int)10);
    }
}

