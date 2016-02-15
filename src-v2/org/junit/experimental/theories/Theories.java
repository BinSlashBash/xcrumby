/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.theories;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.internal.Assignments;
import org.junit.experimental.theories.internal.ParameterizedAssertionError;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public class Theories
extends BlockJUnit4ClassRunner {
    public Theories(Class<?> class_) throws InitializationError {
        super(class_);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void validateDataPointFields(List<Throwable> list) {
        Field[] arrfield = this.getTestClass().getJavaClass().getDeclaredFields();
        int n2 = arrfield.length;
        int n3 = 0;
        while (n3 < n2) {
            Field field = arrfield[n3];
            if (field.getAnnotation(DataPoint.class) != null) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    list.add(new Error("DataPoint field " + field.getName() + " must be static"));
                }
                if (!Modifier.isPublic(field.getModifiers())) {
                    list.add(new Error("DataPoint field " + field.getName() + " must be public"));
                }
            }
            ++n3;
        }
    }

    @Override
    protected void collectInitializationErrors(List<Throwable> list) {
        super.collectInitializationErrors(list);
        this.validateDataPointFields(list);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> list = super.computeTestMethods();
        List<FrameworkMethod> list2 = this.getTestClass().getAnnotatedMethods(Theory.class);
        list.removeAll(list2);
        list.addAll(list2);
        return list;
    }

    @Override
    public Statement methodBlock(FrameworkMethod frameworkMethod) {
        return new TheoryAnchor(frameworkMethod, this.getTestClass());
    }

    @Override
    protected void validateConstructor(List<Throwable> list) {
        this.validateOnlyOneConstructor(list);
    }

    @Override
    protected void validateTestMethods(List<Throwable> list) {
        for (FrameworkMethod frameworkMethod : this.computeTestMethods()) {
            if (frameworkMethod.getAnnotation(Theory.class) != null) {
                frameworkMethod.validatePublicVoid(false, list);
                continue;
            }
            frameworkMethod.validatePublicVoidNoArg(false, list);
        }
    }

    public static class TheoryAnchor
    extends Statement {
        private List<AssumptionViolatedException> fInvalidParameters = new ArrayList<AssumptionViolatedException>();
        private TestClass fTestClass;
        private FrameworkMethod fTestMethod;
        private int successes = 0;

        public TheoryAnchor(FrameworkMethod frameworkMethod, TestClass testClass) {
            this.fTestMethod = frameworkMethod;
            this.fTestClass = testClass;
        }

        private TestClass getTestClass() {
            return this.fTestClass;
        }

        private Statement methodCompletesWithParameters(final FrameworkMethod frameworkMethod, final Assignments assignments, final Object object) {
            return new Statement(){

                @Override
                public void evaluate() throws Throwable {
                    try {
                        Object[] arrobject = assignments.getMethodArguments(TheoryAnchor.this.nullsOk());
                        frameworkMethod.invokeExplosively(object, arrobject);
                        return;
                    }
                    catch (PotentialAssignment.CouldNotGenerateValueException var1_2) {
                        return;
                    }
                }
            };
        }

        private boolean nullsOk() {
            Theory theory = (Theory)this.fTestMethod.getMethod().getAnnotation(Theory.class);
            if (theory == null) {
                return false;
            }
            return theory.nullsAccepted();
        }

        @Override
        public void evaluate() throws Throwable {
            this.runWithAssignment(Assignments.allUnassigned(this.fTestMethod.getMethod(), this.getTestClass()));
            if (this.successes == 0) {
                Assert.fail("Never found parameters that satisfied method assumptions.  Violated assumptions: " + this.fInvalidParameters);
            }
        }

        protected void handleAssumptionViolation(AssumptionViolatedException assumptionViolatedException) {
            this.fInvalidParameters.add(assumptionViolatedException);
        }

        protected void handleDataPointSuccess() {
            ++this.successes;
        }

        protected /* varargs */ void reportParameterizedError(Throwable throwable, Object ... arrobject) throws Throwable {
            if (arrobject.length == 0) {
                throw throwable;
            }
            throw new ParameterizedAssertionError(throwable, this.fTestMethod.getName(), arrobject);
        }

        protected void runWithAssignment(Assignments assignments) throws Throwable {
            if (!assignments.isComplete()) {
                this.runWithIncompleteAssignment(assignments);
                return;
            }
            this.runWithCompleteAssignment(assignments);
        }

        protected void runWithCompleteAssignment(final Assignments assignments) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Throwable {
            new BlockJUnit4ClassRunner(this.getTestClass().getJavaClass()){

                @Override
                protected void collectInitializationErrors(List<Throwable> list) {
                }

                @Override
                public Object createTest() throws Exception {
                    return this.getTestClass().getOnlyConstructor().newInstance(assignments.getConstructorArguments(TheoryAnchor.this.nullsOk()));
                }

                @Override
                public Statement methodBlock(FrameworkMethod frameworkMethod) {
                    return new Statement(super.methodBlock(frameworkMethod)){
                        final /* synthetic */ Statement val$statement;

                        @Override
                        public void evaluate() throws Throwable {
                            try {
                                this.val$statement.evaluate();
                                TheoryAnchor.this.handleDataPointSuccess();
                                return;
                            }
                            catch (AssumptionViolatedException var1_1) {
                                TheoryAnchor.this.handleAssumptionViolation(var1_1);
                                return;
                            }
                            catch (Throwable var1_2) {
                                TheoryAnchor.this.reportParameterizedError(var1_2, assignments.getArgumentStrings(TheoryAnchor.this.nullsOk()));
                                return;
                            }
                        }
                    };
                }

                @Override
                protected Statement methodInvoker(FrameworkMethod frameworkMethod, Object object) {
                    return TheoryAnchor.this.methodCompletesWithParameters(frameworkMethod, assignments, object);
                }

            }.methodBlock(this.fTestMethod).evaluate();
        }

        protected void runWithIncompleteAssignment(Assignments assignments) throws InstantiationException, IllegalAccessException, Throwable {
            Iterator<PotentialAssignment> iterator = assignments.potentialsForNextUnassigned().iterator();
            while (iterator.hasNext()) {
                this.runWithAssignment(assignments.assignNext(iterator.next()));
            }
        }

    }

}

