package org.junit.internal.runners.statements;

import org.junit.runners.model.Statement;

public class Fail extends Statement {
    private final Throwable fError;

    public Fail(Throwable e) {
        this.fError = e;
    }

    public void evaluate() throws Throwable {
        throw this.fError;
    }
}
