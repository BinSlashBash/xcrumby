/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners.model;

import java.util.Arrays;
import java.util.List;

public class InitializationError
extends Exception {
    private static final long serialVersionUID = 1;
    private final List<Throwable> fErrors;

    public InitializationError(String string2) {
        this(new Exception(string2));
    }

    public InitializationError(Throwable throwable) {
        this(Arrays.asList(throwable));
    }

    public InitializationError(List<Throwable> list) {
        this.fErrors = list;
    }

    public List<Throwable> getCauses() {
        return this.fErrors;
    }
}

