/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ki;

public class kj
implements Parcelable.Creator<ki> {
    static void a(ki ki2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, ki2.xH);
        b.c(parcel, 2, ki2.fA());
        b.a(parcel, 3, ki2.getPath(), false);
        b.a(parcel, 4, ki2.getData(), false);
        b.a(parcel, 5, ki2.getSource(), false);
        b.F(parcel, n2);
    }

    public ki by(Parcel parcel) {
        int n2 = 0;
        String string2 = null;
        int n3 = a.o(parcel);
        Object object = null;
        String string3 = null;
        int n4 = 0;
        block7 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block7;
                }
                case 1: {
                    n4 = a.g(parcel, n5);
                    continue block7;
                }
                case 2: {
                    n2 = a.g(parcel, n5);
                    continue block7;
                }
                case 3: {
                    string3 = a.n(parcel, n5);
                    continue block7;
                }
                case 4: {
                    object = a.q(parcel, n5);
                    continue block7;
                }
                case 5: 
            }
            string2 = a.n(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new ki(n4, n2, string3, (byte[])object, string2);
    }

    public ki[] cN(int n2) {
        return new ki[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.by(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cN(n2);
    }
}

