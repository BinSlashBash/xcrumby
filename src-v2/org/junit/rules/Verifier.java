/*
 * Decompiled with CFR 0_110.
 */
package org.junit.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class Verifier
implements TestRule {
    @Override
    public Statement apply(final Statement statement, Description description) {
        return new Statement(){

            @Override
            public void evaluate() throws Throwable {
                statement.evaluate();
                Verifier.this.verify();
            }
        };
    }

    protected void verify() throws Throwable {
    }

}

