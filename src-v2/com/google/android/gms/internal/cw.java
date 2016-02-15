/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.internal.db;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.fl;
import com.google.android.gms.internal.fm;

public class cw
extends ff<db> {
    final int pe;

    public cw(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, int n2) {
        super(context, connectionCallbacks, onConnectionFailedListener, new String[0]);
        this.pe = n2;
    }

    @Override
    protected void a(fm fm2, ff.e e2) throws RemoteException {
        Bundle bundle = new Bundle();
        fm2.g(e2, this.pe, this.getContext().getPackageName(), bundle);
    }

    @Override
    protected String bg() {
        return "com.google.android.gms.ads.service.START";
    }

    @Override
    protected String bh() {
        return "com.google.android.gms.ads.internal.request.IAdRequestService";
    }

    public db bi() {
        return (db)super.eM();
    }

    protected db q(IBinder iBinder) {
        return db.a.s(iBinder);
    }

    @Override
    protected /* synthetic */ IInterface r(IBinder iBinder) {
        return this.q(iBinder);
    }
}

