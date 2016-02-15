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
import com.google.android.gms.drive.internal.ai;

public class OpenFileIntentSenderRequest
implements SafeParcelable {
    public static final Parcelable.Creator<OpenFileIntentSenderRequest> CREATOR = new ai();
    final String EB;
    final DriveId EC;
    final String[] EQ;
    final int xH;

    OpenFileIntentSenderRequest(int n2, String string2, String[] arrstring, DriveId driveId) {
        this.xH = n2;
        this.EB = string2;
        this.EQ = arrstring;
        this.EC = driveId;
    }

    public OpenFileIntentSenderRequest(String string2, String[] arrstring, DriveId driveId) {
        this(1, string2, arrstring, driveId);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ai.a(this, parcel, n2);
    }
}

