package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ConflictEvent;

public class OnEventResponse implements SafeParcelable {
    public static final Creator<OnEventResponse> CREATOR;
    final int ES;
    final ChangeEvent FH;
    final ConflictEvent FI;
    final int xH;

    static {
        CREATOR = new ac();
    }

    OnEventResponse(int versionCode, int eventType, ChangeEvent changeEvent, ConflictEvent conflictEvent) {
        this.xH = versionCode;
        this.ES = eventType;
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

    public void writeToParcel(Parcel dest, int flags) {
        ac.m254a(this, dest, flags);
    }
}
