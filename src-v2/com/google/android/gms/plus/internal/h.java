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
import com.google.android.gms.internal.fo;
import com.google.android.gms.plus.internal.PlusCommonExtras;
import com.google.android.gms.plus.internal.j;
import java.util.Arrays;

public class h
implements SafeParcelable {
    public static final j CREATOR = new j();
    private final String[] Uk;
    private final String[] Ul;
    private final String[] Um;
    private final String Un;
    private final String Uo;
    private final String Up;
    private final String Uq;
    private final PlusCommonExtras Ur;
    private final String wG;
    private final int xH;

    h(int n2, String string2, String[] arrstring, String[] arrstring2, String[] arrstring3, String string3, String string4, String string5, String string6, PlusCommonExtras plusCommonExtras) {
        this.xH = n2;
        this.wG = string2;
        this.Uk = arrstring;
        this.Ul = arrstring2;
        this.Um = arrstring3;
        this.Un = string3;
        this.Uo = string4;
        this.Up = string5;
        this.Uq = string6;
        this.Ur = plusCommonExtras;
    }

    public h(String string2, String[] arrstring, String[] arrstring2, String[] arrstring3, String string3, String string4, String string5, PlusCommonExtras plusCommonExtras) {
        this.xH = 1;
        this.wG = string2;
        this.Uk = arrstring;
        this.Ul = arrstring2;
        this.Um = arrstring3;
        this.Un = string3;
        this.Uo = string4;
        this.Up = string5;
        this.Uq = null;
        this.Ur = plusCommonExtras;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (!(object instanceof h)) {
            return false;
        }
        object = (h)object;
        if (this.xH != object.xH) return false;
        if (!fo.equal(this.wG, object.wG)) return false;
        if (!Arrays.equals(this.Uk, object.Uk)) return false;
        if (!Arrays.equals(this.Ul, object.Ul)) return false;
        if (!Arrays.equals(this.Um, object.Um)) return false;
        if (!fo.equal(this.Un, object.Un)) return false;
        if (!fo.equal(this.Uo, object.Uo)) return false;
        if (!fo.equal(this.Up, object.Up)) return false;
        if (!fo.equal(this.Uq, object.Uq)) return false;
        if (!fo.equal(this.Ur, object.Ur)) return false;
        return true;
    }

    public String getAccountName() {
        return this.wG;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(this.xH, this.wG, this.Uk, this.Ul, this.Um, this.Un, this.Uo, this.Up, this.Uq, this.Ur);
    }

    public String[] iP() {
        return this.Uk;
    }

    public String[] iQ() {
        return this.Ul;
    }

    public String[] iR() {
        return this.Um;
    }

    public String iS() {
        return this.Un;
    }

    public String iT() {
        return this.Uo;
    }

    public String iU() {
        return this.Up;
    }

    public String iV() {
        return this.Uq;
    }

    public PlusCommonExtras iW() {
        return this.Ur;
    }

    public Bundle iX() {
        Bundle bundle = new Bundle();
        bundle.setClassLoader(PlusCommonExtras.class.getClassLoader());
        this.Ur.m(bundle);
        return bundle;
    }

    public String toString() {
        return fo.e(this).a("versionCode", this.xH).a("accountName", this.wG).a("requestedScopes", this.Uk).a("visibleActivities", this.Ul).a("requiredFeatures", this.Um).a("packageNameForAuth", this.Un).a("callingPackageName", this.Uo).a("applicationName", this.Up).a("extra", this.Ur.toString()).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        j.a(this, parcel, n2);
    }
}

