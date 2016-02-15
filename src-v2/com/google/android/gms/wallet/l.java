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
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.CountrySpecification;
import com.google.android.gms.wallet.MaskedWalletRequest;
import java.util.ArrayList;

public class l
implements Parcelable.Creator<MaskedWalletRequest> {
    static void a(MaskedWalletRequest maskedWalletRequest, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, maskedWalletRequest.getVersionCode());
        b.a(parcel, 2, maskedWalletRequest.abi, false);
        b.a(parcel, 3, maskedWalletRequest.abV);
        b.a(parcel, 4, maskedWalletRequest.abW);
        b.a(parcel, 5, maskedWalletRequest.abX);
        b.a(parcel, 6, maskedWalletRequest.abY, false);
        b.a(parcel, 7, maskedWalletRequest.abd, false);
        b.a(parcel, 8, maskedWalletRequest.abZ, false);
        b.a(parcel, 9, maskedWalletRequest.abr, n2, false);
        b.a(parcel, 10, maskedWalletRequest.aca);
        b.a(parcel, 11, maskedWalletRequest.acb);
        b.a((Parcel)parcel, (int)12, (Parcelable[])maskedWalletRequest.acc, (int)n2, (boolean)false);
        b.a(parcel, 13, maskedWalletRequest.acd);
        b.a(parcel, 14, maskedWalletRequest.ace);
        b.b(parcel, 15, maskedWalletRequest.acf, false);
        b.F(parcel, n3);
    }

    public MaskedWalletRequest bh(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        Cart cart = null;
        boolean bl5 = false;
        boolean bl6 = false;
        CountrySpecification[] arrcountrySpecification = null;
        boolean bl7 = true;
        boolean bl8 = true;
        ArrayList<com.google.android.gms.identity.intents.model.CountrySpecification> arrayList = null;
        block17 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block17;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block17;
                }
                case 2: {
                    string2 = a.n(parcel, n4);
                    continue block17;
                }
                case 3: {
                    bl2 = a.c(parcel, n4);
                    continue block17;
                }
                case 4: {
                    bl3 = a.c(parcel, n4);
                    continue block17;
                }
                case 5: {
                    bl4 = a.c(parcel, n4);
                    continue block17;
                }
                case 6: {
                    string3 = a.n(parcel, n4);
                    continue block17;
                }
                case 7: {
                    string4 = a.n(parcel, n4);
                    continue block17;
                }
                case 8: {
                    string5 = a.n(parcel, n4);
                    continue block17;
                }
                case 9: {
                    cart = a.a(parcel, n4, Cart.CREATOR);
                    continue block17;
                }
                case 10: {
                    bl5 = a.c(parcel, n4);
                    continue block17;
                }
                case 11: {
                    bl6 = a.c(parcel, n4);
                    continue block17;
                }
                case 12: {
                    arrcountrySpecification = a.b(parcel, n4, CountrySpecification.CREATOR);
                    continue block17;
                }
                case 13: {
                    bl7 = a.c(parcel, n4);
                    continue block17;
                }
                case 14: {
                    bl8 = a.c(parcel, n4);
                    continue block17;
                }
                case 15: 
            }
            arrayList = a.c(parcel, n4, com.google.android.gms.identity.intents.model.CountrySpecification.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new MaskedWalletRequest(n3, string2, bl2, bl3, bl4, string3, string4, string5, cart, bl5, bl6, arrcountrySpecification, bl7, bl8, arrayList);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bh(parcel);
    }

    public MaskedWalletRequest[] ct(int n2) {
        return new MaskedWalletRequest[n2];
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.ct(n2);
    }
}

