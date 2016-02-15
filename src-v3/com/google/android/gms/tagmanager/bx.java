package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

abstract class bx extends cc {
    public bx(String str) {
        super(str);
    }

    protected boolean m3205a(C1367a c1367a, C1367a c1367a2, Map<String, C1367a> map) {
        dg k = dh.m1461k(c1367a);
        dg k2 = dh.m1461k(c1367a2);
        return (k == dh.lR() || k2 == dh.lR()) ? false : m3206a(k, k2, (Map) map);
    }

    protected abstract boolean m3206a(dg dgVar, dg dgVar2, Map<String, C1367a> map);
}
