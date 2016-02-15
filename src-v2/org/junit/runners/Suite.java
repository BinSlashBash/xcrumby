/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class Suite
extends ParentRunner<Runner> {
    private final List<Runner> fRunners;

    protected Suite(Class<?> class_, List<Runner> list) throws InitializationError {
        super(class_);
        this.fRunners = list;
    }

    public Suite(Class<?> class_, RunnerBuilder runnerBuilder) throws InitializationError {
        this(runnerBuilder, class_, Suite.getAnnotatedClasses(class_));
    }

    protected Suite(Class<?> class_, Class<?>[] arrclass) throws InitializationError {
        this(new AllDefaultPossibilitiesBuilder(true), class_, arrclass);
    }

    protected Suite(RunnerBuilder runnerBuilder, Class<?> class_, Class<?>[] arrclass) throws InitializationError {
        this(class_, runnerBuilder.runners(class_, arrclass));
    }

    public Suite(RunnerBuilder runnerBuilder, Class<?>[] arrclass) throws InitializationError {
        this(null, runnerBuilder.runners(null, arrclass));
    }

    public static Runner emptySuite() {
        try {
            Suite suite = new Suite((Class)null, new Class[0]);
            return suite;
        }
        catch (InitializationError var0_1) {
            throw new RuntimeException("This shouldn't be possible");
        }
    }

    private static Class<?>[] getAnnotatedClasses(Class<?> class_) throws InitializationError {
        SuiteClasses suiteClasses = (SuiteClasses)class_.getAnnotation(SuiteClasses.class);
        if (suiteClasses == null) {
            throw new InitializationError(String.format("class '%s' must have a SuiteClasses annotation", class_.getName()));
        }
        return suiteClasses.value();
    }

    @Override
    protected Description describeChild(Runner runner) {
        return runner.getDescription();
    }

    @Override
    protected List<Runner> getChildren() {
        return this.fRunners;
    }

    @Override
    protected void runChild(Runner runner, RunNotifier runNotifier) {
        runner.run(runNotifier);
    }

    @Inherited
    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface SuiteClasses {
        public Class<?>[] value();
    }

}

