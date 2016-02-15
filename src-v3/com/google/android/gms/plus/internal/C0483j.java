package com.google.android.gms.plus.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.location.GeofenceStatusCodes;

/* renamed from: com.google.android.gms.plus.internal.j */
public class C0483j implements Creator<C1053h> {
    static void m1326a(C1053h c1053h, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m222a(parcel, 1, c1053h.getAccountName(), false);
        C0262b.m234c(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, c1053h.getVersionCode());
        C0262b.m229a(parcel, 2, c1053h.iP(), false);
        C0262b.m229a(parcel, 3, c1053h.iQ(), false);
        C0262b.m229a(parcel, 4, c1053h.iR(), false);
        C0262b.m222a(parcel, 5, c1053h.iS(), false);
        C0262b.m222a(parcel, 6, c1053h.iT(), false);
        C0262b.m222a(parcel, 7, c1053h.iU(), false);
        C0262b.m222a(parcel, 8, c1053h.iV(), false);
        C0262b.m219a(parcel, 9, c1053h.iW(), i, false);
        C0262b.m211F(parcel, p);
    }

    public C1053h aK(Parcel parcel) {
        PlusCommonExtras plusCommonExtras = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String[] strArr = null;
        String[] strArr2 = null;
        String[] strArr3 = null;
        String str5 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    str5 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    strArr3 = C0261a.m208z(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    strArr2 = C0261a.m208z(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    strArr = C0261a.m208z(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str4 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    plusCommonExtras = (PlusCommonExtras) C0261a.m176a(parcel, n, PlusCommonExtras.CREATOR);
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
            return new C1053h(i, str5, strArr3, strArr2, strArr, str4, str3, str2, str, plusCommonExtras);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public C1053h[] bN(int i) {
        return new C1053h[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aK(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bN(x0);
    }
}
