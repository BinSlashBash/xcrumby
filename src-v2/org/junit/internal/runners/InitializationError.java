/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners;

import java.util.Arrays;
import java.util.List;

@Deprecated
public class InitializationError
extends Exception {
    private static final long serialVersionUID = 1;
    private final List<Throwable> fErrors;

    public InitializationError(String string2) {
        this(new Exception(string2));
    }

    public InitializationError(List<Throwable> list) {
        this.fErrors = list;
    }

    public /* varargs */ InitializationError(Throwable ... arrthrowable) {
        this(Arrays.asList(arrthrowable));
    }

    public List<Throwable> getCauses() {
        return this.fErrors;
    }
}

