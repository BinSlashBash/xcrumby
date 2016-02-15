/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wallet.h;

public final class InstrumentInfo
implements SafeParcelable {
    public static final Parcelable.Creator<InstrumentInfo> CREATOR = new h();
    private String abt;
    private String abu;
    private final int xH;

    InstrumentInfo(int n2, String string2, String string3) {
        this.xH = n2;
        this.abt = string2;
        this.abu = string3;
    }

    public int describeContents() {
        return 0;
    }

    public String getInstrumentDetails() {
        return this.abu;
    }

    public String getInstrumentType() {
        return this.abt;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        h.a(this, parcel, n2);
    }
}

