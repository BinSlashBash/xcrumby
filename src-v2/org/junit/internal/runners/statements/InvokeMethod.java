/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners.statements;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class InvokeMethod
extends Statement {
    private Object fTarget;
    private final FrameworkMethod fTestMethod;

    public InvokeMethod(FrameworkMethod frameworkMethod, Object object) {
        this.fTestMethod = frameworkMethod;
        this.fTarget = object;
    }

    @Override
    public void evaluate() throws Throwable {
        this.fTestMethod.invokeExplosively(this.fTarget, new Object[0]);
    }
}

