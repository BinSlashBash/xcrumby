package com.google.android.gms.internal;

import android.os.Parcel;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.Locale;

public class hd implements SafeParcelable, Geofence {
    public static final he CREATOR;
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

    static {
        CREATOR = new he();
    }

    public hd(int i, String str, int i2, short s, double d, double d2, float f, long j, int i3, int i4) {
        aY(str);
        m2283b(f);
        m2282a(d, d2);
        int bB = bB(i2);
        this.xH = i;
        this.NW = s;
        this.Jo = str;
        this.NX = d;
        this.NY = d2;
        this.NZ = f;
        this.Oz = j;
        this.NU = bB;
        this.Oa = i3;
        this.Ob = i4;
    }

    public hd(String str, int i, short s, double d, double d2, float f, long j, int i2, int i3) {
        this(1, str, i, s, d, d2, f, j, i2, i3);
    }

    private static void m2282a(double d, double d2) {
        if (d > 90.0d || d < -90.0d) {
            throw new IllegalArgumentException("invalid latitude: " + d);
        } else if (d2 > 180.0d || d2 < -180.0d) {
            throw new IllegalArgumentException("invalid longitude: " + d2);
        }
    }

    private static void aY(String str) {
        if (str == null || str.length() > 100) {
            throw new IllegalArgumentException("requestId is null or too long: " + str);
        }
    }

    private static void m2283b(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("invalid radius: " + f);
        }
    }

    private static int bB(int i) {
        int i2 = i & 7;
        if (i2 != 0) {
            return i2;
        }
        throw new IllegalArgumentException("No supported transition specified: " + i);
    }

    private static String bC(int i) {
        switch (i) {
            case Std.STD_FILE /*1*/:
                return "CIRCLE";
            default:
                return null;
        }
    }

    public static hd m2284h(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        hd aC = CREATOR.aC(obtain);
        obtain.recycle();
        return aC;
    }

    public int describeContents() {
        he heVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof hd)) {
            return false;
        }
        hd hdVar = (hd) obj;
        if (this.NZ != hdVar.NZ) {
            return false;
        }
        if (this.NX != hdVar.NX) {
            return false;
        }
        if (this.NY != hdVar.NY) {
            return false;
        }
        return this.NW == hdVar.NW;
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
        long doubleToLongBits = Double.doubleToLongBits(this.NX);
        int i = ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 31;
        long doubleToLongBits2 = Double.doubleToLongBits(this.NY);
        return (((((((i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + Float.floatToIntBits(this.NZ)) * 31) + this.NW) * 31) + this.NU;
    }

    public String toString() {
        return String.format(Locale.US, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", new Object[]{bC(this.NW), this.Jo, Integer.valueOf(this.NU), Double.valueOf(this.NX), Double.valueOf(this.NY), Float.valueOf(this.NZ), Integer.valueOf(this.Oa / GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE), Integer.valueOf(this.Ob), Long.valueOf(this.Oz)});
    }

    public void writeToParcel(Parcel parcel, int flags) {
        he heVar = CREATOR;
        he.m1066a(this, parcel, flags);
    }
}
