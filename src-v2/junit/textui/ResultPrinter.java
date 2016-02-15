/*
 * Decompiled with CFR 0_110.
 */
package junit.textui;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Enumeration;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestFailure;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.runner.BaseTestRunner;

public class ResultPrinter
implements TestListener {
    int fColumn = 0;
    PrintStream fWriter;

    public ResultPrinter(PrintStream printStream) {
        this.fWriter = printStream;
    }

    @Override
    public void addError(Test test, Throwable throwable) {
        this.getWriter().print("E");
    }

    @Override
    public void addFailure(Test test, AssertionFailedError assertionFailedError) {
        this.getWriter().print("F");
    }

    protected String elapsedTimeAsString(long l2) {
        return NumberFormat.getInstance().format((double)l2 / 1000.0);
    }

    @Override
    public void endTest(Test test) {
    }

    public PrintStream getWriter() {
        return this.fWriter;
    }

    void print(TestResult testResult, long l2) {
        synchronized (this) {
            this.printHeader(l2);
            this.printErrors(testResult);
            this.printFailures(testResult);
            this.printFooter(testResult);
            return;
        }
    }

    public void printDefect(TestFailure testFailure, int n2) {
        this.printDefectHeader(testFailure, n2);
        this.printDefectTrace(testFailure);
    }

    protected void printDefectHeader(TestFailure testFailure, int n2) {
        this.getWriter().print("" + n2 + ") " + testFailure.failedTest());
    }

    protected void printDefectTrace(TestFailure testFailure) {
        this.getWriter().print(BaseTestRunner.getFilteredTrace(testFailure.trace()));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void printDefects(Enumeration<TestFailure> enumeration, int n2, String string2) {
        if (n2 == 0) {
            return;
        }
        if (n2 == 1) {
            this.getWriter().println("There was " + n2 + " " + string2 + ":");
        } else {
            this.getWriter().println("There were " + n2 + " " + string2 + "s:");
        }
        n2 = 1;
        while (enumeration.hasMoreElements()) {
            this.printDefect(enumeration.nextElement(), n2);
            ++n2;
        }
    }

    protected void printErrors(TestResult testResult) {
        this.printDefects(testResult.errors(), testResult.errorCount(), "error");
    }

    protected void printFailures(TestResult testResult) {
        this.printDefects(testResult.failures(), testResult.failureCount(), "failure");
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void printFooter(TestResult object) {
        if (object.wasSuccessful()) {
            this.getWriter().println();
            this.getWriter().print("OK");
            PrintStream printStream = this.getWriter();
            StringBuilder stringBuilder = new StringBuilder().append(" (").append(object.runCount()).append(" test");
            object = object.runCount() == 1 ? "" : "s";
            printStream.println(stringBuilder.append((String)object).append(")").toString());
        } else {
            this.getWriter().println();
            this.getWriter().println("FAILURES!!!");
            this.getWriter().println("Tests run: " + object.runCount() + ",  Failures: " + object.failureCount() + ",  Errors: " + object.errorCount());
        }
        this.getWriter().println();
    }

    protected void printHeader(long l2) {
        this.getWriter().println();
        this.getWriter().println("Time: " + this.elapsedTimeAsString(l2));
    }

    void printWaitPrompt() {
        this.getWriter().println();
        this.getWriter().println("<RETURN> to continue");
    }

    @Override
    public void startTest(Test test) {
        this.getWriter().print(".");
        int n2 = this.fColumn;
        this.fColumn = n2 + 1;
        if (n2 >= 40) {
            this.getWriter().println();
            this.fColumn = 0;
        }
    }
}

