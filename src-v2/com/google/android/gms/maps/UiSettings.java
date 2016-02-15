/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.internal.IUiSettingsDelegate;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public final class UiSettings {
    private final IUiSettingsDelegate Sy;

    UiSettings(IUiSettingsDelegate iUiSettingsDelegate) {
        this.Sy = iUiSettingsDelegate;
    }

    public boolean isCompassEnabled() {
        try {
            boolean bl2 = this.Sy.isCompassEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isIndoorLevelPickerEnabled() {
        try {
            boolean bl2 = this.Sy.isIndoorLevelPickerEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isMyLocationButtonEnabled() {
        try {
            boolean bl2 = this.Sy.isMyLocationButtonEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isRotateGesturesEnabled() {
        try {
            boolean bl2 = this.Sy.isRotateGesturesEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isScrollGesturesEnabled() {
        try {
            boolean bl2 = this.Sy.isScrollGesturesEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isTiltGesturesEnabled() {
        try {
            boolean bl2 = this.Sy.isTiltGesturesEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isZoomControlsEnabled() {
        try {
            boolean bl2 = this.Sy.isZoomControlsEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isZoomGesturesEnabled() {
        try {
            boolean bl2 = this.Sy.isZoomGesturesEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setAllGesturesEnabled(boolean bl2) {
        try {
            this.Sy.setAllGesturesEnabled(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setCompassEnabled(boolean bl2) {
        try {
            this.Sy.setCompassEnabled(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setIndoorLevelPickerEnabled(boolean bl2) {
        try {
            this.Sy.setIndoorLevelPickerEnabled(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setMyLocationButtonEnabled(boolean bl2) {
        try {
            this.Sy.setMyLocationButtonEnabled(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setRotateGesturesEnabled(boolean bl2) {
        try {
            this.Sy.setRotateGesturesEnabled(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setScrollGesturesEnabled(boolean bl2) {
        try {
            this.Sy.setScrollGesturesEnabled(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setTiltGesturesEnabled(boolean bl2) {
        try {
            this.Sy.setTiltGesturesEnabled(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setZoomControlsEnabled(boolean bl2) {
        try {
            this.Sy.setZoomControlsEnabled(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setZoomGesturesEnabled(boolean bl2) {
        try {
            this.Sy.setZoomGesturesEnabled(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }
}

