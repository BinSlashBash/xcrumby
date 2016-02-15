package junit.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class TestResult {
    protected List<TestFailure> fErrors;
    protected List<TestFailure> fFailures;
    protected List<TestListener> fListeners;
    protected int fRunTests;
    private boolean fStop;

    /* renamed from: junit.framework.TestResult.1 */
    class C12071 implements Protectable {
        final /* synthetic */ TestCase val$test;

        C12071(TestCase testCase) {
            this.val$test = testCase;
        }

        public void protect() throws Throwable {
            this.val$test.runBare();
        }
    }

    public TestResult() {
        this.fFailures = new ArrayList();
        this.fErrors = new ArrayList();
        this.fListeners = new ArrayList();
        this.fRunTests = 0;
        this.fStop = false;
    }

    public synchronized void addError(Test test, Throwable t) {
        this.fErrors.add(new TestFailure(test, t));
        for (TestListener each : cloneListeners()) {
            each.addError(test, t);
        }
    }

    public synchronized void addFailure(Test test, AssertionFailedError t) {
        this.fFailures.add(new TestFailure(test, t));
        for (TestListener each : cloneListeners()) {
            each.addFailure(test, t);
        }
    }

    public synchronized void addListener(TestListener listener) {
        this.fListeners.add(listener);
    }

    public synchronized void removeListener(TestListener listener) {
        this.fListeners.remove(listener);
    }

    private synchronized List<TestListener> cloneListeners() {
        List<TestListener> result;
        result = new ArrayList();
        result.addAll(this.fListeners);
        return result;
    }

    public void endTest(Test test) {
        for (TestListener each : cloneListeners()) {
            each.endTest(test);
        }
    }

    public synchronized int errorCount() {
        return this.fErrors.size();
    }

    public synchronized Enumeration<TestFailure> errors() {
        return Collections.enumeration(this.fErrors);
    }

    public synchronized int failureCount() {
        return this.fFailures.size();
    }

    public synchronized Enumeration<TestFailure> failures() {
        return Collections.enumeration(this.fFailures);
    }

    protected void run(TestCase test) {
        startTest(test);
        runProtected(test, new C12071(test));
        endTest(test);
    }

    public synchronized int runCount() {
        return this.fRunTests;
    }

    public void runProtected(Test test, Protectable p) {
        try {
            p.protect();
        } catch (AssertionFailedError e) {
            addFailure(test, e);
        } catch (ThreadDeath e2) {
            throw e2;
        } catch (Throwable e3) {
            addError(test, e3);
        }
    }

    public synchronized boolean shouldStop() {
        return this.fStop;
    }

    public void startTest(Test test) {
        int count = test.countTestCases();
        synchronized (this) {
            this.fRunTests += count;
        }
        for (TestListener each : cloneListeners()) {
            each.startTest(test);
        }
    }

    public synchronized void stop() {
        this.fStop = true;
    }

    public synchronized boolean wasSuccessful() {
        boolean z;
        z = failureCount() == 0 && errorCount() == 0;
        return z;
    }
}
