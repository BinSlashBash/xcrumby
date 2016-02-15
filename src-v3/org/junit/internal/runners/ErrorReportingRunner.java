package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

public class ErrorReportingRunner extends Runner {
    private final List<Throwable> fCauses;
    private final Class<?> fTestClass;

    public ErrorReportingRunner(Class<?> testClass, Throwable cause) {
        this.fTestClass = testClass;
        this.fCauses = getCauses(cause);
    }

    public Description getDescription() {
        Description description = Description.createSuiteDescription(this.fTestClass);
        for (Throwable each : this.fCauses) {
            description.addChild(describeCause(each));
        }
        return description;
    }

    public void run(RunNotifier notifier) {
        for (Throwable each : this.fCauses) {
            runCause(each, notifier);
        }
    }

    private List<Throwable> getCauses(Throwable cause) {
        if (cause instanceof InvocationTargetException) {
            return getCauses(cause.getCause());
        }
        if (cause instanceof InitializationError) {
            return ((InitializationError) cause).getCauses();
        }
        if (cause instanceof InitializationError) {
            return ((InitializationError) cause).getCauses();
        }
        return Arrays.asList(new Throwable[]{cause});
    }

    private Description describeCause(Throwable child) {
        return Description.createTestDescription(this.fTestClass, "initializationError");
    }

    private void runCause(Throwable child, RunNotifier notifier) {
        Description description = describeCause(child);
        notifier.fireTestStarted(description);
        notifier.fireTestFailure(new Failure(description, child));
        notifier.fireTestFinished(description);
    }
}
