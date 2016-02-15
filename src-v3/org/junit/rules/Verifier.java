package org.junit.rules;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class Verifier implements TestRule {

    /* renamed from: org.junit.rules.Verifier.1 */
    class C12551 extends Statement {
        final /* synthetic */ Statement val$base;

        C12551(Statement statement) {
            this.val$base = statement;
        }

        public void evaluate() throws Throwable {
            this.val$base.evaluate();
            Verifier.this.verify();
        }
    }

    public Statement apply(Statement base, Description description) {
        return new C12551(base);
    }

    protected void verify() throws Throwable {
    }
}
