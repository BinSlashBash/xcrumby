package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.internal.C0433c;
import com.google.android.gms.maps.internal.C0451u;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public final class MapsInitializer {
    private MapsInitializer() {
    }

    public static int initialize(Context context) {
        fq.m985f(context);
        try {
            C0433c A = C0451u.m1247A(context);
            try {
                CameraUpdateFactory.m1222a(A.ix());
                BitmapDescriptorFactory.m1252a(A.iy());
                return 0;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } catch (GooglePlayServicesNotAvailableException e2) {
            return e2.errorCode;
        }
    }
}
