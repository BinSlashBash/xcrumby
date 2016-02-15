/*
 * Decompiled with CFR 0_110.
 */
package junit.framework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import junit.framework.AssertionFailedError;
import junit.framework.Test;

public class TestFailure {
    protected Test fFailedTest;
    protected Throwable fThrownException;

    public TestFailure(Test test, Throwable throwable) {
        this.fFailedTest = test;
        this.fThrownException = throwable;
    }

    public String exceptionMessage() {
        return this.thrownException().getMessage();
    }

    public Test failedTest() {
        return this.fFailedTest;
    }

    public boolean isFailure() {
        return this.thrownException() instanceof AssertionFailedError;
    }

    public Throwable thrownException() {
        return this.fThrownException;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.fFailedTest + ": " + this.fThrownException.getMessage());
        return stringBuffer.toString();
    }

    public String trace() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        this.thrownException().printStackTrace(printWriter);
        return stringWriter.getBuffer().toString();
    }
}

