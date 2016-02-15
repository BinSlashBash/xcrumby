/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runner;

import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class Computer {
    public static Computer serial() {
        return new Computer();
    }

    protected Runner getRunner(RunnerBuilder runnerBuilder, Class<?> class_) throws Throwable {
        return runnerBuilder.runnerForClass(class_);
    }

    public Runner getSuite(final RunnerBuilder runnerBuilder, Class<?>[] arrclass) throws InitializationError {
        return new Suite(new RunnerBuilder(){

            @Override
            public Runner runnerForClass(Class<?> class_) throws Throwable {
                return Computer.this.getRunner(runnerBuilder, class_);
            }
        }, arrclass);
    }

}

