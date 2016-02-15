package junit.extensions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class ActiveTestSuite extends TestSuite {
    private volatile int fActiveTestDeathCount;

    /* renamed from: junit.extensions.ActiveTestSuite.1 */
    class C06661 extends Thread {
        final /* synthetic */ TestResult val$result;
        final /* synthetic */ Test val$test;

        C06661(Test test, TestResult testResult) {
            this.val$test = test;
            this.val$result = testResult;
        }

        public void run() {
            try {
                this.val$test.run(this.val$result);
            } finally {
                ActiveTestSuite.this.runFinished();
            }
        }
    }

    public ActiveTestSuite(Class<? extends TestCase> theClass) {
        super((Class) theClass);
    }

    public ActiveTestSuite(String name) {
        super(name);
    }

    public ActiveTestSuite(Class<? extends TestCase> theClass, String name) {
        super((Class) theClass, name);
    }

    public void run(TestResult result) {
        this.fActiveTestDeathCount = 0;
        super.run(result);
        waitUntilFinished();
    }

    public void runTest(Test test, TestResult result) {
        new C06661(test, result).start();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    synchronized void waitUntilFinished() {
        /*
        r3 = this;
        monitor-enter(r3);
    L_0x0001:
        r1 = r3.fActiveTestDeathCount;	 Catch:{ all -> 0x0010 }
        r2 = r3.testCount();	 Catch:{ all -> 0x0010 }
        if (r1 >= r2) goto L_0x000e;
    L_0x0009:
        r3.wait();	 Catch:{ InterruptedException -> 0x000d }
        goto L_0x0001;
    L_0x000d:
        r0 = move-exception;
    L_0x000e:
        monitor-exit(r3);
        return;
    L_0x0010:
        r1 = move-exception;
        monitor-exit(r3);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: junit.extensions.ActiveTestSuite.waitUntilFinished():void");
    }

    public synchronized void runFinished() {
        this.fActiveTestDeathCount++;
        notifyAll();
    }
}
