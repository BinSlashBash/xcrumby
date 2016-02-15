/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.model;

import android.os.RemoteException;

public final class RuntimeRemoteException
extends RuntimeException {
    public RuntimeRemoteException(RemoteException remoteException) {
        super((Throwable)remoteException);
    }
}

