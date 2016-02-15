/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 */
package com.google.android.gms.tagmanager;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class z
extends aj {
    private static final String ID = a.X.toString();
    private final Context mContext;

    public z(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    protected String G(Context context) {
        return Settings.Secure.getString((ContentResolver)context.getContentResolver(), (String)"android_id");
    }

    @Override
    public boolean jX() {
        return true;
    }

    @Override
    public d.a x(Map<String, d.a> object) {
        object = this.G(this.mContext);
        if (object == null) {
            return dh.lT();
        }
        return dh.r(object);
    }
}

