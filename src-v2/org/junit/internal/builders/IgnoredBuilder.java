/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.builders;

import java.lang.annotation.Annotation;
import org.junit.Ignore;
import org.junit.internal.builders.IgnoredClassRunner;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class IgnoredBuilder
extends RunnerBuilder {
    @Override
    public Runner runnerForClass(Class<?> class_) {
        if (class_.getAnnotation(Ignore.class) != null) {
            return new IgnoredClassRunner(class_);
        }
        return null;
    }
}

