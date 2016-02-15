/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.lang.reflect.Field;

public abstract class fe
implements SafeParcelable {
    private static final Object CW = new Object();
    private static ClassLoader CX = null;
    private static Integer CY = null;
    private boolean CZ = false;

    private static boolean a(Class<?> class_) {
        try {
            boolean bl2 = "SAFE_PARCELABLE_NULL_STRING".equals(class_.getField("NULL").get(null));
            return bl2;
        }
        catch (IllegalAccessException var0_1) {
            return false;
        }
        catch (NoSuchFieldException var0_2) {
            return false;
        }
    }

    protected static boolean al(String string2) {
        ClassLoader classLoader = fe.eI();
        if (classLoader == null) {
            return true;
        }
        try {
            boolean bl2 = fe.a(classLoader.loadClass(string2));
            return bl2;
        }
        catch (Exception var0_1) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static ClassLoader eI() {
        Object object = CW;
        synchronized (object) {
            return CX;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static Integer eJ() {
        Object object = CW;
        synchronized (object) {
            return CY;
        }
    }

    protected boolean eK() {
        return this.CZ;
    }
}

