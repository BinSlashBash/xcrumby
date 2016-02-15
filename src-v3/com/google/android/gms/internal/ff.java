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
import android.os.RemoteException;
import android.util.Log;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Api.C0239a;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.fg.C0394b;
import com.google.android.gms.internal.fl.C0894a;
import com.google.android.gms.internal.fm.C0896a;
import java.util.ArrayList;
import org.json.zip.JSONzip;

public abstract class ff<T extends IInterface> implements GooglePlayServicesClient, C0239a, C0394b {
    public static final String[] Dg;
    private final Looper AS;
    private final fg Bc;
    private T Da;
    private final ArrayList<C0391b<?>> Db;
    private C0392f Dc;
    private volatile int Dd;
    private final String[] De;
    boolean Df;
    private final Context mContext;
    final Handler mHandler;

    /* renamed from: com.google.android.gms.internal.ff.a */
    final class C0390a extends Handler {
        final /* synthetic */ ff Dh;

        public C0390a(ff ffVar, Looper looper) {
            this.Dh = ffVar;
            super(looper);
        }

        public void handleMessage(Message msg) {
            C0391b c0391b;
            if (msg.what == 1 && !this.Dh.isConnecting()) {
                c0391b = (C0391b) msg.obj;
                c0391b.dx();
                c0391b.unregister();
            } else if (msg.what == 3) {
                this.Dh.Bc.m926a(new ConnectionResult(((Integer) msg.obj).intValue(), null));
            } else if (msg.what == 4) {
                this.Dh.m2164M(1);
                this.Dh.Da = null;
                this.Dh.Bc.m925O(((Integer) msg.obj).intValue());
            } else if (msg.what == 2 && !this.Dh.isConnected()) {
                c0391b = (C0391b) msg.obj;
                c0391b.dx();
                c0391b.unregister();
            } else if (msg.what == 2 || msg.what == 1) {
                ((C0391b) msg.obj).eN();
            } else {
                Log.wtf("GmsClient", "Don't know how to handle this message.");
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.ff.b */
    protected abstract class C0391b<TListener> {
        final /* synthetic */ ff Dh;
        private boolean Di;
        private TListener mListener;

        public C0391b(ff ffVar, TListener tListener) {
            this.Dh = ffVar;
            this.mListener = tListener;
            this.Di = false;
        }

        protected abstract void m922a(TListener tListener);

        protected abstract void dx();

        public void eN() {
            synchronized (this) {
                Object obj = this.mListener;
                if (this.Di) {
                    Log.w("GmsClient", "Callback proxy " + this + " being reused. This is not safe.");
                }
            }
            if (obj != null) {
                try {
                    m922a(obj);
                } catch (RuntimeException e) {
                    dx();
                    throw e;
                }
            }
            dx();
            synchronized (this) {
                this.Di = true;
            }
            unregister();
        }

        public void eO() {
            synchronized (this) {
                this.mListener = null;
            }
        }

        public void unregister() {
            eO();
            synchronized (this.Dh.Db) {
                this.Dh.Db.remove(this);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.ff.f */
    final class C0392f implements ServiceConnection {
        final /* synthetic */ ff Dh;

        C0392f(ff ffVar) {
            this.Dh = ffVar;
        }

        public void onServiceConnected(ComponentName component, IBinder binder) {
            this.Dh.m2179z(binder);
        }

        public void onServiceDisconnected(ComponentName component) {
            this.Dh.mHandler.sendMessage(this.Dh.mHandler.obtainMessage(4, Integer.valueOf(1)));
        }
    }

    /* renamed from: com.google.android.gms.internal.ff.c */
    public static final class C0888c implements ConnectionCallbacks {
        private final GooglePlayServicesClient.ConnectionCallbacks Dj;

        public C0888c(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
            this.Dj = connectionCallbacks;
        }

        public boolean equals(Object other) {
            return other instanceof C0888c ? this.Dj.equals(((C0888c) other).Dj) : this.Dj.equals(other);
        }

        public void onConnected(Bundle connectionHint) {
            this.Dj.onConnected(connectionHint);
        }

        public void onConnectionSuspended(int cause) {
            this.Dj.onDisconnected();
        }
    }

    /* renamed from: com.google.android.gms.internal.ff.d */
    public abstract class C0889d<TListener> extends C0391b<TListener> {
        private final DataHolder BB;
        final /* synthetic */ ff Dh;

        public C0889d(ff ffVar, TListener tListener, DataHolder dataHolder) {
            this.Dh = ffVar;
            super(ffVar, tListener);
            this.BB = dataHolder;
        }

        protected final void m2160a(TListener tListener) {
            m2161a(tListener, this.BB);
        }

        protected abstract void m2161a(TListener tListener, DataHolder dataHolder);

        protected void dx() {
            if (this.BB != null) {
                this.BB.close();
            }
        }

        public /* bridge */ /* synthetic */ void eN() {
            super.eN();
        }

        public /* bridge */ /* synthetic */ void eO() {
            super.eO();
        }

        public /* bridge */ /* synthetic */ void unregister() {
            super.unregister();
        }
    }

    /* renamed from: com.google.android.gms.internal.ff.h */
    protected final class C0890h extends C0391b<Boolean> {
        final /* synthetic */ ff Dh;
        public final Bundle Dm;
        public final IBinder Dn;
        public final int statusCode;

        public C0890h(ff ffVar, int i, IBinder iBinder, Bundle bundle) {
            this.Dh = ffVar;
            super(ffVar, Boolean.valueOf(true));
            this.statusCode = i;
            this.Dn = iBinder;
            this.Dm = bundle;
        }

        protected /* synthetic */ void m2162a(Object obj) {
            m2163b((Boolean) obj);
        }

        protected void m2163b(Boolean bool) {
            if (bool == null) {
                this.Dh.m2164M(1);
                return;
            }
            switch (this.statusCode) {
                case JSONzip.zipEmptyObject /*0*/:
                    try {
                        if (this.Dh.bh().equals(this.Dn.getInterfaceDescriptor())) {
                            this.Dh.Da = this.Dh.m2178r(this.Dn);
                            if (this.Dh.Da != null) {
                                this.Dh.m2164M(3);
                                this.Dh.Bc.bV();
                                return;
                            }
                        }
                    } catch (RemoteException e) {
                    }
                    fh.m937x(this.Dh.mContext).m939b(this.Dh.bg(), this.Dh.Dc);
                    this.Dh.Dc = null;
                    this.Dh.m2164M(1);
                    this.Dh.Da = null;
                    this.Dh.Bc.m926a(new ConnectionResult(8, null));
                case Std.STD_TIME_ZONE /*10*/:
                    this.Dh.m2164M(1);
                    throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
                default:
                    PendingIntent pendingIntent = this.Dm != null ? (PendingIntent) this.Dm.getParcelable("pendingIntent") : null;
                    if (this.Dh.Dc != null) {
                        fh.m937x(this.Dh.mContext).m939b(this.Dh.bg(), this.Dh.Dc);
                        this.Dh.Dc = null;
                    }
                    this.Dh.m2164M(1);
                    this.Dh.Da = null;
                    this.Dh.Bc.m926a(new ConnectionResult(this.statusCode, pendingIntent));
            }
        }

        protected void dx() {
        }
    }

    /* renamed from: com.google.android.gms.internal.ff.e */
    public static final class C1374e extends C0894a {
        private ff Dk;

        public C1374e(ff ffVar) {
            this.Dk = ffVar;
        }

        public void m3071b(int i, IBinder iBinder, Bundle bundle) {
            fq.m982b((Object) "onPostInitComplete can be called only once per call to getServiceFromBroker", this.Dk);
            this.Dk.m2174a(i, iBinder, bundle);
            this.Dk = null;
        }
    }

    /* renamed from: com.google.android.gms.internal.ff.g */
    public static final class C1375g implements OnConnectionFailedListener {
        private final GooglePlayServicesClient.OnConnectionFailedListener Dl;

        public C1375g(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.Dl = onConnectionFailedListener;
        }

        public boolean equals(Object other) {
            return other instanceof C1375g ? this.Dl.equals(((C1375g) other).Dl) : this.Dl.equals(other);
        }

        public void onConnectionFailed(ConnectionResult result) {
            this.Dl.onConnectionFailed(result);
        }
    }

    static {
        Dg = new String[]{"service_esmobile", "service_googleme"};
    }

    protected ff(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String... strArr) {
        this.Db = new ArrayList();
        this.Dd = 1;
        this.Df = false;
        this.mContext = (Context) fq.m985f(context);
        this.AS = (Looper) fq.m982b((Object) looper, (Object) "Looper must not be null");
        this.Bc = new fg(context, looper, this);
        this.mHandler = new C0390a(this, looper);
        m2177b(strArr);
        this.De = strArr;
        registerConnectionCallbacks((ConnectionCallbacks) fq.m985f(connectionCallbacks));
        registerConnectionFailedListener((OnConnectionFailedListener) fq.m985f(onConnectionFailedListener));
    }

    protected ff(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String... strArr) {
        this(context, context.getMainLooper(), new C0888c(connectionCallbacks), new C1375g(onConnectionFailedListener), strArr);
    }

    private void m2164M(int i) {
        int i2 = this.Dd;
        this.Dd = i;
        if (i2 == i) {
            return;
        }
        if (i == 3) {
            onConnected();
        } else if (i2 == 3 && i == 1) {
            onDisconnected();
        }
    }

    public void m2173N(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, Integer.valueOf(i)));
    }

    protected void m2174a(int i, IBinder iBinder, Bundle bundle) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, new C0890h(this, i, iBinder, bundle)));
    }

    public final void m2175a(C0391b<?> c0391b) {
        synchronized (this.Db) {
            this.Db.add(c0391b);
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, c0391b));
    }

    protected abstract void m2176a(fm fmVar, C1374e c1374e) throws RemoteException;

    protected void m2177b(String... strArr) {
    }

    protected final void bT() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    protected abstract String bg();

    protected abstract String bh();

    public void connect() {
        this.Df = true;
        m2164M(2);
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
        if (isGooglePlayServicesAvailable != 0) {
            m2164M(1);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(isGooglePlayServicesAvailable)));
            return;
        }
        if (this.Dc != null) {
            Log.e("GmsClient", "Calling connect() while still connected, missing disconnect().");
            this.Da = null;
            fh.m937x(this.mContext).m939b(bg(), this.Dc);
        }
        this.Dc = new C0392f(this);
        if (!fh.m937x(this.mContext).m938a(bg(), this.Dc)) {
            Log.e("GmsClient", "unable to connect to service: " + bg());
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(9)));
        }
    }

    public Bundle dG() {
        return null;
    }

    public void disconnect() {
        this.Df = false;
        synchronized (this.Db) {
            int size = this.Db.size();
            for (int i = 0; i < size; i++) {
                ((C0391b) this.Db.get(i)).eO();
            }
            this.Db.clear();
        }
        m2164M(1);
        this.Da = null;
        if (this.Dc != null) {
            fh.m937x(this.mContext).m939b(bg(), this.Dc);
            this.Dc = null;
        }
    }

    public final String[] eL() {
        return this.De;
    }

    protected final T eM() {
        bT();
        return this.Da;
    }

    public boolean em() {
        return this.Df;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.AS;
    }

    public boolean isConnected() {
        return this.Dd == 3;
    }

    public boolean isConnecting() {
        return this.Dd == 2;
    }

    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
        return this.Bc.isConnectionCallbacksRegistered(new C0888c(listener));
    }

    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        return this.Bc.isConnectionFailedListenerRegistered(listener);
    }

    protected void onConnected() {
    }

    protected void onDisconnected() {
    }

    protected abstract T m2178r(IBinder iBinder);

    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
        this.Bc.registerConnectionCallbacks(new C0888c(listener));
    }

    public void registerConnectionCallbacks(ConnectionCallbacks listener) {
        this.Bc.registerConnectionCallbacks(listener);
    }

    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        this.Bc.registerConnectionFailedListener(listener);
    }

    public void registerConnectionFailedListener(OnConnectionFailedListener listener) {
        this.Bc.registerConnectionFailedListener(listener);
    }

    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
        this.Bc.unregisterConnectionCallbacks(new C0888c(listener));
    }

    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        this.Bc.unregisterConnectionFailedListener(listener);
    }

    protected final void m2179z(IBinder iBinder) {
        try {
            m2176a(C0896a.m2211C(iBinder), new C1374e(this));
        } catch (RemoteException e) {
            Log.w("GmsClient", "service died");
        }
    }
}
