package org.junit.runner;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class Computer {

    /* renamed from: org.junit.runner.Computer.1 */
    class C12561 extends RunnerBuilder {
        final /* synthetic */ RunnerBuilder val$builder;

        C12561(RunnerBuilder runnerBuilder) {
            this.val$builder = runnerBuilder;
        }

        public Runner runnerForClass(Class<?> testClass) throws Throwable {
            return Computer.this.getRunner(this.val$builder, testClass);
        }
    }

    public static Computer serial() {
        return new Computer();
    }

    public Runner getSuite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
        return new Suite(new C12561(builder), (Class[]) classes);
    }

    protected Runner getRunner(RunnerBuilder builder, Class<?> testClass) throws Throwable {
        return builder.runnerForClass(testClass);
    }
}
