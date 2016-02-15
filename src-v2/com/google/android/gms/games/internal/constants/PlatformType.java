/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.constants;

public final class PlatformType {
    private PlatformType() {
    }

    public static String bd(int n2) {
        switch (n2) {
            default: {
                throw new IllegalArgumentException("Unknown platform type: " + n2);
            }
            case 0: {
                return "ANDROID";
            }
            case 1: {
                return "IOS";
            }
            case 2: 
        }
        return "WEB_APP";
    }
}

