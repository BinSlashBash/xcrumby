/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class h
extends aj {
    private static final String ID = a.y.toString();
    private final Context mContext;

    public h(Context context) {
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
            object = dh.r(this.mContext.getPackageManager().getPackageInfo((String)this.mContext.getPackageName(), (int)0).versionCode);
            return object;
        }
        catch (PackageManager.NameNotFoundException var1_2) {
            bh.w("Package name " + this.mContext.getPackageName() + " not found. " + var1_2.getMessage());
            return dh.lT();
        }
    }
}

