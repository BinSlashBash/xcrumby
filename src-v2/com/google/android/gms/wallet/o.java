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
import com.google.android.gms.wallet.ProxyCard;

public class o
implements Parcelable.Creator<ProxyCard> {
    static void a(ProxyCard proxyCard, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, proxyCard.getVersionCode());
        b.a(parcel, 2, proxyCard.ack, false);
        b.a(parcel, 3, proxyCard.acl, false);
        b.c(parcel, 4, proxyCard.acm);
        b.c(parcel, 5, proxyCard.acn);
        b.F(parcel, n2);
    }

    public ProxyCard bk(Parcel parcel) {
        String string2 = null;
        int n2 = 0;
        int n3 = a.o(parcel);
        int n4 = 0;
        String string3 = null;
        int n5 = 0;
        block7 : while (parcel.dataPosition() < n3) {
            int n6 = a.n(parcel);
            switch (a.R(n6)) {
                default: {
                    a.b(parcel, n6);
                    continue block7;
                }
                case 1: {
                    n5 = a.g(parcel, n6);
                    continue block7;
                }
                case 2: {
                    string3 = a.n(parcel, n6);
                    continue block7;
                }
                case 3: {
                    string2 = a.n(parcel, n6);
                    continue block7;
                }
                case 4: {
                    n4 = a.g(parcel, n6);
                    continue block7;
                }
                case 5: 
            }
            n2 = a.g(parcel, n6);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new ProxyCard(n5, string3, string2, n4, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bk(parcel);
    }

    public ProxyCard[] cw(int n2) {
        return new ProxyCard[n2];
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cw(n2);
    }
}

