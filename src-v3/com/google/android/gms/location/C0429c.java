package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.location.c */
public class C0429c implements Creator<C0942b> {
    static void m1221a(C0942b c0942b, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, c0942b.Oh);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, c0942b.getVersionCode());
        C0262b.m234c(parcel, 2, c0942b.Oi);
        C0262b.m215a(parcel, 3, c0942b.Oj);
        C0262b.m211F(parcel, p);
    }

    public C0942b aB(Parcel parcel) {
        int i = 1;
        int o = C0261a.m196o(parcel);
        int i2 = 0;
        long j = 0;
        int i3 = 1;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C0942b(i2, i3, i, j);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public C0942b[] bA(int i) {
        return new C0942b[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aB(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bA(x0);
    }
}
