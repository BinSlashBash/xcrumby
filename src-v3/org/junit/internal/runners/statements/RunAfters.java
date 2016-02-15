package org.junit.internal.runners.statements;

import java.util.ArrayList;
import java.util.List;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public class RunAfters extends Statement {
    private final List<FrameworkMethod> fAfters;
    private final Statement fNext;
    private final Object fTarget;

    public RunAfters(Statement next, List<FrameworkMethod> afters, Object target) {
        this.fNext = next;
        this.fAfters = afters;
        this.fTarget = target;
    }

    public void evaluate() throws Throwable {
        List<Throwable> errors = new ArrayList();
        try {
            this.fNext.evaluate();
            for (FrameworkMethod each : this.fAfters) {
                try {
                    each.invokeExplosively(this.fTarget, new Object[0]);
                } catch (Throwable e) {
                    errors.add(e);
                }
            }
        } catch (Throwable e2) {
            errors.add(e2);
        }
        MultipleFailureException.assertEmpty(errors);
    }
}
