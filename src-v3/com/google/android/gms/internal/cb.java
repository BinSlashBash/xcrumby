package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class cb implements SafeParcelable {
    public static final ca CREATOR;
    public final String mimeType;
    public final String nN;
    public final String nO;
    public final String nP;
    public final String nQ;
    public final String nR;
    public final String packageName;
    public final int versionCode;

    static {
        CREATOR = new ca();
    }

    public cb(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.versionCode = i;
        this.nN = str;
        this.nO = str2;
        this.mimeType = str3;
        this.packageName = str4;
        this.nP = str5;
        this.nQ = str6;
        this.nR = str7;
    }

    public cb(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this(1, str, str2, str3, str4, str5, str6, str7);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        ca.m679a(this, out, flags);
    }
}
