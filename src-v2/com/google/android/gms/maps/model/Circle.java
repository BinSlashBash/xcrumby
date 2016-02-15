/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.internal.b;

public final class Circle {
    private final b SH;

    public Circle(b b2) {
        this.SH = fq.f(b2);
    }

    public boolean equals(Object object) {
        if (!(object instanceof Circle)) {
            return false;
        }
        try {
            boolean bl2 = this.SH.a(((Circle)object).SH);
            return bl2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public LatLng getCenter() {
        try {
            LatLng latLng = this.SH.getCenter();
            return latLng;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public int getFillColor() {
        try {
            int n2 = this.SH.getFillColor();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public String getId() {
        try {
            String string2 = this.SH.getId();
            return string2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public double getRadius() {
        try {
            double d2 = this.SH.getRadius();
            return d2;
        }
        catch (RemoteException var3_2) {
            throw new RuntimeRemoteException(var3_2);
        }
    }

    public int getStrokeColor() {
        try {
            int n2 = this.SH.getStrokeColor();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public float getStrokeWidth() {
        try {
            float f2 = this.SH.getStrokeWidth();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public float getZIndex() {
        try {
            float f2 = this.SH.getZIndex();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public int hashCode() {
        try {
            int n2 = this.SH.hashCodeRemote();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isVisible() {
        try {
            boolean bl2 = this.SH.isVisible();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void remove() {
        try {
            this.SH.remove();
            return;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public void setCenter(LatLng latLng) {
        try {
            this.SH.setCenter(latLng);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setFillColor(int n2) {
        try {
            this.SH.setFillColor(n2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setRadius(double d2) {
        try {
            this.SH.setRadius(d2);
            return;
        }
        catch (RemoteException var3_2) {
            throw new RuntimeRemoteException(var3_2);
        }
    }

    public void setStrokeColor(int n2) {
        try {
            this.SH.setStrokeColor(n2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setStrokeWidth(float f2) {
        try {
            this.SH.setStrokeWidth(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setVisible(boolean bl2) {
        try {
            this.SH.setVisible(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setZIndex(float f2) {
        try {
            this.SH.setZIndex(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }
}

