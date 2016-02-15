/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.identity.intents.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.identity.intents.model.UserAddress;

public class b
implements Parcelable.Creator<UserAddress> {
    static void a(UserAddress userAddress, Parcel parcel, int n2) {
        n2 = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, userAddress.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, userAddress.name, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, userAddress.NB, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, userAddress.NC, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, userAddress.ND, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, userAddress.NE, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 7, userAddress.NF, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 8, userAddress.NG, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 9, userAddress.NH, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 10, userAddress.qd, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 11, userAddress.NI, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 12, userAddress.NJ, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 13, userAddress.NK, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 14, userAddress.NL);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 15, userAddress.NM, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 16, userAddress.NN, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, n2);
    }

    public UserAddress aA(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        String string8 = null;
        String string9 = null;
        String string10 = null;
        String string11 = null;
        String string12 = null;
        String string13 = null;
        boolean bl2 = false;
        String string14 = null;
        String string15 = null;
        block18 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block18;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block18;
                }
                case 2: {
                    string2 = a.n(parcel, n4);
                    continue block18;
                }
                case 3: {
                    string3 = a.n(parcel, n4);
                    continue block18;
                }
                case 4: {
                    string4 = a.n(parcel, n4);
                    continue block18;
                }
                case 5: {
                    string5 = a.n(parcel, n4);
                    continue block18;
                }
                case 6: {
                    string6 = a.n(parcel, n4);
                    continue block18;
                }
                case 7: {
                    string7 = a.n(parcel, n4);
                    continue block18;
                }
                case 8: {
                    string8 = a.n(parcel, n4);
                    continue block18;
                }
                case 9: {
                    string9 = a.n(parcel, n4);
                    continue block18;
                }
                case 10: {
                    string10 = a.n(parcel, n4);
                    continue block18;
                }
                case 11: {
                    string11 = a.n(parcel, n4);
                    continue block18;
                }
                case 12: {
                    string12 = a.n(parcel, n4);
                    continue block18;
                }
                case 13: {
                    string13 = a.n(parcel, n4);
                    continue block18;
                }
                case 14: {
                    bl2 = a.c(parcel, n4);
                    continue block18;
                }
                case 15: {
                    string14 = a.n(parcel, n4);
                    continue block18;
                }
                case 16: 
            }
            string15 = a.n(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new UserAddress(n3, string2, string3, string4, string5, string6, string7, string8, string9, string10, string11, string12, string13, bl2, string14, string15);
    }

    public UserAddress[] bu(int n2) {
        return new UserAddress[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.aA(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.bu(n2);
    }
}

