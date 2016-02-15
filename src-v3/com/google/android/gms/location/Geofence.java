package com.google.android.gms.location;

import android.os.SystemClock;
import com.google.android.gms.internal.hd;

public interface Geofence {
    public static final int GEOFENCE_TRANSITION_DWELL = 4;
    public static final int GEOFENCE_TRANSITION_ENTER = 1;
    public static final int GEOFENCE_TRANSITION_EXIT = 2;
    public static final long NEVER_EXPIRE = -1;

    public static final class Builder {
        private String Jo;
        private int NU;
        private long NV;
        private short NW;
        private double NX;
        private double NY;
        private float NZ;
        private int Oa;
        private int Ob;

        public Builder() {
            this.Jo = null;
            this.NU = 0;
            this.NV = Long.MIN_VALUE;
            this.NW = (short) -1;
            this.Oa = 0;
            this.Ob = -1;
        }

        public Geofence build() {
            if (this.Jo == null) {
                throw new IllegalArgumentException("Request ID not set.");
            } else if (this.NU == 0) {
                throw new IllegalArgumentException("Transitions types not set.");
            } else if ((this.NU & Geofence.GEOFENCE_TRANSITION_DWELL) != 0 && this.Ob < 0) {
                throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELLING.");
            } else if (this.NV == Long.MIN_VALUE) {
                throw new IllegalArgumentException("Expiration not set.");
            } else if (this.NW == (short) -1) {
                throw new IllegalArgumentException("Geofence region not set.");
            } else if (this.Oa >= 0) {
                return new hd(this.Jo, this.NU, (short) 1, this.NX, this.NY, this.NZ, this.NV, this.Oa, this.Ob);
            } else {
                throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
            }
        }

        public Builder setCircularRegion(double latitude, double longitude, float radius) {
            this.NW = (short) 1;
            this.NX = latitude;
            this.NY = longitude;
            this.NZ = radius;
            return this;
        }

        public Builder setExpirationDuration(long durationMillis) {
            if (durationMillis < 0) {
                this.NV = Geofence.NEVER_EXPIRE;
            } else {
                this.NV = SystemClock.elapsedRealtime() + durationMillis;
            }
            return this;
        }

        public Builder setLoiteringDelay(int loiteringDelayMs) {
            this.Ob = loiteringDelayMs;
            return this;
        }

        public Builder setNotificationResponsiveness(int notificationResponsivenessMs) {
            this.Oa = notificationResponsivenessMs;
            return this;
        }

        public Builder setRequestId(String requestId) {
            this.Jo = requestId;
            return this;
        }

        public Builder setTransitionTypes(int transitionTypes) {
            this.NU = transitionTypes;
            return this;
        }
    }

    String getRequestId();
}
