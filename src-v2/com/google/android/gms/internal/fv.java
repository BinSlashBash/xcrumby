/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fw;
import com.google.android.gms.internal.fx;
import com.google.android.gms.internal.ga;

public class fv
implements SafeParcelable {
    public static final fw CREATOR = new fw();
    private final fx DS;
    private final int xH;

    fv(int n2, fx fx2) {
        this.xH = n2;
        this.DS = fx2;
    }

    private fv(fx fx2) {
        this.xH = 1;
        this.DS = fx2;
    }

    public static fv a(ga.b<?, ?> b2) {
        if (b2 instanceof fx) {
            return new fv((fx)b2);
        }
        throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
    }

    public int describeContents() {
        fw fw2 = CREATOR;
        return 0;
    }

    fx eT() {
        return this.DS;
    }

    public ga.b<?, ?> eU() {
        if (this.DS != null) {
            return this.DS;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }

    int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        fw fw2 = CREATOR;
        fw.a(this, parcel, n2);
    }
}

