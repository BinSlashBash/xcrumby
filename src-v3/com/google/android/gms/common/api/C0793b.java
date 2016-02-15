package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.C0239a;
import com.google.android.gms.common.api.Api.C0240b;
import com.google.android.gms.common.api.Api.C0241c;
import com.google.android.gms.common.api.C0245a.C1299b;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fg;
import com.google.android.gms.internal.fg.C0394b;
import com.google.android.gms.internal.fq;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.google.android.gms.common.api.b */
final class C0793b implements GoogleApiClient {
    private final C0246a AL;
    private final Looper AS;
    private final Lock Ba;
    private final Condition Bb;
    private final fg Bc;
    final Queue<C0248c<?>> Bd;
    private ConnectionResult Be;
    private int Bf;
    private int Bg;
    private int Bh;
    private boolean Bi;
    private int Bj;
    private long Bk;
    final Handler Bl;
    private final Bundle Bm;
    private final Map<C0241c<?>, C0239a> Bn;
    private boolean Bo;
    final Set<C0248c<?>> Bp;
    final ConnectionCallbacks Bq;
    private final C0394b Br;

    /* renamed from: com.google.android.gms.common.api.b.a */
    interface C0246a {
        void m133b(C0248c<?> c0248c);
    }

    /* renamed from: com.google.android.gms.common.api.b.b */
    class C0247b extends Handler {
        final /* synthetic */ C0793b Bs;

        C0247b(C0793b c0793b, Looper looper) {
            this.Bs = c0793b;
            super(looper);
        }

        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                this.Bs.Ba.lock();
                try {
                    if (!(this.Bs.isConnected() || this.Bs.isConnecting())) {
                        this.Bs.connect();
                    }
                    this.Bs.Ba.unlock();
                } catch (Throwable th) {
                    this.Bs.Ba.unlock();
                }
            } else {
                Log.wtf("GoogleApiClientImpl", "Don't know how to handle this message.");
            }
        }
    }

    /* renamed from: com.google.android.gms.common.api.b.c */
    interface C0248c<A extends C0239a> {
        void m134a(C0246a c0246a);

        void m135b(A a) throws DeadObjectException;

        void cancel();

        C0241c<A> ea();

        int ef();

        void m136k(Status status);
    }

    /* renamed from: com.google.android.gms.common.api.b.1 */
    class C07901 implements C0246a {
        final /* synthetic */ C0793b Bs;

        C07901(C0793b c0793b) {
            this.Bs = c0793b;
        }

        public void m1664b(C0248c<?> c0248c) {
            this.Bs.Ba.lock();
            try {
                this.Bs.Bp.remove(c0248c);
            } finally {
                this.Bs.Ba.unlock();
            }
        }
    }

    /* renamed from: com.google.android.gms.common.api.b.2 */
    class C07912 implements ConnectionCallbacks {
        final /* synthetic */ C0793b Bs;

        C07912(C0793b c0793b) {
            this.Bs = c0793b;
        }

        public void onConnected(Bundle connectionHint) {
            this.Bs.Ba.lock();
            try {
                if (this.Bs.Bg == 1) {
                    if (connectionHint != null) {
                        this.Bs.Bm.putAll(connectionHint);
                    }
                    this.Bs.ei();
                }
                this.Bs.Ba.unlock();
            } catch (Throwable th) {
                this.Bs.Ba.unlock();
            }
        }

        public void onConnectionSuspended(int cause) {
            this.Bs.Ba.lock();
            try {
                this.Bs.m1665E(cause);
                switch (cause) {
                    case Std.STD_FILE /*1*/:
                        if (!this.Bs.ek()) {
                            this.Bs.Bh = 2;
                            this.Bs.Bl.sendMessageDelayed(this.Bs.Bl.obtainMessage(1), this.Bs.Bk);
                            break;
                        }
                        this.Bs.Ba.unlock();
                        return;
                    case Std.STD_URL /*2*/:
                        this.Bs.connect();
                        break;
                }
                this.Bs.Ba.unlock();
            } catch (Throwable th) {
                this.Bs.Ba.unlock();
            }
        }
    }

    /* renamed from: com.google.android.gms.common.api.b.3 */
    class C07923 implements C0394b {
        final /* synthetic */ C0793b Bs;

        C07923(C0793b c0793b) {
            this.Bs = c0793b;
        }

        public Bundle dG() {
            return null;
        }

        public boolean em() {
            return this.Bs.Bo;
        }

        public boolean isConnected() {
            return this.Bs.isConnected();
        }
    }

    /* renamed from: com.google.android.gms.common.api.b.4 */
    class C13004 implements OnConnectionFailedListener {
        final /* synthetic */ C0793b Bs;
        final /* synthetic */ C0240b Bt;

        C13004(C0793b c0793b, C0240b c0240b) {
            this.Bs = c0793b;
            this.Bt = c0240b;
        }

        public void onConnectionFailed(ConnectionResult result) {
            this.Bs.Ba.lock();
            try {
                if (this.Bs.Be == null || this.Bt.getPriority() < this.Bs.Bf) {
                    this.Bs.Be = result;
                    this.Bs.Bf = this.Bt.getPriority();
                }
                this.Bs.ei();
            } finally {
                this.Bs.Ba.unlock();
            }
        }
    }

    public C0793b(Context context, Looper looper, fc fcVar, Map<Api<?>, ApiOptions> map, Set<ConnectionCallbacks> set, Set<OnConnectionFailedListener> set2) {
        this.Ba = new ReentrantLock();
        this.Bb = this.Ba.newCondition();
        this.Bd = new LinkedList();
        this.Bg = 4;
        this.Bh = 0;
        this.Bi = false;
        this.Bk = 5000;
        this.Bm = new Bundle();
        this.Bn = new HashMap();
        this.Bp = new HashSet();
        this.AL = new C07901(this);
        this.Bq = new C07912(this);
        this.Br = new C07923(this);
        this.Bc = new fg(context, looper, this.Br);
        this.AS = looper;
        this.Bl = new C0247b(this, looper);
        for (ConnectionCallbacks registerConnectionCallbacks : set) {
            this.Bc.registerConnectionCallbacks(registerConnectionCallbacks);
        }
        for (OnConnectionFailedListener registerConnectionFailedListener : set2) {
            this.Bc.registerConnectionFailedListener(registerConnectionFailedListener);
        }
        for (Api api : map.keySet()) {
            C0240b dY = api.dY();
            Object obj = map.get(api);
            this.Bn.put(api.ea(), C0793b.m1667a(dY, obj, context, looper, fcVar, this.Bq, new C13004(this, dY)));
        }
    }

    private void m1665E(int i) {
        this.Ba.lock();
        try {
            if (this.Bg != 3) {
                if (i == -1) {
                    Iterator it;
                    C0248c c0248c;
                    if (isConnecting()) {
                        it = this.Bd.iterator();
                        while (it.hasNext()) {
                            c0248c = (C0248c) it.next();
                            if (c0248c.ef() != 1) {
                                c0248c.cancel();
                                it.remove();
                            }
                        }
                    } else {
                        this.Bd.clear();
                    }
                    for (C0248c c0248c2 : this.Bp) {
                        c0248c2.cancel();
                    }
                    this.Bp.clear();
                    if (this.Be == null && !this.Bd.isEmpty()) {
                        this.Bi = true;
                        return;
                    }
                }
                boolean isConnecting = isConnecting();
                boolean isConnected = isConnected();
                this.Bg = 3;
                if (isConnecting) {
                    if (i == -1) {
                        this.Be = null;
                    }
                    this.Bb.signalAll();
                }
                this.Bo = false;
                for (C0239a c0239a : this.Bn.values()) {
                    if (c0239a.isConnected()) {
                        c0239a.disconnect();
                    }
                }
                this.Bo = true;
                this.Bg = 4;
                if (isConnected) {
                    if (i != -1) {
                        this.Bc.m925O(i);
                    }
                    this.Bo = false;
                }
            }
            this.Ba.unlock();
        } finally {
            this.Ba.unlock();
        }
    }

    private static <C extends C0239a, O> C m1667a(C0240b<C, O> c0240b, Object obj, Context context, Looper looper, fc fcVar, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        return c0240b.m122a(context, looper, fcVar, obj, connectionCallbacks, onConnectionFailedListener);
    }

    private <A extends C0239a> void m1669a(C0248c<A> c0248c) throws DeadObjectException {
        boolean z = true;
        this.Ba.lock();
        try {
            boolean z2 = isConnected() || ek();
            fq.m980a(z2, "GoogleApiClient is not connected yet.");
            if (c0248c.ea() == null) {
                z = false;
            }
            fq.m984b(z, (Object) "This task can not be executed or enqueued (it's probably a Batch or malformed)");
            this.Bp.add(c0248c);
            c0248c.m134a(this.AL);
            if (ek()) {
                c0248c.m136k(new Status(8));
                return;
            }
            c0248c.m135b(m1681a(c0248c.ea()));
            this.Ba.unlock();
        } finally {
            this.Ba.unlock();
        }
    }

    private void ei() {
        this.Ba.lock();
        try {
            this.Bj--;
            if (this.Bj == 0) {
                if (this.Be != null) {
                    this.Bi = false;
                    m1665E(3);
                    if (ek()) {
                        this.Bh--;
                    }
                    if (ek()) {
                        this.Bl.sendMessageDelayed(this.Bl.obtainMessage(1), this.Bk);
                    } else {
                        this.Bc.m926a(this.Be);
                    }
                    this.Bo = false;
                } else {
                    this.Bg = 2;
                    el();
                    this.Bb.signalAll();
                    ej();
                    if (this.Bi) {
                        this.Bi = false;
                        m1665E(-1);
                    } else {
                        this.Bc.m927b(this.Bm.isEmpty() ? null : this.Bm);
                    }
                }
            }
            this.Ba.unlock();
        } catch (Throwable th) {
            this.Ba.unlock();
        }
    }

    private void ej() {
        boolean z = isConnected() || ek();
        fq.m980a(z, "GoogleApiClient is not connected yet.");
        this.Ba.lock();
        while (!this.Bd.isEmpty()) {
            try {
                m1669a((C0248c) this.Bd.remove());
            } catch (Throwable e) {
                Log.w("GoogleApiClientImpl", "Service died while flushing queue", e);
            } catch (Throwable th) {
                this.Ba.unlock();
            }
        }
        this.Ba.unlock();
    }

    private boolean ek() {
        this.Ba.lock();
        try {
            boolean z = this.Bh != 0;
            this.Ba.unlock();
            return z;
        } catch (Throwable th) {
            this.Ba.unlock();
        }
    }

    private void el() {
        this.Ba.lock();
        try {
            this.Bh = 0;
            this.Bl.removeMessages(1);
        } finally {
            this.Ba.unlock();
        }
    }

    public <C extends C0239a> C m1681a(C0241c<C> c0241c) {
        Object obj = (C0239a) this.Bn.get(c0241c);
        fq.m982b(obj, (Object) "Appropriate Api was not requested.");
        return obj;
    }

    public <A extends C0239a, T extends C1299b<? extends Result, A>> T m1682a(T t) {
        this.Ba.lock();
        try {
            if (isConnected()) {
                m1683b((C1299b) t);
            } else {
                this.Bd.add(t);
            }
            this.Ba.unlock();
            return t;
        } catch (Throwable th) {
            this.Ba.unlock();
        }
    }

    public <A extends C0239a, T extends C1299b<? extends Result, A>> T m1683b(T t) {
        boolean z = isConnected() || ek();
        fq.m980a(z, "GoogleApiClient is not connected yet.");
        ej();
        try {
            m1669a((C0248c) t);
        } catch (DeadObjectException e) {
            m1665E(1);
        }
        return t;
    }

    public ConnectionResult blockingConnect(long timeout, TimeUnit unit) {
        ConnectionResult connectionResult;
        fq.m980a(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        this.Ba.lock();
        try {
            connect();
            long toNanos = unit.toNanos(timeout);
            while (isConnecting()) {
                toNanos = this.Bb.awaitNanos(toNanos);
                if (toNanos <= 0) {
                    connectionResult = new ConnectionResult(14, null);
                    break;
                }
            }
            if (isConnected()) {
                connectionResult = ConnectionResult.Ag;
                this.Ba.unlock();
            } else if (this.Be != null) {
                connectionResult = this.Be;
                this.Ba.unlock();
            } else {
                connectionResult = new ConnectionResult(13, null);
                this.Ba.unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            connectionResult = new ConnectionResult(15, null);
        } finally {
            this.Ba.unlock();
        }
        return connectionResult;
    }

    public void connect() {
        this.Ba.lock();
        try {
            this.Bi = false;
            if (isConnected() || isConnecting()) {
                this.Ba.unlock();
                return;
            }
            this.Bo = true;
            this.Be = null;
            this.Bg = 1;
            this.Bm.clear();
            this.Bj = this.Bn.size();
            for (C0239a connect : this.Bn.values()) {
                connect.connect();
            }
            this.Ba.unlock();
        } catch (Throwable th) {
            this.Ba.unlock();
        }
    }

    public void disconnect() {
        el();
        m1665E(-1);
    }

    public Looper getLooper() {
        return this.AS;
    }

    public boolean isConnected() {
        this.Ba.lock();
        try {
            boolean z = this.Bg == 2;
            this.Ba.unlock();
            return z;
        } catch (Throwable th) {
            this.Ba.unlock();
        }
    }

    public boolean isConnecting() {
        boolean z = true;
        this.Ba.lock();
        try {
            if (this.Bg != 1) {
                z = false;
            }
            this.Ba.unlock();
            return z;
        } catch (Throwable th) {
            this.Ba.unlock();
        }
    }

    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks listener) {
        return this.Bc.isConnectionCallbacksRegistered(listener);
    }

    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener listener) {
        return this.Bc.isConnectionFailedListenerRegistered(listener);
    }

    public void reconnect() {
        disconnect();
        connect();
    }

    public void registerConnectionCallbacks(ConnectionCallbacks listener) {
        this.Bc.registerConnectionCallbacks(listener);
    }

    public void registerConnectionFailedListener(OnConnectionFailedListener listener) {
        this.Bc.registerConnectionFailedListener(listener);
    }

    public void unregisterConnectionCallbacks(ConnectionCallbacks listener) {
        this.Bc.unregisterConnectionCallbacks(listener);
    }

    public void unregisterConnectionFailedListener(OnConnectionFailedListener listener) {
        this.Bc.unregisterConnectionFailedListener(listener);
    }
}
