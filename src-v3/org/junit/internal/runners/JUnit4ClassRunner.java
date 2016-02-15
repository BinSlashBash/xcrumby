package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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
public class JUnit4ClassRunner extends Runner implements Filterable, Sortable {
    private TestClass fTestClass;
    private final List<Method> fTestMethods;

    /* renamed from: org.junit.internal.runners.JUnit4ClassRunner.1 */
    class C06981 implements Runnable {
        final /* synthetic */ RunNotifier val$notifier;

        C06981(RunNotifier runNotifier) {
            this.val$notifier = runNotifier;
        }

        public void run() {
            JUnit4ClassRunner.this.runMethods(this.val$notifier);
        }
    }

    /* renamed from: org.junit.internal.runners.JUnit4ClassRunner.2 */
    class C06992 implements Comparator<Method> {
        final /* synthetic */ Sorter val$sorter;

        C06992(Sorter sorter) {
            this.val$sorter = sorter;
        }

        public int compare(Method o1, Method o2) {
            return this.val$sorter.compare(JUnit4ClassRunner.this.methodDescription(o1), JUnit4ClassRunner.this.methodDescription(o2));
        }
    }

    public JUnit4ClassRunner(Class<?> klass) throws InitializationError {
        this.fTestClass = new TestClass(klass);
        this.fTestMethods = getTestMethods();
        validate();
    }

    protected List<Method> getTestMethods() {
        return this.fTestClass.getTestMethods();
    }

    protected void validate() throws InitializationError {
        MethodValidator methodValidator = new MethodValidator(this.fTestClass);
        methodValidator.validateMethodsForDefaultRunner();
        methodValidator.assertValid();
    }

    public void run(RunNotifier notifier) {
        new ClassRoadie(notifier, this.fTestClass, getDescription(), new C06981(notifier)).runProtected();
    }

    protected void runMethods(RunNotifier notifier) {
        for (Method method : this.fTestMethods) {
            invokeTestMethod(method, notifier);
        }
    }

    public Description getDescription() {
        Description spec = Description.createSuiteDescription(getName(), classAnnotations());
        for (Method method : this.fTestMethods) {
            spec.addChild(methodDescription(method));
        }
        return spec;
    }

    protected Annotation[] classAnnotations() {
        return this.fTestClass.getJavaClass().getAnnotations();
    }

    protected String getName() {
        return getTestClass().getName();
    }

    protected Object createTest() throws Exception {
        return getTestClass().getConstructor().newInstance(new Object[0]);
    }

    protected void invokeTestMethod(Method method, RunNotifier notifier) {
        Description description = methodDescription(method);
        try {
            new MethodRoadie(createTest(), wrapMethod(method), notifier, description).run();
        } catch (InvocationTargetException e) {
            testAborted(notifier, description, e.getCause());
        } catch (Exception e2) {
            testAborted(notifier, description, e2);
        }
    }

    private void testAborted(RunNotifier notifier, Description description, Throwable e) {
        notifier.fireTestStarted(description);
        notifier.fireTestFailure(new Failure(description, e));
        notifier.fireTestFinished(description);
    }

    protected TestMethod wrapMethod(Method method) {
        return new TestMethod(method, this.fTestClass);
    }

    protected String testName(Method method) {
        return method.getName();
    }

    protected Description methodDescription(Method method) {
        return Description.createTestDescription(getTestClass().getJavaClass(), testName(method), testAnnotations(method));
    }

    protected Annotation[] testAnnotations(Method method) {
        return method.getAnnotations();
    }

    public void filter(Filter filter) throws NoTestsRemainException {
        Iterator<Method> iter = this.fTestMethods.iterator();
        while (iter.hasNext()) {
            if (!filter.shouldRun(methodDescription((Method) iter.next()))) {
                iter.remove();
            }
        }
        if (this.fTestMethods.isEmpty()) {
            throw new NoTestsRemainException();
        }
    }

    public void sort(Sorter sorter) {
        Collections.sort(this.fTestMethods, new C06992(sorter));
    }

    protected TestClass getTestClass() {
        return this.fTestClass;
    }
}
