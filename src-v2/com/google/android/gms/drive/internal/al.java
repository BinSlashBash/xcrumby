/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.drive.internal.c;

public class al
extends c {
    private final a.d<Status> wH;

    public al(a.d<Status> d2) {
        this.wH = d2;
    }

    @Override
    public void m(Status status) throws RemoteException {
        this.wH.b(status);
    }

    @Override
    public void onSuccess() throws RemoteException {
        this.wH.b(Status.Bv);
    }
}

