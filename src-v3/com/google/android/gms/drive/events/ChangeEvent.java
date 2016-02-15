package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;

public final class ChangeEvent implements SafeParcelable, ResourceEvent {
    public static final Creator<ChangeEvent> CREATOR;
    final int ER;
    final DriveId Ew;
    final int xH;

    static {
        CREATOR = new C0268a();
    }

    ChangeEvent(int versionCode, DriveId driveId, int changeFlags) {
        this.xH = versionCode;
        this.Ew = driveId;
        this.ER = changeFlags;
    }

    public int describeContents() {
        return 0;
    }

    public DriveId getDriveId() {
        return this.Ew;
    }

    public int getType() {
        return 1;
    }

    public boolean hasContentChanged() {
        return (this.ER & 2) != 0;
    }

    public boolean hasMetadataChanged() {
        return (this.ER & 1) != 0;
    }

    public String toString() {
        return String.format("ChangeEvent [id=%s,changeFlags=%x]", new Object[]{this.Ew, Integer.valueOf(this.ER)});
    }

    public void writeToParcel(Parcel dest, int flags) {
        C0268a.m243a(this, dest, flags);
    }
}
