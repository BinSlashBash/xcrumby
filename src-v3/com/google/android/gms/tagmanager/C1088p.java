package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

/* renamed from: com.google.android.gms.tagmanager.p */
class C1088p extends aj {
    private static final String ID;
    private final String Xl;

    static {
        ID = C0321a.CONTAINER_VERSION.toString();
    }

    public C1088p(String str) {
        super(ID, new String[0]);
        this.Xl = str;
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2543x(Map<String, C1367a> map) {
        return this.Xl == null ? dh.lT() : dh.m1471r(this.Xl);
    }
}
