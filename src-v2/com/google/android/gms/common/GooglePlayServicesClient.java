/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

public interface GooglePlayServicesClient {
    public void connect();

    public void disconnect();

    public boolean isConnected();

    public boolean isConnecting();

    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks var1);

    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener var1);

    public void registerConnectionCallbacks(ConnectionCallbacks var1);

    public void registerConnectionFailedListener(OnConnectionFailedListener var1);

    public void unregisterConnectionCallbacks(ConnectionCallbacks var1);

    public void unregisterConnectionFailedListener(OnConnectionFailedListener var1);

    public static interface ConnectionCallbacks {
        public void onConnected(Bundle var1);

        public void onDisconnected();
    }

    public static interface OnConnectionFailedListener {
        public void onConnectionFailed(ConnectionResult var1);
    }

}

