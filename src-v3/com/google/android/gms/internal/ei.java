package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.appstate.AppStateBuffer;
import com.google.android.gms.appstate.AppStateManager.StateConflictResult;
import com.google.android.gms.appstate.AppStateManager.StateDeletedResult;
import com.google.android.gms.appstate.AppStateManager.StateListResult;
import com.google.android.gms.appstate.AppStateManager.StateLoadedResult;
import com.google.android.gms.appstate.AppStateManager.StateResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.internal.ek.C0871a;
import com.google.android.gms.internal.ff.C0391b;
import com.google.android.gms.internal.ff.C0889d;
import com.google.android.gms.internal.ff.C1374e;

public final class ei extends ff<ek> {
    private final String wG;

    /* renamed from: com.google.android.gms.internal.ei.h */
    final class C0867h extends C0391b<C0244d<Status>> {
        final /* synthetic */ ei wI;
        private final Status wJ;

        public C0867h(ei eiVar, C0244d<Status> c0244d, Status status) {
            this.wI = eiVar;
            super(eiVar, c0244d);
            this.wJ = status;
        }

        public /* synthetic */ void m2101a(Object obj) {
            m2102c((C0244d) obj);
        }

        public void m2102c(C0244d<Status> c0244d) {
            c0244d.m132b(this.wJ);
        }

        protected void dx() {
        }
    }

    /* renamed from: com.google.android.gms.internal.ei.b */
    final class C1368b extends C0391b<C0244d<StateDeletedResult>> implements StateDeletedResult {
        final /* synthetic */ ei wI;
        private final Status wJ;
        private final int wK;

        public C1368b(ei eiVar, C0244d<StateDeletedResult> c0244d, Status status, int i) {
            this.wI = eiVar;
            super(eiVar, c0244d);
            this.wJ = status;
            this.wK = i;
        }

        public /* synthetic */ void m3012a(Object obj) {
            m3013c((C0244d) obj);
        }

        public void m3013c(C0244d<StateDeletedResult> c0244d) {
            c0244d.m132b(this);
        }

        protected void dx() {
        }

        public int getStateKey() {
            return this.wK;
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    /* renamed from: com.google.android.gms.internal.ei.d */
    final class C1369d extends C0889d<C0244d<StateListResult>> implements StateListResult {
        final /* synthetic */ ei wI;
        private final Status wJ;
        private final AppStateBuffer wL;

        public C1369d(ei eiVar, C0244d<StateListResult> c0244d, Status status, DataHolder dataHolder) {
            this.wI = eiVar;
            super(eiVar, c0244d, dataHolder);
            this.wJ = status;
            this.wL = new AppStateBuffer(dataHolder);
        }

        public void m3014a(C0244d<StateListResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }

        public AppStateBuffer getStateBuffer() {
            return this.wL;
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    /* renamed from: com.google.android.gms.internal.ei.f */
    final class C1370f extends C0889d<C0244d<StateResult>> implements StateConflictResult, StateLoadedResult, StateResult {
        final /* synthetic */ ei wI;
        private final Status wJ;
        private final int wK;
        private final AppStateBuffer wL;

        public C1370f(ei eiVar, C0244d<StateResult> c0244d, int i, DataHolder dataHolder) {
            this.wI = eiVar;
            super(eiVar, c0244d, dataHolder);
            this.wK = i;
            this.wJ = new Status(dataHolder.getStatusCode());
            this.wL = new AppStateBuffer(dataHolder);
        }

        private boolean dy() {
            return this.wJ.getStatusCode() == GamesStatusCodes.STATUS_REQUEST_UPDATE_PARTIAL_SUCCESS;
        }

        public void m3016a(C0244d<StateResult> c0244d, DataHolder dataHolder) {
            c0244d.m132b(this);
        }

        public StateConflictResult getConflictResult() {
            return dy() ? this : null;
        }

        public StateLoadedResult getLoadedResult() {
            return dy() ? null : this;
        }

        public byte[] getLocalData() {
            return this.wL.getCount() == 0 ? null : this.wL.get(0).getLocalData();
        }

        public String getResolvedVersion() {
            return this.wL.getCount() == 0 ? null : this.wL.get(0).getConflictVersion();
        }

        public byte[] getServerData() {
            return this.wL.getCount() == 0 ? null : this.wL.get(0).getConflictData();
        }

        public int getStateKey() {
            return this.wK;
        }

        public Status getStatus() {
            return this.wJ;
        }

        public void release() {
            this.wL.close();
        }
    }

    /* renamed from: com.google.android.gms.internal.ei.a */
    final class C1485a extends eh {
        private final C0244d<StateDeletedResult> wH;
        final /* synthetic */ ei wI;

        public C1485a(ei eiVar, C0244d<StateDeletedResult> c0244d) {
            this.wI = eiVar;
            this.wH = (C0244d) fq.m982b((Object) c0244d, (Object) "Result holder must not be null");
        }

        public void m3415b(int i, int i2) {
            this.wI.m2175a(new C1368b(this.wI, this.wH, new Status(i), i2));
        }
    }

    /* renamed from: com.google.android.gms.internal.ei.c */
    final class C1486c extends eh {
        private final C0244d<StateListResult> wH;
        final /* synthetic */ ei wI;

        public C1486c(ei eiVar, C0244d<StateListResult> c0244d) {
            this.wI = eiVar;
            this.wH = (C0244d) fq.m982b((Object) c0244d, (Object) "Result holder must not be null");
        }

        public void m3416a(DataHolder dataHolder) {
            this.wI.m2175a(new C1369d(this.wI, this.wH, new Status(dataHolder.getStatusCode()), dataHolder));
        }
    }

    /* renamed from: com.google.android.gms.internal.ei.e */
    final class C1487e extends eh {
        private final C0244d<StateResult> wH;
        final /* synthetic */ ei wI;

        public C1487e(ei eiVar, C0244d<StateResult> c0244d) {
            this.wI = eiVar;
            this.wH = (C0244d) fq.m982b((Object) c0244d, (Object) "Result holder must not be null");
        }

        public void m3417a(int i, DataHolder dataHolder) {
            this.wI.m2175a(new C1370f(this.wI, this.wH, i, dataHolder));
        }
    }

    /* renamed from: com.google.android.gms.internal.ei.g */
    final class C1488g extends eh {
        C0244d<Status> wH;
        final /* synthetic */ ei wI;

        public C1488g(ei eiVar, C0244d<Status> c0244d) {
            this.wI = eiVar;
            this.wH = (C0244d) fq.m982b((Object) c0244d, (Object) "Holder must not be null");
        }

        public void du() {
            this.wI.m2175a(new C0867h(this.wI, this.wH, new Status(0)));
        }
    }

    public ei(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, String[] strArr) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, strArr);
        this.wG = (String) fq.m985f(str);
    }

    public void m3018a(C0244d<StateListResult> c0244d) {
        try {
            ((ek) eM()).m861a(new C1486c(this, c0244d));
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void m3019a(C0244d<StateDeletedResult> c0244d, int i) {
        try {
            ((ek) eM()).m866b(new C1485a(this, c0244d), i);
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void m3020a(C0244d<StateResult> c0244d, int i, String str, byte[] bArr) {
        try {
            ((ek) eM()).m863a(new C1487e(this, c0244d), i, str, bArr);
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void m3021a(C0244d<StateResult> c0244d, int i, byte[] bArr) {
        if (c0244d == null) {
            ej ejVar = null;
        } else {
            Object c1487e = new C1487e(this, c0244d);
        }
        try {
            ((ek) eM()).m864a(ejVar, i, bArr);
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
        }
    }

    protected void m3022a(fm fmVar, C1374e c1374e) throws RemoteException {
        fmVar.m950a((fl) c1374e, (int) GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.wG, eL());
    }

    public void m3023b(C0244d<Status> c0244d) {
        try {
            ((ek) eM()).m865b(new C1488g(this, c0244d));
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void m3024b(C0244d<StateResult> c0244d, int i) {
        try {
            ((ek) eM()).m862a(new C1487e(this, c0244d), i);
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
        }
    }

    protected void m3025b(String... strArr) {
        boolean z = false;
        for (String equals : strArr) {
            if (equals.equals(Scopes.APP_STATE)) {
                z = true;
            }
        }
        fq.m980a(z, String.format("App State APIs requires %s to function.", new Object[]{Scopes.APP_STATE}));
    }

    protected String bg() {
        return "com.google.android.gms.appstate.service.START";
    }

    protected String bh() {
        return "com.google.android.gms.appstate.internal.IAppStateService";
    }

    public int dv() {
        try {
            return ((ek) eM()).dv();
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
            return 2;
        }
    }

    public int dw() {
        try {
            return ((ek) eM()).dw();
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
            return 2;
        }
    }

    protected /* synthetic */ IInterface m3026r(IBinder iBinder) {
        return m3027u(iBinder);
    }

    protected ek m3027u(IBinder iBinder) {
        return C0871a.m2115w(iBinder);
    }
}
