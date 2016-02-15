/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;

public interface GamesMetadata {
    public Game getCurrentGame(GoogleApiClient var1);

    public PendingResult<LoadGamesResult> loadGame(GoogleApiClient var1);

    public static interface LoadExtendedGamesResult
    extends Releasable,
    Result {
    }

    public static interface LoadGameInstancesResult
    extends Releasable,
    Result {
    }

    public static interface LoadGameSearchSuggestionsResult
    extends Releasable,
    Result {
    }

    public static interface LoadGamesResult
    extends Releasable,
    Result {
        public GameBuffer getGames();
    }

}

