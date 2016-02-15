/*
 * Decompiled with CFR 0_110.
 */
package org.junit.rules;

import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class Timeout
implements TestRule {
    private final int fMillis;

    public Timeout(int n2) {
        this.fMillis = n2;
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        return new FailOnTimeout(statement, this.fMillis);
    }
}

