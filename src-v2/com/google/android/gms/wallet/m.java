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
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;

public class m
implements Parcelable.Creator<NotifyTransactionStatusRequest> {
    static void a(NotifyTransactionStatusRequest notifyTransactionStatusRequest, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, notifyTransactionStatusRequest.xH);
        b.a(parcel, 2, notifyTransactionStatusRequest.abh, false);
        b.c(parcel, 3, notifyTransactionStatusRequest.status);
        b.a(parcel, 4, notifyTransactionStatusRequest.ach, false);
        b.F(parcel, n2);
    }

    public NotifyTransactionStatusRequest bi(Parcel parcel) {
        String string2 = null;
        int n2 = 0;
        int n3 = a.o(parcel);
        String string3 = null;
        int n4 = 0;
        block6 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block6;
                }
                case 1: {
                    n4 = a.g(parcel, n5);
                    continue block6;
                }
                case 2: {
                    string3 = a.n(parcel, n5);
                    continue block6;
                }
                case 3: {
                    n2 = a.g(parcel, n5);
                    continue block6;
                }
                case 4: 
            }
            string2 = a.n(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new NotifyTransactionStatusRequest(n4, string3, n2, string2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bi(parcel);
    }

    public NotifyTransactionStatusRequest[] cu(int n2) {
        return new NotifyTransactionStatusRequest[n2];
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cu(n2);
    }
}

