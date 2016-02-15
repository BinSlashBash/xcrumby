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
import com.google.android.gms.drive.events.ResourceEvent;
import com.google.android.gms.drive.events.a;

public final class ChangeEvent
implements SafeParcelable,
ResourceEvent {
    public static final Parcelable.Creator<ChangeEvent> CREATOR = new a();
    final int ER;
    final DriveId Ew;
    final int xH;

    ChangeEvent(int n2, DriveId driveId, int n3) {
        this.xH = n2;
        this.Ew = driveId;
        this.ER = n3;
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

    public boolean hasContentChanged() {
        if ((this.ER & 2) != 0) {
            return true;
        }
        return false;
    }

    public boolean hasMetadataChanged() {
        if ((this.ER & 1) != 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        return String.format("ChangeEvent [id=%s,changeFlags=%x]", this.Ew, this.ER);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        a.a(this, parcel, n2);
    }
}

