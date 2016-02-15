package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

/* renamed from: com.google.android.gms.tagmanager.q */
class C1505q extends dc {
    private static final String ID;

    static {
        ID = C0321a.CONTAINS.toString();
    }

    public C1505q() {
        super(ID);
    }

    protected boolean m3447a(String str, String str2, Map<String, C1367a> map) {
        return str.contains(str2);
    }
}
