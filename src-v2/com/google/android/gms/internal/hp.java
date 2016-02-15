/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.hm;
import com.google.android.gms.internal.hn;
import com.google.android.gms.internal.ho;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngCreator;
import java.util.ArrayList;
import java.util.List;

public class hp
implements Parcelable.Creator<ho> {
    static void a(ho ho2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.a(parcel, 1, ho2.getName(), false);
        b.c(parcel, 1000, ho2.xH);
        b.a(parcel, 2, ho2.ia(), n2, false);
        b.a(parcel, 3, ho2.getAddress(), false);
        b.b(parcel, 4, ho2.ib(), false);
        b.a(parcel, 5, ho2.getPhoneNumber(), false);
        b.a(parcel, 6, ho2.ic(), false);
        b.F(parcel, n3);
    }

    public ho aH(Parcel parcel) {
        String string2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        String string3 = null;
        ArrayList<hm> arrayList = null;
        String string4 = null;
        LatLng latLng = null;
        String string5 = null;
        block9 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block9;
                }
                case 1: {
                    string5 = a.n(parcel, n4);
                    continue block9;
                }
                case 1000: {
                    n3 = a.g(parcel, n4);
                    continue block9;
                }
                case 2: {
                    latLng = (LatLng)a.a(parcel, n4, LatLng.CREATOR);
                    continue block9;
                }
                case 3: {
                    string4 = a.n(parcel, n4);
                    continue block9;
                }
                case 4: {
                    arrayList = a.c(parcel, n4, hm.CREATOR);
                    continue block9;
                }
                case 5: {
                    string3 = a.n(parcel, n4);
                    continue block9;
                }
                case 6: 
            }
            string2 = a.n(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ho(n3, string5, latLng, string4, arrayList, string3, string2);
    }

    public ho[] bI(int n2) {
        return new ho[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aH(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bI(n2);
    }
}

