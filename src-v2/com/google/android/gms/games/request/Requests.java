/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.os.Bundle
 */
package com.google.android.gms.games.request;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestBuffer;
import com.google.android.gms.games.request.OnRequestReceivedListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface Requests {
    public static final String EXTRA_REQUESTS = "requests";
    public static final int REQUEST_DEFAULT_LIFETIME_DAYS = -1;
    public static final int REQUEST_DIRECTION_INBOUND = 0;
    public static final int REQUEST_DIRECTION_OUTBOUND = 1;
    public static final int REQUEST_UPDATE_OUTCOME_FAIL = 1;
    public static final int REQUEST_UPDATE_OUTCOME_RETRY = 2;
    public static final int REQUEST_UPDATE_OUTCOME_SUCCESS = 0;
    public static final int REQUEST_UPDATE_TYPE_ACCEPT = 0;
    public static final int REQUEST_UPDATE_TYPE_DISMISS = 1;
    public static final int SORT_ORDER_EXPIRING_SOON_FIRST = 0;
    public static final int SORT_ORDER_SOCIAL_AGGREGATION = 1;

    public PendingResult<UpdateRequestsResult> acceptRequest(GoogleApiClient var1, String var2);

    public PendingResult<UpdateRequestsResult> acceptRequests(GoogleApiClient var1, List<String> var2);

    public PendingResult<UpdateRequestsResult> dismissRequest(GoogleApiClient var1, String var2);

    public PendingResult<UpdateRequestsResult> dismissRequests(GoogleApiClient var1, List<String> var2);

    public ArrayList<GameRequest> getGameRequestsFromBundle(Bundle var1);

    public ArrayList<GameRequest> getGameRequestsFromInboxResponse(Intent var1);

    public Intent getInboxIntent(GoogleApiClient var1);

    public int getMaxLifetimeDays(GoogleApiClient var1);

    public int getMaxPayloadSize(GoogleApiClient var1);

    public Intent getSendIntent(GoogleApiClient var1, int var2, byte[] var3, int var4, Bitmap var5, String var6);

    public PendingResult<LoadRequestsResult> loadRequests(GoogleApiClient var1, int var2, int var3, int var4);

    public void registerRequestListener(GoogleApiClient var1, OnRequestReceivedListener var2);

    public void unregisterRequestListener(GoogleApiClient var1);

    public static interface LoadRequestSummariesResult
    extends Releasable,
    Result {
    }

    public static interface LoadRequestsResult
    extends Releasable,
    Result {
        public GameRequestBuffer getRequests(int var1);
    }

    public static interface SendRequestResult
    extends Result {
    }

    public static interface UpdateRequestsResult
    extends Releasable,
    Result {
        public Set<String> getRequestIds();

        public int getRequestOutcome(String var1);
    }

}

