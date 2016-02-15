package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

class ce extends aj {
    private static final String ID;
    private static final String YX;
    private static final String YY;

    static {
        ID = C0321a.RANDOM.toString();
        YX = C0325b.MIN.toString();
        YY = C0325b.MAX.toString();
    }

    public ce() {
        super(ID, new String[0]);
    }

    public boolean jX() {
        return false;
    }

    public C1367a m2497x(Map<String, C1367a> map) {
        double doubleValue;
        double d;
        C1367a c1367a = (C1367a) map.get(YX);
        C1367a c1367a2 = (C1367a) map.get(YY);
        if (!(c1367a == null || c1367a == dh.lT() || c1367a2 == null || c1367a2 == dh.lT())) {
            dg k = dh.m1461k(c1367a);
            dg k2 = dh.m1461k(c1367a2);
            if (!(k == dh.lR() || k2 == dh.lR())) {
                double doubleValue2 = k.doubleValue();
                doubleValue = k2.doubleValue();
                if (doubleValue2 <= doubleValue) {
                    d = doubleValue2;
                    return dh.m1471r(Long.valueOf(Math.round(((doubleValue - d) * Math.random()) + d)));
                }
            }
        }
        doubleValue = 2.147483647E9d;
        d = 0.0d;
        return dh.m1471r(Long.valueOf(Math.round(((doubleValue - d) * Math.random()) + d)));
    }
}
