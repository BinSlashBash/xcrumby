/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.RemoteException
 */
package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.internal.u;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.internal.a;

public final class MapsInitializer {
    private MapsInitializer() {
    }

    public static int initialize(Context object) {
        fq.f(object);
        try {
            object = u.A((Context)object);
        }
        catch (GooglePlayServicesNotAvailableException var0_1) {
            return var0_1.errorCode;
        }
        try {
            CameraUpdateFactory.a(object.ix());
            BitmapDescriptorFactory.a(object.iy());
            return 0;
        }
        catch (RemoteException var0_2) {
            throw new RuntimeRemoteException(var0_2);
        }
    }
}

