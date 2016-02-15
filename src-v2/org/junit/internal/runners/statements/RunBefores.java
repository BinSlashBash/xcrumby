/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners.statements;

import java.util.Iterator;
import java.util.List;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RunBefores
extends Statement {
    private final List<FrameworkMethod> fBefores;
    private final Statement fNext;
    private final Object fTarget;

    public RunBefores(Statement statement, List<FrameworkMethod> list, Object object) {
        this.fNext = statement;
        this.fBefores = list;
        this.fTarget = object;
    }

    @Override
    public void evaluate() throws Throwable {
        Iterator<FrameworkMethod> iterator = this.fBefores.iterator();
        while (iterator.hasNext()) {
            iterator.next().invokeExplosively(this.fTarget, new Object[0]);
        }
        this.fNext.evaluate();
    }
}

