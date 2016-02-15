package org.junit.experimental;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.runner.Computer;
import org.junit.runner.Runner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.RunnerScheduler;

public class ParallelComputer extends Computer {
    private final boolean fClasses;
    private final boolean fMethods;

    /* renamed from: org.junit.experimental.ParallelComputer.1 */
    static class C12471 implements RunnerScheduler {
        private final ExecutorService fService;

        C12471() {
            this.fService = Executors.newCachedThreadPool();
        }

        public void schedule(Runnable childStatement) {
            this.fService.submit(childStatement);
        }

        public void finished() {
            try {
                this.fService.shutdown();
                this.fService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    public ParallelComputer(boolean classes, boolean methods) {
        this.fClasses = classes;
        this.fMethods = methods;
    }

    public static Computer classes() {
        return new ParallelComputer(true, false);
    }

    public static Computer methods() {
        return new ParallelComputer(false, true);
    }

    private static Runner parallelize(Runner runner) {
        if (runner instanceof ParentRunner) {
            ((ParentRunner) runner).setScheduler(new C12471());
        }
        return runner;
    }

    public Runner getSuite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
        Runner suite = super.getSuite(builder, classes);
        return this.fClasses ? parallelize(suite) : suite;
    }

    protected Runner getRunner(RunnerBuilder builder, Class<?> testClass) throws Throwable {
        Runner runner = super.getRunner(builder, testClass);
        return this.fMethods ? parallelize(runner) : runner;
    }
}
