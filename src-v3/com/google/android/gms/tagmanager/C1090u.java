package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

/* renamed from: com.google.android.gms.tagmanager.u */
class C1090u extends aj {
    private static final String ID;
    private static final String NAME;
    private static final String XA;
    private final DataLayer WK;

    static {
        ID = C0321a.CUSTOM_VAR.toString();
        NAME = C0325b.NAME.toString();
        XA = C0325b.DEFAULT_VALUE.toString();
    }

    public C1090u(DataLayer dataLayer) {
        super(ID, NAME);
        this.WK = dataLayer;
    }

    public boolean jX() {
        return false;
    }

    public C1367a m2545x(Map<String, C1367a> map) {
        Object obj = this.WK.get(dh.m1460j((C1367a) map.get(NAME)));
        if (obj != null) {
            return dh.m1471r(obj);
        }
        C1367a c1367a = (C1367a) map.get(XA);
        return c1367a != null ? c1367a : dh.lT();
    }
}
