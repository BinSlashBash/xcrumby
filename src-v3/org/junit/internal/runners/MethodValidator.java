package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@Deprecated
public class MethodValidator {
    private final List<Throwable> fErrors;
    private TestClass fTestClass;

    public MethodValidator(TestClass testClass) {
        this.fErrors = new ArrayList();
        this.fTestClass = testClass;
    }

    public void validateInstanceMethods() {
        validateTestMethods(After.class, false);
        validateTestMethods(Before.class, false);
        validateTestMethods(Test.class, false);
        if (this.fTestClass.getAnnotatedMethods(Test.class).size() == 0) {
            this.fErrors.add(new Exception("No runnable methods"));
        }
    }

    public void validateStaticMethods() {
        validateTestMethods(BeforeClass.class, true);
        validateTestMethods(AfterClass.class, true);
    }

    public List<Throwable> validateMethodsForDefaultRunner() {
        validateNoArgConstructor();
        validateStaticMethods();
        validateInstanceMethods();
        return this.fErrors;
    }

    public void assertValid() throws InitializationError {
        if (!this.fErrors.isEmpty()) {
            throw new InitializationError(this.fErrors);
        }
    }

    public void validateNoArgConstructor() {
        try {
            this.fTestClass.getConstructor();
        } catch (Exception e) {
            this.fErrors.add(new Exception("Test class should have public zero-argument constructor", e));
        }
    }

    private void validateTestMethods(Class<? extends Annotation> annotation, boolean isStatic) {
        for (Method each : this.fTestClass.getAnnotatedMethods(annotation)) {
            if (Modifier.isStatic(each.getModifiers()) != isStatic) {
                this.fErrors.add(new Exception("Method " + each.getName() + "() " + (isStatic ? "should" : "should not") + " be static"));
            }
            if (!Modifier.isPublic(each.getDeclaringClass().getModifiers())) {
                this.fErrors.add(new Exception("Class " + each.getDeclaringClass().getName() + " should be public"));
            }
            if (!Modifier.isPublic(each.getModifiers())) {
                this.fErrors.add(new Exception("Method " + each.getName() + " should be public"));
            }
            if (each.getReturnType() != Void.TYPE) {
                this.fErrors.add(new Exception("Method " + each.getName() + " should be void"));
            }
            if (each.getParameterTypes().length != 0) {
                this.fErrors.add(new Exception("Method " + each.getName() + " should have no parameters"));
            }
        }
    }
}
