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
import com.google.android.gms.wallet.LineItem;

public class i
implements Parcelable.Creator<LineItem> {
    static void a(LineItem lineItem, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, lineItem.getVersionCode());
        b.a(parcel, 2, lineItem.description, false);
        b.a(parcel, 3, lineItem.abv, false);
        b.a(parcel, 4, lineItem.abw, false);
        b.a(parcel, 5, lineItem.abc, false);
        b.c(parcel, 6, lineItem.abx);
        b.a(parcel, 7, lineItem.abd, false);
        b.F(parcel, n2);
    }

    public LineItem be(Parcel parcel) {
        int n2 = 0;
        String string2 = null;
        int n3 = a.o(parcel);
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        int n4 = 0;
        block9 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block9;
                }
                case 1: {
                    n4 = a.g(parcel, n5);
                    continue block9;
                }
                case 2: {
                    string6 = a.n(parcel, n5);
                    continue block9;
                }
                case 3: {
                    string5 = a.n(parcel, n5);
                    continue block9;
                }
                case 4: {
                    string4 = a.n(parcel, n5);
                    continue block9;
                }
                case 5: {
                    string3 = a.n(parcel, n5);
                    continue block9;
                }
                case 6: {
                    n2 = a.g(parcel, n5);
                    continue block9;
                }
                case 7: 
            }
            string2 = a.n(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new LineItem(n4, string6, string5, string4, string3, n2, string2);
    }

    public LineItem[] cq(int n2) {
        return new LineItem[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.be(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cq(n2);
    }
}

