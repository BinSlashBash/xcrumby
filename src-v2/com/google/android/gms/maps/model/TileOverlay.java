/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.internal.h;

public final class TileOverlay {
    private final h Ts;

    public TileOverlay(h h2) {
        this.Ts = fq.f(h2);
    }

    public void clearTileCache() {
        try {
            this.Ts.clearTileCache();
            return;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof TileOverlay)) {
            return false;
        }
        try {
            boolean bl2 = this.Ts.a(((TileOverlay)object).Ts);
            return bl2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public boolean getFadeIn() {
        try {
            boolean bl2 = this.Ts.getFadeIn();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public String getId() {
        try {
            String string2 = this.Ts.getId();
            return string2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public float getZIndex() {
        try {
            float f2 = this.Ts.getZIndex();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public int hashCode() {
        try {
            int n2 = this.Ts.hashCodeRemote();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isVisible() {
        try {
            boolean bl2 = this.Ts.isVisible();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void remove() {
        try {
            this.Ts.remove();
            return;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public void setFadeIn(boolean bl2) {
        try {
            this.Ts.setFadeIn(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setVisible(boolean bl2) {
        try {
            this.Ts.setVisible(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setZIndex(float f2) {
        try {
            this.Ts.setZIndex(f2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }
}

