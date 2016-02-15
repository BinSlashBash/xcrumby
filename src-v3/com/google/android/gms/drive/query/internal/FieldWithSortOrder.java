package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class FieldWithSortOrder implements SafeParcelable {
    public static final C0294c CREATOR;
    final String FM;
    final boolean GJ;
    final int xH;

    static {
        CREATOR = new C0294c();
    }

    FieldWithSortOrder(int versionCode, String fieldName, boolean isSortAscending) {
        this.xH = versionCode;
        this.FM = fieldName;
        this.GJ = isSortAscending;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        C0294c.m337a(this, out, flags);
    }
}
