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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.internal.OpenFileIntentSenderRequest;

public class ai
implements Parcelable.Creator<OpenFileIntentSenderRequest> {
    static void a(OpenFileIntentSenderRequest openFileIntentSenderRequest, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, openFileIntentSenderRequest.xH);
        b.a(parcel, 2, openFileIntentSenderRequest.EB, false);
        b.a(parcel, 3, openFileIntentSenderRequest.EQ, false);
        b.a(parcel, 4, openFileIntentSenderRequest.EC, n2, false);
        b.F(parcel, n3);
    }

    public OpenFileIntentSenderRequest W(Parcel parcel) {
        DriveId driveId = null;
        int n2 = a.o(parcel);
        int n3 = 0;
        String[] arrstring = null;
        String string2 = null;
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
                    arrstring = a.z(parcel, n4);
                    continue block6;
                }
                case 4: 
            }
            driveId = a.a(parcel, n4, DriveId.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new OpenFileIntentSenderRequest(n3, string2, arrstring, driveId);
    }

    public OpenFileIntentSenderRequest[] aA(int n2) {
        return new OpenFileIntentSenderRequest[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.W(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.aA(n2);
    }
}

