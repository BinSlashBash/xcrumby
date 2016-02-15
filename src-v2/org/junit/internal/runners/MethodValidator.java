/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.TestClass;

@Deprecated
public class MethodValidator {
    private final List<Throwable> fErrors = new ArrayList<Throwable>();
    private TestClass fTestClass;

    public MethodValidator(TestClass testClass) {
        this.fTestClass = testClass;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void validateTestMethods(Class<? extends Annotation> object, boolean bl2) {
        Iterator<Method> iterator = this.fTestClass.getAnnotatedMethods((Class<? extends Annotation>)object).iterator();
        while (iterator.hasNext()) {
            Method method = iterator.next();
            if (Modifier.isStatic(method.getModifiers()) != bl2) {
                object = bl2 ? "should" : "should not";
                this.fErrors.add(new Exception("Method " + method.getName() + "() " + (String)object + " be static"));
            }
            if (!Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
                this.fErrors.add(new Exception("Class " + method.getDeclaringClass().getName() + " should be public"));
            }
            if (!Modifier.isPublic(method.getModifiers())) {
                this.fErrors.add(new Exception("Method " + method.getName() + " should be public"));
            }
            if (method.getReturnType() != Void.TYPE) {
                this.fErrors.add(new Exception("Method " + method.getName() + " should be void"));
            }
            if (method.getParameterTypes().length == 0) continue;
            this.fErrors.add(new Exception("Method " + method.getName() + " should have no parameters"));
        }
        return;
    }

    public void assertValid() throws InitializationError {
        if (!this.fErrors.isEmpty()) {
            throw new InitializationError(this.fErrors);
        }
    }

    public void validateInstanceMethods() {
        this.validateTestMethods(After.class, false);
        this.validateTestMethods(Before.class, false);
        this.validateTestMethods(Test.class, false);
        if (this.fTestClass.getAnnotatedMethods(Test.class).size() == 0) {
            this.fErrors.add(new Exception("No runnable methods"));
        }
    }

    public List<Throwable> validateMethodsForDefaultRunner() {
        this.validateNoArgConstructor();
        this.validateStaticMethods();
        this.validateInstanceMethods();
        return this.fErrors;
    }

    public void validateNoArgConstructor() {
        try {
            this.fTestClass.getConstructor();
            return;
        }
        catch (Exception var1_1) {
            this.fErrors.add(new Exception("Test class should have public zero-argument constructor", var1_1));
            return;
        }
    }

    public void validateStaticMethods() {
        this.validateTestMethods(BeforeClass.class, true);
        this.validateTestMethods(AfterClass.class, true);
    }
}

