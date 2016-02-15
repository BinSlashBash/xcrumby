package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class jq implements Creator<jp> {
    static void m1110a(jp jpVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, jpVar.getVersionCode());
        C0262b.m234c(parcel, 2, jpVar.adh);
        C0262b.m222a(parcel, 3, jpVar.adi, false);
        C0262b.m213a(parcel, 4, jpVar.adj);
        C0262b.m222a(parcel, 5, jpVar.adk, false);
        C0262b.m215a(parcel, 6, jpVar.adl);
        C0262b.m234c(parcel, 7, jpVar.adm);
        C0262b.m211F(parcel, p);
    }

    public jp bs(Parcel parcel) {
        String str = null;
        int i = 0;
        int o = C0261a.m196o(parcel);
        double d = 0.0d;
        long j = 0;
        int i2 = -1;
        String str2 = null;
        int i3 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    d = C0261a.m192l(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new jp(i3, i, str2, d, str, j, i2);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public jp[] cG(int i) {
        return new jp[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bs(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cG(x0);
    }
}
