/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners.model;

import java.util.Iterator;
import java.util.List;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.MultipleFailureException;

public class EachTestNotifier {
    private final Description fDescription;
    private final RunNotifier fNotifier;

    public EachTestNotifier(RunNotifier runNotifier, Description description) {
        this.fNotifier = runNotifier;
        this.fDescription = description;
    }

    private void addMultipleFailureException(MultipleFailureException object) {
        object = object.getFailures().iterator();
        while (object.hasNext()) {
            this.addFailure((Throwable)object.next());
        }
    }

    public void addFailedAssumption(AssumptionViolatedException assumptionViolatedException) {
        this.fNotifier.fireTestAssumptionFailed(new Failure(this.fDescription, assumptionViolatedException));
    }

    public void addFailure(Throwable throwable) {
        if (throwable instanceof MultipleFailureException) {
            this.addMultipleFailureException((MultipleFailureException)throwable);
            return;
        }
        this.fNotifier.fireTestFailure(new Failure(this.fDescription, throwable));
    }

    public void fireTestFinished() {
        this.fNotifier.fireTestFinished(this.fDescription);
    }

    public void fireTestIgnored() {
        this.fNotifier.fireTestIgnored(this.fDescription);
    }

    public void fireTestStarted() {
        this.fNotifier.fireTestStarted(this.fDescription);
    }
}

