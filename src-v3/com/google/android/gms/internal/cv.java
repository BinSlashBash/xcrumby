package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.internal.cu.C0353a;

public abstract class cv extends C0359do {
    private final cx mQ;
    private final C0353a pc;

    /* renamed from: com.google.android.gms.internal.cv.a */
    public static final class C1365a extends cv {
        private final Context mContext;

        public C1365a(Context context, cx cxVar, C0353a c0353a) {
            super(cxVar, c0353a);
            this.mContext = context;
        }

        public void be() {
        }

        public db bf() {
            return dc.m3004a(this.mContext, new ax(), new bg());
        }
    }

    /* renamed from: com.google.android.gms.internal.cv.b */
    public static final class C1366b extends cv implements ConnectionCallbacks, OnConnectionFailedListener {
        private final Object li;
        private final C0353a pc;
        private final cw pd;

        public C1366b(Context context, cx cxVar, C0353a c0353a) {
            super(cxVar, c0353a);
            this.li = new Object();
            this.pc = c0353a;
            this.pd = new cw(context, this, this, cxVar.kK.rs);
            this.pd.connect();
        }

        public void be() {
            synchronized (this.li) {
                if (this.pd.isConnected() || this.pd.isConnecting()) {
                    this.pd.disconnect();
                }
            }
        }

        public db bf() {
            db bi;
            synchronized (this.li) {
                try {
                    bi = this.pd.bi();
                } catch (IllegalStateException e) {
                    bi = null;
                }
            }
            return bi;
        }

        public void onConnected(Bundle connectionHint) {
            start();
        }

        public void onConnectionFailed(ConnectionResult result) {
            this.pc.m713a(new cz(0));
        }

        public void onDisconnected() {
            dw.m819v("Disconnected from remote ad request service.");
        }
    }

    public cv(cx cxVar, C0353a c0353a) {
        this.mQ = cxVar;
        this.pc = c0353a;
    }

    private static cz m2090a(db dbVar, cx cxVar) {
        try {
            return dbVar.m723b(cxVar);
        } catch (Throwable e) {
            dw.m817c("Could not fetch ad response from ad request service.", e);
            return null;
        }
    }

    public final void aY() {
        try {
            cz czVar;
            db bf = bf();
            if (bf == null) {
                czVar = new cz(0);
            } else {
                czVar = m2090a(bf, this.mQ);
                if (czVar == null) {
                    czVar = new cz(0);
                }
            }
            be();
            this.pc.m713a(czVar);
        } catch (Throwable th) {
            be();
        }
    }

    public abstract void be();

    public abstract db bf();

    public final void onStop() {
        be();
    }
}
