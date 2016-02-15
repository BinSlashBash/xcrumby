/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.internal.c;

public final class GroundOverlay {
    private final c SP;

    public GroundOverlay(c c2) {
        this.SP = fq.f(c2);
    }

    public boolean equals(Object object) {
        if (!(object instanceof GroundOverlay)) {
            return false;
        }
        try {
            boolean bl2 = this.SP.a(((GroundOverlay)object).SP);
            return bl2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public float getBearing() {
        try {
            float f2 = this.SP.getBearing();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public LatLngBounds getBounds() {
        try {
            LatLngBounds latLngBounds = this.SP.getBounds();
            return latLngBounds;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public float getHeight() {
        try {
            float f2 = this.SP.getHeight();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public String getId() {
        try {
            String string2 = this.SP.getId();
            return string2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public LatLng getPosition() {
        try {
            LatLng latLng = this.SP.getPosition();
            return latLng;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public float getTransparency() {
        try {
            float f2 = this.SP.getTransparency();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public float getWidth() {
        try {
            float f2 = this.SP.getWidth();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public float getZIndex() {
        try {
            float f2 = this.SP.getZIndex();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public int hashCode() {
        try {
            int n2 = this.SP.hashCodeRemote();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isVisible() {
        try {
            boolean bl2 = this.SP.isVisible();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void remove() {
        try {
            this.SP.remove();
            return;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public void setBearing(float f2) {
        try {
            this.SP.setBearing(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setDimensions(float f2) {
        try {
            this.SP.setDimensions(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setDimensions(float f2, float f3) {
        try {
            this.SP.a(f2, f3);
            return;
        }
        catch (RemoteException var3_3) {
            throw new RuntimeRemoteException(var3_3);
        }
    }

    public void setImage(BitmapDescriptor bitmapDescriptor) {
        try {
            this.SP.k(bitmapDescriptor.id());
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setPosition(LatLng latLng) {
        try {
            this.SP.setPosition(latLng);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setPositionFromBounds(LatLngBounds latLngBounds) {
        try {
            this.SP.setPositionFromBounds(latLngBounds);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setTransparency(float f2) {
        try {
            this.SP.setTransparency(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setVisible(boolean bl2) {
        try {
            this.SP.setVisible(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setZIndex(float f2) {
        try {
            this.SP.setZIndex(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }
}

