/*
 * Decompiled with CFR 0_110.
 */
package junit.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import junit.framework.AssertionFailedError;
import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestFailure;
import junit.framework.TestListener;

public class TestResult {
    protected List<TestFailure> fErrors = new ArrayList<TestFailure>();
    protected List<TestFailure> fFailures = new ArrayList<TestFailure>();
    protected List<TestListener> fListeners = new ArrayList<TestListener>();
    protected int fRunTests = 0;
    private boolean fStop = false;

    private List<TestListener> cloneListeners() {
        synchronized (this) {
            ArrayList<TestListener> arrayList = new ArrayList<TestListener>();
            arrayList.addAll(this.fListeners);
            return arrayList;
        }
    }

    public void addError(Test test, Throwable throwable) {
        synchronized (this) {
            this.fErrors.add(new TestFailure(test, throwable));
            Iterator<TestListener> iterator = this.cloneListeners().iterator();
            while (iterator.hasNext()) {
                iterator.next().addError(test, throwable);
            }
            return;
        }
    }

    public void addFailure(Test test, AssertionFailedError assertionFailedError) {
        synchronized (this) {
            this.fFailures.add(new TestFailure(test, (Throwable)((Object)assertionFailedError)));
            Iterator<TestListener> iterator = this.cloneListeners().iterator();
            while (iterator.hasNext()) {
                iterator.next().addFailure(test, assertionFailedError);
            }
            return;
        }
    }

    public void addListener(TestListener testListener) {
        synchronized (this) {
            this.fListeners.add(testListener);
            return;
        }
    }

    public void endTest(Test test) {
        Iterator<TestListener> iterator = this.cloneListeners().iterator();
        while (iterator.hasNext()) {
            iterator.next().endTest(test);
        }
    }

    public int errorCount() {
        synchronized (this) {
            int n2 = this.fErrors.size();
            return n2;
        }
    }

    public Enumeration<TestFailure> errors() {
        synchronized (this) {
            Enumeration<TestFailure> enumeration = Collections.enumeration(this.fErrors);
            return enumeration;
        }
    }

    public int failureCount() {
        synchronized (this) {
            int n2 = this.fFailures.size();
            return n2;
        }
    }

    public Enumeration<TestFailure> failures() {
        synchronized (this) {
            Enumeration<TestFailure> enumeration = Collections.enumeration(this.fFailures);
            return enumeration;
        }
    }

    public void removeListener(TestListener testListener) {
        synchronized (this) {
            this.fListeners.remove(testListener);
            return;
        }
    }

    protected void run(final TestCase testCase) {
        this.startTest(testCase);
        this.runProtected(testCase, new Protectable(){

            @Override
            public void protect() throws Throwable {
                testCase.runBare();
            }
        });
        this.endTest(testCase);
    }

    public int runCount() {
        synchronized (this) {
            int n2 = this.fRunTests;
            return n2;
        }
    }

    public void runProtected(Test test, Protectable protectable) {
        try {
            protectable.protect();
            return;
        }
        catch (AssertionFailedError var2_4) {
            this.addFailure(test, var2_4);
            return;
        }
        catch (ThreadDeath var1_2) {
            throw var1_2;
        }
        catch (Throwable var2_5) {
            this.addError(test, var2_5);
            return;
        }
    }

    public boolean shouldStop() {
        synchronized (this) {
            boolean bl2 = this.fStop;
            return bl2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startTest(Test test) {
        int n2 = test.countTestCases();
        synchronized (this) {
            this.fRunTests += n2;
        }
        Iterator<TestListener> iterator = this.cloneListeners().iterator();
        while (iterator.hasNext()) {
            iterator.next().startTest(test);
        }
        return;
    }

    public void stop() {
        synchronized (this) {
            this.fStop = true;
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean wasSuccessful() {
        synchronized (this) {
            if (this.failureCount() != 0) return false;
            int n2 = this.errorCount();
            if (n2 != 0) return false;
            return true;
        }
    }

}

