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
import com.google.android.gms.wallet.FullWalletRequest;

public class g
implements Parcelable.Creator<FullWalletRequest> {
    static void a(FullWalletRequest fullWalletRequest, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, fullWalletRequest.getVersionCode());
        b.a(parcel, 2, fullWalletRequest.abh, false);
        b.a(parcel, 3, fullWalletRequest.abi, false);
        b.a(parcel, 4, fullWalletRequest.abr, n2, false);
        b.F(parcel, n3);
    }

    public FullWalletRequest bc(Parcel parcel) {
        Cart cart = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        String string3 = null;
        block6 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block6;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block6;
                }
                case 2: {
                    string3 = a.n(parcel, n4);
                    continue block6;
                }
                case 3: {
                    string2 = a.n(parcel, n4);
                    continue block6;
                }
                case 4: 
            }
            cart = a.a(parcel, n4, Cart.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new FullWalletRequest(n3, string3, string2, cart);
    }

    public FullWalletRequest[] co(int n2) {
        return new FullWalletRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bc(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.co(n2);
    }
}

