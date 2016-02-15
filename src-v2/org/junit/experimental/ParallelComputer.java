/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental;

import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.runner.Computer;
import org.junit.runner.Runner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.RunnerScheduler;

public class ParallelComputer
extends Computer {
    private final boolean fClasses;
    private final boolean fMethods;

    public ParallelComputer(boolean bl2, boolean bl3) {
        this.fClasses = bl2;
        this.fMethods = bl3;
    }

    public static Computer classes() {
        return new ParallelComputer(true, false);
    }

    public static Computer methods() {
        return new ParallelComputer(false, true);
    }

    private static Runner parallelize(Runner runner) {
        if (runner instanceof ParentRunner) {
            ((ParentRunner)runner).setScheduler(new RunnerScheduler(){
                private final ExecutorService fService = Executors.newCachedThreadPool();

                @Override
                public void finished() {
                    try {
                        this.fService.shutdown();
                        this.fService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                        return;
                    }
                    catch (InterruptedException var1_1) {
                        var1_1.printStackTrace(System.err);
                        return;
                    }
                }

                @Override
                public void schedule(Runnable runnable) {
                    this.fService.submit(runnable);
                }
            });
        }
        return runner;
    }

    @Override
    protected Runner getRunner(RunnerBuilder object, Class<?> object2) throws Throwable {
        object = object2 = super.getRunner((RunnerBuilder)object, object2);
        if (this.fMethods) {
            object = ParallelComputer.parallelize((Runner)object2);
        }
        return object;
    }

    @Override
    public Runner getSuite(RunnerBuilder object, Class<?>[] object2) throws InitializationError {
        object = object2 = super.getSuite((RunnerBuilder)object, (Class<?>[])object2);
        if (this.fClasses) {
            object = ParallelComputer.parallelize((Runner)object2);
        }
        return object;
    }

}

