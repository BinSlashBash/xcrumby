/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.theories.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ParameterizedAssertionError
extends RuntimeException {
    private static final long serialVersionUID = 1;

    public /* varargs */ ParameterizedAssertionError(Throwable throwable, String string2, Object ... arrobject) {
        super(String.format("%s(%s)", string2, ParameterizedAssertionError.join(", ", arrobject)), throwable);
    }

    public static String join(String string2, Collection<Object> object) {
        StringBuffer stringBuffer = new StringBuffer();
        object = object.iterator();
        while (object.hasNext()) {
            stringBuffer.append(ParameterizedAssertionError.stringValueOf(object.next()));
            if (!object.hasNext()) continue;
            stringBuffer.append(string2);
        }
        return stringBuffer.toString();
    }

    public static /* varargs */ String join(String string2, Object ... arrobject) {
        return ParameterizedAssertionError.join(string2, Arrays.asList(arrobject));
    }

    private static String stringValueOf(Object object) {
        try {
            object = String.valueOf(object);
            return object;
        }
        catch (Throwable var0_1) {
            return "[toString failed]";
        }
    }

    public boolean equals(Object object) {
        return this.toString().equals(object.toString());
    }
}

