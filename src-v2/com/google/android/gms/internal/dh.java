/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.ak;
import com.google.android.gms.internal.bi;
import com.google.android.gms.internal.bj;
import com.google.android.gms.internal.bl;
import com.google.android.gms.internal.br;
import com.google.android.gms.internal.dz;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public final class dh {
    public final int errorCode;
    public final bl nA;
    public final List<String> ne;
    public final List<String> nf;
    public final long ni;
    public final bi nx;
    public final br ny;
    public final String nz;
    public final dz oj;
    public final int orientation;
    public final ah pg;
    public final String pj;
    public final long pn;
    public final boolean po;
    public final long pp;
    public final List<String> pq;
    public final String pt;
    public final JSONObject qs;
    public final bj qt;
    public final ak qu;
    public final long qv;
    public final long qw;

    /*
     * Enabled aggressive block sorting
     */
    public dh(ah list, dz dz2, List<String> list2, int n2, List<String> list3, List<String> list4, int n3, long l2, String string2, boolean bl2, bi bi2, br br2, String string3, bj bj2, bl bl3, long l3, ak ak2, long l4, long l5, long l6, String string4, JSONObject jSONObject) {
        this.pg = list;
        this.oj = dz2;
        list = list2 != null ? Collections.unmodifiableList(list2) : null;
        this.ne = list;
        this.errorCode = n2;
        list = list3 != null ? Collections.unmodifiableList(list3) : null;
        this.nf = list;
        list = list4 != null ? Collections.unmodifiableList(list4) : null;
        this.pq = list;
        this.orientation = n3;
        this.ni = l2;
        this.pj = string2;
        this.po = bl2;
        this.nx = bi2;
        this.ny = br2;
        this.nz = string3;
        this.qt = bj2;
        this.nA = bl3;
        this.pp = l3;
        this.qu = ak2;
        this.pn = l4;
        this.qv = l5;
        this.qw = l6;
        this.pt = string4;
        this.qs = jSONObject;
    }
}

