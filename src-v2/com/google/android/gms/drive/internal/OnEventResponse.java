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
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ConflictEvent;
import com.google.android.gms.drive.internal.ac;

public class OnEventResponse
implements SafeParcelable {
    public static final Parcelable.Creator<OnEventResponse> CREATOR = new ac();
    final int ES;
    final ChangeEvent FH;
    final ConflictEvent FI;
    final int xH;

    OnEventResponse(int n2, int n3, ChangeEvent changeEvent, ConflictEvent conflictEvent) {
        this.xH = n2;
        this.ES = n3;
        this.FH = changeEvent;
        this.FI = conflictEvent;
    }

    public int describeContents() {
        return 0;
    }

    public ChangeEvent fL() {
        return this.FH;
    }

    public ConflictEvent fM() {
        return this.FI;
    }

    public int getEventType() {
        return this.ES;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ac.a(this, parcel, n2);
    }
}

