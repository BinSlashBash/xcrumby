package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

abstract class cc extends aj {
    private static final String XQ;
    private static final String YN;

    static {
        XQ = C0325b.ARG0.toString();
        YN = C0325b.ARG1.toString();
    }

    public cc(String str) {
        super(str, XQ, YN);
    }

    protected abstract boolean m2495a(C1367a c1367a, C1367a c1367a2, Map<String, C1367a> map);

    public boolean jX() {
        return true;
    }

    public C1367a m2496x(Map<String, C1367a> map) {
        for (C1367a c1367a : map.values()) {
            if (c1367a == dh.lT()) {
                return dh.m1471r(Boolean.valueOf(false));
            }
        }
        C1367a c1367a2 = (C1367a) map.get(XQ);
        C1367a c1367a3 = (C1367a) map.get(YN);
        boolean a = (c1367a2 == null || c1367a3 == null) ? false : m2495a(c1367a2, c1367a3, map);
        return dh.m1471r(Boolean.valueOf(a));
    }
}
