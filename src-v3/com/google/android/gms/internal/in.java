package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.ih.C1388c;
import java.util.HashSet;
import java.util.Set;

public class in implements Creator<C1388c> {
    static void m1084a(C1388c c1388c, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        Set ja = c1388c.ja();
        if (ja.contains(Integer.valueOf(1))) {
            C0262b.m234c(parcel, 1, c1388c.getVersionCode());
        }
        if (ja.contains(Integer.valueOf(2))) {
            C0262b.m222a(parcel, 2, c1388c.getUrl(), true);
        }
        C0262b.m211F(parcel, p);
    }

    public C1388c aS(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        Set hashSet = new HashSet();
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Std.STD_URL /*2*/:
                    str = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(2));
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C1388c(hashSet, i, str);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public C1388c[] bV(int i) {
        return new C1388c[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aS(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bV(x0);
    }
}
