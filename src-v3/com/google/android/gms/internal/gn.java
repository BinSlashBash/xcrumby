package com.google.android.gms.internal;

public final class gn implements gl {
    private static gn Er;

    public static synchronized gl ft() {
        gl glVar;
        synchronized (gn.class) {
            if (Er == null) {
                Er = new gn();
            }
            glVar = Er;
        }
        return glVar;
    }

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
