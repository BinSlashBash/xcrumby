/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.builders;

import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

public class NullBuilder
extends RunnerBuilder {
    @Override
    public Runner runnerForClass(Class<?> class_) throws Throwable {
        return null;
    }
}

