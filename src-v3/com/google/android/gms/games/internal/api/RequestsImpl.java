package com.google.android.gms.games.internal.api;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestBuffer;
import com.google.android.gms.games.request.OnRequestReceivedListener;
import com.google.android.gms.games.request.Requests;
import com.google.android.gms.games.request.Requests.LoadRequestSummariesResult;
import com.google.android.gms.games.request.Requests.LoadRequestsResult;
import com.google.android.gms.games.request.Requests.SendRequestResult;
import com.google.android.gms.games.request.Requests.UpdateRequestsResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class RequestsImpl implements Requests {

    private static abstract class LoadRequestSummariesImpl extends BaseGamesApiMethodImpl<LoadRequestSummariesResult> {

        /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.LoadRequestSummariesImpl.1 */
        class C13451 implements LoadRequestSummariesResult {
            final /* synthetic */ LoadRequestSummariesImpl KX;
            final /* synthetic */ Status wz;

            C13451(LoadRequestSummariesImpl loadRequestSummariesImpl, Status status) {
                this.KX = loadRequestSummariesImpl;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadRequestSummariesImpl() {
        }

        public LoadRequestSummariesResult m3549N(Status status) {
            return new C13451(this, status);
        }

        public /* synthetic */ Result m3550d(Status status) {
            return m3549N(status);
        }
    }

    private static abstract class LoadRequestsImpl extends BaseGamesApiMethodImpl<LoadRequestsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.LoadRequestsImpl.1 */
        class C13461 implements LoadRequestsResult {
            final /* synthetic */ LoadRequestsImpl KY;
            final /* synthetic */ Status wz;

            C13461(LoadRequestsImpl loadRequestsImpl, Status status) {
                this.KY = loadRequestsImpl;
                this.wz = status;
            }

            public GameRequestBuffer getRequests(int type) {
                return null;
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private LoadRequestsImpl() {
        }

        public LoadRequestsResult m3551O(Status status) {
            return new C13461(this, status);
        }

        public /* synthetic */ Result m3552d(Status status) {
            return m3551O(status);
        }
    }

    private static abstract class SendRequestImpl extends BaseGamesApiMethodImpl<SendRequestResult> {

        /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.SendRequestImpl.1 */
        class C13471 implements SendRequestResult {
            final /* synthetic */ SendRequestImpl KZ;
            final /* synthetic */ Status wz;

            C13471(SendRequestImpl sendRequestImpl, Status status) {
                this.KZ = sendRequestImpl;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        private SendRequestImpl() {
        }

        public SendRequestResult m3553P(Status status) {
            return new C13471(this, status);
        }

        public /* synthetic */ Result m3554d(Status status) {
            return m3553P(status);
        }
    }

    private static abstract class UpdateRequestsImpl extends BaseGamesApiMethodImpl<UpdateRequestsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.UpdateRequestsImpl.1 */
        class C13481 implements UpdateRequestsResult {
            final /* synthetic */ UpdateRequestsImpl La;
            final /* synthetic */ Status wz;

            C13481(UpdateRequestsImpl updateRequestsImpl, Status status) {
                this.La = updateRequestsImpl;
                this.wz = status;
            }

            public Set<String> getRequestIds() {
                return null;
            }

            public int getRequestOutcome(String requestId) {
                throw new IllegalArgumentException("Unknown request ID " + requestId);
            }

            public Status getStatus() {
                return this.wz;
            }

            public void release() {
            }
        }

        private UpdateRequestsImpl() {
        }

        public UpdateRequestsResult m3555Q(Status status) {
            return new C13481(this, status);
        }

        public /* synthetic */ Result m3556d(Status status) {
            return m3555Q(status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.1 */
    class C16361 extends UpdateRequestsImpl {
        final /* synthetic */ String[] KO;
        final /* synthetic */ RequestsImpl KP;

        C16361(RequestsImpl requestsImpl, String[] strArr) {
            this.KP = requestsImpl;
            this.KO = strArr;
            super();
        }

        protected void m3797a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2838b((C0244d) this, this.KO);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.2 */
    class C16372 extends UpdateRequestsImpl {
        final /* synthetic */ String[] KO;
        final /* synthetic */ RequestsImpl KP;

        C16372(RequestsImpl requestsImpl, String[] strArr) {
            this.KP = requestsImpl;
            this.KO = strArr;
            super();
        }

        protected void m3799a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2849c((C0244d) this, this.KO);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.3 */
    class C16383 extends LoadRequestsImpl {
        final /* synthetic */ RequestsImpl KP;
        final /* synthetic */ int KQ;
        final /* synthetic */ int KR;
        final /* synthetic */ int Kk;

        C16383(RequestsImpl requestsImpl, int i, int i2, int i3) {
            this.KP = requestsImpl;
            this.KQ = i;
            this.KR = i2;
            this.Kk = i3;
            super();
        }

        protected void m3801a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2792a((C0244d) this, this.KQ, this.KR, this.Kk);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.4 */
    class C16394 extends SendRequestImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ String[] KS;
        final /* synthetic */ int KT;
        final /* synthetic */ byte[] KU;
        final /* synthetic */ int KV;

        protected void m3803a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2816a((C0244d) this, this.JT, this.KS, this.KT, this.KU, this.KV);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.5 */
    class C16405 extends SendRequestImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ String[] KS;
        final /* synthetic */ int KT;
        final /* synthetic */ byte[] KU;
        final /* synthetic */ int KV;

        protected void m3805a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2816a((C0244d) this, this.JT, this.KS, this.KT, this.KU, this.KV);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.6 */
    class C16416 extends UpdateRequestsImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ String[] KO;
        final /* synthetic */ String KW;

        protected void m3807a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2812a((C0244d) this, this.JT, this.KW, this.KO);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.7 */
    class C16427 extends LoadRequestsImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ int KQ;
        final /* synthetic */ int KR;
        final /* synthetic */ String KW;
        final /* synthetic */ int Kk;

        protected void m3809a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2808a((C0244d) this, this.JT, this.KW, this.KQ, this.KR, this.Kk);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.8 */
    class C16438 extends LoadRequestSummariesImpl {
        final /* synthetic */ int KR;
        final /* synthetic */ String KW;

        protected void m3811a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m2853d(this, this.KW, this.KR);
        }
    }

    public PendingResult<UpdateRequestsResult> acceptRequest(GoogleApiClient apiClient, String requestId) {
        List arrayList = new ArrayList();
        arrayList.add(requestId);
        return acceptRequests(apiClient, arrayList);
    }

    public PendingResult<UpdateRequestsResult> acceptRequests(GoogleApiClient apiClient, List<String> requestIds) {
        return apiClient.m125b(new C16361(this, requestIds == null ? null : (String[]) requestIds.toArray(new String[requestIds.size()])));
    }

    public PendingResult<UpdateRequestsResult> dismissRequest(GoogleApiClient apiClient, String requestId) {
        List arrayList = new ArrayList();
        arrayList.add(requestId);
        return dismissRequests(apiClient, arrayList);
    }

    public PendingResult<UpdateRequestsResult> dismissRequests(GoogleApiClient apiClient, List<String> requestIds) {
        return apiClient.m125b(new C16372(this, requestIds == null ? null : (String[]) requestIds.toArray(new String[requestIds.size()])));
    }

    public ArrayList<GameRequest> getGameRequestsFromBundle(Bundle extras) {
        if (extras == null || !extras.containsKey(Requests.EXTRA_REQUESTS)) {
            return new ArrayList();
        }
        ArrayList arrayList = (ArrayList) extras.get(Requests.EXTRA_REQUESTS);
        ArrayList<GameRequest> arrayList2 = new ArrayList();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList2.add((GameRequest) arrayList.get(i));
        }
        return arrayList2;
    }

    public ArrayList<GameRequest> getGameRequestsFromInboxResponse(Intent response) {
        return response == null ? new ArrayList() : getGameRequestsFromBundle(response.getExtras());
    }

    public Intent getInboxIntent(GoogleApiClient apiClient) {
        return Games.m361c(apiClient).gB();
    }

    public int getMaxLifetimeDays(GoogleApiClient apiClient) {
        return Games.m361c(apiClient).gD();
    }

    public int getMaxPayloadSize(GoogleApiClient apiClient) {
        return Games.m361c(apiClient).gC();
    }

    public Intent getSendIntent(GoogleApiClient apiClient, int type, byte[] payload, int requestLifetimeDays, Bitmap icon, String description) {
        return Games.m361c(apiClient).m2788a(type, payload, requestLifetimeDays, icon, description);
    }

    public PendingResult<LoadRequestsResult> loadRequests(GoogleApiClient apiClient, int requestDirection, int types, int sortOrder) {
        return apiClient.m124a(new C16383(this, requestDirection, types, sortOrder));
    }

    public void registerRequestListener(GoogleApiClient apiClient, OnRequestReceivedListener listener) {
        Games.m361c(apiClient).m2824a(listener);
    }

    public void unregisterRequestListener(GoogleApiClient apiClient) {
        Games.m361c(apiClient).gv();
    }
}
