/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.wallet.CountrySpecification;

public class c
implements Parcelable.Creator<CountrySpecification> {
    static void a(CountrySpecification countrySpecification, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, countrySpecification.getVersionCode());
        b.a(parcel, 2, countrySpecification.qd, false);
        b.F(parcel, n2);
    }

    public CountrySpecification aZ(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        block4 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block4;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block4;
                }
                case 2: 
            }
            string2 = a.n(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new CountrySpecification(n3, string2);
    }

    public CountrySpecification[] cl(int n2) {
        return new CountrySpecification[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aZ(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cl(n2);
    }
}

