/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.runners;

import org.junit.runners.Suite;
import org.junit.runners.model.RunnerBuilder;

public class Enclosed
extends Suite {
    public Enclosed(Class<?> class_, RunnerBuilder runnerBuilder) throws Throwable {
        super(runnerBuilder, class_, class_.getClasses());
    }
}

