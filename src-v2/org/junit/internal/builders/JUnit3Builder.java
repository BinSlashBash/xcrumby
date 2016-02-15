/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.builders;

import junit.framework.TestCase;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class JUnit3Builder
extends RunnerBuilder {
    boolean isPre4Test(Class<?> class_) {
        return TestCase.class.isAssignableFrom(class_);
    }

    @Override
    public Runner runnerForClass(Class<?> class_) throws Throwable {
        if (this.isPre4Test(class_)) {
            return new JUnit38ClassRunner(class_);
        }
        return null;
    }
}

