package com.google.android.gms.appstate;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.C0240b;
import com.google.android.gms.common.api.Api.C0241c;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.C0245a.C1299b;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.ei;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;

public final class AppStateManager {
    public static final Api<NoOptions> API;
    public static final Scope SCOPE_APP_STATE;
    static final C0241c<ei> wx;
    private static final C0240b<ei, NoOptions> wy;

    /* renamed from: com.google.android.gms.appstate.AppStateManager.1 */
    static class C07821 implements C0240b<ei, NoOptions> {
        C07821() {
        }

        public ei m1641a(Context context, Looper looper, fc fcVar, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new ei(context, looper, connectionCallbacks, onConnectionFailedListener, fcVar.eC(), (String[]) fcVar.eE().toArray(new String[0]));
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    }

    public interface StateConflictResult extends Releasable, Result {
        byte[] getLocalData();

        String getResolvedVersion();

        byte[] getServerData();

        int getStateKey();
    }

    public interface StateDeletedResult extends Result {
        int getStateKey();
    }

    public interface StateListResult extends Result {
        AppStateBuffer getStateBuffer();
    }

    public interface StateLoadedResult extends Releasable, Result {
        byte[] getLocalData();

        int getStateKey();
    }

    public interface StateResult extends Releasable, Result {
        StateConflictResult getConflictResult();

        StateLoadedResult getLoadedResult();
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.2 */
    static class C12902 implements StateResult {
        final /* synthetic */ Status wz;

        C12902(Status status) {
            this.wz = status;
        }

        public StateConflictResult getConflictResult() {
            return null;
        }

        public StateLoadedResult getLoadedResult() {
            return null;
        }

        public Status getStatus() {
            return this.wz;
        }

        public void release() {
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.a */
    public static abstract class C1470a<R extends Result> extends C1299b<R, ei> {
        public C1470a() {
            super(AppStateManager.wx);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.b */
    private static abstract class C1512b extends C1470a<StateDeletedResult> {
        private C1512b() {
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.c */
    private static abstract class C1513c extends C1470a<StateListResult> {

        /* renamed from: com.google.android.gms.appstate.AppStateManager.c.1 */
        class C12921 implements StateListResult {
            final /* synthetic */ C1513c wF;
            final /* synthetic */ Status wz;

            C12921(C1513c c1513c, Status status) {
                this.wF = c1513c;
                this.wz = status;
            }

            public AppStateBuffer getStateBuffer() {
                return new AppStateBuffer(null);
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        private C1513c() {
        }

        public /* synthetic */ Result m3448d(Status status) {
            return m3449e(status);
        }

        public StateListResult m3449e(Status status) {
            return new C12921(this, status);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.d */
    private static abstract class C1514d extends C1470a<Status> {
        private C1514d() {
        }

        public /* synthetic */ Result m3450d(Status status) {
            return m3451f(status);
        }

        public Status m3451f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.e */
    private static abstract class C1515e extends C1470a<StateResult> {
        private C1515e() {
        }

        public /* synthetic */ Result m3452d(Status status) {
            return m3453g(status);
        }

        public StateResult m3453g(Status status) {
            return AppStateManager.m74a(status);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.3 */
    static class C15533 extends C1515e {
        final /* synthetic */ int wA;
        final /* synthetic */ byte[] wB;

        C15533(int i, byte[] bArr) {
            this.wA = i;
            this.wB = bArr;
            super();
        }

        protected void m3587a(ei eiVar) {
            eiVar.m3021a(null, this.wA, this.wB);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.4 */
    static class C15544 extends C1515e {
        final /* synthetic */ int wA;
        final /* synthetic */ byte[] wB;

        C15544(int i, byte[] bArr) {
            this.wA = i;
            this.wB = bArr;
            super();
        }

        protected void m3589a(ei eiVar) {
            eiVar.m3021a(this, this.wA, this.wB);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.5 */
    static class C15555 extends C1512b {
        final /* synthetic */ int wA;

        /* renamed from: com.google.android.gms.appstate.AppStateManager.5.1 */
        class C12911 implements StateDeletedResult {
            final /* synthetic */ C15555 wC;
            final /* synthetic */ Status wz;

            C12911(C15555 c15555, Status status) {
                this.wC = c15555;
                this.wz = status;
            }

            public int getStateKey() {
                return this.wC.wA;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        C15555(int i) {
            this.wA = i;
            super();
        }

        protected void m3591a(ei eiVar) {
            eiVar.m3019a((C0244d) this, this.wA);
        }

        public StateDeletedResult m3592c(Status status) {
            return new C12911(this, status);
        }

        public /* synthetic */ Result m3593d(Status status) {
            return m3592c(status);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.6 */
    static class C15566 extends C1515e {
        final /* synthetic */ int wA;

        C15566(int i) {
            this.wA = i;
            super();
        }

        protected void m3595a(ei eiVar) {
            eiVar.m3024b(this, this.wA);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.7 */
    static class C15577 extends C1513c {
        C15577() {
            super();
        }

        protected void m3597a(ei eiVar) {
            eiVar.m3018a(this);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.8 */
    static class C15588 extends C1515e {
        final /* synthetic */ int wA;
        final /* synthetic */ String wD;
        final /* synthetic */ byte[] wE;

        C15588(int i, String str, byte[] bArr) {
            this.wA = i;
            this.wD = str;
            this.wE = bArr;
            super();
        }

        protected void m3599a(ei eiVar) {
            eiVar.m3020a(this, this.wA, this.wD, this.wE);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.9 */
    static class C15599 extends C1514d {
        C15599() {
            super();
        }

        protected void m3601a(ei eiVar) {
            eiVar.m3023b((C0244d) this);
        }
    }

    static {
        wx = new C0241c();
        wy = new C07821();
        SCOPE_APP_STATE = new Scope(Scopes.APP_STATE);
        API = new Api(wy, wx, SCOPE_APP_STATE);
    }

    private AppStateManager() {
    }

    private static StateResult m74a(Status status) {
        return new C12902(status);
    }

    public static ei m75a(GoogleApiClient googleApiClient) {
        boolean z = true;
        fq.m984b(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        fq.m980a(googleApiClient.isConnected(), "GoogleApiClient must be connected.");
        ei eiVar = (ei) googleApiClient.m123a(wx);
        if (eiVar == null) {
            z = false;
        }
        fq.m980a(z, "GoogleApiClient is not configured to use the AppState API. Pass AppStateManager.API into GoogleApiClient.Builder#addApi() to use this feature.");
        return eiVar;
    }

    public static PendingResult<StateDeletedResult> delete(GoogleApiClient googleApiClient, int stateKey) {
        return googleApiClient.m125b(new C15555(stateKey));
    }

    public static int getMaxNumKeys(GoogleApiClient googleApiClient) {
        return m75a(googleApiClient).dw();
    }

    public static int getMaxStateSize(GoogleApiClient googleApiClient) {
        return m75a(googleApiClient).dv();
    }

    public static PendingResult<StateListResult> list(GoogleApiClient googleApiClient) {
        return googleApiClient.m124a(new C15577());
    }

    public static PendingResult<StateResult> load(GoogleApiClient googleApiClient, int stateKey) {
        return googleApiClient.m124a(new C15566(stateKey));
    }

    public static PendingResult<StateResult> resolve(GoogleApiClient googleApiClient, int stateKey, String resolvedVersion, byte[] resolvedData) {
        return googleApiClient.m125b(new C15588(stateKey, resolvedVersion, resolvedData));
    }

    public static PendingResult<Status> signOut(GoogleApiClient googleApiClient) {
        return googleApiClient.m125b(new C15599());
    }

    public static void update(GoogleApiClient googleApiClient, int stateKey, byte[] data) {
        googleApiClient.m125b(new C15533(stateKey, data));
    }

    public static PendingResult<StateResult> updateImmediate(GoogleApiClient googleApiClient, int stateKey, byte[] data) {
        return googleApiClient.m125b(new C15544(stateKey, data));
    }
}
