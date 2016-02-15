package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import java.util.ArrayList;
import java.util.Iterator;

public final class fg {
    private final C0394b Do;
    private final ArrayList<ConnectionCallbacks> Dp;
    final ArrayList<ConnectionCallbacks> Dq;
    private boolean Dr;
    private final ArrayList<OnConnectionFailedListener> Ds;
    private final Handler mHandler;

    /* renamed from: com.google.android.gms.internal.fg.a */
    final class C0393a extends Handler {
        final /* synthetic */ fg Dt;

        public C0393a(fg fgVar, Looper looper) {
            this.Dt = fgVar;
            super(looper);
        }

        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                synchronized (this.Dt.Dp) {
                    if (this.Dt.Do.em() && this.Dt.Do.isConnected() && this.Dt.Dp.contains(msg.obj)) {
                        ((ConnectionCallbacks) msg.obj).onConnected(this.Dt.Do.dG());
                    }
                }
                return;
            }
            Log.wtf("GmsClientEvents", "Don't know how to handle this message.");
        }
    }

    /* renamed from: com.google.android.gms.internal.fg.b */
    public interface C0394b {
        Bundle dG();

        boolean em();

        boolean isConnected();
    }

    public fg(Context context, Looper looper, C0394b c0394b) {
        this.Dp = new ArrayList();
        this.Dq = new ArrayList();
        this.Dr = false;
        this.Ds = new ArrayList();
        this.Do = c0394b;
        this.mHandler = new C0393a(this, looper);
    }

    public void m925O(int i) {
        this.mHandler.removeMessages(1);
        synchronized (this.Dp) {
            this.Dr = true;
            Iterator it = new ArrayList(this.Dp).iterator();
            while (it.hasNext()) {
                ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) it.next();
                if (!this.Do.em()) {
                    break;
                } else if (this.Dp.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnectionSuspended(i);
                }
            }
            this.Dr = false;
        }
    }

    public void m926a(ConnectionResult connectionResult) {
        this.mHandler.removeMessages(1);
        synchronized (this.Ds) {
            Iterator it = new ArrayList(this.Ds).iterator();
            while (it.hasNext()) {
                OnConnectionFailedListener onConnectionFailedListener = (OnConnectionFailedListener) it.next();
                if (!this.Do.em()) {
                    return;
                } else if (this.Ds.contains(onConnectionFailedListener)) {
                    onConnectionFailedListener.onConnectionFailed(connectionResult);
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void m927b(android.os.Bundle r6) {
        /*
        r5 = this;
        r1 = 0;
        r0 = 1;
        r3 = r5.Dp;
        monitor-enter(r3);
        r2 = r5.Dr;	 Catch:{ all -> 0x0062 }
        if (r2 != 0) goto L_0x0052;
    L_0x0009:
        r2 = r0;
    L_0x000a:
        com.google.android.gms.internal.fq.m986x(r2);	 Catch:{ all -> 0x0062 }
        r2 = r5.mHandler;	 Catch:{ all -> 0x0062 }
        r4 = 1;
        r2.removeMessages(r4);	 Catch:{ all -> 0x0062 }
        r2 = 1;
        r5.Dr = r2;	 Catch:{ all -> 0x0062 }
        r2 = r5.Dq;	 Catch:{ all -> 0x0062 }
        r2 = r2.size();	 Catch:{ all -> 0x0062 }
        if (r2 != 0) goto L_0x0054;
    L_0x001e:
        com.google.android.gms.internal.fq.m986x(r0);	 Catch:{ all -> 0x0062 }
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x0062 }
        r1 = r5.Dp;	 Catch:{ all -> 0x0062 }
        r0.<init>(r1);	 Catch:{ all -> 0x0062 }
        r1 = r0.iterator();	 Catch:{ all -> 0x0062 }
    L_0x002c:
        r0 = r1.hasNext();	 Catch:{ all -> 0x0062 }
        if (r0 == 0) goto L_0x0048;
    L_0x0032:
        r0 = r1.next();	 Catch:{ all -> 0x0062 }
        r0 = (com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks) r0;	 Catch:{ all -> 0x0062 }
        r2 = r5.Do;	 Catch:{ all -> 0x0062 }
        r2 = r2.em();	 Catch:{ all -> 0x0062 }
        if (r2 == 0) goto L_0x0048;
    L_0x0040:
        r2 = r5.Do;	 Catch:{ all -> 0x0062 }
        r2 = r2.isConnected();	 Catch:{ all -> 0x0062 }
        if (r2 != 0) goto L_0x0056;
    L_0x0048:
        r0 = r5.Dq;	 Catch:{ all -> 0x0062 }
        r0.clear();	 Catch:{ all -> 0x0062 }
        r0 = 0;
        r5.Dr = r0;	 Catch:{ all -> 0x0062 }
        monitor-exit(r3);	 Catch:{ all -> 0x0062 }
        return;
    L_0x0052:
        r2 = r1;
        goto L_0x000a;
    L_0x0054:
        r0 = r1;
        goto L_0x001e;
    L_0x0056:
        r2 = r5.Dq;	 Catch:{ all -> 0x0062 }
        r2 = r2.contains(r0);	 Catch:{ all -> 0x0062 }
        if (r2 != 0) goto L_0x002c;
    L_0x005e:
        r0.onConnected(r6);	 Catch:{ all -> 0x0062 }
        goto L_0x002c;
    L_0x0062:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0062 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.fg.b(android.os.Bundle):void");
    }

    protected void bV() {
        synchronized (this.Dp) {
            m927b(this.Do.dG());
        }
    }

    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks listener) {
        boolean contains;
        fq.m985f(listener);
        synchronized (this.Dp) {
            contains = this.Dp.contains(listener);
        }
        return contains;
    }

    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener listener) {
        boolean contains;
        fq.m985f(listener);
        synchronized (this.Ds) {
            contains = this.Ds.contains(listener);
        }
        return contains;
    }

    public void registerConnectionCallbacks(ConnectionCallbacks listener) {
        fq.m985f(listener);
        synchronized (this.Dp) {
            if (this.Dp.contains(listener)) {
                Log.w("GmsClientEvents", "registerConnectionCallbacks(): listener " + listener + " is already registered");
            } else {
                this.Dp.add(listener);
            }
        }
        if (this.Do.isConnected()) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, listener));
        }
    }

    public void registerConnectionFailedListener(OnConnectionFailedListener listener) {
        fq.m985f(listener);
        synchronized (this.Ds) {
            if (this.Ds.contains(listener)) {
                Log.w("GmsClientEvents", "registerConnectionFailedListener(): listener " + listener + " is already registered");
            } else {
                this.Ds.add(listener);
            }
        }
    }

    public void unregisterConnectionCallbacks(ConnectionCallbacks listener) {
        fq.m985f(listener);
        synchronized (this.Dp) {
            if (this.Dp != null) {
                if (!this.Dp.remove(listener)) {
                    Log.w("GmsClientEvents", "unregisterConnectionCallbacks(): listener " + listener + " not found");
                } else if (this.Dr) {
                    this.Dq.add(listener);
                }
            }
        }
    }

    public void unregisterConnectionFailedListener(OnConnectionFailedListener listener) {
        fq.m985f(listener);
        synchronized (this.Ds) {
            if (!(this.Ds == null || this.Ds.remove(listener))) {
                Log.w("GmsClientEvents", "unregisterConnectionFailedListener(): listener " + listener + " not found");
            }
        }
    }
}
