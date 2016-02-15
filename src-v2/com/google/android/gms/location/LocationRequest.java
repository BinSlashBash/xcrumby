/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.SystemClock
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;
import com.google.android.gms.location.LocationRequestCreator;

public final class LocationRequest
implements SafeParcelable {
    public static final LocationRequestCreator CREATOR = new LocationRequestCreator();
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

    public LocationRequest() {
        this.xH = 1;
        this.mPriority = 102;
        this.Oc = 3600000;
        this.Od = 600000;
        this.Oe = false;
        this.NV = Long.MAX_VALUE;
        this.Of = Integer.MAX_VALUE;
        this.Og = 0.0f;
    }

    LocationRequest(int n2, int n3, long l2, long l3, boolean bl2, long l4, int n4, float f2) {
        this.xH = n2;
        this.mPriority = n3;
        this.Oc = l2;
        this.Od = l3;
        this.Oe = bl2;
        this.NV = l4;
        this.Of = n4;
        this.Og = f2;
    }

    private static void a(float f2) {
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("invalid displacement: " + f2);
        }
    }

    private static void bw(int n2) {
        switch (n2) {
            default: {
                throw new IllegalArgumentException("invalid quality: " + n2);
            }
            case 100: 
            case 102: 
            case 104: 
            case 105: 
        }
    }

    public static String bx(int n2) {
        switch (n2) {
            default: {
                return "???";
            }
            case 100: {
                return "PRIORITY_HIGH_ACCURACY";
            }
            case 102: {
                return "PRIORITY_BALANCED_POWER_ACCURACY";
            }
            case 104: {
                return "PRIORITY_LOW_POWER";
            }
            case 105: 
        }
        return "PRIORITY_NO_POWER";
    }

    public static LocationRequest create() {
        return new LocationRequest();
    }

    private static void s(long l2) {
        if (l2 < 0) {
            throw new IllegalArgumentException("invalid interval: " + l2);
        }
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LocationRequest)) {
            return false;
        }
        object = (LocationRequest)object;
        if (this.mPriority != object.mPriority) return false;
        if (this.Oc != object.Oc) return false;
        if (this.Od != object.Od) return false;
        if (this.Oe != object.Oe) return false;
        if (this.NV != object.NV) return false;
        if (this.Of != object.Of) return false;
        if (this.Og == object.Og) return true;
        return false;
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
        return fo.hashCode(this.mPriority, this.Oc, this.Od, this.Oe, this.NV, this.Of, Float.valueOf(this.Og));
    }

    /*
     * Enabled aggressive block sorting
     */
    public LocationRequest setExpirationDuration(long l2) {
        long l3 = SystemClock.elapsedRealtime();
        this.NV = l2 > Long.MAX_VALUE - l3 ? Long.MAX_VALUE : l3 + l2;
        if (this.NV < 0) {
            this.NV = 0;
        }
        return this;
    }

    public LocationRequest setExpirationTime(long l2) {
        this.NV = l2;
        if (this.NV < 0) {
            this.NV = 0;
        }
        return this;
    }

    public LocationRequest setFastestInterval(long l2) {
        LocationRequest.s(l2);
        this.Oe = true;
        this.Od = l2;
        return this;
    }

    public LocationRequest setInterval(long l2) {
        LocationRequest.s(l2);
        this.Oc = l2;
        if (!this.Oe) {
            this.Od = (long)((double)this.Oc / 6.0);
        }
        return this;
    }

    public LocationRequest setNumUpdates(int n2) {
        if (n2 <= 0) {
            throw new IllegalArgumentException("invalid numUpdates: " + n2);
        }
        this.Of = n2;
        return this;
    }

    public LocationRequest setPriority(int n2) {
        LocationRequest.bw(n2);
        this.mPriority = n2;
        return this;
    }

    public LocationRequest setSmallestDisplacement(float f2) {
        LocationRequest.a(f2);
        this.Og = f2;
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Request[").append(LocationRequest.bx(this.mPriority));
        if (this.mPriority != 105) {
            stringBuilder.append(" requested=");
            stringBuilder.append("" + this.Oc + "ms");
        }
        stringBuilder.append(" fastest=");
        stringBuilder.append("" + this.Od + "ms");
        if (this.NV != Long.MAX_VALUE) {
            long l2 = this.NV;
            long l3 = SystemClock.elapsedRealtime();
            stringBuilder.append(" expireIn=");
            stringBuilder.append("" + (l2 - l3) + "ms");
        }
        if (this.Of != Integer.MAX_VALUE) {
            stringBuilder.append(" num=").append(this.Of);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        LocationRequestCreator.a(this, parcel, n2);
    }
}

