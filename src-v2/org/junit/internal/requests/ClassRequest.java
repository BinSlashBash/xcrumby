/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.requests;

import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Request;
import org.junit.runner.Runner;

public class ClassRequest
extends Request {
    private final boolean fCanUseSuiteMethod;
    private Runner fRunner;
    private final Object fRunnerLock = new Object();
    private final Class<?> fTestClass;

    public ClassRequest(Class<?> class_) {
        this(class_, true);
    }

    public ClassRequest(Class<?> class_, boolean bl2) {
        this.fTestClass = class_;
        this.fCanUseSuiteMethod = bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Runner getRunner() {
        Object object = this.fRunnerLock;
        synchronized (object) {
            if (this.fRunner != null) return this.fRunner;
            this.fRunner = new AllDefaultPossibilitiesBuilder(this.fCanUseSuiteMethod).safeRunnerForClass(this.fTestClass);
            return this.fRunner;
        }
    }
}

