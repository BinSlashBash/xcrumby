/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 */
package com.google.android.gms.plus.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;
import com.google.android.gms.internal.fo;
import com.google.android.gms.plus.internal.f;

public class PlusCommonExtras
implements SafeParcelable {
    public static final f CREATOR;
    public static String TAG;
    private String Uh;
    private String Ui;
    private final int xH;

    static {
        TAG = "PlusCommonExtras";
        CREATOR = new f();
    }

    public PlusCommonExtras() {
        this.xH = 1;
        this.Uh = "";
        this.Ui = "";
    }

    PlusCommonExtras(int n2, String string2, String string3) {
        this.xH = n2;
        this.Uh = string2;
        this.Ui = string3;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (!(object instanceof PlusCommonExtras)) {
            return false;
        }
        object = (PlusCommonExtras)object;
        if (this.xH != object.xH) return false;
        if (!fo.equal(this.Uh, object.Uh)) return false;
        if (!fo.equal(this.Ui, object.Ui)) return false;
        return true;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(this.xH, this.Uh, this.Ui);
    }

    public String iN() {
        return this.Uh;
    }

    public String iO() {
        return this.Ui;
    }

    public void m(Bundle bundle) {
        bundle.putByteArray("android.gms.plus.internal.PlusCommonExtras.extraPlusCommon", c.a(this));
    }

    public String toString() {
        return fo.e(this).a("versionCode", this.xH).a("Gpsrc", this.Uh).a("ClientCallingPackage", this.Ui).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        f.a(this, parcel, n2);
    }
}

