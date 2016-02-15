/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.fg;
import com.google.android.gms.internal.fh;
import com.google.android.gms.internal.fl;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;

public abstract class ff<T extends IInterface>
implements GooglePlayServicesClient,
Api.a,
fg.b {
    public static final String[] Dg = new String[]{"service_esmobile", "service_googleme"};
    private final Looper AS;
    private final fg Bc;
    private T Da;
    private final ArrayList<ff<T>> Db = new ArrayList();
    private ff<T> Dc;
    private volatile int Dd = 1;
    private final String[] De;
    boolean Df = false;
    private final Context mContext;
    final Handler mHandler;

    protected /* varargs */ ff(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String ... arrstring) {
        this.mContext = fq.f(context);
        this.AS = fq.b(looper, (Object)"Looper must not be null");
        this.Bc = new fg(context, looper, this);
        this.mHandler = new a(looper);
        this.b(arrstring);
        this.De = arrstring;
        this.registerConnectionCallbacks(fq.f(connectionCallbacks));
        this.registerConnectionFailedListener(fq.f(onConnectionFailedListener));
    }

    protected /* varargs */ ff(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String ... arrstring) {
        this(context, context.getMainLooper(), new c(connectionCallbacks), new g(onConnectionFailedListener), arrstring);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void M(int n2) {
        int n3 = this.Dd;
        this.Dd = n2;
        if (n3 == n2) return;
        {
            if (n2 == 3) {
                this.onConnected();
                return;
            } else {
                if (n3 != 3 || n2 != 1) return;
                {
                    this.onDisconnected();
                    return;
                }
            }
        }
    }

    public void N(int n2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, (Object)n2));
    }

    protected void a(int n2, IBinder iBinder, Bundle bundle) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, (Object)new h(n2, iBinder, bundle)));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a(ff<T> ff2) {
        ArrayList<ff<T>> arrayList = this.Db;
        synchronized (arrayList) {
            this.Db.add(ff2);
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, ff2));
    }

    protected abstract void a(fm var1, e var2) throws RemoteException;

    protected /* varargs */ void b(String ... arrstring) {
    }

    protected final void bT() {
        if (!this.isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    protected abstract String bg();

    protected abstract String bh();

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void connect() {
        this.Df = true;
        this.M(2);
        int n2 = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
        if (n2 != 0) {
            this.M(1);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, (Object)n2));
            return;
        } else {
            if (this.Dc != null) {
                Log.e((String)"GmsClient", (String)"Calling connect() while still connected, missing disconnect().");
                this.Da = null;
                fh.x(this.mContext).b(this.bg(), this.Dc);
            }
            this.Dc = new f();
            if (fh.x(this.mContext).a(this.bg(), this.Dc)) return;
            {
                Log.e((String)"GmsClient", (String)("unable to connect to service: " + this.bg()));
                this.mHandler.sendMessage(this.mHandler.obtainMessage(3, (Object)9));
                return;
            }
        }
    }

    @Override
    public Bundle dG() {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void disconnect() {
        this.Df = false;
        ArrayList<ff<T>> arrayList = this.Db;
        synchronized (arrayList) {
            int n2 = this.Db.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                ((b)((Object)this.Db.get(i2))).eO();
            }
            this.Db.clear();
        }
        this.M(1);
        this.Da = null;
        if (this.Dc != null) {
            fh.x(this.mContext).b(this.bg(), this.Dc);
            this.Dc = null;
        }
    }

    public final String[] eL() {
        return this.De;
    }

    protected final T eM() {
        this.bT();
        return this.Da;
    }

    @Override
    public boolean em() {
        return this.Df;
    }

    public final Context getContext() {
        return this.mContext;
    }

    @Override
    public final Looper getLooper() {
        return this.AS;
    }

    @Override
    public boolean isConnected() {
        if (this.Dd == 3) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isConnecting() {
        if (this.Dd == 2) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        return this.Bc.isConnectionCallbacksRegistered(new c(connectionCallbacks));
    }

    @Override
    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.Bc.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    protected void onConnected() {
    }

    protected void onDisconnected() {
    }

    protected abstract T r(IBinder var1);

    @Override
    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.Bc.registerConnectionCallbacks(new c(connectionCallbacks));
    }

    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.Bc.registerConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Bc.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Bc.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Override
    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.Bc.unregisterConnectionCallbacks(new c(connectionCallbacks));
    }

    @Override
    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.Bc.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    protected final void z(IBinder iBinder) {
        try {
            this.a(fm.a.C(iBinder), new e(this));
            return;
        }
        catch (RemoteException var1_2) {
            Log.w((String)"GmsClient", (String)"service died");
            return;
        }
    }

    final class a
    extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message object) {
            if (object.what == 1 && !ff.this.isConnecting()) {
                object = (b)object.obj;
                object.dx();
                object.unregister();
                return;
            }
            if (object.what == 3) {
                ff.this.Bc.a(new ConnectionResult((Integer)object.obj, null));
                return;
            }
            if (object.what == 4) {
                ff.this.M(1);
                ff.this.Da = null;
                ff.this.Bc.O((Integer)object.obj);
                return;
            }
            if (object.what == 2 && !ff.this.isConnected()) {
                object = (b)object.obj;
                object.dx();
                object.unregister();
                return;
            }
            if (object.what == 2 || object.what == 1) {
                ((b)object.obj).eN();
                return;
            }
            Log.wtf((String)"GmsClient", (String)"Don't know how to handle this message.");
        }
    }

    protected abstract class b<TListener> {
        private boolean Di;
        private TListener mListener;

        public b(TListener TListener) {
            this.mListener = TListener;
            this.Di = false;
        }

        protected abstract void a(TListener var1);

        protected abstract void dx();

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void eN() {
            // MONITORENTER : this
            TListener TListener = this.mListener;
            if (this.Di) {
                Log.w((String)"GmsClient", (String)("Callback proxy " + this + " being reused. This is not safe."));
            }
            // MONITOREXIT : this
            if (TListener != null) {
                try {
                    this.a(TListener);
                }
                catch (RuntimeException var1_2) {
                    this.dx();
                    throw var1_2;
                }
            } else {
                this.dx();
            }
            // MONITORENTER : this
            this.Di = true;
            // MONITOREXIT : this
            this.unregister();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void eO() {
            synchronized (this) {
                this.mListener = null;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void unregister() {
            this.eO();
            ArrayList arrayList = ff.this.Db;
            synchronized (arrayList) {
                ff.this.Db.remove(this);
                return;
            }
        }
    }

    public static final class c
    implements GoogleApiClient.ConnectionCallbacks {
        private final GooglePlayServicesClient.ConnectionCallbacks Dj;

        public c(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
            this.Dj = connectionCallbacks;
        }

        public boolean equals(Object object) {
            if (object instanceof c) {
                return this.Dj.equals(((c)object).Dj);
            }
            return this.Dj.equals(object);
        }

        @Override
        public void onConnected(Bundle bundle) {
            this.Dj.onConnected(bundle);
        }

        @Override
        public void onConnectionSuspended(int n2) {
            this.Dj.onDisconnected();
        }
    }

    public abstract class d<TListener>
    extends ff<T> {
        private final DataHolder BB;

        public d(TListener TListener, DataHolder dataHolder) {
            super(TListener);
            this.BB = dataHolder;
        }

        protected final void a(TListener TListener) {
            this.a(TListener, this.BB);
        }

        protected abstract void a(TListener var1, DataHolder var2);

        protected void dx() {
            if (this.BB != null) {
                this.BB.close();
            }
        }
    }

    public static final class e
    extends fl.a {
        private ff Dk;

        public e(ff ff2) {
            this.Dk = ff2;
        }

        @Override
        public void b(int n2, IBinder iBinder, Bundle bundle) {
            fq.b("onPostInitComplete can be called only once per call to getServiceFromBroker", (Object)this.Dk);
            this.Dk.a(n2, iBinder, bundle);
            this.Dk = null;
        }
    }

    final class f
    implements ServiceConnection {
        f() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ff.this.z(iBinder);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            ff.this.mHandler.sendMessage(ff.this.mHandler.obtainMessage(4, (Object)1));
        }
    }

    public static final class g
    implements GoogleApiClient.OnConnectionFailedListener {
        private final GooglePlayServicesClient.OnConnectionFailedListener Dl;

        public g(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.Dl = onConnectionFailedListener;
        }

        public boolean equals(Object object) {
            if (object instanceof g) {
                return this.Dl.equals(((g)object).Dl);
            }
            return this.Dl.equals(object);
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            this.Dl.onConnectionFailed(connectionResult);
        }
    }

    protected final class h
    extends ff<T> {
        public final Bundle Dm;
        public final IBinder Dn;
        public final int statusCode;

        public h(int n2, IBinder iBinder, Bundle bundle) {
            super(true);
            this.statusCode = n2;
            this.Dn = iBinder;
            this.Dm = bundle;
        }

        protected /* synthetic */ void a(Object object) {
            this.b((Boolean)object);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void b(Boolean object) {
            if (object == null) {
                ff.this.M(1);
                return;
            }
            switch (this.statusCode) {
                default: {
                    object = this.Dm != null ? (PendingIntent)this.Dm.getParcelable("pendingIntent") : null;
                }
                case 0: {
                    try {
                        object = this.Dn.getInterfaceDescriptor();
                        if (ff.this.bh().equals(object)) {
                            ff.this.Da = ff.this.r(this.Dn);
                            if (ff.this.Da != null) {
                                ff.this.M(3);
                                ff.this.Bc.bV();
                                return;
                            }
                        }
                    }
                    catch (RemoteException var1_2) {
                        // empty catch block
                    }
                    fh.x(ff.this.mContext).b(ff.this.bg(), ff.this.Dc);
                    ff.this.Dc = null;
                    ff.this.M(1);
                    ff.this.Da = null;
                    ff.this.Bc.a(new ConnectionResult(8, null));
                    return;
                }
                case 10: {
                    ff.this.M(1);
                    throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
                }
            }
            if (ff.this.Dc != null) {
                fh.x(ff.this.mContext).b(ff.this.bg(), ff.this.Dc);
                ff.this.Dc = null;
            }
            ff.this.M(1);
            ff.this.Da = null;
            ff.this.Bc.a(new ConnectionResult(this.statusCode, (PendingIntent)object));
        }

        protected void dx() {
        }
    }

}

