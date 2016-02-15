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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.internal.b;

public class AuthorizeAccessRequest
implements SafeParcelable {
    public static final Parcelable.Creator<AuthorizeAccessRequest> CREATOR = new b();
    final long EU;
    final DriveId Ew;
    final int xH;

    AuthorizeAccessRequest(int n2, long l2, DriveId driveId) {
        this.xH = n2;
        this.EU = l2;
        this.Ew = driveId;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        b.a(this, parcel, n2);
    }
}

