/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.gl;

public final class gn
implements gl {
    private static gn Er;

    public static gl ft() {
        synchronized (gn.class) {
            if (Er == null) {
                Er = new gn();
            }
            gn gn2 = Er;
            return gn2;
        }
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}

