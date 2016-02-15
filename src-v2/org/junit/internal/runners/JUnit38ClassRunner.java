/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import junit.extensions.TestDecorator;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class JUnit38ClassRunner
extends Runner
implements Filterable,
Sortable {
    private Test fTest;

    public JUnit38ClassRunner(Class<?> class_) {
        this(new TestSuite(class_.asSubclass(TestCase.class)));
    }

    public JUnit38ClassRunner(Test test) {
        this.setTest(test);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String createSuiteDescription(TestSuite object) {
        int n2 = object.countTestCases();
        if (n2 == 0) {
            object = "";
            do {
                return String.format("TestSuite with %s tests%s", n2, object);
                break;
            } while (true);
        }
        object = String.format(" [example: %s]", object.testAt(0));
        return String.format("TestSuite with %s tests%s", n2, object);
    }

    private Test getTest() {
        return this.fTest;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Description makeDescription(Test object) {
        if (object instanceof TestCase) {
            object = (TestCase)object;
            return Description.createTestDescription(object.getClass(), object.getName());
        }
        if (object instanceof TestSuite) {
            TestSuite testSuite = (TestSuite)object;
            object = testSuite.getName() == null ? JUnit38ClassRunner.createSuiteDescription(testSuite) : testSuite.getName();
            Description description = Description.createSuiteDescription((String)object, new Annotation[0]);
            int n2 = testSuite.testCount();
            int n3 = 0;
            do {
                object = description;
                if (n3 >= n2) return object;
                description.addChild(JUnit38ClassRunner.makeDescription(testSuite.testAt(n3)));
                ++n3;
            } while (true);
        }
        if (object instanceof Describable) {
            return ((Describable)object).getDescription();
        }
        if (!(object instanceof TestDecorator)) return Description.createSuiteDescription(object.getClass());
        return JUnit38ClassRunner.makeDescription(((TestDecorator)object).getTest());
    }

    private void setTest(Test test) {
        this.fTest = test;
    }

    public TestListener createAdaptingListener(RunNotifier runNotifier) {
        return new OldTestClassAdaptingListener(runNotifier);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void filter(Filter filter) throws NoTestsRemainException {
        if (this.getTest() instanceof Filterable) {
            ((Filterable)((Object)this.getTest())).filter(filter);
            return;
        } else {
            if (!(this.getTest() instanceof TestSuite)) return;
            {
                TestSuite testSuite = (TestSuite)this.getTest();
                TestSuite testSuite2 = new TestSuite(testSuite.getName());
                int n2 = testSuite.testCount();
                int n3 = 0;
                do {
                    if (n3 >= n2) {
                        this.setTest(testSuite2);
                        return;
                    }
                    Test test = testSuite.testAt(n3);
                    if (filter.shouldRun(JUnit38ClassRunner.makeDescription(test))) {
                        testSuite2.addTest(test);
                    }
                    ++n3;
                } while (true);
            }
        }
    }

    @Override
    public Description getDescription() {
        return JUnit38ClassRunner.makeDescription(this.getTest());
    }

    @Override
    public void run(RunNotifier runNotifier) {
        TestResult testResult = new TestResult();
        testResult.addListener(this.createAdaptingListener(runNotifier));
        this.getTest().run(testResult);
    }

    @Override
    public void sort(Sorter sorter) {
        if (this.getTest() instanceof Sortable) {
            ((Sortable)((Object)this.getTest())).sort(sorter);
        }
    }

    private final class OldTestClassAdaptingListener
    implements TestListener {
        private final RunNotifier fNotifier;

        private OldTestClassAdaptingListener(RunNotifier runNotifier) {
            this.fNotifier = runNotifier;
        }

        private Description asDescription(Test test) {
            if (test instanceof Describable) {
                return ((Describable)((Object)test)).getDescription();
            }
            return Description.createTestDescription(this.getEffectiveClass(test), this.getName(test));
        }

        private Class<? extends Test> getEffectiveClass(Test test) {
            return test.getClass();
        }

        private String getName(Test test) {
            if (test instanceof TestCase) {
                return ((TestCase)test).getName();
            }
            return test.toString();
        }

        @Override
        public void addError(Test object, Throwable throwable) {
            object = new Failure(this.asDescription((Test)object), throwable);
            this.fNotifier.fireTestFailure((Failure)object);
        }

        @Override
        public void addFailure(Test test, AssertionFailedError assertionFailedError) {
            this.addError(test, (Throwable)((Object)assertionFailedError));
        }

        @Override
        public void endTest(Test test) {
            this.fNotifier.fireTestFinished(this.asDescription(test));
        }

        @Override
        public void startTest(Test test) {
            this.fNotifier.fireTestStarted(this.asDescription(test));
        }
    }

}

