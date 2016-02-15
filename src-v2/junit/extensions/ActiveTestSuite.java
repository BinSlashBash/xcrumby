/*
 * Decompiled with CFR 0_110.
 */
package junit.extensions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class ActiveTestSuite
extends TestSuite {
    private volatile int fActiveTestDeathCount;

    public ActiveTestSuite() {
    }

    public ActiveTestSuite(Class<? extends TestCase> class_) {
        super(class_);
    }

    public ActiveTestSuite(Class<? extends TestCase> class_, String string2) {
        super(class_, string2);
    }

    public ActiveTestSuite(String string2) {
        super(string2);
    }

    @Override
    public void run(TestResult testResult) {
        this.fActiveTestDeathCount = 0;
        super.run(testResult);
        this.waitUntilFinished();
    }

    public void runFinished() {
        synchronized (this) {
            ++this.fActiveTestDeathCount;
            this.notifyAll();
            return;
        }
    }

    @Override
    public void runTest(final Test test, final TestResult testResult) {
        new Thread(){

            @Override
            public void run() {
                try {
                    test.run(testResult);
                    return;
                }
                finally {
                    ActiveTestSuite.this.runFinished();
                }
            }
        }.start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void waitUntilFinished() {
        synchronized (this) {
            int n3;
            int n2;
            while ((n2 = this.fActiveTestDeathCount) < (n3 = this.testCount())) {
                try {
                    this.wait();
                    continue;
                }
                catch (InterruptedException var3_3) {
                    // empty catch block
                    break;
                }
            }
            return;
        }
    }

}

