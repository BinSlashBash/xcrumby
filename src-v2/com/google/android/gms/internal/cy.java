/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.ai;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.dx;
import com.google.android.gms.internal.dy;

public class cy
implements Parcelable.Creator<cx> {
    static void a(cx cx2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, cx2.versionCode);
        b.a(parcel, 2, cx2.pf, false);
        b.a(parcel, 3, cx2.pg, n2, false);
        b.a(parcel, 4, cx2.kN, n2, false);
        b.a(parcel, 5, cx2.kH, false);
        b.a(parcel, 6, (Parcelable)cx2.applicationInfo, n2, false);
        b.a(parcel, 7, (Parcelable)cx2.ph, n2, false);
        b.a(parcel, 8, cx2.pi, false);
        b.a(parcel, 9, cx2.pj, false);
        b.a(parcel, 10, cx2.pk, false);
        b.a(parcel, 11, cx2.kK, n2, false);
        b.a(parcel, 12, cx2.pl, false);
        b.F(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.f(parcel);
    }

    public cx f(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        Bundle bundle = null;
        ah ah2 = null;
        ak ak2 = null;
        String string2 = null;
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        dx dx2 = null;
        Bundle bundle2 = null;
        block14 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block14;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block14;
                }
                case 2: {
                    bundle = a.p(parcel, n4);
                    continue block14;
                }
                case 3: {
                    ah2 = (ah)a.a(parcel, n4, ah.CREATOR);
                    continue block14;
                }
                case 4: {
                    ak2 = (ak)a.a(parcel, n4, ak.CREATOR);
                    continue block14;
                }
                case 5: {
                    string2 = a.n(parcel, n4);
                    continue block14;
                }
                case 6: {
                    applicationInfo = (ApplicationInfo)a.a(parcel, n4, ApplicationInfo.CREATOR);
                    continue block14;
                }
                case 7: {
                    packageInfo = (PackageInfo)a.a(parcel, n4, PackageInfo.CREATOR);
                    continue block14;
                }
                case 8: {
                    string3 = a.n(parcel, n4);
                    continue block14;
                }
                case 9: {
                    string4 = a.n(parcel, n4);
                    continue block14;
                }
                case 10: {
                    string5 = a.n(parcel, n4);
                    continue block14;
                }
                case 11: {
                    dx2 = (dx)a.a(parcel, n4, dx.CREATOR);
                    continue block14;
                }
                case 12: 
            }
            bundle2 = a.p(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new cx(n3, bundle, ah2, ak2, string2, applicationInfo, packageInfo, string3, string4, string5, dx2, bundle2);
    }

    public cx[] k(int n2) {
        return new cx[n2];
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.k(n2);
    }
}

