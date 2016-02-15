package com.google.android.gms.games;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

public interface Notifications {
    public static final int NOTIFICATION_TYPES_ALL = 7;
    public static final int NOTIFICATION_TYPES_MULTIPLAYER = 3;
    public static final int NOTIFICATION_TYPE_INVITATION = 1;
    public static final int NOTIFICATION_TYPE_MATCH_UPDATE = 2;
    public static final int NOTIFICATION_TYPE_REQUEST = 4;

    public interface ContactSettingLoadResult extends Result {
    }

    public interface GameMuteStatusChangeResult extends Result {
    }

    public interface GameMuteStatusLoadResult extends Result {
    }

    void clear(GoogleApiClient googleApiClient, int i);

    void clearAll(GoogleApiClient googleApiClient);
}
