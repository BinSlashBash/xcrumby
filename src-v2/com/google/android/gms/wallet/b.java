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
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.LineItem;
import java.util.ArrayList;

public class b
implements Parcelable.Creator<Cart> {
    static void a(Cart cart, Parcel parcel, int n2) {
        n2 = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, cart.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, cart.abc, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, cart.abd, false);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 4, cart.abe, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, n2);
    }

    public Cart aY(Parcel parcel) {
        String string2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        ArrayList arrayList = new ArrayList<LineItem>();
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
            arrayList = a.c(parcel, n4, LineItem.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new Cart(n3, string3, string2, arrayList);
    }

    public Cart[] ck(int n2) {
        return new Cart[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aY(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.ck(n2);
    }
}

