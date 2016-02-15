package org.junit.internal.runners.statements;

import java.util.List;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RunBefores extends Statement {
    private final List<FrameworkMethod> fBefores;
    private final Statement fNext;
    private final Object fTarget;

    public RunBefores(Statement next, List<FrameworkMethod> befores, Object target) {
        this.fNext = next;
        this.fBefores = befores;
        this.fTarget = target;
    }

    public void evaluate() throws Throwable {
        for (FrameworkMethod before : this.fBefores) {
            before.invokeExplosively(this.fTarget, new Object[0]);
        }
        this.fNext.evaluate();
    }
}
