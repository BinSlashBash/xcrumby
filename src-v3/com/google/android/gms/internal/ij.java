package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.ih.C1384a;
import java.util.HashSet;
import java.util.Set;

public class ij implements Creator<C1384a> {
    static void m1080a(C1384a c1384a, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        Set ja = c1384a.ja();
        if (ja.contains(Integer.valueOf(1))) {
            C0262b.m234c(parcel, 1, c1384a.getVersionCode());
        }
        if (ja.contains(Integer.valueOf(2))) {
            C0262b.m234c(parcel, 2, c1384a.getMax());
        }
        if (ja.contains(Integer.valueOf(3))) {
            C0262b.m234c(parcel, 3, c1384a.getMin());
        }
        C0262b.m211F(parcel, p);
    }

    public C1384a aO(Parcel parcel) {
        int i = 0;
        int o = C0261a.m196o(parcel);
        Set hashSet = new HashSet();
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i3 = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Std.STD_URL /*2*/:
                    i2 = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Std.STD_URI /*3*/:
                    i = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(3));
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C1384a(hashSet, i3, i2, i);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public C1384a[] bR(int i) {
        return new C1384a[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aO(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bR(x0);
    }
}
