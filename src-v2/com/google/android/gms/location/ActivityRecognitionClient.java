/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 */
package com.google.android.gms.location;

import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.internal.hc;

public class ActivityRecognitionClient
implements GooglePlayServicesClient {
    private final hc NO;

    public ActivityRecognitionClient(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.NO = new hc(context, connectionCallbacks, onConnectionFailedListener, "activity_recognition");
    }

    @Override
    public void connect() {
        this.NO.connect();
    }

    @Override
    public void disconnect() {
        this.NO.disconnect();
    }

    @Override
    public boolean isConnected() {
        return this.NO.isConnected();
    }

    @Override
    public boolean isConnecting() {
        return this.NO.isConnecting();
    }

    @Override
    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        return this.NO.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    @Override
    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.NO.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    @Override
    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.NO.registerConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.NO.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public void removeActivityUpdates(PendingIntent pendingIntent) {
        this.NO.removeActivityUpdates(pendingIntent);
    }

    public void requestActivityUpdates(long l2, PendingIntent pendingIntent) {
        this.NO.requestActivityUpdates(l2, pendingIntent);
    }

    @Override
    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.NO.unregisterConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.NO.unregisterConnectionFailedListener(onConnectionFailedListener);
    }
}

