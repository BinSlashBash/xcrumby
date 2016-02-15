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
import com.google.android.gms.maps.model.internal.e;

public final class IndoorLevel {
    private final e SZ;

    public IndoorLevel(e e2) {
        this.SZ = fq.f(e2);
    }

    public void activate() {
        try {
            this.SZ.activate();
            return;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof IndoorLevel)) {
            return false;
        }
        try {
            boolean bl2 = this.SZ.a(((IndoorLevel)object).SZ);
            return bl2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public String getName() {
        try {
            String string2 = this.SZ.getName();
            return string2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public String getShortName() {
        try {
            String string2 = this.SZ.getShortName();
            return string2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public int hashCode() {
        try {
            int n2 = this.SZ.hashCodeRemote();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }
}

