package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class LocationRequestCreator implements Creator<LocationRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m1220a(LocationRequest locationRequest, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, locationRequest.mPriority);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, locationRequest.getVersionCode());
        C0262b.m215a(parcel, 2, locationRequest.Oc);
        C0262b.m215a(parcel, 3, locationRequest.Od);
        C0262b.m225a(parcel, 4, locationRequest.Oe);
        C0262b.m215a(parcel, 5, locationRequest.NV);
        C0262b.m234c(parcel, 6, locationRequest.Of);
        C0262b.m214a(parcel, 7, locationRequest.Og);
        C0262b.m211F(parcel, p);
    }

    public LocationRequest createFromParcel(Parcel parcel) {
        boolean z = false;
        int o = C0261a.m196o(parcel);
        int i = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
        long j = 3600000;
        long j2 = 600000;
        long j3 = Long.MAX_VALUE;
        int i2 = Integer.MAX_VALUE;
        float f = 0.0f;
        int i3 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    j2 = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    j3 = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    f = C0261a.m191k(parcel, n);
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new LocationRequest(i3, i, j, j2, z, j3, i2, f);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public LocationRequest[] newArray(int size) {
        return new LocationRequest[size];
    }
}
