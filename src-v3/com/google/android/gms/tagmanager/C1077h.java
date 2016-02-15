package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

/* renamed from: com.google.android.gms.tagmanager.h */
class C1077h extends aj {
    private static final String ID;
    private final Context mContext;

    static {
        ID = C0321a.APP_VERSION.toString();
    }

    public C1077h(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2530x(Map<String, C1367a> map) {
        try {
            return dh.m1471r(Integer.valueOf(this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionCode));
        } catch (NameNotFoundException e) {
            bh.m1384w("Package name " + this.mContext.getPackageName() + " not found. " + e.getMessage());
            return dh.lT();
        }
    }
}
