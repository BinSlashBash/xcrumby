/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Point
 *  android.os.RemoteException
 */
package com.google.android.gms.maps;

import android.graphics.Point;
import android.os.RemoteException;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.VisibleRegion;

public final class Projection {
    private final IProjectionDelegate Sc;

    Projection(IProjectionDelegate iProjectionDelegate) {
        this.Sc = iProjectionDelegate;
    }

    public LatLng fromScreenLocation(Point object) {
        try {
            object = this.Sc.fromScreenLocation(e.h(object));
            return object;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public VisibleRegion getVisibleRegion() {
        try {
            VisibleRegion visibleRegion = this.Sc.getVisibleRegion();
            return visibleRegion;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public Point toScreenLocation(LatLng latLng) {
        try {
            latLng = (Point)e.d(this.Sc.toScreenLocation(latLng));
            return latLng;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }
}

