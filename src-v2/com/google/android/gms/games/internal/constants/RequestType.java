/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.constants;

import com.google.android.gms.games.internal.GamesLog;

public final class RequestType {
    private RequestType() {
    }

    public static String bd(int n2) {
        switch (n2) {
            default: {
                GamesLog.h("RequestType", "Unknown request type: " + n2);
                return "UNKNOWN_TYPE";
            }
            case 1: {
                return "GIFT";
            }
            case 2: 
        }
        return "WISH";
    }
}

