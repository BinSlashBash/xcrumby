package org.junit.runners;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
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

public abstract class ParentRunner<T> extends Runner implements Filterable, Sortable {
    private List<T> fFilteredChildren;
    private RunnerScheduler fScheduler;
    private Sorter fSorter;
    private final TestClass fTestClass;

    /* renamed from: org.junit.runners.ParentRunner.3 */
    class C07063 implements Runnable {
        final /* synthetic */ Object val$each;
        final /* synthetic */ RunNotifier val$notifier;

        C07063(Object obj, RunNotifier runNotifier) {
            this.val$each = obj;
            this.val$notifier = runNotifier;
        }

        public void run() {
            ParentRunner.this.runChild(this.val$each, this.val$notifier);
        }
    }

    /* renamed from: org.junit.runners.ParentRunner.4 */
    class C07074 implements Comparator<T> {
        C07074() {
        }

        public int compare(T o1, T o2) {
            return ParentRunner.this.fSorter.compare(ParentRunner.this.describeChild(o1), ParentRunner.this.describeChild(o2));
        }
    }

    /* renamed from: org.junit.runners.ParentRunner.1 */
    class C12691 implements RunnerScheduler {
        C12691() {
        }

        public void schedule(Runnable childStatement) {
            childStatement.run();
        }

        public void finished() {
        }
    }

    /* renamed from: org.junit.runners.ParentRunner.2 */
    class C12702 extends Statement {
        final /* synthetic */ RunNotifier val$notifier;

        C12702(RunNotifier runNotifier) {
            this.val$notifier = runNotifier;
        }

        public void evaluate() {
            ParentRunner.this.runChildren(this.val$notifier);
        }
    }

    protected abstract Description describeChild(T t);

    protected abstract List<T> getChildren();

    protected abstract void runChild(T t, RunNotifier runNotifier);

    protected ParentRunner(Class<?> testClass) throws InitializationError {
        this.fSorter = Sorter.NULL;
        this.fFilteredChildren = null;
        this.fScheduler = new C12691();
        this.fTestClass = new TestClass(testClass);
        validate();
    }

    protected void collectInitializationErrors(List<Throwable> errors) {
        validatePublicVoidNoArgMethods(BeforeClass.class, true, errors);
        validatePublicVoidNoArgMethods(AfterClass.class, true, errors);
        validateClassRules(errors);
    }

    protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors) {
        for (FrameworkMethod eachTestMethod : getTestClass().getAnnotatedMethods(annotation)) {
            eachTestMethod.validatePublicVoidNoArg(isStatic, errors);
        }
    }

    private void validateClassRules(List<Throwable> errors) {
        RuleFieldValidator.CLASS_RULE_VALIDATOR.validate(getTestClass(), errors);
        RuleFieldValidator.CLASS_RULE_METHOD_VALIDATOR.validate(getTestClass(), errors);
    }

    protected Statement classBlock(RunNotifier notifier) {
        return withClassRules(withAfterClasses(withBeforeClasses(childrenInvoker(notifier))));
    }

    protected Statement withBeforeClasses(Statement statement) {
        List<FrameworkMethod> befores = this.fTestClass.getAnnotatedMethods(BeforeClass.class);
        return befores.isEmpty() ? statement : new RunBefores(statement, befores, null);
    }

    protected Statement withAfterClasses(Statement statement) {
        List<FrameworkMethod> afters = this.fTestClass.getAnnotatedMethods(AfterClass.class);
        return afters.isEmpty() ? statement : new RunAfters(statement, afters, null);
    }

    private Statement withClassRules(Statement statement) {
        List<TestRule> classRules = classRules();
        return classRules.isEmpty() ? statement : new RunRules(statement, classRules, getDescription());
    }

    protected List<TestRule> classRules() {
        List<TestRule> result = this.fTestClass.getAnnotatedMethodValues(null, ClassRule.class, TestRule.class);
        result.addAll(this.fTestClass.getAnnotatedFieldValues(null, ClassRule.class, TestRule.class));
        return result;
    }

    protected Statement childrenInvoker(RunNotifier notifier) {
        return new C12702(notifier);
    }

    private void runChildren(RunNotifier notifier) {
        for (T each : getFilteredChildren()) {
            this.fScheduler.schedule(new C07063(each, notifier));
        }
        this.fScheduler.finished();
    }

    protected String getName() {
        return this.fTestClass.getName();
    }

    public final TestClass getTestClass() {
        return this.fTestClass;
    }

    protected final void runLeaf(Statement statement, Description description, RunNotifier notifier) {
        EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
        eachNotifier.fireTestStarted();
        try {
            statement.evaluate();
        } catch (AssumptionViolatedException e) {
            eachNotifier.addFailedAssumption(e);
        } catch (Throwable e2) {
            eachNotifier.addFailure(e2);
        } finally {
            eachNotifier.fireTestFinished();
        }
    }

    protected Annotation[] getRunnerAnnotations() {
        return this.fTestClass.getAnnotations();
    }

    public Description getDescription() {
        Description description = Description.createSuiteDescription(getName(), getRunnerAnnotations());
        for (T child : getFilteredChildren()) {
            description.addChild(describeChild(child));
        }
        return description;
    }

    public void run(RunNotifier notifier) {
        EachTestNotifier testNotifier = new EachTestNotifier(notifier, getDescription());
        try {
            classBlock(notifier).evaluate();
        } catch (AssumptionViolatedException e) {
            testNotifier.fireTestIgnored();
        } catch (StoppedByUserException e2) {
            throw e2;
        } catch (Throwable e3) {
            testNotifier.addFailure(e3);
        }
    }

    public void filter(Filter filter) throws NoTestsRemainException {
        Iterator<T> iter = getFilteredChildren().iterator();
        while (iter.hasNext()) {
            T each = iter.next();
            if (shouldRun(filter, each)) {
                try {
                    filter.apply(each);
                } catch (NoTestsRemainException e) {
                    iter.remove();
                }
            } else {
                iter.remove();
            }
        }
        if (getFilteredChildren().isEmpty()) {
            throw new NoTestsRemainException();
        }
    }

    public void sort(Sorter sorter) {
        this.fSorter = sorter;
        for (T each : getFilteredChildren()) {
            sortChild(each);
        }
        Collections.sort(getFilteredChildren(), comparator());
    }

    private void validate() throws InitializationError {
        List errors = new ArrayList();
        collectInitializationErrors(errors);
        if (!errors.isEmpty()) {
            throw new InitializationError(errors);
        }
    }

    private List<T> getFilteredChildren() {
        if (this.fFilteredChildren == null) {
            this.fFilteredChildren = new ArrayList(getChildren());
        }
        return this.fFilteredChildren;
    }

    private void sortChild(T child) {
        this.fSorter.apply(child);
    }

    private boolean shouldRun(Filter filter, T each) {
        return filter.shouldRun(describeChild(each));
    }

    private Comparator<? super T> comparator() {
        return new C07074();
    }

    public void setScheduler(RunnerScheduler scheduler) {
        this.fScheduler = scheduler;
    }
}
