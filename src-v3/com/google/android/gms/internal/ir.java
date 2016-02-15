package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.ih.C1392h;
import java.util.HashSet;
import java.util.Set;

public class ir implements Creator<C1392h> {
    static void m1088a(C1392h c1392h, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        Set ja = c1392h.ja();
        if (ja.contains(Integer.valueOf(1))) {
            C0262b.m234c(parcel, 1, c1392h.getVersionCode());
        }
        if (ja.contains(Integer.valueOf(3))) {
            C0262b.m234c(parcel, 3, c1392h.jN());
        }
        if (ja.contains(Integer.valueOf(4))) {
            C0262b.m222a(parcel, 4, c1392h.getValue(), true);
        }
        if (ja.contains(Integer.valueOf(5))) {
            C0262b.m222a(parcel, 5, c1392h.getLabel(), true);
        }
        if (ja.contains(Integer.valueOf(6))) {
            C0262b.m234c(parcel, 6, c1392h.getType());
        }
        C0262b.m211F(parcel, p);
    }

    public C1392h aW(Parcel parcel) {
        String str = null;
        int i = 0;
        int o = C0261a.m196o(parcel);
        Set hashSet = new HashSet();
        int i2 = 0;
        String str2 = null;
        int i3 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i3 = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Std.STD_URI /*3*/:
                    i = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(3));
                    break;
                case Std.STD_CLASS /*4*/:
                    str = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(4));
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str2 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(5));
                    break;
                case Std.STD_CURRENCY /*6*/:
                    i2 = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(6));
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C1392h(hashSet, i3, str2, i2, str, i);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public C1392h[] bZ(int i) {
        return new C1392h[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aW(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bZ(x0);
    }
}
