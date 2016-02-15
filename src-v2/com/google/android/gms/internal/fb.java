/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.os.Looper;
import android.util.Log;

public final class fb {
    public static void a(boolean bl2, Object object) {
        if (!bl2) {
            throw new IllegalStateException(String.valueOf(object));
        }
    }

    public static void aj(String string2) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            Log.e((String)"Asserts", (String)("checkMainThread: current thread " + Thread.currentThread() + " IS NOT the main thread " + Looper.getMainLooper().getThread() + "!"));
            throw new IllegalStateException(string2);
        }
    }

    public static void ak(String string2) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Log.e((String)"Asserts", (String)("checkNotMainThread: current thread " + Thread.currentThread() + " IS the main thread " + Looper.getMainLooper().getThread() + "!"));
            throw new IllegalStateException(string2);
        }
    }

    public static void d(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("null reference");
        }
    }

    public static void x(boolean bl2) {
        if (!bl2) {
            throw new IllegalStateException();
        }
    }
}

