package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

class al extends bx {
    private static final String ID;

    static {
        ID = C0321a.GREATER_EQUALS.toString();
    }

    public al() {
        super(ID);
    }

    protected boolean m3441a(dg dgVar, dg dgVar2, Map<String, C1367a> map) {
        return dgVar.m1459a(dgVar2) >= 0;
    }
}
