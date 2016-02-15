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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.b;

public final class ConflictEvent
implements SafeParcelable,
DriveEvent {
    public static final Parcelable.Creator<ConflictEvent> CREATOR = new b();
    final DriveId Ew;
    final int xH;

    ConflictEvent(int n2, DriveId driveId) {
        this.xH = n2;
        this.Ew = driveId;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public DriveId getDriveId() {
        return this.Ew;
    }

    @Override
    public int getType() {
        return 1;
    }

    public String toString() {
        return String.format("ConflictEvent [id=%s]", this.Ew);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        b.a(this, parcel, n2);
    }
}

