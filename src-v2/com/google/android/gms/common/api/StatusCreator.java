/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class StatusCreator
implements Parcelable.Creator<Status> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(Status status, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, status.getStatusCode());
        b.c(parcel, 1000, status.getVersionCode());
        b.a(parcel, 2, status.ep(), false);
        b.a(parcel, 3, (Parcelable)status.eo(), n2, false);
        b.F(parcel, n3);
    }

    public Status createFromParcel(Parcel parcel) {
        PendingIntent pendingIntent = null;
        int n2 = 0;
        int n3 = a.o(parcel);
        String string2 = null;
        int n4 = 0;
        block6 : while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block6;
                }
                case 1: {
                    n2 = a.g(parcel, n5);
                    continue block6;
                }
                case 1000: {
                    n4 = a.g(parcel, n5);
                    continue block6;
                }
                case 2: {
                    string2 = a.n(parcel, n5);
                    continue block6;
                }
                case 3: 
            }
            pendingIntent = (PendingIntent)a.a(parcel, n5, PendingIntent.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new Status(n4, n2, string2, pendingIntent);
    }

    public Status[] newArray(int n2) {
        return new Status[n2];
    }
}

