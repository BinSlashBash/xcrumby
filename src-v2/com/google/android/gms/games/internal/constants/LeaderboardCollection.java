/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.constants;

public final class LeaderboardCollection {
    private LeaderboardCollection() {
    }

    public static String bd(int n2) {
        switch (n2) {
            default: {
                throw new IllegalArgumentException("Unknown leaderboard collection: " + n2);
            }
            case 0: {
                return "PUBLIC";
            }
            case 1: 
        }
        return "SOCIAL";
    }
}

