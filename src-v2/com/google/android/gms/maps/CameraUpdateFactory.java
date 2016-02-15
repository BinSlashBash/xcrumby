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
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public final class CameraUpdateFactory {
    private static ICameraUpdateFactoryDelegate Ro;

    private CameraUpdateFactory() {
    }

    static void a(ICameraUpdateFactoryDelegate iCameraUpdateFactoryDelegate) {
        if (Ro != null) {
            return;
        }
        Ro = fq.f(iCameraUpdateFactoryDelegate);
    }

    private static ICameraUpdateFactoryDelegate ie() {
        return fq.b(Ro, (Object)"CameraUpdateFactory is not initialized");
    }

    public static CameraUpdate newCameraPosition(CameraPosition object) {
        try {
            object = new CameraUpdate(CameraUpdateFactory.ie().newCameraPosition((CameraPosition)object));
            return object;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    public static CameraUpdate newLatLng(LatLng object) {
        try {
            object = new CameraUpdate(CameraUpdateFactory.ie().newLatLng((LatLng)object));
            return object;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    public static CameraUpdate newLatLngBounds(LatLngBounds object, int n2) {
        try {
            object = new CameraUpdate(CameraUpdateFactory.ie().newLatLngBounds((LatLngBounds)object, n2));
            return object;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    public static CameraUpdate newLatLngBounds(LatLngBounds object, int n2, int n3, int n4) {
        try {
            object = new CameraUpdate(CameraUpdateFactory.ie().newLatLngBoundsWithSize((LatLngBounds)object, n2, n3, n4));
            return object;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    public static CameraUpdate newLatLngZoom(LatLng object, float f2) {
        try {
            object = new CameraUpdate(CameraUpdateFactory.ie().newLatLngZoom((LatLng)object, f2));
            return object;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    public static CameraUpdate scrollBy(float f2, float f3) {
        try {
            CameraUpdate cameraUpdate = new CameraUpdate(CameraUpdateFactory.ie().scrollBy(f2, f3));
            return cameraUpdate;
        }
        catch (RemoteException var2_3) {
            throw new RuntimeRemoteException(var2_3);
        }
    }

    public static CameraUpdate zoomBy(float f2) {
        try {
            CameraUpdate cameraUpdate = new CameraUpdate(CameraUpdateFactory.ie().zoomBy(f2));
            return cameraUpdate;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public static CameraUpdate zoomBy(float f2, Point object) {
        try {
            object = new CameraUpdate(CameraUpdateFactory.ie().zoomByWithFocus(f2, object.x, object.y));
            return object;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public static CameraUpdate zoomIn() {
        try {
            CameraUpdate cameraUpdate = new CameraUpdate(CameraUpdateFactory.ie().zoomIn());
            return cameraUpdate;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    public static CameraUpdate zoomOut() {
        try {
            CameraUpdate cameraUpdate = new CameraUpdate(CameraUpdateFactory.ie().zoomOut());
            return cameraUpdate;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    public static CameraUpdate zoomTo(float f2) {
        try {
            CameraUpdate cameraUpdate = new CameraUpdate(CameraUpdateFactory.ie().zoomTo(f2));
            return cameraUpdate;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }
}

