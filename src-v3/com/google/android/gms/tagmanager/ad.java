package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

class ad extends dc {
    private static final String ID;

    static {
        ID = C0321a.ENDS_WITH.toString();
    }

    public ad() {
        super(ID);
    }

    protected boolean m3439a(String str, String str2, Map<String, C1367a> map) {
        return str.endsWith(str2);
    }
}
