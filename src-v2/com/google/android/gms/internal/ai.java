/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.av;
import com.google.android.gms.internal.aw;
import java.util.ArrayList;
import java.util.List;

public class ai
implements Parcelable.Creator<ah> {
    static void a(ah ah2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, ah2.versionCode);
        b.a(parcel, 2, ah2.lH);
        b.a(parcel, 3, ah2.extras, false);
        b.c(parcel, 4, ah2.lI);
        b.a(parcel, 5, ah2.lJ, false);
        b.a(parcel, 6, ah2.lK);
        b.c(parcel, 7, ah2.lL);
        b.a(parcel, 8, ah2.lM);
        b.a(parcel, 9, ah2.lN, false);
        b.a(parcel, 10, ah2.lO, n2, false);
        b.a(parcel, 11, (Parcelable)ah2.lP, n2, false);
        b.a(parcel, 12, ah2.lQ, false);
        b.F(parcel, n3);
    }

    public ah a(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        long l2 = 0;
        Bundle bundle = null;
        int n4 = 0;
        ArrayList<String> arrayList = null;
        boolean bl2 = false;
        int n5 = 0;
        boolean bl3 = false;
        String string2 = null;
        av av2 = null;
        Location location = null;
        String string3 = null;
        block14 : while (parcel.dataPosition() < n2) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block14;
                }
                case 1: {
                    n3 = a.g(parcel, n6);
                    continue block14;
                }
                case 2: {
                    l2 = a.i(parcel, n6);
                    continue block14;
                }
                case 3: {
                    bundle = a.p(parcel, n6);
                    continue block14;
                }
                case 4: {
                    n4 = a.g(parcel, n6);
                    continue block14;
                }
                case 5: {
                    arrayList = a.A(parcel, n6);
                    continue block14;
                }
                case 6: {
                    bl2 = a.c(parcel, n6);
                    continue block14;
                }
                case 7: {
                    n5 = a.g(parcel, n6);
                    continue block14;
                }
                case 8: {
                    bl3 = a.c(parcel, n6);
                    continue block14;
                }
                case 9: {
                    string2 = a.n(parcel, n6);
                    continue block14;
                }
                case 10: {
                    av2 = (av)a.a(parcel, n6, av.CREATOR);
                    continue block14;
                }
                case 11: {
                    location = (Location)a.a(parcel, n6, Location.CREATOR);
                    continue block14;
                }
                case 12: 
            }
            string3 = a.n(parcel, n6);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ah(n3, l2, bundle, n4, arrayList, bl2, n5, bl3, string2, av2, location, string3);
    }

    public ah[] b(int n2) {
        return new ah[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.a(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.b(n2);
    }
}

