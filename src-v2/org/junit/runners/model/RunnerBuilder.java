/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.internal.runners.ErrorReportingRunner;
import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;

public abstract class RunnerBuilder {
    private final Set<Class<?>> parents = new HashSet();

    private List<Runner> runners(Class<?>[] arrclass) {
        ArrayList<Runner> arrayList = new ArrayList<Runner>();
        int n2 = arrclass.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            Runner runner = this.safeRunnerForClass(arrclass[i2]);
            if (runner == null) continue;
            arrayList.add(runner);
        }
        return arrayList;
    }

    Class<?> addParent(Class<?> class_) throws InitializationError {
        if (!this.parents.add(class_)) {
            throw new InitializationError(String.format("class '%s' (possibly indirectly) contains itself as a SuiteClass", class_.getName()));
        }
        return class_;
    }

    void removeParent(Class<?> class_) {
        this.parents.remove(class_);
    }

    public abstract Runner runnerForClass(Class<?> var1) throws Throwable;

    public List<Runner> runners(Class<?> class_, List<Class<?>> list) throws InitializationError {
        return this.runners(class_, list.toArray(new Class[0]));
    }

    public List<Runner> runners(Class<?> class_, Class<?>[] object) throws InitializationError {
        this.addParent(class_);
        try {
            object = this.runners((Class<?>[])object);
            return object;
        }
        finally {
            this.removeParent(class_);
        }
    }

    public Runner safeRunnerForClass(Class<?> class_) {
        try {
            Runner runner = this.runnerForClass(class_);
            return runner;
        }
        catch (Throwable var2_3) {
            return new ErrorReportingRunner(class_, var2_3);
        }
    }
}

