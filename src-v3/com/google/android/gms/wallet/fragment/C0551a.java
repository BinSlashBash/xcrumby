package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

/* renamed from: com.google.android.gms.wallet.fragment.a */
public class C0551a implements Creator<WalletFragmentInitParams> {
    static void m1511a(WalletFragmentInitParams walletFragmentInitParams, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, walletFragmentInitParams.xH);
        C0262b.m222a(parcel, 2, walletFragmentInitParams.getAccountName(), false);
        C0262b.m219a(parcel, 3, walletFragmentInitParams.getMaskedWalletRequest(), i, false);
        C0262b.m234c(parcel, 4, walletFragmentInitParams.getMaskedWalletRequestCode());
        C0262b.m219a(parcel, 5, walletFragmentInitParams.getMaskedWallet(), i, false);
        C0262b.m211F(parcel, p);
    }

    public WalletFragmentInitParams bn(Parcel parcel) {
        MaskedWallet maskedWallet = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        int i2 = -1;
        MaskedWalletRequest maskedWalletRequest = null;
        String str = null;
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
                    maskedWalletRequest = (MaskedWalletRequest) C0261a.m176a(parcel, n, MaskedWalletRequest.CREATOR);
                    break;
                case Std.STD_CLASS /*4*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    maskedWallet = (MaskedWallet) C0261a.m176a(parcel, n, MaskedWallet.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new WalletFragmentInitParams(i, str, maskedWalletRequest, i2, maskedWallet);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public WalletFragmentInitParams[] cA(int i) {
        return new WalletFragmentInitParams[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bn(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cA(x0);
    }
}
