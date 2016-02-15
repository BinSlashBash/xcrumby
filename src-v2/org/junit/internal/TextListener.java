/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import org.junit.internal.JUnitSystem;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TextListener
extends RunListener {
    private final PrintStream fWriter;

    public TextListener(PrintStream printStream) {
        this.fWriter = printStream;
    }

    public TextListener(JUnitSystem jUnitSystem) {
        this(jUnitSystem.out());
    }

    private PrintStream getWriter() {
        return this.fWriter;
    }

    protected String elapsedTimeAsString(long l2) {
        return NumberFormat.getInstance().format((double)l2 / 1000.0);
    }

    protected void printFailure(Failure failure, String string2) {
        this.getWriter().println(string2 + ") " + failure.getTestHeader());
        this.getWriter().print(failure.getTrace());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void printFailures(Result iterator) {
        if ((iterator = iterator.getFailures()).size() != 0) {
            if (iterator.size() == 1) {
                this.getWriter().println("There was " + iterator.size() + " failure:");
            } else {
                this.getWriter().println("There were " + iterator.size() + " failures:");
            }
            int n2 = 1;
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                this.printFailure((Failure)iterator.next(), "" + n2);
                ++n2;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void printFooter(Result object) {
        if (object.wasSuccessful()) {
            this.getWriter().println();
            this.getWriter().print("OK");
            PrintStream printStream = this.getWriter();
            StringBuilder stringBuilder = new StringBuilder().append(" (").append(object.getRunCount()).append(" test");
            object = object.getRunCount() == 1 ? "" : "s";
            printStream.println(stringBuilder.append((String)object).append(")").toString());
        } else {
            this.getWriter().println();
            this.getWriter().println("FAILURES!!!");
            this.getWriter().println("Tests run: " + object.getRunCount() + ",  Failures: " + object.getFailureCount());
        }
        this.getWriter().println();
    }

    protected void printHeader(long l2) {
        this.getWriter().println();
        this.getWriter().println("Time: " + this.elapsedTimeAsString(l2));
    }

    @Override
    public void testFailure(Failure failure) {
        this.fWriter.append('E');
    }

    @Override
    public void testIgnored(Description description) {
        this.fWriter.append('I');
    }

    @Override
    public void testRunFinished(Result result) {
        this.printHeader(result.getRunTime());
        this.printFailures(result);
        this.printFooter(result);
    }

    @Override
    public void testStarted(Description description) {
        this.fWriter.append('.');
    }
}

