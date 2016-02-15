package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.wallet.g */
public class C0554g implements Creator<FullWalletRequest> {
    static void m1514a(FullWalletRequest fullWalletRequest, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, fullWalletRequest.getVersionCode());
        C0262b.m222a(parcel, 2, fullWalletRequest.abh, false);
        C0262b.m222a(parcel, 3, fullWalletRequest.abi, false);
        C0262b.m219a(parcel, 4, fullWalletRequest.abr, i, false);
        C0262b.m211F(parcel, p);
    }

    public FullWalletRequest bc(Parcel parcel) {
        Cart cart = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    cart = (Cart) C0261a.m176a(parcel, n, Cart.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new FullWalletRequest(i, str2, str, cart);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public FullWalletRequest[] co(int i) {
        return new FullWalletRequest[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bc(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return co(x0);
    }
}
