/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public final class fg {
    private final b Do;
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> Dp = new ArrayList();
    final ArrayList<GoogleApiClient.ConnectionCallbacks> Dq = new ArrayList();
    private boolean Dr = false;
    private final ArrayList<GooglePlayServicesClient.OnConnectionFailedListener> Ds = new ArrayList();
    private final Handler mHandler;

    public fg(Context context, Looper looper, b b2) {
        this.Do = b2;
        this.mHandler = new a(looper);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void O(int var1_1) {
        this.mHandler.removeMessages(1);
        var2_2 = this.Dp;
        // MONITORENTER : var2_2
        this.Dr = true;
        var3_3 = new ArrayList<GoogleApiClient.ConnectionCallbacks>(this.Dp).iterator();
        do {
            if (!var3_3.hasNext()) ** GOTO lbl-1000
            var4_4 = var3_3.next();
            if (!this.Do.em()) lbl-1000: // 2 sources:
            {
                this.Dr = false;
                // MONITOREXIT : var2_2
                return;
            }
            if (!this.Dp.contains(var4_4)) continue;
            var4_4.onConnectionSuspended(var1_1);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(ConnectionResult connectionResult) {
        this.mHandler.removeMessages(1);
        ArrayList<GooglePlayServicesClient.OnConnectionFailedListener> arrayList = this.Ds;
        synchronized (arrayList) {
            Iterator<GooglePlayServicesClient.OnConnectionFailedListener> iterator = new ArrayList<GooglePlayServicesClient.OnConnectionFailedListener>(this.Ds).iterator();
            while (iterator.hasNext()) {
                GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener = iterator.next();
                if (!this.Do.em()) {
                    return;
                }
                if (!this.Ds.contains(onConnectionFailedListener)) continue;
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void b(Bundle var1_1) {
        var3_2 = true;
        var4_3 = this.Dp;
        // MONITORENTER : var4_3
        var2_4 = this.Dr == false;
        fq.x(var2_4);
        this.mHandler.removeMessages(1);
        this.Dr = true;
        var2_4 = this.Dq.size() == 0 ? var3_2 : false;
        fq.x(var2_4);
        var5_5 = new ArrayList<GoogleApiClient.ConnectionCallbacks>(this.Dp).iterator();
        do {
            if (!var5_5.hasNext()) ** GOTO lbl-1000
            var6_6 = var5_5.next();
            if (!this.Do.em() || !this.Do.isConnected()) lbl-1000: // 2 sources:
            {
                this.Dq.clear();
                this.Dr = false;
                // MONITOREXIT : var4_3
                return;
            }
            if (this.Dq.contains(var6_6)) continue;
            var6_6.onConnected(var1_1);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void bV() {
        ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList = this.Dp;
        synchronized (arrayList) {
            this.b(this.Do.dG());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        fq.f(connectionCallbacks);
        ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList = this.Dp;
        synchronized (arrayList) {
            return this.Dp.contains(connectionCallbacks);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        fq.f(onConnectionFailedListener);
        ArrayList<GooglePlayServicesClient.OnConnectionFailedListener> arrayList = this.Ds;
        synchronized (arrayList) {
            return this.Ds.contains(onConnectionFailedListener);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        fq.f(connectionCallbacks);
        ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList = this.Dp;
        // MONITORENTER : arrayList
        if (this.Dp.contains(connectionCallbacks)) {
            Log.w((String)"GmsClientEvents", (String)("registerConnectionCallbacks(): listener " + connectionCallbacks + " is already registered"));
        } else {
            this.Dp.add(connectionCallbacks);
        }
        // MONITOREXIT : arrayList
        if (!this.Do.isConnected()) return;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, (Object)connectionCallbacks));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        fq.f(onConnectionFailedListener);
        ArrayList<GooglePlayServicesClient.OnConnectionFailedListener> arrayList = this.Ds;
        synchronized (arrayList) {
            if (this.Ds.contains(onConnectionFailedListener)) {
                Log.w((String)"GmsClientEvents", (String)("registerConnectionFailedListener(): listener " + onConnectionFailedListener + " is already registered"));
            } else {
                this.Ds.add(onConnectionFailedListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        fq.f(connectionCallbacks);
        ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList = this.Dp;
        synchronized (arrayList) {
            if (this.Dp != null) {
                if (!this.Dp.remove(connectionCallbacks)) {
                    Log.w((String)"GmsClientEvents", (String)("unregisterConnectionCallbacks(): listener " + connectionCallbacks + " not found"));
                } else if (this.Dr) {
                    this.Dq.add(connectionCallbacks);
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        fq.f(onConnectionFailedListener);
        ArrayList<GooglePlayServicesClient.OnConnectionFailedListener> arrayList = this.Ds;
        synchronized (arrayList) {
            if (this.Ds != null && !this.Ds.remove(onConnectionFailedListener)) {
                Log.w((String)"GmsClientEvents", (String)("unregisterConnectionFailedListener(): listener " + onConnectionFailedListener + " not found"));
            }
            return;
        }
    }

    final class a
    extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void handleMessage(Message message) {
            if (message.what != 1) {
                Log.wtf((String)"GmsClientEvents", (String)"Don't know how to handle this message.");
                return;
            }
            ArrayList arrayList = fg.this.Dp;
            synchronized (arrayList) {
                if (fg.this.Do.em() && fg.this.Do.isConnected() && fg.this.Dp.contains(message.obj)) {
                    Bundle bundle = fg.this.Do.dG();
                    ((GoogleApiClient.ConnectionCallbacks)message.obj).onConnected(bundle);
                }
                return;
            }
        }
    }

    public static interface b {
        public Bundle dG();

        public boolean em();

        public boolean isConnected();
    }

}

