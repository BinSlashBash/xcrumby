/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.games.leaderboard;

import android.os.Bundle;

public final class LeaderboardScoreBufferHeader {
    private final Bundle Jf;

    public LeaderboardScoreBufferHeader(Bundle bundle) {
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        this.Jf = bundle2;
    }

    public Bundle hE() {
        return this.Jf;
    }

    public static final class Builder {
        private Builder() {
        }
    }

}

