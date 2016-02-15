package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.internal.safeparcel.C0261a;
import com.google.android.gms.common.internal.safeparcel.C0261a.C0260a;
import com.google.android.gms.common.internal.safeparcel.C0262b;
import com.google.android.gms.identity.intents.model.UserAddress;

/* renamed from: com.google.android.gms.wallet.k */
public class C0558k implements Creator<MaskedWallet> {
    static void m1518a(MaskedWallet maskedWallet, Parcel parcel, int i) {
        int p = C0262b.m236p(parcel);
        C0262b.m234c(parcel, 1, maskedWallet.getVersionCode());
        C0262b.m222a(parcel, 2, maskedWallet.abh, false);
        C0262b.m222a(parcel, 3, maskedWallet.abi, false);
        C0262b.m229a(parcel, 4, maskedWallet.abn, false);
        C0262b.m222a(parcel, 5, maskedWallet.abk, false);
        C0262b.m219a(parcel, 6, maskedWallet.abl, i, false);
        C0262b.m219a(parcel, 7, maskedWallet.abm, i, false);
        C0262b.m228a(parcel, 8, maskedWallet.abT, i, false);
        C0262b.m228a(parcel, 9, maskedWallet.abU, i, false);
        C0262b.m219a(parcel, 10, maskedWallet.abo, i, false);
        C0262b.m219a(parcel, 11, maskedWallet.abp, i, false);
        C0262b.m228a(parcel, 12, maskedWallet.abq, i, false);
        C0262b.m211F(parcel, p);
    }

    public MaskedWallet bg(Parcel parcel) {
        int o = C0261a.m196o(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String[] strArr = null;
        String str3 = null;
        Address address = null;
        Address address2 = null;
        LoyaltyWalletObject[] loyaltyWalletObjectArr = null;
        OfferWalletObject[] offerWalletObjectArr = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        InstrumentInfo[] instrumentInfoArr = null;
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
                    str2 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CLASS /*4*/:
                    strArr = C0261a.m208z(parcel, n);
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    str3 = C0261a.m195n(parcel, n);
                    break;
                case Std.STD_CURRENCY /*6*/:
                    address = (Address) C0261a.m176a(parcel, n, Address.CREATOR);
                    break;
                case Std.STD_PATTERN /*7*/:
                    address2 = (Address) C0261a.m176a(parcel, n, Address.CREATOR);
                    break;
                case Std.STD_LOCALE /*8*/:
                    loyaltyWalletObjectArr = (LoyaltyWalletObject[]) C0261a.m181b(parcel, n, LoyaltyWalletObject.CREATOR);
                    break;
                case Std.STD_CHARSET /*9*/:
                    offerWalletObjectArr = (OfferWalletObject[]) C0261a.m181b(parcel, n, OfferWalletObject.CREATOR);
                    break;
                case Std.STD_TIME_ZONE /*10*/:
                    userAddress = (UserAddress) C0261a.m176a(parcel, n, UserAddress.CREATOR);
                    break;
                case Std.STD_INET_ADDRESS /*11*/:
                    userAddress2 = (UserAddress) C0261a.m176a(parcel, n, UserAddress.CREATOR);
                    break;
                case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                    instrumentInfoArr = (InstrumentInfo[]) C0261a.m181b(parcel, n, InstrumentInfo.CREATOR);
                    break;
                default:
                    C0261a.m180b(parcel, n);
                    break;
            }
        }
        if (parcel.dataPosition() == o) {
            return new MaskedWallet(i, str, str2, strArr, str3, address, address2, loyaltyWalletObjectArr, offerWalletObjectArr, userAddress, userAddress2, instrumentInfoArr);
        }
        throw new C0260a("Overread allowed size end=" + o, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bg(x0);
    }

    public MaskedWallet[] cs(int i) {
        return new MaskedWallet[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cs(x0);
    }
}
