/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.constants;

import com.google.android.gms.games.internal.GamesLog;

public final class TurnBasedMatchTurnStatus {
    public static String bd(int n2) {
        switch (n2) {
            default: {
                GamesLog.h("MatchTurnStatus", "Unknown match turn status: " + n2);
                return "TURN_STATUS_UNKNOWN";
            }
            case 0: {
                return "TURN_STATUS_INVITED";
            }
            case 1: {
                return "TURN_STATUS_MY_TURN";
            }
            case 2: {
                return "TURN_STATUS_THEIR_TURN";
            }
            case 3: 
        }
        return "TURN_STATUS_COMPLETE";
    }
}

