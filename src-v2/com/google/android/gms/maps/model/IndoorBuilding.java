/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.model.IndoorLevel;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.internal.d;
import com.google.android.gms.maps.model.internal.e;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class IndoorBuilding {
    private final d SY;

    public IndoorBuilding(d d2) {
        this.SY = fq.f(d2);
    }

    public boolean equals(Object object) {
        if (!(object instanceof IndoorBuilding)) {
            return false;
        }
        try {
            boolean bl2 = this.SY.b(((IndoorBuilding)object).SY);
            return bl2;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public int getActiveLevelIndex() {
        try {
            int n2 = this.SY.getActiveLevelIndex();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public int getDefaultLevelIndex() {
        try {
            int n2 = this.SY.getActiveLevelIndex();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public List<IndoorLevel> getLevels() {
        ArrayList<IndoorLevel> arrayList;
        try {
            Object object = this.SY.getLevels();
            arrayList = new ArrayList<IndoorLevel>(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                arrayList.add(new IndoorLevel(e.a.aF((IBinder)object.next())));
            }
        }
        catch (RemoteException var1_3) {
            throw new RuntimeRemoteException(var1_3);
        }
        return arrayList;
    }

    public int hashCode() {
        try {
            int n2 = this.SY.hashCodeRemote();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isUnderground() {
        try {
            boolean bl2 = this.SY.isUnderground();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }
}

