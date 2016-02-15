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
import com.google.android.gms.maps.model.internal.IPolylineDelegate;
import java.util.List;

public final class Polyline {
    private final IPolylineDelegate Tq;

    public Polyline(IPolylineDelegate iPolylineDelegate) {
        this.Tq = fq.f(iPolylineDelegate);
    }

    public boolean equals(Object object) {
        if (!(object instanceof Polyline)) {
            return false;
        }
        try {
            boolean bl2 = this.Tq.equalsRemote(((Polyline)object).Tq);
            return bl2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public int getColor() {
        try {
            int n2 = this.Tq.getColor();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public String getId() {
        try {
            String string2 = this.Tq.getId();
            return string2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public List<LatLng> getPoints() {
        try {
            List<LatLng> list = this.Tq.getPoints();
            return list;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public float getWidth() {
        try {
            float f2 = this.Tq.getWidth();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public float getZIndex() {
        try {
            float f2 = this.Tq.getZIndex();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public int hashCode() {
        try {
            int n2 = this.Tq.hashCodeRemote();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isGeodesic() {
        try {
            boolean bl2 = this.Tq.isGeodesic();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isVisible() {
        try {
            boolean bl2 = this.Tq.isVisible();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void remove() {
        try {
            this.Tq.remove();
            return;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public void setColor(int n2) {
        try {
            this.Tq.setColor(n2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setGeodesic(boolean bl2) {
        try {
            this.Tq.setGeodesic(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setPoints(List<LatLng> list) {
        try {
            this.Tq.setPoints(list);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setVisible(boolean bl2) {
        try {
            this.Tq.setVisible(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setWidth(float f2) {
        try {
            this.Tq.setWidth(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setZIndex(float f2) {
        try {
            this.Tq.setZIndex(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }
}

