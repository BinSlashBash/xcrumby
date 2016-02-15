package org.junit.experimental.theories;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.experimental.theories.PotentialAssignment.CouldNotGenerateValueException;
import org.junit.experimental.theories.internal.Assignments;
import org.junit.experimental.theories.internal.ParameterizedAssertionError;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public class Theories extends BlockJUnit4ClassRunner {

    public static class TheoryAnchor extends Statement {
        private List<AssumptionViolatedException> fInvalidParameters;
        private TestClass fTestClass;
        private FrameworkMethod fTestMethod;
        private int successes;

        /* renamed from: org.junit.experimental.theories.Theories.TheoryAnchor.2 */
        class C12512 extends Statement {
            final /* synthetic */ Assignments val$complete;
            final /* synthetic */ Object val$freshInstance;
            final /* synthetic */ FrameworkMethod val$method;

            C12512(Assignments assignments, FrameworkMethod frameworkMethod, Object obj) {
                this.val$complete = assignments;
                this.val$method = frameworkMethod;
                this.val$freshInstance = obj;
            }

            public void evaluate() throws Throwable {
                try {
                    this.val$method.invokeExplosively(this.val$freshInstance, this.val$complete.getMethodArguments(TheoryAnchor.this.nullsOk()));
                } catch (CouldNotGenerateValueException e) {
                }
            }
        }

        /* renamed from: org.junit.experimental.theories.Theories.TheoryAnchor.1 */
        class C15521 extends BlockJUnit4ClassRunner {
            final /* synthetic */ Assignments val$complete;

            /* renamed from: org.junit.experimental.theories.Theories.TheoryAnchor.1.1 */
            class C12501 extends Statement {
                final /* synthetic */ Statement val$statement;

                C12501(Statement statement) {
                    this.val$statement = statement;
                }

                public void evaluate() throws Throwable {
                    try {
                        this.val$statement.evaluate();
                        TheoryAnchor.this.handleDataPointSuccess();
                    } catch (AssumptionViolatedException e) {
                        TheoryAnchor.this.handleAssumptionViolation(e);
                    } catch (Throwable e2) {
                        TheoryAnchor.this.reportParameterizedError(e2, C15521.this.val$complete.getArgumentStrings(TheoryAnchor.this.nullsOk()));
                    }
                }
            }

            C15521(Class x0, Assignments assignments) {
                this.val$complete = assignments;
                super(x0);
            }

            protected void collectInitializationErrors(List<Throwable> list) {
            }

            public Statement methodBlock(FrameworkMethod method) {
                return new C12501(super.methodBlock(method));
            }

            protected Statement methodInvoker(FrameworkMethod method, Object test) {
                return TheoryAnchor.this.methodCompletesWithParameters(method, this.val$complete, test);
            }

            public Object createTest() throws Exception {
                return getTestClass().getOnlyConstructor().newInstance(this.val$complete.getConstructorArguments(TheoryAnchor.this.nullsOk()));
            }
        }

        public TheoryAnchor(FrameworkMethod method, TestClass testClass) {
            this.successes = 0;
            this.fInvalidParameters = new ArrayList();
            this.fTestMethod = method;
            this.fTestClass = testClass;
        }

        private TestClass getTestClass() {
            return this.fTestClass;
        }

        public void evaluate() throws Throwable {
            runWithAssignment(Assignments.allUnassigned(this.fTestMethod.getMethod(), getTestClass()));
            if (this.successes == 0) {
                Assert.fail("Never found parameters that satisfied method assumptions.  Violated assumptions: " + this.fInvalidParameters);
            }
        }

        protected void runWithAssignment(Assignments parameterAssignment) throws Throwable {
            if (parameterAssignment.isComplete()) {
                runWithCompleteAssignment(parameterAssignment);
            } else {
                runWithIncompleteAssignment(parameterAssignment);
            }
        }

        protected void runWithIncompleteAssignment(Assignments incomplete) throws InstantiationException, IllegalAccessException, Throwable {
            for (PotentialAssignment source : incomplete.potentialsForNextUnassigned()) {
                runWithAssignment(incomplete.assignNext(source));
            }
        }

        protected void runWithCompleteAssignment(Assignments complete) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Throwable {
            new C15521(getTestClass().getJavaClass(), complete).methodBlock(this.fTestMethod).evaluate();
        }

        private Statement methodCompletesWithParameters(FrameworkMethod method, Assignments complete, Object freshInstance) {
            return new C12512(complete, method, freshInstance);
        }

        protected void handleAssumptionViolation(AssumptionViolatedException e) {
            this.fInvalidParameters.add(e);
        }

        protected void reportParameterizedError(Throwable e, Object... params) throws Throwable {
            if (params.length == 0) {
                throw e;
            }
            throw new ParameterizedAssertionError(e, this.fTestMethod.getName(), params);
        }

        private boolean nullsOk() {
            Theory annotation = (Theory) this.fTestMethod.getMethod().getAnnotation(Theory.class);
            if (annotation == null) {
                return false;
            }
            return annotation.nullsAccepted();
        }

        protected void handleDataPointSuccess() {
            this.successes++;
        }
    }

    public Theories(Class<?> klass) throws InitializationError {
        super(klass);
    }

    protected void collectInitializationErrors(List<Throwable> errors) {
        super.collectInitializationErrors(errors);
        validateDataPointFields(errors);
    }

    private void validateDataPointFields(List<Throwable> errors) {
        for (Field field : getTestClass().getJavaClass().getDeclaredFields()) {
            if (field.getAnnotation(DataPoint.class) != null) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    errors.add(new Error("DataPoint field " + field.getName() + " must be static"));
                }
                if (!Modifier.isPublic(field.getModifiers())) {
                    errors.add(new Error("DataPoint field " + field.getName() + " must be public"));
                }
            }
        }
    }

    protected void validateConstructor(List<Throwable> errors) {
        validateOnlyOneConstructor(errors);
    }

    protected void validateTestMethods(List<Throwable> errors) {
        for (FrameworkMethod each : computeTestMethods()) {
            if (each.getAnnotation(Theory.class) != null) {
                each.validatePublicVoid(false, errors);
            } else {
                each.validatePublicVoidNoArg(false, errors);
            }
        }
    }

    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> testMethods = super.computeTestMethods();
        List<FrameworkMethod> theoryMethods = getTestClass().getAnnotatedMethods(Theory.class);
        testMethods.removeAll(theoryMethods);
        testMethods.addAll(theoryMethods);
        return testMethods;
    }

    public Statement methodBlock(FrameworkMethod method) {
        return new TheoryAnchor(method, getTestClass());
    }
}
