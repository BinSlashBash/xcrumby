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
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.internal.f;

public final class Marker {
    private final f Te;

    public Marker(f f2) {
        this.Te = fq.f(f2);
    }

    public boolean equals(Object object) {
        if (!(object instanceof Marker)) {
            return false;
        }
        try {
            boolean bl2 = this.Te.h(((Marker)object).Te);
            return bl2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public float getAlpha() {
        try {
            float f2 = this.Te.getAlpha();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public String getId() {
        try {
            String string2 = this.Te.getId();
            return string2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public LatLng getPosition() {
        try {
            LatLng latLng = this.Te.getPosition();
            return latLng;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public float getRotation() {
        try {
            float f2 = this.Te.getRotation();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public String getSnippet() {
        try {
            String string2 = this.Te.getSnippet();
            return string2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public String getTitle() {
        try {
            String string2 = this.Te.getTitle();
            return string2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public int hashCode() {
        try {
            int n2 = this.Te.hashCodeRemote();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void hideInfoWindow() {
        try {
            this.Te.hideInfoWindow();
            return;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public boolean isDraggable() {
        try {
            boolean bl2 = this.Te.isDraggable();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isFlat() {
        try {
            boolean bl2 = this.Te.isFlat();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isInfoWindowShown() {
        try {
            boolean bl2 = this.Te.isInfoWindowShown();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isVisible() {
        try {
            boolean bl2 = this.Te.isVisible();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void remove() {
        try {
            this.Te.remove();
            return;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public void setAlpha(float f2) {
        try {
            this.Te.setAlpha(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setAnchor(float f2, float f3) {
        try {
            this.Te.setAnchor(f2, f3);
            return;
        }
        catch (RemoteException var3_3) {
            throw new RuntimeRemoteException(var3_3);
        }
    }

    public void setDraggable(boolean bl2) {
        try {
            this.Te.setDraggable(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setFlat(boolean bl2) {
        try {
            this.Te.setFlat(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setIcon(BitmapDescriptor bitmapDescriptor) {
        try {
            this.Te.l(bitmapDescriptor.id());
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setInfoWindowAnchor(float f2, float f3) {
        try {
            this.Te.setInfoWindowAnchor(f2, f3);
            return;
        }
        catch (RemoteException var3_3) {
            throw new RuntimeRemoteException(var3_3);
        }
    }

    public void setPosition(LatLng latLng) {
        try {
            this.Te.setPosition(latLng);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setRotation(float f2) {
        try {
            this.Te.setRotation(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setSnippet(String string2) {
        try {
            this.Te.setSnippet(string2);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setTitle(String string2) {
        try {
            this.Te.setTitle(string2);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setVisible(boolean bl2) {
        try {
            this.Te.setVisible(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void showInfoWindow() {
        try {
            this.Te.showInfoWindow();
            return;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }
}

