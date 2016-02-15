package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.Status;

public class al extends C1303c {
    private final C0244d<Status> wH;

    public al(C0244d<Status> c0244d) {
        this.wH = c0244d;
    }

    public void m3258m(Status status) throws RemoteException {
        this.wH.m132b(status);
    }

    public void onSuccess() throws RemoteException {
        this.wH.m132b(Status.Bv);
    }
}
