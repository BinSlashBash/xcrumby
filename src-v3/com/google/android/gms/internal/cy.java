package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

public class cy implements Creator<cx> {
    static void m717a(cx cxVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, cxVar.versionCode);
        C0262b.m216a(parcel, 2, cxVar.pf, false);
        C0262b.m219a(parcel, 3, cxVar.pg, i, false);
        C0262b.m219a(parcel, 4, cxVar.kN, i, false);
        C0262b.m222a(parcel, 5, cxVar.kH, false);
        C0262b.m219a(parcel, 6, cxVar.applicationInfo, i, false);
        C0262b.m219a(parcel, 7, cxVar.ph, i, false);
        C0262b.m222a(parcel, 8, cxVar.pi, false);
        C0262b.m222a(parcel, 9, cxVar.pj, false);
        C0262b.m222a(parcel, 10, cxVar.pk, false);
        C0262b.m219a(parcel, 11, cxVar.kK, i, false);
        C0262b.m216a(parcel, 12, cxVar.pl, false);
        C0262b.m211F(parcel, p);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m718f(x0);
    }

    public cx m718f(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        Bundle bundle = null;
        ah ahVar = null;
        ak akVar = null;
        String str = null;
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        dx dxVar = null;
        Bundle bundle2 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    bundle = C0261a.m198p(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    ahVar = (ah) C0261a.m176a(parcel, n, ah.CREATOR);
                    break;
                case Std.STD_CLASS /*4*/:
                    akVar = (ak) C0261a.m176a(parcel, n, ak.CREATOR);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    applicationInfo = (ApplicationInfo) C0261a.m176a(parcel, n, ApplicationInfo.CREATOR);
                    break;
                case Std.STD_PATTERN /*7*/:
                    packageInfo = (PackageInfo) C0261a.m176a(parcel, n, PackageInfo.CREATOR);
                    break;
                case Std.STD_LOCALE /*8*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    str4 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    dxVar = (dx) C0261a.m176a(parcel, n, dx.CREATOR);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    bundle2 = C0261a.m198p(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new cx(i, bundle, ahVar, akVar, str, applicationInfo, packageInfo, str2, str3, str4, dxVar, bundle2);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public cx[] m719k(int i) {
        return new cx[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m719k(x0);
    }
}
