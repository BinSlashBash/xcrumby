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
import com.google.android.gms.wallet.LoyaltyWalletObject;
import com.google.android.gms.wallet.d;

public class e
implements Parcelable.Creator<d> {
    static void a(d d2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, d2.getVersionCode());
        b.a(parcel, 2, d2.abg, n2, false);
        b.F(parcel, n3);
    }

    public d ba(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        LoyaltyWalletObject loyaltyWalletObject = null;
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
            loyaltyWalletObject = a.a(parcel, n4, LoyaltyWalletObject.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new d(n3, loyaltyWalletObject);
    }

    public d[] cm(int n2) {
        return new d[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ba(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cm(n2);
    }
}

