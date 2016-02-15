package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

/* renamed from: com.google.android.gms.tagmanager.b */
class C1061b extends aj {
    private static final String ID;
    private final C0494a Wz;

    static {
        ID = C0321a.ADVERTISER_ID.toString();
    }

    public C1061b(Context context) {
        this(C0494a.m1356E(context));
    }

    C1061b(C0494a c0494a) {
        super(ID, new String[0]);
        this.Wz = c0494a;
    }

    public boolean jX() {
        return false;
    }

    public C1367a m2466x(Map<String, C1367a> map) {
        String jT = this.Wz.jT();
        return jT == null ? dh.lT() : dh.m1471r(jT);
    }
}
