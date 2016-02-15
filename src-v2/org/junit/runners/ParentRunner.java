/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.internal.runners.rules.RuleFieldValidator;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerScheduler;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public abstract class ParentRunner<T>
extends Runner
implements Filterable,
Sortable {
    private List<T> fFilteredChildren = null;
    private RunnerScheduler fScheduler;
    private Sorter fSorter = Sorter.NULL;
    private final TestClass fTestClass;

    protected ParentRunner(Class<?> class_) throws InitializationError {
        this.fScheduler = new RunnerScheduler(){

            @Override
            public void finished() {
            }

            @Override
            public void schedule(Runnable runnable) {
                runnable.run();
            }
        };
        this.fTestClass = new TestClass(class_);
        this.validate();
    }

    private Comparator<? super T> comparator() {
        return new Comparator<T>(){

            @Override
            public int compare(T t2, T t3) {
                return ParentRunner.this.fSorter.compare(ParentRunner.this.describeChild(t2), ParentRunner.this.describeChild(t3));
            }
        };
    }

    private List<T> getFilteredChildren() {
        if (this.fFilteredChildren == null) {
            this.fFilteredChildren = new ArrayList<T>(this.getChildren());
        }
        return this.fFilteredChildren;
    }

    private void runChildren(final RunNotifier runNotifier) {
        for (final T t2 : this.getFilteredChildren()) {
            this.fScheduler.schedule(new Runnable(){

                @Override
                public void run() {
                    ParentRunner.this.runChild(t2, runNotifier);
                }
            });
        }
        this.fScheduler.finished();
    }

    private boolean shouldRun(Filter filter, T t2) {
        return filter.shouldRun(this.describeChild(t2));
    }

    private void sortChild(T t2) {
        this.fSorter.apply(t2);
    }

    private void validate() throws InitializationError {
        ArrayList<Throwable> arrayList = new ArrayList<Throwable>();
        this.collectInitializationErrors(arrayList);
        if (!arrayList.isEmpty()) {
            throw new InitializationError(arrayList);
        }
    }

    private void validateClassRules(List<Throwable> list) {
        RuleFieldValidator.CLASS_RULE_VALIDATOR.validate(this.getTestClass(), list);
        RuleFieldValidator.CLASS_RULE_METHOD_VALIDATOR.validate(this.getTestClass(), list);
    }

    private Statement withClassRules(Statement statement) {
        List<TestRule> list = this.classRules();
        if (list.isEmpty()) {
            return statement;
        }
        return new RunRules(statement, list, this.getDescription());
    }

    protected Statement childrenInvoker(final RunNotifier runNotifier) {
        return new Statement(){

            @Override
            public void evaluate() {
                ParentRunner.this.runChildren(runNotifier);
            }
        };
    }

    protected Statement classBlock(RunNotifier runNotifier) {
        return this.withClassRules(this.withAfterClasses(this.withBeforeClasses(this.childrenInvoker(runNotifier))));
    }

    protected List<TestRule> classRules() {
        List<TestRule> list = this.fTestClass.getAnnotatedMethodValues(null, ClassRule.class, TestRule.class);
        list.addAll(this.fTestClass.getAnnotatedFieldValues(null, ClassRule.class, TestRule.class));
        return list;
    }

    protected void collectInitializationErrors(List<Throwable> list) {
        this.validatePublicVoidNoArgMethods(BeforeClass.class, true, list);
        this.validatePublicVoidNoArgMethods(AfterClass.class, true, list);
        this.validateClassRules(list);
    }

    protected abstract Description describeChild(T var1);

    @Override
    public void filter(Filter filter) throws NoTestsRemainException {
        Iterator<T> iterator = this.getFilteredChildren().iterator();
        while (iterator.hasNext()) {
            T t2 = iterator.next();
            if (this.shouldRun(filter, t2)) {
                try {
                    filter.apply(t2);
                }
                catch (NoTestsRemainException var3_4) {
                    iterator.remove();
                }
                continue;
            }
            iterator.remove();
        }
        if (this.getFilteredChildren().isEmpty()) {
            throw new NoTestsRemainException();
        }
    }

    protected abstract List<T> getChildren();

    @Override
    public Description getDescription() {
        Description description = Description.createSuiteDescription(this.getName(), this.getRunnerAnnotations());
        Iterator<T> iterator = this.getFilteredChildren().iterator();
        while (iterator.hasNext()) {
            description.addChild(this.describeChild(iterator.next()));
        }
        return description;
    }

    protected String getName() {
        return this.fTestClass.getName();
    }

    protected Annotation[] getRunnerAnnotations() {
        return this.fTestClass.getAnnotations();
    }

    public final TestClass getTestClass() {
        return this.fTestClass;
    }

    @Override
    public void run(RunNotifier runNotifier) {
        EachTestNotifier eachTestNotifier = new EachTestNotifier(runNotifier, this.getDescription());
        try {
            this.classBlock(runNotifier).evaluate();
            return;
        }
        catch (AssumptionViolatedException var1_2) {
            eachTestNotifier.fireTestIgnored();
            return;
        }
        catch (StoppedByUserException var1_3) {
            throw var1_3;
        }
        catch (Throwable var1_4) {
            eachTestNotifier.addFailure(var1_4);
            return;
        }
    }

    protected abstract void runChild(T var1, RunNotifier var2);

    protected final void runLeaf(Statement statement, Description object, RunNotifier runNotifier) {
        object = new EachTestNotifier(runNotifier, (Description)object);
        object.fireTestStarted();
        try {
            statement.evaluate();
            return;
        }
        catch (AssumptionViolatedException var1_2) {
            object.addFailedAssumption(var1_2);
            return;
        }
        catch (Throwable var1_3) {
            object.addFailure(var1_3);
            return;
        }
        finally {
            object.fireTestFinished();
        }
    }

    public void setScheduler(RunnerScheduler runnerScheduler) {
        this.fScheduler = runnerScheduler;
    }

    @Override
    public void sort(Sorter object) {
        this.fSorter = object;
        object = this.getFilteredChildren().iterator();
        while (object.hasNext()) {
            this.sortChild(object.next());
        }
        Collections.sort(this.getFilteredChildren(), this.comparator());
    }

    protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> object, boolean bl2, List<Throwable> list) {
        object = this.getTestClass().getAnnotatedMethods((Class<? extends Annotation>)object).iterator();
        while (object.hasNext()) {
            ((FrameworkMethod)object.next()).validatePublicVoidNoArg(bl2, list);
        }
    }

    protected Statement withAfterClasses(Statement statement) {
        List<FrameworkMethod> list = this.fTestClass.getAnnotatedMethods(AfterClass.class);
        if (list.isEmpty()) {
            return statement;
        }
        return new RunAfters(statement, list, null);
    }

    protected Statement withBeforeClasses(Statement statement) {
        List<FrameworkMethod> list = this.fTestClass.getAnnotatedMethods(BeforeClass.class);
        if (list.isEmpty()) {
            return statement;
        }
        return new RunBefores(statement, list, null);
    }

}

