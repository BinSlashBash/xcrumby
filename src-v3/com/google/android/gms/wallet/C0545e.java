package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;

/* renamed from: com.google.android.gms.wallet.e */
public class C0545e implements Creator<C1096d> {
    static void m1491a(C1096d c1096d, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, c1096d.getVersionCode());
        C0262b.m219a(parcel, 2, c1096d.abg, i, false);
        C0262b.m211F(parcel, p);
    }

    public C1096d ba(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        LoyaltyWalletObject loyaltyWalletObject = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    loyaltyWalletObject = (LoyaltyWalletObject) C0261a.m176a(parcel, n, LoyaltyWalletObject.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new C1096d(i, loyaltyWalletObject);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public C1096d[] cm(int i) {
        return new C1096d[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ba(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cm(x0);
    }
}
