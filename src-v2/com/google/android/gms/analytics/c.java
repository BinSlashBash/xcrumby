/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package com.google.android.gms.analytics;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.internal.ef;
import com.google.android.gms.internal.eg;
import java.util.List;
import java.util.Map;

class c
implements com.google.android.gms.analytics.b {
    private Context mContext;
    private ServiceConnection sj;
    private b sk;
    private c sl;
    private eg sm;

    public c(Context context, b b2, c c2) {
        this.mContext = context;
        if (b2 == null) {
            throw new IllegalArgumentException("onConnectedListener cannot be null");
        }
        this.sk = b2;
        if (c2 == null) {
            throw new IllegalArgumentException("onConnectionFailedListener cannot be null");
        }
        this.sl = c2;
    }

    private eg bS() {
        this.bT();
        return this.sm;
    }

    private void bU() {
        this.bV();
    }

    private void bV() {
        this.sk.onConnected();
    }

    @Override
    public void a(Map<String, String> map, long l2, String string2, List<ef> list) {
        try {
            this.bS().a(map, l2, string2, list);
            return;
        }
        catch (RemoteException var1_2) {
            aa.w("sendHit failed: " + (Object)var1_2);
            return;
        }
    }

    @Override
    public void bR() {
        try {
            this.bS().bR();
            return;
        }
        catch (RemoteException var1_1) {
            aa.w("clear hits failed: " + (Object)var1_1);
            return;
        }
    }

    protected void bT() {
        if (!this.isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void connect() {
        Intent intent = new Intent("com.google.android.gms.analytics.service.START");
        intent.setComponent(new ComponentName("com.google.android.gms", "com.google.android.gms.analytics.service.AnalyticsService"));
        intent.putExtra("app_package_name", this.mContext.getPackageName());
        if (this.sj != null) {
            aa.w("Calling connect() while still connected, missing disconnect().");
            return;
        } else {
            this.sj = new a();
            boolean bl2 = this.mContext.bindService(intent, this.sj, 129);
            aa.y("connect: bindService returned " + bl2 + " for " + (Object)intent);
            if (bl2) return;
            {
                this.sj = null;
                this.sl.a(1, null);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void disconnect() {
        this.sm = null;
        if (this.sj != null) {
            try {
                this.mContext.unbindService(this.sj);
            }
            catch (IllegalArgumentException var1_1) {
            }
            catch (IllegalStateException var1_2) {}
            this.sj = null;
            this.sk.onDisconnected();
        }
    }

    public boolean isConnected() {
        if (this.sm != null) {
            return true;
        }
        return false;
    }

    final class a
    implements ServiceConnection {
        a() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            aa.y("service connected, binder: " + (Object)iBinder);
            try {
                if ("com.google.android.gms.analytics.internal.IAnalyticsService".equals(iBinder.getInterfaceDescriptor())) {
                    aa.y("bound to service");
                    c.this.sm = eg.a.t(iBinder);
                    c.this.bU();
                    return;
                }
            }
            catch (RemoteException var1_2) {
                // empty catch block
            }
            c.this.mContext.unbindService((ServiceConnection)this);
            c.this.sj = null;
            c.this.sl.a(2, null);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            aa.y("service disconnected: " + (Object)componentName);
            c.this.sj = null;
            c.this.sk.onDisconnected();
        }
    }

    public static interface b {
        public void onConnected();

        public void onDisconnected();
    }

    public static interface c {
        public void a(int var1, Intent var2);
    }

}

