/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.da;
import java.util.Collections;
import java.util.List;

public final class cz
implements SafeParcelable {
    public static final da CREATOR = new da();
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

    public cz(int n2) {
        this(7, null, null, null, n2, null, -1, false, -1, null, -1, -1, null, -1, null, false, null, null);
    }

    public cz(int n2, long l2) {
        this(7, null, null, null, n2, null, -1, false, -1, null, l2, -1, null, -1, null, false, null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    cz(int n2, String list, String string2, List<String> list2, int n3, List<String> list3, long l2, boolean bl2, long l3, List<String> list4, long l4, int n4, String string3, long l5, String string4, boolean bl3, String string5, String string6) {
        this.versionCode = n2;
        this.ol = list;
        this.pm = string2;
        list = list2 != null ? Collections.unmodifiableList(list2) : null;
        this.ne = list;
        this.errorCode = n3;
        list = list3 != null ? Collections.unmodifiableList(list3) : null;
        this.nf = list;
        this.pn = l2;
        this.po = bl2;
        this.pp = l3;
        list = list4 != null ? Collections.unmodifiableList(list4) : null;
        this.pq = list;
        this.ni = l4;
        this.orientation = n4;
        this.pr = string3;
        this.ps = l5;
        this.pt = string4;
        this.pu = bl3;
        this.pv = string5;
        this.pw = string6;
    }

    public cz(String string2, String string3, List<String> list, List<String> list2, long l2, boolean bl2, long l3, List<String> list3, long l4, int n2, String string4, long l5, String string5, String string6) {
        this(7, string2, string3, list, -2, list2, l2, bl2, l3, list3, l4, n2, string4, l5, string5, false, null, string6);
    }

    public cz(String string2, String string3, List<String> list, List<String> list2, long l2, boolean bl2, long l3, List<String> list3, long l4, int n2, String string4, long l5, String string5, boolean bl3, String string6, String string7) {
        this(7, string2, string3, list, -2, list2, l2, bl2, l3, list3, l4, n2, string4, l5, string5, bl3, string6, string7);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        da.a(this, parcel, n2);
    }
}

