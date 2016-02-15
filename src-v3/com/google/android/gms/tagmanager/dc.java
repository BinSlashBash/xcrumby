package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

abstract class dc extends cc {
    public dc(String str) {
        super(str);
    }

    protected boolean m3216a(C1367a c1367a, C1367a c1367a2, Map<String, C1367a> map) {
        String j = dh.m1460j(c1367a);
        String j2 = dh.m1460j(c1367a2);
        return (j == dh.lS() || j2 == dh.lS()) ? false : m3217a(j, j2, (Map) map);
    }

    protected abstract boolean m3217a(String str, String str2, Map<String, C1367a> map);
}
