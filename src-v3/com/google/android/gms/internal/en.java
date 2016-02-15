package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.Cast.ApplicationConnectionResult;
import com.google.android.gms.cast.Cast.Listener;
import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.internal.ep.C0873a;
import com.google.android.gms.internal.eq.C0874a;
import com.google.android.gms.internal.ff.C1374e;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public final class en extends ff<ep> {
    private static final er zb;
    private static final Object zs;
    private static final Object zt;
    private final Handler mHandler;
    private final Listener xX;
    private double yC;
    private boolean yD;
    private ApplicationMetadata zc;
    private final CastDevice zd;
    private final eq ze;
    private final Map<String, MessageReceivedCallback> zf;
    private final long zg;
    private String zh;
    private boolean zi;
    private boolean zj;
    private final AtomicLong zk;
    private String zl;
    private String zm;
    private Bundle zn;
    private Map<Long, C0244d<Status>> zo;
    private C1373b zp;
    private C0244d<ApplicationConnectionResult> zq;
    private C0244d<Status> zr;

    /* renamed from: com.google.android.gms.internal.en.1 */
    class C13711 extends C0874a {
        final /* synthetic */ en zu;

        /* renamed from: com.google.android.gms.internal.en.1.1 */
        class C03771 implements Runnable {
            final /* synthetic */ int zv;
            final /* synthetic */ C13711 zw;

            C03771(C13711 c13711, int i) {
                this.zw = c13711;
                this.zv = i;
            }

            public void run() {
                if (this.zw.zu.xX != null) {
                    this.zw.zu.xX.onApplicationDisconnected(this.zv);
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.en.1.2 */
        class C03782 implements Runnable {
            final /* synthetic */ double yQ;
            final /* synthetic */ boolean yR;
            final /* synthetic */ C13711 zw;
            final /* synthetic */ String zx;

            C03782(C13711 c13711, String str, double d, boolean z) {
                this.zw = c13711;
                this.zx = str;
                this.yQ = d;
                this.yR = z;
            }

            public void run() {
                this.zw.zu.m3044a(this.zx, this.yQ, this.yR);
            }
        }

        /* renamed from: com.google.android.gms.internal.en.1.3 */
        class C03793 implements Runnable {
            final /* synthetic */ String xN;
            final /* synthetic */ C13711 zw;
            final /* synthetic */ String zy;

            C03793(C13711 c13711, String str, String str2) {
                this.zw = c13711;
                this.xN = str;
                this.zy = str2;
            }

            public void run() {
                synchronized (this.zw.zu.zf) {
                    MessageReceivedCallback messageReceivedCallback = (MessageReceivedCallback) this.zw.zu.zf.get(this.xN);
                }
                if (messageReceivedCallback != null) {
                    messageReceivedCallback.onMessageReceived(this.zw.zu.zd, this.xN, this.zy);
                    return;
                }
                en.zb.m898b("Discarded message for unknown namespace '%s'", this.xN);
            }
        }

        C13711(en enVar) {
            this.zu = enVar;
        }

        private boolean m3028D(int i) {
            synchronized (en.zt) {
                if (this.zu.zr != null) {
                    this.zu.zr.m132b(new Status(i));
                    this.zu.zr = null;
                    return true;
                }
                return false;
            }
        }

        private void m3029b(long j, int i) {
            synchronized (this.zu.zo) {
                C0244d c0244d = (C0244d) this.zu.zo.remove(Long.valueOf(j));
            }
            if (c0244d != null) {
                c0244d.m132b(new Status(i));
            }
        }

        public void m3030A(int i) {
            synchronized (en.zs) {
                if (this.zu.zq != null) {
                    this.zu.zq.m132b(new C1372a(new Status(i)));
                    this.zu.zq = null;
                }
            }
        }

        public void m3031B(int i) {
            m3028D(i);
        }

        public void m3032C(int i) {
            m3028D(i);
        }

        public void m3033a(ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
            this.zu.zc = applicationMetadata;
            this.zu.zl = applicationMetadata.getApplicationId();
            this.zu.zm = str2;
            synchronized (en.zs) {
                if (this.zu.zq != null) {
                    this.zu.zq.m132b(new C1372a(new Status(0), applicationMetadata, str, str2, z));
                    this.zu.zq = null;
                }
            }
        }

        public void m3034a(String str, long j) {
            m3029b(j, 0);
        }

        public void m3035a(String str, long j, int i) {
            m3029b(j, i);
        }

        public void m3036b(String str, double d, boolean z) {
            this.zu.mHandler.post(new C03782(this, str, d, z));
        }

        public void m3037b(String str, byte[] bArr) {
            en.zb.m898b("IGNORING: Receive (type=binary, ns=%s) <%d bytes>", str, Integer.valueOf(bArr.length));
        }

        public void m3038d(String str, String str2) {
            en.zb.m898b("Receive (type=text, ns=%s) %s", str, str2);
            this.zu.mHandler.post(new C03793(this, str, str2));
        }

        public void onApplicationDisconnected(int statusCode) {
            this.zu.zl = null;
            this.zu.zm = null;
            if (!m3028D(statusCode) && this.zu.xX != null) {
                this.zu.mHandler.post(new C03771(this, statusCode));
            }
        }

        public void m3039z(int i) {
            en.zb.m898b("ICastDeviceControllerListener.onDisconnected: %d", Integer.valueOf(i));
            this.zu.zj = false;
            this.zu.zc = null;
            if (i != 0) {
                this.zu.m2173N(2);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.en.a */
    private static final class C1372a implements ApplicationConnectionResult {
        private final String qL;
        private final Status wJ;
        private final String zA;
        private final boolean zB;
        private final ApplicationMetadata zz;

        public C1372a(Status status) {
            this(status, null, null, null, false);
        }

        public C1372a(Status status, ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
            this.wJ = status;
            this.zz = applicationMetadata;
            this.zA = str;
            this.qL = str2;
            this.zB = z;
        }

        public ApplicationMetadata getApplicationMetadata() {
            return this.zz;
        }

        public String getApplicationStatus() {
            return this.zA;
        }

        public String getSessionId() {
            return this.qL;
        }

        public Status getStatus() {
            return this.wJ;
        }

        public boolean getWasLaunched() {
            return this.zB;
        }
    }

    /* renamed from: com.google.android.gms.internal.en.b */
    private class C1373b implements OnConnectionFailedListener {
        final /* synthetic */ en zu;

        private C1373b(en enVar) {
            this.zu = enVar;
        }

        public void onConnectionFailed(ConnectionResult result) {
            this.zu.dJ();
        }
    }

    static {
        zb = new er("CastClientImpl");
        zs = new Object();
        zt = new Object();
    }

    public en(Context context, Looper looper, CastDevice castDevice, long j, Listener listener, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, (String[]) null);
        this.zd = castDevice;
        this.xX = listener;
        this.zg = j;
        this.mHandler = new Handler(looper);
        this.zf = new HashMap();
        this.zj = false;
        this.zc = null;
        this.zh = null;
        this.yC = 0.0d;
        this.yD = false;
        this.zk = new AtomicLong(0);
        this.zo = new HashMap();
        this.zp = new C1373b();
        registerConnectionFailedListener(this.zp);
        this.ze = new C13711(this);
    }

    private void m3044a(String str, double d, boolean z) {
        boolean z2;
        if (eo.m874a(str, this.zh)) {
            z2 = false;
        } else {
            this.zh = str;
            int i = 1;
        }
        if (this.xX != null && (r0 != 0 || this.zi)) {
            this.xX.onApplicationStatusChanged();
        }
        if (d != this.yC) {
            this.yC = d;
            z2 = true;
        } else {
            z2 = false;
        }
        if (z != this.yD) {
            this.yD = z;
            z2 = true;
        }
        zb.m898b("hasChange=%b, mFirstStatusUpdate=%b", Boolean.valueOf(z2), Boolean.valueOf(this.zi));
        if (this.xX != null && (z2 || this.zi)) {
            this.xX.onVolumeChanged();
        }
        this.zi = false;
    }

    private void m3051d(C0244d<ApplicationConnectionResult> c0244d) {
        synchronized (zs) {
            if (this.zq != null) {
                this.zq.m132b(new C1372a(new Status(CastStatusCodes.CANCELED)));
            }
            this.zq = c0244d;
        }
    }

    private void dJ() {
        zb.m898b("removing all MessageReceivedCallbacks", new Object[0]);
        synchronized (this.zf) {
            this.zf.clear();
        }
    }

    private void dK() throws IllegalStateException {
        if (!this.zj) {
            throw new IllegalStateException("not connected to a device");
        }
    }

    private void m3054f(C0244d<Status> c0244d) {
        synchronized (zt) {
            if (this.zr != null) {
                c0244d.m132b(new Status(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE));
                return;
            }
            this.zr = c0244d;
        }
    }

    public void m3058V(String str) throws IllegalArgumentException, RemoteException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Channel namespace cannot be null or empty");
        }
        synchronized (this.zf) {
            MessageReceivedCallback messageReceivedCallback = (MessageReceivedCallback) this.zf.remove(str);
        }
        if (messageReceivedCallback != null) {
            try {
                ((ep) eM()).aa(str);
            } catch (Throwable e) {
                zb.m897a(e, "Error unregistering namespace (%s): %s", str, e.getMessage());
            }
        }
    }

    public void m3059a(double d) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Volume cannot be " + d);
        }
        ((ep) eM()).m879a(d, this.yC, this.yD);
    }

    protected void m3060a(int i, IBinder iBinder, Bundle bundle) {
        if (i == 0 || i == GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES) {
            this.zj = true;
            this.zi = true;
        } else {
            this.zj = false;
        }
        if (i == GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES) {
            this.zn = new Bundle();
            this.zn.putBoolean(Cast.EXTRA_APP_NO_LONGER_RUNNING, true);
            i = 0;
        }
        super.m2174a(i, iBinder, bundle);
    }

    protected void m3061a(fm fmVar, C1374e c1374e) throws RemoteException {
        Bundle bundle = new Bundle();
        zb.m898b("getServiceFromBroker(): mLastApplicationId=%s, mLastSessionId=%s", this.zl, this.zm);
        this.zd.putInBundle(bundle);
        bundle.putLong("com.google.android.gms.cast.EXTRA_CAST_FLAGS", this.zg);
        if (this.zl != null) {
            bundle.putString("last_application_id", this.zl);
            if (this.zm != null) {
                bundle.putString("last_session_id", this.zm);
            }
        }
        fmVar.m949a((fl) c1374e, (int) GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.ze.asBinder(), bundle);
    }

    public void m3062a(String str, MessageReceivedCallback messageReceivedCallback) throws IllegalArgumentException, IllegalStateException, RemoteException {
        eo.m872W(str);
        m3058V(str);
        if (messageReceivedCallback != null) {
            synchronized (this.zf) {
                this.zf.put(str, messageReceivedCallback);
            }
            ((ep) eM()).m878Z(str);
        }
    }

    public void m3063a(String str, C0244d<Status> c0244d) throws IllegalStateException, RemoteException {
        m3054f((C0244d) c0244d);
        ((ep) eM()).m877Y(str);
    }

    public void m3064a(String str, String str2, C0244d<Status> c0244d) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("The message payload cannot be null or empty");
        } else if (str2.length() > Cast.MAX_MESSAGE_LENGTH) {
            throw new IllegalArgumentException("Message exceeds maximum size");
        } else {
            eo.m872W(str);
            dK();
            long incrementAndGet = this.zk.incrementAndGet();
            ((ep) eM()).m880a(str, str2, incrementAndGet);
            this.zo.put(Long.valueOf(incrementAndGet), c0244d);
        }
    }

    public void m3065a(String str, boolean z, C0244d<ApplicationConnectionResult> c0244d) throws IllegalStateException, RemoteException {
        m3051d((C0244d) c0244d);
        ((ep) eM()).m884e(str, z);
    }

    public void m3066b(String str, String str2, C0244d<ApplicationConnectionResult> c0244d) throws IllegalStateException, RemoteException {
        m3051d((C0244d) c0244d);
        ((ep) eM()).m883e(str, str2);
    }

    protected String bg() {
        return "com.google.android.gms.cast.service.BIND_CAST_DEVICE_CONTROLLER_SERVICE";
    }

    protected String bh() {
        return "com.google.android.gms.cast.internal.ICastDeviceController";
    }

    public Bundle dG() {
        if (this.zn == null) {
            return super.dG();
        }
        Bundle bundle = this.zn;
        this.zn = null;
        return bundle;
    }

    public void dH() throws IllegalStateException, RemoteException {
        ((ep) eM()).dH();
    }

    public double dI() throws IllegalStateException {
        dK();
        return this.yC;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void disconnect() {
        /*
        r5 = this;
        r5.dJ();
        r0 = r5.isConnected();	 Catch:{ RemoteException -> 0x0016 }
        if (r0 == 0) goto L_0x0012;
    L_0x0009:
        r0 = r5.eM();	 Catch:{ RemoteException -> 0x0016 }
        r0 = (com.google.android.gms.internal.ep) r0;	 Catch:{ RemoteException -> 0x0016 }
        r0.disconnect();	 Catch:{ RemoteException -> 0x0016 }
    L_0x0012:
        super.disconnect();
    L_0x0015:
        return;
    L_0x0016:
        r0 = move-exception;
        r1 = zb;	 Catch:{ all -> 0x002c }
        r2 = "Error while disconnecting the controller interface: %s";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x002c }
        r4 = 0;
        r0 = r0.getMessage();	 Catch:{ all -> 0x002c }
        r3[r4] = r0;	 Catch:{ all -> 0x002c }
        r1.m898b(r2, r3);	 Catch:{ all -> 0x002c }
        super.disconnect();
        goto L_0x0015;
    L_0x002c:
        r0 = move-exception;
        super.disconnect();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.en.disconnect():void");
    }

    public void m3067e(C0244d<Status> c0244d) throws IllegalStateException, RemoteException {
        m3054f((C0244d) c0244d);
        ((ep) eM()).dO();
    }

    public ApplicationMetadata getApplicationMetadata() throws IllegalStateException {
        dK();
        return this.zc;
    }

    public String getApplicationStatus() throws IllegalStateException {
        dK();
        return this.zh;
    }

    public boolean isMute() throws IllegalStateException {
        dK();
        return this.yD;
    }

    protected /* synthetic */ IInterface m3068r(IBinder iBinder) {
        return m3070x(iBinder);
    }

    public void m3069v(boolean z) throws IllegalStateException, RemoteException {
        ((ep) eM()).m882a(z, this.yC, this.yD);
    }

    protected ep m3070x(IBinder iBinder) {
        return C0873a.m2124y(iBinder);
    }
}
