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
import com.google.android.gms.internal.av;

public class aw
implements Parcelable.Creator<av> {
    static void a(av av2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, av2.versionCode);
        b.c(parcel, 2, av2.mq);
        b.c(parcel, 3, av2.backgroundColor);
        b.c(parcel, 4, av2.mr);
        b.c(parcel, 5, av2.ms);
        b.c(parcel, 6, av2.mt);
        b.c(parcel, 7, av2.mu);
        b.c(parcel, 8, av2.mv);
        b.c(parcel, 9, av2.mw);
        b.a(parcel, 10, av2.mx, false);
        b.c(parcel, 11, av2.my);
        b.a(parcel, 12, av2.mz, false);
        b.c(parcel, 13, av2.mA);
        b.c(parcel, 14, av2.mB);
        b.a(parcel, 15, av2.mC, false);
        b.F(parcel, n2);
    }

    public av c(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        String string2 = null;
        int n12 = 0;
        String string3 = null;
        int n13 = 0;
        int n14 = 0;
        String string4 = null;
        block17 : while (parcel.dataPosition() < n2) {
            int n15 = a.n(parcel);
            switch (a.R(n15)) {
                default: {
                    a.b(parcel, n15);
                    continue block17;
                }
                case 1: {
                    n3 = a.g(parcel, n15);
                    continue block17;
                }
                case 2: {
                    n4 = a.g(parcel, n15);
                    continue block17;
                }
                case 3: {
                    n5 = a.g(parcel, n15);
                    continue block17;
                }
                case 4: {
                    n6 = a.g(parcel, n15);
                    continue block17;
                }
                case 5: {
                    n7 = a.g(parcel, n15);
                    continue block17;
                }
                case 6: {
                    n8 = a.g(parcel, n15);
                    continue block17;
                }
                case 7: {
                    n9 = a.g(parcel, n15);
                    continue block17;
                }
                case 8: {
                    n10 = a.g(parcel, n15);
                    continue block17;
                }
                case 9: {
                    n11 = a.g(parcel, n15);
                    continue block17;
                }
                case 10: {
                    string2 = a.n(parcel, n15);
                    continue block17;
                }
                case 11: {
                    n12 = a.g(parcel, n15);
                    continue block17;
                }
                case 12: {
                    string3 = a.n(parcel, n15);
                    continue block17;
                }
                case 13: {
                    n13 = a.g(parcel, n15);
                    continue block17;
                }
                case 14: {
                    n14 = a.g(parcel, n15);
                    continue block17;
                }
                case 15: 
            }
            string4 = a.n(parcel, n15);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new av(n3, n4, n5, n6, n7, n8, n9, n10, n11, string2, n12, string3, n13, n14, string4);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.c(parcel);
    }

    public av[] e(int n2) {
        return new av[n2];
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.e(n2);
    }
}

