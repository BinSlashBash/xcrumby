/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.model;

import android.graphics.Bitmap;
import android.os.RemoteException;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.internal.a;

public final class BitmapDescriptorFactory {
    public static final float HUE_AZURE = 210.0f;
    public static final float HUE_BLUE = 240.0f;
    public static final float HUE_CYAN = 180.0f;
    public static final float HUE_GREEN = 120.0f;
    public static final float HUE_MAGENTA = 300.0f;
    public static final float HUE_ORANGE = 30.0f;
    public static final float HUE_RED = 0.0f;
    public static final float HUE_ROSE = 330.0f;
    public static final float HUE_VIOLET = 270.0f;
    public static final float HUE_YELLOW = 60.0f;
    private static a SC;

    private BitmapDescriptorFactory() {
    }

    public static void a(a a2) {
        if (SC != null) {
            return;
        }
        SC = fq.f(a2);
    }

    public static BitmapDescriptor defaultMarker() {
        try {
            BitmapDescriptor bitmapDescriptor = new BitmapDescriptor(BitmapDescriptorFactory.iC().iH());
            return bitmapDescriptor;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    public static BitmapDescriptor defaultMarker(float f2) {
        try {
            BitmapDescriptor bitmapDescriptor = new BitmapDescriptor(BitmapDescriptorFactory.iC().c(f2));
            return bitmapDescriptor;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public static BitmapDescriptor fromAsset(String object) {
        try {
            object = new BitmapDescriptor(BitmapDescriptorFactory.iC().ba((String)object));
            return object;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    public static BitmapDescriptor fromBitmap(Bitmap object) {
        try {
            object = new BitmapDescriptor(BitmapDescriptorFactory.iC().b((Bitmap)object));
            return object;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    public static BitmapDescriptor fromFile(String object) {
        try {
            object = new BitmapDescriptor(BitmapDescriptorFactory.iC().bb((String)object));
            return object;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    public static BitmapDescriptor fromPath(String object) {
        try {
            object = new BitmapDescriptor(BitmapDescriptorFactory.iC().bc((String)object));
            return object;
        }
        catch (RemoteException var0_1) {
            throw new RuntimeRemoteException(var0_1);
        }
    }

    public static BitmapDescriptor fromResource(int n2) {
        try {
            BitmapDescriptor bitmapDescriptor = new BitmapDescriptor(BitmapDescriptorFactory.iC().bK(n2));
            return bitmapDescriptor;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    private static a iC() {
        return fq.b(SC, (Object)"IBitmapDescriptorFactory is not initialized");
    }
}

