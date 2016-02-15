package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.ih.C1391g;
import java.util.HashSet;
import java.util.Set;

public class iq implements Creator<C1391g> {
    static void m1087a(C1391g c1391g, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        Set ja = c1391g.ja();
        if (ja.contains(Integer.valueOf(1))) {
            C0262b.m234c(parcel, 1, c1391g.getVersionCode());
        }
        if (ja.contains(Integer.valueOf(2))) {
            C0262b.m225a(parcel, 2, c1391g.isPrimary());
        }
        if (ja.contains(Integer.valueOf(3))) {
            C0262b.m222a(parcel, 3, c1391g.getValue(), true);
        }
        C0262b.m211F(parcel, p);
    }

    public C1391g aV(Parcel parcel) {
        boolean z = false;
        int o = C0261a.m196o(parcel);
        Set hashSet = new HashSet();
        String str = null;
        int i = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Std.STD_URL /*2*/:
                    z = C0261a.m183c(parcel, n);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Std.STD_URI /*3*/:
                    str = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(3));
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C1391g(hashSet, i, z, str);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public C1391g[] bY(int i) {
        return new C1391g[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aV(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bY(x0);
    }
}
