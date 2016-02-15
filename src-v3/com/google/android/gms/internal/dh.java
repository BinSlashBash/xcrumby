package com.google.android.gms.internal;

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

    public dh(ah ahVar, dz dzVar, List<String> list, int i, List<String> list2, List<String> list3, int i2, long j, String str, boolean z, bi biVar, br brVar, String str2, bj bjVar, bl blVar, long j2, ak akVar, long j3, long j4, long j5, String str3, JSONObject jSONObject) {
        this.pg = ahVar;
        this.oj = dzVar;
        this.ne = list != null ? Collections.unmodifiableList(list) : null;
        this.errorCode = i;
        this.nf = list2 != null ? Collections.unmodifiableList(list2) : null;
        this.pq = list3 != null ? Collections.unmodifiableList(list3) : null;
        this.orientation = i2;
        this.ni = j;
        this.pj = str;
        this.po = z;
        this.nx = biVar;
        this.ny = brVar;
        this.nz = str2;
        this.qt = bjVar;
        this.nA = blVar;
        this.pp = j2;
        this.qu = akVar;
        this.pn = j3;
        this.qv = j4;
        this.qw = j5;
        this.pt = str3;
        this.qs = jSONObject;
    }
}
