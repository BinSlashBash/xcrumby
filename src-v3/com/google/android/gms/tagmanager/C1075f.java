package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

/* renamed from: com.google.android.gms.tagmanager.f */
class C1075f extends aj {
    private static final String ID;
    private final Context mContext;

    static {
        ID = C0321a.APP_ID.toString();
    }

    public C1075f(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2528x(Map<String, C1367a> map) {
        return dh.m1471r(this.mContext.getPackageName());
    }
}
