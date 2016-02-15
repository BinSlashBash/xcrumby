package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Collections;
import java.util.List;

public final class cz implements SafeParcelable {
    public static final da CREATOR;
    public final int errorCode;
    public final List<String> ne;
    public final List<String> nf;
    public final long ni;
    public final String ol;
    public final int orientation;
    public final String pm;
    public final long pn;
    public final boolean po;
    public final long pp;
    public final List<String> pq;
    public final String pr;
    public final long ps;
    public final String pt;
    public final boolean pu;
    public final String pv;
    public final String pw;
    public final int versionCode;

    static {
        CREATOR = new da();
    }

    public cz(int i) {
        this(7, null, null, null, i, null, -1, false, -1, null, -1, -1, null, -1, null, false, null, null);
    }

    public cz(int i, long j) {
        this(7, null, null, null, i, null, -1, false, -1, null, j, -1, null, -1, null, false, null, null);
    }

    cz(int i, String str, String str2, List<String> list, int i2, List<String> list2, long j, boolean z, long j2, List<String> list3, long j3, int i3, String str3, long j4, String str4, boolean z2, String str5, String str6) {
        this.versionCode = i;
        this.ol = str;
        this.pm = str2;
        this.ne = list != null ? Collections.unmodifiableList(list) : null;
        this.errorCode = i2;
        this.nf = list2 != null ? Collections.unmodifiableList(list2) : null;
        this.pn = j;
        this.po = z;
        this.pp = j2;
        this.pq = list3 != null ? Collections.unmodifiableList(list3) : null;
        this.ni = j3;
        this.orientation = i3;
        this.pr = str3;
        this.ps = j4;
        this.pt = str4;
        this.pu = z2;
        this.pv = str5;
        this.pw = str6;
    }

    public cz(String str, String str2, List<String> list, List<String> list2, long j, boolean z, long j2, List<String> list3, long j3, int i, String str3, long j4, String str4, String str5) {
        this(7, str, str2, list, -2, list2, j, z, j2, list3, j3, i, str3, j4, str4, false, null, str5);
    }

    public cz(String str, String str2, List<String> list, List<String> list2, long j, boolean z, long j2, List<String> list3, long j3, int i, String str3, long j4, String str4, boolean z2, String str5, String str6) {
        this(7, str, str2, list, -2, list2, j, z, j2, list3, j3, i, str3, j4, str4, z2, str5, str6);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        da.m720a(this, out, flags);
    }
}
