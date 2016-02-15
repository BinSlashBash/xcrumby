package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.wallet.fragment.b */
public class C0552b implements Creator<WalletFragmentOptions> {
    static void m1512a(WalletFragmentOptions walletFragmentOptions, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, walletFragmentOptions.xH);
        C0262b.m234c(parcel, 2, walletFragmentOptions.getEnvironment());
        C0262b.m234c(parcel, 3, walletFragmentOptions.getTheme());
        C0262b.m219a(parcel, 4, walletFragmentOptions.getFragmentStyle(), i, false);
        C0262b.m234c(parcel, 5, walletFragmentOptions.getMode());
        C0262b.m211F(parcel, p);
    }

    public WalletFragmentOptions bo(Parcel parcel) {
        int i = 1;
        int i2 = 0;
        int o = C0261a.m196o(parcel);
        WalletFragmentStyle walletFragmentStyle = null;
        int i3 = 1;
        int i4 = 0;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i4 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    i3 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    i2 = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    walletFragmentStyle = (WalletFragmentStyle) C0261a.m176a(parcel, n, WalletFragmentStyle.CREATOR);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new WalletFragmentOptions(i4, i3, i2, walletFragmentStyle, i);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public WalletFragmentOptions[] cB(int i) {
        return new WalletFragmentOptions[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bo(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cB(x0);
    }
}
