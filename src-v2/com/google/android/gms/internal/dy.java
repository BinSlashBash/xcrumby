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
import com.google.android.gms.internal.dx;

public class dy
implements Parcelable.Creator<dx> {
    static void a(dx dx2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, dx2.versionCode);
        b.a(parcel, 2, dx2.rq, false);
        b.c(parcel, 3, dx2.rr);
        b.c(parcel, 4, dx2.rs);
        b.a(parcel, 5, dx2.rt);
        b.F(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.h(parcel);
    }

    public dx h(Parcel parcel) {
        boolean bl2 = false;
        int n2 = a.o(parcel);
        String string2 = null;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        block7 : while (parcel.dataPosition() < n2) {
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
                    string2 = a.n(parcel, n6);
                    continue block7;
                }
                case 3: {
                    n4 = a.g(parcel, n6);
                    continue block7;
                }
                case 4: {
                    n3 = a.g(parcel, n6);
                    continue block7;
                }
                case 5: 
            }
            bl2 = a.c(parcel, n6);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new dx(n5, string2, n4, n3, bl2);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.o(n2);
    }

    public dx[] o(int n2) {
        return new dx[n2];
    }
}

