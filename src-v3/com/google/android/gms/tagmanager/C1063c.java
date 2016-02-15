package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

/* renamed from: com.google.android.gms.tagmanager.c */
class C1063c extends aj {
    private static final String ID;
    private final C0494a Wz;

    static {
        ID = C0321a.ADVERTISING_TRACKING_ENABLED.toString();
    }

    public C1063c(Context context) {
        this(C0494a.m1356E(context));
    }

    C1063c(C0494a c0494a) {
        super(ID, new String[0]);
        this.Wz = c0494a;
    }

    public boolean jX() {
        return false;
    }

    public C1367a m2477x(Map<String, C1367a> map) {
        return dh.m1471r(Boolean.valueOf(!this.Wz.isLimitAdTrackingEnabled()));
    }
}
