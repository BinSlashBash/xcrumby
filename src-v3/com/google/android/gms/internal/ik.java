package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.ih.C1387b;
import com.google.android.gms.internal.ih.C1387b.C1385a;
import com.google.android.gms.internal.ih.C1387b.C1386b;
import java.util.HashSet;
import java.util.Set;

public class ik implements Creator<C1387b> {
    static void m1081a(C1387b c1387b, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        Set ja = c1387b.ja();
        if (ja.contains(Integer.valueOf(1))) {
            C0262b.m234c(parcel, 1, c1387b.getVersionCode());
        }
        if (ja.contains(Integer.valueOf(2))) {
            C0262b.m219a(parcel, 2, c1387b.jE(), i, true);
        }
        if (ja.contains(Integer.valueOf(3))) {
            C0262b.m219a(parcel, 3, c1387b.jF(), i, true);
        }
        if (ja.contains(Integer.valueOf(4))) {
            C0262b.m234c(parcel, 4, c1387b.getLayout());
        }
        C0262b.m211F(parcel, p);
    }

    public C1387b aP(Parcel parcel) {
        C1386b c1386b = null;
        int i = 0;
        int o = C0261a.m196o(parcel);
        Set hashSet = new HashSet();
        C1385a c1385a = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i2 = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Std.STD_URL /*2*/:
                    C1385a c1385a2 = (C1385a) C0261a.m176a(parcel, n, C1385a.CREATOR);
                    hashSet.add(Integer.valueOf(2));
                    c1385a = c1385a2;
                    break;
                case Std.STD_URI /*3*/:
                    C1386b c1386b2 = (C1386b) C0261a.m176a(parcel, n, C1386b.CREATOR);
                    hashSet.add(Integer.valueOf(3));
                    c1386b = c1386b2;
                    break;
                case Std.STD_CLASS /*4*/:
                    i = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(4));
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C1387b(hashSet, i2, c1385a, c1386b, i);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public C1387b[] bS(int i) {
        return new C1387b[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aP(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bS(x0);
    }
}
