/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.os.Bundle
 *  android.os.RemoteException
 */
package com.google.android.gms.games.internal.api;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestBuffer;
import com.google.android.gms.games.request.OnRequestReceivedListener;
import com.google.android.gms.games.request.Requests;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class RequestsImpl
implements Requests {
    @Override
    public PendingResult<Requests.UpdateRequestsResult> acceptRequest(GoogleApiClient googleApiClient, String string2) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(string2);
        return this.acceptRequests(googleApiClient, arrayList);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public PendingResult<Requests.UpdateRequestsResult> acceptRequests(GoogleApiClient googleApiClient, final List<String> arrstring) {
        if (arrstring == null) {
            arrstring = null;
            do {
                return googleApiClient.b(new UpdateRequestsImpl(){

                    @Override
                    protected void a(GamesClientImpl gamesClientImpl) {
                        gamesClientImpl.b(this, arrstring);
                    }
                });
                break;
            } while (true);
        }
        arrstring = arrstring.toArray(new String[arrstring.size()]);
        return googleApiClient.b(new );
    }

    @Override
    public PendingResult<Requests.UpdateRequestsResult> dismissRequest(GoogleApiClient googleApiClient, String string2) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(string2);
        return this.dismissRequests(googleApiClient, arrayList);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public PendingResult<Requests.UpdateRequestsResult> dismissRequests(GoogleApiClient googleApiClient, final List<String> arrstring) {
        if (arrstring == null) {
            arrstring = null;
            do {
                return googleApiClient.b(new UpdateRequestsImpl(){

                    @Override
                    protected void a(GamesClientImpl gamesClientImpl) {
                        gamesClientImpl.c(this, arrstring);
                    }
                });
                break;
            } while (true);
        }
        arrstring = arrstring.toArray(new String[arrstring.size()]);
        return googleApiClient.b(new );
    }

    @Override
    public ArrayList<GameRequest> getGameRequestsFromBundle(Bundle object) {
        if (object == null || !object.containsKey("requests")) {
            return new ArrayList<GameRequest>();
        }
        object = (ArrayList)object.get("requests");
        ArrayList<GameRequest> arrayList = new ArrayList<GameRequest>();
        int n2 = object.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add((GameRequest)object.get(i2));
        }
        return arrayList;
    }

    @Override
    public ArrayList<GameRequest> getGameRequestsFromInboxResponse(Intent intent) {
        if (intent == null) {
            return new ArrayList<GameRequest>();
        }
        return this.getGameRequestsFromBundle(intent.getExtras());
    }

    @Override
    public Intent getInboxIntent(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gB();
    }

    @Override
    public int getMaxLifetimeDays(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gD();
    }

    @Override
    public int getMaxPayloadSize(GoogleApiClient googleApiClient) {
        return Games.c(googleApiClient).gC();
    }

    @Override
    public Intent getSendIntent(GoogleApiClient googleApiClient, int n2, byte[] arrby, int n3, Bitmap bitmap, String string2) {
        return Games.c(googleApiClient).a(n2, arrby, n3, bitmap, string2);
    }

    @Override
    public PendingResult<Requests.LoadRequestsResult> loadRequests(GoogleApiClient googleApiClient, final int n2, final int n3, final int n4) {
        return googleApiClient.a(new LoadRequestsImpl(){

            @Override
            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, n2, n3, n4);
            }
        });
    }

    @Override
    public void registerRequestListener(GoogleApiClient googleApiClient, OnRequestReceivedListener onRequestReceivedListener) {
        Games.c(googleApiClient).a(onRequestReceivedListener);
    }

    @Override
    public void unregisterRequestListener(GoogleApiClient googleApiClient) {
        Games.c(googleApiClient).gv();
    }

    private static abstract class LoadRequestSummariesImpl
    extends Games.BaseGamesApiMethodImpl<Requests.LoadRequestSummariesResult> {
        private LoadRequestSummariesImpl() {
        }

        public Requests.LoadRequestSummariesResult N(final Status status) {
            return new Requests.LoadRequestSummariesResult(){

                @Override
                public Status getStatus() {
                    return status;
                }

                @Override
                public void release() {
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.N(status);
        }

    }

    private static abstract class LoadRequestsImpl
    extends Games.BaseGamesApiMethodImpl<Requests.LoadRequestsResult> {
        private LoadRequestsImpl() {
        }

        public Requests.LoadRequestsResult O(final Status status) {
            return new Requests.LoadRequestsResult(){

                @Override
                public GameRequestBuffer getRequests(int n2) {
                    return null;
                }

                @Override
                public Status getStatus() {
                    return status;
                }

                @Override
                public void release() {
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.O(status);
        }

    }

    private static abstract class SendRequestImpl
    extends Games.BaseGamesApiMethodImpl<Requests.SendRequestResult> {
        private SendRequestImpl() {
        }

        public Requests.SendRequestResult P(final Status status) {
            return new Requests.SendRequestResult(){

                @Override
                public Status getStatus() {
                    return status;
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.P(status);
        }

    }

    private static abstract class UpdateRequestsImpl
    extends Games.BaseGamesApiMethodImpl<Requests.UpdateRequestsResult> {
        private UpdateRequestsImpl() {
        }

        public Requests.UpdateRequestsResult Q(final Status status) {
            return new Requests.UpdateRequestsResult(){

                @Override
                public Set<String> getRequestIds() {
                    return null;
                }

                @Override
                public int getRequestOutcome(String string2) {
                    throw new IllegalArgumentException("Unknown request ID " + string2);
                }

                @Override
                public Status getStatus() {
                    return status;
                }

                @Override
                public void release() {
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.Q(status);
        }

    }

}

