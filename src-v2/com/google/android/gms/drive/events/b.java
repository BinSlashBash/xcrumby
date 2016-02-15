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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.ConflictEvent;

public class b
implements Parcelable.Creator<ConflictEvent> {
    static void a(ConflictEvent conflictEvent, Parcel parcel, int n2) {
        int n3 = com.google.android.gms.common.internal.safeparcel.b.p(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, conflictEvent.xH);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, conflictEvent.Ew, n2, false);
        com.google.android.gms.common.internal.safeparcel.b.F(parcel, n3);
    }

    public ConflictEvent B(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        DriveId driveId = null;
        block4 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block4;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block4;
                }
                case 2: 
            }
            driveId = a.a(parcel, n4, DriveId.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new ConflictEvent(n3, driveId);
    }

    public ConflictEvent[] af(int n2) {
        return new ConflictEvent[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.B(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.af(n2);
    }
}

