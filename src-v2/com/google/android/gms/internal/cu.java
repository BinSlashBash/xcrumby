/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.cv;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.cz;
import com.google.android.gms.internal.do;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.dx;

public final class cu {
    public static do a(Context context, cx cx2, a a2) {
        if (cx2.kK.rt) {
            return cu.b(context, cx2, a2);
        }
        return cu.c(context, cx2, a2);
    }

    private static do b(Context object, cx cx2, a a2) {
        dw.v("Fetching ad response from local ad request service.");
        object = new cv.a((Context)object, cx2, a2);
        object.start();
        return object;
    }

    private static do c(Context context, cx cx2, a a2) {
        dw.v("Fetching ad response from remote ad request service.");
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) != 0) {
            dw.z("Failed to connect to remote ad request service.");
            return null;
        }
        return new cv.b(context, cx2, a2);
    }

    public static interface a {
        public void a(cz var1);
    }

}

