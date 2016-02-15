/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.os.RemoteException
 */
package com.google.android.gms.drive.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.c;
import com.google.android.gms.drive.internal.AddEventListenerRequest;
import com.google.android.gms.drive.internal.DisconnectRequest;
import com.google.android.gms.drive.internal.RemoveEventListenerRequest;
import com.google.android.gms.drive.internal.al;
import com.google.android.gms.drive.internal.l;
import com.google.android.gms.drive.internal.s;
import com.google.android.gms.drive.internal.u;
import com.google.android.gms.drive.internal.v;
import com.google.android.gms.drive.internal.w;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.fl;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.fq;
import java.util.HashMap;
import java.util.Map;

public class n
extends ff<u> {
    private DriveId Fh;
    private DriveId Fi;
    final GoogleApiClient.ConnectionCallbacks Fj;
    Map<DriveId, Map<DriveEvent.Listener<?>, s<?>>> Fk = new HashMap();
    private final String wG;

    public n(Context context, Looper looper, fc fc2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String[] arrstring) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, arrstring);
        this.wG = fq.b(fc2.eC(), (Object)"Must call Api.ClientBuilder.setAccountName()");
        this.Fj = connectionCallbacks;
    }

    protected u F(IBinder iBinder) {
        return u.a.G(iBinder);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    <C extends DriveEvent> PendingResult<Status> a(GoogleApiClient object, final DriveId driveId, final int n2, DriveEvent.Listener<C> listener) {
        fq.b(c.a(n2, driveId), (Object)"id");
        fq.b(listener, (Object)"listener");
        fq.a(this.isConnected(), "Client must be connected");
        Map map = this.Fk;
        synchronized (map) {
            Object object2;
            Object object3 = object2 = this.Fk.get(driveId);
            if (object2 == null) {
                object3 = new HashMap();
                this.Fk.put(driveId, object3);
            }
            if (object3.containsKey(listener)) {
                return new l.k((GoogleApiClient)object, Status.Bv);
            }
            object2 = new s<C>(this.getLooper(), n2, listener);
            object3.put(listener, (Object)object2);
            return object.b(new l.j((s)object2){
                final /* synthetic */ s Fn;

                @Override
                protected void a(n n22) throws RemoteException {
                    n22.fE().a(new AddEventListenerRequest(driveId, n2, null), (w)this.Fn, null, (v)new al(this));
                }
            });
        }
    }

    @Override
    protected void a(int n2, IBinder iBinder, Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(this.getClass().getClassLoader());
            this.Fh = (DriveId)bundle.getParcelable("com.google.android.gms.drive.root_id");
            this.Fi = (DriveId)bundle.getParcelable("com.google.android.gms.drive.appdata_id");
        }
        super.a(n2, iBinder, bundle);
    }

    @Override
    protected void a(fm fm2, ff.e e2) throws RemoteException {
        String string2 = this.getContext().getPackageName();
        fq.f(e2);
        fq.f(string2);
        fq.f(this.eL());
        fm2.a(e2, 4452000, string2, this.eL(), this.wG, new Bundle());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    PendingResult<Status> b(GoogleApiClient object, final DriveId driveId, final int n2, DriveEvent.Listener<?> object2) {
        fq.b(c.a(n2, driveId), (Object)"id");
        fq.b(object2, (Object)"listener");
        fq.a(this.isConnected(), "Client must be connected");
        Map map = this.Fk;
        synchronized (map) {
            Map map2 = this.Fk.get(driveId);
            if (map2 == null) {
                return new l.k((GoogleApiClient)object, Status.Bv);
            }
            if ((object2 = map2.remove(object2)) == null) {
                return new l.k((GoogleApiClient)object, Status.Bv);
            }
            if (!map2.isEmpty()) return object.b(new l.j((s)object2){
                final /* synthetic */ s Fn;

                @Override
                protected void a(n n22) throws RemoteException {
                    n22.fE().a(new RemoveEventListenerRequest(driveId, n2), (w)this.Fn, null, (v)new al(this));
                }
            });
            this.Fk.remove(driveId);
            return object.b(new );
        }
    }

    @Override
    protected String bg() {
        return "com.google.android.gms.drive.ApiService.START";
    }

    @Override
    protected String bh() {
        return "com.google.android.gms.drive.internal.IDriveService";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void disconnect() {
        u u2 = (u)this.eM();
        if (u2 != null) {
            try {
                u2.a(new DisconnectRequest());
            }
            catch (RemoteException var1_2) {}
        }
        super.disconnect();
        this.Fk.clear();
    }

    public u fE() {
        return (u)this.eM();
    }

    public DriveId fF() {
        return this.Fh;
    }

    public DriveId fG() {
        return this.Fi;
    }

    @Override
    protected /* synthetic */ IInterface r(IBinder iBinder) {
        return this.F(iBinder);
    }

}

