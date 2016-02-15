/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.query.internal.j;

public class Operator
implements SafeParcelable {
    public static final Parcelable.Creator<Operator> CREATOR = new j();
    public static final Operator GU = new Operator("=");
    public static final Operator GV = new Operator("<");
    public static final Operator GW = new Operator("<=");
    public static final Operator GX = new Operator(">");
    public static final Operator GY = new Operator(">=");
    public static final Operator GZ = new Operator("and");
    public static final Operator Ha = new Operator("or");
    public static final Operator Hb = new Operator("not");
    public static final Operator Hc = new Operator("contains");
    final String mTag;
    final int xH;

    Operator(int n2, String string2) {
        this.xH = n2;
        this.mTag = string2;
    }

    private Operator(String string2) {
        this(1, string2);
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (Operator)object;
        if (this.mTag == null) {
            if (object.mTag == null) return true;
            return false;
        }
        if (!this.mTag.equals(object.mTag)) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n2;
        if (this.mTag == null) {
            n2 = 0;
            do {
                return n2 + 31;
                break;
            } while (true);
        }
        n2 = this.mTag.hashCode();
        return n2 + 31;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        j.a(this, parcel, n2);
    }
}

