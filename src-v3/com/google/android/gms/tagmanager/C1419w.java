package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.android.gms.tagmanager.w */
class C1419w extends df {
    private static final String ID;
    private static final String VALUE;
    private static final String XL;
    private final DataLayer WK;

    static {
        ID = C0321a.DATA_LAYER_WRITE.toString();
        VALUE = C0325b.VALUE.toString();
        XL = C0325b.CLEAR_PERSISTENT_DATA_LAYER_PREFIX.toString();
    }

    public C1419w(DataLayer dataLayer) {
        super(ID, VALUE);
        this.WK = dataLayer;
    }

    private void m3243a(C1367a c1367a) {
        if (c1367a != null && c1367a != dh.lN()) {
            String j = dh.m1460j(c1367a);
            if (j != dh.lS()) {
                this.WK.bv(j);
            }
        }
    }

    private void m3244b(C1367a c1367a) {
        if (c1367a != null && c1367a != dh.lN()) {
            Object o = dh.m1468o(c1367a);
            if (o instanceof List) {
                for (Object o2 : (List) o2) {
                    if (o2 instanceof Map) {
                        this.WK.push((Map) o2);
                    }
                }
            }
        }
    }

    public void m3245z(Map<String, C1367a> map) {
        m3244b((C1367a) map.get(VALUE));
        m3243a((C1367a) map.get(XL));
    }
}
