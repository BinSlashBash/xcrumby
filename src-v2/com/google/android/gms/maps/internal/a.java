/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.maps.internal;

public final class a {
    public static Boolean a(byte by2) {
        switch (by2) {
            default: {
                return null;
            }
            case 1: {
                return Boolean.TRUE;
            }
            case 0: 
        }
        return Boolean.FALSE;
    }

    public static byte c(Boolean bl2) {
        if (bl2 != null) {
            if (bl2.booleanValue()) {
                return 1;
            }
            return 0;
        }
        return -1;
    }
}

