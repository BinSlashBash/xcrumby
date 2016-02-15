/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.internal;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.internal.AddEventListenerRequest;

public class a
implements Parcelable.Creator<AddEventListenerRequest> {
    static void a(AddEventListenerRequest addEventListenerRequest, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, addEventListenerRequest.xH);
        b.a(parcel, 2, addEventListenerRequest.Ew, n2, false);
        b.c(parcel, 3, addEventListenerRequest.ES);
        b.a(parcel, 4, (Parcelable)addEventListenerRequest.ET, n2, false);
        b.F(parcel, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public AddEventListenerRequest C(Parcel parcel) {
        PendingIntent pendingIntent = null;
        int n2 = 0;
        int n3 = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        DriveId driveId = null;
        int n4 = 0;
        while (parcel.dataPosition() < n3) {
            int n5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(n5)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, n5);
                    n5 = n2;
                    n2 = n4;
                    n4 = n5;
                    break;
                }
                case 1: {
                    n5 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n5);
                    n4 = n2;
                    n2 = n5;
                    break;
                }
                case 2: {
                    driveId = com.google.android.gms.common.internal.safeparcel.a.a(parcel, n5, DriveId.CREATOR);
                    n5 = n4;
                    n4 = n2;
                    n2 = n5;
                    break;
                }
                case 3: {
                    n5 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n5);
                    n2 = n4;
                    n4 = n5;
                    break;
                }
                case 4: {
                    pendingIntent = (PendingIntent)com.google.android.gms.common.internal.safeparcel.a.a(parcel, n5, PendingIntent.CREATOR);
                    n5 = n4;
                    n4 = n2;
                    n2 = n5;
                }
            }
            n5 = n2;
            n2 = n4;
            n4 = n5;
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new AddEventListenerRequest(n4, driveId, n2, pendingIntent);
    }

    public AddEventListenerRequest[] ag(int n2) {
        return new AddEventListenerRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.C(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.ag(n2);
    }
}

