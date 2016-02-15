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
import com.google.android.gms.internal.ak;

public class al
implements Parcelable.Creator<ak> {
    static void a(ak ak2, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, ak2.versionCode);
        b.a(parcel, 2, ak2.lS, false);
        b.c(parcel, 3, ak2.height);
        b.c(parcel, 4, ak2.heightPixels);
        b.a(parcel, 5, ak2.lT);
        b.c(parcel, 6, ak2.width);
        b.c(parcel, 7, ak2.widthPixels);
        b.a((Parcel)parcel, (int)8, (Parcelable[])ak2.lU, (int)n2, (boolean)false);
        b.F(parcel, n3);
    }

    public ak b(Parcel parcel) {
        ak[] arrak = null;
        int n2 = 0;
        int n3 = a.o(parcel);
        int n4 = 0;
        boolean bl2 = false;
        int n5 = 0;
        int n6 = 0;
        String string2 = null;
        int n7 = 0;
        block10 : while (parcel.dataPosition() < n3) {
            int n8 = a.n(parcel);
            switch (a.R(n8)) {
                default: {
                    a.b(parcel, n8);
                    continue block10;
                }
                case 1: {
                    n7 = a.g(parcel, n8);
                    continue block10;
                }
                case 2: {
                    string2 = a.n(parcel, n8);
                    continue block10;
                }
                case 3: {
                    n6 = a.g(parcel, n8);
                    continue block10;
                }
                case 4: {
                    n5 = a.g(parcel, n8);
                    continue block10;
                }
                case 5: {
                    bl2 = a.c(parcel, n8);
                    continue block10;
                }
                case 6: {
                    n4 = a.g(parcel, n8);
                    continue block10;
                }
                case 7: {
                    n2 = a.g(parcel, n8);
                    continue block10;
                }
                case 8: 
            }
            arrak = (ak[])a.b(parcel, n8, ak.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new ak(n7, string2, n6, n5, bl2, n4, n2, arrak);
    }

    public ak[] c(int n2) {
        return new ak[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.b(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.c(n2);
    }
}

