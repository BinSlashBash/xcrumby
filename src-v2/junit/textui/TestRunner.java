/*
 * Decompiled with CFR 0_110.
 */
package junit.textui;

import java.io.InputStream;
import java.io.PrintStream;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.runner.BaseTestRunner;
import junit.runner.Version;
import junit.textui.ResultPrinter;

public class TestRunner
extends BaseTestRunner {
    public static final int EXCEPTION_EXIT = 2;
    public static final int FAILURE_EXIT = 1;
    public static final int SUCCESS_EXIT = 0;
    private ResultPrinter fPrinter;

    public TestRunner() {
        this(System.out);
    }

    public TestRunner(PrintStream printStream) {
        this(new ResultPrinter(printStream));
    }

    public TestRunner(ResultPrinter resultPrinter) {
        this.fPrinter = resultPrinter;
    }

    public static void main(String[] arrstring) {
        TestRunner testRunner = new TestRunner();
        try {
            if (!testRunner.start(arrstring).wasSuccessful()) {
                System.exit(1);
            }
            System.exit(0);
            return;
        }
        catch (Exception var0_1) {
            System.err.println(var0_1.getMessage());
            System.exit(2);
            return;
        }
    }

    public static TestResult run(Test test) {
        return new TestRunner().doRun(test);
    }

    public static void run(Class<? extends TestCase> class_) {
        TestRunner.run(new TestSuite(class_));
    }

    public static void runAndWait(Test test) {
        new TestRunner().doRun(test, true);
    }

    protected TestResult createTestResult() {
        return new TestResult();
    }

    public TestResult doRun(Test test) {
        return this.doRun(test, false);
    }

    public TestResult doRun(Test test, boolean bl2) {
        TestResult testResult = this.createTestResult();
        testResult.addListener(this.fPrinter);
        long l2 = System.currentTimeMillis();
        test.run(testResult);
        long l3 = System.currentTimeMillis();
        this.fPrinter.print(testResult, l3 - l2);
        this.pause(bl2);
        return testResult;
    }

    protected void pause(boolean bl2) {
        if (!bl2) {
            return;
        }
        this.fPrinter.printWaitPrompt();
        try {
            System.in.read();
            return;
        }
        catch (Exception var2_2) {
            return;
        }
    }

    @Override
    protected void runFailed(String string2) {
        System.err.println(string2);
        System.exit(1);
    }

    protected TestResult runSingleMethod(String string2, String string3, boolean bl2) throws Exception {
        return this.doRun(TestSuite.createTest(this.loadSuiteClass(string2).asSubclass(TestCase.class), string3), bl2);
    }

    public void setPrinter(ResultPrinter resultPrinter) {
        this.fPrinter = resultPrinter;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public TestResult start(String[] object) throws Exception {
        Object object2 = "";
        Object object3 = "";
        boolean bl2 = false;
        for (int i2 = 0; i2 < object.length; ++i2) {
            if (object[i2].equals("-wait")) {
                bl2 = true;
                continue;
            }
            if (object[i2].equals("-c")) {
                object2 = this.extractClassName((String)object[++i2]);
                continue;
            }
            if (object[i2].equals("-m")) {
                object3 = object[++i2];
                int n2 = object3.lastIndexOf(46);
                object2 = object3.substring(0, n2);
                object3 = object3.substring(n2 + 1);
                continue;
            }
            if (object[i2].equals("-v")) {
                System.err.println("JUnit " + Version.id() + " by Kent Beck and Erich Gamma");
                continue;
            }
            object2 = object[i2];
        }
        if (object2.equals("")) {
            throw new Exception("Usage: TestRunner [-wait] testCaseName, where name is the name of the TestCase class");
        }
        try {
            if (object3.equals("")) return this.doRun(this.getTest((String)object2), bl2);
            return this.runSingleMethod((String)object2, (String)object3, bl2);
        }
        catch (Exception var1_2) {
            throw new Exception("Could not create and run test suite: " + var1_2);
        }
    }

    @Override
    public void testEnded(String string2) {
    }

    @Override
    public void testFailed(int n2, Test test, Throwable throwable) {
    }

    @Override
    public void testStarted(String string2) {
    }
}

