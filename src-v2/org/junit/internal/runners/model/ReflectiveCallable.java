/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners.model;

import java.lang.reflect.InvocationTargetException;

public abstract class ReflectiveCallable {
    public Object run() throws Throwable {
        try {
            Object object = this.runReflectiveCall();
            return object;
        }
        catch (InvocationTargetException var1_2) {
            throw var1_2.getTargetException();
        }
    }

    protected abstract Object runReflectiveCall() throws Throwable;
}

