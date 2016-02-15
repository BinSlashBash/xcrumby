/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners.statements;

import java.util.ArrayList;
import java.util.List;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public class RunAfters
extends Statement {
    private final List<FrameworkMethod> fAfters;
    private final Statement fNext;
    private final Object fTarget;

    public RunAfters(Statement statement, List<FrameworkMethod> list, Object object) {
        this.fNext = statement;
        this.fAfters = list;
        this.fTarget = object;
    }

    @Override
    public void evaluate() throws Throwable {
        ArrayList<Throwable> arrayList = new ArrayList<Throwable>();
        try {
            this.fNext.evaluate();
        }
        catch (Throwable var2_3) {
            arrayList.add(var2_3);
        }
        finally {
            for (FrameworkMethod frameworkMethod : this.fAfters) {
                try {
                    frameworkMethod.invokeExplosively(this.fTarget, new Object[0]);
                }
                catch (Throwable var3_7) {
                    arrayList.add(var3_7);
                }
            }
        }
        MultipleFailureException.assertEmpty(arrayList);
    }
}

