/*
 * Decompiled with CFR 0_110.
 */
package org.junit.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class ExternalResource
implements TestRule {
    private Statement statement(final Statement statement) {
        return new Statement(){

            @Override
            public void evaluate() throws Throwable {
                ExternalResource.this.before();
                try {
                    statement.evaluate();
                    return;
                }
                finally {
                    ExternalResource.this.after();
                }
            }
        };
    }

    protected void after() {
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        return this.statement(statement);
    }

    protected void before() throws Throwable {
    }

}

