/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.kl;
import com.google.android.gms.wearable.f;

public class kk
implements SafeParcelable,
f {
    public static final Parcelable.Creator<kk> CREATOR = new kl();
    private final String HA;
    private final String wp;
    final int xH;

    kk(int n2, String string2, String string3) {
        this.xH = n2;
        this.wp = string2;
        this.HA = string3;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (!(object instanceof kk)) {
            return false;
        }
        object = (kk)object;
        if (!object.wp.equals(this.wp)) return false;
        if (!object.HA.equals(this.HA)) return false;
        return true;
    }

    public String getDisplayName() {
        return this.HA;
    }

    public String getId() {
        return this.wp;
    }

    public int hashCode() {
        return (this.wp.hashCode() + 629) * 37 + this.HA.hashCode();
    }

    public String toString() {
        return "NodeParcelable{" + this.wp + "," + this.HA + "}";
    }

    public void writeToParcel(Parcel parcel, int n2) {
        kl.a(this, parcel, n2);
    }
}

