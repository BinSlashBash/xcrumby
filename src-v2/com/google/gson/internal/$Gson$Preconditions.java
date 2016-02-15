/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal;

public final class $Gson$Preconditions {
    public static void checkArgument(boolean bl2) {
        if (!bl2) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T checkNotNull(T t2) {
        if (t2 == null) {
            throw new NullPointerException();
        }
        return t2;
    }
}

