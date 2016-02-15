/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;

public final class gk {
    public static boolean y(Context context) {
        if (!context.getPackageManager().hasSystemFeature("android.hardware.type.watch")) {
            return false;
        }
        return true;
    }
}

