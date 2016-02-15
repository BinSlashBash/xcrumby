/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import junit.framework.Test;
import org.junit.internal.runners.JUnit38ClassRunner;

public class SuiteMethod
extends JUnit38ClassRunner {
    public SuiteMethod(Class<?> class_) throws Throwable {
        super(SuiteMethod.testFromSuiteMethod(class_));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Test testFromSuiteMethod(Class<?> object) throws Throwable {
        try {
            Method method = object.getMethod("suite", new Class[0]);
            if (Modifier.isStatic(method.getModifiers())) return (Test)method.invoke(null, new Object[0]);
            throw new Exception(object.getName() + ".suite() must be static");
        }
        catch (InvocationTargetException var0_1) {
            throw var0_1.getCause();
        }
    }
}

