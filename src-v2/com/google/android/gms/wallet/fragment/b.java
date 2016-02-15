/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;

public class b
implements Parcelable.Creator<WalletFragmentOptions> {
    static void a(WalletFragmentOptions walletFragmentOptions, Parcel parcel, int n2) {
        int n3 = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, walletFragmentOptions.xH);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 2, walletFragmentOptions.getEnvironment());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, walletFragmentOptions.getTheme());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, walletFragmentOptions.getFragmentStyle(), n2, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 5, walletFragmentOptions.getMode());
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, n3);
    }

    public WalletFragmentOptions bo(Parcel parcel) {
        int n2 = 1;
        int n3 = 0;
        int n4 = a.o(parcel);
        WalletFragmentStyle walletFragmentStyle = null;
        int n5 = 1;
        int n6 = 0;
        block7 : while (parcel.dataPosition() < n4) {
            int n7 = a.n(parcel);
            switch (a.R(n7)) {
                default: {
                    a.b(parcel, n7);
                    continue block7;
                }
                case 1: {
                    n6 = a.g(parcel, n7);
                    continue block7;
                }
                case 2: {
                    n5 = a.g(parcel, n7);
                    continue block7;
                }
                case 3: {
                    n3 = a.g(parcel, n7);
                    continue block7;
                }
                case 4: {
                    walletFragmentStyle = a.a(parcel, n7, WalletFragmentStyle.CREATOR);
                    continue block7;
                }
                case 5: 
            }
            n2 = a.g(parcel, n7);
        }
        if (parcel.dataPosition() != n4) {
            throw new a.a("Overread allowed size end=" + n4, parcel);
        }
        return new WalletFragmentOptions(n6, n5, n3, walletFragmentStyle, n2);
    }

    public WalletFragmentOptions[] cB(int n2) {
        return new WalletFragmentOptions[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bo(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cB(n2);
    }
}

