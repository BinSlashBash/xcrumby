package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.internal.ih.C1390f;
import java.util.HashSet;
import java.util.Set;

public class ip implements Creator<C1390f> {
    static void m1086a(C1390f c1390f, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        Set ja = c1390f.ja();
        if (ja.contains(Integer.valueOf(1))) {
            C0262b.m234c(parcel, 1, c1390f.getVersionCode());
        }
        if (ja.contains(Integer.valueOf(2))) {
            C0262b.m222a(parcel, 2, c1390f.getDepartment(), true);
        }
        if (ja.contains(Integer.valueOf(3))) {
            C0262b.m222a(parcel, 3, c1390f.getDescription(), true);
        }
        if (ja.contains(Integer.valueOf(4))) {
            C0262b.m222a(parcel, 4, c1390f.getEndDate(), true);
        }
        if (ja.contains(Integer.valueOf(5))) {
            C0262b.m222a(parcel, 5, c1390f.getLocation(), true);
        }
        if (ja.contains(Integer.valueOf(6))) {
            C0262b.m222a(parcel, 6, c1390f.getName(), true);
        }
        if (ja.contains(Integer.valueOf(7))) {
            C0262b.m225a(parcel, 7, c1390f.isPrimary());
        }
        if (ja.contains(Integer.valueOf(8))) {
            C0262b.m222a(parcel, 8, c1390f.getStartDate(), true);
        }
        if (ja.contains(Integer.valueOf(9))) {
            C0262b.m222a(parcel, 9, c1390f.getTitle(), true);
        }
        if (ja.contains(Integer.valueOf(10))) {
            C0262b.m234c(parcel, 10, c1390f.getType());
        }
        C0262b.m211F(parcel, p);
    }

    public C1390f aU(Parcel parcel) {
        int i = 0;
        String str = null;
        int o = C0261a.m196o(parcel);
        Set hashSet = new HashSet();
        String str2 = null;
        boolean z = false;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i2 = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Std.STD_URL /*2*/:
                    str7 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Std.STD_URI /*3*/:
                    str6 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(3));
                    break;
                case Std.STD_CLASS /*4*/:
                    str5 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(4));
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str4 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(5));
                    break;
                case Std.STD_CURRENCY /*6*/:
                    str3 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(6));
                    break;
                case Std.STD_PATTERN /*7*/:
                    z = C0261a.m183c(parcel, n);
                    hashSet.add(Integer.valueOf(7));
                    break;
                case Std.STD_LOCALE /*8*/:
                    str2 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(8));
                    break;
                case Std.STD_CHARSET /*9*/:
                    str = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(9));
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    i = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(10));
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C1390f(hashSet, i2, str7, str6, str5, str4, str3, z, str2, str, i);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public C1390f[] bX(int i) {
        return new C1390f[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aU(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bX(x0);
    }
}
