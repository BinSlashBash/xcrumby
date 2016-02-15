/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.FailedBefore;
import org.junit.internal.runners.TestMethod;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

@Deprecated
public class MethodRoadie {
    private final Description fDescription;
    private final RunNotifier fNotifier;
    private final Object fTest;
    private TestMethod fTestMethod;

    public MethodRoadie(Object object, TestMethod testMethod, RunNotifier runNotifier, Description description) {
        this.fTest = object;
        this.fNotifier = runNotifier;
        this.fDescription = description;
        this.fTestMethod = testMethod;
    }

    private void runAfters() {
        for (Method method : this.fTestMethod.getAfters()) {
            try {
                method.invoke(this.fTest, new Object[0]);
            }
            catch (InvocationTargetException var2_3) {
                this.addFailure(var2_3.getTargetException());
            }
            catch (Throwable var2_4) {
                this.addFailure(var2_4);
            }
        }
    }

    private void runBefores() throws FailedBefore {
        try {
            Iterator<Method> iterator = this.fTestMethod.getBefores().iterator();
            while (iterator.hasNext()) {
                iterator.next().invoke(this.fTest, new Object[0]);
            }
        }
        catch (InvocationTargetException var1_2) {
            try {
                throw var1_2.getTargetException();
            }
            catch (AssumptionViolatedException var1_3) {
                throw new FailedBefore();
            }
            catch (Throwable var1_4) {
                this.addFailure(var1_4);
                throw new FailedBefore();
            }
        }
    }

    private void runWithTimeout(final long l2) {
        this.runBeforesThenTestThenAfters(new Runnable(){

            @Override
            public void run() {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                Future future = executorService.submit(new Callable<Object>(){

                    @Override
                    public Object call() throws Exception {
                        MethodRoadie.this.runTestMethod();
                        return null;
                    }
                });
                executorService.shutdown();
                try {
                    if (!executorService.awaitTermination(l2, TimeUnit.MILLISECONDS)) {
                        executorService.shutdownNow();
                    }
                    future.get(0, TimeUnit.MILLISECONDS);
                    return;
                }
                catch (TimeoutException var1_2) {
                    MethodRoadie.this.addFailure(new Exception(String.format("test timed out after %d milliseconds", l2)));
                    return;
                }
                catch (Exception var1_3) {
                    MethodRoadie.this.addFailure(var1_3);
                    return;
                }
            }

        });
    }

    protected void addFailure(Throwable throwable) {
        this.fNotifier.fireTestFailure(new Failure(this.fDescription, throwable));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void run() {
        if (this.fTestMethod.isIgnored()) {
            this.fNotifier.fireTestIgnored(this.fDescription);
            return;
        }
        this.fNotifier.fireTestStarted(this.fDescription);
        try {
            long l2 = this.fTestMethod.getTimeout();
            if (l2 > 0) {
                this.runWithTimeout(l2);
                do {
                    return;
                    break;
                } while (true);
            }
            this.runTest();
            return;
        }
        finally {
            this.fNotifier.fireTestFinished(this.fDescription);
        }
    }

    public void runBeforesThenTestThenAfters(Runnable runnable) {
        try {
            this.runBefores();
            runnable.run();
            return;
        }
        catch (FailedBefore var1_2) {
            return;
        }
        catch (Exception var1_3) {
            throw new RuntimeException("test should never throw an exception to this level");
        }
        finally {
            this.runAfters();
        }
    }

    public void runTest() {
        this.runBeforesThenTestThenAfters(new Runnable(){

            @Override
            public void run() {
                MethodRoadie.this.runTestMethod();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void runTestMethod() {
        try {
            this.fTestMethod.invoke(this.fTest);
            if (!this.fTestMethod.expectsException()) return;
            {
                this.addFailure((Throwable)((Object)new AssertionError((Object)("Expected exception: " + this.fTestMethod.getExpectedException().getName()))));
                return;
            }
        }
        catch (InvocationTargetException var1_1) {
            Throwable throwable = var1_1.getTargetException();
            if (throwable instanceof AssumptionViolatedException) return;
            if (!this.fTestMethod.expectsException()) {
                this.addFailure(throwable);
                return;
            }
            if (!this.fTestMethod.isUnexpected(throwable)) return;
            this.addFailure(new Exception("Unexpected exception, expected<" + this.fTestMethod.getExpectedException().getName() + "> but was<" + throwable.getClass().getName() + ">", throwable));
            return;
        }
        catch (Throwable var1_3) {
            this.addFailure(var1_3);
            return;
        }
    }

}

