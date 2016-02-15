package org.junit.rules;

import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

@Deprecated
public class TestWatchman implements MethodRule {

    /* renamed from: org.junit.rules.TestWatchman.1 */
    class C12541 extends Statement {
        final /* synthetic */ Statement val$base;
        final /* synthetic */ FrameworkMethod val$method;

        C12541(FrameworkMethod frameworkMethod, Statement statement) {
            this.val$method = frameworkMethod;
            this.val$base = statement;
        }

        public void evaluate() throws Throwable {
            TestWatchman.this.starting(this.val$method);
            try {
                this.val$base.evaluate();
                TestWatchman.this.succeeded(this.val$method);
                TestWatchman.this.finished(this.val$method);
            } catch (AssumptionViolatedException e) {
                throw e;
            } catch (Throwable th) {
                TestWatchman.this.finished(this.val$method);
            }
        }
    }

    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        return new C12541(method, base);
    }

    public void succeeded(FrameworkMethod method) {
    }

    public void failed(Throwable e, FrameworkMethod method) {
    }

    public void starting(FrameworkMethod method) {
    }

    public void finished(FrameworkMethod method) {
    }
}
