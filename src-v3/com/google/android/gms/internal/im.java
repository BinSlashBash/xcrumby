package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.ih.C1387b.C1386b;
import java.util.HashSet;
import java.util.Set;

public class im implements Creator<C1386b> {
    static void m1083a(C1386b c1386b, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        Set ja = c1386b.ja();
        if (ja.contains(Integer.valueOf(1))) {
            C0262b.m234c(parcel, 1, c1386b.getVersionCode());
        }
        if (ja.contains(Integer.valueOf(2))) {
            C0262b.m234c(parcel, 2, c1386b.getHeight());
        }
        if (ja.contains(Integer.valueOf(3))) {
            C0262b.m222a(parcel, 3, c1386b.getUrl(), true);
        }
        if (ja.contains(Integer.valueOf(4))) {
            C0262b.m234c(parcel, 4, c1386b.getWidth());
        }
        C0262b.m211F(parcel, p);
    }

    public C1386b aR(Parcel parcel) {
        int i = 0;
        int o = C0261a.m196o(parcel);
        Set hashSet = new HashSet();
        String str = null;
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
                    str = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(3));
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
            return new C1386b(hashSet, i3, i2, str, i);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public C1386b[] bU(int i) {
        return new C1386b[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aR(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bU(x0);
    }
}
