package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import java.util.ArrayList;

public class jn implements Creator<jm> {
    static void m1109a(jm jmVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, jmVar.getVersionCode());
        C0262b.m222a(parcel, 2, jmVar.add, false);
        C0262b.m222a(parcel, 3, jmVar.ade, false);
        C0262b.m233b(parcel, 4, jmVar.adf, false);
        C0262b.m211F(parcel, p);
    }

    public jm br(Parcel parcel) {
        String str = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        ArrayList fs = gi.fs();
        String str2 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    fs = C0261a.m182c(parcel, n, jk.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new jm(i, str2, str, fs);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public jm[] cF(int i) {
        return new jm[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return br(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cF(x0);
    }
}
