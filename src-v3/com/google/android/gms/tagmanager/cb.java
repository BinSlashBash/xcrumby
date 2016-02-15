package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

class cb extends aj {
    private static final String ID;
    private static final C1367a YM;

    static {
        ID = C0321a.PLATFORM.toString();
        YM = dh.m1471r("Android");
    }

    public cb() {
        super(ID, new String[0]);
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2494x(Map<String, C1367a> map) {
        return YM;
    }
}
