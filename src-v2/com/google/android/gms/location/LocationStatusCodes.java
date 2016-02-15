/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.location;

public final class LocationStatusCodes {
    public static final int ERROR = 1;
    public static final int GEOFENCE_NOT_AVAILABLE = 1000;
    public static final int GEOFENCE_TOO_MANY_GEOFENCES = 1001;
    public static final int GEOFENCE_TOO_MANY_PENDING_INTENTS = 1002;
    public static final int SUCCESS = 0;

    private LocationStatusCodes() {
    }

    public static int bz(int n2) {
        if (n2 >= 0 && n2 <= 1 || 1000 <= n2 && n2 <= 1002) {
            return n2;
        }
        return 1;
    }
}

