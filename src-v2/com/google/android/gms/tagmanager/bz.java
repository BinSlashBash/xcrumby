/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.tagmanager;

import android.os.Build;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.aj;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class bz
extends aj {
    private static final String ID = a.M.toString();

    public bz() {
        super(ID, new String[0]);
    }

    @Override
    public boolean jX() {
        return true;
    }

    @Override
    public d.a x(Map<String, d.a> map) {
        return dh.r(Build.VERSION.RELEASE);
    }
}

