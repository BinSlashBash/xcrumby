/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.he;
import com.google.android.gms.location.Geofence;
import java.util.Locale;

public class hd
implements SafeParcelable,
Geofence {
    public static final he CREATOR = new he();
    private final String Jo;
    private final int NU;
    private final short NW;
    private final double NX;
    private final double NY;
    private final float NZ;
    private final int Oa;
    private final int Ob;
    private final long Oz;
    private final int xH;

    public hd(int n2, String string2, int n3, short s2, double d2, double d3, float f2, long l2, int n4, int n5) {
        hd.aY(string2);
        hd.b(f2);
        hd.a(d2, d3);
        n3 = hd.bB(n3);
        this.xH = n2;
        this.NW = s2;
        this.Jo = string2;
        this.NX = d2;
        this.NY = d3;
        this.NZ = f2;
        this.Oz = l2;
        this.NU = n3;
        this.Oa = n4;
        this.Ob = n5;
    }

    public hd(String string2, int n2, short s2, double d2, double d3, float f2, long l2, int n3, int n4) {
        this(1, string2, n2, s2, d2, d3, f2, l2, n3, n4);
    }

    private static void a(double d2, double d3) {
        if (d2 > 90.0 || d2 < -90.0) {
            throw new IllegalArgumentException("invalid latitude: " + d2);
        }
        if (d3 > 180.0 || d3 < -180.0) {
            throw new IllegalArgumentException("invalid longitude: " + d3);
        }
    }

    private static void aY(String string2) {
        if (string2 == null || string2.length() > 100) {
            throw new IllegalArgumentException("requestId is null or too long: " + string2);
        }
    }

    private static void b(float f2) {
        if (f2 <= 0.0f) {
            throw new IllegalArgumentException("invalid radius: " + f2);
        }
    }

    private static int bB(int n2) {
        int n3 = n2 & 7;
        if (n3 == 0) {
            throw new IllegalArgumentException("No supported transition specified: " + n2);
        }
        return n3;
    }

    private static String bC(int n2) {
        switch (n2) {
            default: {
                return null;
            }
            case 1: 
        }
        return "CIRCLE";
    }

    public static hd h(byte[] object) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall((byte[])object, 0, object.length);
        parcel.setDataPosition(0);
        object = CREATOR.aC(parcel);
        parcel.recycle();
        return object;
    }

    public int describeContents() {
        he he2 = CREATOR;
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
        if (object == null) {
            return false;
        }
        if (!(object instanceof hd)) {
            return false;
        }
        object = (hd)object;
        if (this.NZ != object.NZ) {
            return false;
        }
        if (this.NX != object.NX) {
            return false;
        }
        if (this.NY != object.NY) {
            return false;
        }
        if (this.NW == object.NW) return true;
        return false;
    }

    public long getExpirationTime() {
        return this.Oz;
    }

    public double getLatitude() {
        return this.NX;
    }

    public double getLongitude() {
        return this.NY;
    }

    public int getNotificationResponsiveness() {
        return this.Oa;
    }

    @Override
    public String getRequestId() {
        return this.Jo;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public short hS() {
        return this.NW;
    }

    public float hT() {
        return this.NZ;
    }

    public int hU() {
        return this.NU;
    }

    public int hV() {
        return this.Ob;
    }

    public int hashCode() {
        long l2 = Double.doubleToLongBits(this.NX);
        int n2 = (int)(l2 ^ l2 >>> 32);
        l2 = Double.doubleToLongBits(this.NY);
        return ((((n2 + 31) * 31 + (int)(l2 ^ l2 >>> 32)) * 31 + Float.floatToIntBits(this.NZ)) * 31 + this.NW) * 31 + this.NU;
    }

    public String toString() {
        return String.format(Locale.US, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", hd.bC(this.NW), this.Jo, this.NU, this.NX, this.NY, Float.valueOf(this.NZ), this.Oa / 1000, this.Ob, this.Oz);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        he he2 = CREATOR;
        he.a(this, parcel, n2);
    }
}

