/*
 * Decompiled with CFR 0_110.
 */
package com.google.gdata.util.common.base;

import java.util.Collection;
import java.util.Iterator;

public final class Preconditions {
    private Preconditions() {
    }

    public static void checkArgument(boolean bl2) {
        if (!bl2) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean bl2, Object object) {
        if (!bl2) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
    }

    public static /* varargs */ void checkArgument(boolean bl2, String string2, Object ... arrobject) {
        if (!bl2) {
            throw new IllegalArgumentException(Preconditions.format(string2, arrobject));
        }
    }

    public static <T extends Iterable<?>> T checkContentsNotNull(T t2) {
        if (Preconditions.containsOrIsNull(t2)) {
            throw new NullPointerException();
        }
        return t2;
    }

    public static <T extends Iterable<?>> T checkContentsNotNull(T t2, Object object) {
        if (Preconditions.containsOrIsNull(t2)) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t2;
    }

    public static /* varargs */ <T extends Iterable<?>> T checkContentsNotNull(T t2, String string2, Object ... arrobject) {
        if (Preconditions.containsOrIsNull(t2)) {
            throw new NullPointerException(Preconditions.format(string2, arrobject));
        }
        return t2;
    }

    public static void checkElementIndex(int n2, int n3) {
        Preconditions.checkElementIndex(n2, n3, "index");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void checkElementIndex(int n2, int n3, String string2) {
        boolean bl2 = n3 >= 0;
        Preconditions.checkArgument(bl2, "negative size: %s", n3);
        if (n2 < 0) {
            throw new IndexOutOfBoundsException(Preconditions.format("%s (%s) must not be negative", string2, n2));
        }
        if (n2 >= n3) {
            throw new IndexOutOfBoundsException(Preconditions.format("%s (%s) must be less than size (%s)", string2, n2, n3));
        }
    }

    public static <T> T checkNotNull(T t2) {
        if (t2 == null) {
            throw new NullPointerException();
        }
        return t2;
    }

    public static <T> T checkNotNull(T t2, Object object) {
        if (t2 == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t2;
    }

    public static /* varargs */ <T> T checkNotNull(T t2, String string2, Object ... arrobject) {
        if (t2 == null) {
            throw new NullPointerException(Preconditions.format(string2, arrobject));
        }
        return t2;
    }

    public static void checkPositionIndex(int n2, int n3) {
        Preconditions.checkPositionIndex(n2, n3, "index");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void checkPositionIndex(int n2, int n3, String string2) {
        boolean bl2 = n3 >= 0;
        Preconditions.checkArgument(bl2, "negative size: %s", n3);
        if (n2 < 0) {
            throw new IndexOutOfBoundsException(Preconditions.format("%s (%s) must not be negative", string2, n2));
        }
        if (n2 > n3) {
            throw new IndexOutOfBoundsException(Preconditions.format("%s (%s) must not be greater than size (%s)", string2, n2, n3));
        }
    }

    public static void checkPositionIndexes(int n2, int n3, int n4) {
        Preconditions.checkPositionIndex(n2, n4, "start index");
        Preconditions.checkPositionIndex(n3, n4, "end index");
        if (n3 < n2) {
            throw new IndexOutOfBoundsException(Preconditions.format("end index (%s) must not be less than start index (%s)", n3, n2));
        }
    }

    public static void checkState(boolean bl2) {
        if (!bl2) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean bl2, Object object) {
        if (!bl2) {
            throw new IllegalStateException(String.valueOf(object));
        }
    }

    public static /* varargs */ void checkState(boolean bl2, String string2, Object ... arrobject) {
        if (!bl2) {
            throw new IllegalStateException(Preconditions.format(string2, arrobject));
        }
    }

    private static boolean containsOrIsNull(Iterable<?> object) {
        if (object == null) {
            return true;
        }
        if (object instanceof Collection) {
            object = (Collection)object;
            try {
                boolean bl2 = object.contains(null);
                return bl2;
            }
            catch (NullPointerException var0_1) {
                return false;
            }
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (object.next() != null) continue;
            return true;
        }
        return false;
    }

    static /* varargs */ String format(String string2, Object ... arrobject) {
        StringBuilder stringBuilder;
        block3 : {
            stringBuilder = new StringBuilder(string2.length() + arrobject.length * 16);
            int n2 = 0;
            int n3 = 0;
            do {
                int n4;
                if (n3 >= arrobject.length || (n4 = string2.indexOf("%s", n2)) == -1) {
                    stringBuilder.append(string2.substring(n2));
                    if (n3 >= arrobject.length) break block3;
                    stringBuilder.append(" [");
                    stringBuilder.append(arrobject[n3]);
                    ++n3;
                    while (n3 < arrobject.length) {
                        stringBuilder.append(", ");
                        stringBuilder.append(arrobject[n3]);
                        ++n3;
                    }
                    break;
                }
                stringBuilder.append(string2.substring(n2, n4));
                stringBuilder.append(arrobject[n3]);
                n2 = n4 + 2;
                ++n3;
            } while (true);
            stringBuilder.append("]");
        }
        return stringBuilder.toString();
    }
}

