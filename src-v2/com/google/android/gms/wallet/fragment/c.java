/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.fragment;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;

public class c
implements Parcelable.Creator<WalletFragmentStyle> {
    static void a(WalletFragmentStyle walletFragmentStyle, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, walletFragmentStyle.xH);
        b.a(parcel, 2, walletFragmentStyle.acT, false);
        b.c(parcel, 3, walletFragmentStyle.acU);
        b.F(parcel, n2);
    }

    public WalletFragmentStyle bp(Parcel parcel) {
        int n2 = 0;
        int n3 = a.o(parcel);
        Bundle bundle = null;
        int n4 = 0;
        block5 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block5;
                }
                case 1: {
                    n4 = a.g(parcel, n5);
                    continue block5;
                }
                case 2: {
                    bundle = a.p(parcel, n5);
                    continue block5;
                }
                case 3: 
            }
            n2 = a.g(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new WalletFragmentStyle(n4, bundle, n2);
    }

    public WalletFragmentStyle[] cC(int n2) {
        return new WalletFragmentStyle[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bp(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cC(n2);
    }
}

