/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.games;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;

public interface Players {
    public static final String EXTRA_PLAYER_SEARCH_RESULTS = "player_search_results";

    public Player getCurrentPlayer(GoogleApiClient var1);

    public String getCurrentPlayerId(GoogleApiClient var1);

    public Intent getPlayerSearchIntent(GoogleApiClient var1);

    public PendingResult<LoadPlayersResult> loadConnectedPlayers(GoogleApiClient var1, boolean var2);

    public PendingResult<LoadPlayersResult> loadInvitablePlayers(GoogleApiClient var1, int var2, boolean var3);

    public PendingResult<LoadPlayersResult> loadMoreInvitablePlayers(GoogleApiClient var1, int var2);

    public PendingResult<LoadPlayersResult> loadMoreRecentlyPlayedWithPlayers(GoogleApiClient var1, int var2);

    public PendingResult<LoadPlayersResult> loadPlayer(GoogleApiClient var1, String var2);

    public PendingResult<LoadPlayersResult> loadRecentlyPlayedWithPlayers(GoogleApiClient var1, int var2, boolean var3);

    public static interface LoadExtendedPlayersResult
    extends Releasable,
    Result {
    }

    public static interface LoadOwnerCoverPhotoUrisResult
    extends Result {
    }

    public static interface LoadPlayersResult
    extends Releasable,
    Result {
        public PlayerBuffer getPlayers();
    }

}

