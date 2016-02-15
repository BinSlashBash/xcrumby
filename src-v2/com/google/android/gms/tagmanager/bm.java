/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.tagmanager;

import android.os.Build;
import com.google.android.gms.tagmanager.av;
import com.google.android.gms.tagmanager.aw;
import com.google.android.gms.tagmanager.bl;

class bm {
    bm() {
    }

    int jZ() {
        return Build.VERSION.SDK_INT;
    }

    public bl kH() {
        if (this.jZ() < 8) {
            return new av();
        }
        return new aw();
    }
}

