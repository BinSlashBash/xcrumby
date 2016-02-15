/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class g
extends aj {
    private static final String ID = a.x.toString();
    private final Context mContext;

    public g(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    @Override
    public boolean jX() {
        return true;
    }

    @Override
    public d.a x(Map<String, d.a> object) {
        try {
            object = this.mContext.getPackageManager();
            object = dh.r(object.getApplicationLabel(object.getApplicationInfo(this.mContext.getPackageName(), 0)).toString());
            return object;
        }
        catch (PackageManager.NameNotFoundException var1_2) {
            bh.b("App name is not found.", (Throwable)var1_2);
            return dh.lT();
        }
    }
}

