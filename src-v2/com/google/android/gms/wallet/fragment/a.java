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
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;

public class a
implements Parcelable.Creator<WalletFragmentInitParams> {
    static void a(WalletFragmentInitParams walletFragmentInitParams, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, walletFragmentInitParams.xH);
        b.a(parcel, 2, walletFragmentInitParams.getAccountName(), false);
        b.a(parcel, 3, walletFragmentInitParams.getMaskedWalletRequest(), n2, false);
        b.c(parcel, 4, walletFragmentInitParams.getMaskedWalletRequestCode());
        b.a(parcel, 5, walletFragmentInitParams.getMaskedWallet(), n2, false);
        b.F(parcel, n3);
    }

    public WalletFragmentInitParams bn(Parcel parcel) {
        MaskedWallet maskedWallet = null;
        int n2 = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int n3 = 0;
        int n4 = -1;
        MaskedWalletRequest maskedWalletRequest = null;
        String string2 = null;
        block7 : while (parcel.dataPosition() < n2) {
            int n5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(n5)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, n5);
                    continue block7;
                }
                case 1: {
                    n3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n5);
                    continue block7;
                }
                case 2: {
                    string2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, n5);
                    continue block7;
                }
                case 3: {
                    maskedWalletRequest = com.google.android.gms.common.internal.safeparcel.a.a(parcel, n5, MaskedWalletRequest.CREATOR);
                    continue block7;
                }
                case 4: {
                    n4 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n5);
                    continue block7;
                }
                case 5: 
            }
            maskedWallet = com.google.android.gms.common.internal.safeparcel.a.a(parcel, n5, MaskedWallet.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new WalletFragmentInitParams(n3, string2, maskedWalletRequest, n4, maskedWallet);
    }

    public WalletFragmentInitParams[] cA(int n2) {
        return new WalletFragmentInitParams[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bn(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cA(n2);
    }
}

