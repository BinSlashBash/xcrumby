/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class f
extends aj {
    private static final String ID = a.w.toString();
    private final Context mContext;

    public f(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    @Override
    public boolean jX() {
        return true;
    }

    @Override
    public d.a x(Map<String, d.a> map) {
        return dh.r(this.mContext.getPackageName());
    }
}

