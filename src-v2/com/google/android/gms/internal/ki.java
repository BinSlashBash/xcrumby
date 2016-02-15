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
import com.google.android.gms.internal.kj;
import com.google.android.gms.wearable.e;

public class ki
implements SafeParcelable,
e {
    public static final Parcelable.Creator<ki> CREATOR = new kj();
    private final int Eu;
    private final byte[] Nf;
    private final String adF;
    private final String adG;
    final int xH;

    ki(int n2, int n3, String string2, byte[] arrby, String string3) {
        this.xH = n2;
        this.Eu = n3;
        this.adF = string2;
        this.Nf = arrby;
        this.adG = string3;
    }

    public int describeContents() {
        return 0;
    }

    public int fA() {
        return this.Eu;
    }

    public byte[] getData() {
        return this.Nf;
    }

    public String getPath() {
        return this.adF;
    }

    public String getSource() {
        return this.adG;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder().append("MessageEventParcelable[").append(this.Eu).append(",").append(this.adF);
        if (this.Nf == null) {
            object = "null";
            do {
                return stringBuilder.append(object).append("]").toString();
                break;
            } while (true);
        }
        object = this.Nf.length;
        return stringBuilder.append(object).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        kj.a(this, parcel, n2);
    }
}

