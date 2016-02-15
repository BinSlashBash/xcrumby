/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.constants;

public final class RequestUpdateResultOutcome {
    private RequestUpdateResultOutcome() {
    }

    public static boolean isValid(int n2) {
        switch (n2) {
            default: {
                return false;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
        }
        return true;
    }
}

