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
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.internal.e;

public class CloseContentsRequest
implements SafeParcelable {
    public static final Parcelable.Creator<CloseContentsRequest> CREATOR = new e();
    final Contents EX;
    final Boolean EY;
    final int xH;

    CloseContentsRequest(int n2, Contents contents, Boolean bl2) {
        this.xH = n2;
        this.EX = contents;
        this.EY = bl2;
    }

    public CloseContentsRequest(Contents contents, boolean bl2) {
        this(1, contents, bl2);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        e.a(this, parcel, n2);
    }
}

