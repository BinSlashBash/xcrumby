/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.constants;

public final class TimeSpan {
    private TimeSpan() {
        throw new AssertionError((Object)"Uninstantiable");
    }

    public static String bd(int n2) {
        switch (n2) {
            default: {
                throw new IllegalArgumentException("Unknown time span " + n2);
            }
            case 0: {
                return "DAILY";
            }
            case 1: {
                return "WEEKLY";
            }
            case 2: 
        }
        return "ALL_TIME";
    }
}

