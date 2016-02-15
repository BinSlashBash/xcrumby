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
import com.google.android.gms.drive.internal.aa;

public class OnDownloadProgressResponse
implements SafeParcelable {
    public static final Parcelable.Creator<OnDownloadProgressResponse> CREATOR = new aa();
    final long FF;
    final long FG;
    final int xH;

    OnDownloadProgressResponse(int n2, long l2, long l3) {
        this.xH = n2;
        this.FF = l2;
        this.FG = l3;
    }

    public int describeContents() {
        return 0;
    }

    public long fJ() {
        return this.FF;
    }

    public long fK() {
        return this.FG;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        aa.a(this, parcel, n2);
    }
}

