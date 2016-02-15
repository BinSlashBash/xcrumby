package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.fx.C0899a;

public class fz implements Creator<C0899a> {
    static void m1001a(C0899a c0899a, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, c0899a.versionCode);
        C0262b.m222a(parcel, 2, c0899a.DW, false);
        C0262b.m234c(parcel, 3, c0899a.DX);
        C0262b.m211F(parcel, p);
    }

    public C0899a[] m1002U(int i) {
        return new C0899a[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m1003s(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m1002U(x0);
    }

    public C0899a m1003s(Parcel parcel) {
        int i = 0;
        int o = C0261a.m196o(parcel);
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C0899a(i2, str, i);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }
}
