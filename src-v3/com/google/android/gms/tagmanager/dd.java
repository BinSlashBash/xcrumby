package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

class dd extends aj {
    private static final String ID;

    static {
        ID = C0321a.TIME.toString();
    }

    public dd() {
        super(ID, new String[0]);
    }

    public boolean jX() {
        return false;
    }

    public C1367a m2524x(Map<String, C1367a> map) {
        return dh.m1471r(Long.valueOf(System.currentTimeMillis()));
    }
}
