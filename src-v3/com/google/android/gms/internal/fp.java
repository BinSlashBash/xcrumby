package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.fc.C0887a;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.List;

public class fp implements Creator<C0887a> {
    static void m977a(C0887a c0887a, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m222a(parcel, 1, c0887a.getAccountName(), false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, c0887a.getVersionCode());
        C0262b.m223a(parcel, 2, c0887a.eE(), false);
        C0262b.m234c(parcel, 3, c0887a.eD());
        C0262b.m222a(parcel, 4, c0887a.eG(), false);
        C0262b.m211F(parcel, p);
    }

    public C0887a[] m978Q(int i) {
        return new C0887a[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m979m(x0);
    }

    public C0887a m979m(Parcel parcel) {
        int i = 0;
        String str = null;
        int o = C0261a.m196o(parcel);
        List list = null;
        String str2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    list = C0261a.m171A(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    str = C0261a.m195n(parcel, n);
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
            return new C0887a(i2, str2, list, i, str);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m978Q(x0);
    }
}
