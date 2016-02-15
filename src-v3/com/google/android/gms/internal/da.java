package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.crumby.C0065R;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.games.GamesStatusCodes;
import java.util.List;

public class da implements Creator<cz> {
    static void m720a(cz czVar, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, czVar.versionCode);
        C0262b.m222a(parcel, 2, czVar.ol, false);
        C0262b.m222a(parcel, 3, czVar.pm, false);
        C0262b.m223a(parcel, 4, czVar.ne, false);
        C0262b.m234c(parcel, 5, czVar.errorCode);
        C0262b.m223a(parcel, 6, czVar.nf, false);
        C0262b.m215a(parcel, 7, czVar.pn);
        C0262b.m225a(parcel, 8, czVar.po);
        C0262b.m215a(parcel, 9, czVar.pp);
        C0262b.m223a(parcel, 10, czVar.pq, false);
        C0262b.m215a(parcel, 11, czVar.ni);
        C0262b.m234c(parcel, 12, czVar.orientation);
        C0262b.m222a(parcel, 13, czVar.pr, false);
        C0262b.m215a(parcel, 14, czVar.ps);
        C0262b.m222a(parcel, 15, czVar.pt, false);
        C0262b.m222a(parcel, 19, czVar.pv, false);
        C0262b.m225a(parcel, 18, czVar.pu);
        C0262b.m222a(parcel, 21, czVar.pw, false);
        C0262b.m211F(parcel, p);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m721g(x0);
    }

    public cz m721g(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        List list = null;
        int i2 = 0;
        List list2 = null;
        long j = 0;
        boolean z = false;
        long j2 = 0;
        List list3 = null;
        long j3 = 0;
        int i3 = 0;
        String str3 = null;
        long j4 = 0;
        String str4 = null;
        boolean z2 = false;
        String str5 = null;
        String str6 = null;
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
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    list = C0261a.m171A(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    list2 = C0261a.m171A(parcel, n);
                    break;
                case Std.STD_PATTERN /*7*/:
                    j = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_LOCALE /*8*/:
                    z = C0261a.m183c(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    j2 = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    list3 = C0261a.m171A(parcel, n);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    j3 = C0261a.m189i(parcel, n);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case CommonStatusCodes.ERROR /*13*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    j4 = C0261a.m189i(parcel, n);
                    break;
                case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                    str4 = C0261a.m195n(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_paddingBottom /*18*/:
                    z2 = C0261a.m183c(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_focusable /*19*/:
                    str5 = C0261a.m195n(parcel, n);
                    break;
                case C0065R.styleable.TwoWayView_android_visibility /*21*/:
                    str6 = C0261a.m195n(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new cz(i, str, str2, list, i2, list2, j, z, j2, list3, j3, i3, str3, j4, str4, z2, str5, str6);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public cz[] m722l(int i) {
        return new cz[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m722l(x0);
    }
}
