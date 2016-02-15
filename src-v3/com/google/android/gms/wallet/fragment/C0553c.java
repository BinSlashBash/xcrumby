package com.google.android.gms.wallet.fragment;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.wallet.fragment.c */
public class C0553c implements Creator<WalletFragmentStyle> {
    static void m1513a(WalletFragmentStyle walletFragmentStyle, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, walletFragmentStyle.xH);
        C0262b.m216a(parcel, 2, walletFragmentStyle.acT, false);
        C0262b.m234c(parcel, 3, walletFragmentStyle.acU);
        C0262b.m211F(parcel, p);
    }

    public WalletFragmentStyle bp(Parcel parcel) {
        int i = 0;
        int o = C0261a.m196o(parcel);
        Bundle bundle = null;
        int i2 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    bundle = C0261a.m198p(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new WalletFragmentStyle(i2, bundle, i);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public WalletFragmentStyle[] cC(int i) {
        return new WalletFragmentStyle[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bp(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cC(x0);
    }
}
