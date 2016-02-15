/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.net.Uri
 */
package com.google.android.gms.games.leaderboard;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Player;

public interface LeaderboardScore
extends Freezable<LeaderboardScore> {
    public static final int LEADERBOARD_RANK_UNKNOWN = -1;

    public String getDisplayRank();

    public void getDisplayRank(CharArrayBuffer var1);

    public String getDisplayScore();

    public void getDisplayScore(CharArrayBuffer var1);

    public long getRank();

    public long getRawScore();

    public Player getScoreHolder();

    public String getScoreHolderDisplayName();

    public void getScoreHolderDisplayName(CharArrayBuffer var1);

    public Uri getScoreHolderHiResImageUri();

    @Deprecated
    public String getScoreHolderHiResImageUrl();

    public Uri getScoreHolderIconImageUri();

    @Deprecated
    public String getScoreHolderIconImageUrl();

    public String getScoreTag();

    public long getTimestampMillis();
}

