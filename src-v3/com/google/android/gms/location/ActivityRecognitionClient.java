package com.google.android.gms.location;

import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.internal.hc;

public class ActivityRecognitionClient implements GooglePlayServicesClient {
    private final hc NO;

    public ActivityRecognitionClient(Context context, ConnectionCallbacks connectedListener, OnConnectionFailedListener connectionFailedListener) {
        this.NO = new hc(context, connectedListener, connectionFailedListener, "activity_recognition");
    }

    public void connect() {
        this.NO.connect();
    }

    public void disconnect() {
        this.NO.disconnect();
    }

    public boolean isConnected() {
        return this.NO.isConnected();
    }

    public boolean isConnecting() {
        return this.NO.isConnecting();
    }

    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks listener) {
        return this.NO.isConnectionCallbacksRegistered(listener);
    }

    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener listener) {
        return this.NO.isConnectionFailedListenerRegistered(listener);
    }

    public void registerConnectionCallbacks(ConnectionCallbacks listener) {
        this.NO.registerConnectionCallbacks(listener);
    }

    public void registerConnectionFailedListener(OnConnectionFailedListener listener) {
        this.NO.registerConnectionFailedListener(listener);
    }

    public void removeActivityUpdates(PendingIntent callbackIntent) {
        this.NO.removeActivityUpdates(callbackIntent);
    }

    public void requestActivityUpdates(long detectionIntervalMillis, PendingIntent callbackIntent) {
        this.NO.requestActivityUpdates(detectionIntervalMillis, callbackIntent);
    }

    public void unregisterConnectionCallbacks(ConnectionCallbacks listener) {
        this.NO.unregisterConnectionCallbacks(listener);
    }

    public void unregisterConnectionFailedListener(OnConnectionFailedListener listener) {
        this.NO.unregisterConnectionFailedListener(listener);
    }
}
