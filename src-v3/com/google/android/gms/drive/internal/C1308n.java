package com.google.android.gms.drive.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.C0270c;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.DriveEvent.Listener;
import com.google.android.gms.drive.internal.C0283u.C0805a;
import com.google.android.gms.drive.internal.C0801l.C1522j;
import com.google.android.gms.drive.internal.C0801l.C1582k;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.ff.C1374e;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.fq;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.android.gms.drive.internal.n */
public class C1308n extends ff<C0283u> {
    private DriveId Fh;
    private DriveId Fi;
    final ConnectionCallbacks Fj;
    Map<DriveId, Map<Listener<?>, C1314s<?>>> Fk;
    private final String wG;

    /* renamed from: com.google.android.gms.drive.internal.n.1 */
    class C15831 extends C1522j {
        final /* synthetic */ DriveId Fl;
        final /* synthetic */ int Fm;
        final /* synthetic */ C1314s Fn;
        final /* synthetic */ C1308n Fo;

        C15831(C1308n c1308n, DriveId driveId, int i, C1314s c1314s) {
            this.Fo = c1308n;
            this.Fl = driveId;
            this.Fm = i;
            this.Fn = c1314s;
        }

        protected void m3649a(C1308n c1308n) throws RemoteException {
            c1308n.fE().m296a(new AddEventListenerRequest(this.Fl, this.Fm, null), this.Fn, null, new al(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.n.2 */
    class C15842 extends C1522j {
        final /* synthetic */ DriveId Fl;
        final /* synthetic */ int Fm;
        final /* synthetic */ C1314s Fn;
        final /* synthetic */ C1308n Fo;

        C15842(C1308n c1308n, DriveId driveId, int i, C1314s c1314s) {
            this.Fo = c1308n;
            this.Fl = driveId;
            this.Fm = i;
            this.Fn = c1314s;
        }

        protected void m3651a(C1308n c1308n) throws RemoteException {
            c1308n.fE().m308a(new RemoveEventListenerRequest(this.Fl, this.Fm), this.Fn, null, new al(this));
        }
    }

    public C1308n(Context context, Looper looper, fc fcVar, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String[] strArr) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, strArr);
        this.Fk = new HashMap();
        this.wG = (String) fq.m982b(fcVar.eC(), (Object) "Must call Api.ClientBuilder.setAccountName()");
        this.Fj = connectionCallbacks;
    }

    protected C0283u m2664F(IBinder iBinder) {
        return C0805a.m1723G(iBinder);
    }

    <C extends DriveEvent> PendingResult<Status> m2665a(GoogleApiClient googleApiClient, DriveId driveId, int i, Listener<C> listener) {
        PendingResult<Status> c1582k;
        fq.m984b(C0270c.m247a(i, driveId), (Object) "id");
        fq.m982b((Object) listener, (Object) "listener");
        fq.m980a(isConnected(), "Client must be connected");
        synchronized (this.Fk) {
            Map map = (Map) this.Fk.get(driveId);
            if (map == null) {
                map = new HashMap();
                this.Fk.put(driveId, map);
            }
            if (map.containsKey(listener)) {
                c1582k = new C1582k(googleApiClient, Status.Bv);
            } else {
                C1314s c1314s = new C1314s(getLooper(), i, listener);
                map.put(listener, c1314s);
                c1582k = googleApiClient.m125b(new C15831(this, driveId, i, c1314s));
            }
        }
        return c1582k;
    }

    protected void m2666a(int i, IBinder iBinder, Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.Fh = (DriveId) bundle.getParcelable("com.google.android.gms.drive.root_id");
            this.Fi = (DriveId) bundle.getParcelable("com.google.android.gms.drive.appdata_id");
        }
        super.m2174a(i, iBinder, bundle);
    }

    protected void m2667a(fm fmVar, C1374e c1374e) throws RemoteException {
        String packageName = getContext().getPackageName();
        fq.m985f(c1374e);
        fq.m985f(packageName);
        fq.m985f(eL());
        fmVar.m953a(c1374e, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, packageName, eL(), this.wG, new Bundle());
    }

    PendingResult<Status> m2668b(GoogleApiClient googleApiClient, DriveId driveId, int i, Listener<?> listener) {
        PendingResult<Status> c1582k;
        fq.m984b(C0270c.m247a(i, driveId), (Object) "id");
        fq.m982b((Object) listener, (Object) "listener");
        fq.m980a(isConnected(), "Client must be connected");
        synchronized (this.Fk) {
            Map map = (Map) this.Fk.get(driveId);
            if (map == null) {
                c1582k = new C1582k(googleApiClient, Status.Bv);
            } else {
                C1314s c1314s = (C1314s) map.remove(listener);
                if (c1314s == null) {
                    c1582k = new C1582k(googleApiClient, Status.Bv);
                } else {
                    if (map.isEmpty()) {
                        this.Fk.remove(driveId);
                    }
                    c1582k = googleApiClient.m125b(new C15842(this, driveId, i, c1314s));
                }
            }
        }
        return c1582k;
    }

    protected String bg() {
        return "com.google.android.gms.drive.ApiService.START";
    }

    protected String bh() {
        return "com.google.android.gms.drive.internal.IDriveService";
    }

    public void disconnect() {
        C0283u c0283u = (C0283u) eM();
        if (c0283u != null) {
            try {
                c0283u.m303a(new DisconnectRequest());
            } catch (RemoteException e) {
            }
        }
        super.disconnect();
        this.Fk.clear();
    }

    public C0283u fE() {
        return (C0283u) eM();
    }

    public DriveId fF() {
        return this.Fh;
    }

    public DriveId fG() {
        return this.Fi;
    }

    protected /* synthetic */ IInterface m2669r(IBinder iBinder) {
        return m2664F(iBinder);
    }
}
