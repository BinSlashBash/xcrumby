/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.squareup.otto;

import android.os.Looper;
import com.squareup.otto.Bus;

public interface ThreadEnforcer {
    public static final ThreadEnforcer ANY = new ThreadEnforcer(){

        @Override
        public void enforce(Bus bus) {
        }
    };
    public static final ThreadEnforcer MAIN = new ThreadEnforcer(){

        @Override
        public void enforce(Bus bus) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                throw new IllegalStateException("Event bus " + bus + " accessed from non-main thread " + (Object)Looper.myLooper());
            }
        }
    };

    public void enforce(Bus var1);

}

