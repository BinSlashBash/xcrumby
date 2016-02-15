package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.google.android.gms.internal.if */
public class C0404if implements Creator<ie> {
    static void m1078a(ie ieVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        Set ja = ieVar.ja();
        if (ja.contains(Integer.valueOf(1))) {
            C0262b.m234c(parcel, 1, ieVar.getVersionCode());
        }
        if (ja.contains(Integer.valueOf(2))) {
            C0262b.m222a(parcel, 2, ieVar.getId(), true);
        }
        if (ja.contains(Integer.valueOf(4))) {
            C0262b.m219a(parcel, 4, ieVar.jr(), i, true);
        }
        if (ja.contains(Integer.valueOf(5))) {
            C0262b.m222a(parcel, 5, ieVar.getStartDate(), true);
        }
        if (ja.contains(Integer.valueOf(6))) {
            C0262b.m219a(parcel, 6, ieVar.js(), i, true);
        }
        if (ja.contains(Integer.valueOf(7))) {
            C0262b.m222a(parcel, 7, ieVar.getType(), true);
        }
        C0262b.m211F(parcel, p);
    }

    public ie aM(Parcel parcel) {
        String str = null;
        int o = C0261a.m196o(parcel);
        Set hashSet = new HashSet();
        int i = 0;
        ic icVar = null;
        String str2 = null;
        ic icVar2 = null;
        String str3 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            ic icVar3;
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Std.STD_URL /*2*/:
                    str3 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Std.STD_CLASS /*4*/:
                    icVar3 = (ic) C0261a.m176a(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(4));
                    icVar2 = icVar3;
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str2 = C0261a.m195n(parcel, n);
                    hashSet.add(Integer.valueOf(5));
                    break;
                case Std.STD_CURRENCY /*6*/:
                    icVar3 = (ic) C0261a.m176a(parcel, n, ic.CREATOR);
                    hashSet.add(Integer.valueOf(6));
                    icVar = icVar3;
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
            return new ie(hashSet, i, str3, icVar2, str2, icVar, str);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public ie[] bP(int i) {
        return new ie[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aM(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bP(x0);
    }
}
