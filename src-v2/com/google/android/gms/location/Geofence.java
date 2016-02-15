/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 */
package com.google.android.gms.location;

import android.os.SystemClock;
import com.google.android.gms.internal.hd;

public interface Geofence {
    public static final int GEOFENCE_TRANSITION_DWELL = 4;
    public static final int GEOFENCE_TRANSITION_ENTER = 1;
    public static final int GEOFENCE_TRANSITION_EXIT = 2;
    public static final long NEVER_EXPIRE = -1;

    public String getRequestId();

    public static final class Builder {
        private String Jo = null;
        private int NU = 0;
        private long NV = Long.MIN_VALUE;
        private short NW = -1;
        private double NX;
        private double NY;
        private float NZ;
        private int Oa = 0;
        private int Ob = -1;

        public Geofence build() {
            if (this.Jo == null) {
                throw new IllegalArgumentException("Request ID not set.");
            }
            if (this.NU == 0) {
                throw new IllegalArgumentException("Transitions types not set.");
            }
            if ((this.NU & 4) != 0 && this.Ob < 0) {
                throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELLING.");
            }
            if (this.NV == Long.MIN_VALUE) {
                throw new IllegalArgumentException("Expiration not set.");
            }
            if (this.NW == -1) {
                throw new IllegalArgumentException("Geofence region not set.");
            }
            if (this.Oa < 0) {
                throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
            }
            return new hd(this.Jo, this.NU, 1, this.NX, this.NY, this.NZ, this.NV, this.Oa, this.Ob);
        }

        public Builder setCircularRegion(double d2, double d3, float f2) {
            this.NW = 1;
            this.NX = d2;
            this.NY = d3;
            this.NZ = f2;
            return this;
        }

        public Builder setExpirationDuration(long l2) {
            if (l2 < 0) {
                this.NV = -1;
                return this;
            }
            this.NV = SystemClock.elapsedRealtime() + l2;
            return this;
        }

        public Builder setLoiteringDelay(int n2) {
            this.Ob = n2;
            return this;
        }

        public Builder setNotificationResponsiveness(int n2) {
            this.Oa = n2;
            return this;
        }

        public Builder setRequestId(String string2) {
            this.Jo = string2;
            return this;
        }

        public Builder setTransitionTypes(int n2) {
            this.NU = n2;
            return this;
        }
    }

}

