package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class cx implements SafeParcelable {
    public static final cy CREATOR;
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

    /* renamed from: com.google.android.gms.internal.cx.a */
    public static final class C0354a {
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

        public C0354a(Bundle bundle, ah ahVar, ak akVar, String str, ApplicationInfo applicationInfo, PackageInfo packageInfo, String str2, String str3, dx dxVar, Bundle bundle2) {
            this.pf = bundle;
            this.pg = ahVar;
            this.kN = akVar;
            this.kH = str;
            this.applicationInfo = applicationInfo;
            this.ph = packageInfo;
            this.pj = str2;
            this.pk = str3;
            this.kK = dxVar;
            this.pl = bundle2;
        }
    }

    static {
        CREATOR = new cy();
    }

    cx(int i, Bundle bundle, ah ahVar, ak akVar, String str, ApplicationInfo applicationInfo, PackageInfo packageInfo, String str2, String str3, String str4, dx dxVar, Bundle bundle2) {
        this.versionCode = i;
        this.pf = bundle;
        this.pg = ahVar;
        this.kN = akVar;
        this.kH = str;
        this.applicationInfo = applicationInfo;
        this.ph = packageInfo;
        this.pi = str2;
        this.pj = str3;
        this.pk = str4;
        this.kK = dxVar;
        this.pl = bundle2;
    }

    public cx(Bundle bundle, ah ahVar, ak akVar, String str, ApplicationInfo applicationInfo, PackageInfo packageInfo, String str2, String str3, String str4, dx dxVar, Bundle bundle2) {
        this(2, bundle, ahVar, akVar, str, applicationInfo, packageInfo, str2, str3, str4, dxVar, bundle2);
    }

    public cx(C0354a c0354a, String str) {
        this(c0354a.pf, c0354a.pg, c0354a.kN, c0354a.kH, c0354a.applicationInfo, c0354a.ph, str, c0354a.pj, c0354a.pk, c0354a.kK, c0354a.pl);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        cy.m717a(this, out, flags);
    }
}
