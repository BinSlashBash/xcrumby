package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.identity.intents.model.UserAddress;

/* renamed from: com.google.android.gms.wallet.f */
public class C0546f implements Creator<FullWallet> {
    static void m1492a(FullWallet fullWallet, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, fullWallet.getVersionCode());
        C0262b.m222a(parcel, 2, fullWallet.abh, false);
        C0262b.m222a(parcel, 3, fullWallet.abi, false);
        C0262b.m219a(parcel, 4, fullWallet.abj, i, false);
        C0262b.m222a(parcel, 5, fullWallet.abk, false);
        C0262b.m219a(parcel, 6, fullWallet.abl, i, false);
        C0262b.m219a(parcel, 7, fullWallet.abm, i, false);
        C0262b.m229a(parcel, 8, fullWallet.abn, false);
        C0262b.m219a(parcel, 9, fullWallet.abo, i, false);
        C0262b.m219a(parcel, 10, fullWallet.abp, i, false);
        C0262b.m228a(parcel, 11, fullWallet.abq, i, false);
        C0262b.m211F(parcel, p);
    }

    public FullWallet bb(Parcel parcel) {
        InstrumentInfo[] instrumentInfoArr = null;
        int o = C0261a.m196o(parcel);
        int i = 0;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        String[] strArr = null;
        Address address = null;
        Address address2 = null;
        String str = null;
        ProxyCard proxyCard = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < o) {
            int n = C0261a.m194n(parcel);
            switch (C0261a.m174R(n)) {
                case Std.STD_FILE /*1*/:
                    i = C0261a.m187g(parcel, n);
                    break;
                case Std.STD_URL /*2*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_URI /*3*/:
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    proxyCard = (ProxyCard) C0261a.m176a(parcel, n, ProxyCard.CREATOR);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    address2 = (Address) C0261a.m176a(parcel, n, Address.CREATOR);
                    break;
                case Std.STD_PATTERN /*7*/:
                    address = (Address) C0261a.m176a(parcel, n, Address.CREATOR);
                    break;
                case Std.STD_LOCALE /*8*/:
                    strArr = C0261a.m208z(parcel, n);
                    break;
                case Std.STD_CHARSET /*9*/:
                    userAddress2 = (UserAddress) C0261a.m176a(parcel, n, UserAddress.CREATOR);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    userAddress = (UserAddress) C0261a.m176a(parcel, n, UserAddress.CREATOR);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    instrumentInfoArr = (InstrumentInfo[]) C0261a.m181b(parcel, n, InstrumentInfo.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new FullWallet(i, str3, str2, proxyCard, str, address2, address, strArr, userAddress2, userAddress, instrumentInfoArr);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public FullWallet[] cn(int i) {
        return new FullWallet[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bb(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cn(x0);
    }
}
