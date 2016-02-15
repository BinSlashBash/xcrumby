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
import com.google.android.gms.maps.model.internal.g;
import java.util.List;

public final class Polygon {
    private final g Tm;

    public Polygon(g g2) {
        this.Tm = fq.f(g2);
    }

    public boolean equals(Object object) {
        if (!(object instanceof Polygon)) {
            return false;
        }
        try {
            boolean bl2 = this.Tm.a(((Polygon)object).Tm);
            return bl2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public int getFillColor() {
        try {
            int n2 = this.Tm.getFillColor();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public List<List<LatLng>> getHoles() {
        try {
            List list = this.Tm.getHoles();
            return list;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public String getId() {
        try {
            String string2 = this.Tm.getId();
            return string2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public List<LatLng> getPoints() {
        try {
            List<LatLng> list = this.Tm.getPoints();
            return list;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public int getStrokeColor() {
        try {
            int n2 = this.Tm.getStrokeColor();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public float getStrokeWidth() {
        try {
            float f2 = this.Tm.getStrokeWidth();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public float getZIndex() {
        try {
            float f2 = this.Tm.getZIndex();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public int hashCode() {
        try {
            int n2 = this.Tm.hashCodeRemote();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isGeodesic() {
        try {
            boolean bl2 = this.Tm.isGeodesic();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isVisible() {
        try {
            boolean bl2 = this.Tm.isVisible();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void remove() {
        try {
            this.Tm.remove();
            return;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public void setFillColor(int n2) {
        try {
            this.Tm.setFillColor(n2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setGeodesic(boolean bl2) {
        try {
            this.Tm.setGeodesic(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setHoles(List<? extends List<LatLng>> list) {
        try {
            this.Tm.setHoles(list);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setPoints(List<LatLng> list) {
        try {
            this.Tm.setPoints(list);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setStrokeColor(int n2) {
        try {
            this.Tm.setStrokeColor(n2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setStrokeWidth(float f2) {
        try {
            this.Tm.setStrokeWidth(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setVisible(boolean bl2) {
        try {
            this.Tm.setVisible(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setZIndex(float f2) {
        try {
            this.Tm.setZIndex(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }
}

