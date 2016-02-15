package com.google.android.gms.games;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;

public interface GamesMetadata {

    public interface LoadExtendedGamesResult extends Releasable, Result {
    }

    public interface LoadGameInstancesResult extends Releasable, Result {
    }

    public interface LoadGameSearchSuggestionsResult extends Releasable, Result {
    }

    public interface LoadGamesResult extends Releasable, Result {
        GameBuffer getGames();
    }

    Game getCurrentGame(GoogleApiClient googleApiClient);

    PendingResult<LoadGamesResult> loadGame(GoogleApiClient googleApiClient);
}
