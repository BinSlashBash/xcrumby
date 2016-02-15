package com.google.android.gms.internal;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public abstract class fe implements SafeParcelable {
    private static final Object CW;
    private static ClassLoader CX;
    private static Integer CY;
    private boolean CZ;

    static {
        CW = new Object();
        CX = null;
        CY = null;
    }

    public fe() {
        this.CZ = false;
    }

    private static boolean m2159a(Class<?> cls) {
        boolean z = false;
        try {
            z = SafeParcelable.NULL.equals(cls.getField("NULL").get(null));
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e2) {
        }
        return z;
    }

    protected static boolean al(String str) {
        ClassLoader eI = eI();
        if (eI == null) {
            return true;
        }
        try {
            return m2159a(eI.loadClass(str));
        } catch (Exception e) {
            return false;
        }
    }

    protected static ClassLoader eI() {
        ClassLoader classLoader;
        synchronized (CW) {
            classLoader = CX;
        }
        return classLoader;
    }

    protected static Integer eJ() {
        Integer num;
        synchronized (CW) {
            num = CY;
        }
        return num;
    }

    protected boolean eK() {
        return this.CZ;
    }
}
