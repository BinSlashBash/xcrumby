/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.wallet.Address;
import com.google.android.gms.wallet.InstrumentInfo;
import com.google.android.gms.wallet.LoyaltyWalletObject;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.OfferWalletObject;

public class k
implements Parcelable.Creator<MaskedWallet> {
    static void a(MaskedWallet maskedWallet, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, maskedWallet.getVersionCode());
        b.a(parcel, 2, maskedWallet.abh, false);
        b.a(parcel, 3, maskedWallet.abi, false);
        b.a(parcel, 4, maskedWallet.abn, false);
        b.a(parcel, 5, maskedWallet.abk, false);
        b.a(parcel, 6, maskedWallet.abl, n2, false);
        b.a(parcel, 7, maskedWallet.abm, n2, false);
        b.a((Parcel)parcel, (int)8, (Parcelable[])maskedWallet.abT, (int)n2, (boolean)false);
        b.a((Parcel)parcel, (int)9, (Parcelable[])maskedWallet.abU, (int)n2, (boolean)false);
        b.a(parcel, 10, maskedWallet.abo, n2, false);
        b.a(parcel, 11, maskedWallet.abp, n2, false);
        b.a((Parcel)parcel, (int)12, (Parcelable[])maskedWallet.abq, (int)n2, (boolean)false);
        b.F(parcel, n3);
    }

    public MaskedWallet bg(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        String string3 = null;
        String[] arrstring = null;
        String string4 = null;
        Address address = null;
        Address address2 = null;
        LoyaltyWalletObject[] arrloyaltyWalletObject = null;
        OfferWalletObject[] arrofferWalletObject = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        InstrumentInfo[] arrinstrumentInfo = null;
        block14 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block14;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block14;
                }
                case 2: {
                    string2 = a.n(parcel, n4);
                    continue block14;
                }
                case 3: {
                    string3 = a.n(parcel, n4);
                    continue block14;
                }
                case 4: {
                    arrstring = a.z(parcel, n4);
                    continue block14;
                }
                case 5: {
                    string4 = a.n(parcel, n4);
                    continue block14;
                }
                case 6: {
                    address = a.a(parcel, n4, Address.CREATOR);
                    continue block14;
                }
                case 7: {
                    address2 = a.a(parcel, n4, Address.CREATOR);
                    continue block14;
                }
                case 8: {
                    arrloyaltyWalletObject = a.b(parcel, n4, LoyaltyWalletObject.CREATOR);
                    continue block14;
                }
                case 9: {
                    arrofferWalletObject = a.b(parcel, n4, OfferWalletObject.CREATOR);
                    continue block14;
                }
                case 10: {
                    userAddress = a.a(parcel, n4, UserAddress.CREATOR);
                    continue block14;
                }
                case 11: {
                    userAddress2 = a.a(parcel, n4, UserAddress.CREATOR);
                    continue block14;
                }
                case 12: 
            }
            arrinstrumentInfo = a.b(parcel, n4, InstrumentInfo.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new MaskedWallet(n3, string2, string3, arrstring, string4, address, address2, arrloyaltyWalletObject, arrofferWalletObject, userAddress, userAddress2, arrinstrumentInfo);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bg(parcel);
    }

    public MaskedWallet[] cs(int n2) {
        return new MaskedWallet[n2];
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cs(n2);
    }
}

