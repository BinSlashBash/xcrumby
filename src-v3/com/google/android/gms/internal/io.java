package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.ih.C1389d;
import java.util.HashSet;
import java.util.Set;

public class io implements Creator<C1389d> {
    static void m1085a(C1389d c1389d, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        Set ja = c1389d.ja();
        if (ja.contains(Integer.valueOf(1))) {
            C0262b.m234c(parcel, 1, c1389d.getVersionCode());
        }
        if (ja.contains(Integer.valueOf(2))) {
            C0262b.m222a(parcel, 2, c1389d.getFamilyName(), true);
        }
        if (ja.contains(Integer.valueOf(3))) {
            C0262b.m222a(parcel, 3, c1389d.getFormatted(), true);
        }
        if (ja.contains(Integer.valueOf(4))) {
            C0262b.m222a(parcel, 4, c1389d.getGivenName(), true);
        }
        if (ja.contains(Integer.valueOf(5))) {
            C0262b.m222a(parcel, 5, c1389d.getHonorificPrefix(), true);
        }
        if (ja.contains(Integer.valueOf(6))) {
            C0262b.m222a(parcel, 6, c1389d.getHonorificSuffix(), true);
        }
        if (ja.contains(Integer.valueOf(7))) {
            C0262b.m222a(parcel, 7, c1389d.getMiddleName(), true);
        }
        C0262b.m211F(parcel, p);
    }

    public C1389d aT(Parcel parcel) {
        String str = null;
        int o = C0261a.m196o(parcel);
        Set hashSet = new HashSet();
        int i = 0;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Std.STD_URL /*2*/:
                    str6 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Std.STD_URI /*3*/:
                    str5 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(3));
                    break;
                case Std.STD_CLASS /*4*/:
                    str4 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(4));
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str3 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(5));
                    break;
                case Std.STD_CURRENCY /*6*/:
                    str2 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(6));
                    break;
                case Std.STD_PATTERN /*7*/:
                    str = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(7));
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C1389d(hashSet, i, str6, str5, str4, str3, str2, str);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public C1389d[] bW(int i) {
        return new C1389d[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aT(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bW(x0);
    }
}
