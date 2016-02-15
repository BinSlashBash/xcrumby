/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.builders;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class IgnoredClassRunner
extends Runner {
    private final Class<?> fTestClass;

    public IgnoredClassRunner(Class<?> class_) {
        this.fTestClass = class_;
    }

    @Override
    public Description getDescription() {
        return Description.createSuiteDescription(this.fTestClass);
    }

    @Override
    public void run(RunNotifier runNotifier) {
        runNotifier.fireTestIgnored(this.getDescription());
    }
}

