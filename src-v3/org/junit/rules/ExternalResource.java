package org.junit.rules;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class ExternalResource implements TestRule {

    /* renamed from: org.junit.rules.ExternalResource.1 */
    class C12521 extends Statement {
        final /* synthetic */ Statement val$base;

        C12521(Statement statement) {
            this.val$base = statement;
        }

        public void evaluate() throws Throwable {
            ExternalResource.this.before();
            try {
                this.val$base.evaluate();
            } finally {
                ExternalResource.this.after();
            }
        }
    }

    public Statement apply(Statement base, Description description) {
        return statement(base);
    }

    private Statement statement(Statement base) {
        return new C12521(base);
    }

    protected void before() throws Throwable {
    }

    protected void after() {
    }
}
