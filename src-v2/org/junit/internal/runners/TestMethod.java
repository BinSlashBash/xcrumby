/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.TestClass;

@Deprecated
public class TestMethod {
    private final Method fMethod;
    private TestClass fTestClass;

    public TestMethod(Method method, TestClass testClass) {
        this.fMethod = method;
        this.fTestClass = testClass;
    }

    boolean expectsException() {
        if (this.getExpectedException() != null) {
            return true;
        }
        return false;
    }

    List<Method> getAfters() {
        return this.fTestClass.getAnnotatedMethods(After.class);
    }

    List<Method> getBefores() {
        return this.fTestClass.getAnnotatedMethods(Before.class);
    }

    protected Class<? extends Throwable> getExpectedException() {
        Test test = (Test)this.fMethod.getAnnotation(Test.class);
        if (test == null || test.expected() == Test.None.class) {
            return null;
        }
        return test.expected();
    }

    public long getTimeout() {
        Test test = (Test)this.fMethod.getAnnotation(Test.class);
        if (test == null) {
            return 0;
        }
        return test.timeout();
    }

    public void invoke(Object object) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        this.fMethod.invoke(object, new Object[0]);
    }

    public boolean isIgnored() {
        if (this.fMethod.getAnnotation(Ignore.class) != null) {
            return true;
        }
        return false;
    }

    boolean isUnexpected(Throwable throwable) {
        if (!this.getExpectedException().isAssignableFrom(throwable.getClass())) {
            return true;
        }
        return false;
    }
}

