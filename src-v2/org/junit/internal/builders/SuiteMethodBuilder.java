/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.builders;

import java.lang.reflect.Method;
import org.junit.internal.runners.SuiteMethod;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class SuiteMethodBuilder
extends RunnerBuilder {
    public boolean hasSuiteMethod(Class<?> class_) {
        try {
            class_.getMethod("suite", new Class[0]);
            return true;
        }
        catch (NoSuchMethodException var1_2) {
            return false;
        }
    }

    @Override
    public Runner runnerForClass(Class<?> class_) throws Throwable {
        if (this.hasSuiteMethod(class_)) {
            return new SuiteMethod(class_);
        }
        return null;
    }
}

