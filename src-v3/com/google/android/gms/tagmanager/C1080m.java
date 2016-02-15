package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0325b;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

/* renamed from: com.google.android.gms.tagmanager.m */
class C1080m extends aj {
    private static final String ID;
    private static final String VALUE;

    static {
        ID = C0321a.CONSTANT.toString();
        VALUE = C0325b.VALUE.toString();
    }

    public C1080m() {
        super(ID, VALUE);
    }

    public static String ka() {
        return ID;
    }

    public static String kb() {
        return VALUE;
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2531x(Map<String, C1367a> map) {
        return (C1367a) map.get(VALUE);
    }
}
