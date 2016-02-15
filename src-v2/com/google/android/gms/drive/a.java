/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;

public class a
implements Parcelable.Creator<Contents> {
    static void a(Contents contents, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, contents.xH);
        b.a(parcel, 2, (Parcelable)contents.Cj, n2, false);
        b.c(parcel, 3, contents.Eu);
        b.c(parcel, 4, contents.Ev);
        b.a(parcel, 5, contents.Ew, n2, false);
        b.F(parcel, n3);
    }

    public Contents[] ac(int n2) {
        return new Contents[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.y(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.ac(n2);
    }

    public Contents y(Parcel parcel) {
        DriveId driveId = null;
        int n2 = 0;
        int n3 = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        int n4 = 0;
        ParcelFileDescriptor parcelFileDescriptor = null;
        int n5 = 0;
        block7 : while (parcel.dataPosition() < n3) {
            int n6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.R(n6)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, n6);
                    continue block7;
                }
                case 1: {
                    n5 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n6);
                    continue block7;
                }
                case 2: {
                    parcelFileDescriptor = (ParcelFileDescriptor)com.google.android.gms.common.internal.safeparcel.a.a(parcel, n6, ParcelFileDescriptor.CREATOR);
                    continue block7;
                }
                case 3: {
                    n4 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n6);
                    continue block7;
                }
                case 4: {
                    n2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, n6);
                    continue block7;
                }
                case 5: 
            }
            driveId = com.google.android.gms.common.internal.safeparcel.a.a(parcel, n6, DriveId.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new Contents(n5, parcelFileDescriptor, n4, n2, driveId);
    }
}

