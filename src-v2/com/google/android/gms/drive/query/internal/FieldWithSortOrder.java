/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.internal.c;

public class FieldWithSortOrder
implements SafeParcelable {
    public static final c CREATOR = new c();
    final String FM;
    final boolean GJ;
    final int xH;

    FieldWithSortOrder(int n2, String string2, boolean bl2) {
        this.xH = n2;
        this.FM = string2;
        this.GJ = bl2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        c.a(this, parcel, n2);
    }
}

