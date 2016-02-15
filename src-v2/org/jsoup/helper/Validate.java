/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.helper;

public final class Validate {
    private Validate() {
    }

    public static void fail(String string2) {
        throw new IllegalArgumentException(string2);
    }

    public static void isFalse(boolean bl2) {
        if (bl2) {
            throw new IllegalArgumentException("Must be false");
        }
    }

    public static void isFalse(boolean bl2, String string2) {
        if (bl2) {
            throw new IllegalArgumentException(string2);
        }
    }

    public static void isTrue(boolean bl2) {
        if (!bl2) {
            throw new IllegalArgumentException("Must be true");
        }
    }

    public static void isTrue(boolean bl2, String string2) {
        if (!bl2) {
            throw new IllegalArgumentException(string2);
        }
    }

    public static void noNullElements(Object[] arrobject) {
        Validate.noNullElements(arrobject, "Array must not contain any null objects");
    }

    public static void noNullElements(Object[] arrobject, String string2) {
        int n2 = arrobject.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (arrobject[i2] != null) continue;
            throw new IllegalArgumentException(string2);
        }
    }

    public static void notEmpty(String string2) {
        if (string2 == null || string2.length() == 0) {
            throw new IllegalArgumentException("String must not be empty");
        }
    }

    public static void notEmpty(String string2, String string3) {
        if (string2 == null || string2.length() == 0) {
            throw new IllegalArgumentException(string3);
        }
    }

    public static void notNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
    }

    public static void notNull(Object object, String string2) {
        if (object == null) {
            throw new IllegalArgumentException(string2);
        }
    }
}

