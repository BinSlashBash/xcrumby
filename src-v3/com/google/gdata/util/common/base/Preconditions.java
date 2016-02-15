package com.google.gdata.util.common.base;

import java.util.Collection;

public final class Preconditions {
    private Preconditions() {
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    public static void checkState(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
        }
    }

    public static <T> T checkNotNull(T reference) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException();
    }

    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(String.valueOf(errorMessage));
    }

    public static <T> T checkNotNull(T reference, String errorMessageTemplate, Object... errorMessageArgs) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
    }

    public static <T extends Iterable<?>> T checkContentsNotNull(T iterable) {
        if (!containsOrIsNull(iterable)) {
            return iterable;
        }
        throw new NullPointerException();
    }

    public static <T extends Iterable<?>> T checkContentsNotNull(T iterable, Object errorMessage) {
        if (!containsOrIsNull(iterable)) {
            return iterable;
        }
        throw new NullPointerException(String.valueOf(errorMessage));
    }

    public static <T extends Iterable<?>> T checkContentsNotNull(T iterable, String errorMessageTemplate, Object... errorMessageArgs) {
        if (!containsOrIsNull(iterable)) {
            return iterable;
        }
        throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
    }

    private static boolean containsOrIsNull(Iterable<?> iterable) {
        if (iterable == null) {
            return true;
        }
        if (iterable instanceof Collection) {
            try {
                return ((Collection) iterable).contains(null);
            } catch (NullPointerException e) {
                return false;
            }
        }
        for (Object element : iterable) {
            if (element == null) {
                return true;
            }
        }
        return false;
    }

    public static void checkElementIndex(int index, int size) {
        checkElementIndex(index, size, "index");
    }

    public static void checkElementIndex(int index, int size, String desc) {
        boolean z;
        if (size >= 0) {
            z = true;
        } else {
            z = false;
        }
        checkArgument(z, "negative size: %s", Integer.valueOf(size));
        if (index < 0) {
            throw new IndexOutOfBoundsException(format("%s (%s) must not be negative", desc, Integer.valueOf(index)));
        } else if (index >= size) {
            throw new IndexOutOfBoundsException(format("%s (%s) must be less than size (%s)", desc, Integer.valueOf(index), Integer.valueOf(size)));
        }
    }

    public static void checkPositionIndex(int index, int size) {
        checkPositionIndex(index, size, "index");
    }

    public static void checkPositionIndex(int index, int size, String desc) {
        boolean z;
        if (size >= 0) {
            z = true;
        } else {
            z = false;
        }
        checkArgument(z, "negative size: %s", Integer.valueOf(size));
        if (index < 0) {
            throw new IndexOutOfBoundsException(format("%s (%s) must not be negative", desc, Integer.valueOf(index)));
        } else if (index > size) {
            throw new IndexOutOfBoundsException(format("%s (%s) must not be greater than size (%s)", desc, Integer.valueOf(index), Integer.valueOf(size)));
        }
    }

    public static void checkPositionIndexes(int start, int end, int size) {
        checkPositionIndex(start, size, "start index");
        checkPositionIndex(end, size, "end index");
        if (end < start) {
            throw new IndexOutOfBoundsException(format("end index (%s) must not be less than start index (%s)", Integer.valueOf(end), Integer.valueOf(start)));
        }
    }

    static String format(String template, Object... args) {
        StringBuilder builder = new StringBuilder(template.length() + (args.length * 16));
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            int i2 = i + 1;
            builder.append(args[i]);
            templateStart = placeholderStart + 2;
            i = i2;
        }
        builder.append(template.substring(templateStart));
        if (i < args.length) {
            builder.append(" [");
            i2 = i + 1;
            builder.append(args[i]);
            i = i2;
            while (i < args.length) {
                builder.append(", ");
                i2 = i + 1;
                builder.append(args[i]);
                i = i2;
            }
            builder.append("]");
        }
        return builder.toString();
    }
}
