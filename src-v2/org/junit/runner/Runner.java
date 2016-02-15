/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runner;

import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

public abstract class Runner
implements Describable {
    @Override
    public abstract Description getDescription();

    public abstract void run(RunNotifier var1);

    public int testCount() {
        return this.getDescription().testCount();
    }
}

