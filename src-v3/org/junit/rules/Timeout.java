package org.junit.rules;

import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class Timeout implements TestRule {
    private final int fMillis;

    public Timeout(int millis) {
        this.fMillis = millis;
    }

    public Statement apply(Statement base, Description description) {
        return new FailOnTimeout(base, (long) this.fMillis);
    }
}
