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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.internal.a;

public class AddEventListenerRequest
implements SafeParcelable {
    public static final Parcelable.Creator<AddEventListenerRequest> CREATOR = new a();
    final int ES;
    final PendingIntent ET;
    final DriveId Ew;
    final int xH;

    AddEventListenerRequest(int n2, DriveId driveId, int n3, PendingIntent pendingIntent) {
        this.xH = n2;
        this.Ew = driveId;
        this.ES = n3;
        this.ET = pendingIntent;
    }

    public AddEventListenerRequest(DriveId driveId, int n2, PendingIntent pendingIntent) {
        this(1, driveId, n2, pendingIntent);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        a.a(this, parcel, n2);
    }
}

