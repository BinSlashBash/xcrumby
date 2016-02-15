/*
 * Decompiled with CFR 0_110.
 */
package org.junit.rules;

import java.util.ArrayList;
import java.util.List;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public abstract class TestWatcher
implements TestRule {
    private void failedQuietly(Throwable throwable, Description description, List<Throwable> list) {
        try {
            this.failed(throwable, description);
            return;
        }
        catch (Throwable var1_2) {
            list.add(var1_2);
            return;
        }
    }

    private void finishedQuietly(Description description, List<Throwable> list) {
        try {
            this.finished(description);
            return;
        }
        catch (Throwable var1_2) {
            list.add(var1_2);
            return;
        }
    }

    private void skippedQuietly(AssumptionViolatedException assumptionViolatedException, Description description, List<Throwable> list) {
        try {
            this.skipped(assumptionViolatedException, description);
            return;
        }
        catch (Throwable var1_2) {
            list.add(var1_2);
            return;
        }
    }

    private void startingQuietly(Description description, List<Throwable> list) {
        try {
            this.starting(description);
            return;
        }
        catch (Throwable var1_2) {
            list.add(var1_2);
            return;
        }
    }

    private void succeededQuietly(Description description, List<Throwable> list) {
        try {
            this.succeeded(description);
            return;
        }
        catch (Throwable var1_2) {
            list.add(var1_2);
            return;
        }
    }

    @Override
    public Statement apply(final Statement statement, final Description description) {
        return new Statement(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void evaluate() throws Throwable {
                ArrayList<Throwable> arrayList;
                arrayList = new ArrayList<Throwable>();
                TestWatcher.this.startingQuietly(description, arrayList);
                try {
                    statement.evaluate();
                    TestWatcher.this.succeededQuietly(description, arrayList);
                }
                catch (AssumptionViolatedException var2_2) {
                    arrayList.add(var2_2);
                    TestWatcher.this.skippedQuietly(var2_2, description, arrayList);
                }
                catch (Throwable var2_3) {
                    arrayList.add(var2_3);
                    TestWatcher.this.failedQuietly(var2_3, description, arrayList);
                }
                finally {
                    TestWatcher.this.finishedQuietly(description, arrayList);
                }
                MultipleFailureException.assertEmpty(arrayList);
            }
        };
    }

    protected void failed(Throwable throwable, Description description) {
    }

    protected void finished(Description description) {
    }

    protected void skipped(AssumptionViolatedException assumptionViolatedException, Description description) {
    }

    protected void starting(Description description) {
    }

    protected void succeeded(Description description) {
    }

}

