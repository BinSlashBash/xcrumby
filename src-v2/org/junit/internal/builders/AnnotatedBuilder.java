/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.builders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class AnnotatedBuilder
extends RunnerBuilder {
    private static final String CONSTRUCTOR_ERROR_FORMAT = "Custom runner class %s should have a public constructor with signature %s(Class testClass)";
    private RunnerBuilder fSuiteBuilder;

    public AnnotatedBuilder(RunnerBuilder runnerBuilder) {
        this.fSuiteBuilder = runnerBuilder;
    }

    public Runner buildRunner(Class<? extends Runner> object, Class<?> object2) throws Exception {
        try {
            Runner runner = object.getConstructor(Class.class).newInstance(object2);
            return runner;
        }
        catch (NoSuchMethodException var3_5) {
            try {
                object2 = object.getConstructor(Class.class, RunnerBuilder.class).newInstance(object2, this.fSuiteBuilder);
                return object2;
            }
            catch (NoSuchMethodException var2_3) {
                object = object.getSimpleName();
                throw new InitializationError(String.format("Custom runner class %s should have a public constructor with signature %s(Class testClass)", object, object));
            }
        }
    }

    @Override
    public Runner runnerForClass(Class<?> class_) throws Exception {
        RunWith runWith = (RunWith)class_.getAnnotation(RunWith.class);
        if (runWith != null) {
            return this.buildRunner(runWith.value(), class_);
        }
        return null;
    }
}

