package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

@Deprecated
public class MethodRoadie {
    private final Description fDescription;
    private final RunNotifier fNotifier;
    private final Object fTest;
    private TestMethod fTestMethod;

    /* renamed from: org.junit.internal.runners.MethodRoadie.1 */
    class C07011 implements Runnable {
        final /* synthetic */ long val$timeout;

        /* renamed from: org.junit.internal.runners.MethodRoadie.1.1 */
        class C07001 implements Callable<Object> {
            C07001() {
            }

            public Object call() throws Exception {
                MethodRoadie.this.runTestMethod();
                return null;
            }
        }

        C07011(long j) {
            this.val$timeout = j;
        }

        public void run() {
            ExecutorService service = Executors.newSingleThreadExecutor();
            Future<Object> result = service.submit(new C07001());
            service.shutdown();
            try {
                if (!service.awaitTermination(this.val$timeout, TimeUnit.MILLISECONDS)) {
                    service.shutdownNow();
                }
                result.get(0, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                MethodRoadie.this.addFailure(new Exception(String.format("test timed out after %d milliseconds", new Object[]{Long.valueOf(this.val$timeout)})));
            } catch (Exception e2) {
                MethodRoadie.this.addFailure(e2);
            }
        }
    }

    /* renamed from: org.junit.internal.runners.MethodRoadie.2 */
    class C07022 implements Runnable {
        C07022() {
        }

        public void run() {
            MethodRoadie.this.runTestMethod();
        }
    }

    public MethodRoadie(Object test, TestMethod method, RunNotifier notifier, Description description) {
        this.fTest = test;
        this.fNotifier = notifier;
        this.fDescription = description;
        this.fTestMethod = method;
    }

    public void run() {
        if (this.fTestMethod.isIgnored()) {
            this.fNotifier.fireTestIgnored(this.fDescription);
            return;
        }
        this.fNotifier.fireTestStarted(this.fDescription);
        try {
            long timeout = this.fTestMethod.getTimeout();
            if (timeout > 0) {
                runWithTimeout(timeout);
            } else {
                runTest();
            }
            this.fNotifier.fireTestFinished(this.fDescription);
        } catch (Throwable th) {
            this.fNotifier.fireTestFinished(this.fDescription);
        }
    }

    private void runWithTimeout(long timeout) {
        runBeforesThenTestThenAfters(new C07011(timeout));
    }

    public void runTest() {
        runBeforesThenTestThenAfters(new C07022());
    }

    public void runBeforesThenTestThenAfters(Runnable test) {
        try {
            runBefores();
            test.run();
            runAfters();
        } catch (FailedBefore e) {
            runAfters();
        } catch (Exception e2) {
            throw new RuntimeException("test should never throw an exception to this level");
        } catch (Throwable th) {
            runAfters();
        }
    }

    protected void runTestMethod() {
        try {
            this.fTestMethod.invoke(this.fTest);
            if (this.fTestMethod.expectsException()) {
                addFailure(new AssertionError("Expected exception: " + this.fTestMethod.getExpectedException().getName()));
            }
        } catch (InvocationTargetException e) {
            Throwable actual = e.getTargetException();
            if (!(actual instanceof AssumptionViolatedException)) {
                if (!this.fTestMethod.expectsException()) {
                    addFailure(actual);
                } else if (this.fTestMethod.isUnexpected(actual)) {
                    addFailure(new Exception("Unexpected exception, expected<" + this.fTestMethod.getExpectedException().getName() + "> but was<" + actual.getClass().getName() + ">", actual));
                }
            }
        } catch (Throwable e2) {
            addFailure(e2);
        }
    }

    private void runBefores() throws FailedBefore {
        try {
            for (Method before : this.fTestMethod.getBefores()) {
                before.invoke(this.fTest, new Object[0]);
            }
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (AssumptionViolatedException e2) {
            throw new FailedBefore();
        } catch (Throwable e3) {
            addFailure(e3);
            FailedBefore failedBefore = new FailedBefore();
        }
    }

    private void runAfters() {
        for (Method after : this.fTestMethod.getAfters()) {
            try {
                after.invoke(this.fTest, new Object[0]);
            } catch (InvocationTargetException e) {
                addFailure(e.getTargetException());
            } catch (Throwable e2) {
                addFailure(e2);
            }
        }
    }

    protected void addFailure(Throwable e) {
        this.fNotifier.fireTestFailure(new Failure(this.fDescription, e));
    }
}
