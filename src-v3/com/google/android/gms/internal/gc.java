package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.ga.C0900a;
import com.google.android.gms.internal.gd.C0902b;

public class gc implements Creator<C0902b> {
    static void m1013a(C0902b c0902b, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, c0902b.versionCode);
        C0262b.m222a(parcel, 2, c0902b.eM, false);
        C0262b.m219a(parcel, 3, c0902b.Em, i, false);
        C0262b.m211F(parcel, p);
    }

    public C0902b[] m1014W(int i) {
        return new C0902b[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m1015u(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m1014W(x0);
    }

    public C0902b m1015u(Parcel parcel) {
        C0900a c0900a = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    c0900a = (C0900a) C0261a.m176a(parcel, n, C0900a.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C0902b(i, str, c0900a);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }
}
