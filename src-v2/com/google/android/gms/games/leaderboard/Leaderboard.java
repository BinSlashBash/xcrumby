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
import com.google.android.gms.games.Game;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import java.util.ArrayList;

public interface Leaderboard
extends Freezable<Leaderboard> {
    public static final int SCORE_ORDER_LARGER_IS_BETTER = 1;
    public static final int SCORE_ORDER_SMALLER_IS_BETTER = 0;

    public String getDisplayName();

    public void getDisplayName(CharArrayBuffer var1);

    public Game getGame();

    public Uri getIconImageUri();

    @Deprecated
    public String getIconImageUrl();

    public String getLeaderboardId();

    public int getScoreOrder();

    public ArrayList<LeaderboardVariant> getVariants();
}

