package org.junit.internal.builders;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class IgnoredClassRunner extends Runner {
    private final Class<?> fTestClass;

    public IgnoredClassRunner(Class<?> testClass) {
        this.fTestClass = testClass;
    }

    public void run(RunNotifier notifier) {
        notifier.fireTestIgnored(getDescription());
    }

    public Description getDescription() {
        return Description.createSuiteDescription(this.fTestClass);
    }
}
