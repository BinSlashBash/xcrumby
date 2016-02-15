package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

class ax extends aj {
    private static final String ID;
    private static final String WA;
    private final Context kI;

    static {
        ID = C0321a.INSTALL_REFERRER.toString();
        WA = C0325b.COMPONENT.toString();
    }

    public ax(Context context) {
        super(ID, new String[0]);
        this.kI = context;
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2461x(Map<String, C1367a> map) {
        String d = ay.m1373d(this.kI, ((C1367a) map.get(WA)) != null ? dh.m1460j((C1367a) map.get(WA)) : null);
        return d != null ? dh.m1471r(d) : dh.lT();
    }
}
