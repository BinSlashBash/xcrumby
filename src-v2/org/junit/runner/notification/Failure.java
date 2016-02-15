/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runner.notification;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import org.junit.runner.Description;

public class Failure
implements Serializable {
    private static final long serialVersionUID = 1;
    private final Description fDescription;
    private final Throwable fThrownException;

    public Failure(Description description, Throwable throwable) {
        this.fThrownException = throwable;
        this.fDescription = description;
    }

    public Description getDescription() {
        return this.fDescription;
    }

    public Throwable getException() {
        return this.fThrownException;
    }

    public String getMessage() {
        return this.getException().getMessage();
    }

    public String getTestHeader() {
        return this.fDescription.getDisplayName();
    }

    public String getTrace() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        this.getException().printStackTrace(printWriter);
        return stringWriter.getBuffer().toString();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getTestHeader() + ": " + this.fThrownException.getMessage());
        return stringBuffer.toString();
    }
}

