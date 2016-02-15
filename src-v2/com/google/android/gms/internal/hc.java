/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.location.Location
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.RemoteException
 *  android.util.Log
 *  com.google.android.gms.internal.ff.b
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.fl;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.gz;
import com.google.android.gms.internal.ha;
import com.google.android.gms.internal.hb;
import com.google.android.gms.internal.hd;
import com.google.android.gms.internal.hf;
import com.google.android.gms.internal.hr;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;
import java.util.List;

public class hc
extends ff<ha> {
    private final hf<ha> Ok;
    private final hb Oq;
    private final hr Or;
    private final String Os;
    private final Context mContext;
    private final String wG;

    public hc(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String string2) {
        super(context, connectionCallbacks, onConnectionFailedListener, new String[0]);
        this.Ok = new c();
        this.mContext = context;
        this.Oq = new hb(context, this.Ok);
        this.Os = string2;
        this.wG = null;
        this.Or = new hr(this.getContext(), context.getPackageName(), this.Ok);
    }

    protected ha X(IBinder iBinder) {
        return ha.a.W(iBinder);
    }

    @Override
    protected void a(fm fm2, ff.e e2) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("client_name", this.Os);
        fm2.e(e2, 4452000, this.getContext().getPackageName(), bundle);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void addGeofences(List<hd> var1_1, PendingIntent var2_3, LocationClient.OnAddGeofencesResultListener var3_4) {
        this.bT();
        var4_5 = var1_1 != null && var1_1.size() > 0;
        fq.b(var4_5, (Object)"At least one geofence must be specified.");
        fq.b(var2_3, (Object)"PendingIntent must be specified.");
        fq.b(var3_4, (Object)"OnAddGeofencesResultListener not provided.");
        if (var3_4 != null) ** GOTO lbl-1000
        var3_4 = null;
        try {}
        catch (RemoteException var1_2) {
            throw new IllegalStateException((Throwable)var1_2);
        }
        ** GOTO lbl13
lbl-1000: // 1 sources:
        {
            var3_4 = new b((LocationClient.OnAddGeofencesResultListener)var3_4, this);
lbl13: // 2 sources:
            ((ha)this.eM()).a(var1_1, var2_3, (gz)var3_4, this.getContext().getPackageName());
            return;
        }
    }

    @Override
    protected String bg() {
        return "com.google.android.location.internal.GoogleLocationManagerService.START";
    }

    @Override
    protected String bh() {
        return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void disconnect() {
        hb hb2 = this.Oq;
        synchronized (hb2) {
            if (this.isConnected()) {
                this.Oq.removeAllListeners();
                this.Oq.hQ();
            }
            super.disconnect();
            return;
        }
    }

    public Location getLastLocation() {
        return this.Oq.getLastLocation();
    }

    @Override
    protected /* synthetic */ IInterface r(IBinder iBinder) {
        return this.X(iBinder);
    }

    public void removeActivityUpdates(PendingIntent pendingIntent) {
        this.bT();
        fq.f(pendingIntent);
        try {
            ((ha)this.eM()).removeActivityUpdates(pendingIntent);
            return;
        }
        catch (RemoteException var1_2) {
            throw new IllegalStateException((Throwable)var1_2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void removeGeofences(PendingIntent var1_1, LocationClient.OnRemoveGeofencesResultListener var2_3) {
        this.bT();
        fq.b(var1_1, (Object)"PendingIntent must be specified.");
        fq.b(var2_3, (Object)"OnRemoveGeofencesResultListener not provided.");
        if (var2_3 != null) ** GOTO lbl-1000
        var2_3 = null;
        try {}
        catch (RemoteException var1_2) {
            throw new IllegalStateException((Throwable)var1_2);
        }
        ** GOTO lbl11
lbl-1000: // 1 sources:
        {
            var2_3 = new b((LocationClient.OnRemoveGeofencesResultListener)var2_3, this);
lbl11: // 2 sources:
            ((ha)this.eM()).a(var1_1, (gz)var2_3, this.getContext().getPackageName());
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void removeGeofences(List<String> var1_1, LocationClient.OnRemoveGeofencesResultListener var2_3) {
        this.bT();
        var3_4 = var1_1 != null && var1_1.size() > 0;
        fq.b(var3_4, (Object)"geofenceRequestIds can't be null nor empty.");
        fq.b(var2_3, (Object)"OnRemoveGeofencesResultListener not provided.");
        var4_5 = var1_1.toArray(new String[0]);
        if (var2_3 != null) ** GOTO lbl-1000
        var1_1 = null;
        try {}
        catch (RemoteException var1_2) {
            throw new IllegalStateException((Throwable)var1_2);
        }
        ** GOTO lbl13
lbl-1000: // 1 sources:
        {
            var1_1 = new b(var2_3, this);
lbl13: // 2 sources:
            ((ha)this.eM()).a(var4_5, (gz)var1_1, this.getContext().getPackageName());
            return;
        }
    }

    public void removeLocationUpdates(PendingIntent pendingIntent) {
        this.Oq.removeLocationUpdates(pendingIntent);
    }

    public void removeLocationUpdates(LocationListener locationListener) {
        this.Oq.removeLocationUpdates(locationListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void requestActivityUpdates(long l2, PendingIntent pendingIntent) {
        boolean bl2 = true;
        this.bT();
        fq.f(pendingIntent);
        if (l2 < 0) {
            bl2 = false;
        }
        fq.b(bl2, (Object)"detectionIntervalMillis must be >= 0");
        try {
            ((ha)this.eM()).a(l2, true, pendingIntent);
            return;
        }
        catch (RemoteException var3_3) {
            throw new IllegalStateException((Throwable)var3_3);
        }
    }

    public void requestLocationUpdates(LocationRequest locationRequest, PendingIntent pendingIntent) {
        this.Oq.requestLocationUpdates(locationRequest, pendingIntent);
    }

    public void requestLocationUpdates(LocationRequest locationRequest, LocationListener locationListener) {
        this.requestLocationUpdates(locationRequest, locationListener, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void requestLocationUpdates(LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        hb hb2 = this.Oq;
        synchronized (hb2) {
            this.Oq.requestLocationUpdates(locationRequest, locationListener, looper);
            return;
        }
    }

    public void setMockLocation(Location location) {
        this.Oq.setMockLocation(location);
    }

    public void setMockMode(boolean bl2) {
        this.Oq.setMockMode(bl2);
    }

    private final class a
    extends com.google.android.gms.internal.ff.b<LocationClient.OnAddGeofencesResultListener> {
        private final int Ah;
        private final String[] Ot;

        public a(LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener, int n2, String[] arrstring) {
            super(onAddGeofencesResultListener);
            this.Ah = LocationStatusCodes.bz(n2);
            this.Ot = arrstring;
        }

        protected void a(LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener) {
            if (onAddGeofencesResultListener != null) {
                onAddGeofencesResultListener.onAddGeofencesResult(this.Ah, this.Ot);
            }
        }

        protected void dx() {
        }
    }

    private static final class b
    extends gz.a {
        private LocationClient.OnAddGeofencesResultListener Ov;
        private LocationClient.OnRemoveGeofencesResultListener Ow;
        private hc Ox;

        public b(LocationClient.OnAddGeofencesResultListener onAddGeofencesResultListener, hc hc2) {
            this.Ov = onAddGeofencesResultListener;
            this.Ow = null;
            this.Ox = hc2;
        }

        public b(LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, hc hc2) {
            this.Ow = onRemoveGeofencesResultListener;
            this.Ov = null;
            this.Ox = hc2;
        }

        @Override
        public void onAddGeofencesResult(int n2, String[] arrstring) throws RemoteException {
            if (this.Ox == null) {
                Log.wtf((String)"LocationClientImpl", (String)"onAddGeofenceResult called multiple times");
                return;
            }
            hc hc2 = this.Ox;
            hc hc3 = this.Ox;
            hc3.getClass();
            hc2.a((ff.b)((Object)hc3.new a(this.Ov, n2, arrstring)));
            this.Ox = null;
            this.Ov = null;
            this.Ow = null;
        }

        @Override
        public void onRemoveGeofencesByPendingIntentResult(int n2, PendingIntent pendingIntent) {
            if (this.Ox == null) {
                Log.wtf((String)"LocationClientImpl", (String)"onRemoveGeofencesByPendingIntentResult called multiple times");
                return;
            }
            hc hc2 = this.Ox;
            hc hc3 = this.Ox;
            hc3.getClass();
            hc2.a((ff.b)((Object)new d(hc3, 1, this.Ow, n2, pendingIntent)));
            this.Ox = null;
            this.Ov = null;
            this.Ow = null;
        }

        @Override
        public void onRemoveGeofencesByRequestIdsResult(int n2, String[] arrstring) {
            if (this.Ox == null) {
                Log.wtf((String)"LocationClientImpl", (String)"onRemoveGeofencesByRequestIdsResult called multiple times");
                return;
            }
            hc hc2 = this.Ox;
            hc hc3 = this.Ox;
            hc3.getClass();
            hc2.a((ff.b)((Object)new d(hc3, 2, this.Ow, n2, arrstring)));
            this.Ox = null;
            this.Ov = null;
            this.Ow = null;
        }
    }

    private final class c
    implements hf<ha> {
        private c() {
        }

        @Override
        public void bT() {
            hc.this.bT();
        }

        @Override
        public /* synthetic */ IInterface eM() {
            return this.hR();
        }

        public ha hR() {
            return (ha)hc.this.eM();
        }
    }

    private final class d
    extends com.google.android.gms.internal.ff.b<LocationClient.OnRemoveGeofencesResultListener> {
        private final int Ah;
        private final String[] Ot;
        final /* synthetic */ hc Ou;
        private final int Oy;
        private final PendingIntent mPendingIntent;

        /*
         * Enabled aggressive block sorting
         */
        public d(hc hc2, int n2, LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, int n3, PendingIntent pendingIntent) {
            boolean bl2 = true;
            this.Ou = hc2;
            super(onRemoveGeofencesResultListener);
            if (n2 != 1) {
                bl2 = false;
            }
            fb.x(bl2);
            this.Oy = n2;
            this.Ah = LocationStatusCodes.bz(n3);
            this.mPendingIntent = pendingIntent;
            this.Ot = null;
        }

        /*
         * Enabled aggressive block sorting
         */
        public d(hc hc2, int n2, LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, int n3, String[] arrstring) {
            this.Ou = hc2;
            super(onRemoveGeofencesResultListener);
            boolean bl2 = n2 == 2;
            fb.x(bl2);
            this.Oy = n2;
            this.Ah = LocationStatusCodes.bz(n3);
            this.Ot = arrstring;
            this.mPendingIntent = null;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        protected void a(LocationClient.OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
            if (onRemoveGeofencesResultListener == null) return;
            switch (this.Oy) {
                default: {
                    Log.wtf((String)"LocationClientImpl", (String)("Unsupported action: " + this.Oy));
                    return;
                }
                case 1: {
                    onRemoveGeofencesResultListener.onRemoveGeofencesByPendingIntentResult(this.Ah, this.mPendingIntent);
                    return;
                }
                case 2: 
            }
            onRemoveGeofencesResultListener.onRemoveGeofencesByRequestIdsResult(this.Ah, this.Ot);
        }

        protected void dx() {
        }
    }

}

