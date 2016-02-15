package com.google.android.gms.plus;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Moments.LoadMomentsResult;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.internal.C0482i;
import com.google.android.gms.plus.internal.C1415e;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.Collection;

@Deprecated
public class PlusClient implements GooglePlayServicesClient {
    final C1415e TL;

    @Deprecated
    public static class Builder {
        private final ConnectionCallbacks TQ;
        private final OnConnectionFailedListener TR;
        private final C0482i TS;
        private final Context mContext;

        public Builder(Context context, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener connectionFailedListener) {
            this.mContext = context;
            this.TQ = connectionCallbacks;
            this.TR = connectionFailedListener;
            this.TS = new C0482i(this.mContext);
        }

        public PlusClient build() {
            return new PlusClient(new C1415e(this.mContext, this.TQ, this.TR, this.TS.iZ()));
        }

        public Builder clearScopes() {
            this.TS.iY();
            return this;
        }

        public Builder setAccountName(String accountName) {
            this.TS.bh(accountName);
            return this;
        }

        public Builder setActions(String... actions) {
            this.TS.m1325f(actions);
            return this;
        }

        public Builder setScopes(String... scopes) {
            this.TS.m1324e(scopes);
            return this;
        }
    }

    @Deprecated
    public interface OnAccessRevokedListener {
        void onAccessRevoked(ConnectionResult connectionResult);
    }

    @Deprecated
    public interface OnMomentsLoadedListener {
        @Deprecated
        void onMomentsLoaded(ConnectionResult connectionResult, MomentBuffer momentBuffer, String str, String str2);
    }

    @Deprecated
    public interface OnPeopleLoadedListener {
        void onPeopleLoaded(ConnectionResult connectionResult, PersonBuffer personBuffer, String str);
    }

    @Deprecated
    public interface OrderBy {
        @Deprecated
        public static final int ALPHABETICAL = 0;
        @Deprecated
        public static final int BEST = 1;
    }

    /* renamed from: com.google.android.gms.plus.PlusClient.1 */
    class C10351 implements C0244d<LoadMomentsResult> {
        final /* synthetic */ OnMomentsLoadedListener TM;
        final /* synthetic */ PlusClient TN;

        C10351(PlusClient plusClient, OnMomentsLoadedListener onMomentsLoadedListener) {
            this.TN = plusClient;
            this.TM = onMomentsLoadedListener;
        }

        public void m2394a(LoadMomentsResult loadMomentsResult) {
            this.TM.onMomentsLoaded(loadMomentsResult.getStatus().eq(), loadMomentsResult.getMomentBuffer(), loadMomentsResult.getNextPageToken(), loadMomentsResult.getUpdated());
        }

        public /* synthetic */ void m2395b(Object obj) {
            m2394a((LoadMomentsResult) obj);
        }
    }

    /* renamed from: com.google.android.gms.plus.PlusClient.2 */
    class C10362 implements C0244d<LoadMomentsResult> {
        final /* synthetic */ OnMomentsLoadedListener TM;
        final /* synthetic */ PlusClient TN;

        C10362(PlusClient plusClient, OnMomentsLoadedListener onMomentsLoadedListener) {
            this.TN = plusClient;
            this.TM = onMomentsLoadedListener;
        }

        public void m2396a(LoadMomentsResult loadMomentsResult) {
            this.TM.onMomentsLoaded(loadMomentsResult.getStatus().eq(), loadMomentsResult.getMomentBuffer(), loadMomentsResult.getNextPageToken(), loadMomentsResult.getUpdated());
        }

        public /* synthetic */ void m2397b(Object obj) {
            m2396a((LoadMomentsResult) obj);
        }
    }

    /* renamed from: com.google.android.gms.plus.PlusClient.3 */
    class C10373 implements C0244d<LoadPeopleResult> {
        final /* synthetic */ PlusClient TN;
        final /* synthetic */ OnPeopleLoadedListener TO;

        C10373(PlusClient plusClient, OnPeopleLoadedListener onPeopleLoadedListener) {
            this.TN = plusClient;
            this.TO = onPeopleLoadedListener;
        }

        public void m2398a(LoadPeopleResult loadPeopleResult) {
            this.TO.onPeopleLoaded(loadPeopleResult.getStatus().eq(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
        }

        public /* synthetic */ void m2399b(Object obj) {
            m2398a((LoadPeopleResult) obj);
        }
    }

    /* renamed from: com.google.android.gms.plus.PlusClient.4 */
    class C10384 implements C0244d<LoadPeopleResult> {
        final /* synthetic */ PlusClient TN;
        final /* synthetic */ OnPeopleLoadedListener TO;

        C10384(PlusClient plusClient, OnPeopleLoadedListener onPeopleLoadedListener) {
            this.TN = plusClient;
            this.TO = onPeopleLoadedListener;
        }

        public void m2400a(LoadPeopleResult loadPeopleResult) {
            this.TO.onPeopleLoaded(loadPeopleResult.getStatus().eq(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
        }

        public /* synthetic */ void m2401b(Object obj) {
            m2400a((LoadPeopleResult) obj);
        }
    }

    /* renamed from: com.google.android.gms.plus.PlusClient.5 */
    class C10395 implements C0244d<LoadPeopleResult> {
        final /* synthetic */ PlusClient TN;
        final /* synthetic */ OnPeopleLoadedListener TO;

        C10395(PlusClient plusClient, OnPeopleLoadedListener onPeopleLoadedListener) {
            this.TN = plusClient;
            this.TO = onPeopleLoadedListener;
        }

        public void m2402a(LoadPeopleResult loadPeopleResult) {
            this.TO.onPeopleLoaded(loadPeopleResult.getStatus().eq(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
        }

        public /* synthetic */ void m2403b(Object obj) {
            m2402a((LoadPeopleResult) obj);
        }
    }

    /* renamed from: com.google.android.gms.plus.PlusClient.6 */
    class C10406 implements C0244d<LoadPeopleResult> {
        final /* synthetic */ PlusClient TN;
        final /* synthetic */ OnPeopleLoadedListener TO;

        C10406(PlusClient plusClient, OnPeopleLoadedListener onPeopleLoadedListener) {
            this.TN = plusClient;
            this.TO = onPeopleLoadedListener;
        }

        public void m2404a(LoadPeopleResult loadPeopleResult) {
            this.TO.onPeopleLoaded(loadPeopleResult.getStatus().eq(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
        }

        public /* synthetic */ void m2405b(Object obj) {
            m2404a((LoadPeopleResult) obj);
        }
    }

    /* renamed from: com.google.android.gms.plus.PlusClient.7 */
    class C10417 implements C0244d<Status> {
        final /* synthetic */ PlusClient TN;
        final /* synthetic */ OnAccessRevokedListener TP;

        C10417(PlusClient plusClient, OnAccessRevokedListener onAccessRevokedListener) {
            this.TN = plusClient;
            this.TP = onAccessRevokedListener;
        }

        public void m2406Y(Status status) {
            this.TP.onAccessRevoked(status.getStatus().eq());
        }

        public /* synthetic */ void m2407b(Object obj) {
            m2406Y((Status) obj);
        }
    }

    PlusClient(C1415e plusClientImpl) {
        this.TL = plusClientImpl;
    }

    @Deprecated
    public void clearDefaultAccount() {
        this.TL.clearDefaultAccount();
    }

    @Deprecated
    public void connect() {
        this.TL.connect();
    }

    @Deprecated
    public void disconnect() {
        this.TL.disconnect();
    }

    @Deprecated
    public String getAccountName() {
        return this.TL.getAccountName();
    }

    @Deprecated
    public Person getCurrentPerson() {
        return this.TL.getCurrentPerson();
    }

    C1415e iI() {
        return this.TL;
    }

    @Deprecated
    public boolean isConnected() {
        return this.TL.isConnected();
    }

    @Deprecated
    public boolean isConnecting() {
        return this.TL.isConnecting();
    }

    @Deprecated
    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks listener) {
        return this.TL.isConnectionCallbacksRegistered(listener);
    }

    @Deprecated
    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener listener) {
        return this.TL.isConnectionFailedListenerRegistered(listener);
    }

    @Deprecated
    public void loadMoments(OnMomentsLoadedListener listener) {
        this.TL.m3200l(new C10351(this, listener));
    }

    @Deprecated
    public void loadMoments(OnMomentsLoadedListener listener, int maxResults, String pageToken, Uri targetUrl, String type, String userId) {
        this.TL.m3195a(new C10362(this, listener), maxResults, pageToken, targetUrl, type, userId);
    }

    @Deprecated
    public void loadPeople(OnPeopleLoadedListener listener, Collection<String> personIds) {
        this.TL.m3197a(new C10395(this, listener), (Collection) personIds);
    }

    @Deprecated
    public void loadPeople(OnPeopleLoadedListener listener, String... personIds) {
        this.TL.m3199d(new C10406(this, listener), personIds);
    }

    @Deprecated
    public void loadVisiblePeople(OnPeopleLoadedListener listener, int orderBy, String pageToken) {
        this.TL.m3193a(new C10373(this, listener), orderBy, pageToken);
    }

    @Deprecated
    public void loadVisiblePeople(OnPeopleLoadedListener listener, String pageToken) {
        this.TL.m3203o(new C10384(this, listener), pageToken);
    }

    @Deprecated
    public void registerConnectionCallbacks(ConnectionCallbacks listener) {
        this.TL.registerConnectionCallbacks(listener);
    }

    @Deprecated
    public void registerConnectionFailedListener(OnConnectionFailedListener listener) {
        this.TL.registerConnectionFailedListener(listener);
    }

    @Deprecated
    public void removeMoment(String momentId) {
        this.TL.removeMoment(momentId);
    }

    @Deprecated
    public void revokeAccessAndDisconnect(OnAccessRevokedListener listener) {
        this.TL.m3202n(new C10417(this, listener));
    }

    @Deprecated
    public void unregisterConnectionCallbacks(ConnectionCallbacks listener) {
        this.TL.unregisterConnectionCallbacks(listener);
    }

    @Deprecated
    public void unregisterConnectionFailedListener(OnConnectionFailedListener listener) {
        this.TL.unregisterConnectionFailedListener(listener);
    }

    @Deprecated
    public void writeMoment(Moment moment) {
        this.TL.m3196a(null, moment);
    }
}
