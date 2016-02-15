/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.DriveId;

public class d
implements Parcelable.Creator<DriveId> {
    static void a(DriveId driveId, Parcel parcel, int n2) {
        n2 = b.p(parcel);
        b.c(parcel, 1, driveId.xH);
        b.a(parcel, 2, driveId.EH, false);
        b.a(parcel, 3, driveId.EI);
        b.a(parcel, 4, driveId.EJ);
        b.F(parcel, n2);
    }

    public DriveId[] ad(int n2) {
        return new DriveId[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.z(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.ad(n2);
    }

    public DriveId z(Parcel parcel) {
        long l2 = 0;
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        long l3 = 0;
        block6 : while (parcel.dataPosition() < n2) {
            int n4 = a.n(parcel);
            switch (a.R(n4)) {
                default: {
                    a.b(parcel, n4);
                    continue block6;
                }
                case 1: {
                    n3 = a.g(parcel, n4);
                    continue block6;
                }
                case 2: {
                    string2 = a.n(parcel, n4);
                    continue block6;
                }
                case 3: {
                    l3 = a.i(parcel, n4);
                    continue block6;
                }
                case 4: 
            }
            l2 = a.i(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new DriveId(n3, string2, l3, l2);
    }
}

