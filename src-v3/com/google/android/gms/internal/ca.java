package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class ca implements Creator<cb> {
    static void m679a(cb cbVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, cbVar.versionCode);
        C0262b.m222a(parcel, 2, cbVar.nN, false);
        C0262b.m222a(parcel, 3, cbVar.nO, false);
        C0262b.m222a(parcel, 4, cbVar.mimeType, false);
        C0262b.m222a(parcel, 5, cbVar.packageName, false);
        C0262b.m222a(parcel, 6, cbVar.nP, false);
        C0262b.m222a(parcel, 7, cbVar.nQ, false);
        C0262b.m222a(parcel, 8, cbVar.nR, false);
        C0262b.m211F(parcel, p);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m680d(x0);
    }

    public cb m680d(Parcel parcel) {
        String str = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str7 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    str6 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    str5 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str4 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new cb(i, str7, str6, str5, str4, str3, str2, str);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public cb[] m681h(int i) {
        return new cb[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m681h(x0);
    }
}
