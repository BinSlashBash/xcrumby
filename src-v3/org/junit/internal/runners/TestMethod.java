package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Test.None;

@Deprecated
public class TestMethod {
    private final Method fMethod;
    private TestClass fTestClass;

    public TestMethod(Method method, TestClass testClass) {
        this.fMethod = method;
        this.fTestClass = testClass;
    }

    public boolean isIgnored() {
        return this.fMethod.getAnnotation(Ignore.class) != null;
    }

    public long getTimeout() {
        Test annotation = (Test) this.fMethod.getAnnotation(Test.class);
        if (annotation == null) {
            return 0;
        }
        return annotation.timeout();
    }

    protected Class<? extends Throwable> getExpectedException() {
        Test annotation = (Test) this.fMethod.getAnnotation(Test.class);
        if (annotation == null || annotation.expected() == None.class) {
            return null;
        }
        return annotation.expected();
    }

    boolean isUnexpected(Throwable exception) {
        return !getExpectedException().isAssignableFrom(exception.getClass());
    }

    boolean expectsException() {
        return getExpectedException() != null;
    }

    List<Method> getBefores() {
        return this.fTestClass.getAnnotatedMethods(Before.class);
    }

    List<Method> getAfters() {
        return this.fTestClass.getAnnotatedMethods(After.class);
    }

    public void invoke(Object test) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        this.fMethod.invoke(test, new Object[0]);
    }
}
