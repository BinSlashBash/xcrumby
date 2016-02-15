package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

/* renamed from: com.google.android.gms.tagmanager.e */
class C1074e extends aj {
    private static final String ID;
    private static final String WA;
    private static final String WB;
    private final Context kI;

    static {
        ID = C0321a.ADWORDS_CLICK_REFERRER.toString();
        WA = C0325b.COMPONENT.toString();
        WB = C0325b.CONVERSION_ID.toString();
    }

    public C1074e(Context context) {
        super(ID, WB);
        this.kI = context;
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2527x(Map<String, C1367a> map) {
        C1367a c1367a = (C1367a) map.get(WB);
        if (c1367a == null) {
            return dh.lT();
        }
        String j = dh.m1460j(c1367a);
        c1367a = (C1367a) map.get(WA);
        String e = ay.m1374e(this.kI, j, c1367a != null ? dh.m1460j(c1367a) : null);
        return e != null ? dh.m1471r(e) : dh.lT();
    }
}
