/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.location.Location
 *  android.os.Looper
 */
package com.google.android.gms.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Looper;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.hc;
import com.google.android.gms.internal.hd;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocationClient
implements GooglePlayServicesClient {
    public static final String KEY_LOCATION_CHANGED = "com.google.android.location.LOCATION";
    public static final String KEY_MOCK_LOCATION = "mockLocation";
    private final hc NO;

    public LocationClient(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.NO = new hc(context, connectionCallbacks, onConnectionFailedListener, "location");
    }

    public static int getErrorCode(Intent intent) {
        return intent.getIntExtra("gms_error_code", -1);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int getGeofenceTransition(Intent intent) {
        int n2 = intent.getIntExtra("com.google.android.location.intent.extra.transition", -1);
        if (n2 == -1 || n2 != 1 && n2 != 2 && n2 != 4) {
            return -1;
        }
        return n2;
    }

    public static List<Geofence> getTriggeringGeofences(Intent object) {
        Object object2 = (ArrayList)object.getSerializableExtra("com.google.android.location.intent.extra.geofence_list");
        if (object2 == null) {
            return null;
        }
        object = new ArrayList(object2.size());
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object.add(hd.h((byte[])object2.next()));
        }
        return object;
    }

    public static boolean hasError(Intent intent) {
        return intent.hasExtra("gms_error_code");
    }

    public void addGeofences(List<Geofence> object, PendingIntent pendingIntent, OnAddGeofencesResultListener onAddGeofencesResultListener) {
        ArrayList<hd> arrayList = null;
        if (object != null) {
            arrayList = new ArrayList<hd>();
            object = object.iterator();
            while (object.hasNext()) {
                Geofence geofence = (Geofence)object.next();
                fq.b(geofence instanceof hd, (Object)"Geofence must be created using Geofence.Builder.");
                arrayList.add((hd)geofence);
            }
        }
        this.NO.addGeofences(arrayList, pendingIntent, onAddGeofencesResultListener);
    }

    @Override
    public void connect() {
        this.NO.connect();
    }

    @Override
    public void disconnect() {
        this.NO.disconnect();
    }

    public Location getLastLocation() {
        return this.NO.getLastLocation();
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

    public void removeGeofences(PendingIntent pendingIntent, OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
        this.NO.removeGeofences(pendingIntent, onRemoveGeofencesResultListener);
    }

    public void removeGeofences(List<String> list, OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
        this.NO.removeGeofences(list, onRemoveGeofencesResultListener);
    }

    public void removeLocationUpdates(PendingIntent pendingIntent) {
        this.NO.removeLocationUpdates(pendingIntent);
    }

    public void removeLocationUpdates(LocationListener locationListener) {
        this.NO.removeLocationUpdates(locationListener);
    }

    public void requestLocationUpdates(LocationRequest locationRequest, PendingIntent pendingIntent) {
        this.NO.requestLocationUpdates(locationRequest, pendingIntent);
    }

    public void requestLocationUpdates(LocationRequest locationRequest, LocationListener locationListener) {
        this.NO.requestLocationUpdates(locationRequest, locationListener);
    }

    public void requestLocationUpdates(LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        this.NO.requestLocationUpdates(locationRequest, locationListener, looper);
    }

    public void setMockLocation(Location location) {
        this.NO.setMockLocation(location);
    }

    public void setMockMode(boolean bl2) {
        this.NO.setMockMode(bl2);
    }

    @Override
    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.NO.unregisterConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.NO.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    public static interface OnAddGeofencesResultListener {
        public void onAddGeofencesResult(int var1, String[] var2);
    }

    public static interface OnRemoveGeofencesResultListener {
        public void onRemoveGeofencesByPendingIntentResult(int var1, PendingIntent var2);

        public void onRemoveGeofencesByRequestIdsResult(int var1, String[] var2);
    }

}

