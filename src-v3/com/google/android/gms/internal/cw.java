package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.internal.db.C0860a;
import com.google.android.gms.internal.ff.C1374e;

public class cw extends ff<db> {
    final int pe;

    public cw(Context context, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, int i) {
        super(context, connectionCallbacks, onConnectionFailedListener, new String[0]);
        this.pe = i;
    }

    protected void m2993a(fm fmVar, C1374e c1374e) throws RemoteException {
        fmVar.m964g(c1374e, this.pe, getContext().getPackageName(), new Bundle());
    }

    protected String bg() {
        return "com.google.android.gms.ads.service.START";
    }

    protected String bh() {
        return "com.google.android.gms.ads.internal.request.IAdRequestService";
    }

    public db bi() {
        return (db) super.eM();
    }

    protected db m2994q(IBinder iBinder) {
        return C0860a.m2092s(iBinder);
    }

    protected /* synthetic */ IInterface m2995r(IBinder iBinder) {
        return m2994q(iBinder);
    }
}
