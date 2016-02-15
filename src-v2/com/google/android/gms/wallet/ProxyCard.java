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
import com.google.android.gms.wallet.o;

public final class ProxyCard
implements SafeParcelable {
    public static final Parcelable.Creator<ProxyCard> CREATOR = new o();
    String ack;
    String acl;
    int acm;
    int acn;
    private final int xH;

    ProxyCard(int n2, String string2, String string3, int n3, int n4) {
        this.xH = n2;
        this.ack = string2;
        this.acl = string3;
        this.acm = n3;
        this.acn = n4;
    }

    public int describeContents() {
        return 0;
    }

    public String getCvn() {
        return this.acl;
    }

    public int getExpirationMonth() {
        return this.acm;
    }

    public int getExpirationYear() {
        return this.acn;
    }

    public String getPan() {
        return this.ack;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        o.a(this, parcel, n2);
    }
}

