package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.gd.C0901a;
import com.google.android.gms.internal.gd.C0902b;
import java.util.ArrayList;

public class gf implements Creator<C0901a> {
    static void m1019a(C0901a c0901a, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, c0901a.versionCode);
        C0262b.m222a(parcel, 2, c0901a.className, false);
        C0262b.m233b(parcel, 3, c0901a.El, false);
        C0262b.m211F(parcel, p);
    }

    public C0901a[] m1020Y(int i) {
        return new C0901a[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m1021w(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m1020Y(x0);
    }

    public C0901a m1021w(Parcel parcel) {
        ArrayList arrayList = null;
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
                    arrayList = C0261a.m182c(parcel, n, C0902b.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C0901a(i, str, arrayList);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }
}
