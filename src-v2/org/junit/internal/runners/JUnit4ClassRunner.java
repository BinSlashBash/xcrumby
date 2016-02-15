/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.junit.internal.runners.ClassRoadie;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.MethodRoadie;
import org.junit.internal.runners.MethodValidator;
import org.junit.internal.runners.TestClass;
import org.junit.internal.runners.TestMethod;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

@Deprecated
public class JUnit4ClassRunner
extends Runner
implements Filterable,
Sortable {
    private TestClass fTestClass;
    private final List<Method> fTestMethods;

    public JUnit4ClassRunner(Class<?> class_) throws InitializationError {
        this.fTestClass = new TestClass(class_);
        this.fTestMethods = this.getTestMethods();
        this.validate();
    }

    private void testAborted(RunNotifier runNotifier, Description description, Throwable throwable) {
        runNotifier.fireTestStarted(description);
        runNotifier.fireTestFailure(new Failure(description, throwable));
        runNotifier.fireTestFinished(description);
    }

    protected Annotation[] classAnnotations() {
        return this.fTestClass.getJavaClass().getAnnotations();
    }

    protected Object createTest() throws Exception {
        return this.getTestClass().getConstructor().newInstance(new Object[0]);
    }

    @Override
    public void filter(Filter filter) throws NoTestsRemainException {
        Iterator<Method> iterator = this.fTestMethods.iterator();
        while (iterator.hasNext()) {
            if (filter.shouldRun(this.methodDescription(iterator.next()))) continue;
            iterator.remove();
        }
        if (this.fTestMethods.isEmpty()) {
            throw new NoTestsRemainException();
        }
    }

    @Override
    public Description getDescription() {
        Description description = Description.createSuiteDescription(this.getName(), this.classAnnotations());
        Iterator<Method> iterator = this.fTestMethods.iterator();
        while (iterator.hasNext()) {
            description.addChild(this.methodDescription(iterator.next()));
        }
        return description;
    }

    protected String getName() {
        return this.getTestClass().getName();
    }

    protected TestClass getTestClass() {
        return this.fTestClass;
    }

    protected List<Method> getTestMethods() {
        return this.fTestClass.getTestMethods();
    }

    protected void invokeTestMethod(Method method, RunNotifier runNotifier) {
        Description description = this.methodDescription(method);
        try {
            Object object = this.createTest();
            new MethodRoadie(object, this.wrapMethod(method), runNotifier, description).run();
            return;
        }
        catch (InvocationTargetException var1_2) {
            this.testAborted(runNotifier, description, var1_2.getCause());
            return;
        }
        catch (Exception var1_3) {
            this.testAborted(runNotifier, description, var1_3);
            return;
        }
    }

    protected Description methodDescription(Method method) {
        return Description.createTestDescription(this.getTestClass().getJavaClass(), this.testName(method), this.testAnnotations(method));
    }

    @Override
    public void run(final RunNotifier runNotifier) {
        new ClassRoadie(runNotifier, this.fTestClass, this.getDescription(), new Runnable(){

            @Override
            public void run() {
                JUnit4ClassRunner.this.runMethods(runNotifier);
            }
        }).runProtected();
    }

    protected void runMethods(RunNotifier runNotifier) {
        Iterator<Method> iterator = this.fTestMethods.iterator();
        while (iterator.hasNext()) {
            this.invokeTestMethod(iterator.next(), runNotifier);
        }
    }

    @Override
    public void sort(final Sorter sorter) {
        Collections.sort(this.fTestMethods, new Comparator<Method>(){

            @Override
            public int compare(Method method, Method method2) {
                return sorter.compare(JUnit4ClassRunner.this.methodDescription(method), JUnit4ClassRunner.this.methodDescription(method2));
            }
        });
    }

    protected Annotation[] testAnnotations(Method method) {
        return method.getAnnotations();
    }

    protected String testName(Method method) {
        return method.getName();
    }

    protected void validate() throws InitializationError {
        MethodValidator methodValidator = new MethodValidator(this.fTestClass);
        methodValidator.validateMethodsForDefaultRunner();
        methodValidator.assertValid();
    }

    protected TestMethod wrapMethod(Method method) {
        return new TestMethod(method, this.fTestClass);
    }

}

