/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.ChangeEvent;

public class a
implements Parcelable.Creator<ChangeEvent> {
    static void a(ChangeEvent changeEvent, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, changeEvent.xH);
        b.a(parcel, 2, changeEvent.Ew, n2, false);
        b.c(parcel, 3, changeEvent.ER);
        b.F(parcel, n3);
    }

    public ChangeEvent A(Parcel parcel) {
        int n2 = 0;
        int n3 = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        DriveId driveId = null;
        int n4 = 0;
        block5 : while (parcel.dataPosition() < n3) {
            int n5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(n5)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, n5);
                    continue block5;
                }
                case 1: {
                    n4 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n5);
                    continue block5;
                }
                case 2: {
                    driveId = com.google.android.gms.common.internal.safeparcel.a.a(parcel, n5, DriveId.CREATOR);
                    continue block5;
                }
                case 3: 
            }
            n2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new ChangeEvent(n4, driveId, n2);
    }

    public ChangeEvent[] ae(int n2) {
        return new ChangeEvent[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.A(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.ae(n2);
    }
}

