/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.builders;

import java.util.Arrays;
import java.util.Iterator;
import org.junit.internal.builders.AnnotatedBuilder;
import org.junit.internal.builders.IgnoredBuilder;
import org.junit.internal.builders.JUnit3Builder;
import org.junit.internal.builders.JUnit4Builder;
import org.junit.internal.builders.NullBuilder;
import org.junit.internal.builders.SuiteMethodBuilder;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class AllDefaultPossibilitiesBuilder
extends RunnerBuilder {
    private final boolean fCanUseSuiteMethod;

    public AllDefaultPossibilitiesBuilder(boolean bl2) {
        this.fCanUseSuiteMethod = bl2;
    }

    protected AnnotatedBuilder annotatedBuilder() {
        return new AnnotatedBuilder(this);
    }

    protected IgnoredBuilder ignoredBuilder() {
        return new IgnoredBuilder();
    }

    protected JUnit3Builder junit3Builder() {
        return new JUnit3Builder();
    }

    protected JUnit4Builder junit4Builder() {
        return new JUnit4Builder();
    }

    @Override
    public Runner runnerForClass(Class<?> class_) throws Throwable {
        Iterator<RunnerBuilder> iterator = Arrays.asList(this.ignoredBuilder(), this.annotatedBuilder(), this.suiteMethodBuilder(), this.junit3Builder(), this.junit4Builder()).iterator();
        while (iterator.hasNext()) {
            Runner runner = iterator.next().safeRunnerForClass(class_);
            if (runner == null) continue;
            return runner;
        }
        return null;
    }

    protected RunnerBuilder suiteMethodBuilder() {
        if (this.fCanUseSuiteMethod) {
            return new SuiteMethodBuilder();
        }
        return new NullBuilder();
    }
}

