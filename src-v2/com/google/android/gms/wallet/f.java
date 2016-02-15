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
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.InstrumentInfo;
import com.google.android.gms.wallet.ProxyCard;

public class f
implements Parcelable.Creator<FullWallet> {
    static void a(FullWallet fullWallet, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, fullWallet.getVersionCode());
        b.a(parcel, 2, fullWallet.abh, false);
        b.a(parcel, 3, fullWallet.abi, false);
        b.a(parcel, 4, fullWallet.abj, n2, false);
        b.a(parcel, 5, fullWallet.abk, false);
        b.a(parcel, 6, fullWallet.abl, n2, false);
        b.a(parcel, 7, fullWallet.abm, n2, false);
        b.a(parcel, 8, fullWallet.abn, false);
        b.a(parcel, 9, fullWallet.abo, n2, false);
        b.a(parcel, 10, fullWallet.abp, n2, false);
        b.a((Parcel)parcel, (int)11, (Parcelable[])fullWallet.abq, (int)n2, (boolean)false);
        b.F(parcel, n3);
    }

    public FullWallet bb(Parcel parcel) {
        InstrumentInfo[] arrinstrumentInfo = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        String[] arrstring = null;
        Address address = null;
        Address address2 = null;
        String string2 = null;
        ProxyCard proxyCard = null;
        String string3 = null;
        String string4 = null;
        block13 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block13;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block13;
                }
                case 2: {
                    string4 = a.n(parcel, n4);
                    continue block13;
                }
                case 3: {
                    string3 = a.n(parcel, n4);
                    continue block13;
                }
                case 4: {
                    proxyCard = a.a(parcel, n4, ProxyCard.CREATOR);
                    continue block13;
                }
                case 5: {
                    string2 = a.n(parcel, n4);
                    continue block13;
                }
                case 6: {
                    address2 = a.a(parcel, n4, Address.CREATOR);
                    continue block13;
                }
                case 7: {
                    address = a.a(parcel, n4, Address.CREATOR);
                    continue block13;
                }
                case 8: {
                    arrstring = a.z(parcel, n4);
                    continue block13;
                }
                case 9: {
                    userAddress2 = a.a(parcel, n4, UserAddress.CREATOR);
                    continue block13;
                }
                case 10: {
                    userAddress = a.a(parcel, n4, UserAddress.CREATOR);
                    continue block13;
                }
                case 11: 
            }
            arrinstrumentInfo = a.b(parcel, n4, InstrumentInfo.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new FullWallet(n3, string4, string3, proxyCard, string2, address2, address, arrstring, userAddress2, userAddress, arrinstrumentInfo);
    }

    public FullWallet[] cn(int n2) {
        return new FullWallet[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bb(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cn(n2);
    }
}

