package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.gd.C0901a;
import java.util.ArrayList;

public class ge implements Creator<gd> {
    static void m1016a(gd gdVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, gdVar.getVersionCode());
        C0262b.m233b(parcel, 2, gdVar.fn(), false);
        C0262b.m222a(parcel, 3, gdVar.fo(), false);
        C0262b.m211F(parcel, p);
    }

    public gd[] m1017X(int i) {
        return new gd[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m1018v(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m1017X(x0);
    }

    public gd m1018v(Parcel parcel) {
        String str = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        ArrayList arrayList = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    arrayList = C0261a.m182c(parcel, n, C0901a.CREATOR);
                    break;
                case Std.STD_URI /*3*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new gd(i, arrayList, str);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }
}
