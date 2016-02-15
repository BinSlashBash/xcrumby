/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners.statements;

import org.junit.runners.model.Statement;

public class Fail
extends Statement {
    private final Throwable fError;

    public Fail(Throwable throwable) {
        this.fError = throwable;
    }

    @Override
    public void evaluate() throws Throwable {
        throw this.fError;
    }
}

