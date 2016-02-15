/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.RemoteException
 *  android.util.Log
 *  com.google.android.gms.internal.ff.b
 *  com.google.android.gms.internal.ff.d
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.appstate.AppState;
import com.google.android.gms.appstate.AppStateBuffer;
import com.google.android.gms.appstate.AppStateManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.eh;
import com.google.android.gms.internal.ej;
import com.google.android.gms.internal.ek;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.fl;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.fq;

public final class ei
extends ff<ek> {
    private final String wG;

    public ei(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String string2, String[] arrstring) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, arrstring);
        this.wG = fq.f(string2);
    }

    @Override
    public void a(a.d<AppStateManager.StateListResult> d2) {
        try {
            ((ek)this.eM()).a(new c(d2));
            return;
        }
        catch (RemoteException var1_2) {
            Log.w((String)"AppStateClient", (String)"service died");
            return;
        }
    }

    public void a(a.d<AppStateManager.StateDeletedResult> d2, int n2) {
        try {
            ((ek)this.eM()).b(new a(d2), n2);
            return;
        }
        catch (RemoteException var1_2) {
            Log.w((String)"AppStateClient", (String)"service died");
            return;
        }
    }

    public void a(a.d<AppStateManager.StateResult> d2, int n2, String string2, byte[] arrby) {
        try {
            ((ek)this.eM()).a(new e(d2), n2, string2, arrby);
            return;
        }
        catch (RemoteException var1_2) {
            Log.w((String)"AppStateClient", (String)"service died");
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void a(a.d<AppStateManager.StateResult> var1_1, int var2_3, byte[] var3_4) {
        if (var1_1 != null) ** GOTO lbl-1000
        var1_1 = null;
        try {}
        catch (RemoteException var1_2) {
            Log.w((String)"AppStateClient", (String)"service died");
            return;
        }
        ** GOTO lbl9
lbl-1000: // 1 sources:
        {
            var1_1 = new e((a.d<AppStateManager.StateResult>)var1_1);
lbl9: // 2 sources:
            ((ek)this.eM()).a((ej)var1_1, var2_3, var3_4);
            return;
        }
    }

    @Override
    protected void a(fm fm2, ff.e e2) throws RemoteException {
        fm2.a((fl)e2, 4452000, this.getContext().getPackageName(), this.wG, this.eL());
    }

    public void b(a.d<Status> d2) {
        try {
            ((ek)this.eM()).b(new g(d2));
            return;
        }
        catch (RemoteException var1_2) {
            Log.w((String)"AppStateClient", (String)"service died");
            return;
        }
    }

    public void b(a.d<AppStateManager.StateResult> d2, int n2) {
        try {
            ((ek)this.eM()).a(new e(d2), n2);
            return;
        }
        catch (RemoteException var1_2) {
            Log.w((String)"AppStateClient", (String)"service died");
            return;
        }
    }

    @Override
    protected /* varargs */ void b(String ... arrstring) {
        boolean bl2 = false;
        for (int i2 = 0; i2 < arrstring.length; ++i2) {
            if (!arrstring[i2].equals("https://www.googleapis.com/auth/appstate")) continue;
            bl2 = true;
        }
        fq.a(bl2, String.format("App State APIs requires %s to function.", "https://www.googleapis.com/auth/appstate"));
    }

    @Override
    protected String bg() {
        return "com.google.android.gms.appstate.service.START";
    }

    @Override
    protected String bh() {
        return "com.google.android.gms.appstate.internal.IAppStateService";
    }

    public int dv() {
        try {
            int n2 = ((ek)this.eM()).dv();
            return n2;
        }
        catch (RemoteException var2_2) {
            Log.w((String)"AppStateClient", (String)"service died");
            return 2;
        }
    }

    public int dw() {
        try {
            int n2 = ((ek)this.eM()).dw();
            return n2;
        }
        catch (RemoteException var2_2) {
            Log.w((String)"AppStateClient", (String)"service died");
            return 2;
        }
    }

    @Override
    protected /* synthetic */ IInterface r(IBinder iBinder) {
        return this.u(iBinder);
    }

    protected ek u(IBinder iBinder) {
        return ek.a.w(iBinder);
    }

    final class a
    extends eh {
        private final a.d<AppStateManager.StateDeletedResult> wH;

        public a(a.d<AppStateManager.StateDeletedResult> d2) {
            this.wH = fq.b(d2, (Object)"Result holder must not be null");
        }

        @Override
        public void b(int n2, int n3) {
            Status status = new Status(n2);
            ei.this.a((ff.b)((Object)new b(this.wH, status, n3)));
        }
    }

    final class b
    extends com.google.android.gms.internal.ff.b<a.d<AppStateManager.StateDeletedResult>>
    implements AppStateManager.StateDeletedResult {
        private final Status wJ;
        private final int wK;

        public b(a.d<AppStateManager.StateDeletedResult> d2, Status status, int n2) {
            super(d2);
            this.wJ = status;
            this.wK = n2;
        }

        public /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        public void c(a.d<AppStateManager.StateDeletedResult> d2) {
            d2.b(this);
        }

        protected void dx() {
        }

        @Override
        public int getStateKey() {
            return this.wK;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class c
    extends eh {
        private final a.d<AppStateManager.StateListResult> wH;

        public c(a.d<AppStateManager.StateListResult> d2) {
            this.wH = fq.b(d2, (Object)"Result holder must not be null");
        }

        @Override
        public void a(DataHolder dataHolder) {
            Status status = new Status(dataHolder.getStatusCode());
            ei.this.a((ff.b)((Object)new d(this.wH, status, dataHolder)));
        }
    }

    final class d
    extends com.google.android.gms.internal.ff.d<a.d<AppStateManager.StateListResult>>
    implements AppStateManager.StateListResult {
        private final Status wJ;
        private final AppStateBuffer wL;

        public d(a.d<AppStateManager.StateListResult> d2, Status status, DataHolder dataHolder) {
            super(d2, dataHolder);
            this.wJ = status;
            this.wL = new AppStateBuffer(dataHolder);
        }

        public void a(a.d<AppStateManager.StateListResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }

        @Override
        public AppStateBuffer getStateBuffer() {
            return this.wL;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

    final class e
    extends eh {
        private final a.d<AppStateManager.StateResult> wH;

        public e(a.d<AppStateManager.StateResult> d2) {
            this.wH = fq.b(d2, (Object)"Result holder must not be null");
        }

        @Override
        public void a(int n2, DataHolder dataHolder) {
            ei.this.a((ff.b)((Object)new f(this.wH, n2, dataHolder)));
        }
    }

    final class f
    extends com.google.android.gms.internal.ff.d<a.d<AppStateManager.StateResult>>
    implements AppStateManager.StateConflictResult,
    AppStateManager.StateLoadedResult,
    AppStateManager.StateResult {
        private final Status wJ;
        private final int wK;
        private final AppStateBuffer wL;

        public f(a.d<AppStateManager.StateResult> d2, int n2, DataHolder dataHolder) {
            super(d2, dataHolder);
            this.wK = n2;
            this.wJ = new Status(dataHolder.getStatusCode());
            this.wL = new AppStateBuffer(dataHolder);
        }

        private boolean dy() {
            if (this.wJ.getStatusCode() == 2000) {
                return true;
            }
            return false;
        }

        public void a(a.d<AppStateManager.StateResult> d2, DataHolder dataHolder) {
            d2.b(this);
        }

        @Override
        public AppStateManager.StateConflictResult getConflictResult() {
            if (this.dy()) {
                return this;
            }
            return null;
        }

        @Override
        public AppStateManager.StateLoadedResult getLoadedResult() {
            f f2 = this;
            if (this.dy()) {
                f2 = null;
            }
            return f2;
        }

        @Override
        public byte[] getLocalData() {
            if (this.wL.getCount() == 0) {
                return null;
            }
            return this.wL.get(0).getLocalData();
        }

        @Override
        public String getResolvedVersion() {
            if (this.wL.getCount() == 0) {
                return null;
            }
            return this.wL.get(0).getConflictVersion();
        }

        @Override
        public byte[] getServerData() {
            if (this.wL.getCount() == 0) {
                return null;
            }
            return this.wL.get(0).getConflictData();
        }

        @Override
        public int getStateKey() {
            return this.wK;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }

        @Override
        public void release() {
            this.wL.close();
        }
    }

    final class g
    extends eh {
        a.d<Status> wH;

        public g(a.d<Status> d2) {
            this.wH = fq.b(d2, (Object)"Holder must not be null");
        }

        @Override
        public void du() {
            Status status = new Status(0);
            ei.this.a((ff.b)((Object)new h(this.wH, status)));
        }
    }

    final class h
    extends com.google.android.gms.internal.ff.b<a.d<Status>> {
        private final Status wJ;

        public h(a.d<Status> d2, Status status) {
            super(d2);
            this.wJ = status;
        }

        public /* synthetic */ void a(Object object) {
            this.c((a.d)object);
        }

        public void c(a.d<Status> d2) {
            d2.b(this.wJ);
        }

        protected void dx() {
        }
    }

}

