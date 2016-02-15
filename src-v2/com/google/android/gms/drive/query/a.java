/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SortOrder;
import com.google.android.gms.drive.query.internal.LogicalFilter;

public class a
implements Parcelable.Creator<Query> {
    static void a(Query query, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1000, query.xH);
        b.a(parcel, 1, query.GA, n2, false);
        b.a(parcel, 3, query.GB, false);
        b.a(parcel, 4, query.GC, n2, false);
        b.F(parcel, n3);
    }

    public Query[] aG(int n2) {
        return new Query[n2];
    }

    /*
     * Enabled aggressive block sorting
     */
    public Query ac(Parcel parcel) {
        SortOrder sortOrder = null;
        int n2 = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int n3 = 0;
        Object object = null;
        Object object2 = null;
        while (parcel.dataPosition() < n2) {
            Object object3;
            int n4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(n4)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, n4);
                    object3 = object;
                    object = object2;
                    object2 = object3;
                    break;
                }
                case 1000: {
                    n3 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n4);
                    object3 = object2;
                    object2 = object;
                    object = object3;
                    break;
                }
                case 1: {
                    object3 = com.google.android.gms.common.internal.safeparcel.a.a(parcel, n4, LogicalFilter.CREATOR);
                    object2 = object;
                    object = object3;
                    break;
                }
                case 3: {
                    object3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, n4);
                    object = object2;
                    object2 = object3;
                    break;
                }
                case 4: {
                    sortOrder = com.google.android.gms.common.internal.safeparcel.a.a(parcel, n4, SortOrder.CREATOR);
                    object3 = object2;
                    object2 = object;
                    object = object3;
                }
            }
            object3 = object;
            object = object2;
            object2 = object3;
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new Query(n3, (LogicalFilter)object2, (String)object, sortOrder);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.ac(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aG(n2);
    }
}

