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
import com.google.android.gms.internal.js;

public class jt
implements Parcelable.Creator<js> {
    static void a(js js2, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, js2.getVersionCode());
        b.a(parcel, 2, js2.adn, false);
        b.a(parcel, 3, js2.pm, false);
        b.F(parcel, n2);
    }

    public js bu(Parcel parcel) {
        String string2 = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        String string3 = null;
        block5 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block5;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block5;
                }
                case 2: {
                    string3 = a.n(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            string2 = a.n(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new js(n3, string3, string2);
    }

    public js[] cI(int n2) {
        return new js[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bu(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cI(n2);
    }
}

