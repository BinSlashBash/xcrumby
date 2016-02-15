package com.google.android.gms.plus.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;
import java.util.Arrays;

/* renamed from: com.google.android.gms.plus.internal.h */
public class C1053h implements SafeParcelable {
    public static final C0483j CREATOR;
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

    static {
        CREATOR = new C0483j();
    }

    C1053h(int i, String str, String[] strArr, String[] strArr2, String[] strArr3, String str2, String str3, String str4, String str5, PlusCommonExtras plusCommonExtras) {
        this.xH = i;
        this.wG = str;
        this.Uk = strArr;
        this.Ul = strArr2;
        this.Um = strArr3;
        this.Un = str2;
        this.Uo = str3;
        this.Up = str4;
        this.Uq = str5;
        this.Ur = plusCommonExtras;
    }

    public C1053h(String str, String[] strArr, String[] strArr2, String[] strArr3, String str2, String str3, String str4, PlusCommonExtras plusCommonExtras) {
        this.xH = 1;
        this.wG = str;
        this.Uk = strArr;
        this.Ul = strArr2;
        this.Um = strArr3;
        this.Un = str2;
        this.Uo = str3;
        this.Up = str4;
        this.Uq = null;
        this.Ur = plusCommonExtras;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C1053h)) {
            return false;
        }
        C1053h c1053h = (C1053h) obj;
        return this.xH == c1053h.xH && fo.equal(this.wG, c1053h.wG) && Arrays.equals(this.Uk, c1053h.Uk) && Arrays.equals(this.Ul, c1053h.Ul) && Arrays.equals(this.Um, c1053h.Um) && fo.equal(this.Un, c1053h.Un) && fo.equal(this.Uo, c1053h.Uo) && fo.equal(this.Up, c1053h.Up) && fo.equal(this.Uq, c1053h.Uq) && fo.equal(this.Ur, c1053h.Ur);
    }

    public String getAccountName() {
        return this.wG;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public int hashCode() {
        return fo.hashCode(Integer.valueOf(this.xH), this.wG, this.Uk, this.Ul, this.Um, this.Un, this.Uo, this.Up, this.Uq, this.Ur);
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
        this.Ur.m2408m(bundle);
        return bundle;
    }

    public String toString() {
        return fo.m976e(this).m975a("versionCode", Integer.valueOf(this.xH)).m975a("accountName", this.wG).m975a("requestedScopes", this.Uk).m975a("visibleActivities", this.Ul).m975a("requiredFeatures", this.Um).m975a("packageNameForAuth", this.Un).m975a("callingPackageName", this.Uo).m975a("applicationName", this.Up).m975a("extra", this.Ur.toString()).toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        C0483j.m1326a(this, out, flags);
    }
}
