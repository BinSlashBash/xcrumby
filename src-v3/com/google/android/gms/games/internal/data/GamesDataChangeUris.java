package com.google.android.gms.games.internal.data;

import android.net.Uri;
import com.google.android.gms.games.Games;

public final class GamesDataChangeUris {
    private static final Uri Lq;
    public static final Uri Lr;
    public static final Uri Ls;

    static {
        Lq = Uri.parse("content://com.google.android.gms.games/").buildUpon().appendPath("data_change").build();
        Lr = Lq.buildUpon().appendPath("invitations").build();
        Ls = Lq.buildUpon().appendEncodedPath(Games.EXTRA_PLAYER_IDS).build();
    }
}
