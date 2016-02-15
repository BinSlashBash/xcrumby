package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

class db extends dc {
    private static final String ID;

    static {
        ID = C0321a.STARTS_WITH.toString();
    }

    public db() {
        super(ID);
    }

    protected boolean m3446a(String str, String str2, Map<String, C1367a> map) {
        return str.startsWith(str2);
    }
}
