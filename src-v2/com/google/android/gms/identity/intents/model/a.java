/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.identity.intents.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.identity.intents.model.CountrySpecification;

public class a
implements Parcelable.Creator<CountrySpecification> {
    static void a(CountrySpecification countrySpecification, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, countrySpecification.getVersionCode());
        b.a(parcel, 2, countrySpecification.qd, false);
        b.F(parcel, n2);
    }

    public CountrySpecification az(Parcel parcel) {
        int n2 = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int n3 = 0;
        String string2 = null;
        block4 : while (parcel.dataPosition() < n2) {
            int n4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(n4)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, n4);
                    continue block4;
                }
                case 1: {
                    n3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n4);
                    continue block4;
                }
                case 2: 
            }
            string2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new CountrySpecification(n3, string2);
    }

    public CountrySpecification[] bt(int n2) {
        return new CountrySpecification[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.az(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bt(n2);
    }
}

