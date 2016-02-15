/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.RemoteException
 *  android.text.TextUtils
 */
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
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.eo;
import com.google.android.gms.internal.ep;
import com.google.android.gms.internal.eq;
import com.google.android.gms.internal.er;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.fl;
import com.google.android.gms.internal.fm;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public final class en
extends ff<ep> {
    private static final er zb = new er("CastClientImpl");
    private static final Object zs = new Object();
    private static final Object zt = new Object();
    private final Handler mHandler;
    private final Cast.Listener xX;
    private double yC;
    private boolean yD;
    private ApplicationMetadata zc;
    private final CastDevice zd;
    private final eq ze;
    private final Map<String, Cast.MessageReceivedCallback> zf;
    private final long zg;
    private String zh;
    private boolean zi;
    private boolean zj;
    private final AtomicLong zk;
    private String zl;
    private String zm;
    private Bundle zn;
    private Map<Long, a.d<Status>> zo;
    private b zp;
    private a.d<Cast.ApplicationConnectionResult> zq;
    private a.d<Status> zr;

    public en(Context context, Looper looper, CastDevice castDevice, long l2, Cast.Listener listener, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, (String[])null);
        this.zd = castDevice;
        this.xX = listener;
        this.zg = l2;
        this.mHandler = new Handler(looper);
        this.zf = new HashMap<String, Cast.MessageReceivedCallback>();
        this.zj = false;
        this.zc = null;
        this.zh = null;
        this.yC = 0.0;
        this.yD = false;
        this.zk = new AtomicLong(0);
        this.zo = new HashMap<Long, a.d<Status>>();
        this.zp = new b();
        this.registerConnectionFailedListener(this.zp);
        this.ze = new eq.a(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            private boolean D(int n2) {
                Object object = zt;
                synchronized (object) {
                    if (en.this.zr != null) {
                        en.this.zr.b(new Status(n2));
                        en.this.zr = null;
                        return true;
                    }
                    return false;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            private void b(long l2, int n2) {
                Map map = en.this.zo;
                // MONITORENTER : map
                a.d d2 = (a.d)en.this.zo.remove(l2);
                // MONITOREXIT : map
                if (d2 == null) return;
                d2.b(new Status(n2));
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void A(int n2) {
                Object object = zs;
                synchronized (object) {
                    if (en.this.zq != null) {
                        en.this.zq.b(new a(new Status(n2)));
                        en.this.zq = null;
                    }
                    return;
                }
            }

            @Override
            public void B(int n2) {
                this.D(n2);
            }

            @Override
            public void C(int n2) {
                this.D(n2);
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(ApplicationMetadata applicationMetadata, String string2, String string3, boolean bl2) {
                en.this.zc = applicationMetadata;
                en.this.zl = applicationMetadata.getApplicationId();
                en.this.zm = string3;
                Object object = zs;
                synchronized (object) {
                    if (en.this.zq != null) {
                        en.this.zq.b(new a(new Status(0), applicationMetadata, string2, string3, bl2));
                        en.this.zq = null;
                    }
                    return;
                }
            }

            @Override
            public void a(String string2, long l2) {
                this.b(l2, 0);
            }

            @Override
            public void a(String string2, long l2, int n2) {
                this.b(l2, n2);
            }

            @Override
            public void b(final String string2, final double d2, final boolean bl2) {
                en.this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        en.this.a(string2, d2, bl2);
                    }
                });
            }

            @Override
            public void b(String string2, byte[] arrby) {
                zb.b("IGNORING: Receive (type=binary, ns=%s) <%d bytes>", string2, arrby.length);
            }

            @Override
            public void d(final String string2, final String string3) {
                zb.b("Receive (type=text, ns=%s) %s", string2, string3);
                en.this.mHandler.post(new Runnable(){

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     * Converted monitor instructions to comments
                     * Lifted jumps to return sites
                     */
                    @Override
                    public void run() {
                        Map map = en.this.zf;
                        // MONITORENTER : map
                        Cast.MessageReceivedCallback messageReceivedCallback = (Cast.MessageReceivedCallback)en.this.zf.get(string2);
                        // MONITOREXIT : map
                        if (messageReceivedCallback != null) {
                            messageReceivedCallback.onMessageReceived(en.this.zd, string2, string3);
                            return;
                        }
                        zb.b("Discarded message for unknown namespace '%s'", string2);
                    }
                });
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onApplicationDisconnected(final int n2) {
                en.this.zl = null;
                en.this.zm = null;
                if (this.D(n2) || en.this.xX == null) {
                    return;
                }
                en.this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        if (en.this.xX != null) {
                            en.this.xX.onApplicationDisconnected(n2);
                        }
                    }
                });
            }

            @Override
            public void z(int n2) {
                zb.b("ICastDeviceControllerListener.onDisconnected: %d", n2);
                en.this.zj = false;
                en.this.zc = null;
                if (n2 != 0) {
                    en.this.N(2);
                }
            }

        };
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(String string2, double d2, boolean bl2) {
        boolean bl3;
        boolean bl4;
        if (!eo.a(string2, this.zh)) {
            this.zh = string2;
            bl4 = true;
        } else {
            bl4 = false;
        }
        if (this.xX != null && (bl4 || this.zi)) {
            this.xX.onApplicationStatusChanged();
        }
        if (d2 != this.yC) {
            this.yC = d2;
            bl3 = true;
        } else {
            bl3 = false;
        }
        if (bl2 != this.yD) {
            this.yD = bl2;
            bl3 = true;
        }
        zb.b("hasChange=%b, mFirstStatusUpdate=%b", bl3, this.zi);
        if (this.xX != null && (bl3 || this.zi)) {
            this.xX.onVolumeChanged();
        }
        this.zi = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void d(a.d<Cast.ApplicationConnectionResult> d2) {
        Object object = zs;
        synchronized (object) {
            if (this.zq != null) {
                this.zq.b(new a(new Status(2002)));
            }
            this.zq = d2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dJ() {
        zb.b("removing all MessageReceivedCallbacks", new Object[0]);
        Map<String, Cast.MessageReceivedCallback> map = this.zf;
        synchronized (map) {
            this.zf.clear();
            return;
        }
    }

    private void dK() throws IllegalStateException {
        if (!this.zj) {
            throw new IllegalStateException("not connected to a device");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void f(a.d<Status> d2) {
        Object object = zt;
        synchronized (object) {
            if (this.zr != null) {
                d2.b(new Status(2001));
                return;
            }
            this.zr = d2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void V(String string2) throws IllegalArgumentException, RemoteException {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("Channel namespace cannot be null or empty");
        }
        Map<String, Cast.MessageReceivedCallback> map = this.zf;
        // MONITORENTER : map
        Cast.MessageReceivedCallback messageReceivedCallback = this.zf.remove(string2);
        // MONITOREXIT : map
        if (messageReceivedCallback == null) return;
        try {
            ((ep)this.eM()).aa(string2);
            return;
        }
        catch (IllegalStateException var2_3) {
            zb.a(var2_3, "Error unregistering namespace (%s): %s", string2, var2_3.getMessage());
            return;
        }
    }

    public void a(double d2) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (Double.isInfinite(d2) || Double.isNaN(d2)) {
            throw new IllegalArgumentException("Volume cannot be " + d2);
        }
        ((ep)this.eM()).a(d2, this.yC, this.yD);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void a(int n2, IBinder iBinder, Bundle bundle) {
        if (n2 == 0 || n2 == 1001) {
            this.zj = true;
            this.zi = true;
        } else {
            this.zj = false;
        }
        int n3 = n2;
        if (n2 == 1001) {
            this.zn = new Bundle();
            this.zn.putBoolean("com.google.android.gms.cast.EXTRA_APP_NO_LONGER_RUNNING", true);
            n3 = 0;
        }
        super.a(n3, iBinder, bundle);
    }

    @Override
    protected void a(fm fm2, ff.e e2) throws RemoteException {
        Bundle bundle = new Bundle();
        zb.b("getServiceFromBroker(): mLastApplicationId=%s, mLastSessionId=%s", this.zl, this.zm);
        this.zd.putInBundle(bundle);
        bundle.putLong("com.google.android.gms.cast.EXTRA_CAST_FLAGS", this.zg);
        if (this.zl != null) {
            bundle.putString("last_application_id", this.zl);
            if (this.zm != null) {
                bundle.putString("last_session_id", this.zm);
            }
        }
        fm2.a((fl)e2, 4452000, this.getContext().getPackageName(), this.ze.asBinder(), bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(String string2, Cast.MessageReceivedCallback messageReceivedCallback) throws IllegalArgumentException, IllegalStateException, RemoteException {
        eo.W(string2);
        this.V(string2);
        if (messageReceivedCallback != null) {
            Map<String, Cast.MessageReceivedCallback> map = this.zf;
            synchronized (map) {
                this.zf.put(string2, messageReceivedCallback);
            }
            ((ep)this.eM()).Z(string2);
        }
    }

    public void a(String string2, a.d<Status> d2) throws IllegalStateException, RemoteException {
        this.f(d2);
        ((ep)this.eM()).Y(string2);
    }

    public void a(String string2, String string3, a.d<Status> d2) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (TextUtils.isEmpty((CharSequence)string3)) {
            throw new IllegalArgumentException("The message payload cannot be null or empty");
        }
        if (string3.length() > 65536) {
            throw new IllegalArgumentException("Message exceeds maximum size");
        }
        eo.W(string2);
        this.dK();
        long l2 = this.zk.incrementAndGet();
        ((ep)this.eM()).a(string2, string3, l2);
        this.zo.put(l2, d2);
    }

    public void a(String string2, boolean bl2, a.d<Cast.ApplicationConnectionResult> d2) throws IllegalStateException, RemoteException {
        this.d(d2);
        ((ep)this.eM()).e(string2, bl2);
    }

    public void b(String string2, String string3, a.d<Cast.ApplicationConnectionResult> d2) throws IllegalStateException, RemoteException {
        this.d(d2);
        ((ep)this.eM()).e(string2, string3);
    }

    @Override
    protected String bg() {
        return "com.google.android.gms.cast.service.BIND_CAST_DEVICE_CONTROLLER_SERVICE";
    }

    @Override
    protected String bh() {
        return "com.google.android.gms.cast.internal.ICastDeviceController";
    }

    @Override
    public Bundle dG() {
        if (this.zn != null) {
            Bundle bundle = this.zn;
            this.zn = null;
            return bundle;
        }
        return super.dG();
    }

    public void dH() throws IllegalStateException, RemoteException {
        ((ep)this.eM()).dH();
    }

    public double dI() throws IllegalStateException {
        this.dK();
        return this.yC;
    }

    @Override
    public void disconnect() {
        this.dJ();
        try {
            if (this.isConnected()) {
                ((ep)this.eM()).disconnect();
            }
            return;
        }
        catch (RemoteException var1_1) {
            zb.b("Error while disconnecting the controller interface: %s", var1_1.getMessage());
            return;
        }
        finally {
            super.disconnect();
        }
    }

    public void e(a.d<Status> d2) throws IllegalStateException, RemoteException {
        this.f(d2);
        ((ep)this.eM()).dO();
    }

    public ApplicationMetadata getApplicationMetadata() throws IllegalStateException {
        this.dK();
        return this.zc;
    }

    public String getApplicationStatus() throws IllegalStateException {
        this.dK();
        return this.zh;
    }

    public boolean isMute() throws IllegalStateException {
        this.dK();
        return this.yD;
    }

    @Override
    protected /* synthetic */ IInterface r(IBinder iBinder) {
        return this.x(iBinder);
    }

    public void v(boolean bl2) throws IllegalStateException, RemoteException {
        ((ep)this.eM()).a(bl2, this.yC, this.yD);
    }

    protected ep x(IBinder iBinder) {
        return ep.a.y(iBinder);
    }

    private static final class a
    implements Cast.ApplicationConnectionResult {
        private final String qL;
        private final Status wJ;
        private final String zA;
        private final boolean zB;
        private final ApplicationMetadata zz;

        public a(Status status) {
            this(status, null, null, null, false);
        }

        public a(Status status, ApplicationMetadata applicationMetadata, String string2, String string3, boolean bl2) {
            this.wJ = status;
            this.zz = applicationMetadata;
            this.zA = string2;
            this.qL = string3;
            this.zB = bl2;
        }

        @Override
        public ApplicationMetadata getApplicationMetadata() {
            return this.zz;
        }

        @Override
        public String getApplicationStatus() {
            return this.zA;
        }

        @Override
        public String getSessionId() {
            return this.qL;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }

        @Override
        public boolean getWasLaunched() {
            return this.zB;
        }
    }

    private class b
    implements GoogleApiClient.OnConnectionFailedListener {
        private b() {
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            en.this.dJ();
        }
    }

}

