/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.internal.runners.InitializationError;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class ErrorReportingRunner
extends Runner {
    private final List<Throwable> fCauses;
    private final Class<?> fTestClass;

    public ErrorReportingRunner(Class<?> class_, Throwable throwable) {
        this.fTestClass = class_;
        this.fCauses = this.getCauses(throwable);
    }

    private Description describeCause(Throwable throwable) {
        return Description.createTestDescription(this.fTestClass, "initializationError");
    }

    private List<Throwable> getCauses(Throwable throwable) {
        if (throwable instanceof InvocationTargetException) {
            return this.getCauses(throwable.getCause());
        }
        if (throwable instanceof org.junit.runners.model.InitializationError) {
            return ((org.junit.runners.model.InitializationError)throwable).getCauses();
        }
        if (throwable instanceof InitializationError) {
            return ((InitializationError)throwable).getCauses();
        }
        return Arrays.asList(throwable);
    }

    private void runCause(Throwable throwable, RunNotifier runNotifier) {
        Description description = this.describeCause(throwable);
        runNotifier.fireTestStarted(description);
        runNotifier.fireTestFailure(new Failure(description, throwable));
        runNotifier.fireTestFinished(description);
    }

    @Override
    public Description getDescription() {
        Description description = Description.createSuiteDescription(this.fTestClass);
        Iterator<Throwable> iterator = this.fCauses.iterator();
        while (iterator.hasNext()) {
            description.addChild(this.describeCause(iterator.next()));
        }
        return description;
    }

    @Override
    public void run(RunNotifier runNotifier) {
        Iterator<Throwable> iterator = this.fCauses.iterator();
        while (iterator.hasNext()) {
            this.runCause(iterator.next(), runNotifier);
        }
    }
}

