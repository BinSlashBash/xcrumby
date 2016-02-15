/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.internal.OnDownloadProgressResponse;

public class aa
implements Parcelable.Creator<OnDownloadProgressResponse> {
    static void a(OnDownloadProgressResponse onDownloadProgressResponse, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, onDownloadProgressResponse.xH);
        b.a(parcel, 2, onDownloadProgressResponse.FF);
        b.a(parcel, 3, onDownloadProgressResponse.FG);
        b.F(parcel, n2);
    }

    public OnDownloadProgressResponse O(Parcel parcel) {
        long l2 = 0;
        int n2 = a.o(parcel);
        int n3 = 0;
        long l3 = 0;
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
                    l3 = a.i(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            l2 = a.i(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new OnDownloadProgressResponse(n3, l3, l2);
    }

    public OnDownloadProgressResponse[] as(int n2) {
        return new OnDownloadProgressResponse[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.O(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.as(n2);
    }
}

