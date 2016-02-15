package com.google.android.gms.analytics;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.ef;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.eg.C0866a;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.android.gms.analytics.c */
class C0769c implements C0202b {
    private Context mContext;
    private ServiceConnection sj;
    private C0204b sk;
    private C0205c sl;
    private eg sm;

    /* renamed from: com.google.android.gms.analytics.c.a */
    final class C0203a implements ServiceConnection {
        final /* synthetic */ C0769c sn;

        C0203a(C0769c c0769c) {
            this.sn = c0769c;
        }

        public void onServiceConnected(ComponentName component, IBinder binder) {
            aa.m34y("service connected, binder: " + binder);
            try {
                if ("com.google.android.gms.analytics.internal.IAnalyticsService".equals(binder.getInterfaceDescriptor())) {
                    aa.m34y("bound to service");
                    this.sn.sm = C0866a.m2100t(binder);
                    this.sn.bU();
                    return;
                }
            } catch (RemoteException e) {
            }
            this.sn.mContext.unbindService(this);
            this.sn.sj = null;
            this.sn.sl.m49a(2, null);
        }

        public void onServiceDisconnected(ComponentName component) {
            aa.m34y("service disconnected: " + component);
            this.sn.sj = null;
            this.sn.sk.onDisconnected();
        }
    }

    /* renamed from: com.google.android.gms.analytics.c.b */
    public interface C0204b {
        void onConnected();

        void onDisconnected();
    }

    /* renamed from: com.google.android.gms.analytics.c.c */
    public interface C0205c {
        void m49a(int i, Intent intent);
    }

    public C0769c(Context context, C0204b c0204b, C0205c c0205c) {
        this.mContext = context;
        if (c0204b == null) {
            throw new IllegalArgumentException("onConnectedListener cannot be null");
        }
        this.sk = c0204b;
        if (c0205c == null) {
            throw new IllegalArgumentException("onConnectionFailedListener cannot be null");
        }
        this.sl = c0205c;
    }

    private eg bS() {
        bT();
        return this.sm;
    }

    private void bU() {
        bV();
    }

    private void bV() {
        this.sk.onConnected();
    }

    public void m1585a(Map<String, String> map, long j, String str, List<ef> list) {
        try {
            bS().m856a(map, j, str, list);
        } catch (RemoteException e) {
            aa.m32w("sendHit failed: " + e);
        }
    }

    public void bR() {
        try {
            bS().bR();
        } catch (RemoteException e) {
            aa.m32w("clear hits failed: " + e);
        }
    }

    protected void bT() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public void connect() {
        Intent intent = new Intent("com.google.android.gms.analytics.service.START");
        intent.setComponent(new ComponentName(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, "com.google.android.gms.analytics.service.AnalyticsService"));
        intent.putExtra("app_package_name", this.mContext.getPackageName());
        if (this.sj != null) {
            aa.m32w("Calling connect() while still connected, missing disconnect().");
            return;
        }
        this.sj = new C0203a(this);
        boolean bindService = this.mContext.bindService(intent, this.sj, 129);
        aa.m34y("connect: bindService returned " + bindService + " for " + intent);
        if (!bindService) {
            this.sj = null;
            this.sl.m49a(1, null);
        }
    }

    public void disconnect() {
        this.sm = null;
        if (this.sj != null) {
            try {
                this.mContext.unbindService(this.sj);
            } catch (IllegalStateException e) {
            } catch (IllegalArgumentException e2) {
            }
            this.sj = null;
            this.sk.onDisconnected();
        }
    }

    public boolean isConnected() {
        return this.sm != null;
    }
}
