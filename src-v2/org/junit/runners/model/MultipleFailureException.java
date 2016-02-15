/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MultipleFailureException
extends Exception {
    private static final long serialVersionUID = 1;
    private final List<Throwable> fErrors;

    public MultipleFailureException(List<Throwable> list) {
        this.fErrors = new ArrayList<Throwable>(list);
    }

    public static void assertEmpty(List<Throwable> list) throws Throwable {
        if (list.isEmpty()) {
            return;
        }
        if (list.size() == 1) {
            throw list.get(0);
        }
        throw new org.junit.internal.runners.model.MultipleFailureException(list);
    }

    public List<Throwable> getFailures() {
        return Collections.unmodifiableList(this.fErrors);
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder(String.format("There were %d errors:", this.fErrors.size()));
        for (Throwable throwable : this.fErrors) {
            stringBuilder.append(String.format("\n  %s(%s)", throwable.getClass().getName(), throwable.getMessage()));
        }
        return stringBuilder.toString();
    }
}

