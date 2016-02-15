package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;

public final class LocationRequest implements SafeParcelable {
    public static final LocationRequestCreator CREATOR;
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
    public static final int PRIORITY_HIGH_ACCURACY = 100;
    public static final int PRIORITY_LOW_POWER = 104;
    public static final int PRIORITY_NO_POWER = 105;
    long NV;
    long Oc;
    long Od;
    boolean Oe;
    int Of;
    float Og;
    int mPriority;
    private final int xH;

    static {
        CREATOR = new LocationRequestCreator();
    }

    public LocationRequest() {
        this.xH = 1;
        this.mPriority = PRIORITY_BALANCED_POWER_ACCURACY;
        this.Oc = 3600000;
        this.Od = 600000;
        this.Oe = false;
        this.NV = Long.MAX_VALUE;
        this.Of = Integer.MAX_VALUE;
        this.Og = 0.0f;
    }

    LocationRequest(int versionCode, int priority, long interval, long fastestInterval, boolean explicitFastestInterval, long expireAt, int numUpdates, float smallestDisplacement) {
        this.xH = versionCode;
        this.mPriority = priority;
        this.Oc = interval;
        this.Od = fastestInterval;
        this.Oe = explicitFastestInterval;
        this.NV = expireAt;
        this.Of = numUpdates;
        this.Og = smallestDisplacement;
    }

    private static void m2337a(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("invalid displacement: " + f);
        }
    }

    private static void bw(int i) {
        switch (i) {
            case PRIORITY_HIGH_ACCURACY /*100*/:
            case PRIORITY_BALANCED_POWER_ACCURACY /*102*/:
            case PRIORITY_LOW_POWER /*104*/:
            case PRIORITY_NO_POWER /*105*/:
            default:
                throw new IllegalArgumentException("invalid quality: " + i);
        }
    }

    public static String bx(int i) {
        switch (i) {
            case PRIORITY_HIGH_ACCURACY /*100*/:
                return "PRIORITY_HIGH_ACCURACY";
            case PRIORITY_BALANCED_POWER_ACCURACY /*102*/:
                return "PRIORITY_BALANCED_POWER_ACCURACY";
            case PRIORITY_LOW_POWER /*104*/:
                return "PRIORITY_LOW_POWER";
            case PRIORITY_NO_POWER /*105*/:
                return "PRIORITY_NO_POWER";
            default:
                return "???";
        }
    }

    public static LocationRequest create() {
        return new LocationRequest();
    }

    private static void m2338s(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("invalid interval: " + j);
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LocationRequest)) {
            return false;
        }
        LocationRequest locationRequest = (LocationRequest) object;
        return this.mPriority == locationRequest.mPriority && this.Oc == locationRequest.Oc && this.Od == locationRequest.Od && this.Oe == locationRequest.Oe && this.NV == locationRequest.NV && this.Of == locationRequest.Of && this.Og == locationRequest.Og;
    }

    public long getExpirationTime() {
        return this.NV;
    }

    public long getFastestInterval() {
        return this.Od;
    }

    public long getInterval() {
        return this.Oc;
    }

    public int getNumUpdates() {
        return this.Of;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public float getSmallestDisplacement() {
        return this.Og;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(Integer.valueOf(this.mPriority), Long.valueOf(this.Oc), Long.valueOf(this.Od), Boolean.valueOf(this.Oe), Long.valueOf(this.NV), Integer.valueOf(this.Of), Float.valueOf(this.Og));
    }

    public LocationRequest setExpirationDuration(long millis) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (millis > Long.MAX_VALUE - elapsedRealtime) {
            this.NV = Long.MAX_VALUE;
        } else {
            this.NV = elapsedRealtime + millis;
        }
        if (this.NV < 0) {
            this.NV = 0;
        }
        return this;
    }

    public LocationRequest setExpirationTime(long millis) {
        this.NV = millis;
        if (this.NV < 0) {
            this.NV = 0;
        }
        return this;
    }

    public LocationRequest setFastestInterval(long millis) {
        m2338s(millis);
        this.Oe = true;
        this.Od = millis;
        return this;
    }

    public LocationRequest setInterval(long millis) {
        m2338s(millis);
        this.Oc = millis;
        if (!this.Oe) {
            this.Od = (long) (((double) this.Oc) / 6.0d);
        }
        return this;
    }

    public LocationRequest setNumUpdates(int numUpdates) {
        if (numUpdates <= 0) {
            throw new IllegalArgumentException("invalid numUpdates: " + numUpdates);
        }
        this.Of = numUpdates;
        return this;
    }

    public LocationRequest setPriority(int priority) {
        bw(priority);
        this.mPriority = priority;
        return this;
    }

    public LocationRequest setSmallestDisplacement(float smallestDisplacementMeters) {
        m2337a(smallestDisplacementMeters);
        this.Og = smallestDisplacementMeters;
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Request[").append(bx(this.mPriority));
        if (this.mPriority != PRIORITY_NO_POWER) {
            stringBuilder.append(" requested=");
            stringBuilder.append(this.Oc + "ms");
        }
        stringBuilder.append(" fastest=");
        stringBuilder.append(this.Od + "ms");
        if (this.NV != Long.MAX_VALUE) {
            long elapsedRealtime = this.NV - SystemClock.elapsedRealtime();
            stringBuilder.append(" expireIn=");
            stringBuilder.append(elapsedRealtime + "ms");
        }
        if (this.Of != Integer.MAX_VALUE) {
            stringBuilder.append(" num=").append(this.Of);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        LocationRequestCreator.m1220a(this, parcel, flags);
    }
}
