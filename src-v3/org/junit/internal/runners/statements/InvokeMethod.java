package org.junit.internal.runners.statements;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class InvokeMethod extends Statement {
    private Object fTarget;
    private final FrameworkMethod fTestMethod;

    public InvokeMethod(FrameworkMethod testMethod, Object target) {
        this.fTestMethod = testMethod;
        this.fTarget = target;
    }

    public void evaluate() throws Throwable {
        this.fTestMethod.invokeExplosively(this.fTarget, new Object[0]);
    }
}
