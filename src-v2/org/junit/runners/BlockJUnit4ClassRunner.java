/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.rules.RuleFieldValidator;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.internal.runners.statements.Fail;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.MethodRule;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public class BlockJUnit4ClassRunner
extends ParentRunner<FrameworkMethod> {
    public BlockJUnit4ClassRunner(Class<?> class_) throws InitializationError {
        super(class_);
    }

    private boolean expectsException(Test test) {
        if (this.getExpectedException(test) != null) {
            return true;
        }
        return false;
    }

    private Class<? extends Throwable> getExpectedException(Test test) {
        if (test == null || test.expected() == Test.None.class) {
            return null;
        }
        return test.expected();
    }

    private List<MethodRule> getMethodRules(Object object) {
        return this.rules(object);
    }

    private long getTimeout(Test test) {
        if (test == null) {
            return 0;
        }
        return test.timeout();
    }

    private boolean hasOneConstructor() {
        if (this.getTestClass().getJavaClass().getConstructors().length == 1) {
            return true;
        }
        return false;
    }

    private void validateMethods(List<Throwable> list) {
        RuleFieldValidator.RULE_METHOD_VALIDATOR.validate(this.getTestClass(), list);
    }

    private Statement withMethodRules(FrameworkMethod frameworkMethod, List<TestRule> list, Object object, Statement statement) {
        for (MethodRule methodRule : this.getMethodRules(object)) {
            if (list.contains(methodRule)) continue;
            statement = methodRule.apply(statement, frameworkMethod, object);
        }
        return statement;
    }

    private Statement withRules(FrameworkMethod frameworkMethod, Object object, Statement statement) {
        List<TestRule> list = this.getTestRules(object);
        return this.withTestRules(frameworkMethod, list, this.withMethodRules(frameworkMethod, list, object, statement));
    }

    private Statement withTestRules(FrameworkMethod frameworkMethod, List<TestRule> list, Statement statement) {
        if (list.isEmpty()) {
            return statement;
        }
        return new RunRules(statement, list, this.describeChild(frameworkMethod));
    }

    @Override
    protected void collectInitializationErrors(List<Throwable> list) {
        super.collectInitializationErrors(list);
        this.validateNoNonStaticInnerClass(list);
        this.validateConstructor(list);
        this.validateInstanceMethods(list);
        this.validateFields(list);
        this.validateMethods(list);
    }

    protected List<FrameworkMethod> computeTestMethods() {
        return this.getTestClass().getAnnotatedMethods(Test.class);
    }

    protected Object createTest() throws Exception {
        return this.getTestClass().getOnlyConstructor().newInstance(new Object[0]);
    }

    @Override
    protected Description describeChild(FrameworkMethod frameworkMethod) {
        return Description.createTestDescription(this.getTestClass().getJavaClass(), this.testName(frameworkMethod), frameworkMethod.getAnnotations());
    }

    @Override
    protected List<FrameworkMethod> getChildren() {
        return this.computeTestMethods();
    }

    protected List<TestRule> getTestRules(Object object) {
        List<TestRule> list = this.getTestClass().getAnnotatedMethodValues(object, Rule.class, TestRule.class);
        list.addAll(this.getTestClass().getAnnotatedFieldValues(object, Rule.class, TestRule.class));
        return list;
    }

    protected Statement methodBlock(FrameworkMethod frameworkMethod) {
        try {
            Object object = new ReflectiveCallable(){

                @Override
                protected Object runReflectiveCall() throws Throwable {
                    return BlockJUnit4ClassRunner.this.createTest();
                }
            }.run();
            return this.withRules(frameworkMethod, object, this.withAfters(frameworkMethod, object, this.withBefores(frameworkMethod, object, this.withPotentialTimeout(frameworkMethod, object, this.possiblyExpectingExceptions(frameworkMethod, object, this.methodInvoker(frameworkMethod, object))))));
        }
        catch (Throwable var1_2) {
            return new Fail(var1_2);
        }
    }

    protected Statement methodInvoker(FrameworkMethod frameworkMethod, Object object) {
        return new InvokeMethod(frameworkMethod, object);
    }

    @Deprecated
    protected Statement possiblyExpectingExceptions(FrameworkMethod object, Object object2, Statement statement) {
        object2 = (Test)object.getAnnotation(Test.class);
        object = statement;
        if (this.expectsException((Test)object2)) {
            object = new ExpectException(statement, this.getExpectedException((Test)object2));
        }
        return object;
    }

    protected List<MethodRule> rules(Object object) {
        return this.getTestClass().getAnnotatedFieldValues(object, Rule.class, MethodRule.class);
    }

    @Override
    protected void runChild(FrameworkMethod frameworkMethod, RunNotifier runNotifier) {
        Description description = this.describeChild(frameworkMethod);
        if (frameworkMethod.getAnnotation(Ignore.class) != null) {
            runNotifier.fireTestIgnored(description);
            return;
        }
        this.runLeaf(this.methodBlock(frameworkMethod), description, runNotifier);
    }

    protected String testName(FrameworkMethod frameworkMethod) {
        return frameworkMethod.getName();
    }

    protected void validateConstructor(List<Throwable> list) {
        this.validateOnlyOneConstructor(list);
        this.validateZeroArgConstructor(list);
    }

    protected void validateFields(List<Throwable> list) {
        RuleFieldValidator.RULE_VALIDATOR.validate(this.getTestClass(), list);
    }

    @Deprecated
    protected void validateInstanceMethods(List<Throwable> list) {
        this.validatePublicVoidNoArgMethods(After.class, false, list);
        this.validatePublicVoidNoArgMethods(Before.class, false, list);
        this.validateTestMethods(list);
        if (this.computeTestMethods().size() == 0) {
            list.add(new Exception("No runnable methods"));
        }
    }

    protected void validateNoNonStaticInnerClass(List<Throwable> list) {
        if (this.getTestClass().isANonStaticInnerClass()) {
            list.add(new Exception("The inner class " + this.getTestClass().getName() + " is not static."));
        }
    }

    protected void validateOnlyOneConstructor(List<Throwable> list) {
        if (!this.hasOneConstructor()) {
            list.add(new Exception("Test class should have exactly one public constructor"));
        }
    }

    protected void validateTestMethods(List<Throwable> list) {
        this.validatePublicVoidNoArgMethods(Test.class, false, list);
    }

    protected void validateZeroArgConstructor(List<Throwable> list) {
        if (!this.getTestClass().isANonStaticInnerClass() && this.hasOneConstructor() && this.getTestClass().getOnlyConstructor().getParameterTypes().length != 0) {
            list.add(new Exception("Test class should have exactly one public zero-argument constructor"));
        }
    }

    @Deprecated
    protected Statement withAfters(FrameworkMethod list, Object object, Statement statement) {
        list = this.getTestClass().getAnnotatedMethods(After.class);
        if (list.isEmpty()) {
            return statement;
        }
        return new RunAfters(statement, list, object);
    }

    @Deprecated
    protected Statement withBefores(FrameworkMethod list, Object object, Statement statement) {
        list = this.getTestClass().getAnnotatedMethods(Before.class);
        if (list.isEmpty()) {
            return statement;
        }
        return new RunBefores(statement, list, object);
    }

    @Deprecated
    protected Statement withPotentialTimeout(FrameworkMethod object, Object object2, Statement statement) {
        long l2 = this.getTimeout((Test)object.getAnnotation(Test.class));
        object = statement;
        if (l2 > 0) {
            object = new FailOnTimeout(statement, l2);
        }
        return object;
    }

}

