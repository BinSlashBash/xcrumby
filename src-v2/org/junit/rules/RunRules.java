/*
 * Decompiled with CFR 0_110.
 */
package org.junit.rules;

import java.util.Iterator;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RunRules
extends Statement {
    private final Statement statement;

    public RunRules(Statement statement, Iterable<TestRule> iterable, Description description) {
        this.statement = RunRules.applyAll(statement, iterable, description);
    }

    private static Statement applyAll(Statement statement, Iterable<TestRule> object, Description description) {
        object = object.iterator();
        while (object.hasNext()) {
            statement = ((TestRule)object.next()).apply(statement, description);
        }
        return statement;
    }

    @Override
    public void evaluate() throws Throwable {
        this.statement.evaluate();
    }
}

