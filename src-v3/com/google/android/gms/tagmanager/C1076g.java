package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.gms.internal.C0321a;
import com.google.android.gms.internal.C0355d.C1367a;
import java.util.Map;

/* renamed from: com.google.android.gms.tagmanager.g */
class C1076g extends aj {
    private static final String ID;
    private final Context mContext;

    static {
        ID = C0321a.APP_NAME.toString();
    }

    public C1076g(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public boolean jX() {
        return true;
    }

    public C1367a m2529x(Map<String, C1367a> map) {
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            return dh.m1471r(packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.mContext.getPackageName(), 0)).toString());
        } catch (Throwable e) {
            bh.m1381b("App name is not found.", e);
            return dh.lT();
        }
    }
}
