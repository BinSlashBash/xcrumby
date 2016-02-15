package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.location.GeofenceStatusCodes;

public class he implements Creator<hd> {
    static void m1066a(hd hdVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m222a(parcel, 1, hdVar.getRequestId(), false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, hdVar.getVersionCode());
        C0262b.m215a(parcel, 2, hdVar.getExpirationTime());
        C0262b.m224a(parcel, 3, hdVar.hS());
        C0262b.m213a(parcel, 4, hdVar.getLatitude());
        C0262b.m213a(parcel, 5, hdVar.getLongitude());
        C0262b.m214a(parcel, 6, hdVar.hT());
        C0262b.m234c(parcel, 7, hdVar.hU());
        C0262b.m234c(parcel, 8, hdVar.getNotificationResponsiveness());
        C0262b.m234c(parcel, 9, hdVar.hV());
        C0262b.m211F(parcel, p);
    }

    public hd aC(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        int i2 = 0;
        short s = (short) 0;
        double d = 0.0d;
        double d2 = 0.0d;
        float f = 0.0f;
        long j = 0;
        int i3 = 0;
        int i4 = -1;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    s = C0261a.m186f(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    d = C0261a.m192l(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    d2 = C0261a.m192l(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    f = C0261a.m191k(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    i4 = C0261a.m187g(parcel, n);
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new hd(i, str, i2, s, d, d2, f, j, i3, i4);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public hd[] bD(int i) {
        return new hd[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aC(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bD(x0);
    }
}
