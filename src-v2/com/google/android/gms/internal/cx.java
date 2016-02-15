/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.os.Bundle
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.cy;
import com.google.android.gms.internal.dx;

public final class cx
implements SafeParcelable {
    public static final cy CREATOR = new cy();
    public final ApplicationInfo applicationInfo;
    public final String kH;
    public final dx kK;
    public final ak kN;
    public final Bundle pf;
    public final ah pg;
    public final PackageInfo ph;
    public final String pi;
    public final String pj;
    public final String pk;
    public final Bundle pl;
    public final int versionCode;

    cx(int n2, Bundle bundle, ah ah2, ak ak2, String string2, ApplicationInfo applicationInfo, PackageInfo packageInfo, String string3, String string4, String string5, dx dx2, Bundle bundle2) {
        this.versionCode = n2;
        this.pf = bundle;
        this.pg = ah2;
        this.kN = ak2;
        this.kH = string2;
        this.applicationInfo = applicationInfo;
        this.ph = packageInfo;
        this.pi = string3;
        this.pj = string4;
        this.pk = string5;
        this.kK = dx2;
        this.pl = bundle2;
    }

    public cx(Bundle bundle, ah ah2, ak ak2, String string2, ApplicationInfo applicationInfo, PackageInfo packageInfo, String string3, String string4, String string5, dx dx2, Bundle bundle2) {
        this(2, bundle, ah2, ak2, string2, applicationInfo, packageInfo, string3, string4, string5, dx2, bundle2);
    }

    public cx(a a2, String string2) {
        this(a2.pf, a2.pg, a2.kN, a2.kH, a2.applicationInfo, a2.ph, string2, a2.pj, a2.pk, a2.kK, a2.pl);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        cy.a(this, parcel, n2);
    }

    public static final class a {
        public final ApplicationInfo applicationInfo;
        public final String kH;
        public final dx kK;
        public final ak kN;
        public final Bundle pf;
        public final ah pg;
        public final PackageInfo ph;
        public final String pj;
        public final String pk;
        public final Bundle pl;

        public a(Bundle bundle, ah ah2, ak ak2, String string2, ApplicationInfo applicationInfo, PackageInfo packageInfo, String string3, String string4, dx dx2, Bundle bundle2) {
            this.pf = bundle;
            this.pg = ah2;
            this.kN = ak2;
            this.kH = string2;
            this.applicationInfo = applicationInfo;
            this.ph = packageInfo;
            this.pj = string3;
            this.pk = string4;
            this.kK = dx2;
            this.pl = bundle2;
        }
    }

}

