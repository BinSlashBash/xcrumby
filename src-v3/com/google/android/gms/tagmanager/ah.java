package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

class ah extends aj {
    private static final String ID;
    private final cs WL;

    static {
        ID = C0321a.EVENT.toString();
    }

    public ah(cs csVar) {
        super(ID, new String[0]);
        this.WL = csVar;
    }

    public boolean jX() {
        return false;
    }

    public C1367a m2446x(Map<String, C1367a> map) {
        String lx = this.WL.lx();
        return lx == null ? dh.lT() : dh.m1471r(lx);
    }
}
